package banking.api.dto.response.exception;

import javax.ws.rs.core.Response;

public class NotFound extends BankingException {
    public NotFound(String message) throws IllegalArgumentException {
        super(message, Response.Status.NOT_FOUND);
    }
}
