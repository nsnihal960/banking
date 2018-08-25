package banking.mappers;

import banking.api.dto.response.Balance;
import banking.common.CurrencyConversionUtils;
import banking.dao.dataobject.BalanceDO;

import static banking.common.Constants.GLOBAL_CURRENCY;

public class BalanceMapper {
    public static Balance balanceDoToDto(BalanceDO balanceDO, String localCurrency) {
        return new Balance(balanceDO.getAmount(),
                    CurrencyConversionUtils.conversionRate(localCurrency, GLOBAL_CURRENCY),
                    localCurrency
                );
    }
}
