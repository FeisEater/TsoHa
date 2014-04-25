
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
 *
 * @author Pavel
 */
public class QAModel {
    private Connection c;
    private PreparedStatement ps;
    private Result result;
    private boolean resultRetrieved;
    private class Result {
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
    public QAModel()
    {
        result = new Result();
    }
/**
 * Gets connection with database.
 * @return connection object.
 */
    private Connection getConnection() throws SQLException {
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
    public void closeComponents()
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

    public void prepareSQL(String sql, Object... param)
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
    public void executeUpdate()
    {
        try {
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private boolean getResult() throws SQLException
    {
        if (resultRetrieved)
            return true;
        
        result.add(ps.executeQuery());
        if (!result.get().next())
            return false;
        resultRetrieved = true;
        return true;
    }
    public int retrieveInt(int index)
    {
        try {
            getResult();
            return result.get().getInt(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return -1;
    }
    public String retrieveString(int index)
    {
        try {
            getResult();
            return result.get().getString(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public Timestamp retrieveTimestamp(int index)
    {
        try {
            getResult();
            return result.get().getTimestamp(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public byte[] retrieveByteArray(int index)
    {
        try {
            getResult();
            return result.get().getBytes(index);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    public boolean retrieveSingleObject(Model m)
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
    public List<Model> retrieveObjectList(Model m)
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
    public boolean resultFound()
    {
        try {
            return getResult();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }
}
