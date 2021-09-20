package springboot.customer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.customer.model.Customer;
import springboot.customer.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomersByCreditIds(List<Integer> creditNumberList) {
        return new ArrayList<>(customerRepository.findAllByCreditIdIn(creditNumberList));
    }

    public void createCustomer(Customer customer) {
        System.out.println(customer);
        customerRepository.save(customer);
    }
}
