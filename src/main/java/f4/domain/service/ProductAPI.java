package f4.domain.service;

import f4.domain.dto.request.AuctionTimeStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PRODUCT-SERVICE")
public interface ProductAPI {

  @GetMapping("/product/v1/status/{id}")
  AuctionTimeStatusDto getStatus(@PathVariable long id);
}
