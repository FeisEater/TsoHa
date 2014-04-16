
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
public class User implements Model {
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

    private static final String sql_getByID =
            "SELECT * from regusers where r_id = ?";

    private static final String sql_getQuestions =
            "SELECT * from questions where r_id = ?";

    private static final String sql_getAnswers =
            "SELECT * from answers where r_id = ?";

    public User() {}
    public User(String n, String e, String p)
    {
        nick = n;
        email = e;
        password = p;
    }
/*    public User(int i, String n, String e, String p, Timestamp t, boolean m)
    {
        this(n, e, p);
        id = i;
        joined = t;
        moderator = m;
    }*/
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
/**
 * Adds a User to the database.
 */
    public void register()
    {
        QAModel.prepareSQL(sql_registerUser, nick, email, password);
        id = QAModel.retrieveInt(1);
        joined = QAModel.retrieveTimestamp(2);
        moderator = false;
        QAModel.closeComponents();
/*        Connection c = null;
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
        }*/
    }
/**
 * Edits user's information in the database.
 */
    public void changeSettings()
    {
        QAModel.prepareSQL(sql_changeSettings, nick, email, password, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
        /*Connection c = null;
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
        }*/
    }
/**
 * Finds all questions asked by this user.
 * @return List of Questions.
 */
    public List<Question> getQuestions()
    {
        QAModel.prepareSQL(sql_getQuestions, id);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
        /*Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getQuestions);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            List<Question> questions = new ArrayList<Question>();
            while (result.next())
                questions.add(Question.retrieveQuestionFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return questions;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
    }
/**
 * Finds all answers made by this user.
 * @return List of Answers.
 */
    public List<Answer> getAnswers()
    {
        QAModel.prepareSQL(sql_getAnswers, id);
        List result = QAModel.retrieveObjectList(new Answer());
        QAModel.closeComponents();
        return result;
        /*Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getAnswers);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            List<Answer> answers = new ArrayList<Answer>();
            while (result.next())
                answers.add(Answer.retrieveAnswerFromResults(result));

            QAConnection.closeComponents(result, ps, c);
            return answers;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
    }
/**
 * Finds all users.
 * @return List of Users.
 */
    public static List<User> getUsers()
    {
        QAModel.prepareSQL(sql_getAllUsers);
        List result = QAModel.retrieveObjectList(new User());
        QAModel.closeComponents();
        return result;
/*        Connection c = null;
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
        return null;*/
    }
/**
 * Retrieves User object by login info.
 * @param nameoremail String for nickname or email.
 * @param password String for password.
 * @return User object.
 */
    public static User getByLoginInfo(String nameoremail, String password)
    {
        User u = new User();
        QAModel.prepareSQL(sql_getUserByLogin, nameoremail, nameoremail, password);
        QAModel.retrieveSingleObject(u);
        QAModel.closeComponents();
        return u;
        /*Connection c = null;
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
        return null;*/
    }
/**
 * Retrieves specific user by its ID.
 * @param id Specified ID.
 * @return user object.
 */
    public static User getByID(int id)
    {
        User u = new User();
        QAModel.prepareSQL(sql_getByID, id);
        QAModel.retrieveSingleObject(u);
        QAModel.closeComponents();
        return u;
        /*Connection c = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            c = QAConnection.getConnection();
            ps = c.prepareStatement(sql_getByID);
            ps.setInt(1, id);
            result = ps.executeQuery();
            
            User u = null;
            if (result.next())
                u = retrieveUserFromResults(result);

            QAConnection.closeComponents(result, ps, c);
            return u;
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            QAConnection.closeComponents(result, ps, c);
        }
        return null;*/
    }
/**
 * Forms a user object based by query results.
 * @param result ResultSet object.
 * @return User object.
 * @throws SQLException 
 */
/*    private static User retrieveObjectFromResults(ResultSet result) throws SQLException
    {
        int i = result.getInt("r_id");
        String n = result.getString("nick");
        String e = result.getString("email");
        String p = result.getString("password");
        Timestamp t = result.getTimestamp("joined");
        boolean m = result.getBoolean("moderator");
        return new User(i,n,e,p,t,m);
    }*/

    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("r_id");
        nick = result.getString("nick");
        email = result.getString("email");
        password = result.getString("password");
        joined = result.getTimestamp("joined");
        moderator = result.getBoolean("moderator");
    }

    public Model newModel()
    {
        return new User();
    }
}
