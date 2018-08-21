package banking.dao;

import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.exception.NotFound;
import banking.dao.dataobject.Balance;

public class TransactionDao {
    //short circuiting database here
    private Map<Long, Balance> balanceMap = new ConcurrentHashMap<>(1000);
    //used to simulate row level locks in db like MySQl
    private Map<Long, Object> lockMap = new ConcurrentHashMap<>(1000);

    public void createBalance(Long userId, Currency currency){
        Long now = System.currentTimeMillis();
        balanceMap.put(userId, new Balance(0.0,currency,now,now));
        lockMap.put(userId, new Object());
    }

    public void addBalance(Long userId, Double amount, Currency currency){
        Long now = System.currentTimeMillis();
        Balance balance = balanceMap.get(userId);
        if(balance == null){
            throw new NotFound("User Id cannot be located!");
        }
        synchronized (lockMap.get(userId)){

        }
    }
}
