package springboot.credit.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.credit.client.CustomerClient;
import springboot.credit.dto.CustomerDto;

import java.util.List;

@Import(CustomerService.class)
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerClient mockClient;

    @DisplayName("getCustomers")
    @Nested
    class GetCustomers {
        List<Integer> creditNumberList;
        CustomerDto customerDto;
        List<CustomerDto> customerDtoList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditNumberList = List.of(2);
            customerDto = CustomerDto.builder()
                    .creditId(2)
                    .firstName("aaa")
                    .pesel("123456789")
                    .surname("bbb")
                    .build();
            customerDtoList = List.of(customerDto);
            Mockito.when(mockClient.getCustomers(creditNumberList))
                    .thenReturn(customerDtoList);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockClient);
        }

        @Test
        void shouldGetCostumerList() {
            //when
            List<CustomerDto> customerDtoListActual = customerService.getCustomers(creditNumberList);
            //then
            MatcherAssert.assertThat(customerDtoListActual, Matchers.notNullValue());
            MatcherAssert.assertThat(customerDtoListActual, Matchers.contains(customerDto));
        }

        @Test
        void shouldInvokeClientForCustomers() {
            //when
            customerService.getCustomers(creditNumberList);
            //then
            Mockito.verify(mockClient).getCustomers(creditNumberList);
        }
    }

    @DisplayName("createCustomer")
    @Nested
    class CreateCustomer {
        CustomerDto customerDto;

        @BeforeEach
        void setUp() throws Exception {
            //given
            customerDto = CustomerDto.builder()
                    .creditId(2)
                    .firstName("aaa")
                    .pesel("123456789")
                    .surname("bbb")
                    .build();
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockClient);
        }

        @Test
        void shouldCreateCostumerInClient() {
            //when
            customerService.createCustomer(customerDto);
            //then
            Mockito.verify(mockClient).createCustomer(customerDto);
        }
    }
}
