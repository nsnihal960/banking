package banking.dao;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.dao.dataobject.ProfileDO;
import banking.mappers.ProfileMapper;

@Singleton
public class ProfileDao {
    //short circuiting database here
    private Map<String, ProfileDO> profileMap = new ConcurrentHashMap<>(1000);
    private Map<String, String> emailMap = new HashMap<>(1000);
    private Map<String, String> mobileMap = new HashMap<>(1000);

    public ProfileDO createUser(PublicProfile publicProfile) {
        Profile profile = getPrivateProfile(publicProfile);
        ProfileDO profileDO = ProfileMapper.profileDtoToDo(profile);
        profileMap.put(profile.id, profileDO);
        emailMap.put(profile.email, profile.id);
        mobileMap.put(profile.mobile, profile.id);
        return profileDO;
    }

    public ProfileDO getUserById(String id) {
        return profileMap.get(id);
    }

    public ProfileDO getUserByEmail(String email) {
        String userId = emailMap.get(email);
        if (userId == null) {
            return null;
        }
        return profileMap.get(userId);
    }

    public ProfileDO getUserByMobile(String mobile) {
        String userId = mobileMap.get(mobile);
        if (userId == null) {
            return null;
        }
        return profileMap.get(userId);
    }

    private Profile getPrivateProfile(PublicProfile publicProfile) {
        String userId = UUID.randomUUID().toString();
        while (profileMap.containsKey(userId)) {
            userId = UUID.randomUUID().toString();
        }
        return new Profile(userId, publicProfile);
    }
}
