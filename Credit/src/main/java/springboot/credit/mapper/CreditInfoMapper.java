package springboot.credit.mapper;

import org.springframework.stereotype.Service;
import springboot.credit.controller.request.CreditAllInfoRequest;
import springboot.credit.controller.response.CreditAllInfoResponse;
import springboot.credit.dto.CreditDto;
import springboot.credit.model.Credit;

@Service
public class CreditInfoMapper {

    public Credit map(CreditDto creditDto) {
        return Credit.builder()
                .id(creditDto.getId())
                .creditName(creditDto.getCreditName())
                .build();
    }
    public CreditDto mapToDto(Credit creditInfo) {
        return CreditDto.builder()
                .id(creditInfo.getId())
                .creditName(creditInfo.getCreditName())
                .build();
    }
    public CreditDto mapToDto(CreditAllInfoRequest.CreditInfo creditInfo) {
        return CreditDto.builder()
                .creditName(creditInfo.getCreditName())
                .build();
    }

    public CreditAllInfoResponse.CreditInfo mapToResponse(CreditDto creditDto) {
        return CreditAllInfoResponse.CreditInfo.builder()
                .creditName(creditDto.getCreditName())
                .build();
    }
}
