package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.DashboardController;
import controllers.SignUpMenuController;
import models.App;
import models.enums.DashboardCommands;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;
import models.enums.SignUpMenuCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Dashboard implements AppMenu {
    private static final DashboardController dashboardController = new DashboardController();

    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = DashboardCommands.CreateGroup.getMatcher(input)) != null) {
            System.out.println(dashboardController.createGroup(matcher.group(1), matcher.group(2)).message());
        } else if ((matcher = DashboardCommands.AddUserToGroup.getMatcher(input)) != null) {
            System.out.println(dashboardController.addUserToGroup(matcher.group(1),
                    matcher.group(2),
                    matcher.group(3)
            ).message());
        } else if ((matcher = DashboardCommands.AddUserToGroup.getMatcher(input)) != null) {
            System.out.println(dashboardController.addUserToGroup(matcher.group(1),
                    matcher.group(2),
                    matcher.group(3)).message());
        } else if ((matcher = DashboardCommands.ShowGroups.getMatcher(input)) != null) {
            System.out.print(dashboardController.ShowGroups().message());
        } else if ((matcher = DashboardCommands.AddExpense.getMatcher(input)) != null) {
            List<String> usernames = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(matcher.group("numberOfUser")); i++) {
                usernames.add(DashboardCommands.Username.getMatcher(scanner.nextLine()).group(1));
            }
            System.out.println(dashboardController.addExpense(matcher.group("groupId"),
                    matcher.group("equality"),
                    matcher.group("totalExpense"),
                    usernames
            ).message());
        } else if ((matcher = DashboardCommands.ShowBalance.getMatcher(input)) != null) {
            System.out.println(dashboardController.showBalance(matcher.group(1)).message());
        } else if ((matcher = DashboardCommands.SettleUp.getMatcher(input)) != null) {
            System.out.println(dashboardController.settleUp(matcher.group("username"),
                    matcher.group("inputMoney")).message());
        } else if ((matcher = DashboardCommands.GoToProfileMenu.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.ProfileMenu);
            System.out.println("you are now in profile menu!");
        } else if ((matcher = DashboardCommands.LogOut.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.LoginMenu);
            System.out.println("user logged out successfully.you are now in login menu!");
        } else if ((matcher = DashboardCommands.Exit.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.ExitMenu);
        } else {
            System.out.println(dashboardController.invalidCommand().message());
        }
    }
}
