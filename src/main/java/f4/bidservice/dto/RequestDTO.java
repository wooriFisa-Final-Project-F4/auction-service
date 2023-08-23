package f4.bidservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class RequestDTO {

  private long userId;
  private long productId;
  private String pay;
  private String result;
  private boolean status;
  private String password;
  private String account;
}
