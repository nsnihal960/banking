package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Transaction implements Response {
    public final String id;
    public final Long from;
    public final Long to;
    public final Date date;
    public final String amount;//pretty printed for different locale
    public final Double conversionRate;
    public final TransactionType transactionType;
    public final Double balance;

    @JsonCreator
    public Transaction(@JsonProperty("id") String id,
                       @JsonProperty("from") Long from,
                       @JsonProperty("to") Long to,
                       @JsonProperty("date") Date date,
                       @JsonProperty("amount") String amount,
                       @JsonProperty("conversionRate") Double conversionRate,
                       @JsonProperty("transactionType") TransactionType transactionType,
                       @JsonProperty("balance") Double balance) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.amount = amount;
        this.conversionRate = conversionRate;
        this.transactionType = transactionType;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date +
                ", amount='" + amount + '\'' +
                ", conversionRate=" + conversionRate +
                ", transactionType=" + transactionType +
                ", totalBalance=" + balance +
                '}';
    }

    public enum TransactionType {
        DEBIT, CREDIT
    }
}
