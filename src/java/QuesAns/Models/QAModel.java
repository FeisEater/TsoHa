
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
public class QAModel {
    Connection c;
    PreparedStatement ps;
    ResultSet result;
    int count;
    
    public QAModel()
    {
        count = 1;
    }
    
    public void prepareSQL(String sql)
    {
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void setInt(int i)
    {
        try {
            ps.setInt(count, i);
            count++;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void setString(String s)
    {
        try {
            ps.setString(count, s);
            count++;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void setTimestamp(Timestamp t)
    {
        try {
            ps.setTimestamp(count, t);
            count++;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
