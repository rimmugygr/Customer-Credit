package springboot.credit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreditDto {
    private Integer id;
    private String creditName;

}
