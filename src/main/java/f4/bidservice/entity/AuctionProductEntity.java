package f4.bidservice.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "auction_product")
public class AuctionProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long bidUserId;
  private long productId;
  private String bidPrice;
  private String auctionStatus;
  private Date auctionStartTime;
  private Date auctionEndTime;

}
