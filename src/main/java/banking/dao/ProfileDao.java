package banking.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banking.dao.dataobject.Profile;

public class ProfileDao {
    //short circuiting database here
    private Map<Long, Profile> profileMap = new ConcurrentHashMap<>(1000);
}
