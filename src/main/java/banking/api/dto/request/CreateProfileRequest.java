package banking.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import banking.api.dto.response.PublicProfile;
import banking.api.dto.response.exception.ClientException;

public class CreateProfileRequest implements Request {
    public final PublicProfile profile;

    @JsonCreator
    public CreateProfileRequest(@JsonProperty("profile") PublicProfile profile) {
        this.profile = profile;
        validate();
    }

    @Override
    public void validate() {
        if (profile.name == null) {
            throw new ClientException("User name cannot be blank");
        }
        if (StringUtils.isBlank(profile.name.firstName)) {
            throw new ClientException("User first name cannot be blank");
        }
        if (StringUtils.isBlank(profile.mobile)) {
            throw new ClientException("User mobile cannot be blank");
        }
        if (StringUtils.isBlank(profile.email)) {
            throw new ClientException("User email cannot be blank");
        }
        if (StringUtils.isBlank(profile.currency)) {
            throw new ClientException("User currency cannot be blank");
        }
    }


}
