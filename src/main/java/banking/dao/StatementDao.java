package banking.dao;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.response.Profile;
import banking.dao.dataobject.TransactionDO;

@Singleton
public class StatementDao {
    //short circuiting database here
    private Map<Long, List<TransactionDO>> statementMap = new ConcurrentHashMap<>(1000);

    public void addTransaction(Profile user, TransactionDO transaction) {
        getTransaction(user).add(transaction);
        //can be made thread safe, overkill for now as its unique for users
        getTransaction(user).sort((t1, t2) -> t2.getTime().compareTo(t1.getTime()));
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
        List<TransactionDO> transactions = statementMap.get(user.id);
        return transactions;
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
