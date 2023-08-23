package f4.bidservice.feign;

import f4.bidservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserFeign {

  @GetMapping(value = "user/{id}")
  public UserDTO getUser(@PathVariable long id);
}
