package f4.bidservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import f4.bidservice.dto.AuctionHistoryDTO;
import f4.bidservice.dto.MockDTO;
import f4.bidservice.dto.RequestDTO;
import f4.bidservice.dto.UserDTO;
import f4.bidservice.entity.AuctionProductEntity;
import f4.bidservice.entity.ProductAuctionHistoryEntity;
import f4.bidservice.entity.ProductEntity;
import f4.bidservice.feign.MockFeign;
import f4.bidservice.feign.UserFeign;
import f4.bidservice.repository.AuctionProductRepository;
import f4.bidservice.repository.ProductAuctionHistoryRepository;
import f4.bidservice.repository.ProductRepository;
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
    private AuctionProductRepository APR;
    @Autowired
    private ProductAuctionHistoryRepository PAHR;
    @Autowired
    private ProductRepository PR;
    @Autowired
    private MockFeign mf;
    @Autowired
    private UserFeign uf;
    //decode
    public Map<String, Object> decode(String info) throws JsonProcessingException {
        Base64.Decoder deco = Base64.getDecoder();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> token = mapper.readValue(new String(deco.decode(info)), Map.class);
        return token;
    }

    //경매 이력 전체 조회
    public List<AuctionHistoryDTO> findAllHistory() {
        ModelMapper mapper = new ModelMapper();
        return PAHR.findAll().stream().map(e -> mapper.map(e, AuctionHistoryDTO.class)).collect(Collectors.toList());
    }

    //유저별 경매 이력  조회
    public List<AuctionHistoryDTO> findUserHistory(long bidUserId) {
        ModelMapper mapper = new ModelMapper();
        return PAHR.findByBidUserId(bidUserId).stream().map(e -> mapper.map(e, AuctionHistoryDTO.class)).collect(Collectors.toList());
    }
    
    //유저 정보 가져오기
    public UserDTO getUser(long id) {
        return uf.getUser(id);
    }

    //비밀번호 확인 && 잔액 가능 여부
    public void userCheck(UserDTO user) {
        mf.check(user);
    }

    //입찰가가 낮거나, 연속 입찰일 경우 안돼
    public RequestDTO bidValidation(RequestDTO request) {
        AuctionProductEntity ape = APR.findByProductId(request.getProductId());
        if (Long.parseLong(request.getPay()) <= Long.parseLong(ape.getBidPrice())) {
            request.setResult("제시한 금액이 현재 입찰가보다 낮습니다.");
            request.setStatus(false);
        } else if (ape.getBidUserId() == request.getUserId()) {
            request.setResult("현재 입찰중 입니다.");
            request.setStatus(false);
        } else {
            request.setResult("입찰 성공하셨습니다.");
            request.setStatus(true);
        }
        return request;
    }

    public void done(RequestDTO request, UserDTO user) throws Exception {
        //경매 이력 추가
        ProductEntity pe = PR.findById(request.getProductId()).get();
        PAHR.save(ProductAuctionHistoryEntity.builder().productId(request.getProductId())
            .bidPrice(request.getPay())
            .productName(pe.getName())
            .bidUserId(request.getUserId())
            .build()
        );

        //경매 진행 상품 변경
        AuctionProductEntity ap = APR.findByProductId(request.getProductId());
        long preUserId = 0;
        String preBalance = "";
        if (ap.getBidUserId() != 0) {
            preUserId = ap.getBidUserId();
            preBalance = ap.getBidPrice();
        }
        ap.setBidPrice(request.getPay());
        ap.setBidUserId(request.getUserId());
        APR.save(ap);

        //mock api에 정보 변경 요청
        UserDTO preUser = uf.getUser(preUserId);
        mf.mock(MockDTO.builder()
            .preUserName(preUser.getUserName()).preAccount(preUser.getAccount()).preBalance(preBalance)
            .userName(user.getUserName()).acconut(user.getAccount()).balance(user.getPay()).build());
    }

}
