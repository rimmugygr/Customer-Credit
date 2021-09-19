package springboot.credit.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.credit.client.response.CustomersResponse;
import springboot.credit.dto.CustomerDto;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerClient {
    private final RestTemplate restTemplate;


    public void createCustomer(CustomerDto customer) {
        restTemplate.postForLocation("http://customer:8080",customer);
    }

    public List<CustomerDto> getCustomers(List<Integer> creditsNumber) {
        CustomersResponse customers = restTemplate.getForObject("http://customer:8080", CustomersResponse.class, creditsNumber);
        assert customers != null;
        return customers.getCustomers();
    }
}
