package f4.global.constant;

import f4.global.exception.CustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiStatus {

  SUCCESS("success"),
  ERROR("error");

  private final String apiStatus;

  public static ApiStatus of(String status) {
    return Arrays.stream(values())
        .filter(i -> i.getApiStatus().equals(status))
        .findFirst()
        .orElseThrow(() -> new CustomException(CustomErrorCode.INVALID_STATUS_CODE));
  }
}
