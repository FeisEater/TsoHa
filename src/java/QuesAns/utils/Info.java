
package QuesAns.utils;

/**
 *
 * @author FeisEater
 */
public class Info {
    public static final String quesSuccess = "Question asked succesfully. Here's the question page.";
    public static String loginSuccess(String name)
    {
        return "Logged in as " + name + ".";
    }
    public static final String logoutSuccess = "Thanks for using QuesAns!";
    public static String registerSuccess(String username)
    {
        return username + ", welcome to QuesAns!";
    }
}
