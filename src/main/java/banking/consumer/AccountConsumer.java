package banking.consumer;

import banking.api.dto.response.Balance;
import banking.api.dto.response.Profile;
import banking.api.dto.response.Statement;
import banking.common.CurrencyConversionUtils;
import banking.dao.impl.BalanceDaoImpl;
import banking.dao.dataobject.BalanceDO;
import banking.dao.dataobject.TransactionDO;
import banking.mappers.BalanceMapper;
import banking.mappers.StatementMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class AccountConsumer {
    private final BalanceDaoImpl balanceDao;
    private final ProfileConsumer profileConsumer;
    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);

    @Inject
    public AccountConsumer(BalanceDaoImpl balanceDao,
                           ProfileConsumer profileConsumer) {
        this.balanceDao = balanceDao;
        this.profileConsumer = profileConsumer;
    }

    public Statement getStatement(Long id, Long startTime, Long endTime, Integer pageCount){
        logger.info("Received request to getStatement for user: {}, start : {}," +
                " end: {}, count: {}", id, startTime, endTime, pageCount);
        Profile user = profileConsumer.getUserById(id);
        List<TransactionDO> transactions = balanceDao.getTrimmedTransactions(user,startTime,endTime,pageCount);
        BalanceDO balance = balanceDao.getOrCreateBalance(user);
        Statement statement = StatementMapper.toStatementDto(
                user,
                BalanceMapper.balanceDoToDto(balance, user.currency),
                transactions);
        logger.info("Completed request to getStatement for user: {}", id);
        return statement;
    }

    public Balance getBalance(Long id) {
        logger.info("Received request to getBalance for user: {}", id);
        Profile user = profileConsumer.getUserById(id);
        Balance balance = BalanceMapper.balanceDoToDto(
                balanceDao.getOrCreateBalance(user),
                user.currency
        );
        logger.info("Completed request to getBalance for user: {}", id);
        return balance;
    }

    public Balance addBalance(Long userId, Double amount, String currency) {
        logger.info("Received request to addBalance for user: {}", userId); // hide acc data
        Profile profile = profileConsumer.getUserById(userId);
        currency = CurrencyConversionUtils.getFallBackCurrency(currency, profile);
        addTransaction(
                userId,
                balanceDao.addBalance(profile, amount, currency)
        );
        Balance balance = getBalance(userId);
        logger.info("Completed request to addBalance for user: {}", userId);
        return balance;
    }

    public Balance deductBalance(Long userId, Double amount, String currency) {
        logger.info("Received request to deductBalance for user: {}", userId);
        Profile profile = profileConsumer.getUserById(userId);
        currency = CurrencyConversionUtils.getFallBackCurrency(currency, profile);
        addTransaction(
                userId,
                balanceDao.deductBalance(profile, amount, currency)
        );
        Balance balance = getBalance(userId);
        logger.info("Completed request to deductBalance for user: {}", userId);
        return balance;
    }

    public Balance transferMoney(Long fromUserId, Long toUserId, Double amount, String currency) {
        logger.info("Received request to transferBalance from user: {}, to: {}", fromUserId, toUserId);
        Profile fromUser = profileConsumer.getUserById(fromUserId);
        Profile toUser = profileConsumer.getUserById(toUserId);
        currency = CurrencyConversionUtils.getFallBackCurrency(currency, fromUser);
        Pair<TransactionDO, TransactionDO> transactionPair = balanceDao.transferBalance(fromUser, toUser, amount, currency);
        addTransaction(fromUser.id, transactionPair.getKey());
        addTransaction(toUser.id, transactionPair.getValue());
        Balance balance = getBalance(fromUser.id);
        logger.info("Completed request to transferBalance from user: {}, to: {}", fromUserId, toUserId);
        return balance;
    }
    private void addTransaction(Long userId, TransactionDO transaction){
        Profile user = profileConsumer.getUserById(userId);
        balanceDao.addTransaction(user, transaction);
    }
}
