package springboot.credit.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.credit.client.response.ProductsResponse;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    private final RestTemplate restTemplate;

    public void createProduct(ProductDto product) {
        restTemplate.postForLocation("http://product:8080",product);
    }

    public List<ProductDto> getProducts(List<Integer> creditsNumber) {
        ProductsResponse customers = restTemplate.getForObject("http://product:8080", ProductsResponse.class, creditsNumber);
        assert customers != null;
        return customers.getProducts();
    }
}
