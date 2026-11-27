package controllers;

import models.*;
import models.enums.Currency;
import models.enums.DashboardCommands;
import views.Dashboard;

import java.util.*;
import java.util.regex.Matcher;


/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
public class DashboardController {
    public Result createGroup(String groupName, String groupType) {
        Matcher matcher;
        if ((matcher = DashboardCommands.GroupName.getMatcher(groupName)) == null) {
            return new Result(false, "group name format is invalid!");
        } else if ((matcher = DashboardCommands.GroupTypes.getMatcher(groupType)) == null) {
            return new Result(false, "group type is invalid!");
        }
        int newGroupId = App.groups.size() + 1;
        App.groups.add(new Group(groupName, groupType, newGroupId));
        App.groups.get(newGroupId - 1).members.add(App.getCurrentUser());
        App.getCurrentUser().groupIds.add(newGroupId);
        return new Result(true, "group created successfully!");
    }

    public Result addUserToGroup(String userName, String email, String stringOfGroupId) {
        User newUser = App.getUserByUsername(userName);
        //if we can't convert stringOfGroupId to Integer, we assign a negative number to group id
        //and when we check it in our validation of group id, we show the error of invalid group
        int groupId = isStringConvertibleToInteger(stringOfGroupId) ? Integer.parseInt(stringOfGroupId) : -8;
        if (newUser == null) {
            return new Result(false, "user not found!");
        }
        if (groupId <= 0 || groupId > App.groups.size()) {
            return new Result(false, "group not found!");
        }
        Group group = App.groups.get(groupId - 1);
        if (group.members.contains(newUser)) {
            return new Result(false, "user already in the group!");
        } else if (!newUser.getEmail().equals(email)) {
            return new Result(false, "the email provided does not match the username!");
        } else if (group.members.get(0) != App.getCurrentUser()) {
            return new Result(false, "only the group creator can add users!");
        }
        App.groups.get(groupId - 1).members.add(newUser);
        newUser.groupIds.add(groupId);
        return new Result(true, "user added to the group successfully!");
    }

    private static boolean isStringConvertibleToInteger(String input) {
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Result ShowGroups() {
        StringBuilder groups = new StringBuilder();
        for (int Id : App.getCurrentUser().groupIds) {
            Group group = App.groups.get(Id - 1);
            groups.append("group name : ").
                    append(group.getName()).
                    append("\n").append("id : ").
                    append(group.getGroupId()).append("\n").
                    append("type : ").append(group.getType()).append("\n").
                    append("creator : ").append(group.members.get(0).getName()).
                    append("\n").append("members :\n");
            for (User member : group.members) {
                groups.append(member.getName()).append("\n");
            }
                groups.append("--------------------\n");
        }
        if(groups.isEmpty()) {
            groups.append("\n");
        }
        return new Result(true, groups.toString());
    }

    public Result addExpense(String stringOfGroupId,
                             String equality,
                             String stringOfTotalExpense,
                             List<String> strings) {
        int groupId = canConvertToInteger(stringOfGroupId) ? Integer.parseInt(stringOfGroupId) : -8;
        int totalExpense = canConvertToInteger(stringOfTotalExpense) ? Integer.parseInt(stringOfTotalExpense) : -8;
        boolean equally = equality.equals("equally");
        if (!App.getCurrentUser().groupIds.contains(groupId)) {
            return new Result(false, "group not found!");
        }
        if (equally) {
            return checkExpenseEqually(groupId, totalExpense, strings);
        } else {
            List<String> newUsernames = new ArrayList<>();
            for (String username : strings) {
                newUsernames.add(DashboardCommands.UsernameAndExpense.getMatcher(username).group(1));
            }
            return checkExpenseUnequally(groupId, totalExpense, strings, newUsernames);
        }
    }

    public Result showBalance(String userName) {
        User user = App.getUserByUsername(userName);
        if (user == null) {
            return new Result(false, "user not found!");
        }
        if(!App.getCurrentUser().balances.containsKey(user)) {
            return new Result(true, "you are settled with " + userName);
        }
        List<String> commonGroups = new ArrayList<>();
        for (int groupId : App.getCurrentUser().groupIds) {
            Group group = App.groups.get(groupId - 1);
            if (group.members.contains(user)) {
                commonGroups.add(group.getName());
            }
        }
        StringBuilder groups = new StringBuilder();
        for (int i = 0; i < commonGroups.size(); i++) {
            if(i == commonGroups.size() - 1) {
                groups.append(commonGroups.get(i));
            } else {
                groups.append(commonGroups.get(i)).append(", ");
            }
        }
        int price = 0;
        if (!App.getCurrentUser().getCurrency().equals(Currency.GTC)) {
            price = (int) Currency.GTC.exchangeTo(App.getCurrentUser().getCurrency(), App.getCurrentUser().balances.get(user));
        } else {
            price = App.getCurrentUser().balances.get(user).intValue();
        }
        String message = "";
        if (App.getCurrentUser().balances.get(user) < 0) {
            message = "you owe " +
                    userName + " " +
                    -1 * price + " " +
                    App.getCurrentUser().getCurrency().getCurrencyName() +
                    " in " + groups.toString() +
                    "!";
        } else if (App.getCurrentUser().balances.get(user) > 0) {
            message = userName +
                    " owes you " + price + " " +
                    App.getCurrentUser().getCurrency().getCurrencyName() +
                    " in " + groups.toString() +
                    "!";
        } else {
            message = "you are settled with " + userName;
        }
        return new Result(true, message);
    }

    public Result settleUp(String userName, String inputMoney) {
        User user = App.getUserByUsername(userName);
        int inputMoneyInt = canConvertToInteger(inputMoney) ? Integer.parseInt(inputMoney) : -8;
        if (user == null) {
            return new Result(false, "user not found!");
        } else if (inputMoneyInt < 0) {
            return new Result(false, "input money format is invalid!");
        }
        boolean haveCommonGroups = false;
        for (int i : App.getCurrentUser().groupIds) {
            if (App.groups.get(i - 1).members.contains(user)) {
                haveCommonGroups = true;
            }
        }
        if (!haveCommonGroups) {
            return new Result(false,"you are settled with " + user.getUsername() + " now!");
        }
        Currency currency = App.getCurrentUser().getCurrency();
        if (!currency.equals(Currency.GTC)) {
            inputMoneyInt = (int) currency.convertToGtc(inputMoneyInt);
        }
        addBalanceForUsers(App.getCurrentUser(), user, inputMoneyInt);
        addBalanceForUsers(user, App.getCurrentUser(), -1*inputMoneyInt);
        int price = 0;
        if (!currency.equals(Currency.GTC)) {
            price = (int) currency.exchangeTo(currency, App.getCurrentUser().balances.get(user));
        } else {
            price = App.getCurrentUser().balances.get(user).intValue();
        }
        String message = "";
        if (price < 0) {
            message = "you owe " +
                    user.getUsername() +
                    " " + -1 * price + " " +
                    currency.getCurrencyName() + " now!";
        } else if (App.getCurrentUser().balances.get(user) > 0) {
            message = user.getUsername() + " owes you " +
                    price + " " +
                    currency.getCurrencyName() + " now!";
        } else {
            message = "you are settled with " + user.getUsername() + " now!";
        }
        return new Result(true, message);
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command!");
    }

    private static boolean canConvertToInteger(String input) {
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Result checkExpenseEqually(int groupId, int totalExpense, List<String> userNames) {
        List<User> users = new ArrayList<>();
        String usersAreNotInGroup = "";
        usersAreNotInGroup = areUsersInGroup(groupId, userNames);
        if (!usersAreNotInGroup.equals("")) {
            return new Result(false, usersAreNotInGroup);
        }
        users = makeUsernameList(userNames);
        if (totalExpense < 0) {
            return new Result(false, "expense format is invalid!");
        }
        //storing all balances in GTC
        Currency currentCurrency = App.getCurrentUser().getCurrency();
        double totalExpense2 = 0;
        if (!currentCurrency.equals(Currency.GTC)) {
            totalExpense2 = currentCurrency.convertToGtc(totalExpense);
        } else {
            totalExpense2 = totalExpense;
        }
        HashMap<User, Double> userShares = new HashMap<>();
        for (User user : users) {
            userShares.put(user, (double) ((int) totalExpense2 / users.size()));
        }
        Expense newExpense = new Expense(groupId,
                App.getCurrentUser(),
                App.getCurrentUser().getCurrency(),
                totalExpense2,
                true,
                userShares);
        return addExpenseAndgetResult(groupId, userShares, newExpense, userShares.keySet());
    }

    private static Result addExpenseAndgetResult(int groupId,
                                                 HashMap<User, Double> userShares,
                                                 Expense newExpense,
                                                 Set<User> users2) {
        App.expenses.add(newExpense);
        App.groups.get(groupId - 1).expenses.add(newExpense);

        for (User user : users2) {
            if (!user.getUsername().equals(App.getCurrentUser().getUsername())) {
                addBalanceForUsers(App.getCurrentUser(), user, userShares.get(user));
                addBalanceForUsers(user, App.getCurrentUser(), -1 * userShares.get(user));
            }
        }
        return new Result(true, "expense added successfully!");
    }

    private static Result checkExpenseUnequally(int groupId,
                                                int totalExpense,
                                                List<String> strings,
                                                List<String> userNames) {
        String usersAreNotInGroup = "";
        usersAreNotInGroup = areUsersInGroup(groupId, userNames);
        if (!usersAreNotInGroup.equals("")) {
            return new Result(false, usersAreNotInGroup);
        }
        HashMap<User, Integer> usersAndExpenses;
        usersAndExpenses = makeUsernameAndExpenseList(strings);
        if (totalExpense < 0) {
            return new Result(false, "expense format is invalid!");
        }
        for (User user : usersAndExpenses.keySet()) {
            if (usersAndExpenses.get(user) < 0) {
                return new Result(false, "expense format is invalid!");
            }
        }
        int sumExpenses = usersAndExpenses.values().stream().mapToInt(Integer::intValue).sum();
        if (sumExpenses != totalExpense) {
            return new Result(false, "the sum of individual costs does not equal the total cost!");
        }
        //store all balances in GTC
        Currency currentCurrency = App.getCurrentUser().getCurrency();
        double totalExpense2 = 0;
        HashMap<User, Double> newMap = new HashMap<>();
        if (!currentCurrency.equals(Currency.GTC)) {
            totalExpense2 = currentCurrency.convertToGtc(totalExpense);
            for (User user : usersAndExpenses.keySet()) {
                newMap.put(user,
                        currentCurrency.convertToGtc(usersAndExpenses.get(user)));
            }
        } else {
            totalExpense2 = totalExpense;
            for (User user : usersAndExpenses.keySet()) {
                newMap.put(user, Double.valueOf(usersAndExpenses.get(user)));
            }
        }
        Expense newExpense = new Expense(groupId,
                App.getCurrentUser(),
                App.getCurrentUser().getCurrency(),
                totalExpense2,
                false,
                newMap);

        return addExpenseAndgetResult(groupId, newMap, newExpense, usersAndExpenses.keySet());
    }

    private static List<User> makeUsernameList(List<String> strings) {
        List<User> users = new ArrayList<>();
        for (String name : strings) {
            users.add(App.getUserByUsername(name));
        }
        return users;
    }

    private static HashMap<User, Integer> makeUsernameAndExpenseList(List<String> strings) {
        HashMap<User, Integer> usersAndExpenses = new HashMap<>();
        Matcher matcher;
        for (String name : strings) {
            matcher = DashboardCommands.UsernameAndExpense.getMatcher(name);
            User user = App.getUserByUsername(matcher.group(1)) != null ? App.getUserByUsername(matcher.group(1)) : null;
            Integer expense = canConvertToInteger(matcher.group(2)) ? Integer.parseInt(matcher.group(2)) : -8;
            usersAndExpenses.put(user, expense);
        }
        return usersAndExpenses;
    }

    public static String areUsersInGroup(int groupId, List<String> usernames) {
        StringBuilder usersAreNotInGroup = new StringBuilder();
        List<String> AreNotInGroup = new ArrayList<>();
        for (String userName : usernames) {
            User user = App.getUserByUsername(userName);
            if (user == null ||
                    !App.groups.get(groupId - 1).members.contains(user)) {
                AreNotInGroup.add(userName);
            }
        }
        for (String userName : AreNotInGroup) {
            if (userName.equals(AreNotInGroup.get(AreNotInGroup.size() - 1))) {
                usersAreNotInGroup.append(userName).append(" not in group!");
            } else {
                usersAreNotInGroup.append(userName).append(" not in group!").append("\n");
            }
        }
        return usersAreNotInGroup.toString();
    }

    private static void addBalanceForUsers(User user1, User user2, double amountInGTC) {
        if (user1.balances.containsKey(user2)) {
            user1.balances.put(user2, user1.balances.get(user2) + amountInGTC);
        } else {
            user1.balances.put(user2, amountInGTC);
        }
    }

}