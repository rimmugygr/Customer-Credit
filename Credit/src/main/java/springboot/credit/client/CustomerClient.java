package springboot.credit.client;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springboot.credit.client.request.CreditNumbersRequest;
import springboot.credit.client.response.CustomersResponse;
import springboot.credit.client.response.ProductsResponse;
import springboot.credit.dto.CustomerDto;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerClient {
    private final String CUSTOMER_URL = "http://localhost:8081/";
    private final RestTemplate restTemplate;


    public void createCustomer(CustomerDto customer) {
        restTemplate.postForLocation(CUSTOMER_URL, customer);
    }

    public List<CustomerDto> getCustomers(List<Integer> creditsNumber) {

        System.out.println(creditsNumber);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CUSTOMER_URL)
                .queryParam("ids", creditsNumber);

        System.out.println(builder.toUriString());

        ResponseEntity<CustomersResponse> exchange = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                CustomersResponse.class);

        return  Objects.requireNonNull(exchange.getBody()).getCustomers();
    }
}
