package springboot.product.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.product.dto.ProductDto;
import springboot.product.mapper.ProductMapper;
import springboot.product.model.Product;
import springboot.product.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(ProductService.class)
@ExtendWith(SpringExtension.class)
@DataJdbcTest
@ActiveProfiles("test")
class ProductServiceTest {
    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository mockRepository;

    @MockBean
    ProductMapper mockMapper;

    @DisplayName("getProductsByCreditIds")
    @Nested
    class GetProduct {
        List<Integer> creditNumberList;
        Product product1;
        Product product2;
        List<Product> productList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditNumberList = List.of(1,2);
            product1 = Product.builder()
                    .id(2)
                    .productName("aaa")
                    .value(312)
                    .creditId(1)
                    .build();
            product2 = Product.builder()
                    .id(3)
                    .productName("aaa")
                    .value(312)
                    .creditId(2)
                    .build();
            productList = List.of(product1, product2);
            Mockito.when(mockRepository.findAllByCreditIdIn(creditNumberList))
                    .thenReturn(productList);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetListProductWhenProvideListOfId() {
            //when
            List<Product> products = productService.getProducts(creditNumberList);
            //then
            MatcherAssert.assertThat(products, Matchers.notNullValue());
            MatcherAssert.assertThat(products, Matchers.contains(product1, product2));
        }

        @Test
        void shouldInvokeRepositoryForProductsWhenProvideListOfId() {
            //when
            productService.getProducts(creditNumberList);
            //then
            Mockito.verify(mockRepository).findAllByCreditIdIn(creditNumberList);
        }
    }

    @DisplayName("createProduct")
    @Nested
    class CreateProduct {
        ProductDto productDto;
        Product product;

        @BeforeEach
        void setUp() throws Exception {
            //given
            product = Product.builder()
                    .id(2)
                    .productName("aaa")
                    .value(312)
                    .creditId(1)
                    .build();
            productDto = ProductDto.builder()
                    .id(2)
                    .productName("aaa")
                    .value(312)
                    .creditId(1)
                    .build();
            Mockito.when(mockMapper.map(productDto))
                    .thenReturn(product);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldCreateProductWhenProvideProduct() {
            //when
            productService.createProduct(productDto);
            //then
            Mockito.verify(mockRepository).save(product);
        }
    }



}
