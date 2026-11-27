package views;
/*
Explanation:
- This is a view class for the login menu.
- This class should use to check inputs and print outputs for the login menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.LoginMenuController;
import models.App;
import models.enums.LoginMenuCommands;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;
import models.enums.SignUpMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private static final LoginMenuController loginMenuController = new LoginMenuController();

    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.LoginCommand.getMatcher(input)) != null) {
            System.out.println(loginMenuController.login(matcher.group(1), matcher.group(2)).message());
        } else if ((matcher = LoginMenuCommands.forgetPassword.getMatcher(input)) != null) {
            System.out.println(loginMenuController.forgetPassword(matcher.group(1), matcher.group(2)).message());
        } else if ((matcher = LoginMenuCommands.backToSignUp.getMatcher(input)) != null) {
            System.out.println(loginMenuController.backToSignUp().message());
        } else if ((matcher = LoginMenuCommands.Exit.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.ExitMenu);
        } else {
            System.out.println(loginMenuController.invalidCommand().message());
        }
    }

}
