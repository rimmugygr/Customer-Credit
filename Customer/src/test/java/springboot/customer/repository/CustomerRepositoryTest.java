package springboot.customer.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.customer.model.Customer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:customer-repository-test.sql")
@DataJdbcTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void shouldFindAllCustomerByCreditIds() {
        //given
        List<Integer> creditNumberList = List.of(5,6);
        List<Customer> expectedCustomerList = entities()
                .map(customerRepository::save)
                .filter(x -> creditNumberList.contains(x.getCreditId()))
                .collect(Collectors.toList());
        //when
        List<Customer> actualCustomerList = customerRepository.findAllByCreditIdIn(creditNumberList);
        //then
        assertEquals(expectedCustomerList, actualCustomerList);
    }

    @Test
    void shouldSaveNewCustomerWithIncrementedId() {
        //given
        Customer product = Customer.builder()
                .creditId(4)
                .firstName("aaaa")
                .pesel("234234")
                .surname("bbbb")
                .build();
        //when
        Customer actualProduct = customerRepository.save(product);
        //then
        assertEquals(5, actualProduct.getId());
    }

    private Stream<Customer> entities() {
        return Stream.of(
                new Customer(null, 5, "aaa", "bbb1", "1234567"),
                new Customer(null, 6, "bbb", "bbb2", "4567223"),
                new Customer(null, 7, "ccc", "bbb3", "4567443"),
                new Customer(null, 8, "sss", "bbb4", "12345367")
        );
    }
}
