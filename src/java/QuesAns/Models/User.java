
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
    
    private static final String sql_getAllUsers =
            "SELECT r_id, nick, email, password, joined from regusers";
    
    private static final String sql_getUserByLogin = "SELECT * from regusers "
                + "where (nick = ? or email = ?) and password = ?";
    
    public User(int i, String n, String e, String p, Timestamp t)
    {
        id = i;
        nick = n;
        email = e;
        password = p;
        joined = t;
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
    public static List<User> getUsers() throws ServletException, IOException
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_getAllUsers);
            ResultSet result = ps.executeQuery();
            
            List<User> users = new ArrayList<User>();
            while (result.next())
                users.add(retrieveUserFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return users;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    public static User getByLoginInfo(String nameoremail, String password)
            throws ServletException, IOException
    {
        try {
            Connection c = QAConnection.getConnection();
            PreparedStatement ps = c.prepareStatement(sql_getUserByLogin);
            ps.setString(1, nameoremail);
            ps.setString(2, nameoremail);
            ps.setString(3, password);
            ResultSet result = ps.executeQuery();
            
            User loggedIn = null;
            if (result.next())
                loggedIn = retrieveUserFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return loggedIn;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
    
    private static User retrieveUserFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("r_id");
        String n = result.getString("nick");
        String e = result.getString("email");
        String p = result.getString("password");
        Timestamp t = result.getTimestamp("joined");
        return new User(i,n,e,p,t);
    }
}
