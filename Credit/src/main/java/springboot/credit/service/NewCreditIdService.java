package springboot.credit.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class NewCreditIdService {
    Random rand = new Random();
    public int getNewCreditId() {
        return rand.nextInt(Integer.MAX_VALUE);
    }
}
