package springboot.customer.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerDto {
    private Integer id;
    private Integer creditId;
    private String firstName;
    private String surname;
    private String pesel;
}
