package springboot.credit.client.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditNumbersRequest {
    private List<Integer> creditIds;
}
