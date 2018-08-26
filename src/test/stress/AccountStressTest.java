package stress;

import banking.common.CurrencyConversionUtils;
import functionality.BankingTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountStressTest extends BankingTest {
    @Test
    public void testStressesAddMoney(){
        createAccount();
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_STRESS_THREADS);
        List<CompletableFuture> futures = new ArrayList<>();
        for(int i=0; i<NUMBER_OF_STRESS_THREADS;i++) {
            futures.add(CompletableFuture.runAsync(() ->
                    accountConsumer.addBalance(1L, 10.0, "USD"),
                    executor
            ));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        ((ExecutorService) executor).shutdown();
        assertEquals(
                CurrencyConversionUtils.getGlobalCurrencyAmount(10.0*NUMBER_OF_STRESS_THREADS, "USD"),
                accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testStressesAddDeductMoney(){
        createAccount();
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_STRESS_THREADS);
        accountConsumer.addBalance(1L, 10.0 * (NUMBER_OF_STRESS_THREADS + 1), "USD");
        List<CompletableFuture> futures = new ArrayList<>();
        for(int i=0; i<NUMBER_OF_STRESS_THREADS;i++) {
            futures.add(CompletableFuture.runAsync(() ->
                            accountConsumer.deductBalance(1L, 10.0, "USD"),
                    executor
            ));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        ((ExecutorService) executor).shutdown();
        assertEquals(
                CurrencyConversionUtils.getGlobalCurrencyAmount(10.0, "USD"),
                accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testStressesTransferMoney(){
        profileConsumer.createUser(getRandomPublicProfile());
        profileConsumer.createUser(getRandomPublicProfile());
        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_STRESS_THREADS);
        List<CompletableFuture> futures = new ArrayList<>();
        for(int i=0; i<NUMBER_OF_STRESS_THREADS;i++) {
            futures.add(CompletableFuture.runAsync(() ->
                            accountConsumer.addBalance(1L, 10.0, "USD"),
                    executor
            ));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        futures.clear();
        for(int i=0; i<NUMBER_OF_STRESS_THREADS;i++) {
            futures.add(CompletableFuture.runAsync(() ->
                            accountConsumer.transferMoney(1L, 2L, 10.0, "USD"),
                    executor
            ));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        ((ExecutorService) executor).shutdown();
        assertEquals(
                CurrencyConversionUtils.getGlobalCurrencyAmount(0.0, "USD"),
                accountConsumer.getBalance(1L).balance);
    }
}
