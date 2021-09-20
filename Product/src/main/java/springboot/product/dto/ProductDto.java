package springboot.product.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Integer id;
    private String productName;
    private Integer value;
    private Integer creditId;
}
