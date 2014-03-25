
package QuesAns.Models;

import QuesAns.DataBase.QAConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    
    public User(int i, String n, String e, String p, Timestamp t)
    {
        id = i;
        nick = n;
        email = e;
        password = p;
        joined = t;
    }
    public String toString()
    {
        return ""+nick+" ("+email+") - "+password+" - joined: " + joined;
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
    public static List<User> getUsers() throws Throwable
    {
        String sql = "SELECT r_id, nick, email, password, joined from regusers";
        Connection c = QAConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet result = ps.executeQuery();

        List<User> users = new ArrayList<User>();
        while (result.next())
        {
            int i = result.getInt("r_id");
            String n = result.getString("nick");
            String e = result.getString("email");
            String p = result.getString("password");
            Timestamp t = result.getTimestamp("joined");
            users.add(new User(i,n,e,p,t));
        }
        
        try {result.close();} catch (Exception e) {}
        try {ps.close();} catch (Exception e) {}
        QAConnection.closeConnection(c);
        
        return users;
    }
}
