package f4.bidservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class MockDTO {

    private String preUserName;
    private String preAccount;
    private String preBalance;
    private String userName;
    private String acconut;
    private String balance;
}


