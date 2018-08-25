package banking.dao;

import banking.api.dto.response.exception.NotFound;
import banking.api.dto.response.exception.OperationNotAllowed;
import banking.common.CurrencyConversionUtils;
import banking.dao.dataobject.BalanceDO;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.response.Profile;
import banking.dao.dataobject.TransactionDO;
import javafx.util.Pair;

import static banking.api.dto.response.Transaction.TransactionType.CREDIT;
import static banking.api.dto.response.Transaction.TransactionType.DEBIT;
import static banking.common.Constants.GLOBAL_CURRENCY;
import static banking.common.Constants.USER_MAP_SIZE_DEFAULT;

@Singleton
public class AccountDao {
    //short circuiting database here
    private Map<Long, List<TransactionDO>> statementMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);
    private Map<Long, BalanceDO> balanceMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);
    //used to simulate row level locks in db like MySQl
    private Map<Long, Object> lockMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);

    public void addTransaction(Profile user, TransactionDO transaction) {
        synchronized (lockMap.get(user.id)) {
            getTransaction(user).add(transaction);
            getTransaction(user).sort((t1, t2) -> t2.getTime().compareTo(t1.getTime()));
        }
    }

    public List<TransactionDO> getTrimmedTransactions(Profile user,
                                                      Long start,
                                                      Long end,
                                                      int pageCount) {
        return getSubList(getTransaction(user),
                start,
                end,
                pageCount);
    }

    public List<TransactionDO> getTransaction(Profile user) {
        if (!statementMap.containsKey(user.id)) {
            statementMap.put(user.id, new ArrayList<>());
        }
        getOrCreateBalanceAndInstantiateLocks(user);
        List<TransactionDO> transactions = statementMap.get(user.id);
        return transactions;
    }

    public synchronized BalanceDO getOrCreateBalanceAndInstantiateLocks(Profile profile){
        BalanceDO balanceDO = balanceMap.get(profile.id);
        if(balanceDO == null){
            Long now = System.currentTimeMillis();
            balanceDO = new BalanceDO(0.0, now, now);
            balanceMap.put(profile.id, balanceDO);
            lockMap.put(profile.id, new Object());
        }
        return balanceDO;
    }

    public TransactionDO addBalance(Profile user, Double amount, String currency){
        Long now = System.currentTimeMillis();
        BalanceDO balanceDO = getOrCreateBalanceAndInstantiateLocks(user);
        Double totalBalance;
        if(balanceDO == null){
            throw new NotFound("User Id cannot be located!");
        }
        Double updateAmount = CurrencyConversionUtils.getGlobalCurrencyAmount(amount,currency);
        //lock this user balance
        synchronized (lockMap.get(user.id)){
            totalBalance = balanceDO.getAmount() + updateAmount;
            balanceDO.setAmount(totalBalance);
            balanceDO.setUpdatedOn(now);
        }
        return new TransactionDO(UUID.randomUUID().toString(),
                null,
                user.id,
                now,
                updateAmount,
                currency,
                CurrencyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                CREDIT,
                totalBalance);
    }

    public TransactionDO deductBalance(Profile user, Double amount, String currency){
        Long now = System.currentTimeMillis();
        BalanceDO balanceDO = getOrCreateBalanceAndInstantiateLocks(user);
        Double totalBalance;
        if(balanceDO == null){
            throw new NotFound("User Id cannot be located!");
        }
        Double updateAmount = CurrencyConversionUtils.getGlobalCurrencyAmount(amount,currency);
        //lock this user balance
        synchronized (lockMap.get(user.id)){
            if(balanceDO.getAmount() < updateAmount){
                throw new OperationNotAllowed("Insufficient Funds!");
            }
            totalBalance = balanceDO.getAmount() - updateAmount;
            balanceDO.setAmount(totalBalance);
            balanceDO.setUpdatedOn(now);
        }
        return new TransactionDO(UUID.randomUUID().toString(),
                user.id,
                null,
                now,
                updateAmount,
                currency,
                CurrencyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                DEBIT,
                totalBalance);
    }

    public Pair<TransactionDO, TransactionDO> transferBalance(Profile fromUser, Profile toUser, Double amount, String currency){
        String transactionId = UUID.randomUUID().toString();
        Long now = System.currentTimeMillis();
        BalanceDO fromBalanceDO = getOrCreateBalanceAndInstantiateLocks(fromUser);
        if(fromBalanceDO == null){
            throw new NotFound("Sender User Id cannot be located!");
        }
        BalanceDO toBalanceDO = getOrCreateBalanceAndInstantiateLocks(toUser);
        if(toBalanceDO == null){
            throw new NotFound("Receiver User Id cannot be located!");
        }
        Double updateAmount = CurrencyConversionUtils.getGlobalCurrencyAmount(amount,currency);
        Double fromTotalAmount, toTotalAmount;

        if(!fromUser.equals(toUser)) {//else deadlock
            //lock both user balance, deadlock free manner
            Long min = fromUser.id.compareTo(toUser.id) > 0 ? fromUser.id : toUser.id;
            Long max = fromUser.id.compareTo(toUser.id) < 0 ? fromUser.id : toUser.id;
            synchronized (lockMap.get(min)) {
                synchronized (lockMap.get(max)) {
                    if (fromBalanceDO.getAmount() < updateAmount) {
                        throw new OperationNotAllowed("Insufficient Funds!");
                    }
                    fromTotalAmount = fromBalanceDO.getAmount() - updateAmount;
                    toTotalAmount = toBalanceDO.getAmount() + updateAmount;
                    fromBalanceDO.setAmount(fromTotalAmount);
                    fromBalanceDO.setUpdatedOn(now);
                    toBalanceDO.setAmount(toTotalAmount);
                    toBalanceDO.setUpdatedOn(now);
                }
            }
        } else {
            fromTotalAmount = fromBalanceDO.getAmount();
            toTotalAmount = fromTotalAmount;
        }
        return new Pair<>(
                new TransactionDO(transactionId,
                        fromUser.id,
                        toUser.id,
                        now,
                        updateAmount,
                        currency,
                        CurrencyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                        DEBIT,
                        fromTotalAmount),
                new TransactionDO(transactionId,
                        fromUser.id,
                        toUser.id,
                        now,
                        updateAmount,
                        currency,
                        CurrencyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                        CREDIT,
                        toTotalAmount
                ));
    }

    private List<TransactionDO> getSubList(List<TransactionDO> list,
                                           Long start,
                                           Long end,
                                           int pageCount) {
        List<TransactionDO> transactions = new ArrayList<>(pageCount);
        int count = 0;
        for (int i = 0; i < list.size() && count < pageCount; i++) {
            if (list.get(i).getTime() >= start
                    && list.get(i).getTime() < end) {
                transactions.add(list.get(i));
                count++;
            }
        }
        return transactions;
    }
}
