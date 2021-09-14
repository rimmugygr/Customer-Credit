package springboot.customer.controller.response;

import lombok.Builder;
import springboot.customer.dto.CustomerDto;

import java.util.List;

@Builder
public class CustomerListResponse {
    private final List<CustomerDto> customers;
}
