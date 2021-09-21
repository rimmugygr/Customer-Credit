package springboot.product.controller;

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
import springboot.product.controller.request.ProductRequest;
import springboot.product.controller.response.ProductResponse;
import springboot.product.controller.response.ProductsResponse;
import springboot.product.dto.ProductDto;
import springboot.product.exceptions.ResourceNotFound;
import springboot.product.exceptions.ResourceUnprocessable;
import springboot.product.mapper.ProductMapper;
import springboot.product.model.Product;
import springboot.product.service.ProductService;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class ProductControllerTest {
    private final String URL_BASE = "/product/";

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService mockService;

    @MockBean
    ProductMapper mockMapper;


    @DisplayName("when data POST /product/")
    @Nested
    class PostProduct {
        String url;
        ProductRequest productRequest, productRequestInvalid;
        ProductDto productDto, productDtoInvalid;
        String productRequestJson, productRequestInvalidJson;

        @BeforeEach
        void setUp() throws Exception {
            //given
            url = URL_BASE;

            // any product case
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

            // invalid product case
            productRequestInvalid = ProductRequest.builder()
                    .productName("")
                    .value(null)
                    .build();
            productDtoInvalid = ProductDto.builder()
                    .productName("")
                    .value(null)
                    .build();
            productRequestInvalidJson = objectMapper.writeValueAsString(productRequestInvalid);
            Mockito.when(mockMapper.mapToDto(productRequestInvalid))
                    .thenReturn(productDtoInvalid);
            Mockito.doThrow(ResourceUnprocessable.class)
                    .when(mockService).createProduct(productDtoInvalid);
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
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productRequestJson));
            //then
            Mockito.verify(mockService).createProduct(productDto);
        }
        @Test
        void shouldResponseCreateStatusWhenProductIsCreated() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productRequestJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isCreated());
        }
        @Test
        void shouldResponseUnprocessableEntityStatusWhenProductHaveWrongData() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productRequestInvalidJson));
            //then
            result.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        }
    }

    @DisplayName("when data GET /product/")
    @Nested
    class GetProduct {
        String url, urlNotFound;
        List<Integer> creditNumberList, creditNumberListNotFound;
        Product product;
        List<Product> productList;
        ProductResponse productResponse;
        ProductsResponse productsResponse;
        String productsResponseJson;

        @BeforeEach
        void setUp() throws Exception {
            //given

            // case with resource found
            creditNumberList = List.of(1);
            url = URL_BASE + "?ids=1";
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
            productsResponseJson = objectMapper.writeValueAsString(productsResponse);
            Mockito.when(mockService.getProductsByCreditIds(creditNumberList))
                    .thenReturn(productList);
            Mockito.when(mockMapper.mapToResponse(product))
                    .thenReturn(productResponse);

            // case with resource not found
            urlNotFound = URL_BASE + "?ids=3";
            creditNumberListNotFound = List.of(3);
            Mockito.doThrow(ResourceNotFound.class)
                    .when(mockService).getProductsByCreditIds(creditNumberListNotFound);
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
                    MockMvcRequestBuilders.get(url));
            //then
            Mockito.verify(mockService).getProductsByCreditIds(creditNumberList);
        }
        @Test
        void shouldResponseListProductsWhenProductsIsFinds() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(url));
            //then
            result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
            result.andExpect(MockMvcResultMatchers.content().json(productsResponseJson));
        }
        @Test
        void shouldResponseOkStatusWhenProductsIsFinds() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(url));
            //then
            result.andExpect(MockMvcResultMatchers.status().isOk());
        }
        @Test
        void shouldResponseNotFoundStatusWhenProductNotFoundByIds() throws Exception {
            //when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get(urlNotFound));
            //then
            result.andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

}
