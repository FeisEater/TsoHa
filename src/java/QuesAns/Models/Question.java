
package QuesAns.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private int flags;
    private User asker;
    
    private static final String sql_getQuestions =
            "SELECT * from questions ";

    private static final String sql_getByID =
            "SELECT * from questions where q_id = ?";

    private static final String sql_addToDB =
            "INSERT INTO questions(title, body, r_id, asked, flags) "
            + "VALUES(?,?,?,LOCALTIMESTAMP,0) RETURNING q_id, asked";

    private static final String sql_addFlag =
            "UPDATE questions SET flags = ? where q_id = ?";
    
    private static final String sql_removeFromDB =
            "DELETE FROM questions WHERE q_id = ?";

    private static final String sql_getQuestionsAnswers =
        "SELECT * from answers where q_id = ? order by rating desc";

    private static final String sql_getQuestionsTags =
            "SELECT t.t_id, t.tag, t.firsttagged from tagstoquestions as tq, tags as t where q_id = ? and tq.t_id = t.t_id";

    private static final String sql_countAnswers =
            "SELECT count(*) from answers where q_id = ?";
    
/**
 * Forms an sql statement for finding questions that have the required set of tags
 * @param tagcount amount of tags
 * @return sql statement
 */
    private static String sql_getQuestionByTags(int tagcount)
    {
        String sql = "SELECT questions.q_id, title, body, r_id, asked, flags from questions";
        for (int i = 0; i < tagcount; i++)
            sql += ", tags as t" + i + ", tagstoquestions as tq" + i;
        sql += " where ";
        for (int i = 0; i < tagcount; i++)
        {
            sql += "t" + i + ".t_id = tq" + i + ".t_id and tq" + i + ".q_id = questions.q_id and t" + i + ".tag = ?";
            if (i != tagcount - 1)  sql += " and ";
        }
        return sql;
    }
    
    public Question() {}
    public Question(String t, String b)
    {
        title = t;
        body = b;
        flags = 0;
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
    public int getFlags()
    {
        return flags;
    }
    public User getAsker()
    {
        return asker;
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
 * Adds a question to the database.
 * @param owner User that made the question.
 */
    public void addToDatabase(User owner)
    {
        asker = owner;
        title = reformatString(title);
        body = reformatString(body);
        Integer ownerID = (owner == null) ? null : owner.getID();
        QAModel.prepareSQL(sql_addToDB, title, body, ownerID);
        id = QAModel.retrieveInt(1);
        asked = QAModel.retrieveTimestamp(2);
        flags = 0;
        QAModel.closeComponents();
    }
/**
 * Adds question's flag count by one.
 */
    public void addFlag()
    {
        QAModel.prepareSQL(sql_addFlag, ++flags, id);
        QAModel.executeUpdate();
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
 * Finds all answers for the question.
 * @return List of answers.
 */
    public List<Answer> getAnswers()
    {
        QAModel.prepareSQL(sql_getQuestionsAnswers, id);
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
 * Finds all questions.
 * @param order final part of the sql statement, can be used for specifying the order to be listed.
 * @return List of questions
 */
    public static List<Question> getQuestions(String order)
    {
        QAModel.prepareSQL(sql_getQuestions + order);
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
        Question q = new Question();
        QAModel.prepareSQL(sql_getByID, id);
        if (!QAModel.retrieveSingleObject(q))
            q = null;
        QAModel.closeComponents();
        return q;
    }
/**
 * Counts the amount of answer for this question.
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
 * Finds question that have the specified set of tags.
 * @param tags set of tags.
 * @return Questions that pass the filter.
 */
    public static List<Question> getQuestionsByTags(String[] tags)
    {
        QAModel.prepareSQL(sql_getQuestionByTags(tags.length), (Object[]) tags);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
    }
    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("q_id");
        title = result.getString("title");
        body = result.getString("body");
        asked = result.getTimestamp("asked");
        flags = result.getInt("flags");
        int askerID = result.getInt("r_id");
        asker = User.getByID(askerID);
    }

    public Model newModel()
    {
        return new Question();
    }

}
