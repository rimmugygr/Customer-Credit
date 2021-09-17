package springboot.credit.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CreditInfoDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Import(CreditService.class)
@ExtendWith(SpringExtension.class)
class CreditServiceTest {
    @Autowired
    CreditService creditService;

    @MockBean
    CreditInfoService mockCreditInfo;

    @MockBean
    ProductService mockProduct;

    @MockBean
    CustomerService mockCustomer;

    @MockBean
    NewCreditIdService mockNewId;

    @DisplayName("createCredit")
    @Nested
    class CreateCredit {
        Integer newCreditId;
        CreditDto creditDto;
        CustomerDto customerDto;
        ProductDto productDto;
        CreditInfoDto creditInfoDto;

        CreditInfoDto creditInfoDtoWithNewId;
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
            creditInfoDto = CreditInfoDto.builder()
                    .creditName("aaab")
                    .build();
            creditDto = CreditDto.builder()
                    .credit(creditInfoDto)
                    .customer(customerDto)
                    .product(productDto)
                    .build();
            creditInfoDtoWithNewId = Stream.of(creditInfoDto)
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

            Mockito.when(mockNewId.getNewCreditId())
                    .thenReturn(newCreditId);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockCreditInfo);
            Mockito.reset(mockCustomer);
            Mockito.reset(mockProduct);
            Mockito.reset(mockNewId);

        }

        @Test
        void shouldCreateCostumerWithNewCreditId() {
            //when
            creditService.createCredit(creditDto);
            //then
            Mockito.verify(mockCustomer).createCustomer(customerDtoWithNewId);
            assertThat(customerDtoWithNewId.getCreditId(), equalTo(newCreditId));
        }

        @Test
        void shouldCreateProductWithNewCreditId() {
            //when
            creditService.createCredit(creditDto);
            //then
            Mockito.verify(mockProduct).createProduct(productDtoWithNewId);
            assertThat(productDtoWithNewId.getCreditId(), equalTo(newCreditId));
        }

        @Test
        void shouldCreateCreditInfoWithNewCreditId() {
            //when
            creditService.createCredit(creditDto);
            //then
            Mockito.verify(mockCreditInfo).saveCreditInfo(creditInfoDtoWithNewId);
            assertThat(creditInfoDtoWithNewId.getId(), equalTo(newCreditId));
        }

        @Test
        void shouldReturnNewCreditId() {
            //when
            Integer newCreditActual = creditService.createCredit(creditDto);
            //then
            assertThat(newCreditActual, equalTo(newCreditId));
        }
    }

    @DisplayName("getAllCredits")
    @Nested
    class GetAllCredits {
        CreditDto creditDto;
        CustomerDto customerDto;
        ProductDto productDto;
        CreditInfoDto creditInfoDto;
        Integer creditId;

        List<Integer> creditIdList;
        List<CreditInfoDto> creditInfoDtoList;
        List<CustomerDto> customerDtoList;
        List<ProductDto> productDtoList;
        List<CreditDto> creditDtoList;

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

            creditInfoDto = CreditInfoDto.builder()
                    .creditName("aaab")
                    .id(creditId)
                    .build();
            creditInfoDtoList = new ArrayList<>();
            creditInfoDtoList.add(creditInfoDto);

            creditDto = CreditDto.builder()
                    .credit(creditInfoDto)
                    .customer(customerDto)
                    .product(productDto)
                    .build();
            creditDtoList =  List.of(creditDto);

            Mockito.when(mockCreditInfo.getCreditInfos())
                    .thenReturn(creditInfoDtoList);

            Mockito.when(mockCustomer.getCustomers(creditIdList))
                    .thenReturn(customerDtoList);

            Mockito.when(mockProduct.getProducts(creditIdList))
                    .thenReturn(productDtoList);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockCreditInfo);
            Mockito.reset(mockCustomer);
            Mockito.reset(mockProduct);
        }

        @Test
        void shouldGetCostumerListWithCreditId() {
            //when
            creditService.getAllCredits();
            //then
            Mockito.verify(mockCustomer).getCustomers(creditIdList);
        }

        @Test
        void shouldGetProductListWithCreditId() {
            //when
            creditService.getAllCredits();
            //then
            Mockito.verify(mockProduct).getProducts(creditIdList);
        }

        @Test
        void shouldGetAllCreditInfo() {
            //when
            creditService.getAllCredits();
            //then
            Mockito.verify(mockCreditInfo).getCreditInfos();
        }

        @Test
        void shouldReturnAllCredits() {
            //when
            List<CreditDto> actualCreditDtoList = creditService.getAllCredits();
            //then
            assertThat(actualCreditDtoList, equalTo(creditDtoList));
        }
    }
}
