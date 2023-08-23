package f4.bidservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import f4.bidservice.dto.AuctionHistoryDTO;
import f4.bidservice.dto.RequestDTO;
import f4.bidservice.dto.UserDTO;
import f4.bidservice.service.BidService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/bid/v1")
public class BidController {

  @Autowired
  BidService service;

  //경매 이력 조회, 토큰 상태값 보고 관리자면 전체, 유저면 유저의 경매 이력 반환
  @GetMapping
  public List<AuctionHistoryDTO> findHistoryAll(@CookieValue("user") Cookie cookie)
      throws JsonProcessingException {
    String info = cookie.getValue().split("\\.")[1];
    Map<String, Object> map = service.decode(info);
    if (map.get("role").toString().equals("manager")) {
      return service.findAllHistory();
    } else {
      return service.findUserHistory(Long.parseLong(map.get("id").toString()));
    }
  }

  @PostMapping("/order")
  public ResponseEntity<String> bid(RequestDTO request, @CookieValue("user") Cookie cookie)
      throws Exception {
    //jwt에서 유저ID 가져오기
    String info = cookie.getValue().split("\\.")[1];
    Map<String, Object> map = service.decode(info);
    request.setUserId(Long.parseLong(map.get("id").toString()));

    //userAPI에서 유저 정보 가져오기
    UserDTO user = service.getUser(request.getUserId());
    user.setPassword(request.getPassword());
    user.setPay(request.getPay());

    //사용자 비밀번호 확인 && 잔액 조회
    service.userCheck(user);

    //입찰가에 대한 유효성 검사
    request = service.bidValidation(request);

    //입찰 성공인 경우 경매 변경 및 히스토리 추가
    if (request.isStatus()) {
      service.done(request, user);
    }
    return new ResponseEntity<>(request.getResult(), HttpStatus.OK);
  }

  @ExceptionHandler
  public ResponseEntity<String> handler(Exception e) {
    e.printStackTrace();
    return new ResponseEntity<>("시스템 오류 다시 시도해 주세요", HttpStatus.NOT_MODIFIED);
  }
}
