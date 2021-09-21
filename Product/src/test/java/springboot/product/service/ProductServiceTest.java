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
import springboot.product.exceptions.ResourceNotFound;
import springboot.product.exceptions.ResourceUnprocessable;
import springboot.product.mapper.ProductMapper;
import springboot.product.model.Product;
import springboot.product.repository.ProductRepository;

import java.util.Collections;
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
        List<Integer> creditNumberList, creditNumberListNotFund;
        Product product1;
        Product product2;
        List<Product> productList, customerListNotFound ;

        @BeforeEach
        void setUp() throws Exception {
            //given

            // case when found customers
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

            // case not found customers
            creditNumberListNotFund = List.of(5, 6);
            customerListNotFound = Collections.emptyList();
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetListProductWhenProvideListOfId() {
            //when
            List<Product> products = productService.getProductsByCreditIds(creditNumberList);
            //then
            MatcherAssert.assertThat(products, Matchers.notNullValue());
            MatcherAssert.assertThat(products, Matchers.contains(product1, product2));
        }

        @Test
        void shouldInvokeRepositoryForProductsWhenProvideListOfId() {
            //when
            productService.getProductsByCreditIds(creditNumberList);
            //then
            Mockito.verify(mockRepository).findAllByCreditIdIn(creditNumberList);
        }

        @Test
        void shouldThrowExceptionWhenProvidedCreditIdsNotFund() {
            //when
            Exception exception = assertThrows(RuntimeException.class, () ->
                    productService.getProductsByCreditIds(creditNumberListNotFund));
            //then
            MatcherAssert.assertThat(exception, Matchers.instanceOf(ResourceNotFound.class));
        }
    }

    @DisplayName("createProduct")
    @Nested
    class CreateProduct {
        ProductDto productDto, productDtoInvalid;
        Product product, productInvalid;

        @BeforeEach
        void setUp() throws Exception {
            //given

            // case with validate data
            product = Product.builder()
                    .productName("aaa")
                    .value(312)
                    .creditId(1)
                    .build();
            productDto = ProductDto.builder()
                    .productName("aaa")
                    .value(312)
                    .creditId(1)
                    .build();
            Mockito.when(mockMapper.map(productDto))
                    .thenReturn(product);

            // case with invalidate data
            productDtoInvalid = ProductDto.builder()
                    .productName("")
                    .value(-10)
                    .build();
            productInvalid = Product.builder()
                    .productName("")
                    .value(-10)
                    .build();
            Mockito.when(mockMapper.map(productDtoInvalid))
                    .thenReturn(productInvalid);
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

        @Test
        void shouldThrowExceptionWhenProvidedInvalidProduct() {
            //when
            Exception exception = assertThrows(RuntimeException.class, () ->
                    productService.createProduct(productDtoInvalid));
            //then
            MatcherAssert.assertThat(exception, Matchers.instanceOf(ResourceUnprocessable.class));
        }
    }



}
