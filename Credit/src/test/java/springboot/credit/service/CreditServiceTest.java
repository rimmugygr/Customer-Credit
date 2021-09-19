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
import springboot.credit.dto.CreditDto;
import springboot.credit.mapper.CreditInfoMapper;
import springboot.credit.model.Credit;
import springboot.credit.repository.CreditInfoRepository;

import java.util.List;

@Import(CreditService.class)
@ExtendWith(SpringExtension.class)
class CreditServiceTest {
    @Autowired
    CreditService creditService;

    @MockBean
    CreditInfoMapper mockMapper;

    @MockBean
    CreditInfoRepository mockRepository;

    @DisplayName("getCreditInfos")
    @Nested
    class GetCreditInfos {
        Credit creditInfo;
        CreditDto creditDto;
        List<Credit> creditInfoList;
        List<CreditDto> creditDtoList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditInfo = Credit.builder()
                    .id(2)
                    .creditName("aaa")
                    .build();
            creditDto = CreditDto.builder()
                    .id(2)
                    .creditName("aaa")
                    .build();
            creditInfoList = List.of(creditInfo);
            creditDtoList = List.of(creditDto);
            Mockito.when(mockRepository.findAll())
                    .thenReturn(creditInfoList);
            Mockito.when(mockMapper.mapToDto(creditInfo))
                    .thenReturn(creditDto);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetCreditInfoList() {
            //when
            List<CreditDto> creditDtoListActual = creditService.getCreditInfos();
            //then
            MatcherAssert.assertThat(creditDtoListActual, Matchers.notNullValue());
            MatcherAssert.assertThat(creditDtoListActual, Matchers.contains(creditDto));
        }

        @Test
        void shouldInvokeRepositoryForCreditInfo() {
            //when
            creditService.getCreditInfos();
            //then
            Mockito.verify(mockRepository).findAll();
        }
    }


    @DisplayName("saveCreditInfo")
    @Nested
    class SaveCredit {
        Credit creditInfo;
        CreditDto creditDto;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditInfo = Credit.builder()
                    .creditName("aaa")
                    .build();
            creditDto = CreditDto.builder()
                    .creditName("aaa")
                    .build();

            Mockito.when(mockMapper.map(creditDto))
                    .thenReturn(creditInfo);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldSaveInRepository() {
            //when
            creditService.saveCreditInfo(creditDto);
            //then
            Mockito.verify(mockRepository).save(creditInfo);
        }
    }
}
