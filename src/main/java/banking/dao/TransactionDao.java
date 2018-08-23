package banking.dao;

import com.google.inject.Singleton;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.response.Profile;
import banking.api.dto.response.exception.NotFound;
import banking.api.dto.response.exception.OperationNotAllowed;
import banking.common.CurrenyConversionUtils;
import banking.dao.dataobject.BalanceDO;
import banking.dao.dataobject.TransactionDO;
import javafx.util.Pair;

import static banking.api.dto.response.Transaction.TransactionType.CREDIT;
import static banking.api.dto.response.Transaction.TransactionType.DEBIT;
import static banking.common.Constants.GLOBAL_CURRENCY;

@Singleton
public class TransactionDao {
    //short circuiting database here
    private Map<Long, BalanceDO> balanceMap = new ConcurrentHashMap<>(1000);
    //used to simulate row level locks in db like MySQl
    private Map<Long, Object> lockMap = new ConcurrentHashMap<>(1000);

    public BalanceDO getOrCreateBalance(Profile profile){
        BalanceDO balanceDO = balanceMap.get(profile.id);
        if(balanceDO == null){
            Long now = System.currentTimeMillis();
            balanceDO = new BalanceDO(0.0,now,now);
            balanceMap.put(profile.id, balanceDO);
            lockMap.put(profile.id, new Object());
        }
        return balanceDO;
    }

    public TransactionDO addBalance(Profile user, Double amount, String currency){
        Long now = System.currentTimeMillis();
        BalanceDO balanceDO = getOrCreateBalance(user);
        Double totalBalance;
        if(balanceDO == null){
            throw new NotFound("User Id cannot be located!");
        }
        Double updateAmount = CurrenyConversionUtils.getGlobalCurrencyAmount(amount,currency);
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
                CurrenyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                CREDIT,
                totalBalance);
    }

    public TransactionDO deductBalance(Profile user, Double amount, String currency){
        Long now = System.currentTimeMillis();
        BalanceDO balanceDO = getOrCreateBalance(user);
        Double totalBalance;
        if(balanceDO == null){
            throw new NotFound("User Id cannot be located!");
        }
        Double updateAmount = CurrenyConversionUtils.getGlobalCurrencyAmount(amount,currency);
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
                CurrenyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                DEBIT,
                totalBalance);
    }

    public Pair<TransactionDO, TransactionDO> transferBalance(Profile fromUser, Profile toUser, Double amount, String currency){
        String transactionId = UUID.randomUUID().toString();
        Long now = System.currentTimeMillis();
        BalanceDO fromBalanceDO = getOrCreateBalance(fromUser);
        if(fromBalanceDO == null){
            throw new NotFound("Sender User Id cannot be located!");
        }
        BalanceDO toBalanceDO = getOrCreateBalance(toUser);
        if(toBalanceDO == null){
            throw new NotFound("Receiver User Id cannot be located!");
        }
        Double updateAmount = CurrenyConversionUtils.getGlobalCurrencyAmount(amount,currency);
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
                                CurrenyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                                DEBIT,
                                fromTotalAmount),
                        new TransactionDO(transactionId,
                                fromUser.id,
                                toUser.id,
                                now,
                                updateAmount,
                                currency,
                                CurrenyConversionUtils.conversionRate(currency, GLOBAL_CURRENCY),
                                CREDIT,
                                toTotalAmount
                                ));
    }
}
