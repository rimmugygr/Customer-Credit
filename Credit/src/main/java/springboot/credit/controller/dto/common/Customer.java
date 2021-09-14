package springboot.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Integer id;
    private String firstName;
    private String surname;
    private String pesel;
}
