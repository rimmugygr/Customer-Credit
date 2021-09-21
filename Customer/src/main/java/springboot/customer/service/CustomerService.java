package springboot.customer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.customer.exceptions.ResourceNotFound;
import springboot.customer.exceptions.ResourceUnprocessable;
import springboot.customer.model.Customer;
import springboot.customer.repository.CustomerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomersByCreditIds(List<Integer> creditNumberList) {
        List<Customer> creditsList = customerRepository.findAllByCreditIdIn(creditNumberList);
        if (creditsList.isEmpty())
            throw new ResourceNotFound("not fund customer by " + creditNumberList.toString());
        return creditsList;
    }

    public void createCustomer(Customer customer) {
        validationOfNewCustomer(customer);
        customerRepository.save(customer);
    }

    private void validationOfNewCustomer(Customer customer) {
        if (customer.getCreditId() <= 0)
            throw new ResourceUnprocessable("missing correct credit id in customer entity");
        if (customer.getFirstName() == null || customer.getFirstName().equals(""))
            throw new ResourceUnprocessable("missing correct first name in customer entity");
        if (customer.getSurname() == null || customer.getSurname().equals(""))
            throw new ResourceUnprocessable("missing correct surname in customer entity");
        if (customer.getPesel() == null || customer.getPesel().equals(""))
            throw new ResourceUnprocessable("missing correct pesel in customer entity");
    }
}
