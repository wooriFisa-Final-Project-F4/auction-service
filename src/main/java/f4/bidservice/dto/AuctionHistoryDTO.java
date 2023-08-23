package f4.bidservice.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionHistoryDTO {

  private long productId;
  private String productName;
  private String bidPrice;
  private Date bidTime;
}
