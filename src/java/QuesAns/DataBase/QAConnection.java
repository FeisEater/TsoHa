package QuesAns.DataBase;

/**
 * Static class for forming and closing components requiring
 * connection with the database.
 * @author FeisEater
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class QAConnection {
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
 * @param r ResultSet object.
 * @param p PreparedStatement object.
 * @param c Connection object.
 */
    public static void closeComponents(ResultSet r, PreparedStatement p, Connection c)
    {
        try
        {
            if (r != null)  r.close();
            if (p != null)  p.close();
            if (c != null)  c.close();
        }   catch (Throwable e) {}
    }
}
