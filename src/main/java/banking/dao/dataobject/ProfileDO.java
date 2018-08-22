package banking.dao.dataobject;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.Name;

public class ProfileDO {
    private Long id;
    private Name name;
    private String email;
    private Mobile mobile;
    private String currency;
    private Long createdOn;
    private Long updatedOn;

    public ProfileDO(Long id, Name name, String email, Mobile mobile, String currency) {
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
                ", mobile='" + mobile.toString() + '\'' +
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

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
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
