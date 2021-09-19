package springboot.credit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.credit.model.Credit;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:credit-repository-test.sql")
@DataJdbcTest
@ActiveProfiles("test")
class CreditRepositoryTest {
    @Autowired
    CreditInfoRepository creditInfoRepository;

    @Test
    void shouldSaveNewCreditInfo() {
        //given
        entities().forEach(creditInfoRepository::save);
        Credit creditInfo = Credit.builder()
                .id(5)
                .creditName("aaa")
                .build();
        //when
        Credit actualCreditInfo = creditInfoRepository.save(creditInfo);
        //then
        assertEquals(creditInfo, actualCreditInfo);
    }
    @Test
    void shouldFindAllCreditInfo() {
        //given
        Iterable<Credit> expectedProductList = entities()
                .map(creditInfoRepository::save)
                .collect(Collectors.toList());
        //when
        Iterable<Credit> actualCreditInfoList = creditInfoRepository.findAll();
        //then
        assertEquals(expectedProductList, actualCreditInfoList);
    }



    private Stream<Credit> entities() {
        return Stream.of(
                Credit.builder().creditName("Jojo").build(),
                Credit.builder().creditName("Jonatan").build(),
                Credit.builder().creditName("Robert").build(),
                Credit.builder().creditName("Jozef").build()
        );
    }
}
