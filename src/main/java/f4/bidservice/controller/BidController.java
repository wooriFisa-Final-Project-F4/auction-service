package f4.bidservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import f4.bidservice.dto.AuctionHistoryDTO;
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

  @ExceptionHandler
  public ResponseEntity<String> handler(Exception e) {
    e.printStackTrace();
    return new ResponseEntity<>("시스템 오류 다시 시도해 주세요", HttpStatus.NOT_MODIFIED);
  }
}
