package banking.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import banking.api.dto.response.Balance;
import banking.api.dto.response.Profile;
import banking.api.dto.response.Statement;
import banking.api.dto.response.Transaction;
import banking.dao.dataobject.TransactionDO;

public class StatementMapper {
    public static Statement toStatementDto(Profile profile, Balance balance, List<TransactionDO> transactionList) {
        List<Transaction> transactionsDto = new ArrayList<>();
        Long minTime = Long.MAX_VALUE;
        for (TransactionDO txn : transactionList) {
            transactionsDto.add(TransactionMapper.transactionDoToDto(txn));
            minTime = Math.min(minTime, txn.getTime());
        }
        minTime = minTime == Long.MAX_VALUE ? null : minTime;
        return new Statement(profile, new Date(), balance, transactionsDto, minTime);
    }
}
