package springboot.credit.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditInfo {
    private final Credit credit;
    private final Product product;
    private final Customer customer;

    @Data
    @Builder
    public static class Customer {
        private String firstName;
        private String surname;
        private String pesel;
    }

    @Data
    @Builder
    public static class Product {
        private String productName;
        private Integer value;
    }

    @Data
    @Builder
    public static class Credit {
        private String creditName;
    }
}


