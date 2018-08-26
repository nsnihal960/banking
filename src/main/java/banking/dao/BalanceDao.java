package banking.dao;

import banking.api.dto.response.Profile;
import banking.dao.dataobject.BalanceDO;
import banking.dao.dataobject.TransactionDO;
import javafx.util.Pair;

public interface BalanceDao {
    BalanceDO getOrCreateBalance(Profile profile);
    TransactionDO addBalance(Profile user, Double amount, String currency);
    TransactionDO deductBalance(Profile user, Double amount, String currency);
    Pair<TransactionDO, TransactionDO> transferBalance(Profile fromUser, Profile toUser, Double amount, String currency);
}
