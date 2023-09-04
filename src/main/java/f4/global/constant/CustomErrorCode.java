package f4.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

  // Bad Request 400

  // Unathorized 401

  // Forbidden 402

  // Not Found 404
  ;

  private final int code;
  private final String message;
}
