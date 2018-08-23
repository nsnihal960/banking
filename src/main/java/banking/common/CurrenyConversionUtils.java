package banking.common;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import banking.api.dto.response.Profile;
import banking.api.dto.response.exception.NotSupported;

public class CurrenyConversionUtils {
    private static Map<String, Double> exchange; //for USD
    private static Map<String, Locale> localeStore;

    static {
        exchange = new HashMap<>();
        exchange.put("USD", 1.0);
        exchange.put("INR", 64.0);
        exchange.put("GBP", 1.28);
        exchange.put("JPY", 110.50);
        exchange.put("AUD", 1.36);

        localeStore = new HashMap<>();
        localeStore.put("USD", Locale.US);
        localeStore.put("INR", new Locale("en", "IN"));
        localeStore.put("GBP", new Locale("en", "GB"));
        localeStore.put("JPY", Locale.JAPAN);
        localeStore.put("AUD", new Locale("en", "AU"));
    }

    public static Double getGlobalCurrencyAmount(Double amount, String currencyCode) {
        validateCurrency(currencyCode);
        return amount / exchange.get(currencyCode);
    }

    public static String getLocalCurrency(Double amount, String currencyCode) {
        validateCurrency(currencyCode);
        return formatCurrency(amount * exchange.get(currencyCode), currencyCode);
    }

    public static Double conversionRate(String fromCurrency, String toCurrency) {
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
        return exchange.get(toCurrency) / exchange.get(fromCurrency);
    }

    public static void validateCurrency(String currencyCode) {
        if (currencyCode == null || exchange.get(currencyCode.toUpperCase()) == null) {
            throw new NotSupported("Currency not supported yet!");
        }
    }

    public static String getFallBackCurrency(String currency, Profile user){
        if(currency == null){
            currency = user.currency;
        }
        CurrenyConversionUtils.validateCurrency(currency);
        return currency;
    }

    private static String formatCurrency(Double amount, String currency) {
        validateCurrency(currency);
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance(localeStore.get(currency.toUpperCase()));
        return numberFormatter.format(amount);
    }
}
