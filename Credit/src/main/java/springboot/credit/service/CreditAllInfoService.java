package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditAllInfoDto;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditAllInfoService {
    private final CustomerService customerService;
    private final ProductService productService;
    private final CreditService creditService;
    private final NewCreditIdService creditIdService;


    public Integer createCredit(CreditAllInfoDto creditAllInfoDto){
        System.out.println(creditAllInfoDto);
        Integer newCreditId = creditIdService.getNewCreditId();

        creditAllInfoDto.getProduct().setCreditId(newCreditId);
        productService.createProduct(creditAllInfoDto.getProduct());

        creditAllInfoDto.getCustomer().setCreditId(newCreditId);
        customerService.createCustomer(creditAllInfoDto.getCustomer());

        CreditDto creditInfo = creditAllInfoDto.getCredit();
        creditInfo.setId(newCreditId);
        creditService.saveCreditInfo(creditInfo);

        return newCreditId;
    }

    public List<CreditAllInfoDto> getAllCredits() {
        List<CreditDto> creditsInfoAll = creditService.getCreditInfos();

        List<Integer> creditsNumberAll = creditsInfoAll.stream()
                .map(CreditDto::getId)
                .collect(Collectors.toList());

        List<CustomerDto> customersAll = customerService.getCustomers(creditsNumberAll);
        List<ProductDto> productsAll = productService.getProducts(creditsNumberAll);

        return getCreditListAggregationByCreditNumber(creditsInfoAll, customersAll, productsAll);
    }


    private List<CreditAllInfoDto> getCreditListAggregationByCreditNumber(List<CreditDto> creditsInfoAll,
                                                                          List<CustomerDto> customersAll,
                                                                          List<ProductDto> productsAll) {
        List<CreditAllInfoDto> credits = new ArrayList<>();

        for (CreditDto creditDto : creditsInfoAll) {
            CreditAllInfoDto build = getCreditAggregationByCreditNumber(customersAll, productsAll, creditDto);
            credits.add(build);
        }
        return credits;
    }

    private CreditAllInfoDto getCreditAggregationByCreditNumber(List<CustomerDto> customersAll,
                                                                List<ProductDto> productsAll,
                                                                CreditDto creditDto) {
        Integer creditNumber = creditDto.getId();

        CustomerDto customerForThisCredit = getCustomerFromListByCreditNumber(customersAll, creditNumber);

        ProductDto productForThisCredit = getProductFromListByCreditNumber(productsAll, creditNumber);

        return CreditAllInfoDto.builder()
                .credit(creditDto)
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
