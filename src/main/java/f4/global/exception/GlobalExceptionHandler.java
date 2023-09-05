package f4.global.exception;

import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({CustomException.class})
  public ResponseEntity<?> customExceptionHandler(CustomException e) {
    log.error(
        "ErrorCode: {}, Message: {}",
        e.getCustomErrorCode().getCode(),
        e.getCustomErrorCode().getMessage());

    return new ResponseEntity<>(
        ErrorDetails.builder()
            .code(e.getCustomErrorCode().getCode())
            .message(e.getCustomErrorCode().getMessage())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<?> feignExceptionHandler(FeignException e) {
    log.error("ErrorCode : {}, ErrorMessage : {}, detail : {}", 500, e.getMessage(), e.getObject().toString());

    return new ResponseEntity<>(
        ErrorDetails.builder()
            .code(500)
            .message(e.getObject().toString())
            .build()
        , HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({NoSuchAlgorithmException.class})
  public ResponseEntity<?> noSuchAlgorithmExceptionHandler(NoSuchAlgorithmException e) {
    return new ResponseEntity<>(
        ErrorDetails.builder().code(500).message("암호화를 수행할 수 없습니다.").build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
