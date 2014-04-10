
package QuesAns.Models;

import java.sql.Timestamp;

/**
 *
 * @author Pavel
 */
public class Tag {
    private int id;
    private String tag;
    private Timestamp tagged;
    public Tag(int i, String tag, Timestamp t)
    {
        id = i;
        this.tag = tag;
        tagged = t;
    }
}
