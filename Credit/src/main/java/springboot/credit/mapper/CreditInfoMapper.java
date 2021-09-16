package springboot.credit.mapper;

import org.mapstruct.Mapper;
import springboot.credit.dto.CreditInfoDto;
import springboot.credit.model.CreditInfo;

@Mapper(componentModel = "spring")
public interface CreditInfoMapper {
    CreditInfo map(CreditInfoDto creditInfoDto);
    CreditInfoDto map(CreditInfo creditInfo);
}
