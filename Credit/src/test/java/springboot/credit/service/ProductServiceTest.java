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
import springboot.credit.client.ProductClient;
import springboot.credit.dto.ProductDto;

import java.util.List;

@Import(ProductService.class)
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @MockBean
    ProductClient mockClient;

    @DisplayName("getProducts")
    @Nested
    class GetProducts {
        List<Integer> creditNumberList;
        ProductDto productDto;
        List<ProductDto> productDtoList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditNumberList = List.of(2);
            productDto = ProductDto.builder()
                    .creditId(2)
                    .productName("asd")
                    .value(123)
                    .build();
            productDtoList = List.of(productDto);
            Mockito.when(mockClient.getProducts(creditNumberList))
                    .thenReturn(productDtoList);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockClient);
        }

        @Test
        void shouldGetCostumerList() {
            //when
            List<ProductDto> productDtoListActual = productService.getProducts(creditNumberList);
            //then
            MatcherAssert.assertThat(productDtoListActual, Matchers.notNullValue());
            MatcherAssert.assertThat(productDtoListActual, Matchers.contains(productDto));
        }

        @Test
        void shouldInvokeClientForCustomers() {
            //when
            productService.getProducts(creditNumberList);
            //then
            Mockito.verify(mockClient).getProducts(creditNumberList);
        }
    }

    @DisplayName("createProduct")
    @Nested
    class CreateProduct {
        ProductDto productDto;

        @BeforeEach
        void setUp() throws Exception {
            //given
            productDto = ProductDto.builder()
                    .creditId(2)
                    .productName("asd")
                    .value(123)
                    .build();
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockClient);
        }

        @Test
        void shouldCreateCostumerInClient() {
            //when
            productService.createProduct(productDto);
            //then
            Mockito.verify(mockClient).createProduct(productDto);
        }
    }
}
