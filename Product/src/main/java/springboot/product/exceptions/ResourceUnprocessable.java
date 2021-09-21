package springboot.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceUnprocessable extends RuntimeException {
    public ResourceUnprocessable() {
    }

    public ResourceUnprocessable(String message) {
        super(message);
    }
}
