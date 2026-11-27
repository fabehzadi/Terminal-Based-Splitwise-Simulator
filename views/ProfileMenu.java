package views;
/*
Explanation: 
- This is a view class for profile menu.
- This class should use to check inputs and print outputs for profile menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.ProfileMenuController;
import models.App;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    private static final ProfileMenuController profileMenuController = new ProfileMenuController();

    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = ProfileMenuCommands.ShowInfo.getMatcher(input)) != null) {
            System.out.println(profileMenuController.showInfo().message());
        } else if ((matcher = ProfileMenuCommands.ChangeCurrency.getMatcher(input)) != null) {
            System.out.println(profileMenuController.changeCurrency(matcher.group(1)).message());
        } else if ((matcher = ProfileMenuCommands.ChangeUserName.getMatcher(input)) != null) {
            System.out.println(profileMenuController.changeUserName(matcher.group(1)).message());
        } else if ((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            System.out.println(profileMenuController.changePassword(matcher.group(1), matcher.group(2)).message());
        } else if ((matcher = ProfileMenuCommands.BackToDashboard.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.Dashboard);
            System.out.println("you are now in dashboard!");
        } else if ((matcher = ProfileMenuCommands.Exit.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.ExitMenu);
        } else {
            System.out.println(profileMenuController.invalidCommand().message());
        }
    }

}
