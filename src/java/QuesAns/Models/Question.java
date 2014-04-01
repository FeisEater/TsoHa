
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

    public Question(int i, String t, String b, Timestamp a, int f)
    {
        id = i;
        title = t;
        body = b;
        asked = a;
        flags = f;
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
    public static List<Question> getQuestions() throws ServletException, IOException
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_getQuestions);
            ResultSet result = ps.executeQuery();
            
            List<Question> users = new ArrayList<Question>();
            while (result.next())
                users.add(retrieveQuestionFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return users;
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
