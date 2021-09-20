package springboot.credit.client.response;

import lombok.*;
import springboot.credit.dto.CustomerDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomersResponse {
    private List<CustomerDto> customers;
}
