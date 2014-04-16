
package QuesAns.Models;

import QuesAns.DataBase.QAConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Model class for answers database table.
 * @author Pavel
 */
public class Answer implements Model {
    private int id;
    private String body;
    private int rating;
    private int flags;
    private boolean askerApproved;
    private Timestamp answered;
    private Timestamp lastEdited;
    private User answerer;
    private Question question;
    
    private static final String sql_getByID =
            "SELECT * from answers where a_id = ?";

    private static final String sql_addToDB =
            "INSERT INTO answers(body, answered, lastedited, r_id, q_id) "
            + "VALUES(?,LOCALTIMESTAMP,LOCALTIMESTAMP,?,?) RETURNING a_id, answered";

    private static final String sql_append =
            "UPDATE answers SET body = ?, lastedited = LOCALTIMESTAMP where a_id = ?";
    
    private static final String sql_rate =
            "UPDATE answers SET rating = ? where a_id = ?";

    private static final String sql_addFlag =
            "UPDATE answers SET flags = ? where a_id = ?";
    
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
    public Question getQuestion()
    {
        return question;
    }
    public String getBody()
    {
        return body;
    }
    public int getRating()
    {
        return rating;
    }
    public int getID()
    {
        return id;
    }
/**
 * Formats string in a way that html-code won't confuse it as a set of html-elements.
 * @param s String to be formatted.
 * @return Formatted version of the string.
 */
    public String reformatString(String s)
    {
        s = s.replace("&", "&amp");
        s = s.replace("<", "&lt");
        s = s.replace(">", "&gt");
        s = s.replace("\n", "<br>");
        s = s.replace(" ", "&nbsp");
        return s;
    }
/**
 * Adds an answer to the database.
 * @param owner User that made the answer.
 * @param q Question, which this answers.
 */
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
/**
 * Appends stuff to the answer.
 * @param s new information.
 */
    public void appendAnswer(String s)
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            s = "<br><br> Update " + ts + ":<br>" + reformatString(s);
            ps = c.prepareStatement(sql_append);
            ps.setString(1, body + s);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
    }
/**
 * Changes answers rating.
 * @param up if true, rates answer up. Otherwise rate down.
 */
    public void rate(boolean up)
    {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_rate);
            ps.setInt(1, rating + (up ? 1 : -1));
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(null, ps, c);
        }
    }
/**
 * Adds answer's flag count by one.
 */
    public void addFlag()
    {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_addFlag);
            ps.setInt(1, flags + 1);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(null, ps, c);
        }
    }
/**
 * Retrieves specific answer by its ID.
 * @param id Specified ID.
 * @return answer object.
 */
    public static Answer getByID(int id)
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getByID);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            Answer a = null;
            if (result.next())
                a = retrieveAnswerFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return a;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;
    }
/**
 * Forms an answer object based by query results.
 * @param result ResultSet object.
 * @return Answer object.
 * @throws SQLException 
 */
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

    public Model getObjectFromResults(ResultSet result) throws SQLException {
        return retrieveAnswerFromResults(result);
    }
}
