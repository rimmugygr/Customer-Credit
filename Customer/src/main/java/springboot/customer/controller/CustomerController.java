package springboot.customer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.customer.controller.request.CreditNumbersRequest;
import springboot.customer.controller.response.CustomerResponse;
import springboot.customer.controller.response.CustomersResponse;
import springboot.customer.dto.CustomerDto;
import springboot.customer.mapper.CustomerMapper;
import springboot.customer.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomersResponse getCustomers(@RequestBody CreditNumbersRequest creditNumbersRequest) {
        List<CustomerResponse> customerList = customerService.getCustomersByCreditIds(creditNumbersRequest.getCreditIds()).stream()
                .map(customerMapper::mapToResponse)
                .collect(Collectors.toList());
        return CustomersResponse.builder()
                .customers(customerList)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerMapper.map(customerDto));
    }
}
