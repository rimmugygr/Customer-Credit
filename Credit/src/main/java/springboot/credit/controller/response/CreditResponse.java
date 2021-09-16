package springboot.credit.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CreditResponse {
    private final CreditInfo credit;
    private final Product product;
    private final Customer customer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        private String firstName;
        private String surname;
        private String pesel;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String productName;
        private Integer value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditInfo {
        private String creditName;
    }
}


