package springboot.product.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.product.dto.ProductDto;
import springboot.product.exceptions.ResourceNotFound;
import springboot.product.exceptions.ResourceUnprocessable;
import springboot.product.mapper.ProductMapper;
import springboot.product.model.Product;
import springboot.product.repository.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getProductsByCreditIds(List<Integer> creditNumberList){
        List<Product> productList = productRepository.findAllByCreditIdIn(creditNumberList);
        if (productList.isEmpty())
            throw new ResourceNotFound("not fund products by " + creditNumberList.toString());
        return productList;
    }

    public void createProduct(ProductDto productDto) throws ResourceUnprocessable {
        validationOfProduct(productDto);
        Product product = productMapper.map(productDto);
        productRepository.save(product);
    }

    private void validationOfProduct(ProductDto productDto) throws ResourceUnprocessable {
        if (productDto.getCreditId() == null || productDto.getCreditId() <= 0)
            throw new ResourceUnprocessable("missing correct credit id in product entity");
        if (productDto.getProductName() == null || productDto.getProductName().equals(""))
            throw new ResourceUnprocessable("missing correct product name in product entity");
        if (productDto.getValue() == null || productDto.getValue() < 0 )
            throw new ResourceUnprocessable("missing correct value in product entity");
    }
}
