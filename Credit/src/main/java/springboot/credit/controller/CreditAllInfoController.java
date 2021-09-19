package springboot.credit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.credit.controller.request.CreditAllInfoRequest;
import springboot.credit.controller.response.CreditNumberResponse;
import springboot.credit.controller.response.CreditAllInfoResponse;
import springboot.credit.controller.response.CreditAllInfoListResponse;
import springboot.credit.mapper.CreditMapper;
import springboot.credit.service.CreditAllInfoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/", produces = "application/json")
public class CreditAllInfoController {
    private final CreditAllInfoService creditAllInfoService;
    private final CreditMapper creditMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditNumberResponse createCredit(@RequestBody CreditAllInfoRequest credit) {
        System.out.println(credit);
        Integer neCreditId = creditAllInfoService.createCredit(creditMapper.map(credit));
        return CreditNumberResponse.builder()
                .creditId(neCreditId)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CreditAllInfoListResponse getCredits() {
        List<CreditAllInfoResponse> creditAllInfoResponseList = creditAllInfoService.getAllCredits().stream()
                .map(creditMapper::map)
                .collect(Collectors.toList());

        return CreditAllInfoListResponse.builder()
                .credits(creditAllInfoResponseList)
                .build();
    }
}
