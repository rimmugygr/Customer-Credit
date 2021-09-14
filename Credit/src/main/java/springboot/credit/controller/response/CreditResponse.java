package springboot.credit.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditResponse {
    private final Credit credit;
    private final Product product;
    private final Customer customer;

    @Data
    @Builder
    public static class Customer {
        private final String firstName;
        private final String surname;
        private final String pesel;
    }

    @Data
    @Builder
    public static class Product {
        private final String productName;
        private final Integer value;
    }

    @Data
    @Builder
    public static class Credit {
        private final String creditName;
    }
}


