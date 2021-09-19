package springboot.credit.mapper;

import org.springframework.stereotype.Service;
import springboot.credit.controller.request.CreditAllInfoRequest;
import springboot.credit.controller.response.CreditAllInfoResponse;
import springboot.credit.dto.ProductDto;

@Service
public class ProductMapper {

    public ProductDto mapToDto(CreditAllInfoRequest.Product product) {
        return ProductDto.builder()
                .productName(product.getProductName())
                .value(product.getValue())
                .build();
    }

    public CreditAllInfoResponse.Product mapToResponse(ProductDto productDto) {
        return CreditAllInfoResponse.Product.builder()
                .productName(productDto.getProductName())
                .value(productDto.getValue())
                .build();
    }
}
