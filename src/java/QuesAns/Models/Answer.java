
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
    
    public Answer() {}
    public Answer(String answer)
    {
        body = answer;
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
        QAModel.prepareSQL(sql_addToDB, body, owner.getID(), q.getID());
        id = QAModel.retrieveInt(1);
        answered = QAModel.retrieveTimestamp(2);
        lastEdited = QAModel.retrieveTimestamp(2);
        QAModel.closeComponents();
        askerApproved = false;
        rating = 0;
        flags = 0;
        answerer = owner;
        question = q;
    }
/**
 * Appends stuff to the answer.
 * @param s new information.
 */
    public void appendAnswer(String s)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        s = "<br><br> Update " + ts + ":<br>" + reformatString(s);
        body += s;
        QAModel.prepareSQL(sql_append, body, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Changes answers rating.
 * @param up if true, rates answer up. Otherwise rate down.
 */
    public void rate(boolean up)
    {
        QAModel.prepareSQL(sql_rate, up ? ++rating : --rating, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Adds answer's flag count by one.
 */
    public void addFlag()
    {
        QAModel.prepareSQL(sql_addFlag, ++flags, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Retrieves specific answer by its ID.
 * @param id Specified ID.
 * @return answer object.
 */
    public static Answer getByID(int id)
    {
        Answer a = new Answer();
        QAModel.prepareSQL(sql_getByID, id);
        if (!QAModel.retrieveSingleObject(a))
            a = null;
        QAModel.closeComponents();
        return a;
    }
    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("a_id");
        body = result.getString("body");
        rating = result.getInt("rating");
        flags = result.getInt("flags");
        askerApproved = result.getBoolean("approvedbyasker");
        answered = result.getTimestamp("answered");
        lastEdited = result.getTimestamp("lastedited");
        int answererID = result.getInt("r_id");
        answerer = User.getByID(answererID);
        int questionID = result.getInt("q_id");
        question = Question.getByID(questionID);
    }
    
    public Model newModel()
    {
        return new Answer();
    }
}
