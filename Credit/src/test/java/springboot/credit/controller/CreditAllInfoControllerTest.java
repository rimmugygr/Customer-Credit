package springboot.credit.controller;

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
import springboot.credit.controller.request.CreditAllInfoRequest;
import springboot.credit.controller.response.CreditNumberResponse;
import springboot.credit.controller.response.CreditAllInfoResponse;
import springboot.credit.controller.response.CreditAllInfoListResponse;
import springboot.credit.dto.CreditAllInfoDto;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;
import springboot.credit.mapper.CreditMapper;
import springboot.credit.service.CreditAllInfoService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DataJdbcTest
@ActiveProfiles("test")
class CreditAllInfoControllerTest {
    private final String URL_BASE = "/credit/";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CreditAllInfoService mockService;

    @MockBean
    CreditMapper mockMapper;

    @DisplayName("when data POST /credit/")
    @Nested
    class PostCredit {
        CreditAllInfoRequest creditAllInfoRequest;
        String creditRequestJson;

        CreditAllInfoDto creditAllInfoDto;

        Integer creditNumber;
        CreditNumberResponse creditNumberResponse;
        String creditNumberResponseJson;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditAllInfoRequest = CreditAllInfoRequest.builder()
                    .credit(new CreditAllInfoRequest.CreditInfo("name_credit"))
                    .customer(new CreditAllInfoRequest.Customer("aa","ss","234"))
                    .product(new CreditAllInfoRequest.Product("name",100))
                    .build();
            creditAllInfoDto = CreditAllInfoDto.builder()
                    .credit(new CreditDto(null,"name_credit"))
                    .customer(new CustomerDto(null,"aa","ss","234"))
                    .product(new ProductDto(null,"name",100))
                    .build();
            creditNumber = 1;
            creditNumberResponse = CreditNumberResponse.builder()
                    .creditId(creditNumber)
                    .build();
            creditRequestJson = objectMapper.writeValueAsString(creditAllInfoRequest);
            creditNumberResponseJson = objectMapper.writeValueAsString(creditNumberResponse);
            Mockito.when(mockMapper.map(creditAllInfoRequest))
                    .thenReturn(creditAllInfoDto);
            Mockito.when(mockService.createCredit(creditAllInfoDto))
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
                    MockMvcRequestBuilders.post(URL_BASE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditRequestJson));
            //then
            Mockito.verify(mockService).createCredit(creditAllInfoDto);
        }
        @Test
        void shouldResponseCreateStatusWhenCreditIsCreated() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post(URL_BASE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }


    @DisplayName("when data GET /credit/")
    @Nested
    class GetCredit {
        CreditAllInfoResponse creditAllInfoResponse;
        CreditAllInfoListResponse creditAllInfoListResponse;
        String creditsResponseJson;
        CreditAllInfoDto creditAllInfoDto;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditAllInfoResponse = CreditAllInfoResponse.builder()
                    .credit(new CreditAllInfoResponse.CreditInfo("name_credit"))
                    .customer(new CreditAllInfoResponse.Customer("aa","ss","234"))
                    .product(new CreditAllInfoResponse.Product("name",100))
                    .build();
            creditAllInfoDto = CreditAllInfoDto.builder()
                    .credit(new CreditDto(null,"name_credit"))
                    .customer(new CustomerDto(null,"aa","ss","234"))
                    .product(new ProductDto(null,"name",100))
                    .build();
            creditAllInfoListResponse = CreditAllInfoListResponse.builder()
                    .credits(List.of(creditAllInfoResponse))
                    .build();
            creditsResponseJson = objectMapper.writeValueAsString(creditAllInfoResponse);
            Mockito.when(mockMapper.map(creditAllInfoDto))
                    .thenReturn(creditAllInfoResponse);
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
                    MockMvcRequestBuilders.get(URL_BASE));
            //then
            Mockito.verify(mockService).getAllCredits();
        }
        @Test
        void shouldResponseStatusOkWhenCreditsAreFound() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(URL_BASE));
            //then
            result.andExpect(MockMvcResultMatchers.status().isOk());
        }
    }
}
