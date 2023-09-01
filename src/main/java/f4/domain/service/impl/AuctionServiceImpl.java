package f4.domain.service.impl;

import f4.domain.dto.request.AuctionRequestDto;
import f4.domain.dto.request.AuctionTimeStatusDto;
import f4.domain.dto.response.BidCheckRequestDto;
import f4.domain.dto.request.KafkaDTO;
import f4.domain.dto.response.ApiResponse;
import f4.domain.kafka.Producer;
import f4.domain.service.AuctionService;
import f4.domain.service.MockApi;
import f4.domain.service.ProductAPI;
import f4.global.constant.ApiStatus;
import f4.global.constant.ValidationResult;
import f4.global.exception.ErrorDetails;
import f4.global.utils.Encryptor;
import feign.FeignException;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final Encryptor encryptor;
    private final Producer kafkaProducer;
    @Value(value = "${mock.url}")
    private String mockUrl;
//    private final MockApi mockApi;
    private final ProductAPI productApi;
    private final Logger logger = LoggerFactory.getLogger(AuctionServiceImpl.class);

    @Override
    public String bidOrder(Long id, AuctionRequestDto request){
        try{
            if(productValidate(productApi.getStatus(request.getProductId()))){
                return ValidationResult.NOT_PROGRESS.getMessage();
            }
            BidCheckRequestDto user = bidCheckRequestBuilder(request,id);

            ApiResponse<ErrorDetails> result = userCheck(user);
//            ApiResponse<ErrorDetails> result = mockApi.bidAvailabilityCheck(user);
            if (result.getStatus().equals(ApiStatus.SUCCESS.getApiStatus())) {
                event(id, request);
                return ValidationResult.SUCCESS.getMessage();
            } else {
                return result.getError().getMessage();
            }
        }catch (FeignException e){
            logger.error("error : { } ", e);
            return e.getMessage();
        }
    }

    public boolean productValidate(AuctionTimeStatusDto product){
      return !LocalDateTime.now().isAfter(product.getAuctionEndTime()) && !product.getAuctionStatus()
          .equals("END") &&
          !LocalDateTime.now().isBefore(product.getAuctionStartTime()) && !product.getAuctionStatus()
          .equals("WAIT");
    }

    public BidCheckRequestDto bidCheckRequestBuilder(AuctionRequestDto request, long id){
        return BidCheckRequestDto.builder()
            .arteUserId(id)
            .bidPrice(request.getPrice())
            .password(encryptor.encrypt(request.getPassword())).build();
    }

    public void event(Long id, AuctionRequestDto request){
        kafkaProducer.produce(KafkaDTO.builder()
            .productId(request.getProductId())
            .time(LocalDateTime.now())
            .userId(id)
            .request(request)
            .build());
    }

    public ApiResponse<ErrorDetails> userCheck(BidCheckRequestDto user){
        URI uri = UriComponentsBuilder
            .fromUriString(mockUrl)
            .path("/woori/account/v1/bid/check")
            .encode()
            .build()
            .expand("Flature")
            .toUri();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(uri, user, ApiResponse.class);
    }

}
