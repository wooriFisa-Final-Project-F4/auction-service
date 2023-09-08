package f4.domain.controller;

import f4.domain.dto.request.AuctionRequestDto;
import f4.domain.service.AuctionService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AuctionController {

  private final AuctionService service;

  /*
  * @date : 2023.08.29
  * @author : KHJ
  * @param : @RequestHeader(userId), @AuctionRequestDto(productId, price, password)
  * @description : 계좌를 조회하여 해당 입찰금이 있는지 파악 후 경매를 참여토록 한다.
  */
  @PostMapping("/tender")
  public ResponseEntity<?> submitBid(
      @RequestHeader("userId") Long userId,
      @Valid @RequestBody AuctionRequestDto request) {
    log.info("입찰 수행. userId : {}", userId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(service.submitBid(userId, request));
  }

}
