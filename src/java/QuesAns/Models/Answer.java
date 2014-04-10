
package QuesAns.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private User answerer;
    
    public Answer(int i, String b, int r, int f, boolean appr, Timestamp a, Timestamp l, User u)
    {
        id = i;
        body = b;
        rating = r;
        flags = f;
        askerApproved = appr;
        answered = a;
        lastEdited = l;
        answerer = u;
    }
    public User getAnswerer()
    {
        return answerer;
    }
    public String getBody()
    {
        return body;
    }
    public int getRating()
    {
        return rating;
    }
    public static Answer retrieveAnswerFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("a_id");
        String b = result.getString("body");
        int r = result.getInt("rating");
        int f = result.getInt("flags");
        boolean appr = result.getBoolean("approvedbyasker");
        Timestamp a = result.getTimestamp("answered");
        Timestamp l = result.getTimestamp("lastedited");
        int answererID = result.getInt("r_id");
        User u = User.getByID(answererID);
        return new Answer(i,b,r,f,appr,a,l,u);
    }
}
