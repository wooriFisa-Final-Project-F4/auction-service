package f4.global.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiStatus {

  SUCCESS("success"),
  ERROR("error");

  private final String apiStatus;
}
