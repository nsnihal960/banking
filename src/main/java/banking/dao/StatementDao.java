package banking.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import banking.api.dto.Transaction;

public class StatementDao {
    //short circuiting database here
    private Map<Long, List<Transaction>> statementMap = new ConcurrentHashMap<>(1000);
}
