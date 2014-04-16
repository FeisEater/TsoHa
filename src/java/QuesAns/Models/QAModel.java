
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
            if (result != null)     result.close();
            if (ps != null)         ps.close();
            if (c != null)          c.close();
            c = null;
            ps = null;
            result = null;
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
        
        result = ps.executeQuery();
        if (!result.next())
            return false;
        resultRetrieved = true;
        return true;
    }
    public static int retrieveInt(int index)
    {
        try {
            getResult();
            return result.getInt(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return -1;
    }
    public static String retrieveString(int index)
    {
        try {
            getResult();
            return result.getString(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public static Timestamp retrieveTimestamp(int index)
    {
        try {
            getResult();
            return result.getTimestamp(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public static void retrieveSingleObject(Model m)
    {
        try {
            if (!getResult())
            {
                m = null;
                return;
            }
            m.getObjectFromResults(result);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public static List<Model> retrieveObjectList(Model m)
    {
        List<Model> list = new ArrayList<Model>();
        try {
            result = ps.executeQuery();
            while(result.next())
            {
                Model newm = m.newModel();
                newm.getObjectFromResults(result);
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
