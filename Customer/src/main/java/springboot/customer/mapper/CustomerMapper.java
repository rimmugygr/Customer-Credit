package springboot.customer.mapper;

import org.mapstruct.Mapper;
import springboot.customer.controller.response.CustomerResponse;
import springboot.customer.dto.CustomerDto;
import springboot.customer.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer map(CustomerDto customerDto);
    CustomerDto mapToDto(Customer customer);
    CustomerResponse mapToResponse(Customer customer);
}
