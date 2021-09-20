package springboot.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.customer.controller.response.CustomerResponse;
import springboot.customer.controller.response.CustomersResponse;
import springboot.customer.dto.CustomerDto;
import springboot.customer.mapper.CustomerMapper;
import springboot.customer.model.Customer;
import springboot.customer.service.CustomerService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DataJdbcTest
@ActiveProfiles("test")
class CustomerControllerTest {
    private final String URL_BASE = "/customer/";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService mockService;

    @MockBean
    CustomerMapper mockMapper;

    @DisplayName("when data POST /customer/")
    @Nested
    class PostCustomer {
        String url = URL_BASE;
        CustomerDto customerDto;
        Customer customer;
        String customerDtoRequestJson;

        @BeforeEach
        void setUp() throws Exception {
            //given
            customerDto = CustomerDto.builder()
                    .creditId(2)
                    .firstName("name")
                    .surname("surname")
                    .pesel("12323432")
                    .build();
            customer = Customer.builder()
                    .creditId(2)
                    .firstName("name")
                    .surname("surname")
                    .pesel("12323432")
                    .build();
            customerDtoRequestJson = objectMapper.writeValueAsString(customerDto);
            Mockito.when(mockMapper.map(customerDto))
                    .thenReturn(customer);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockService);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldCreateCustomerWhenProvideCustomer() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerDtoRequestJson));
            //then
            Mockito.verify(mockService).createCustomer(customer);
        }
        @Test
        void shouldResponseCreateStatusWhenCustomerIsCreated() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerDtoRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }

    @DisplayName("when data GET /customer/")
    @Nested
    class GetCustomer {
        List<Integer> creditNumberList;
        String url;
        Customer customer;
        List<Customer> productList;
        CustomersResponse customersResponse;
        CustomerResponse customerResponse;
        String customersResponseJson;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditNumberList = List.of(2);
            url = URL_BASE + "/?ids=2";
            customer = Customer.builder()
                    .creditId(2)
                    .firstName("name")
                    .surname("surname")
                    .pesel("12323432")
                    .build();
            productList = List.of(customer);
            customerResponse = CustomerResponse.builder()
                    .creditId(2)
                    .firstName("name")
                    .surname("surname")
                    .pesel("12323432")
                    .build();
            customersResponse = CustomersResponse.builder()
                    .customers(List.of(customerResponse))
                    .build();
            customersResponseJson = objectMapper.writeValueAsString(customersResponse);
            Mockito.when(mockService.getCustomersByCreditIds(creditNumberList))
                    .thenReturn(productList);
            Mockito.when(mockMapper.mapToResponse(customer))
                    .thenReturn(customerResponse);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockService);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetCustomersWhenProvideListOfCreditsId() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(url));
            //then
            Mockito.verify(mockService).getCustomersByCreditIds(creditNumberList);
        }
        @Test
        void shouldResponseListCustomersWhenCustomersIsFound() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(url));
            //then
            result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
            result.andExpect(MockMvcResultMatchers.content().json(customersResponseJson));
        }
        @Test
        void shouldResponseStatusOkWhenCustomersIsFound() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(url));
            //then
            result.andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
}
