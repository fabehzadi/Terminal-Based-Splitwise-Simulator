package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;

public class LoginMenuController {

    public Result login(String username, String password) {
        User currentUser = findUsername(username);
        if (currentUser == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!currentUser.getPassword().equals(password)) {
            return new Result(false, "password is incorrect!");
        }
        //if username and password are correct, go to dashboard
        App.setCurrentUser(currentUser);
        App.setCurrentMenu(Menu.Dashboard);
        return new Result(true, "user logged in successfully.you are now in dashboard!");
    }

    public  Result forgetPassword(String username, String email) {
        User currentUser = findUsername(username);
        if (currentUser == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!currentUser.getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password : " + currentUser.getPassword());
    }

    public  Result backToSignUp() {
        App.setCurrentMenu(Menu.SignUpMenu);
        return new Result(true, "you are now in signup menu!");
    }

    private static User findUsername(String username) {
        for (User user : App.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public Result invalidCommand() {
        return new Result(false, "invalid command!");
    }
}
