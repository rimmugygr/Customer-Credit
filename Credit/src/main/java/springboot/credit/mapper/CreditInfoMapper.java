package springboot.credit.mapper;

import org.mapstruct.Mapper;
import springboot.credit.dto.CreditDto;
import springboot.credit.model.Credit;

@Mapper(componentModel = "spring")
public interface CreditMapper {
    Credit map(CreditDto creditDto);
    CreditDto map(Credit credit);
}
