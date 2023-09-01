package f4.domain.service;

import f4.domain.dto.request.AuctionRequestDto;

public interface AuctionService {

  String bidOrder(Long id, AuctionRequestDto request);
}
