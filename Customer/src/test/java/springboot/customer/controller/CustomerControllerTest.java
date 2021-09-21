package springboot.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.customer.controller.request.CustomerRequest;
import springboot.customer.controller.response.CustomerResponse;
import springboot.customer.controller.response.CustomersResponse;
import springboot.customer.dto.CustomerDto;
import springboot.customer.exceptions.ResourceNotFound;
import springboot.customer.exceptions.ResourceUnprocessable;
import springboot.customer.mapper.CustomerMapper;
import springboot.customer.model.Customer;
import springboot.customer.service.CustomerService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
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
        String url;
        CustomerDto customerDto , customerDtoInvalid;
        CustomerRequest customerRequest, customerRequestInvalid;
        String customerDtoRequestJson, customerRequestInvalidJson;

        @BeforeEach
        void setUp() throws Exception {
            //given
            url = URL_BASE;
            customerDto = CustomerDto.builder()
                    .creditId(2)
                    .firstName("name")
                    .surname("surname")
                    .pesel("12323432")
                    .build();
            customerRequest = CustomerRequest.builder()
                    .creditId(2)
                    .firstName("name")
                    .surname("surname")
                    .pesel("12323432")
                    .build();
            customerDtoRequestJson = objectMapper.writeValueAsString(customerRequest);
            Mockito.when(mockMapper.mapToDto(customerRequest))
                    .thenReturn(customerDto);

            // invalid product case
            customerRequestInvalid = CustomerRequest.builder()
                    .firstName("")
                    .creditId(null)
                    .build();
            customerDtoInvalid = CustomerDto.builder()
                    .firstName("")
                    .creditId(null)
                    .build();
            customerRequestInvalidJson = objectMapper.writeValueAsString(customerRequestInvalid);
            Mockito.when(mockMapper.mapToDto(customerRequestInvalid))
                    .thenReturn(customerDtoInvalid);
            Mockito.doThrow(ResourceUnprocessable.class)
                    .when(mockService).createCustomer(customerDtoInvalid);
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
            Mockito.verify(mockService).createCustomer(customerDto);
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
        @Test
        void shouldResponseUnprocessableEntityStatusWhenCustomerHaveWrongData() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(customerRequestInvalidJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        }
    }

    @DisplayName("when data GET /customer/")
    @Nested
    class GetCustomer {
        List<Integer> creditNumberList, creditNumberListNotFound;
        String url, urlNotFoundIds;
        Customer customer;
        List<Customer> productList;
        CustomersResponse customersResponse;
        CustomerResponse customerResponse;
        String customersResponseJson;

        @BeforeEach
        void setUp() throws Exception {
            //given

            // case with resource found
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

            // case with resource not found
            urlNotFoundIds = URL_BASE + "?ids=3";
            creditNumberListNotFound = List.of(3);
            Mockito.doThrow(ResourceNotFound.class)
                    .when(mockService).getCustomersByCreditIds(creditNumberListNotFound);
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
        @Test
        void shouldResponseNotFoundStatusWhenCustomersNotFoundByIds() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(urlNotFoundIds));
            //then
            result.andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }
}
