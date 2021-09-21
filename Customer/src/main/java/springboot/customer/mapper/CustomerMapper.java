package springboot.customer.mapper;

import org.springframework.stereotype.Service;
import springboot.customer.controller.request.CustomerRequest;
import springboot.customer.controller.response.CustomerResponse;
import springboot.customer.dto.CustomerDto;
import springboot.customer.model.Customer;

@Service
public class CustomerMapper {

    public Customer map(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .surname(customerDto.getSurname())
                .pesel(customerDto.getPesel())
                .firstName(customerDto.getFirstName())
                .creditId(customerDto.getCreditId())
                .build();
    }
    public CustomerDto mapToDto(Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .surname(customer.getSurname())
                .pesel(customer.getPesel())
                .firstName(customer.getFirstName())
                .creditId(customer.getCreditId())
                .build();
    }
    public CustomerDto mapToDto(CustomerRequest customerRequest){
        return CustomerDto.builder()
                .surname(customerRequest.getSurname())
                .pesel(customerRequest.getPesel())
                .firstName(customerRequest.getFirstName())
                .creditId(customerRequest.getCreditId())
                .build();
    }
    public CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .surname(customer.getSurname())
                .pesel(customer.getPesel())
                .firstName(customer.getFirstName())
                .creditId(customer.getCreditId())
                .build();
    }
}
