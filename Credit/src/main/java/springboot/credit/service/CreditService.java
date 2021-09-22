package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.ProductDto;
import springboot.credit.exceptions.ResourceUnprocessable;
import springboot.credit.mapper.CreditInfoMapper;
import springboot.credit.model.Credit;
import springboot.credit.repository.CreditInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditService {
    private final CreditInfoRepository creditInfoRepository;
    private final CreditInfoMapper creditInfoMapper;
    private final Random rand;

    public List<CreditDto> getCreditInfos() {
        List<Credit> creditInfoList = new ArrayList<>();
        creditInfoRepository.findAll()
                .iterator()
                .forEachRemaining(creditInfoList::add);
        return creditInfoList.stream()
                .map(creditInfoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void saveCreditInfo(CreditDto creditDto) {
        Credit credit = creditInfoMapper.map(creditDto);
        credit.setNew(true);
        creditInfoRepository.save(credit);
    }

    // get pseudo random number which not use in credit table as id
    public Integer getNewCreditId() {
        Integer pseudoRandomNumber;

        do {
            pseudoRandomNumber = rand.nextInt(Integer.MAX_VALUE);
        } while (creditInfoRepository.existsById(pseudoRandomNumber));

        return pseudoRandomNumber;
    }

}
