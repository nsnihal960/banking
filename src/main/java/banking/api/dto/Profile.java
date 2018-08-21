package banking.api.dto;

import java.util.Currency;

public class Profile {
    public final Long id;
    public final Name name;
    public final String email;
    public final String mobile;
    public final Currency currency;

    public Profile(Long id, Name name, String email, String mobile, Currency currency) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        return id != null ? id.equals(profile.id) : profile.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
