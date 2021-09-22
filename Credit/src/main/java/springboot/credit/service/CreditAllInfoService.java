package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditAllInfoDto;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;
import springboot.credit.exceptions.ResourceIncorrectFormat;
import springboot.credit.exceptions.ResourceUnprocessable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditAllInfoService {
    private final CustomerService customerService;
    private final ProductService productService;
    private final CreditService creditService;

    public Integer createCreditInfo(CreditAllInfoDto creditAllInfoDto){
        // validate data
        validationCreditInfo(creditAllInfoDto);

        // getting new id for credit
        Integer newCreditId = creditService.getNewCreditId();

        // save product
        creditAllInfoDto.getProduct().setCreditId(newCreditId);
        productService.createProduct(creditAllInfoDto.getProduct());

        // save customer
        creditAllInfoDto.getCustomer().setCreditId(newCreditId);
        customerService.createCustomer(creditAllInfoDto.getCustomer());

        // save credit
        CreditDto creditInfo = creditAllInfoDto.getCredit();
        creditInfo.setId(newCreditId);
        creditService.saveCreditInfo(creditInfo);

        return newCreditId;
    }

    public List<CreditAllInfoDto> getAllCreditsInfo() {
        List<CreditDto> creditsInfoAll = creditService.getCreditInfos();

        // get list of all credit ids
        List<Integer> creditsNumberAll = creditsInfoAll.stream()
                .map(CreditDto::getId)
                .collect(Collectors.toList());

        // get all products and customers by list of credit ids
        List<CustomerDto> customersAll = customerService.getCustomers(creditsNumberAll);
        List<ProductDto> productsAll = productService.getProducts(creditsNumberAll);

        return getCreditInfoListAggregationByCreditNumber(creditsInfoAll, customersAll, productsAll);
    }

    private void validationCreditInfo(CreditAllInfoDto creditAllInfoDto) {
        // validation credit
        if (creditAllInfoDto.getCredit() == null)
            throw new ResourceIncorrectFormat("in credit info is credit missing");
        validationOfCredit(creditAllInfoDto.getCredit());

        // validation product
        if (creditAllInfoDto.getProduct() == null)
            throw new ResourceIncorrectFormat("in credit info is product missing");
        validationOfProduct(creditAllInfoDto.getProduct());

        // validation customer
        if (creditAllInfoDto.getCustomer() == null)
            throw new ResourceIncorrectFormat("in credit info is customer missing");
        validationOfCustomer(creditAllInfoDto.getCustomer());
    }

    private List<CreditAllInfoDto> getCreditInfoListAggregationByCreditNumber(List<CreditDto> creditsInfoAll,
                                                                              List<CustomerDto> customersAll,
                                                                              List<ProductDto> productsAll) {
        List<CreditAllInfoDto> credits = new ArrayList<>();

        // aggregation CreditInfo{Costumer, Product, Credit} by Credit
        for (CreditDto creditDto : creditsInfoAll) {
            CreditAllInfoDto build = getCreditInfoAggregationByCreditNumber(customersAll, productsAll, creditDto);
            credits.add(build);
        }
        return credits;
    }

    private CreditAllInfoDto getCreditInfoAggregationByCreditNumber(List<CustomerDto> customersAll,
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

    private void validationOfProduct(ProductDto productDto) {
        if (productDto.getProductName() == null || productDto.getProductName().equals(""))
            throw new ResourceUnprocessable("missing correct product name in product entity");
        if (productDto.getValue() == null || productDto.getValue() < 0 )
            throw new ResourceUnprocessable("missing correct value in product entity");
    }

    private void validationOfCustomer(CustomerDto customerDto) {
        if (customerDto.getFirstName() == null || customerDto.getFirstName().equals(""))
            throw new ResourceUnprocessable("missing correct first name in customer entity");
        if (customerDto.getSurname() == null || customerDto.getSurname().equals(""))
            throw new ResourceUnprocessable("missing correct surname in customer entity");
        if (customerDto.getPesel() == null || customerDto.getPesel().equals(""))
            throw new ResourceUnprocessable("missing correct pesel in customer entity");
    }

    private void validationOfCredit(CreditDto creditDto) {
        if (creditDto.getCreditName() == null || creditDto.getCreditName().equals(""))
            throw new ResourceUnprocessable("missing correct credit name in credit entity");
    }

}
