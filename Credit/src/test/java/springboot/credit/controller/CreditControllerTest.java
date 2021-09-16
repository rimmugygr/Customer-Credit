package springboot.credit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.credit.controller.request.CreditRequest;
import springboot.credit.controller.response.CreditNumberResponse;
import springboot.credit.controller.response.CreditResponse;
import springboot.credit.controller.response.CreditsResponse;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CreditInfoDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;
import springboot.credit.mapper.CreditMapper;
import springboot.credit.service.CreditService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CreditControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CreditService mockService;

    @MockBean
    CreditMapper mockMapper;

    @DisplayName("when data POST /")
    @Nested
    class PostCredit {
        CreditRequest creditRequest;
        String creditRequestJson;

        CreditDto creditDto;

        Integer creditNumber;
        CreditNumberResponse creditNumberResponse;
        String creditNumberResponseJson;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditRequest = CreditRequest.builder()
                    .credit(new CreditRequest.CreditInfo("name_credit"))
                    .customer(new CreditRequest.Customer("aa","ss","234"))
                    .product(new CreditRequest.Product("name",100))
                    .build();
            creditDto = CreditDto.builder()
                    .credit(new CreditInfoDto(null,"name_credit"))
                    .customer(new CustomerDto(null,"aa","ss","234"))
                    .product(new ProductDto(null,"name",100))
                    .build();
            creditNumber = 1;
            creditNumberResponse = CreditNumberResponse.builder()
                    .creditId(creditNumber)
                    .build();
            creditRequestJson = objectMapper.writeValueAsString(creditRequest);
            creditNumberResponseJson = objectMapper.writeValueAsString(creditNumberResponse);
            Mockito.when(mockMapper.map(creditRequest))
                    .thenReturn(creditDto);
            Mockito.when(mockService.createCredit(creditDto))
                    .thenReturn(creditNumber);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockService);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldCreateCreditWhenProvidedNewCredit() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditRequestJson));
            //then
            Mockito.verify(mockService).createCredit(creditDto);
        }
        @Test
        void shouldResponseCreateStatusWhenCreditIsCreated() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }


    @DisplayName("when data GET /")
    @Nested
    class GetCredit {
        CreditResponse creditResponse;
        CreditsResponse creditsResponse;
        String creditsResponseJson;
        CreditDto creditDto;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditResponse = CreditResponse.builder()
                    .credit(new CreditResponse.CreditInfo("name_credit"))
                    .customer(new CreditResponse.Customer("aa","ss","234"))
                    .product(new CreditResponse.Product("name",100))
                    .build();
            creditDto = CreditDto.builder()
                    .credit(new CreditInfoDto(null,"name_credit"))
                    .customer(new CustomerDto(null,"aa","ss","234"))
                    .product(new ProductDto(null,"name",100))
                    .build();
            creditsResponse = CreditsResponse.builder()
                    .credits(List.of(creditResponse))
                    .build();
            creditsResponseJson = objectMapper.writeValueAsString(creditResponse);
            Mockito.when(mockMapper.map(creditDto))
                    .thenReturn(creditResponse);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockService);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetAllCreditsWhenGet() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/"));
            //then
            Mockito.verify(mockService).getAllCredits();
        }
        @Test
        void shouldResponseStatusOkWhenCreditsAreFound() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/"));
            //then
            result.andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }
}
