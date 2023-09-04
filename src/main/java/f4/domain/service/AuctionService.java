package f4.domain.service;

import f4.domain.dto.request.AuctionRequestDto;
import org.springframework.stereotype.Service;

public interface AuctionService {

  String bidOrder(Long id, AuctionRequestDto request);
}
