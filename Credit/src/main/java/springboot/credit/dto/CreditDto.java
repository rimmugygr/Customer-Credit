package springboot.credit.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDto {
    private CreditInfoDto credit;
    private ProductDto product;
    private CustomerDto customer;
}


