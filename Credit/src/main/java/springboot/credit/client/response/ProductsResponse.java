package springboot.credit.client.response;

import lombok.*;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponse {
    private List<ProductDto> products;
}
