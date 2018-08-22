package banking.dao.dataobject;

import banking.api.dto.response.Transaction.TransactionType;

public class TransactionDO {
    private String id;
    private String from;
    private String to;
    private Long time;
    private Double amount;
    private String currency;
    private Double conversionRate;
    private TransactionType transactionType;
    private Double totalBalance;

    public TransactionDO(String id, String from, String to, Long time, Double amount, String currency, Double conversionRate, TransactionType transactionType, Double totalBalance) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.time = time;
        this.amount = amount;
        this.currency = currency;
        this.conversionRate = conversionRate;
        this.transactionType = transactionType;
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "TransactionDO{" +
                "id='" + id + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", time=" + time +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", conversionRate=" + conversionRate +
                ", transactionType=" + transactionType +
                ", totalBalance=" + totalBalance +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
