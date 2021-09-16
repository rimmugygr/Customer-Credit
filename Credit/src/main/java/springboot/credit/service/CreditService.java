package springboot.credit.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.credit.dto.CreditDto;
import springboot.credit.dto.CustomerDto;
import springboot.credit.dto.ProductDto;
import springboot.credit.mapper.CreditInfoMapper;
import springboot.credit.model.CreditInfo;
import springboot.credit.repository.CreditInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreditService {
    private final CreditInfoRepository creditInfoRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final CreditInfoMapper creditInfoMapper;

    public Integer createCredit(CreditDto creditDto){
        Integer newCreditId = Math.toIntExact(UUID.randomUUID().getLeastSignificantBits());

        productService.createProduct(creditDto.getProduct());
        customerService.createCustomer(creditDto.getCustomer());

        CreditInfo newCreditInfoWithId = CreditInfo.builder()
                .id(newCreditId)
                .creditName(creditDto.getCredit().getCreditName())
                .build();
        saveCreditInfo(newCreditInfoWithId);

        return newCreditId;
    }

    public List<CreditDto> getAllCredits() {
        List<CreditInfo> creditsInfoAll = getAllCreditInfos();
        List<Integer> creditsNumberAll = creditsInfoAll.stream().map(CreditInfo::getId).collect(Collectors.toList());

        List<CustomerDto> customersAll = customerService.getCustomers(creditsNumberAll);
        List<ProductDto> productsAll = productService.getProducts(creditsNumberAll);

        return aggregationInformationOfCredit(creditsInfoAll, customersAll, productsAll);
    }

    public List<CreditInfo> getAllCreditInfos() {
        List<CreditInfo> creditInfoList = new ArrayList<>();
        creditInfoRepository.findAll()
                .iterator()
                .forEachRemaining(creditInfoList::add);
        return creditInfoList;
    }

    public void saveCreditInfo(CreditInfo creditInfo) {
        creditInfoRepository.save(creditInfo);
    }

    private List<CreditDto> aggregationInformationOfCredit(List<CreditInfo> creditsInfoAll,
                                                           List<CustomerDto> customersAll,
                                                           List<ProductDto> productsAll) {
        List<CreditDto> credits = new ArrayList<>();

        for (CreditInfo creditInfo : creditsInfoAll) {
            CustomerDto customerForThisCredit = customersAll.stream()
                    .filter(x -> x.getCreditId().equals(creditInfo.getId()))
                    .findFirst()
                    .get();
            customersAll.remove(customerForThisCredit);

            ProductDto productForThisCredit = productsAll.stream()
                    .filter(x -> x.getCreditId().equals(creditInfo.getId()))
                    .findFirst()
                    .get();
            productsAll.remove(productForThisCredit);

            CreditDto build = CreditDto.builder()
                    .credit(creditInfoMapper.map(creditInfo))
                    .customer(customerForThisCredit)
                    .product(productForThisCredit)
                    .build();
            credits.add(build);
        }
        return credits;
    }
}
