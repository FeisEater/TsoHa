
package QuesAns.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
    
/**
 * Gets connection with database.
 * @return connection object.
 */
    public Connection getConnection() {
        try
        {
            InitialContext cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
            return ds.getConnection();
        }
        catch (NamingException e) {}
        catch (SQLException e) {}
        
        return null;
    }
/**
 * Closes components requiring connection with database.
 * @param r ResultSet object.
 * @param p PreparedStatement object.
 * @param c Connection object.
 */
    public void closeComponents(ResultSet r, PreparedStatement p, Connection c)
    {
        try
        {
            if (r != null)  r.close();
            if (p != null)  p.close();
            if (c != null)  c.close();
        }   catch (Throwable e) {}
    }

    public void prepareSQL(String sql)
    {
        try {
            c = getConnection();
            ps = c.prepareStatement(sql);
        } catch (SQLException ex) {
            closeComponents(null, ps, c);
            System.out.println(ex);
        }
    }
    
    public void setInt(int i)
    {
        try {
            ps.setInt(count, i);
            count++;
        } catch (SQLException ex) {
            closeComponents(null, ps, c);
            System.out.println(ex);
        }
    }

    public void setString(String s)
    {
        try {
            ps.setString(count, s);
            count++;
        } catch (SQLException ex) {
            closeComponents(null, ps, c);
            System.out.println(ex);
        }
    }

    public void setTimestamp(Timestamp t)
    {
        try {
            ps.setTimestamp(count, t);
            count++;
        } catch (SQLException ex) {
            closeComponents(null, ps, c);
            System.out.println(ex);
        }
    }

    public void executeQuery()
    {
        try {
            result = ps.executeQuery();
            count = 1;
        } catch (SQLException ex) {
            closeComponents(null, ps, c);
            System.out.println(ex);
        }
    }
   /* 
    public Object getResult(int index)
    {
        try {
            ps.setTimestamp(count, t);
            count++;
        } catch (SQLException ex) {
            closeComponents(null, ps, c);
            System.out.println(ex);
        }
    }*/
}
