package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Currency;
import models.enums.ProfileMenuCommands;
import models.enums.SignUpMenuCommands;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;

public class ProfileMenuController {
    public Result showInfo() {
        String message = "username : " + App.getCurrentUser().getUsername() + "\n" +
                "password : " + App.getCurrentUser().getPassword() + "\n" +
                "currency : " + App.getCurrentUser().getCurrency().getCurrencyName() + "\n" +
                "email : " + App.getCurrentUser().getEmail() + "\n" +
                "name : " + App.getCurrentUser().getName();
        return new Result(true, message);
    }

    public Result changeCurrency(String newCurrency) {
        Matcher matcher;
        if ((matcher = ProfileMenuCommands.ValidCurrency.getMatcher(newCurrency)) == null) {
            return new Result(false, "currency format is invalid!");
        }
        Currency currency = Currency.valueOf(newCurrency.trim());
        App.getCurrentUser().setCurrency(currency);
        return new Result(true, "your currency changed to " +
                currency.getCurrencyName() +
                " successfully!");
    }

    public Result changePassword(String oldPassword, String newPassword) {
        Matcher matcher;
        if(!oldPassword.equals(App.getCurrentUser().getPassword())) {
            return new Result(false, "password incorrect!");
        } else if(newPassword.equals(App.getCurrentUser().getPassword())) {
            return new Result(false, "please enter a new password!");
        } else if(SignUpMenuCommands.password.getMatcher(newPassword) == null) {
            return new Result(false, "new password format is invalid!");
        }
        App.getCurrentUser().setPassword(newPassword);
        return new Result(true, "your password changed successfully!");
    }

    public Result changeUserName(String newUserName) {
        Matcher matcher;
        matcher = SignUpMenuCommands.username.getMatcher(newUserName);
        if (matcher == null) {
            return new Result(false, "new username format is invalid!");
        } else if (newUserName.equals(App.getCurrentUser().getUsername())) {
            return new Result(false, "please enter a new username!");
        }
        for (User user : App.users) {
            if (user.getUsername().equals(newUserName)) {
                return new Result(false, "this username is already taken!");
            }
        }
        App.getCurrentUser().setUsername(newUserName);
        return new Result(true, "your username changed to " + newUserName + " successfully!");
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command!");
    }
}
