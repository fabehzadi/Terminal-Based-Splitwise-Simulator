package models;

import models.enums.Menu;

import java.util.ArrayList;

/*
Explanation:
- In out app, we need somewhere to keep our general data like list of users and list of groups and logged-in user etc.
- This class is the place for that.
- Put your general data here and use them in your code.
- you should put some functions here to manage your data too.
 */
public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Group> groups = new ArrayList<>();
    public static ArrayList<Expense> expenses = new ArrayList<>();
    private static Menu currentMenu = Menu.SignUpMenu;
    private static User currentUser = null;

    public static  void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
