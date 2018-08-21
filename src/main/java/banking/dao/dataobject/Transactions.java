package banking.dao.dataobject;

import java.util.Currency;

public class Transactions {
    private Long from;
    private Long to;
    private Long time;
    private Double amount;
    private Currency currency;

    public Transactions(Long from, Long to, Long time, Double amount, Currency currency) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "from=" + from +
                ", to=" + to +
                ", time=" + time +
                ", amount=" + amount +
                ", currency=" + currency +
                '}';
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
