package f4.domain.service.impl;

import static f4.global.constant.ApiStatus.SUCCESS;

import f4.domain.dto.KafkaDTO;
import f4.domain.dto.request.AuctionRequestDto;
import f4.domain.dto.request.AuctionTimeStatusDto;
import f4.domain.dto.response.ApiResponse;
import f4.domain.dto.response.BidCheckRequestDto;
import f4.domain.kafka.Producer;
import f4.domain.service.AuctionService;
import f4.domain.service.MockAccountServiceAPI;
import f4.domain.service.ProductServiceAPI;
import f4.global.constant.ApiStatus;
import f4.global.constant.CustomErrorCode;
import f4.global.exception.CustomException;
import f4.global.exception.FeignException;
import f4.global.utils.Encryptor;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuctionServiceImpl implements AuctionService {

  private final Encryptor encryptor;
  private final Producer kafkaProducer;
  @Value(value = "${mock.url}")
  private String mockUrl;
  private final MockAccountServiceAPI mockAccountServiceAPI;
  private final ProductServiceAPI productServiceAPI;

  @Override
  public String submitBid(Long id, AuctionRequestDto auctionRequestDto) {
    // product-service에서 상품에 경매 시작,종료 시간 및 경매 상태에 대한 정보를 갖고온다.
    AuctionTimeStatusDto auctionTimeStatusDto = loadByProductId(auctionRequestDto.getProductId());
    // auctionTimeStatusDto Validator
    productValidate(auctionTimeStatusDto);

    //Bid
    BidCheckRequestDto userInfo = loadByBidCheckRequest(auctionRequestDto, id);
    ApiResponse<?> result = userBidAvailabilityCheck(userInfo);

    if (SUCCESS != ApiStatus.of(result.getStatus())) {
      throw new FeignException(result.getError());
    }

    event(id, auctionRequestDto);
    return "입찰에 성공하셨습니다.";
  }

  private AuctionTimeStatusDto loadByProductId(Long productId) {
    return productServiceAPI.getStatus(productId);
  }

  public void productValidate(AuctionTimeStatusDto product) {
    if (!product.getAuctionStatus().equals("PROGRESS")) {
      throw new CustomException(CustomErrorCode.NOT_PROGRESS_PRODUCT);
    }

    if (!product.getAuctionStartTime().isBefore(LocalDateTime.now())) {
      throw new CustomException(CustomErrorCode.NOT_READY_AUCTION);
    }

    if (!product.getAuctionEndTime().isAfter(LocalDateTime.now())) {
      throw new CustomException(CustomErrorCode.ALREADY_END_AUCTION);
    }
  }

  public BidCheckRequestDto loadByBidCheckRequest(AuctionRequestDto auctionRequestDto, Long userId) {
    String encrypt = encryptor.encrypt(auctionRequestDto.getPassword());

    return BidCheckRequestDto.builder()
        .arteUserId(userId)
        .bidPrice(auctionRequestDto.getPrice())
        .password(encrypt)
        .build();
  }

  public void event(Long id, AuctionRequestDto auctionRequestDto) {
    kafkaProducer.produce(KafkaDTO.builder()
        .productId(auctionRequestDto.getProductId())
        .time(LocalDateTime.now())
        .userId(id)
        .request(auctionRequestDto)
        .build());
  }

  public ApiResponse<?> userBidAvailabilityCheck(BidCheckRequestDto bidCheckRequestDto) {
    URI uri = UriComponentsBuilder
        .fromUriString(mockUrl)
        .path("/woori/account/v1/bid/check")
        .encode()
        .build()
        .expand("Flature")
        .toUri();

    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.postForObject(uri, bidCheckRequestDto, ApiResponse.class);
  }

}
