package springboot.credit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;
import springboot.credit.controller.response.CreditInfoResponse;

@Mapper(componentModel = "spring")
public interface CreditInfoResponseMapper {
    @Mapping(source = "creditDto.creditName", target = "credit.creditName")
    @Mapping(source = "productDto.productName", target = "product.productName")
    @Mapping(source = "productDto.value", target = "product.value")
    @Mapping(source = "customerDto.firstName", target = "customer.firstName")
    @Mapping(source = "customerDto.surname", target = "customer.surname")
    @Mapping(source = "customerDto.pesel", target = "customer.pesel")
    CreditInfoResponse map(CreditDto creditDto, CustomerDto customerDto, ProductDto productDto);

    CreditDto map(CreditInfoResponse.Credit credit);
    CustomerDto map(CreditInfoResponse.Customer customer);
    ProductDto map(CreditInfoResponse.Product product);
}
