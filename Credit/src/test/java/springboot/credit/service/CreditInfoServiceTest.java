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
import springboot.credit.dto.CreditInfoDto;
import springboot.credit.mapper.CreditInfoMapper;
import springboot.credit.model.CreditInfo;
import springboot.credit.repository.CreditInfoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Import(CreditInfoService.class)
@ExtendWith(SpringExtension.class)
class CreditInfoServiceTest {
    @Autowired
    CreditInfoService creditInfoService;

    @MockBean
    CreditInfoMapper mockMapper;

    @MockBean
    CreditInfoRepository mockRepository;

    @DisplayName("getCreditInfos")
    @Nested
    class GetCreditInfos {
        CreditInfo creditInfo;
        CreditInfoDto creditInfoDto;
        List<CreditInfo> creditInfoList;
        List<CreditInfoDto> creditInfoDtoList;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditInfo = CreditInfo.builder()
                    .id(2)
                    .creditName("aaa")
                    .build();
            creditInfoDto = CreditInfoDto.builder()
                    .id(2)
                    .creditName("aaa")
                    .build();
            creditInfoList = List.of(creditInfo);
            creditInfoDtoList = List.of(creditInfoDto);
            Mockito.when(mockRepository.findAll())
                    .thenReturn(creditInfoList);
            Mockito.when(mockMapper.map(creditInfo))
                    .thenReturn(creditInfoDto);
        }

        @AfterEach
        void tearDown() {
            Mockito.reset(mockRepository);
            Mockito.reset(mockMapper);
        }

        @Test
        void shouldGetCreditInfoList() {
            //when
            List<CreditInfoDto> creditInfoDtoListActual = creditInfoService.getCreditInfos();
            //then
            MatcherAssert.assertThat(creditInfoDtoListActual, Matchers.notNullValue());
            MatcherAssert.assertThat(creditInfoDtoListActual, Matchers.contains(creditInfoDto));
        }

        @Test
        void shouldInvokeRepositoryForCreditInfo() {
            //when
            creditInfoService.getCreditInfos();
            //then
            Mockito.verify(mockRepository).findAll();
        }
    }


    @DisplayName("saveCreditInfo")
    @Nested
    class SaveCreditInfo {
        CreditInfo creditInfo;
        CreditInfoDto creditInfoDto;

        @BeforeEach
        void setUp() throws Exception {
            //given
            creditInfo = CreditInfo.builder()
                    .creditName("aaa")
                    .build();
            creditInfoDto = CreditInfoDto.builder()
                    .creditName("aaa")
                    .build();

            Mockito.when(mockMapper.map(creditInfoDto))
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
            creditInfoService.saveCreditInfo(creditInfoDto);
            //then
            Mockito.verify(mockRepository).save(creditInfo);
        }
    }
}
