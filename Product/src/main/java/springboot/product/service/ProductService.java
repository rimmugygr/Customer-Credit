package springboot.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.product.dto.ProductDto;
import springboot.product.mapper.ProductMapper;
import springboot.product.model.Product;
import springboot.product.repository.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getProducts(List<Integer> creditNumberList){
        return productRepository.findAllByCreditIdIn(creditNumberList);
    }

    public void createProduct(ProductDto productDto) {
        Product product = productMapper.map(productDto);
        productRepository.save(product);
    }
}
