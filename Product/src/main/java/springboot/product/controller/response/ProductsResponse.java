package springboot.product.controller.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductsResponse {
    private final List<ProductResponse> products;
}
