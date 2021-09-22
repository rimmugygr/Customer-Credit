package springboot.credit.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.credit.dto.CreditAllInfoDto;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Import(CreditAllInfoService.class)
@ExtendWith(SpringExtension.class)
@DataJdbcTest
@ActiveProfiles("test")
class CreditAllInfoServiceTest {
    @Autowired
    CreditAllInfoService creditAllInfoService;

    @MockBean
    CreditService mockCredit;

    @MockBean
    ProductService mockProduct;

    @MockBean
    CustomerService mockCustomer;

    @DisplayName("createCredit")
    @Nested
    class CreateCredit {
        Integer newCreditId;
        CreditAllInfoDto creditAllInfoDto;
        CustomerDto customerDto;
        ProductDto productDto;
        CreditDto creditDto;

        CreditDto creditDtoWithNewId;
        CustomerDto customerDtoWithNewId;
        ProductDto productDtoWithNewId;

        @BeforeEach
        void setUp() throws Exception {
            //given
            newCreditId = 3;
            customerDto = CustomerDto.builder()
                    .firstName("aaa")
                    .pesel("123456789")
                    .surname("bbb")
                    .build();
            productDto = ProductDto.builder()
                    .productName("asd")
                    .value(123)
                    .build();
            creditDto = CreditDto.builder()
                    .creditName("aaab")
                    .build();
            creditAllInfoDto = CreditAllInfoDto.builder()
                    .credit(creditDto)
                    .customer(customerDto)
                    .product(productDto)
                    .build();
            creditDtoWithNewId = Stream.of(creditDto)
                    .peek(x -> x.setId(newCreditId))
                    .findFirst()
                    .get();
            customerDtoWithNewId = Stream.of(customerDto)
                    .peek(x -> x.setCreditId(newCreditId))
                    .findFirst()
                    .get();
            productDtoWithNewId = Stream.of(productDto)
                    .peek(x -> x.setCreditId(newCreditId))
                    .findFirst()
                    .get();

            Mockito.when(mockCredit.getNewCreditId())
                    .thenReturn(newCreditId);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockCredit);
            Mockito.reset(mockCustomer);
            Mockito.reset(mockProduct);
        }

        @Test
        void shouldCreateCostumerWithNewCreditId() {
            //when
            creditAllInfoService.createCreditInfo(creditAllInfoDto);
            //then
            Mockito.verify(mockCustomer).createCustomer(customerDtoWithNewId);
            assertThat(customerDtoWithNewId.getCreditId(), equalTo(newCreditId));
        }

        @Test
        void shouldCreateProductWithNewCreditId() {
            //when
            creditAllInfoService.createCreditInfo(creditAllInfoDto);
            //then
            Mockito.verify(mockProduct).createProduct(productDtoWithNewId);
            assertThat(productDtoWithNewId.getCreditId(), equalTo(newCreditId));
        }

        @Test
        void shouldCreateCreditInfoWithNewCreditId() {
            //when
            creditAllInfoService.createCreditInfo(creditAllInfoDto);
            //then
            Mockito.verify(mockCredit).saveCreditInfo(creditDtoWithNewId);
            assertThat(creditDtoWithNewId.getId(), equalTo(newCreditId));
        }

        @Test
        void shouldReturnNewCreditId() {
            //when
            Integer newCreditActual = creditAllInfoService.createCreditInfo(creditAllInfoDto);
            //then
            assertThat(newCreditActual, equalTo(newCreditId));
        }
    }

    @DisplayName("getAllCredits")
    @Nested
    class GetAllCredits {
        CreditAllInfoDto creditAllInfoDto;
        CustomerDto customerDto;
        ProductDto productDto;
        CreditDto creditDto;
        Integer creditId;

        List<Integer> creditIdList;
        List<CreditDto> creditDtoList;
        List<CustomerDto> customerDtoList;
        List<ProductDto> productDtoList;
        List<CreditAllInfoDto> creditAllInfoDtoList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditId = 3;
            creditIdList = List.of(creditId);

            customerDto = CustomerDto.builder()
                    .firstName("aaa")
                    .pesel("123456789")
                    .surname("bbb")
                    .creditId(creditId)
                    .build();
            customerDtoList = new ArrayList<>();
            customerDtoList.add(customerDto);

            productDto = ProductDto.builder()
                    .productName("asd")
                    .value(123)
                    .creditId(creditId)
                    .build();
            productDtoList = new ArrayList<>();
            productDtoList.add(productDto);

            creditDto = CreditDto.builder()
                    .creditName("aaab")
                    .id(creditId)
                    .build();
            creditDtoList = new ArrayList<>();
            creditDtoList.add(creditDto);

            creditAllInfoDto = CreditAllInfoDto.builder()
                    .credit(creditDto)
                    .customer(customerDto)
                    .product(productDto)
                    .build();
            creditAllInfoDtoList =  List.of(creditAllInfoDto);

            Mockito.when(mockCredit.getCreditInfos())
                    .thenReturn(creditDtoList);

            Mockito.when(mockCustomer.getCustomers(creditIdList))
                    .thenReturn(customerDtoList);

            Mockito.when(mockProduct.getProducts(creditIdList))
                    .thenReturn(productDtoList);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockCredit);
            Mockito.reset(mockCustomer);
            Mockito.reset(mockProduct);
        }

        @Test
        void shouldGetCostumerListWithCreditId() {
            //when
            creditAllInfoService.getAllCreditsInfo();
            //then
            Mockito.verify(mockCustomer).getCustomers(creditIdList);
        }

        @Test
        void shouldGetProductListWithCreditId() {
            //when
            creditAllInfoService.getAllCreditsInfo();
            //then
            Mockito.verify(mockProduct).getProducts(creditIdList);
        }

        @Test
        void shouldGetAllCreditInfo() {
            //when
            creditAllInfoService.getAllCreditsInfo();
            //then
            Mockito.verify(mockCredit).getCreditInfos();
        }

        @Test
        void shouldReturnAllCredits() {
            //when
            List<CreditAllInfoDto> actualCreditAllInfoDtoList = creditAllInfoService.getAllCreditsInfo();
            //then
            assertThat(actualCreditAllInfoDtoList, equalTo(creditAllInfoDtoList));
        }
    }
}
