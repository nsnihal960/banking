package banking.dao.dataobject;

import java.util.Currency;

import banking.api.dto.Name;

public class Profile {
    private Long id;
    private Name name;
    private String email;
    private String mobile;
    private Currency currency;
    private Long createdOn;
    private Long updatedOn;

    public Profile(Long id, Name name, String email, String mobile, Currency currency) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name=" + name +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", currency=" + currency +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
