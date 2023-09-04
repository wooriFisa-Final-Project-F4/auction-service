package f4.domain.service;

import f4.domain.dto.response.ApiResponse;
import f4.domain.dto.response.BidCheckRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "MOCK", url = "${mock.url}")
public interface MockAccountServiceAPI {

  @PostMapping("/woori/account/v1/bid/check")
  ApiResponse<?> bidAvailabilityCheck(BidCheckRequestDto bidCheckRequestDto);
}
