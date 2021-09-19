package springboot.credit.controller.request;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditAllInfoRequest {
    private CreditInfo credit;
    private Product product;
    private Customer customer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Customer {
        private String firstName;
        private String surname;
        private String pesel;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Product {
        private String productName;
        private Integer value;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class CreditInfo {
        private String creditName;
    }
}


