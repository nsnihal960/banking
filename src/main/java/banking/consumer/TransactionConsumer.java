package banking.consumer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import banking.api.dto.response.Balance;
import banking.api.dto.response.Profile;
import banking.biz.CurrenyConversionUtils;
import banking.dao.TransactionDao;
import banking.dao.dataobject.TransactionDO;
import banking.mappers.BalanceMapper;
import javafx.util.Pair;

@Singleton
public class TransactionConsumer {
    private final TransactionDao transactionDao;
    private final StatementConsumer statementConsumer;
    private final ProfileConsumer profileConsumer;

    @Inject
    public TransactionConsumer(TransactionDao transactionDao,
                               StatementConsumer statementConsumer,
                               ProfileConsumer profileConsumer) {
        this.transactionDao = transactionDao;
        this.statementConsumer = statementConsumer;
        this.profileConsumer = profileConsumer;
    }

    public Balance getBalance(Long userId) {
        Profile profile = profileConsumer.getUserById(userId);
        return BalanceMapper.balanceDoToDto(transactionDao.getOrCreateBalance(profile),
                profile.currency);
    }

    public Balance addBalance(Long userId, Double amount, String currency) {
        Profile profile = profileConsumer.getUserById(userId);
        currency = CurrenyConversionUtils.getFallBackCurrency(currency, profile);
        statementConsumer.addTransaction(
                userId,
                transactionDao.addBalance(profile, amount, currency)
        );
        return getBalance(userId);
    }

    public Balance deductBalance(Long userId, Double amount, String currency) {
        Profile profile = profileConsumer.getUserById(userId);
        currency = CurrenyConversionUtils.getFallBackCurrency(currency, profile);
        statementConsumer.addTransaction(
                userId,
                transactionDao.deductBalance(profile, amount, currency)
        );
        return getBalance(userId);
    }

    public Balance transferMoney(Long fromUserId, Long toUserId, Double amount, String currency) {
        Profile fromUser = profileConsumer.getUserById(fromUserId);
        Profile toUser = profileConsumer.getUserById(toUserId);
        currency = CurrenyConversionUtils.getFallBackCurrency(currency, fromUser);
        Pair<TransactionDO, TransactionDO> transactionPair = transactionDao.transferBalance(fromUser, toUser, amount, currency);
        statementConsumer.addTransaction(fromUser.id, transactionPair.getKey());
        statementConsumer.addTransaction(toUser.id, transactionPair.getValue());
        return getBalance(fromUser.id);
    }
}
