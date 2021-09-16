package springboot.credit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.credit.controller.request.CreditRequest;
import springboot.credit.controller.response.CreditNumberResponse;
import springboot.credit.controller.response.CreditResponse;
import springboot.credit.controller.response.CreditsResponse;
import springboot.credit.mapper.CreditMapper;
import springboot.credit.service.CreditService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/", produces = "application/json")
public class CreditController {
    private final CreditService creditService;
    private final CreditMapper creditMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditNumberResponse createCredit(@RequestBody CreditRequest credit) {
        return CreditNumberResponse.builder()
                .creditId(creditService.createCredit(creditMapper.map(credit)))
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CreditsResponse getCredits() {
        List<CreditResponse> creditResponseList = creditService.getAllCredits().stream()
                .map(creditMapper::map)
                .collect(Collectors.toList());

        return CreditsResponse.builder()
                .credits(creditResponseList)
                .build();
    }
}
