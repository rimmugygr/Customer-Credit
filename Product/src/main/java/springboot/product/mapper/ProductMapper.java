package springboot.product.mapper;

import org.mapstruct.Mapper;
import springboot.product.dto.ProductDto;
import springboot.product.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper{
    public Product map(ProductDto productDto);
    public ProductDto map(Product product);
}
