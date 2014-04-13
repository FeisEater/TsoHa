
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
 *
 * @author FeisEater
 */
public class Question {
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
            "UPDATE questions SET flags = ? where q_id = ? order by rating";
    
    private static final String sql_removeFromDB =
            "DELETE FROM questions WHERE q_id = ?";

    private static final String sql_getQuestionsAnswers =
        "SELECT * from answers where q_id = ?";

    private static final String sql_getQuestionsTags =
            "SELECT * from tagstoquestions where q_id = ?";

    private static final String sql_countAnswers =
            "SELECT count(*) from answers where q_id = ?";
    
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
    
    public Question(String t, String b)
    {
        title = t;
        body = b;
        flags = 0;
    }
    public Question(int i, String t, String b, Timestamp a, int f, User u)
    {
        this(t,b);
        id = i;
        asked = a;
        flags = f;
        asker = u;
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
    public String reformatString(String s)
    {
        s = s.replace("&", "&amp");
        s = s.replace("<", "&lt");
        s = s.replace(">", "&gt");
        s = s.replace("\n", "<br>");
        s = s.replace(" ", "&nbsp");
        return s;
    }
    public void addToDatabase(User owner)
    {
        title = reformatString(title);
        body = reformatString(body);
        Connection c = null;
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
        }
    }
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
    public void removeFromDatabase()
    {
        Connection c = null;
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
        }
    }
    public List<Answer> getAnswers() throws ServletException, IOException
    {
        Connection c = null;
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
        return null;
    }
    public List<Tag> getTags() throws ServletException, IOException
    {
        Connection c = null;
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
        return null;
    }
    public static List<Question> getQuestions(String order) throws ServletException, IOException
    {
        Connection c = null;
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
        return null;
    }
    public static Question getByID(int id)
    {
        Connection c = null;
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
        return null;
    }
    public int getAnswerCount()
    {
        Connection c = null;
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
        return -1;
    }
    public static List<Question> getQuestionsByTags(String[] tags) throws ServletException, IOException
    {
        Connection c = null;
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
        return null;
    }
    private static Question retrieveQuestionFromResults(ResultSet result) throws SQLException
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

}
