package springboot.customer.controller.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerRequest {
    private Integer creditId;
    private String firstName;
    private String surname;
    private String pesel;
}
