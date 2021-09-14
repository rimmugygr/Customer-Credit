package springboot.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.product.controller.request.CreditNumbers;
import springboot.product.controller.response.ProductsResponse;
import springboot.product.dto.ProductDto;
import springboot.product.mapper.ProductMapper;
import springboot.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ProductsResponse getProducts(@RequestBody CreditNumbers creditNumbers) {
        List<ProductDto> productDtoList = productService.getProducts(creditNumbers.getNumbers()).stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
        return ProductsResponse.builder()
                .products(productDtoList)
                .build();
    }

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto) {
        productService.createProduct(productMapper.map(productDto));
    }
}
