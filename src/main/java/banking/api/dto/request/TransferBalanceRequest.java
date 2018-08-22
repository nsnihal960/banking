package banking.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import banking.api.dto.response.exception.ClientException;

public class TransferBalanceRequest implements Request {
    public final Long fromUserId;
    public final Long toUserId;
    public final Double amount;
    public final String currency;
    public final String token; // not used for now

    @JsonCreator
    public TransferBalanceRequest(@JsonProperty("fromUserId") Long fromUserId,
                                  @JsonProperty("toUserId") Long toUserId,
                                  @JsonProperty("amount") Double amount,
                                  @JsonProperty("currency") String currency,
                                  @JsonProperty("token") String token) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.currency = currency;
        this.token = token;
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (fromUserId == null || fromUserId <= 0) {
            errors.add("FromUser id cannot be blank");
        }

        if (toUserId == null || fromUserId <= 0) {
            errors.add("ToUser id cannot be blank");
        }

        if (amount == null || amount <= 0) {
            errors.add("Amount should be positive");
        }

        if(StringUtils.isBlank(token)){
            errors.add("Token can not be blank");
        }

        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}
