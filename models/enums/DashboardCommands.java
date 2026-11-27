package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.enums.GroupType;

/*
Explanation:
- we have commands in our dashboard and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum DashboardCommands implements Command {
    CreateGroup("^\\s*create-group\\s+-n\\s+(?<name>.*\\S)\\s+-t\\s+(?<type>.*\\S)\\s*$"),
    GroupName("[a-zA-Z0-9!@#$%^&* ]{4,30}"),
    GroupTypes("^\\s*" + GroupType.home.getGroupType() + "|"
            + GroupType.other.getGroupType() + "|"
            + GroupType.trip.getGroupType() + "|"
            + GroupType.zob.getGroupType() + "\\s*$"),
    AddUserToGroup("^\\s*add-user\\s+-u\\s+" +
            "(?<username>.*\\S)\\s+-e\\s+(?<email>.*\\S)\\s+" +
            "-g\\s+(?<groupId>.*\\S)\\s*$"),
    ShowGroups("^\\s*show\\s+my\\s+groups\\s*$"),
    AddExpense("^\\s*add-expense\\s+-g\\s+(?<groupId>.*\\S)" +
            "\\s+-s\\s+(?<equality>equally|unequally)" +
            "\\s+-t\\s+(?<totalExpense>.*\\S)" +
            "\\s+-n\\s+(?<numberOfUser>.*\\S)\\s*$"),
    UsernameAndExpense("^\\s*(?<username>\\S+)\\s+(?<expense>.*\\S)\\s*$"),
    Username("^\\s*(?<username>.*\\S)\\s*$"),
    ShowBalance("^\\s*show\\s+balance\\s+-u\\s+(?<username>.*\\S)\\s*$"),
    SettleUp("^\\s*settle-up\\s+-u\\s+(?<username>.*\\S)\\s+-m\\s+(?<inputMoney>.*\\S)\\s*$"),
    GoToProfileMenu("^\\s*go\\s+to\\s+profile\\s+menu\\s*$"),
    LogOut("^\\s*logout\\s*$"),
    Exit("^\\s*exit\\s*$");


    private final String pattern;

    DashboardCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
