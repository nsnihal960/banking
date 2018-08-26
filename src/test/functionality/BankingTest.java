package functionality;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.Name;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.consumer.AccountConsumer;
import banking.consumer.ProfileConsumer;
import banking.dao.AccountDao;
import banking.dao.ProfileDao;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class BankingTest extends TestCase implements TestConstants {
    protected AccountConsumer accountConsumer;
    protected ProfileConsumer profileConsumer;

    @Before
    protected void setUp() throws Exception{
        super.setUp();
        profileConsumer = new ProfileConsumer(new ProfileDao());
        accountConsumer = new AccountConsumer(new AccountDao(), profileConsumer);
    }

    @Test
    public void testSetUp(){

    }

    protected void tearDown() throws Exception {
        super.tearDown();
        profileConsumer = null;
        accountConsumer = null;
    }

    protected PublicProfile getPublicProfile(){
        return new PublicProfile(new Name("Nihal", "Srivastava"),
                "nsnihal960@gmail.com",
                new Mobile("91", "8004968195"),
                "INR"
        );
    }

    protected PublicProfile getRandomPublicProfile(){
        return new PublicProfile(new Name("Nihal", "Srivastava"),
                UUID.randomUUID().toString() + "@gmail.com",
                new Mobile("91", String.valueOf(
                        (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L)),
                "INR"
                );
    }

    protected Profile createAccount(){
        return profileConsumer.createUser(getPublicProfile());
    }
}
