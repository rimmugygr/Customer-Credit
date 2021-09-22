package springboot.credit.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springboot.credit.client.response.CustomersResponse;
import springboot.credit.dto.CustomerDto;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerClient {
    private final String CUSTOMER_URL = "http://customer:8081/customer/";
    private final RestTemplate restTemplate;


    public void createCustomer(CustomerDto customer) {
        log.info("client POST on /customer/ with {}" , customer.toString());

        restTemplate.postForLocation(CUSTOMER_URL, customer);
    }

    public List<CustomerDto> getCustomers(List<Integer> creditsNumber) {
        log.info("client GET on /customer/ with {}" , creditsNumber.toString());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CUSTOMER_URL)
                .queryParam("ids", creditsNumber);

        ResponseEntity<CustomersResponse> exchange = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                CustomersResponse.class);

        log.info("client GET on /customer/ with {} and receive {}" , creditsNumber.toString() , exchange);
        return  Objects.requireNonNull(exchange.getBody()).getCustomers();
    }
}
