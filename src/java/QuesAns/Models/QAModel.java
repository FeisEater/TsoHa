
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
import java.util.Stack;
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
    private static Stack<ResultSet> result = new Stack<ResultSet>();
    private static boolean resultRetrieved;
        
/**
 * Gets connection with database.
 * @return connection object.
 */
    private static Connection getConnection() throws SQLException {
        try {
            InitialContext cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/tietokanta");
            return ds.getConnection();
        } catch (NamingException e) {
            System.out.println(e);
        }
        
        return null;
    }
/**
 * Closes components requiring connection with database.
 */
    public static void closeComponents()
    {
        try {
            if (!result.empty())     result.pop().close();
            if (result.empty())
            {
                if (ps != null)         ps.close();
                if (c != null)          c.close();
                c = null;
                ps = null;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void prepareSQL(String sql, Object... param)
    {
        try {
            if (c == null)   c = getConnection();
            ps = c.prepareStatement(sql);
            for (int i = 0; i < param.length; i++)
                ps.setObject(i+1, param[i]);
            resultRetrieved = false;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public static void executeUpdate()
    {
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private static boolean getResult() throws SQLException
    {
        if (resultRetrieved)
            return true;
        
        result.push(ps.executeQuery());
        if (!result.peek().next())
            return false;
        resultRetrieved = true;
        return true;
    }
    public static int retrieveInt(int index)
    {
        try {
            getResult();
            return result.peek().getInt(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return -1;
    }
    public static String retrieveString(int index)
    {
        try {
            getResult();
            return result.peek().getString(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public static Timestamp retrieveTimestamp(int index)
    {
        try {
            getResult();
            return result.peek().getTimestamp(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public static boolean retrieveSingleObject(Model m)
    {
        try {
            if (!getResult())
                return false;
            m.getObjectFromResults(result.peek());
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
    public static List<Model> retrieveObjectList(Model m)
    {
        List<Model> list = new ArrayList<Model>();
        try {
            result.push(ps.executeQuery());
            while (result.peek().next())
            {
                Model newm = m.newModel();
                newm.getObjectFromResults(result.peek());
                list.add(newm);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
    public static boolean resultFound()
    {
        try {
            return getResult();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
}
