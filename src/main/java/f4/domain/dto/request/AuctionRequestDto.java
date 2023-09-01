package f4.domain.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AuctionRequestDto {

  @NotNull
  private Long productId;
  @NotNull
  private String price;
  @NotNull
  private String password;

}
