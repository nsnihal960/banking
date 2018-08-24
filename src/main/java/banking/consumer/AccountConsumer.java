package banking.consumer;

import banking.api.dto.response.Balance;
import banking.common.CurrenyConversionUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import banking.api.dto.response.Profile;
import banking.api.dto.response.Statement;
import banking.dao.AccountDao;
import banking.dao.dataobject.BalanceDO;
import banking.dao.dataobject.TransactionDO;
import banking.mappers.BalanceMapper;
import banking.mappers.StatementMapper;
import javafx.util.Pair;

@Singleton
public class AccountConsumer {
    private final AccountDao accountDao;
    private final ProfileConsumer profileConsumer;

    @Inject
    public AccountConsumer(AccountDao accountDao,
                           ProfileConsumer profileConsumer) {
        this.accountDao = accountDao;
        this.profileConsumer = profileConsumer;
    }

    public void addTransaction(Long userId, TransactionDO transaction){
        Profile user = profileConsumer.getUserById(userId);
        accountDao.addTransaction(user, transaction);
    }

    public Statement getStatement(Long id, Long startTime, Long endTime, Integer pageCount){
        Profile user = profileConsumer.getUserById(id);
        List<TransactionDO> transactions = accountDao.getTrimmedTransactions(user,startTime,endTime,pageCount);
        BalanceDO balance = accountDao.getOrCreateBalance(user);
        return StatementMapper.toStatementDto(
                user,
                BalanceMapper.balanceDoToDto(balance, user.currency),
                transactions);
    }

    public Balance getBalance(Long id) {
        Profile user = profileConsumer.getUserById(id);
        return BalanceMapper.balanceDoToDto(
                accountDao.getOrCreateBalance(user)
                , user.currency
        );
    }

    public Balance addBalance(Long userId, Double amount, String currency) {
        Profile profile = profileConsumer.getUserById(userId);
        currency = CurrenyConversionUtils.getFallBackCurrency(currency, profile);
        addTransaction(
                userId,
                accountDao.addBalance(profile, amount, currency)
        );
        return getBalance(userId);
    }

    public Balance deductBalance(Long userId, Double amount, String currency) {
        Profile profile = profileConsumer.getUserById(userId);
        currency = CurrenyConversionUtils.getFallBackCurrency(currency, profile);
        addTransaction(
                userId,
                accountDao.deductBalance(profile, amount, currency)
        );
        return getBalance(userId);
    }

    public Balance transferMoney(Long fromUserId, Long toUserId, Double amount, String currency) {
        Profile fromUser = profileConsumer.getUserById(fromUserId);
        Profile toUser = profileConsumer.getUserById(toUserId);
        currency = CurrenyConversionUtils.getFallBackCurrency(currency, fromUser);
        Pair<TransactionDO, TransactionDO> transactionPair = accountDao.transferBalance(fromUser, toUser, amount, currency);
        addTransaction(fromUser.id, transactionPair.getKey());
        addTransaction(toUser.id, transactionPair.getValue());
        return getBalance(fromUser.id);
    }
}
