
package QuesAns.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Class for managing connections with database and queries.
 * @author Pavel
 */
public class QAModel {
    private static Connection c;
    private static PreparedStatement ps;
    private static Result result = new Result();
    private static boolean resultRetrieved;
/**
 * Stack of query results.
 */
    private static class Result {
        private Stack<ResultSet> results;
        private Result()
        {
            results = new Stack<ResultSet>();
        }
        private void add(ResultSet res)
        {
            results.push(res);
        }
        private ResultSet get()
        {
            return results.peek();
        }
        private void close() throws SQLException
        {
            results.pop().close();
        }
        private boolean allclosed()
        {
            return results.empty();
        }
    }
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
            if (!result.allclosed())     result.close();
            if (result.allclosed())
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
/**
 * Prepares injection-proof query.
 * @param sql query string.
 * @param param parameters to be inserted to the string.
 */
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
/**
 * Executes an update for query.
 */
    public static void executeUpdate()
    {
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
/**
 * Executes query to access results.
 * @return query returned results.
 * @throws SQLException 
 */
    private static boolean getResult() throws SQLException
    {
        if (resultRetrieved)
            return true;
        
        result.add(ps.executeQuery());
        if (!result.get().next())
            return false;
        resultRetrieved = true;
        return true;
    }
/**
 * Retrieves integer from result.
 * @param index index of result.
 * @return integer
 */
    public static int retrieveInt(int index)
    {
        try {
            getResult();
            return result.get().getInt(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return -1;
    }
/**
 * Retrieves string from result.
 * @param index index of result.
 * @return string
 */
    public static String retrieveString(int index)
    {
        try {
            getResult();
            return result.get().getString(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
/**
 * Retrieves timestamp from result.
 * @param index index of result.
 * @return timestamp
 */
    public static Timestamp retrieveTimestamp(int index)
    {
        try {
            getResult();
            return result.get().getTimestamp(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
/**
 * Retrieves byte array from result.
 * @param index index of result.
 * @return byte array
 */
    public static byte[] retrieveByteArray(int index)
    {
        try {
            getResult();
            return result.get().getBytes(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
/**
 * Retrieves a single model object from result.
 * @param m model object to access correct class.
 * @return model object.
 */
    public static boolean retrieveSingleObject(Model m)
    {
        try {
            if (!getResult())
                return false;
            m.getObjectFromResults(result.get());
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
/**
 * Retrieves a model object list from results.
 * @param m model object to access correct class.
 * @return model object list.
 */
    public static List<Model> retrieveObjectList(Model m)
    {
        List<Model> list = new ArrayList<Model>();
        try {
            result.add(ps.executeQuery());
            while (result.get().next())
            {
                Model newm = m.newModel();
                newm.getObjectFromResults(result.get());
                list.add(newm);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return list;
    }
/**
 * Checks if query returned any result.
 * @return query returned even one result.
 */
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
