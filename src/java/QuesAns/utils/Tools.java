
package QuesAns.utils;

/**
 * Auxiliary static methods used throughout the code.
 * @author FeisEater
 */
public class Tools {
/** Elements shown per page. */
    public static final int elementsPerPage = 10;
/**
 * Exception-proof string to int conversion. If exception occured, returns -1.
 * @param s string
 * @return integer
 */
    public static int stringToInt(String s)
    {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
        return i;
    }
/**
 * Checks if only consisted of whitespace.
 * @param s string
 * @return true if only whitespace.
 */
    public static boolean stringOnlyWhitespace(String s)
    {
        s = s.replace(" ", "");
        return s.isEmpty();
    }
    public static final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyzåäö";
    public static final String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
    public static final String numbers = "0123456789";
    public static final String foreignLatinLetters = "áàâãéèëêóòôõíìïîúùüûýÿñÁÀÂÃÉÈËÊÓÒÔÕÍÌÏÎÚÙÜÛÝÑ";
/**
 * Checks if string consists only of determined characters.
 * @param s string
 * @param accepted determined characters.
 * @return true if string consists only of determined characters.
 */
    public static boolean stringHasOnlyDeterminedCharacters(String s, String accepted)
    {
        for (int i = 0; i < accepted.length(); i++)
            s = s.replace(""+accepted.charAt(i), "");
        return s.isEmpty();
    }
/**
 * 
 * @param s string
 * @param accepted determined characters.
 * @return first character that was not determined in the string.
 */
    public static char getInvalidChar(String s, String accepted)
    {
        for (int i = 0; i < accepted.length(); i++)
            s = s.replace(""+accepted.charAt(i), "");
        return s.charAt(0);
    }
/**
 * 
 * @param s string
 * @return true if string contains an upper case character.
 */
    public static boolean stringHasUpperCaseCharacters(String s)
    {
        String t = s.toLowerCase();
        return !t.equals(s);
    }
/**
 * 
 * @param s string
 * @return true if string contains a lower case character.
 */
    public static boolean stringHasLowerCaseCharacters(String s)
    {
        String t = s.toUpperCase();
        return !t.equals(s);
    }
/**
 * Formats string in a way that html-code won't confuse it as a set of html-elements.
 * @param s String to be formatted.
 * @return Formatted version of the string.
 */
    public static String formatHTMLsafe(String s)
    {
        s = s.replace("&", "&amp");
        s = s.replace("<", "&lt");
        s = s.replace(">", "&gt");
        s = s.replace("\n", "<br>");
        s = s.replace(" ", "&nbsp");
        return s;
    }
}
