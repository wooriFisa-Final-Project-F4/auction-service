package f4.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

  // Bad Request 400
  NOT_PROGRESS_PRODUCT(400, "현재 경매 진행 상품이 아닙니다."),
  ALREADY_END_AUCTION(400, "이미 경매가 종료된 상품입니다."),
  NOT_READY_AUCTION(400, "아직 경매 시작 전 상품입니다."),
  // Unathorized 401

  // Forbidden 402

  // Not Found 404
  INVALID_STATUS_CODE(404, "해당 상태 코드는 존재하지 않습니다."),
  ;

  private final int code;
  private final String message;
}
