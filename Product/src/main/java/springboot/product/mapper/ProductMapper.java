package springboot.product.mapper;


import org.springframework.stereotype.Service;
import springboot.product.controller.request.ProductRequest;
import springboot.product.controller.response.ProductResponse;
import springboot.product.dto.ProductDto;
import springboot.product.model.Product;

@Service
public class ProductMapper{
    public Product map(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .productName(productDto.getProductName())
                .value(productDto.getValue())
                .creditId(productDto.getCreditId())
                .build();
    }
    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productName(product.getProductName())
                .creditId(product.getCreditId())
                .value(product.getValue())
                .build();
    }
    public ProductDto mapToDto(ProductRequest productRequest) {
        return ProductDto.builder()
                .value(productRequest.getValue())
                .productName(productRequest.getProductName())
                .creditId(productRequest.getCreditId())
                .build();
    }
}
