package springboot.credit.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditAllInfoDto {
    private CreditDto credit;
    private ProductDto product;
    private CustomerDto customer;
}


