package springboot.product.controller;

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
import springboot.product.controller.request.CreditNumbersRequest;
import springboot.product.controller.request.ProductRequest;
import springboot.product.controller.response.ProductResponse;
import springboot.product.controller.response.ProductsResponse;
import springboot.product.dto.ProductDto;
import springboot.product.mapper.ProductMapper;
import springboot.product.model.Product;
import springboot.product.service.ProductService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService mockService;

    @MockBean
    ProductMapper mockMapper;


    @DisplayName("when data POST /")
    @Nested
    class PostProduct {
        ProductRequest productRequest;
        ProductDto productDto;
        String productRequestJson;
        @BeforeEach
        void setUp() throws Exception {
            //given
            productRequest = ProductRequest.builder()
                    .productName("name")
                    .value(123)
                    .creditId(21)
                    .build();
            productDto = ProductDto.builder()
                    .productName("name")
                    .value(123)
                    .creditId(21)
                    .build();
            productRequestJson = objectMapper.writeValueAsString(productRequest);
            Mockito.when(mockMapper.mapToDto(productRequest))
                    .thenReturn(productDto);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockService);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldCreateProductWhenProvideProduct() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productRequestJson));
            //then
            Mockito.verify(mockService).createProduct(productDto);
        }
        @Test
        void shouldResponseCreateStatusWhenProductIsCreated() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }

    @DisplayName("when data GET /")
    @Nested
    class GetProduct {
        CreditNumbersRequest creditNumbersRequest;
        List<Integer> creditNumberList;
        String creditNumbersRequestJson;
        Product product;
        List<Product> productList;
        ProductResponse productResponse;
        ProductsResponse productsResponse;
        String productsResponseJson;
        @BeforeEach
        void setUp() throws Exception {
            //given
            creditNumberList = List.of(1);
            creditNumbersRequest = CreditNumbersRequest.builder()
                    .creditIds(creditNumberList)
                    .build();
            product = Product.builder()
                    .id(1)
                    .creditId(1)
                    .productName("name")
                    .value(12)
                    .build();
            productList = List.of(product);
            productResponse = ProductResponse.builder()
                    .creditId(1)
                    .productName("name")
                    .value(12)
                    .build();
            productsResponse = ProductsResponse.builder()
                    .products(List.of(productResponse))
                    .build();
            creditNumbersRequestJson = objectMapper.writeValueAsString(creditNumbersRequest);
            productsResponseJson = objectMapper.writeValueAsString(productsResponse);
            Mockito.when(mockService.getProducts(creditNumberList))
                    .thenReturn(productList);
            Mockito.when(mockMapper.mapToResponse(product))
                    .thenReturn(productResponse);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockService);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetProductsWhenProvideListOfCreditsId() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditNumbersRequestJson));
            //then
            Mockito.verify(mockService).getProducts(creditNumberList);
        }
        @Test
        void shouldResponseListProductsWhenProductsIsFinds() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditNumbersRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
            result.andExpect(MockMvcResultMatchers.content().json(productsResponseJson));
        }
        @Test
        void shouldResponseOkStatusWhenProductsIsFinds() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(creditNumbersRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

}
