package springboot.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.product.model.Product;
import springboot.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts(List<Integer> creditNumberList){
        List<Product> productList = new ArrayList<>();
        productRepository.findAllByCreditID(creditNumberList)
                .spliterator()
                .forEachRemaining(productList::add);
        return productList;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }
}
