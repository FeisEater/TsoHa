
package QuesAns.Models;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.catalina.util.Base64;

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
    private QAModel db;
    
    private static final String sql_getAllUsers = "SELECT * from regusers order by joined desc";
    
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
    
    private static final String sql_getAvatar =
            "SELECT avatar from regusers where r_id = ?";

    private static final String sql_setAvatar =
            "UPDATE regusers SET avatar = ? where r_id = ?";

    private static final String sql_removeFromDB =
            "DELETE FROM regusers WHERE r_id = ?";
    
    private static final String sql_countAnswers =
            "SELECT count(*) from answers where r_id = ?";

    private static final String sql_countQuestions =
            "SELECT count(*) from questions where r_id = ?";
    
    private static final String sql_addAnswerFlag =
            "INSERT INTO ratedflaggedanswers(r_id, a_id, flagged) "
            + "VALUES(?,?,true)";
            //"UPDATE answers SET flags = ? where a_id = ?";

    private static final String sql_undoAnswerFlag =
            "DELETE FROM ratedflaggedanswers "
            + "WHERE r_id = ? and a_id = ? and flagged = true";

    private static final String sql_hasRated =
            "SELECT * from ratedflaggedanswers where r_id = ? and a_id = ? and flagged = false";

    private static final String sql_hasFlaggedAnswer =
            "SELECT * from ratedflaggedanswers where r_id = ? and a_id = ? and flagged = true";

    private static final String sql_addQuestionFlag =
            "INSERT INTO flaggedquestions(r_id, q_id) VALUES(?,?)";
            //"UPDATE answers SET flags = ? where a_id = ?";

    private static final String sql_undoQuestionFlag =
            "DELETE FROM flaggedquestions WHERE r_id = ? and q_id = ?";

    private static final String sql_hasFlaggedQuestion =
            "SELECT * from flaggedquestions where r_id = ? and q_id = ?";
    
    private static final String sql_userExists =
            "SELECT r_id from regusers where r_id = ?";

    public User()
    {
        db = new QAModel();
    }
    public User(String n, String e, String p)
    {
        this();
        nick = n;
        email = e;
        password = p;
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
    public String getJoined()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(joined);
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
        db.prepareSQL(sql_registerUser, nick, email, password);
        id = db.retrieveInt(1);
        joined = db.retrieveTimestamp(2);
        moderator = false;
        db.closeComponents();
    }
/**
 * Edits user's information in the database.
 */
    public void changeSettings()
    {
        db.prepareSQL(sql_changeSettings, nick, email, password, id);
        db.executeUpdate();
        db.closeComponents();
    }
    public void setAvatar(byte[] avatar)
    {
        db.prepareSQL(sql_setAvatar, avatar, id);
        db.executeUpdate();
        db.closeComponents();
    }
/**
 * Finds all questions asked by this user.
 * @return List of Questions.
 */
    public List<Question> getQuestions()
    {
        db.prepareSQL(sql_getQuestions, id);
        List result = db.retrieveObjectList(new Question());
        db.closeComponents();
        return result;
    }
/**
 * Finds all answers made by this user.
 * @return List of Answers.
 */
    public List<Answer> getAnswers()
    {
        db.prepareSQL(sql_getAnswers, id);
        List result = db.retrieveObjectList(new Answer());
        db.closeComponents();
        return result;
    }
/**
 * Finds all users.
 * @return List of Users.
 */
    public static List<User> getUsers()
    {
        QAModel d = new QAModel();
        d.prepareSQL(sql_getAllUsers);
        List result = d.retrieveObjectList(new User());
        d.closeComponents();
        return result;
    }
/**
 * Retrieves User object by login info.
 * @param nameoremail String for nickname or email.
 * @param password String for password.
 * @return User object.
 */
    public static User getByLoginInfo(String nameoremail, String password)
    {
        QAModel d = new QAModel();
        User u = new User();
        d.prepareSQL(sql_getUserByLogin, nameoremail, nameoremail, password);
        if (!d.retrieveSingleObject(u))
            u = null;
        d.closeComponents();
        return u;
    }
/**
 * Retrieves specific user by its ID.
 * @param id Specified ID.
 * @return user object.
 */
    public static User getByID(int id)
    {
        QAModel d = new QAModel();
        User u = new User();
        d.prepareSQL(sql_getByID, id);
        if (!d.retrieveSingleObject(u))
            u = null;
        d.closeComponents();
        return u;
    }
    public String getAvatar()
    {
        db.prepareSQL(sql_getAvatar, id);
        byte[] bytearray = db.retrieveByteArray(1);
        db.closeComponents();
        if (bytearray == null)  return null;
        return new String(Base64.encode(bytearray));
    }
    public void removeFromDatabase()
    {
        db.prepareSQL(sql_removeFromDB, id);
        db.executeUpdate();
        db.closeComponents();
    }
    public int getAnscount()
    {
        db.prepareSQL(sql_countAnswers, id);
        int result = db.retrieveInt(1);
        db.closeComponents();
        return result;
    }
    public int getQuescount()
    {
        db.prepareSQL(sql_countQuestions, id);
        int result = db.retrieveInt(1);
        db.closeComponents();
        return result;
    }
/**
 * Adds answer's flag count by one.
 */
    public void addFlagToAnswer(Answer ans)
    {
        db.prepareSQL(sql_addAnswerFlag, id, ans.getID());
        db.executeUpdate();
        db.closeComponents();
    }
    public void removeFlagFromAnswer(Answer ans)
    {
        db.prepareSQL(sql_undoAnswerFlag, id, ans.getID());
        db.executeUpdate();
        db.closeComponents();
    }
    public boolean hasRated(Answer ans)
    {
        db.prepareSQL(sql_hasRated, id, ans.getID());
        boolean result = db.resultFound();
        db.closeComponents();
        return result;
    }
    public boolean hasFlaggedAnswer(Answer ans)
    {
        db.prepareSQL(sql_hasFlaggedAnswer, id, ans.getID());
        boolean result = db.resultFound();
        db.closeComponents();
        return result;
    }
/**
 * Adds question's flag count by one.
 */
    public void addFlagToQuestion(Question ques)
    {
        db.prepareSQL(sql_addQuestionFlag, id, ques.getID());
        db.executeUpdate();
        db.closeComponents();
    }
    public void removeFlagFromQuestion(Question ques)
    {
        db.prepareSQL(sql_undoQuestionFlag, id, ques.getID());
        db.executeUpdate();
        db.closeComponents();
    }
    public boolean hasFlaggedQuestion(Question ques)
    {
        db.prepareSQL(sql_hasFlaggedQuestion, id, ques.getID());
        boolean result = db.resultFound();
        db.closeComponents();
        return result;
    }
    public boolean stillExists()
    {
        db.prepareSQL(sql_userExists, id);
        boolean result = db.resultFound();
        db.closeComponents();
        return result;
    }

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
