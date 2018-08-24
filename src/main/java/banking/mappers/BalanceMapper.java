package banking.mappers;

import banking.api.dto.response.Balance;
import banking.common.CurrenyConversionUtils;
import banking.dao.dataobject.BalanceDO;

import static banking.common.Constants.GLOBAL_CURRENCY;

public class BalanceMapper {
    public static Balance balanceDoToDto(BalanceDO balanceDO, String localCurrency) {
        return new Balance(balanceDO.getAmount(),
                    CurrenyConversionUtils.conversionRate(localCurrency, GLOBAL_CURRENCY),
                    localCurrency
                );
    }
}
