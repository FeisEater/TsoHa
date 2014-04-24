
package QuesAns.Models;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            "SELECT ratedflaggedanswers where r_id = ? and a_id = ? and flagged = false";

    private static final String sql_hasFlaggedAnswer =
            "SELECT ratedflaggedanswers where r_id = ? and a_id = ? and flagged = true";

    private static final String sql_addQuestionFlag =
            "INSERT INTO flaggedquestions(r_id, q_id) VALUES(?,?)";
            //"UPDATE answers SET flags = ? where a_id = ?";

    private static final String sql_undoQuestionFlag =
            "DELETE FROM flaggedquestions WHERE r_id = ? and q_id = ?";

    private static final String sql_hasFlaggedQuestion =
            "SELECT * from flaggedquestions where r_id = ? and q_id = ?";

    public User() {}
    public User(String n, String e, String p)
    {
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
        return joined.toString();
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
    }
/**
 * Edits user's information in the database.
 */
    public void changeSettings()
    {
        QAModel.prepareSQL(sql_changeSettings, nick, email, password, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public void setAvatar(byte[] avatar)
    {
        QAModel.prepareSQL(sql_setAvatar, avatar, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
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
        if (!QAModel.retrieveSingleObject(u))
            u = null;
        QAModel.closeComponents();
        return u;
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
        if (!QAModel.retrieveSingleObject(u))
            u = null;
        QAModel.closeComponents();
        return u;
    }
    public String getAvatar()
    {
        QAModel.prepareSQL(sql_getAvatar, id);
        byte[] bytearray = QAModel.retrieveByteArray(1);
        QAModel.closeComponents();
        if (bytearray == null)  return null;
        return new String(Base64.encode(bytearray));
    }
    public void removeFromDatabase()
    {
        QAModel.prepareSQL(sql_removeFromDB, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public int getAnscount()
    {
        QAModel.prepareSQL(sql_countAnswers, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
    public int getQuescount()
    {
        QAModel.prepareSQL(sql_countQuestions, id);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
/**
 * Adds answer's flag count by one.
 */
    public void addFlagToAnswer(Answer ans)
    {
        QAModel.prepareSQL(sql_addAnswerFlag, id, ans.getID());
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public void removeFlagFromAnswer(Answer ans)
    {
        QAModel.prepareSQL(sql_undoAnswerFlag, id, ans.getID());
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public boolean hasRated(Answer ans)
    {
        QAModel.prepareSQL(sql_hasRated, id, ans.getID());
        boolean result = QAModel.resultFound();
        QAModel.closeComponents();
        return result;
    }
    public boolean hasFlaggedAnswer(Answer ans)
    {
        QAModel.prepareSQL(sql_hasFlaggedAnswer, id, ans.getID());
        boolean result = QAModel.resultFound();
        QAModel.closeComponents();
        return result;
    }
/**
 * Adds question's flag count by one.
 */
    public void addFlagToQuestion(Question ques)
    {
        QAModel.prepareSQL(sql_addQuestionFlag, id, ques.getID());
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public void removeFlagFromQuestion(Question ques)
    {
        QAModel.prepareSQL(sql_undoQuestionFlag, id, ques.getID());
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public boolean hasFlaggedQuestion(Question ques)
    {
        QAModel.prepareSQL(sql_hasFlaggedQuestion, id, ques.getID());
        boolean result = QAModel.resultFound();
        QAModel.closeComponents();
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
