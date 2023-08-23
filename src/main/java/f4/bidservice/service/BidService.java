package f4.bidservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import f4.bidservice.dto.AuctionHistoryDTO;
import f4.bidservice.repository.ProductAuctionHistoryRepository;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

    @Autowired
    private ProductAuctionHistoryRepository PAHR;

    //decode
    public Map<String, Object> decode(String info) throws JsonProcessingException {
        Base64.Decoder deco = Base64.getDecoder();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> token = mapper.readValue(new String(deco.decode(info)), Map.class);
        return token;
    }

    public List<AuctionHistoryDTO> findAllHistory() {
        ModelMapper mapper = new ModelMapper();
        return PAHR.findAll().stream().map(e -> mapper.map(e, AuctionHistoryDTO.class)).collect(Collectors.toList());
    }

    public List<AuctionHistoryDTO> findUserHistory(long bidUserId) {
        ModelMapper mapper = new ModelMapper();
        return PAHR.findByBidUserId(bidUserId).stream().map(e -> mapper.map(e, AuctionHistoryDTO.class)).collect(Collectors.toList());
    }


}
