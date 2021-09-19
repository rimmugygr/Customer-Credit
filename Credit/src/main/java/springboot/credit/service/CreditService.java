package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditDto;
import springboot.credit.mapper.CreditInfoMapper;
import springboot.credit.model.Credit;
import springboot.credit.repository.CreditInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditService {
    private final CreditInfoRepository creditInfoRepository;
    private final CreditInfoMapper creditInfoMapper;

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

}
