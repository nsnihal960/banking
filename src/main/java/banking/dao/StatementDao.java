package banking.dao;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.response.Profile;
import banking.dao.dataobject.TransactionDO;

@Singleton
public class StatementDao {
    //short circuiting database here
    private Map<String, List<TransactionDO>> statementMap = new ConcurrentHashMap<>(1000);

    public void addTransaction(Profile user, TransactionDO transaction) {
        getTransaction(user).add(transaction);
    }

    public List<TransactionDO> getTransaction(Profile user) {
        if (!statementMap.containsKey(user.id)) {
            statementMap.put(user.id, new ArrayList<>());
        }
        List<TransactionDO> transactions = statementMap.get(user.id);
        transactions.sort(Comparator.comparing(TransactionDO::getTime));
        return transactions;
    }
}
