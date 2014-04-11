
package QuesAns.Models;

import QuesAns.DataBase.QAConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Pavel
 */
public class Tag {
    private int id;
    private String tag;
    private Timestamp tagged;
    
    private static final String sql_getByID =
            "SELECT * from tags where t_id = ?";

    private static final String sql_addToDB =
            "INSERT INTO tags(tag, firsttagged) "
            + "VALUES(?,LOCALTIMESTAMP) RETURNING t_id, firsttagged";

    private static final String sql_connectTagWithQuestion =
            "INSERT INTO tagstoquestions(t_id, q_id) VALUES(?,?)";

    public Tag(String tag)
    {
        this.tag = tag;
    }
    public Tag(int i, String tag, Timestamp t)
    {
        this(tag);
        id = i;
        tagged = t;
    }
    public String getText()
    {
        return tag;
    }
    public static Tag getByID(int id)
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getByID);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            Tag t = null;
            if (result.next())
                t = retrieveTagFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return t;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;
    }
    public void addToDatabase(Question q)
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_addToDB);
            ps.setString(1, tag);
            result = ps.executeQuery();
            result.next();
            id = result.getInt(1);
            tagged = result.getTimestamp(2);
            ps = c.prepareStatement(sql_connectTagWithQuestion);
            ps.setInt(1, id);
            ps.setInt(2, q.getID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
    }
    private static Tag retrieveTagFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("t_id");
        String tagtext = result.getString("tag");
        Timestamp t = result.getTimestamp("firsttagged");
        return new Tag(i,tagtext,t);
    }
}
