package banking.mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import banking.api.dto.response.Balance;
import banking.api.dto.response.Profile;
import banking.api.dto.response.Statement;
import banking.api.dto.response.Transaction;
import banking.dao.dataobject.TransactionDO;

public class StatementMapper {
    public static Statement toStatementDto(Profile profile, Balance balance, List<TransactionDO> transactionList) {
        List<Transaction> transactionsDto = transactionList.stream()
                .map(TransactionMapper::transactionDoToDto)
                .collect(Collectors.toList());
        return new Statement(profile, new Date(), balance, transactionsDto);
    }
}
