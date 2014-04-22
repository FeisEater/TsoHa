
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
}
