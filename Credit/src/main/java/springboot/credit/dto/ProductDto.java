package springboot.credit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditInfo {
    private final Credit credit;
    private final Product product;
    private final Customer customer;
}
@Data
@Builder
class Credit {
    private String creditName;
}


@Data
@Builder
class Product {
    private String productName;
    private Integer value;
}
