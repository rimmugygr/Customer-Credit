package springboot.customer.controller.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditNumbersRequest {
    private List<Integer> creditIds;
}
