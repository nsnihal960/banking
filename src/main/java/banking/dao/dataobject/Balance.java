package banking.dao.dataobject;

import java.util.Currency;

public class Balance {
    private Double amount;
    private Currency currency;
    private Long createdOn;
    private Long updatedOn;

    public Balance(Double amount, Currency currency, Long createdOn, Long updatedOn) {
        this.amount = amount;
        this.currency = currency;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
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

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "amount=" + amount +
                ", currency=" + currency +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
