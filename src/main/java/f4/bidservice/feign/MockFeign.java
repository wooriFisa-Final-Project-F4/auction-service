package f4.bidservice.feign;

import f4.bidservice.dto.MockDTO;
import f4.bidservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "MOCK")
public interface MockFeign {

  @PostMapping(value = "/mock/check")
  public ResponseEntity<Object> check(UserDTO user);

  @PostMapping(value = "/mock")
  public ResponseEntity<Object> mock(MockDTO mock);

//  @GetMapping(value = "/mock/test")
//  public ResponseEntity<String> test();
}
