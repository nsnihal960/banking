package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Statement implements Response{
    public final Profile profile;
    public final Date generationData;
    public final Balance balance;
    public final List<Transaction> transactions;

    @JsonCreator
    public Statement(@JsonProperty("profile") Profile profile,
                     @JsonProperty("generationDate") Date generationDate,
                     @JsonProperty("balance") Balance balance,
                     @JsonProperty("transactions") List<Transaction> transactions) {
        this.profile = profile;
        this.generationData = generationDate;
        this.balance = balance;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "profile=" + profile +
                ", generationData=" + generationData +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}
