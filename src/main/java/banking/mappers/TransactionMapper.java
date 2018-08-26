package banking.mappers;

import banking.api.dto.response.Transaction;
import banking.common.CurrencyConversionUtils;
import banking.dao.dataobject.TransactionDO;

import java.util.Date;

public class TransactionMapper {
    public static Transaction transactionDoToDto(TransactionDO transactionDO){
        return new Transaction(transactionDO.getId(),
                transactionDO.getFrom(),
                transactionDO.getTo(),
                new Date(transactionDO.getTime()),
                CurrencyConversionUtils.getLocalCurrency(transactionDO.getAmount(), transactionDO.getCurrency()),
                transactionDO.getConversionRate(),
                transactionDO.getTransactionType(),
                transactionDO.getTotalBalance());
    }
}
