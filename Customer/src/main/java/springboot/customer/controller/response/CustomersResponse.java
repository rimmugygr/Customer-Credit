package springboot.customer.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomersResponse {
    private final List<CustomerResponse> customers;
}
