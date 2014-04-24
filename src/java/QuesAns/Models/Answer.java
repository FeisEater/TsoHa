
package QuesAns.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Model class for answers database table.
 * @author Pavel
 */
public class Answer implements Model {
    private int id;
    private String body;
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
            "INSERT INTO ratedflaggedanswers(r_id, a_id, flagged, rated) "
            + "VALUES(?,?,false,?)";
            //"UPDATE answers SET rating = ? where a_id = ?";

    private static final String sql_undoRate =
            "DELETE FROM ratedflaggedanswers "
            + "WHERE r_id = ? and a_id = ? and flagged = false";

    private static final String sql_undoAnonRate =
            "DELETE FROM ratedflaggedanswers "
            + "WHERE ctid IN (select ctid from ratedflaggedanswers "
            + "where r_id is null and a_id = ? and flagged = false and rated = ? limit 1)";

    private static final String sql_changeRate =
            "UPDATE ratedflaggedanswers SET rated = ? "
            + "WHERE ctid IN (select ctid from ratedflaggedanswers "
            + "where r_id = ? and a_id = ? and flagged = false limit 1)";
    
    private static final String sql_countRating =
            "SELECT sum(rated) from ratedflaggedanswers "
            + "WHERE a_id = ? and flagged = false";

    private static final String sql_countFlags =
            "SELECT count(*) from ratedflaggedanswers "
            + "WHERE a_id = ? and flagged = true";

    private static final String sql_allAnswersByFlags =
            "select a.a_id, body, approvedbyasker, answered, lastedited, r_id, q_id "
            + "from answers as a, (select a_id, count(*) as f from ratedflaggedanswers where flagged = true group by a_id) as fa "
            + "where a.a_id = fa.a_id order by f desc";
            //"SELECT * from answers order by flags desc";

    private static final String sql_getUnflaggedAnswers =
            "select * from answers a "
            + "where a.a_id not in (select a_id from ratedflaggedanswers where flagged = true)";

    private static final String sql_removeFromDB =
            "DELETE FROM answers WHERE a_id = ?";

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
    public String getShortBody()
    {
        if (body.length() <= 64)
            return body.replace("<br>", " ");
        return body.substring(0, 64).replace("<br>", " ");
    }
    public int getRating()
    {
        QAModel.prepareSQL(sql_countRating, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
    public int getFlags()
    {
        QAModel.prepareSQL(sql_countFlags, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
    public int getID()
    {
        return id;
    }
    public String getAnswered()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(answered);
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
        s = "<br><br> Update: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts)
                + "<br>" + reformatString(s);
        body += s;
        QAModel.prepareSQL(sql_append, body, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Changes answers rating.
 * @param up if true, rates answer up. Otherwise rate down.
 */
    public void getRated(User rater, boolean up)
    {
        Integer rateId = (rater == null) ? null : rater.getID();
        QAModel.prepareSQL(sql_rate, rateId, id, up ? 1 : -1);
        QAModel.executeUpdate();
        QAModel.closeComponents();
        System.out.println("rated " + (up ? "up" : "down"));
    }
    public void undoRate(User rater, boolean up)
    {
        if (rater == null)
            QAModel.prepareSQL(sql_undoAnonRate, id, up ? 1 : -1);
        else
            QAModel.prepareSQL(sql_undoRate, rater.getID(), id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
        System.out.println("removed rate " + (up ? "up" : "down"));
    }
    public void changeRate(User rater, boolean up)
    {
        Integer rateId = (rater == null) ? null : rater.getID();
        QAModel.prepareSQL(sql_changeRate, up ? 1 : -1, rateId, id);
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
    public static List<Answer> getAnswersSortedByFlags()
    {
        QAModel.prepareSQL(sql_allAnswersByFlags);
        List result = QAModel.retrieveObjectList(new Answer());
        QAModel.prepareSQL(sql_getUnflaggedAnswers);
        result.addAll(QAModel.retrieveObjectList(new Answer()));
        QAModel.closeComponents();
        return result;
    }
    public void removeFromDatabase()
    {
        QAModel.prepareSQL(sql_removeFromDB, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("a_id");
        body = result.getString("body");
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
    @Override
    public boolean equals(Object o)
    {
        Answer a = (Answer)o;
        return a.getID() == id;
    }
    
    @Override
    public int hashCode()
    {
        return id;
    }
    public String toString()
    {
        return ""+id;
    }
}
