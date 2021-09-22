package springboot.credit.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
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

    @ApiOperation(value = "Add new credit", httpMethod = "POST")
    @ApiResponse(code = 201, message = "Add credit succeeded")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditNumberResponse createCredit(@RequestBody CreditAllInfoRequest credit) {
        log.info("POST on /credit/ {}" , credit.toString());
        Integer newCreditId = creditAllInfoService.createCreditInfo(creditMapper.map(credit));
        return CreditNumberResponse.builder()
                .creditId(newCreditId)
                .build();
    }

    @ApiOperation(value = "Returns all credits", httpMethod = "GET")
    @ApiResponse(code = 200, message = "Get all credits succeeded")
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
