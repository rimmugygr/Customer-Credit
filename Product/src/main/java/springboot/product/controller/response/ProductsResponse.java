package springboot.product.controller.response;


import lombok.Builder;
import springboot.product.dto.ProductDto;

import java.util.List;

@Builder
public class ProductsResponse {
    private final List<ProductDto> products;
}
