
package QuesAns.utils;

/**
 *
 * @author FeisEater
 */
public class Error {
    public static final String quesTitleEmpty = "Question title can't be empty.";
    public static final String quesTitleOnlyWhitespaces = "Question title consists only of whitespaces.";
    public static final String quesTitleTooLong = "Question title is too long. Keep it below 96 characters.";
    public static final String quesBodyTooLong = "Question body is too long, I'm afraid. Please try to keep it below 65536 characters.";
    public static final String quesTooManyTags = "There are too many tags. Amount of tags must be below 1024.";
    public static String quesTagTooLong(String tag)
    {
        return "Tag " + tag + " was too long, tag can't be longer than 12 characters.";
    }
    public static String quesTagHasInvalidCharacter(String tag, char t)
    {
        return "Tag " + tag + " has inappropriate characters, such as '" + t + "'. "
                                + "Please use letters a-z, å, ä, ö, numbers 0-9 and character '_'.";
    }
    public static final String searchTooLong = "Search text was too long. Keep it below 1024 characters.";
    public static final String loginFail = "Log in failed. Check your username, email or password.";
    public static final String invalidQues = "Question you tried to access doesn't exist.";
    public static final String regNameTooShort = "Username should be at least 3 characters.";
    public static final String regNameTooLong = "Username is too long. Keep it below 16 characters.";
    public static final String regNameOnlyWhitespaces = "Username consists only of whitespaces.";
    public static String regNameHasInvalidCharacter(char n)
    {
        return "Username has inappropriate characters, such as '" + n + "'.";
    }
    public static final String regEmailTooShort = "Email should be at least 3 characters.";
    public static final String regEmailTooLong = "Email is too long. Keep it below 64 characters.";
    public static final String regEmailHasWhitespace = "Email can't contain whitespace.";
    public static final String regEmailMustHaveAt = "Email must contain @ character.";
    public static String regEmailHasInvalidCharacter(char e)
    {
        return "Email has inappropriate characters, such as '" + e + "'.";
    }
    public static final String regPasswordTooShort = "Password should have at least 7 characters.";
    public static final String regPasswordTooLong = "Password is too long. Keep it below 128 characters.";
    public static final String regPasswordMismatch = "Retyped password is mismatched.";
    public static final String regPasswordNoUpperCase = "Password must have upper case letters.";
    public static final String regPasswordNoLowerCase = "Password must have lower case letters.";
    public static final String regPasswordSpecialCharacter = "Password must have a number or a special character.";
}
