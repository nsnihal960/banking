package functionality;

import banking.api.dto.response.exception.OperationNotAllowed;
import banking.common.CurrencyConversionUtils;
import org.junit.Test;

public class AccountFuncTests extends BankingTest {
    @Test
    public void testAddBalance(){
        createAccount();
        accountConsumer.addBalance(1L, 10.0, "INR");
    }

    @Test
    public void testGetBalance(){
        createAccount();
        accountConsumer.addBalance(1L, 10.0, "INR");
        assertEquals(CurrencyConversionUtils.getGlobalCurrencyAmount(10.0, "INR"),
                accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testGetBalanceWrongIn(){
        createAccount();
        try {
            accountConsumer.addBalance(1L, -10.0, "INR");
        } catch (Exception ae) {
            assertEquals(OperationNotAllowed.class, ae.getClass());
        }
    }

    @Test
    public void testDeductBalance(){
        createAccount();
        accountConsumer.addBalance(1L, 10.0, "INR");
        accountConsumer.deductBalance(1L, 5.0, "INR");
        assertEquals(CurrencyConversionUtils.getGlobalCurrencyAmount(5.0, "INR"),
                accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testDeductBalanceException(){
        createAccount();
        accountConsumer.addBalance(1L, 10.0, "INR");
        try {
            accountConsumer.deductBalance(1L, 15.0, "INR");
        } catch (Exception ae) {
            assertEquals(OperationNotAllowed.class, ae.getClass());
        }
    }

    @Test
    public void testTransferMoney(){
        profileConsumer.createUser(getRandomPublicProfile());
        profileConsumer.createUser(getRandomPublicProfile());
        accountConsumer.addBalance(1L, 100.0, "INR");
        //same currency
        accountConsumer.transferMoney(1L,2L,1.0,"INR");
        assertEquals(
                CurrencyConversionUtils.getGlobalCurrencyAmount(99.0, "INR"),
                accountConsumer.getBalance(1L).balance);
        //different currency
        accountConsumer.transferMoney(1L,2L,1.0,"USD");
        assertEquals(
                CurrencyConversionUtils.getGlobalCurrencyAmount(99.0, "INR") - 1.0,
                accountConsumer.getBalance(1L).balance);

    }

    @Test
    public void testGetStatement(){
        profileConsumer.createUser(getRandomPublicProfile());
        profileConsumer.createUser(getRandomPublicProfile());
        accountConsumer.addBalance(1L, 100.0, "INR");
        //same currency
        accountConsumer.transferMoney(1L,2L,1.0,"INR");
        //different currency
        accountConsumer.transferMoney(1L,2L,1.0,"USD");
        assertEquals(3,
                accountConsumer.getStatement(1L, 0L, Long.MAX_VALUE, 10).transactions.size());
        assertEquals(2,
                accountConsumer.getStatement(2L, 0L, Long.MAX_VALUE, 10).transactions.size());
        assertEquals(1,
                accountConsumer.getStatement(1L, 0L, Long.MAX_VALUE, 1).transactions.size());

    }
}
