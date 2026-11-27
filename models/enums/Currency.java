package models.enums;

/*
Explanation:
- We need to define a currency enum.
- currencies in out app are some constants that we need to define them in our code once and use them in our code.
- each currency has some data, put them here and use some methods to work with currencies so simply.
 */

public enum Currency {
    GTC(1, "GTC"),
    SUD(2, "SUD"),
    QTR(5, "QTR");
    private final double valueInGtc;
    private final String currencyName;
    Currency(double valueInGtc, String currencyName) {
        this.valueInGtc = 1 / valueInGtc;
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double convertToGtc(double amount) {
        return amount * valueInGtc;
    }
    public double exchangeTo(Currency currency, double amountInGtc) {
        return amountInGtc / currency.valueInGtc;
    }
}
