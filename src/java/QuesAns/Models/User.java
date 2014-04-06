
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
public class User {
    private int id;
    private String nick;
    private String email;
    private String password;
    private Timestamp joined;
    private boolean moderator;
    
    private static final String sql_getAllUsers = "SELECT * from regusers";
    
    private static final String sql_getUserByLogin = "SELECT * from regusers "
                + "where (nick = ? or email = ?) and password = ?";

    private static final String sql_registerUser =
            "INSERT INTO regusers(nick, email, password, joined) "
            + "VALUES(?,?,?,LOCALTIMESTAMP) RETURNING r_id, joined";
    
    private static final String sql_changeSettings =
            "UPDATE regusers SET nick = ?, email = ?, password = ? where r_id = ?";


    public User(String n, String e, String p)
    {
        nick = n;
        email = e;
        password = p;
    }
    public User(int i, String n, String e, String p, Timestamp t, boolean m)
    {
        this(n, e, p);
        id = i;
        joined = t;
        moderator = m;
    }
    @Override
    public String toString()
    {
        return ""+nick+" ("+email+") - "+password+" - joined: " + joined;
    }
    public int getID()
    {
        return id;
    }
    public String getName()
    {
        return nick;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPassword()
    {
        return password;
    }
    public boolean getModerator()
    {
        return moderator;
    }
    public void setName(String n)
    {
        if (!n.isEmpty())
            nick = n;
    }
    public void setEmail(String e)
    {
        if (!e.isEmpty())
            email = e;
    }
    public void setPassword(String p)
    {
        if (!p.isEmpty())
            password = p;
    }
    public void register()
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_registerUser);
            ps.setString(1, nick);
            ps.setString(2, email);
            ps.setString(3, password);
            result = ps.executeQuery();
            result.next();
            id = result.getInt(1);
            joined = result.getTimestamp(2);
            QAConnection.closeComponents(result, ps, c);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
    }
    public void changeSettings()
    {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_changeSettings);
            ps.setString(1, nick);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(null, ps, c);
        }
    }
    public static List<User> getUsers() throws ServletException, IOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getAllUsers);
            result = ps.executeQuery();
            
            List<User> users = new ArrayList<User>();
            while (result.next())
                users.add(retrieveUserFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return users;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;
    }
    public static User getByLoginInfo(String nameoremail, String password)
            throws ServletException, IOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getUserByLogin);
            ps.setString(1, nameoremail);
            ps.setString(2, nameoremail);
            ps.setString(3, password);
            result = ps.executeQuery();
            
            User loggedIn = null;
            if (result.next())
                loggedIn = retrieveUserFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return loggedIn;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;
    }
    
    private static User retrieveUserFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("r_id");
        String n = result.getString("nick");
        String e = result.getString("email");
        String p = result.getString("password");
        Timestamp t = result.getTimestamp("joined");
        boolean m = result.getBoolean("moderator");
        return new User(i,n,e,p,t,m);
    }
}
