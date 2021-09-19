package springboot.credit.client.response;

import lombok.*;
import springboot.credit.dto.CustomerDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomersResponse {
    private List<CustomerDto> customers;
}
