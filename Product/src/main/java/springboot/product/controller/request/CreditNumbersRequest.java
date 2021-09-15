package springboot.product.controller.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreditNumbers {
    private List<Integer> creditIds;
}
