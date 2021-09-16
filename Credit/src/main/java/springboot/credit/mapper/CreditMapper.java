package springboot.credit.mapper;

import org.mapstruct.Mapper;
import springboot.credit.controller.request.CreditRequest;
import springboot.credit.controller.response.CreditResponse;
import springboot.credit.dto.CreditDto;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    CreditDto map(CreditRequest creditRequest);
    CreditResponse map(CreditDto creditDto);
}
