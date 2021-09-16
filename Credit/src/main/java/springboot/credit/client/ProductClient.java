package springboot.credit.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.credit.client.response.CustomersResponse;
import springboot.credit.client.response.ProductsResponse;
import springboot.credit.configuration.ClientApiConfig;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    private final RestTemplate restTemplate;
    private final ClientApiConfig config;

    public void createProduct(ProductDto product) {
        restTemplate.postForLocation(config.getProduct(),product);
    }

    public List<ProductDto> getProducts(List<Integer> creditsNumber) {
        ProductsResponse customers = restTemplate.getForObject(config.getProduct(), ProductsResponse.class, creditsNumber);
        assert customers != null;
        return customers.getProducts();
    }
}
