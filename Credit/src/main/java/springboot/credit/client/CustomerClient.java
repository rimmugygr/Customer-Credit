package springboot.credit.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.credit.client.response.CustomersResponse;
import springboot.credit.configuration.ClientApiConfig;
import springboot.credit.dto.CustomerDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerClient {
    private final RestTemplate restTemplate;
    private final ClientApiConfig config;


    public void createCustomer(CustomerDto customer) {
        restTemplate.postForLocation(config.getCustomer(),customer);
    }

    public List<CustomerDto> getCustomers(List<Integer> creditsNumber) {
        CustomersResponse customers = restTemplate.getForObject(config.getCustomer(), CustomersResponse.class, creditsNumber);
        assert customers != null;
        return customers.getCustomers();
    }
}
