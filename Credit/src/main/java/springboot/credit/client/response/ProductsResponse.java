package springboot.credit.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {
    private List<ProductDto> products;
}
