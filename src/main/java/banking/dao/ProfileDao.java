package banking.dao;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.PublicProfile;
import banking.dao.dataobject.ProfileDO;

public interface ProfileDao {
    ProfileDO createUser(PublicProfile publicProfile);
    ProfileDO getUserById(Long id);
    ProfileDO getUserByMobile(Mobile mobile);
}
