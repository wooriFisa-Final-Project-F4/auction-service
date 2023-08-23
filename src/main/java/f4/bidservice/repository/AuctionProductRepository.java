package f4.bidservice.repository;

import f4.bidservice.entity.AuctionProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionProductRepository extends JpaRepository<AuctionProductEntity, Long> {

  public AuctionProductEntity findByProductId(long productId);
}
