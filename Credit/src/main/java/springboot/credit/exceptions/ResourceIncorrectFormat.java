package springboot.credit.exceptions;

public class ResourceIncorrectFormat extends RuntimeException {
    public ResourceIncorrectFormat() {
    }

    public ResourceIncorrectFormat(String message) {
        super(message);
    }
}
