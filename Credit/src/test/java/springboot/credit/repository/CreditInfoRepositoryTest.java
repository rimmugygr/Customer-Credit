package springboot.credit.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.credit.model.CreditInfo;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:credit-repository-test.sql")
@DataJdbcTest
@ActiveProfiles("test")
class CreditInfoRepositoryTest {
    @Autowired
    CreditInfoRepository creditInfoRepository;

    @Test
    void shouldSaveNewCreditInfo() {
        //given
        entities().forEach(creditInfoRepository::save);
        CreditInfo creditInfo = CreditInfo.builder()
                .id(5)
                .creditName("aaa")
                .build();
        //when
        CreditInfo actualCreditInfo = creditInfoRepository.save(creditInfo);
        //then
        assertEquals(creditInfo, actualCreditInfo);
    }
    @Test
    void shouldFindAllCreditInfo() {
        //given
        Iterable<CreditInfo> expectedProductList = entities()
                .map(creditInfoRepository::save)
                .collect(Collectors.toList());
        //when
        Iterable<CreditInfo> actualCreditInfoList = creditInfoRepository.findAll();
        //then
        assertEquals(expectedProductList, actualCreditInfoList);
    }



    private Stream<CreditInfo> entities() {
        return Stream.of(
                new CreditInfo(null, "Jojo"),
                new CreditInfo(null, "Jonatan"),
                new CreditInfo(null, "Robert"),
                new CreditInfo(null, "Jozef")
        );
    }
}
