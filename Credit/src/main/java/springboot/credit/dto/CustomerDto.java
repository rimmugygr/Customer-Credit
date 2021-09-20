package springboot.credit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Integer creditId;
    private String firstName;
    private String surname;
    private String pesel;
}
