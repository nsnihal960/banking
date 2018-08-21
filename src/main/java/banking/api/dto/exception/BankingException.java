package banking.api.dto.exception;

public class BankingException extends RuntimeException {
    public BankingException() {
    }

    public BankingException(String message) {
        super(message);
    }

    public BankingException(String message, Throwable cause) {
        super(message, cause);
    }
}
