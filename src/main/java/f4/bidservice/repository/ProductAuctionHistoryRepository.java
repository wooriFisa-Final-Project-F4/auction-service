package f4.bidservice.repository;

import f4.bidservice.entity.ProductAuctionHistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAuctionHistoryRepository extends
    JpaRepository<ProductAuctionHistoryEntity, Long> {

  public List<ProductAuctionHistoryEntity> findByBidUserId(long bidUserId);
}
