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
import banking.common.CurrencyConversionUtils;
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
        logger.info("Received request to create user for: {}", publicProfile);
        if(profileDao.getUserByEmail(publicProfile.email) != null){
            throw new OperationNotAllowed("User already exists with email!");
        }
        if(profileDao.getUserByMobile(publicProfile.mobile) != null){
            throw new OperationNotAllowed("User already exists with mobile!");
        }
        CurrencyConversionUtils.validateCurrency(publicProfile.currency);
        ProfileDO privateProfile = profileDao.createUser(publicProfile);
        Profile profile = ProfileMapper.profileDoToDto(privateProfile);
        logger.info("Completed request to create user for: {}", publicProfile);
        return profile;
    }

    public Profile getUserById(Long userId){
        logger.info("Received request to get user for id: {}", userId);
        ProfileDO privateProfile = profileDao.getUserById(userId);
        if(privateProfile == null){
            throw new NotFound("User Id cannot be located! ");
        }
        Profile profile = ProfileMapper.profileDoToDto(privateProfile);
        logger.info("Completed request to get user for id: {}", userId);
        return profile;
    }

    public Profile getUserByMobile(Mobile mobile){
        logger.info("Received request to get user for mobile: {}", mobile);
        ProfileDO privateProfile = profileDao.getUserByMobile(mobile);
        if(privateProfile == null){
            throw new NotFound("User Id cannot be located! ");
        }
        Profile profile = ProfileMapper.profileDoToDto(privateProfile);
        logger.info("Completed request to get user for mobile: {}", mobile);
        return profile;
    }

    public Profile getUserByEmail(String email){
        ProfileDO privateProfile = profileDao.getUserByEmail(email);
        if(privateProfile == null){
            throw new NotFound("User Id cannot be located! ");
        }
        return ProfileMapper.profileDoToDto(privateProfile);
    }
}
