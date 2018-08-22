package banking.mappers;

import banking.api.dto.response.Balance;
import banking.biz.CurrenyConversionUtils;
import banking.dao.dataobject.BalanceDO;

import static banking.biz.Constants.GLOBAL_CURRENCY;

public class BalanceMapper {
    public static Balance balanceDoToDto(BalanceDO balanceDO, String localCurrency) {
        return new Balance(
                CurrenyConversionUtils.getLocalCurrency(balanceDO.getAmount(), localCurrency),
                CurrenyConversionUtils.conversionRate(localCurrency, GLOBAL_CURRENCY));
    }
}
