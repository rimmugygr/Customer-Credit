package springboot.credit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDto {
    private Integer id;
    private String creditName;

}
