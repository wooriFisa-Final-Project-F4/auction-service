package f4.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationResult {
  NOT_PROGRESS("경매 중인 상품이 아닙니다."),
  SUCCESS("입찰 요청 되었습니다.");

  private final String message;
}
