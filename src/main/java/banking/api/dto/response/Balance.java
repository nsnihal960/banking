package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Balance implements Response{
    public final String balance;
    public final Double conversionRate;

    @JsonCreator
    public Balance(@JsonProperty("balance") String balance,
                   @JsonProperty("conversionRate") Double conversionRate) {
        this.balance = balance;
        this.conversionRate = conversionRate;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "balance='" + balance + '\'' +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
