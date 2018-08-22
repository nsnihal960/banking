package banking.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import banking.api.dto.response.exception.ClientException;

public class TransferBalanceRequest implements Request {
    public final String fromUserId;
    public final String toUserId;
    public final Double amount;
    public final String currency;

    @JsonCreator
    public TransferBalanceRequest(@JsonProperty("fromUserId") String fromUserId,
                                  @JsonProperty("toUserId") String toUserId,
                                  @JsonProperty("amount") Double amount,
                                  @JsonProperty("currency") String currency) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.currency = currency;
        validate();
    }

    @Override
    public void validate() {
        if(StringUtils.isBlank(fromUserId)){
            throw new ClientException("FromUser id cannot be blank");
        }

        if(StringUtils.isBlank(toUserId)){
            throw new ClientException("ToUser id cannot be blank");
        }

        if(amount == null || amount <= 0){
            throw new ClientException("Amount should be positive");
        }
    }
}
