package springboot.credit.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.credit.dto.CustomerDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomersResponse {
    private List<CustomerDto> customers;
}
