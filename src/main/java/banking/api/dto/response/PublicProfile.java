package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicProfile implements Response {
    public final Name name;
    public final String email;
    public final String mobile;
    public final String currency;

    @JsonCreator
    public PublicProfile(@JsonProperty("name") Name name,
                         @JsonProperty("email") String email,
                         @JsonProperty("mobile") String mobile,
                         @JsonProperty("currency") String currency) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PublicProfile{" +
                "name=" + name +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", currency=" + currency +
                '}';
    }
}
