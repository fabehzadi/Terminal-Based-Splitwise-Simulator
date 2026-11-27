package models;
/*
Explanation:
- when we create an expense, we need to store some information about it.
- Expense is something that we need to make it an object.
- put those information here and use them in your code.
 */

import models.enums.Currency;

import java.util.HashMap;

public class Expense {
    private int groupId;
    private User payerUser;
    private Currency currency;
    private double totalAmount;
    private boolean isEquallySplit;
    private HashMap<User, Double> userShares;

    public Expense(int groupId,
                   User payerUser,
                   Currency currency,
                   double totalAmount,
                   boolean isEquallySplit,
                   HashMap<User, Double> userShares) {
        this.groupId = groupId;
        this.payerUser = payerUser;
        this.currency = currency;
        this.isEquallySplit = isEquallySplit;
        this.totalAmount = totalAmount;
        this.userShares = userShares;
    }
}
