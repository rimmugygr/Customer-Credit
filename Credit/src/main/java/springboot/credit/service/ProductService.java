package springboot.credit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.client.ProductClient;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductClient productClient;

    public void createProduct(ProductDto product) {
        productClient.createProduct(product);
    }

    public List<ProductDto> getProducts(List<Integer> creditsNumber) {
        return productClient.getProducts(creditsNumber);
    }
}
