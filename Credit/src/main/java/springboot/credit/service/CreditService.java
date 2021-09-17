package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CreditInfoDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditService {
    private final CustomerService customerService;
    private final ProductService productService;
    private final CreditInfoService creditInfoService;
    private final NewCreditIdService creditIdService;


    public Integer createCredit(CreditDto creditDto){
        Integer newCreditId = creditIdService.getNewCreditId();

        creditDto.getProduct().setCreditId(newCreditId);
        productService.createProduct(creditDto.getProduct());

        creditDto.getCustomer().setCreditId(newCreditId);
        customerService.createCustomer(creditDto.getCustomer());

        CreditInfoDto creditInfo = creditDto.getCredit();
        creditInfo.setId(newCreditId);
        creditInfoService.saveCreditInfo(creditInfo);

        return newCreditId;
    }

    public List<CreditDto> getAllCredits() {
        List<CreditInfoDto> creditsInfoAll = creditInfoService.getCreditInfos();

        List<Integer> creditsNumberAll = creditsInfoAll.stream()
                .map(CreditInfoDto::getId)
                .collect(Collectors.toList());

        List<CustomerDto> customersAll = customerService.getCustomers(creditsNumberAll);
        List<ProductDto> productsAll = productService.getProducts(creditsNumberAll);

        return getCreditListAggregationByCreditNumber(creditsInfoAll, customersAll, productsAll);
    }


    private List<CreditDto> getCreditListAggregationByCreditNumber(List<CreditInfoDto> creditsInfoAll,
                                                                   List<CustomerDto> customersAll,
                                                                   List<ProductDto> productsAll) {
        List<CreditDto> credits = new ArrayList<>();

        for (CreditInfoDto creditInfoDto : creditsInfoAll) {
            CreditDto build = getCreditAggregationByCreditNumber(customersAll, productsAll, creditInfoDto);
            credits.add(build);
        }
        return credits;
    }

    private CreditDto getCreditAggregationByCreditNumber(List<CustomerDto> customersAll,
                                                         List<ProductDto> productsAll,
                                                         CreditInfoDto creditInfoDto) {
        Integer creditNumber = creditInfoDto.getId();

        CustomerDto customerForThisCredit = getCustomerFromListByCreditNumber(customersAll, creditNumber);

        ProductDto productForThisCredit = getProductFromListByCreditNumber(productsAll, creditNumber);

        return CreditDto.builder()
                .credit(creditInfoDto)
                .customer(customerForThisCredit)
                .product(productForThisCredit)
                .build();
    }

    // return first product from list by credit number and remove this product from list
    private ProductDto getProductFromListByCreditNumber(List<ProductDto> productsAll, Integer creditNumber) {
        ProductDto productForThisCredit = productsAll.stream()
                .filter(x -> x.getCreditId().equals(creditNumber))
                .findFirst()
                .get();

        productsAll.remove(productForThisCredit);

        return productForThisCredit;
    }

    // return first customer from list by credit number and remove this customer from list
    private CustomerDto getCustomerFromListByCreditNumber(List<CustomerDto> customersAll, Integer creditNumber) {
        CustomerDto customerForThisCredit = customersAll.stream()
                .filter(x -> x.getCreditId().equals(creditNumber))
                .findFirst()
                .get();

        customersAll.remove(customerForThisCredit);

        return customerForThisCredit;
    }


}
