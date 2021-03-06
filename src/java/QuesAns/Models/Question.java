
package QuesAns.Models;

import QuesAns.utils.Tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Model class for questions database table.
 * @author FeisEater
 */
public class Question implements Model {
    private int id;
    private String title;
    private String body;
    private Timestamp asked;
    private boolean askerBanned;
    private User asker;
    
    private static final String sql_getQuestions =
            "SELECT * from questions order by asked desc limit ? offset ?";

    private static final String sql_getFlaggedQuestions =
            "select q.q_id, title, body, q.r_id, askerbanned, asked, fa.f "
            + "from questions as q, (select q_id, count(*) as f from flaggedquestions group by q_id) as fa "
            + "where q.q_id = fa.q_id";
    
    private static final String sql_getUnflaggedQuestions =
            "select *, 0 as f from questions as q "
            + "where q.q_id not in (select q_id from flaggedquestions)";

    private static final String sql_getQuestionsByFlags =
            "SELECT x.q_id, x.title, x.body, x.r_id, x.askerbanned, x.asked FROM (" + 
            sql_getFlaggedQuestions + " UNION " + sql_getUnflaggedQuestions + 
            " ORDER BY f DESC) AS x limit ? offset ?";
    
    private static final String sql_getByID =
            "SELECT * from questions where q_id = ?";

    private static final String sql_addToDB =
            "INSERT INTO questions(title, body, r_id, asked) "
            + "VALUES(?,?,?,LOCALTIMESTAMP) RETURNING q_id, asked";

    private static final String sql_removeFromDB =
            "DELETE FROM questions WHERE q_id = ?";

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
            sql_getRatedAnswers + " UNION " + sql_getUnratedAnswers + " ORDER BY f DESC) AS x WHERE x.q_id = ? limit ? offset ?";

    private static final String sql_getQuestionsTags =
            "SELECT t.t_id, t.tag, t.firsttagged from tagstoquestions as tq, tags as t where q_id = ? and tq.t_id = t.t_id";

    private static final String sql_countAnswers =
            "SELECT count(*) from answers where q_id = ?";

    private static final String sql_countFlags =
            "SELECT count(*) from flaggedquestions WHERE q_id = ?";

/**
 * Forms an sql statement for finding questions that have the required set of tags
 * @param tagcount amount of tags
 * @return sql statement
 */
    private static String sql_getQuestionByTags(int tagcount, boolean count)
    {
        String sql = "SELECT " + (count ? "count(questions.q_id)" : 
                "questions.q_id, title, body, r_id, asked, askerbanned") + " from questions";
        for (int i = 0; i < tagcount; i++)
            sql += ", tags as t" + i + ", tagstoquestions as tq" + i;
        if (tagcount > 0)   sql += " where ";
        for (int i = 0; i < tagcount; i++)
        {
            sql += "t" + i + ".t_id = tq" + i + ".t_id and tq" + i + ".q_id = questions.q_id and t" + i + ".tag = ?";
            if (i != tagcount - 1)  sql += " and ";
        }
        return sql + (count ? "" : " limit ? offset ?");
    }
    
    public Question()   {}
    public Question(String t, String b)
    {
        title = t;
        body = b;
    }
    public int getID()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getBody()
    {
        return body;
    }
/**
 * Counts flags for question.
 * @return question's flag count.
 */
    public int getFlags()
    {
        QAModel.prepareSQL(sql_countFlags, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
    public User getAsker()
    {
        return asker;
    }
    public String getAsked()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(asked);
    }
    public boolean getAskerBanned()
    {
        return askerBanned;
    }
/**
 * Adds a question to the database.
 * @param owner User that made the question.
 */
    public void addToDatabase(User owner)
    {
        asker = owner;
        title = Tools.formatHTMLsafe(title);
        body = Tools.formatHTMLsafe(body);
        Integer ownerID = (owner == null) ? null : owner.getID();
        QAModel.prepareSQL(sql_addToDB, title, body, ownerID);
        id = QAModel.retrieveInt(1);
        asked = QAModel.retrieveTimestamp(2);
        QAModel.closeComponents();
    }
/**
 * Removes question from the database.
 */
    public void removeFromDatabase()
    {
        QAModel.prepareSQL(sql_removeFromDB, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
/**
 * Finds all answers for the question limited to fit a page.
 * @param page The number of the page, for which the sublist of answers is to be retrieved.
 * @return Sublist of answers.
 */
    public List<Answer> getAnswers(int page)
    {
        QAModel.prepareSQL(sql_getQuestionsAnswers, id, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
        List result = QAModel.retrieveObjectList(new Answer());
        QAModel.closeComponents();
        return result;
    }
/**
 * Finds all tags for the question.
 * @return List of tags.
 */
    public List<Tag> getTags()
    {
        QAModel.prepareSQL(sql_getQuestionsTags, id);
        List result = QAModel.retrieveObjectList(new Tag());
        QAModel.closeComponents();
        return result;
    }
/**
 * Finds all questions limited to fit a page.
 * @param page The number of the page, for which the sublist of questions is to be retrieved.
 * @return Sublist of questions
 */
    public static List<Question> getQuestions(int page)
    {
        QAModel.prepareSQL(sql_getQuestions, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
    }
/**
 * Retrieves specific question by its ID.
 * @param id Specified ID.
 * @return question object.
 */
    public static Question getByID(int id)
    {
        if (id < 0) return null;
        Question q = new Question();
        QAModel.prepareSQL(sql_getByID, id);
        if (!QAModel.retrieveSingleObject(q))
            q = null;
        QAModel.closeComponents();
        return q;
    }
/**
 * Counts the amount of answers for this question.
 * @return answer count.
 */
    public int getAnswerCount()
    {
        QAModel.prepareSQL(sql_countAnswers, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
/**
 * Finds question that have the specified set of tags limited to fit a page.
 * @param tags set of tags.
 * @param page The number of the page, for which the sublist of question is to be retrieved.
 * @return Sublist of questions that pass the filter.
 */
    public static List<Question> getQuestionsByTags(String[] tags, int page)
    {
        Object[] params = new Object[tags.length + 2];
        for (int i = 0; i < tags.length; i++)
            params[i] = tags[i];
        params[tags.length] = Tools.elementsPerPage;
        params[tags.length + 1] = (page - 1) * Tools.elementsPerPage;
        QAModel.prepareSQL(sql_getQuestionByTags(tags.length, false), params);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
    }
/**
 * Gets questions sorted by flag count limited to fit a page.
 * @param page The number of the page, for which the sublist of questions is to be retrieved.
 * @return Sublist of questions sorted by flag count.
 */
    public static List<Question> getQuestionsSortedByFlags(int page)
    {
        QAModel.prepareSQL(sql_getQuestionsByFlags, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
    }
/**
 * Counts questions that include the tag set.
 * @param tags tag set.
 * @return question count.
 */
    public static int countQuestionsByTags(String[] tags)
    {
        if (tags == null)
            QAModel.prepareSQL(sql_getQuestionByTags(0, true));
        else
            QAModel.prepareSQL(sql_getQuestionByTags(tags.length, true), (Object[])tags);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
/**
 * Forms a question object from query results.
 * @param result query result.
 * @throws SQLException 
 */
    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("q_id");
        title = result.getString("title");
        body = result.getString("body");
        asked = result.getTimestamp("asked");
        askerBanned = result.getBoolean("askerbanned");
        int askerID = result.getInt("r_id");
        asker = User.getByID(askerID);
    }

    public Model newModel()
    {
        return new Question();
    }

}
