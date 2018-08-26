package banking.api.dto.response.exception;

import javax.ws.rs.core.Response;

public class ClientException extends BankingException {
    public ClientException(String message) throws IllegalArgumentException {
        super(message, Response.Status.PRECONDITION_FAILED);
    }
}
