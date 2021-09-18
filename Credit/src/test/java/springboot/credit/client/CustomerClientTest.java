package springboot.credit.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import springboot.credit.client.response.CustomersResponse;
import springboot.credit.dto.CustomerDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ExtendWith(SpringExtension.class)
@RestClientTest(CustomerClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
class CustomerClientTest {

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        server.reset();
    }

    @Test
    void createCustomer() throws Exception {
        //given
        CustomerDto customerDto = CustomerDto.builder()
                .creditId(4)
                .surname("matejko")
                .firstName("jan")
                .pesel("1234567")
                .build();
        String customersDtoJson = objectMapper.writeValueAsString(customerDto);

        server.expect(requestTo("/"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(customersDtoJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withStatus(HttpStatus.CREATED));
        //when
        customerClient.createCustomer(customerDto);
        //then
        server.verify();
    }

    @Test
    void getCustomers() throws Exception {
        //given
        List<Integer> creditIdList = List.of(4);

        CustomerDto customerDto = CustomerDto.builder()
                .creditId(4)
                .surname("matejko")
                .firstName("jan")
                .pesel("1234567")
                .build();
        CustomersResponse customersResponse = CustomersResponse.builder()
                .customers(List.of(customerDto))
                .build();
        List<CustomerDto> customerDtoList = customersResponse.getCustomers();

        String customersResponseJson = objectMapper.writeValueAsString(customersResponse);

        server.expect(requestTo("/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(customersResponseJson));
        //when
        List<CustomerDto> customersDtoActual = customerClient.getCustomers(creditIdList);
        //then
        assertEquals(customerDtoList, customersDtoActual);
        server.verify();
    }

}
