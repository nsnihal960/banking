package banking.mappers;

import java.util.Date;

import banking.api.dto.response.Transaction;
import banking.biz.CurrenyConversionUtils;
import banking.dao.dataobject.TransactionDO;

public class TransactionMapper {
    public static Transaction transactionDoToDto(TransactionDO transactionDO){
        return new Transaction(transactionDO.getId(),
                transactionDO.getFrom(),
                transactionDO.getTo(),
                new Date(transactionDO.getTime()),
                CurrenyConversionUtils.getLocalCurrency(transactionDO.getAmount(), transactionDO.getCurrency()),
                transactionDO.getConversionRate(),
                transactionDO.getTransactionType(),
                transactionDO.getTotalBalance());
    }
}
