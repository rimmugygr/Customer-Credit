package springboot.credit.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NewCreditIdService {
    public int getNewCreditId() {
        return Math.toIntExact(UUID.randomUUID().getLeastSignificantBits());
    }
}
