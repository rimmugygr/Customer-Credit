package springboot.customer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.customer.dto.CustomerDto;
import springboot.customer.exceptions.ResourceNotFound;
import springboot.customer.exceptions.ResourceUnprocessable;
import springboot.customer.mapper.CustomerMapper;
import springboot.customer.model.Customer;
import springboot.customer.repository.CustomerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<Customer> getCustomersByCreditIds(List<Integer> creditNumberList) {
        List<Customer> customerList = customerRepository.findAllByCreditIdIn(creditNumberList);
        if (customerList.isEmpty())
            throw new ResourceNotFound("not fund customer by " + creditNumberList.toString());
        return customerList;
    }

    public void createCustomer(CustomerDto customerDto) {
        validationOfNewCustomer(customerDto);
        Customer customer = customerMapper.map(customerDto);
        customerRepository.save(customer);
    }

    private void validationOfNewCustomer(CustomerDto customerDto) {
        if (customerDto.getCreditId() == null || customerDto.getCreditId() <= 0)
            throw new ResourceUnprocessable("missing correct credit id in customer entity");
        if (customerDto.getFirstName() == null || customerDto.getFirstName().equals(""))
            throw new ResourceUnprocessable("missing correct first name in customer entity");
        if (customerDto.getSurname() == null || customerDto.getSurname().equals(""))
            throw new ResourceUnprocessable("missing correct surname in customer entity");
        if (customerDto.getPesel() == null || customerDto.getPesel().equals(""))
            throw new ResourceUnprocessable("missing correct pesel in customer entity");
    }
}
