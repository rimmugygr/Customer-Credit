package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditInfoDto;
import springboot.credit.mapper.CreditInfoMapper;
import springboot.credit.model.CreditInfo;
import springboot.credit.repository.CreditInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditInfoService {
    private final CreditInfoRepository creditInfoRepository;
    private final CreditInfoMapper creditInfoMapper;

    public List<CreditInfoDto> getCreditInfos() {
        List<CreditInfo> creditInfoList = new ArrayList<>();
        creditInfoRepository.findAll()
                .iterator()
                .forEachRemaining(creditInfoList::add);
        return creditInfoList.stream()
                .map(creditInfoMapper::map)
                .collect(Collectors.toList());
    }

    public void saveCreditInfo(CreditInfoDto creditInfoDto) {
        creditInfoRepository.save(creditInfoMapper.map(creditInfoDto));
    }

}
