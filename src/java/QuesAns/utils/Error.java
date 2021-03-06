
package QuesAns.utils;

/**
 * Error strings in a separate file to make localisation easier.
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
    public static final String invalidQuesToAnswer = "Question you tried to answer doesn't exist.";
    public static final String ansEmpty = "What's the point of an empty answer?";
    public static final String ansTooLong = "Answer is too long. Keep it below 65536 characters.";
    public static final String ansOnlyWhitespaces = "Answer only consists of whitespaces.";
    public static final String ansNotLoggedIn = "You must be logged in to post an answer.";
    public static final String appendNotLoggedIn = "You must be logged in to edit an answer.";
    public static final String invalidAnswer = "Answer you tried to edit doesn't exist.";
    public static final String appendNotYourAnswer = "This is not your answer to edit.";
    public static final String appendEmpty = "New part of the answer is empty.";
    public static final String appendTooLong = "New part of the answer is too long. Keep it below 65536 characters.";
    public static final String appendOnlyWhitespace = "New part of the answer consists only of whitespace.";
    public static final String settingsNotLoggedIn = "You must be logged in to change account settings.";
    public static final String avatarTooBig = "Avatar's file size is too big. It must be below 256 kilobytes.";
    public static final String avatarNotPicture = "Avatar was not recognized as a picture file. Supported files are .jpg, .gif, .png and .bmp";
    public static final String rateInvalidId = "Answer you tried to rate doesn't exist.";
    public static final String invalidUrl = "Something is wrong with the url.";
    public static final String flagInvalidQues = "Question you tried to flag doesn't exist.";
    public static final String flagInvalidAns = "Answer you tried to flag doesn't exist.";
    public static final String flagNotLoggedIn = "You must be logged in to flag something.";
    public static final String modPage = "You are required to have a moderator status to visit this page.";
    public static final String modCantRemove = "You are required to have a moderator status to perform this action.";
    public static final String modNoUser = "The user you tried to ban doesn't exist.";
    public static final String modNoQues = "The question you tried to remove doesn't exist.";
    public static final String modNoAns = "The answer you tried to remove doesn't exist.";
    public static final String modNoTag = "The tag you tried to remove doesn't exist.";
    public static final String accNotLoggedIn = "You have to be logged in to visit this page.";
}
