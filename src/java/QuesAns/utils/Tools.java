
package QuesAns.utils;

/**
 *
 * @author FeisEater
 */
public class Tools {
    public static final int elementsPerPage = 10;
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
    public static boolean stringOnlyWhitespace(String s)
    {
        s = s.replace(" ", "");
        return s.isEmpty();
    }
    public static final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyzåäö";
    public static final String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
    public static final String numbers = "0123456789";
    public static final String foreignLatinLetters = "áàâãéèëêóòôõíìïîúùüûýÿñÁÀÂÃÉÈËÊÓÒÔÕÍÌÏÎÚÙÜÛÝÑ";
    public static boolean stringHasOnlyDeterminedCharacters(String s, String accepted)
    {
        for (int i = 0; i < accepted.length(); i++)
            s = s.replace(""+accepted.charAt(i), "");
        return s.isEmpty();
    }
    public static char getInvalidChar(String s, String accepted)
    {
        for (int i = 0; i < accepted.length(); i++)
            s = s.replace(""+accepted.charAt(i), "");
        return s.charAt(0);
    }
    public static boolean stringHasUpperCaseCharacters(String s)
    {
        String t = s.toLowerCase();
        return !t.equals(s);
    }
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
