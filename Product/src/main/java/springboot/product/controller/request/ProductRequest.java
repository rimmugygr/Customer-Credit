package springboot.product.controller.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String productName;
    private Integer value;
    private Integer creditId;
}
