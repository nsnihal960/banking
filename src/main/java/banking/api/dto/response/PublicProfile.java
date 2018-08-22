package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class PublicProfile implements Response {
    @NotNull
    public final Name name;
    public final String email;
    public final Mobile mobile;
    public final String currency;

    @JsonCreator
    public PublicProfile(@NotNull @JsonProperty("name") Name name,
                         @NotNull @JsonProperty("email") String email,
                         @NotNull @JsonProperty("mobile") Mobile mobile,
                         @NotNull @JsonProperty("currency") String currency) {
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
