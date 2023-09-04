package f4.domain.service;

import f4.domain.dto.request.AuctionRequestDto;

public interface AuctionService {

  String submitBid(Long id, AuctionRequestDto auctionRequestDto);
}
