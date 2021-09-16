package springboot.customer.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.customer.dto.CustomerDto;
import springboot.customer.mapper.CustomerMapper;
import springboot.customer.model.Customer;
import springboot.customer.repository.CustomerRepository;

import java.util.List;

@Import(CustomerService.class)
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @Autowired
    CustomerService productService;

    @MockBean
    CustomerRepository mockRepository;

    @MockBean
    CustomerMapper mockMapper;

    @DisplayName("getCustomersByCreditIds")
    @Nested
    class GetCustomer {
        List<Integer> creditNumberList;
        Customer product1;
        Customer product2;
        List<Customer> productList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditNumberList = List.of(1,2);
            product1 = Customer.builder()
                    .creditId(2)
                    .id(3)
                    .firstName("asdasd1")
                    .surname("surname1")
                    .pesel("734321")
                    .build();
            product2 = Customer.builder()
                    .creditId(1)
                    .id(4)
                    .firstName("asdasd2")
                    .surname("surname2")
                    .pesel("73432")
                    .build();
            productList = List.of(product1, product2);
            Mockito.when(mockRepository.findAllByCreditIdIn(creditNumberList))
                    .thenReturn(productList);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetListProductWhenProvideListOfId() {
            //when
            List<Customer> products = productService.getCustomersByCreditIds(creditNumberList);
            //then
            MatcherAssert.assertThat(products, Matchers.notNullValue());
            MatcherAssert.assertThat(products, Matchers.contains(product1, product2));
        }

        @Test
        void shouldInvokeRepositoryForProductsWhenProvideListOfId() {
            //when
            productService.getCustomersByCreditIds(creditNumberList);
            //then
            Mockito.verify(mockRepository).findAllByCreditIdIn(creditNumberList);
        }
    }

    @DisplayName("createCustomer")
    @Nested
    class CreateCustomer {
        CustomerDto anyCustomerDto;
        Customer anyCustomer;

        @BeforeEach
        void setUp() throws Exception {
            //given
            anyCustomerDto = CustomerDto.builder()
                    .creditId(2)
                    .firstName("asdasd")
                    .surname("surname")
                    .pesel("73432")
                    .build();
            anyCustomer = Customer.builder()
                    .creditId(2)
                    .firstName("asdasd")
                    .surname("surname")
                    .pesel("73432")
                    .build();
            Mockito.when(mockMapper.map(anyCustomerDto))
                    .thenReturn(anyCustomer);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldCreateCustomerWhenProvidedAnyCustomer() {
            //when
            productService.createCustomer(anyCustomer);
            //then
            Mockito.verify(mockRepository).save(anyCustomer);
        }
    }

}
