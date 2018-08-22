package banking.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import banking.api.dto.response.PublicProfile;
import banking.api.dto.response.exception.ClientException;

public class CreateProfileRequest implements Request {
    public final PublicProfile profile;

    @JsonCreator
    public CreateProfileRequest(@NotNull @JsonProperty("profile") PublicProfile profile) {
        this.profile = profile;
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (profile.name == null) {
            errors.add("User name cannot be blank");
        } else if (StringUtils.isBlank(profile.name.firstName)) {
            errors.add("User first name cannot be blank");
        }
        if (profile.mobile == null) {
            errors.add("User mobile cannot be null");
        } else {
            profile.mobile.validate();
        }
        if (StringUtils.isBlank(profile.email)) {
            errors.add("User email cannot be blank");
        }
        if (StringUtils.isBlank(profile.currency)) {
            errors.add("User currency cannot be blank");
        }
        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}
