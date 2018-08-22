package banking.consumer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.api.dto.response.exception.NotFound;
import banking.api.dto.response.exception.OperationNotAllowed;
import banking.biz.CurrenyConversionUtils;
import banking.dao.ProfileDao;
import banking.dao.dataobject.ProfileDO;
import banking.mappers.ProfileMapper;

@Singleton
public class ProfileConsumer {
    private final ProfileDao profileDao;
    private final Logger logger = LoggerFactory.getLogger(ProfileConsumer.class);

    @Inject
    public ProfileConsumer(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public Profile createUser(PublicProfile publicProfile){
        if(profileDao.getUserByEmail(publicProfile.email) != null){
            throw new OperationNotAllowed("User already exists with email!");
        }
        if(profileDao.getUserByMobile(publicProfile.mobile) != null){
            throw new OperationNotAllowed("User already exists with mobile!");
        }
        CurrenyConversionUtils.validateCurrency(publicProfile.currency);
        ProfileDO privateProfile = profileDao.createUser(publicProfile);
        return ProfileMapper.profileDoToDto(privateProfile);
    }

    public Profile getUserById(Long userId){
        ProfileDO privateProfile = profileDao.getUserById(userId);
        if(privateProfile == null){
            throw new NotFound("User Id cannot be located! ");
        }
        return ProfileMapper.profileDoToDto(privateProfile);
    }

    public Profile getUserByEmail(String email){
        ProfileDO privateProfile = profileDao.getUserByEmail(email);
        if(privateProfile == null){
            throw new NotFound("User Id cannot be located! ");
        }
        return ProfileMapper.profileDoToDto(privateProfile);
    }

    public Profile getUserByMobile(Mobile mobile){
        ProfileDO privateProfile = profileDao.getUserByMobile(mobile);
        if(privateProfile == null){
            throw new NotFound("User Id cannot be located! ");
        }
        return ProfileMapper.profileDoToDto(privateProfile);
    }
}
