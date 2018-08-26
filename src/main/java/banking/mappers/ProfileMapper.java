package banking.mappers;

import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.dao.dataobject.ProfileDO;

public class ProfileMapper {
    public static ProfileDO profileDtoToDo(Profile dto){
        return new ProfileDO(dto.id, dto.name, dto.email, dto.mobile, dto.currency);
    }

    public static Profile profileDoToDto(ProfileDO profileDO){
        return new Profile(profileDO.getId(),
                new PublicProfile(profileDO.getName(),
                        profileDO.getEmail(),
                        profileDO.getMobile(),
                        profileDO.getCurrency().toUpperCase()));
    }
}
