package springboot.credit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {
    private Integer creditId;
    private String firstName;
    private String surname;
    private String pesel;
}
