package springboot.customer.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.customer.dto.CustomerDto;
import springboot.customer.exceptions.ResourceNotFound;
import springboot.customer.exceptions.ResourceUnprocessable;
import springboot.customer.mapper.CustomerMapper;
import springboot.customer.model.Customer;
import springboot.customer.repository.CustomerRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(CustomerService.class)
@ExtendWith(SpringExtension.class)
@DataJdbcTest
@ActiveProfiles("test")
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository mockRepository;

    @MockBean
    CustomerMapper mockMapper;

    @DisplayName("getCustomersByCreditIds")
    @Nested
    class GetCustomer {
        List<Integer> creditNumberList, creditNumberListNotFund;
        Customer product1;
        Customer product2;
        List<Customer> productList, productListNotFound;

        @BeforeEach
        void setUp() throws Exception {
            //given

            // case found customers
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

            // case not found customers
            creditNumberListNotFund = List.of(5, 6);
            productListNotFound = Collections.emptyList();
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetListProductWhenProvideListOfId() {
            //when
            List<Customer> products = customerService.getCustomersByCreditIds(creditNumberList);
            //then
            MatcherAssert.assertThat(products, Matchers.notNullValue());
            MatcherAssert.assertThat(products, Matchers.contains(product1, product2));
        }

        @Test
        void shouldInvokeRepositoryForProductsWhenProvideListOfId() {
            //when
            customerService.getCustomersByCreditIds(creditNumberList);
            //then
            Mockito.verify(mockRepository).findAllByCreditIdIn(creditNumberList);
        }

        @Test
        void shouldThrowExceptionWhenProvidedCreditIdsNotFund() {
            //when
            Exception exception = assertThrows(RuntimeException.class, () ->
                    customerService.getCustomersByCreditIds(creditNumberListNotFund));
            //then
            MatcherAssert.assertThat(exception, Matchers.instanceOf(ResourceNotFound.class));
        }
    }

    @DisplayName("createCustomer")
    @Nested
    class CreateCustomer {
        CustomerDto anyCustomerDto , customerDtoInvalid;
        Customer anyCustomer, customerInvalid;

        @BeforeEach
        void setUp() throws Exception {
            //given

            // case with validate data
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

            // case with invalidate data
            customerDtoInvalid = CustomerDto.builder()
                    .firstName("")
                    .surname("")
                    .build();
            customerInvalid = Customer.builder()
                    .firstName("")
                    .surname("")
                    .build();
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldCreateCustomerWhenProvidedAnyCustomer() {
            //when
            customerService.createCustomer(anyCustomerDto);
            //then
            Mockito.verify(mockRepository).save(anyCustomer);
        }

        @Test
        void shouldThrowExceptionWhenProvidedInvalidCustomer() {
            //when
            Exception exception = assertThrows(RuntimeException.class, () ->
                customerService.createCustomer(customerDtoInvalid));
            //then
            MatcherAssert.assertThat(exception, Matchers.instanceOf(ResourceUnprocessable.class));
        }
    }

}
