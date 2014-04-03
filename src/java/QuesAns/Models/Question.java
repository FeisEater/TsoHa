
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
    
    private static final String sql_getQuestions =
            "SELECT * from questions order by asked desc";

    private static final String sql_getByID =
            "SELECT * from questions where q_id = ?";

    private static final String sql_addToDB =
            "INSERT INTO questions(title, body, q_id, asked, flags) "
            + "VALUES(?,?,?,LOCALTIMESTAMP,0) RETURNING q_id, asked";

    public Question(String t, String b)
    {
        title = t;
        body = b;
        flags = 0;
    }
    public Question(int i, String t, String b, Timestamp a, int f)
    {
        this(t,b);
        id = i;
        asked = a;
        flags = f;
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
    public void addToDatabase(User owner)
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_addToDB);
            ps.setString(1, title);
            System.out.println(body);
            ps.setString(2, body);
            int givenID = (owner == null) ? -1 : owner.getID();
            ps.setInt(3, givenID);
            ResultSet result = ps.executeQuery();
            result.next();
            id = result.getInt(1);
            asked = result.getTimestamp(2);
            QAConnection.closeComponents(result, ps, c);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public static List<Question> getQuestions() throws ServletException, IOException
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_getQuestions);
            ResultSet result = ps.executeQuery();
            
            List<Question> questions = new ArrayList<Question>();
            while (result.next())
                questions.add(retrieveQuestionFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return questions;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public static Question getByID(int id)
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_getByID);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            
            Question q = null;
            if (result.next())
                q = retrieveQuestionFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return q;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    private static Question retrieveQuestionFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("q_id");
        String t = result.getString("title");
        String b = result.getString("body");
        Timestamp a = result.getTimestamp("asked");
        int f = result.getInt("flags");
        return new Question(i,t,b,a,f);
    }

}
