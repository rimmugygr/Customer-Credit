package springboot.credit.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditNumberResponse {
    private final Integer creditId;
}
