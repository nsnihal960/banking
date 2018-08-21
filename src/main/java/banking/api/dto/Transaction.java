package banking.api.dto;

import java.util.Currency;
import java.util.Date;

public class Transaction {
    public final Long id;
    public final Long from;
    public final Long to;
    public final Date date;
    public final String amount;//pretty printed for different locale
    public final Currency currency;

    public Transaction(Long id, Long from, Long to, Date date, String amount, Currency currency) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", date=" + date +
                ", amount='" + amount + '\'' +
                ", currency=" + currency +
                '}';
    }
}
