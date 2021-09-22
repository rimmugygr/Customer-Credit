package springboot.credit.repository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot.credit.dto.CustomerDto;
import springboot.credit.model.Credit;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:credit-repository-test.sql")
@DataJdbcTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)// reset auto_incrementation
class CreditRepositoryTest {
    @Autowired
    CreditInfoRepository creditInfoRepository;

    @Test
    void shouldFindAllCreditInfo() {
        //given
        Iterable<Credit> expectedProductList = entities()
                .map(creditInfoRepository::save)
                .peek(x -> x.setNewEntity(false))
                .collect(Collectors.toList());
        //when
        Iterable<Credit> actualCreditInfoList = creditInfoRepository.findAll();
        actualCreditInfoList.forEach(System.out::println);
        //then
        assertEquals(expectedProductList, actualCreditInfoList);
    }

    @Test
    void shouldSaveNewCreditInfo() {
        //given
        entities().forEach(creditInfoRepository::save);
        Credit creditInfo = Credit.builder()
                .id(5)
                .creditName("aaa")
                .newEntity(true)
                .build();
        //when
        Credit actualCreditInfo = creditInfoRepository.save(creditInfo);
        //then
        assertEquals(creditInfo, actualCreditInfo);
    }

    @Test
    void shouldUpdateExistingCreditInfo() {
        //given
        entities().forEach(creditInfoRepository::save);
        Credit creditInfo = Credit.builder()
                .id(4)
                .creditName("aaa")
                .newEntity(false)
                .build();
        //when
        Credit actualCreditInfo = creditInfoRepository.save(creditInfo);
        //then
        assertEquals(creditInfo, actualCreditInfo);
    }

    @Test
    void shouldThrowExceptionWhenUpdateNoneExistingCreditInfo() {
        //given
        entities().forEach(creditInfoRepository::save);
        Credit creditInfo = Credit.builder()
                .id(4)
                .creditName("aaa")
                .newEntity(true)
                .build();
        //when
        Exception exception = assertThrows(RuntimeException.class, () ->
                creditInfoRepository.save(creditInfo));
        //then
        MatcherAssert.assertThat(exception, Matchers.instanceOf(Exception.class));
    }

    @Test
    void shouldThrowExceptionWhenSaveExistingCreditInfo() {
        //given
        entities().forEach(creditInfoRepository::save);
        Credit creditInfo = Credit.builder()
                .id(5)
                .creditName("aaa")
                .newEntity(false)
                .build();
        //when
        Exception exception = assertThrows(RuntimeException.class, () ->
                creditInfoRepository.save(creditInfo));
        //then
        MatcherAssert.assertThat(exception, Matchers.instanceOf(Exception.class));
    }

    // populate date base witch ids: 1, 2, 3, 4
    private Stream<Credit> entities() {
        return Stream.of(
                Credit.builder().creditName("Jojo").newEntity(true).build(),
                Credit.builder().creditName("Jonatan").newEntity(true).build(),
                Credit.builder().creditName("Robert").newEntity(true).build(),
                Credit.builder().creditName("Jozef").newEntity(true).build()
        );
    }
}
