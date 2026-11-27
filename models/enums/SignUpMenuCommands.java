package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our sign-up menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum SignUpMenuCommands implements Command {
    Register("^\\s*register\\s+-u\\s+" +
            "(?<username>.*\\S)+\\s+-p\\s+" +
            "(?<password>.*\\S)+\\s+-e\\s+" +
            "(?<email>.*\\S)\\s+-n\\s+" +
            "(?<name>.*\\S)\\s*$"
    ),
    username("[a-zA-Z][a-zA-Z\\-_.\\d]{3,9}"),
    // Username: Starts with a letter, 4-10 chars
    password("(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[!@#$%^&*]+)[A-Za-z!@#$%^&*\\d]{6,12}"),
    // Password: 6-12 chars, with at least 1 lowercase, 1 uppercase, 1 digit, and 1 special char
    email("^[a-zA-Z][a-zA-Z\\-_.\\d]{3,9}@(?<domain>[a-z][a-z]*[.-]?[a-z]*[a-z])\\.(org|com|net|edu)$"),
    // Email: Valid format with specific TLDs
    name("[a-zA-Z]+(?:[-]?[a-zA-Z]+)*"),
    // Name: Letters, optional hyphens, spaces, or apostrophes
    goToLoginMenu("^\\s*go\\s+to\\s+login\\s+menu\\s*$"),
    Exit("^\\s*exit\\s*$");
    private final String pattern;

    SignUpMenuCommands(String pattern) {
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
