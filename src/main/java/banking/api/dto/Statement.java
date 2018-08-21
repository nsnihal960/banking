package banking.api.dto;

import java.util.Date;
import java.util.List;

public class Statement {
    public final Profile profile;
    public final Date generationData;
    public final List<Transaction> transactions;

    public Statement(Profile profile, Date generationData, List<Transaction> transactions) {
        this.profile = profile;
        this.generationData = generationData;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "profile=" + profile +
                ", generationData=" + generationData +
                ", transactions=" + transactions +
                '}';
    }
}
