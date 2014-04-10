
package QuesAns.Models;

import java.sql.Timestamp;

/**
 *
 * @author Pavel
 */
public class Answer {
    private int id;
    private String body;
    private int rating;
    private int flags;
    private boolean askerApproved;
    private Timestamp answered;
    private Timestamp lastEdited;
    
    public Answer(int i, String b, int r, int f, boolean appr, Timestamp a, Timestamp l)
    {
        id = i;
        body = b;
        rating = r;
        flags = f;
        askerApproved = appr;
        answered = a;
        lastEdited = l;
    }
}
