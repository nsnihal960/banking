package banking.api.dto.response;

import banking.api.dto.request.Request;
import banking.api.dto.response.exception.ClientException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
        return isVerified.equals(mobile.isVerified);
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
        if(!(countryCode.matches("\\d{3}") || countryCode.matches("\\d{2}"))){
            errors.add("User mobile number not valid(Country Code not valid. Must be 2-3 digit string)");
        }
        if(!(number.matches("\\d{10}")
                || number.matches("\\d{7}")
                || number.matches("\\d{8}")
                || number.matches("\\d{9}"))){
            errors.add("User mobile number not valid(Number not valid. Must be 7-10 digit string)");
        }
        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}
