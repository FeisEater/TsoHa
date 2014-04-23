
package QuesAns.utils;

/**
 *
 * @author FeisEater
 */
public class Info {
    public static final String quesSuccess = "Question asked succesfully. Here's the question page.";
    public static String loginSuccess(String name)
    {
        return "Logged in as " + name;
    }
    public static final String logoutSuccess = "Thanks for using QuesAns!";
    public static String registerSuccess(String username)
    {
        return username + ", welcome to QuesAns!";
    }
    public static final String answerSuccess = "Answer posted succesfully.";
    public static final String appendSuccess = "Answer updated succesfully.";
    public static String nameChanged(String username)
    {
        return "Username changed to " + username;
    }
    public static final String passwordChanged = "Password changed succesfully.";
    public static String emailChanged(String email)
    {
        return "Email changed to " + email;
    }
    public static final String avatarChanged = "Avatar changed succesfully.";
}
