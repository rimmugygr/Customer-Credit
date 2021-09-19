package springboot.credit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerDto {
    private Integer creditId;
    private String firstName;
    private String surname;
    private String pesel;
}
