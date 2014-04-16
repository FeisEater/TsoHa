
package QuesAns.Models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Pavel
 */
public class QAModel {
    private static Connection c;
    private static PreparedStatement ps;
    private static ResultSet result;
    private static int count;
        
/**
 * Gets connection with database.
 * @return connection object.
 */
    public static Connection getConnection() {
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
 */
    public static void closeComponents()
    {
        try
        {
            if (result != null)     result.close();
            if (ps != null)         ps.close();
            if (c != null)          c.close();
            c = null;
        }   catch (Throwable e) {}
    }

    public static void prepareSQL(String sql)
    {
        try {
            if (c == null)   c = getConnection();
            ps = c.prepareStatement(sql);
            count = 1;
        } catch (SQLException ex) {
            closeComponents();
            System.out.println(ex);
        }
    }
    
    public static void setInt(int i)
    {
        try {
            ps.setInt(count, i);
            count++;
        } catch (SQLException ex) {
            closeComponents();
            System.out.println(ex);
        }
    }

    public static void setString(String s)
    {
        try {
            ps.setString(count, s);
            count++;
        } catch (SQLException ex) {
            closeComponents();
            System.out.println(ex);
        }
    }

    public static void setTimestamp(Timestamp t)
    {
        try {
            ps.setTimestamp(count, t);
            count++;
        } catch (SQLException ex) {
            closeComponents();
            System.out.println(ex);
        }
    }

    public static void executeQuery()
    {
        try {
            result = ps.executeQuery();
            count = 1;
        } catch (SQLException ex) {
            closeComponents();
            System.out.println(ex);
        }
    }

    public static void executeUpdate()
    {
        try {
            ps.executeUpdate();
            count = 1;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            closeComponents();
        }
    }

    public static List getObjectList(Class c)
    {
        List list = new ArrayList();
        try {
            while(result.next())
                list.add(c.getMethod("retrieveObjectFromResults", ResultSet.class).invoke(null, result));
        } catch (Throwable ex) {
            closeComponents();
            System.out.println(ex);
        }
        return list;
    }
}
