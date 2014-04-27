
package QuesAns.utils;

/**
 * Notification strings in a separate file to make localisation easier.
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
    public static String modUserBanned(String user)
    {
        return "User " + user + " banned succesfully.";
    }
    public static String modQuesRemoved(String ques)
    {
        return "Question '" + ques + "' removed succesfully.";
    }
    public static String modAnsRemoved(String ans)
    {
        return "Answer '" + ans + "' removed succesfully.";
    }
    public static String modTagRemoved(String tag)
    {
        return "Tag " + tag + " removed succesfully.";
    }
    public static final String flagQuestion = "You have notified that this question is inappropriate. Thank you for your concern.";
    public static final String flagAnswer = "You have notified that this answer is inappropriate. Thank you for your concern.";
    public static final String flagQuestionUndo = "Your question flag was removed.";
    public static final String flagAnswerUndo = "You answer flag was removed.";
    public static String rate(boolean up)
    {
        return "The answer was rated " + (up ? "up." : "down.");
    }
    public static final String undoRate = "Your rating was undone.";

}
