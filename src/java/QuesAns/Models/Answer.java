
package QuesAns.Models;

import QuesAns.DataBase.QAConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private Question question;
    
    private static final String sql_addToDB =
            "INSERT INTO answers(body, answered, lastedited, r_id, q_id) "
            + "VALUES(?,LOCALTIMESTAMP,LOCALTIMESTAMP,?,?) RETURNING a_id, answered";

    private static final String sql_append =
            "UPDATE answers SET body = ?, lastedited = ? where a_id = ?";
    
    public Answer(String answer)
    {
        body = answer;
    }
    public Answer(int i, String b, int r, int f, boolean appr, Timestamp a, Timestamp l, User u, Question q)
    {
        this(b);
        id = i;
        rating = r;
        flags = f;
        askerApproved = appr;
        answered = a;
        lastEdited = l;
        answerer = u;
        question = q;
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
    public String reformatString(String s)
    {
        s = s.replace("&", "&amp");
        s = s.replace("<", "&lt");
        s = s.replace(">", "&gt");
        s = s.replace("\n", "<br>");
        s = s.replace(" ", "&nbsp");
        return s;
    }
    public void addToDatabase(User owner, Question q)
    {
        body = reformatString(body);
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_addToDB);
            ps.setString(1, body);
            ps.setInt(2, owner.getID());
            ps.setInt(3, q.getID());
            result = ps.executeQuery();
            result.next();
            id = result.getInt(1);
            answered = result.getTimestamp(2);
            lastEdited = result.getTimestamp(2);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
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
        int questionID = result.getInt("q_id");
        Question q = Question.getByID(questionID);
        return new Answer(i,b,r,f,appr,a,l,u,q);
    }
}
