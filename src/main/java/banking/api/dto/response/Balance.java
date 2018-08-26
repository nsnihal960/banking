package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Balance implements Response{
    public final Double balance;
    public final Double conversionRate;
    public final String userCurrency;

    @JsonCreator
    public Balance(@JsonProperty("balance") Double balance,
                   @JsonProperty("conversionRate") Double conversionRate,
                   @JsonProperty("userCurrency") String userCurrency) {
        this.balance = balance;
        this.conversionRate = conversionRate;
        this.userCurrency = userCurrency;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "balance='" + balance + '\'' +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
