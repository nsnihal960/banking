package banking.api.dto.request;

import banking.api.dto.response.exception.ClientException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddBalanceRequest implements Request {
    public final Long userId;
    public final Double amount;
    public final String currency;
    public final String token; // Not used for now

    @JsonCreator
    public AddBalanceRequest(@JsonProperty("userId") Long userId,
                             @JsonProperty("amount") Double amount,
                             @JsonProperty("currency") String currency,
                             @JsonProperty("token") String token) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.token = token;
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if(userId == null || userId <= 0){
            errors.add("User id is not valid");
        }
        if(amount == null || amount <= 0){
            errors.add("Amount should be positive");
        }
        if(StringUtils.isBlank(token)){
            errors.add("Token can not be blank");
        }
        if(errors.size() > 0){
            throw new ClientException(errors.toString());
        }
    }

    @Override
    public String toString() {
        return "AddBalanceRequest{" +
                "userId=" + userId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
