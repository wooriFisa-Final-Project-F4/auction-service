package f4.domain.controller;

import f4.domain.dto.request.AuctionRequestDto;
import f4.domain.service.impl.AuctionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bid/v1")
@Slf4j
public class AuctionController {

  private final AuctionServiceImpl service;

  @PostMapping("/order")
  public ResponseEntity<String> bid(@RequestHeader(name = "userId") Long id,
      @RequestBody AuctionRequestDto request){
    String result = service.bidOrder(id, request);
    return new ResponseEntity<>(result,HttpStatus.OK);
  }

}
