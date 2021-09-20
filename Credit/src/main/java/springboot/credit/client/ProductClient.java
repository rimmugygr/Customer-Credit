package springboot.credit.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import springboot.credit.client.response.ProductsResponse;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {
    private final String PRODUCT_URL = "http://product:8082/product/";
    private final RestTemplate restTemplate;

    public void createProduct(ProductDto product) {
        restTemplate.postForLocation(PRODUCT_URL, product);
    }

    public List<ProductDto> getProducts(List<Integer> creditsNumber) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PRODUCT_URL)
                .queryParam("ids", creditsNumber);

        ProductsResponse productsResponse = restTemplate.getForObject(builder.toUriString(), ProductsResponse.class);
        assert productsResponse != null;
        return productsResponse.getProducts();
    }
}
