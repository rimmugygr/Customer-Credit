package springboot.product.mapper;

import org.mapstruct.Mapper;
import springboot.product.controller.request.ProductRequest;
import springboot.product.controller.response.ProductResponse;
import springboot.product.dto.ProductDto;
import springboot.product.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper{
    public Product map(ProductDto productDto);
    public ProductResponse mapToResponse(Product product);
    public ProductDto mapToDto(ProductRequest productRequest);
}
