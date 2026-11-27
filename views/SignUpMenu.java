package views;

/*
Explanation:
- This is a view class for the SignUpMenu.
- This class should use to check inputs and print outputs for the SignUpMenu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.SignUpMenuController;
import models.App;
import models.enums.Menu;
import models.enums.ProfileMenuCommands;
import models.enums.SignUpMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignUpMenu implements AppMenu {
    private static final SignUpMenuController signUpController = new SignUpMenuController();

    @Override
    public void checkCommand(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = SignUpMenuCommands.Register.getMatcher(input)) != null) {
            System.out.println(signUpController.signUp
                    (matcher.group(1),
                            matcher.group(2),
                            matcher.group(3),
                            matcher.group(4)
                    ).message());
        } else if ((matcher = SignUpMenuCommands.goToLoginMenu.getMatcher(input)) != null) {
            System.out.println(signUpController.navigateToLoginMenu().message());
        } else if ((matcher = SignUpMenuCommands.Exit.getMatcher(input)) != null) {
            App.setCurrentMenu(Menu.ExitMenu);
        }  else {
            System.out.println(input);
            System.out.println(signUpController.invalidCommand().message());
        }
    }
}
