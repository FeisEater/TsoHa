
package QuesAns.Models;

import QuesAns.utils.Tools;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.apache.catalina.util.Base64;

/**
 *
 * @author FeisEater
 */
public class User implements Model {
    private int id;
    private String nick;
    private String email;
    private Timestamp joined;
    private boolean moderator;
    
    private static final String sql_getAllUsers =
            "SELECT r_id, nick, email, joined, moderator from regusers order by joined desc limit ? offset ?";
    
    private static final String sql_countUsers = "SELECT count(*) from regusers";
    
    private static final String sql_getSalt = "SELECT salt from regusers where (nick = ? or email = ?)";
    
    private static final String sql_getUserByPassword =
            "SELECT r_id, nick, email, joined, moderator from regusers where password = ?";

    private static final String sql_registerUser =
            "INSERT INTO regusers(nick, email, password, salt, joined) "
            + "VALUES(?,?,?,?,LOCALTIMESTAMP) RETURNING r_id, joined";
    
    private static final String sql_changeSettings =
            "UPDATE regusers SET nick = ?, email = ? where r_id = ?";

    private static final String sql_changePassword =
            "UPDATE regusers SET password = ?, salt = ? where r_id = ?";

    private static final String sql_getByID =
            "SELECT r_id, nick, email, joined, moderator from regusers where r_id = ?";

    private static final String sql_getQuestions =
            "SELECT * from questions where r_id = ? order by asked desc limit ? offset ?";

    private static final String sql_getAnswers =
            "SELECT * from answers where r_id = ? order by answered desc limit ? offset ?";
    
    private static final String sql_getAvatar =
            "SELECT avatar from regusers where r_id = ?";

    private static final String sql_setAvatar =
            "UPDATE regusers SET avatar = ? where r_id = ?";

    private static final String sql_removeFromDB =
            "DELETE FROM regusers WHERE r_id = ?";
    
    private static final String sql_notifyQuestionsAboutBan =
            "UPDATE questions SET askerbanned = true where r_id = ?";
    
    private static final String sql_countAnswers =
            "SELECT count(*) from answers where r_id = ?";

    private static final String sql_countQuestions =
            "SELECT count(*) from questions where r_id = ?";
    
    private static final String sql_addAnswerFlag =
            "INSERT INTO ratedflaggedanswers(r_id, a_id, flagged) "
            + "VALUES(?,?,true)";

    private static final String sql_undoAnswerFlag =
            "DELETE FROM ratedflaggedanswers "
            + "WHERE r_id = ? and a_id = ? and flagged = true";

    private static final String sql_hasRated =
            "SELECT * from ratedflaggedanswers where r_id = ? and a_id = ? and flagged = false";

    private static final String sql_hasFlaggedAnswer =
            "SELECT * from ratedflaggedanswers where r_id = ? and a_id = ? and flagged = true";

    private static final String sql_addQuestionFlag =
            "INSERT INTO flaggedquestions(r_id, q_id) VALUES(?,?)";

    private static final String sql_undoQuestionFlag =
            "DELETE FROM flaggedquestions WHERE r_id = ? and q_id = ?";

    private static final String sql_hasFlaggedQuestion =
            "SELECT * from flaggedquestions where r_id = ? and q_id = ?";
    
    private static final String sql_userExists =
            "SELECT r_id from regusers where r_id = ?";

    public User()   {}
    public User(String n, String e)
    {
        nick = n;
        email = e;
    }
    @Override
    public String toString()
    {
        return ""+nick+" ("+email+") - joined: " + joined;
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
    private static byte[] encryptPassword(String password, byte[] salt)
    {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        } catch (InvalidKeySpecException ex) {
            System.out.println(ex);
        }
        return null;
    }
    private static byte[] newSalt()
    {
        Random random = new Random();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
/**
 * Adds a User to the database.
 */
    public void register(String password)
    {
        byte[] salt = newSalt();
        QAModel.prepareSQL(sql_registerUser, nick, email, encryptPassword(password, salt), salt);
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
        QAModel.prepareSQL(sql_changeSettings, nick, email, id);
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public void changePassword(String password)
    {
        byte[] salt = newSalt();
        QAModel.prepareSQL(sql_changePassword, encryptPassword(password, salt), salt, id);
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
    public List<Question> getQuestions(int page)
    {
        QAModel.prepareSQL(sql_getQuestions, id, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
        List result = QAModel.retrieveObjectList(new Question());
        QAModel.closeComponents();
        return result;
    }
/**
 * Finds all answers made by this user.
 * @return List of Answers.
 */
    public List<Answer> getAnswers(int page)
    {
        QAModel.prepareSQL(sql_getAnswers, id, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
        List result = QAModel.retrieveObjectList(new Answer());
        QAModel.closeComponents();
        return result;
    }
/**
 * Finds all users.
 * @return List of Users.
 */
    public static List<User> getUsers(int page)
    {
        QAModel.prepareSQL(sql_getAllUsers, Tools.elementsPerPage, (page - 1) * Tools.elementsPerPage);
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
        QAModel.prepareSQL(sql_getSalt, nameoremail, nameoremail);
        byte[] salt = QAModel.retrieveByteArray(1);
        QAModel.prepareSQL(sql_getUserByPassword, encryptPassword(password, salt));
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
        QAModel.prepareSQL(sql_notifyQuestionsAboutBan, id);
        QAModel.executeUpdate();
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
    public boolean stillExists()
    {
        QAModel.prepareSQL(sql_userExists, id);
        boolean result = QAModel.resultFound();
        QAModel.closeComponents();
        return result;
    }
    public static int countUsers()
    {
        QAModel.prepareSQL(sql_countUsers);
        int result = QAModel.retrieveInt(1);
        QAModel.closeComponents();
        return result;
    }
    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("r_id");
        nick = result.getString("nick");
        email = result.getString("email");
        joined = result.getTimestamp("joined");
        moderator = result.getBoolean("moderator");
    }

    public Model newModel()
    {
        return new User();
    }
}
