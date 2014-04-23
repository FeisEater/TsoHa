
package QuesAns.utils;

/**
 *
 * @author FeisEater
 */
public class Tools {
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
}
