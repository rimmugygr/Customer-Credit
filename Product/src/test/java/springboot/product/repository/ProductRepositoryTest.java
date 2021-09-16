package springboot.product.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:product-repository-test.sql")
@DataJdbcTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void shouldSaveNewProductWithIncrementedId() {
        //given
        Product product = Product.builder()
                .creditId(4)
                .value(11)
                .productName("aaa")
                .build();
        //when
        Product actualProduct = productRepository.save(product);
        //then
        assertEquals(5, actualProduct.getId());
    }
    @Test
    void shouldFindAllProductsByCreditIdIn() {
        //given
        List<Integer> creditNumberList = List.of(1,2);
        List<Product> expectedProductList = entities()
                .map(productRepository::save)
                .filter(x -> creditNumberList.contains(x.getCreditId()))
                .collect(Collectors.toList());
        //when
        List<Product> actualProductList = productRepository.findAllByCreditIdIn(creditNumberList);
        //then
        assertEquals(expectedProductList, actualProductList);
    }



    private Stream<Product> entities() {
        return Stream.of(
                new Product(null, "Jojo", 100, 1),
                new Product(null, "Jonatan", 200, 2),
                new Product(null, "Robert", 300, 3),
                new Product(null, "Jozef", 400, 4)
        );
    }
}
