package banking.api.dto.response.exception;

import javax.ws.rs.core.Response;

public class NotSupported extends BankingException {
    public NotSupported(String message) throws IllegalArgumentException {
        super(message, Response.Status.NOT_IMPLEMENTED);
    }
}
