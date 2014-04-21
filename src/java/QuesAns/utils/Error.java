
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
        return "Tag " + tag + " used inappropriate characters, such as '" + t + "'. "
                                + "Please use letters a-z, å, ä, ö, numbers 0-9 and character '_'.";
    }
}
