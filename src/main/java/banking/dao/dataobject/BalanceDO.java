package banking.dao.dataobject;

public class BalanceDO {
    private Double amount;
    private Long createdOn;
    private Long updatedOn;

    public BalanceDO(Double amount, Long createdOn, Long updatedOn) {
        this.amount = amount;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
