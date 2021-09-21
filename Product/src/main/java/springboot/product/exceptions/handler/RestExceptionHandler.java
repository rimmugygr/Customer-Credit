package springboot.product.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springboot.product.exceptions.ResourceNotFound;
import springboot.product.exceptions.ResourceUnprocessable;


@ControllerAdvice
public class RestExceptionHandler{
    @ExceptionHandler(value = {ResourceNotFound.class})
    public ResponseEntity<Object> handleResourceNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResourceUnprocessable.class})
    public ResponseEntity<Object> handleResourceNotProvide(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
