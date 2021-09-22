package springboot.credit.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springboot.credit.exceptions.ResourceIncorrectFormat;
import springboot.credit.exceptions.ResourceUnprocessable;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceUnprocessable.class})
    public ResponseEntity<Object> handleResourceUnprocessable(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {ResourceIncorrectFormat.class})
    public ResponseEntity<Object> handleResourceIncorrectFormat(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.BAD_REQUEST);
    }
}
