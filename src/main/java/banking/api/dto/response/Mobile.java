package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import banking.api.dto.request.Request;
import banking.api.dto.response.exception.ClientException;

public class Mobile implements Request {
    public final String countryCode;
    public final String number;
    public final Boolean isVerified; // short circuited for now

    @JsonCreator
    public Mobile(@NotNull @JsonProperty("countryCode") String countryCode,
                  @NotNull @JsonProperty("firstName") String number) {
        this.countryCode = countryCode;
        this.number = number;
        this.isVerified = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mobile mobile = (Mobile) o;

        if (!countryCode.equals(mobile.countryCode)) return false;
        if (!number.equals(mobile.number)) return false;
        return isVerified != null ? isVerified.equals(mobile.isVerified) : mobile.isVerified == null;
    }

    @Override
    public int hashCode() {
        int result = countryCode.hashCode();
        result = 31 * result + number.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "countryCode='" + countryCode + '\'' +
                ", number='" + number + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(countryCode)) {
            errors.add("User mobile number not valid(Country Code cannot be blank)");
        }
        if (StringUtils.isBlank(number)) {
            errors.add("User mobile number not valid(Extension number cannot be blank)");
        }
        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}