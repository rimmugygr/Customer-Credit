package springboot.product.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.product.controller.request.ProductRequest;
import springboot.product.controller.response.ProductResponse;
import springboot.product.controller.response.ProductsResponse;
import springboot.product.dto.ProductDto;
import springboot.product.mapper.ProductMapper;
import springboot.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/product/", produces = "application/json")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductsResponse getProducts(@RequestParam(value = "ids") List<Integer> creditNumbers) {
        System.out.println(creditNumbers);

        List<ProductResponse> productsResponseList = productService
                .getProducts(creditNumbers)
                .stream()
                .map(productMapper::mapToResponse)
                .collect(Collectors.toList());
        return ProductsResponse.builder()
                .products(productsResponseList)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        System.out.println(productRequest);
        ProductDto productDto = productMapper.mapToDto(productRequest);
        System.out.println(productDto);
        productService.createProduct(productDto);
    }
}
