package functionality;

import banking.api.dto.request.CreateProfileRequest;
import banking.api.dto.response.Mobile;
import banking.api.dto.response.Name;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.api.dto.response.exception.ClientException;
import org.junit.Test;

public class ProfileFuncTests extends BankingTest {
    @Test
    public void testAccountCreation(){
        Profile profile = profileConsumer.createUser(getPublicProfile());
        assertEquals(profile.id.longValue(), 1);
    }

    public void testWrongNameDataCreation(){
        PublicProfile profile = new PublicProfile(
                null,
                "n@r.com",
                new Mobile("91", "8004968195"),
                "INR");
        try {
            new CreateProfileRequest(profile).validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    public void testWrongFNameDataCreation(){
        PublicProfile profile = new PublicProfile(
                new Name("", "Srivastava"),
                "n@r.com",
                new Mobile("91", "8004968195"),
                "INR");
        try {
            new CreateProfileRequest(profile).validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    public void testWrongMobileDataCreation(){
        try {
            new Mobile("","8004968195").validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    public void testWrongCurrDataCreation(){
        PublicProfile profile = new PublicProfile(
                new Name("", "Srivastava"),
                "n@r.com",
                new Mobile("91", "8004968195"),
                "UUU");
        try {
            new CreateProfileRequest(profile).validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    public void testWrongCurrNullDataCreation(){
        PublicProfile profile = new PublicProfile(
                new Name("", "Srivastava"),
                "n@r.com",
                null,
                null);
        try {
            new CreateProfileRequest(profile).validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    @Test
    public void testGetUserById(){
        PublicProfile publicProfile = getPublicProfile();
        profileConsumer.createUser(publicProfile);
        PublicProfile gotProfile = profileConsumer.getUserById(1L);
        assertEquals(publicProfile.name, gotProfile.name);
    }

    @Test
    public void testGetUserByEmail(){
        PublicProfile publicProfile = getPublicProfile();
        profileConsumer.createUser(publicProfile);
        PublicProfile gotProfile = profileConsumer.getUserByEmail("nsnihal960@gmail.com");
        assertEquals(publicProfile.name, gotProfile.name);
    }

    @Test
    public void testGetUserByMobile(){
        PublicProfile publicProfile = getPublicProfile();
        profileConsumer.createUser(publicProfile);
        PublicProfile gotProfile = profileConsumer.getUserByMobile(new Mobile("91",
                "8004968195"));
        assertEquals(publicProfile.name, gotProfile.name);
    }

}
