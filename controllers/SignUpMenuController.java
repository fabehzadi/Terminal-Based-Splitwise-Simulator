package controllers;
/*
Explanation:
- This is a controller class for the sign-up menu Controller.
- This class will be used to implement functions that do sign up menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.SignUpMenuCommands;
import models.enums.Menu;


import java.util.regex.Matcher;

public class SignUpMenuController {
    public Result signUp(String username, String password, String email, String name) {
        Matcher matcher;
        if ((matcher = SignUpMenuCommands.username.getMatcher(username)) == null) {
            return new Result(false, "username format is invalid!");
        } else if (usernameIsDuplicate(username)) {
            return new Result(false, "this username is already taken!");
        } else if ((matcher = SignUpMenuCommands.password.getMatcher(password)) == null) {
            return new Result(false, "password format is invalid!");
        }
        Matcher newMatcher;
        if (((matcher = SignUpMenuCommands.email.getMatcher(email)) == null) ||
                ((newMatcher = SignUpMenuCommands.username.getMatcher(matcher.group(1))) == null) ||
                matcher.group(2).length() < 3 ||
                matcher.group(2).length() > 7) {
            return new Result(false, "email format is invalid!");
        } else if ((matcher = SignUpMenuCommands.name.getMatcher(name)) == null) {
            return new Result(false, "name format is invalid!");
        }
        //if all parts are valid, we add this user to our users
        App.users.add(new User(username, password, email, name));
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "user registered successfully.you are now in login menu!");
    }

    public Result navigateToLoginMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "you are now in login menu!");
    }

    private boolean usernameIsDuplicate(String username) {
        for (var user : App.users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command!");
    }
}
