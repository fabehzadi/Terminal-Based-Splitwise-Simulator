package models;

/*
Explanation:
- User is definitely an object in our app.
- put the information that you need to store about the user here.
- you can put some functions here to manage the user data too.
 */

import models.enums.Currency;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private  String password;
    private final String email;
    private final String name;
    public ArrayList<Integer> groupIds = new ArrayList<>();
    public HashMap<User, Double> balances = new HashMap<>();
    private Currency currency = Currency.GTC;
    public User(String username, String password, String email, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void changeCurrency(Currency newCurrency) {
        this.currency = newCurrency;
    }
    public Currency getCurrency() {
        return currency;
    }
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
