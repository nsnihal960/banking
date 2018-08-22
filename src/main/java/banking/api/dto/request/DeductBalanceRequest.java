package banking.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import banking.api.dto.response.exception.ClientException;

public class DeductBalanceRequest implements Request {
    public final String userId;
    public final Double amount;
    public final String currency;

    @JsonCreator
    public DeductBalanceRequest(@JsonProperty("userId") String userId,
                                @JsonProperty("amount") Double amount,
                                @JsonProperty("currency") String currency) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        validate();
    }

    @Override
    public void validate() {
        if(StringUtils.isBlank(userId)){
            throw new ClientException("User id cannot be blank");
        }
        if(amount == null || amount <= 0){
            throw new ClientException("Amount should be positive");
        }
    }

    @Override
    public String toString() {
        return "DeductBalanceRequest{" +
                "fromUserId='" + userId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
