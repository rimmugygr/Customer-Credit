package springboot.credit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.client.CustomerClient;
import springboot.credit.dto.CustomerDto;
import springboot.credit.exceptions.ResourceUnprocessable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerClient customerClient;

    public void createCustomer(CustomerDto customer) {
        customerClient.createCustomer(customer);
    }

    public List<CustomerDto> getCustomers(List<Integer> creditNumberList) {
        return customerClient.getCustomers(creditNumberList);
    }
}
