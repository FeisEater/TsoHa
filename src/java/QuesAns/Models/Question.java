
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
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_addToDB);
            ps.setString(1, title);
            ps.setString(2, body);
            if (owner == null)
                ps.setObject(3, null);
            else
                ps.setInt(3, owner.getID());
            ResultSet result = ps.executeQuery();
            result.next();
            id = result.getInt(1);
            asked = result.getTimestamp(2);
            QAConnection.closeComponents(result, ps, c);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void addFlag()
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_addFlag);
            ps.setInt(1, flags + 1);
            ps.setInt(2, id);
            ps.executeUpdate();
            QAConnection.closeComponents(null, ps, c);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public void removeFromDatabase()
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_removeFromDB);
            ps.setInt(1, id);
            ps.executeUpdate();
            QAConnection.closeComponents(null, ps, c);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public static List<Question> getQuestions(String order) throws ServletException, IOException
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_getQuestions + order);
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
