package f4.global.exception;

import lombok.Getter;

@Getter
public class FeignException extends RuntimeException {

  private final Object object;

  public FeignException(Object object) {
    super("Feign 통신 중 에러가 발생했습니다.");
    this.object = object;
  }
}
