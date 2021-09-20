package springboot.credit.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import springboot.credit.client.response.ProductsResponse;
import springboot.credit.dto.ProductDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@RestClientTest(ProductClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@DataJdbcTest
@ActiveProfiles("test")
class ProductClientTest {
    private final String PRODUCT_URL = "http://product:8082/product/";

    @Autowired
    private ProductClient productClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        server.reset();
    }

    @Test
    void createProduct() throws Exception {
        //given
        String url = PRODUCT_URL;
        ProductDto productDto = ProductDto.builder()
                .creditId(4)
                .value(123)
                .productName("abc")
                .build();
        String customersDtoJson = objectMapper.writeValueAsString(productDto);

        server.expect(requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(customersDtoJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withStatus(HttpStatus.CREATED));
        //when
        productClient.createProduct(productDto);
        //then
        server.verify();
    }

    @Test
    void getProducts() throws Exception {
        //given
        List<Integer> creditIdList = List.of(4);
        String url = PRODUCT_URL + "?ids=4";

        ProductDto productDto = ProductDto.builder()
                .creditId(4)
                .value(123)
                .productName("abc")
                .build();
        ProductsResponse productsResponse = ProductsResponse.builder()
                .products(List.of(productDto))
                .build();
        List<ProductDto> productDtoList = productsResponse.getProducts();

        String productsResponseJson = objectMapper.writeValueAsString(productsResponse);

        server.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productsResponseJson));
        //when
        List<ProductDto> productsDtoActual = productClient.getProducts(creditIdList);
        //then
        assertEquals(productDtoList, productsDtoActual);
        server.verify();
    }
}
