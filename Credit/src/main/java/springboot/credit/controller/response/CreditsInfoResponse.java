package springboot.credit.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreditsInfoResponse {
    private final List<CreditInfoResponse> credits;
}
