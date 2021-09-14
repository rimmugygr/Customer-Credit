package springboot.credit.controller.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditInfo {
    private final Credit credit;
    private final Product product;
}
