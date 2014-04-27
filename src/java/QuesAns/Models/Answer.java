
package QuesAns.Models;

import QuesAns.utils.Tools;
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

    private static final String sql_undoRate =
            "DELETE FROM ratedflaggedanswers "
            + "WHERE r_id = ? and a_id = ? and flagged = false";
/**
 * If rated anonymously, removes some anonymous rating.
 */
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

    private static final String sql_flaggedAnswers =
            "select a.a_id, body, approvedbyasker, answered, lastedited, r_id, q_id, fa.f "
            + "from answers as a, (select a_id, count(*) as f from ratedflaggedanswers where flagged = true group by a_id) as fa "
            + "where a.a_id = fa.a_id";

    private static final String sql_getUnflaggedAnswers =
            "select *, 0 as f from answers a "
            + "where a.a_id not in (select a_id from ratedflaggedanswers where flagged = true)";
/**
 * Joins two subqueries, list of flagged and unflagged answers.
 */
    private static String sql_allAnswersByFlags =
            "SELECT x.a_id, x.body, x.approvedbyasker, x.answered, x.lastedited, x.r_id, x.q_id FROM (" + 
            sql_flaggedAnswers + " UNION " + sql_getUnflaggedAnswers + 
            " ORDER BY f DESC) AS x limit ? offset ?";

    private static final String sql_removeFromDB =
            "DELETE FROM answers WHERE a_id = ?";
    
    private static final String sql_countAnswers =
            "SELECT count(*) from answers";
    
    private static final String sql_getRatedAnswers
            = "select a.a_id, body, approvedbyasker, answered, lastedited, a.r_id, q_id, fa.f "
            + "from answers as a, (select a_id, sum(rated) as f from ratedflaggedanswers where flagged = false group by a_id) as fa "
            + "where a.a_id = fa.a_id";

    private static final String sql_getUnratedAnswers =
            "select *, 0 as f from answers as a "
            + "where a.a_id not in (select a_id from ratedflaggedanswers where flagged = false)";
/**
 * Joins two subqueries, rated and unrated answers.
 */
    private static final String sql_getQuestionsAnswers =
            "SELECT row_number() over () as row, x.a_id, x.body, x.approvedbyasker, x.answered, x.lastedited, x.r_id, x.q_id FROM (" + 
            sql_getRatedAnswers + " UNION " + sql_getUnratedAnswers + " ORDER BY f DESC) AS x WHERE x.q_id = ?";

    private static final String sql_getPage =
            "SELECT y.row from (" + sql_getQuestionsAnswers + ") as y where y.a_id = ?";

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
/**
 * Shortened body that fits in search results.
 * @return answer body up to 64 characters.
 */
    public String getShortBody()
    {
        if (body.length() <= 64)
            return body.replace("<br>", " ");
        return body.substring(0, 64).replace("<br>", " ");
    }
/**
 * Counts rating for the answer.
 * @return answer's rating.
 */
    public int getRating()
    {
        QAModel.prepareSQL(sql_countRating, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
/**
 * Counts flags for the answer.
 * @return answer's flag count.
 */
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
 * Adds an answer to the database.
 * @param owner User that made the answer.
 * @param q Question, which this answers.
 */
    public void addToDatabase(User owner, Question q)
    {
        body = Tools.formatHTMLsafe(body);
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
                + "<br>" + Tools.formatHTMLsafe(s);
        body += s;
        QAModel.prepareSQL(sql_append, body, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Changes answers rating.
 * @param rater User that rated the answer. Can be null.
 * @param up if true, rates answer up. Otherwise rate down.
 */
    public void getRated(User rater, boolean up)
    {
        Integer rateId = (rater == null) ? null : rater.getID();
        QAModel.prepareSQL(sql_rate, rateId, id, up ? 1 : -1);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Undoes rating for the answer
 * @param rater User that rated the answer. Can be null.
 * @param up If rater is null, checks if rating was up or down.
 */
    public void undoRate(User rater, boolean up)
    {
        if (rater == null)
            QAModel.prepareSQL(sql_undoAnonRate, id, up ? 1 : -1);
        else
            QAModel.prepareSQL(sql_undoRate, rater.getID(), id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Changes existing rater.
 * @param rater User that rated the answer. Can be null.
 * @param up Rating's new value.
 */
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
/**
 * Gets answer list limited to fit a page. Answers are sorted by flag count
 * @param page the number of the page, for which the sublist of answers is to be retrieved.
 * @return sublist of all answers.
 */
    public static List<Answer> getAnswersSortedByFlags(int page)
    {
        QAModel.prepareSQL(sql_allAnswersByFlags, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
        List result = QAModel.retrieveObjectList(new Answer());
        QAModel.closeComponents();
        return result;
    }
/**
 * Removes answer from database.
 */
    public void removeFromDatabase()
    {
        QAModel.prepareSQL(sql_removeFromDB, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Counts all answers.
 * @return number of answers in the database.
 */
    public static int countAnswers()
    {
        QAModel.prepareSQL(sql_countAnswers);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
/**
 * Finds in which page the answer is located.
 * @return page at which answer is located.
 */
    public int getPage()
    {
        QAModel.prepareSQL(sql_getPage, question.getID(), id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        result = ((int)Math.floor((result-1)/Tools.elementsPerPage) + 1);
        return result;
    }
/**
 * Forms an answer object from query results.
 * @param result query result.
 * @throws SQLException 
 */
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
