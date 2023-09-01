package f4.domain.service;

import f4.domain.dto.response.BidCheckRequestDto;
import f4.domain.dto.response.ApiResponse;
import f4.global.exception.ErrorDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "MOCK",url = "${mock.url}")
public interface MockApi {

  @PostMapping("/woori/account/v1/bid/check")
  ApiResponse<ErrorDetails> bidAvailabilityCheck(BidCheckRequestDto bidCheckRequestDto);
}
