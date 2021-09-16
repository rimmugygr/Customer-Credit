package springboot.credit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Integer creditId;
    private String productName;
    private Integer value;
}
