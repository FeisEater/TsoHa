
package QuesAns.Models;

import QuesAns.DataBase.QAConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

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
/*    public Question(int i, String t, String b, Timestamp a, int f, User u)
    {
        this(t,b);
        id = i;
        asked = a;
        flags = f;
        asker = u;
    }*/
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
        int ownerID = (owner == null) ? null : owner.getID();
        QAModel.prepareSQL(sql_addToDB, title, body, ownerID);
        id = QAModel.retrieveInt(1);
        asked = QAModel.retrieveTimestamp(2);
        QAModel.closeComponents();
/*        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_addToDB);
            ps.setString(1, title);
            ps.setString(2, body);
            if (owner == null)
                ps.setObject(3, null);
            else
                ps.setInt(3, owner.getID());
            result = ps.executeQuery();
            result.next();
            id = result.getInt(1);
            asked = result.getTimestamp(2);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }*/
    }
/**
 * Adds question's flag count by one.
 */
    public void addFlag()
    {
        QAModel.prepareSQL(sql_addFlag, flags++, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
/*        Connection c = null;
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
        }*/
    }
/**
 * Removes question from the database.
 */
    public void removeFromDatabase()
    {
        QAModel.prepareSQL(sql_removeFromDB, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
        /*Connection c = null;
        PreparedStatement ps = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_removeFromDB);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(null, ps, c);
        }*/
    }
/**
 * Finds all answers for the question.
 * @return List of answers.
 * @throws ServletException
 * @throws IOException 
 */
    public List<Answer> getAnswers()
    {
        QAModel.prepareSQL(sql_getQuestionsAnswers, id);
        List result = QAModel.retrieveObjectList(new Answer());
        QAModel.closeComponents();
        return result;
/*        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getQuestionsAnswers);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            List<Answer> answers = new ArrayList<Answer>();
            while (result.next())
                answers.add(Answer.retrieveAnswerFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return answers;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
    }
/**
 * Finds all tags for the question.
 * @return List of tags.
 * @throws ServletException
 * @throws IOException 
 */
    public List<Tag> getTags() throws ServletException, IOException
    {
        QAModel.prepareSQL(sql_getQuestionsTags, id);
        List result = QAModel.retrieveObjectList(new Tag());
        QAModel.closeComponents();
        return result;
        /*Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getQuestionsTags);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            List<Tag> tags = new ArrayList<Tag>();
            while (result.next())
                tags.add(Tag.getByID(result.getInt("t_id")));

            QAConnection.closeComponents(result, ps, c);
            return tags;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
    }
/**
 * Finds all questions.
 * @param order final part of the sql statement, can be used for specifying the order to be listed.
 * @return List of questions
 * @throws ServletException
 * @throws IOException 
 */
    public static List<Question> getQuestions(String order) throws ServletException, IOException
    {
        QAModel.prepareSQL(sql_getQuestions + order);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
/*        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getQuestions + order);
            result = ps.executeQuery();
            
            List<Question> questions = new ArrayList<Question>();
            while (result.next())
                questions.add(retrieveQuestionFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return questions;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
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
        /*Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getByID);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            Question q = null;
            if (result.next())
                q = retrieveQuestionFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return q;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
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
/*        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_countAnswers);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            int count = -1;
            if (result.next())
                count = result.getInt(1);

            QAConnection.closeComponents(result, ps, c);
            return count;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return -1;*/
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
/*        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getQuestionByTags(tags.length));
            for (int i = 1; i <= tags.length; i++)
                ps.setString(i, tags[i-1]);
            result = ps.executeQuery();
            
            List<Question> questions = new ArrayList<Question>();
            while (result.next())
                questions.add(retrieveQuestionFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return questions;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
    }
/**
 * Forms a question object based by query results.
 * @param result ResultSet object.
 * @return Question object.
 * @throws SQLException 
 */
/*    public static Question retrieveObjectFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("q_id");
        String t = result.getString("title");
        String b = result.getString("body");
        Timestamp a = result.getTimestamp("asked");
        int f = result.getInt("flags");
        int askerID = result.getInt("r_id");
        User u = User.getByID(askerID);
        return new Question(i,t,b,a,f,u);
    }
*/
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
