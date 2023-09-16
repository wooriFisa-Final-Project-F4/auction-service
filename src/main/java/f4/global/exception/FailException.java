package f4.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class FailException extends RuntimeException {

  private final Object object;

  public FailException(Object object) {
    super("Feign 통신 중 에러가 발생했습니다.");
    this.object = object;
    log.info("FailException Object : {}",object.toString());
  }
}
