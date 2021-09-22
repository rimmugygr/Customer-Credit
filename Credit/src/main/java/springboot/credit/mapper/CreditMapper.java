package springboot.credit.mapper;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.controller.request.CreditAllInfoRequest;
import springboot.credit.controller.response.CreditAllInfoResponse;
import springboot.credit.dto.CreditAllInfoDto;

@Service
@AllArgsConstructor
public class CreditMapper {
    private final CreditInfoMapper creditInfoMapper;
    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;

    public CreditAllInfoDto map(CreditAllInfoRequest creditAllInfoRequest) {
        if(creditAllInfoRequest == null) return null;
        return CreditAllInfoDto.builder()
                .product(productMapper.mapToDto(creditAllInfoRequest.getProduct()))
                .credit(creditInfoMapper.mapToDto(creditAllInfoRequest.getCredit()))
                .customer(customerMapper.mapToDto(creditAllInfoRequest.getCustomer()))
                .build();
    }
    public CreditAllInfoResponse map(CreditAllInfoDto creditAllInfoDto) {
        if(creditAllInfoDto == null) return null;
        return CreditAllInfoResponse.builder()
                .credit(creditInfoMapper.mapToResponse(creditAllInfoDto.getCredit()))
                .product(productMapper.mapToResponse(creditAllInfoDto.getProduct()))
                .customer(customerMapper.mapToResponse(creditAllInfoDto.getCustomer()))
                .build();
    }
}
