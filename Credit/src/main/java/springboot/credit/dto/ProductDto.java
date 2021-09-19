package springboot.credit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {
    private Integer creditId;
    private String productName;
    private Integer value;
}
