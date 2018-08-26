package stress;

import functionality.BankingTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileStressTest extends BankingTest {
    @Test
    public void testIdGeneration(){
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_STRESS_THREADS);
        List<CompletableFuture> futures = new ArrayList<>();
        for(int i=0; i<NUMBER_OF_STRESS_THREADS;i++) {
            futures.add(CompletableFuture.runAsync(() ->
                    profileConsumer.createUser(getRandomPublicProfile()), executor));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        ((ExecutorService) executor).shutdown();
        assertEquals(profileConsumer.createUser(getRandomPublicProfile()).id.longValue(), 1L + NUMBER_OF_STRESS_THREADS);
    }
}
