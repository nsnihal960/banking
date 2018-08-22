package banking.consumer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import banking.api.dto.response.Profile;
import banking.api.dto.response.Statement;
import banking.dao.StatementDao;
import banking.dao.TransactionDao;
import banking.dao.dataobject.BalanceDO;
import banking.dao.dataobject.TransactionDO;
import banking.mappers.BalanceMapper;
import banking.mappers.StatementMapper;

@Singleton
public class StatementConsumer {
    private final StatementDao statementDao;
    private final ProfileConsumer profileConsumer;
    private final TransactionDao transactionDao; //TODO bad

    @Inject
    public StatementConsumer(StatementDao statementDao, ProfileConsumer profileConsumer, TransactionDao transactionDao) {
        this.statementDao = statementDao;
        this.profileConsumer = profileConsumer;
        this.transactionDao = transactionDao;
    }

    public void addTransaction(Long userId, TransactionDO transaction){
        Profile user = profileConsumer.getUserById(userId);
        statementDao.addTransaction(user, transaction);
    }

    public Statement getStatement(Long id, Long startTime, Long endTime, Integer pageCount){
        Profile user = profileConsumer.getUserById(id);
        List<TransactionDO> transactions = statementDao.getTrimmedTransactions(user,startTime,endTime,pageCount);
        BalanceDO balance = transactionDao.getOrCreateBalance(user);
        return StatementMapper.toStatementDto(
                user,
                BalanceMapper.balanceDoToDto(balance, user.currency),
                transactions);
    }
}
