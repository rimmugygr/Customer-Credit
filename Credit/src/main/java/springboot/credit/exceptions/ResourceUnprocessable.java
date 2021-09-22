package springboot.credit.exceptions;

public class ResourceUnprocessable extends RuntimeException {
    public ResourceUnprocessable() {
    }

    public ResourceUnprocessable(String message) {
        super(message);
    }
}
