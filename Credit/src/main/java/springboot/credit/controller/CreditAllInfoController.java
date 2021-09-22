package springboot.credit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/credit/", produces = "application/json")
@Slf4j
public class CreditAllInfoController {
    private final CreditAllInfoService creditAllInfoService;
    private final CreditMapper creditMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditNumberResponse createCredit(@RequestBody CreditAllInfoRequest credit) {
        log.info("POST on /credit/ {}" , credit.toString());
        Integer newCreditId = creditAllInfoService.createCreditInfo(creditMapper.map(credit));
        return CreditNumberResponse.builder()
                .creditId(newCreditId)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CreditAllInfoListResponse getCredits() {
        log.info("GET on /credit/ ");
        List<CreditAllInfoResponse> creditAllInfoResponseList = creditAllInfoService.getAllCreditsInfo().stream()
                .map(creditMapper::map)
                .collect(Collectors.toList());

        return CreditAllInfoListResponse.builder()
                .credits(creditAllInfoResponseList)
                .build();
    }
}
