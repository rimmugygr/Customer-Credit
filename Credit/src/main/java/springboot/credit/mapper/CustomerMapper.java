package springboot.credit.mapper;

import org.springframework.stereotype.Service;
import springboot.credit.controller.request.CreditAllInfoRequest;
import springboot.credit.controller.response.CreditAllInfoResponse;
import springboot.credit.dto.CustomerDto;

@Service
public class CustomerMapper {

    public CustomerDto mapToDto(CreditAllInfoRequest.Customer customer) {
        if(customer == null) return null;
        return CustomerDto.builder()
                .pesel(customer.getPesel())
                .firstName(customer.getFirstName())
                .surname(customer.getSurname())
                .build();
    }

    public CreditAllInfoResponse.Customer mapToResponse(CustomerDto customerDto) {
        if(customerDto == null) return null;
        return CreditAllInfoResponse.Customer.builder()
                .firstName(customerDto.getFirstName())
                .pesel(customerDto.getPesel())
                .surname(customerDto.getSurname())
                .build();
    }
}
