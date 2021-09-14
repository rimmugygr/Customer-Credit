package springboot.credit.controller.response;

import lombok.Builder;
import lombok.Data;
import springboot.credit.dto.CreditInfo;

import java.util.List;

@Data
@Builder
public class CreditInfoList {
    private final List<CreditInfo> credits;
}
