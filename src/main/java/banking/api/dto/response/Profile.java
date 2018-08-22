package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile extends PublicProfile {
    public final String id;

    @JsonCreator
    public Profile(@JsonProperty("id") String id,
                   @JsonProperty("profile") PublicProfile profile) {
        super(profile.name,
                profile.email,
                profile.mobile,
                profile.currency);
        this.id = id;
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

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                "} " + super.toString();
    }
}
