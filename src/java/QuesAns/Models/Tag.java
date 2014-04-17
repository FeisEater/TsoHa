
package QuesAns.Models;

import QuesAns.DataBase.QAConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Pavel
 */
public class Tag implements Model {
    private int id;
    private String tag;
    private Timestamp tagged;
    
    private static final String sql_getByID =
            "SELECT * from tags where t_id = ?";

    private static final String sql_findTag =
            "SELECT t_id, firsttagged from tags where tag = ?";

    private static final String sql_addToDB =
            "INSERT INTO tags(tag, firsttagged) "
            + "VALUES(?,LOCALTIMESTAMP) RETURNING t_id, firsttagged";

    private static final String sql_connectTagWithQuestion =
            "INSERT INTO tagstoquestions(t_id, q_id) VALUES(?,?)";

    public Tag() {}
    public Tag(String tag)
    {
        this.tag = tag;
    }
    public String getText()
    {
        return tag;
    }
/**
 * Retrieves specific tag by its ID.
 * @param id Specified ID.
 * @return tag object.
 */
    public static Tag getByID(int id)
    {
        Tag t = new Tag();
        QAModel.prepareSQL(sql_getByID, id);
        if (!QAModel.retrieveSingleObject(t))
            t = null;
        QAModel.closeComponents();
        return t;
    }
/**
 * Adds a tag to the database.
 * @param q Question to which tag is added.
 */
    public void addToDatabase(Question q)
    {
        QAModel.prepareSQL(sql_findTag, tag);
        if (!QAModel.resultFound())
            QAModel.prepareSQL(sql_addToDB, tag);
        id = QAModel.retrieveInt(1);
        tagged = QAModel.retrieveTimestamp(2);
        QAModel.prepareSQL(sql_connectTagWithQuestion, id, q.getID());
        QAModel.executeUpdate();
        QAModel.closeComponents();
    }
    public void getObjectFromResults(ResultSet result) throws SQLException
    {
        id = result.getInt("t_id");
        tag = result.getString("tag");
        tagged = result.getTimestamp("firsttagged");
    }

    public Model newModel()
    {
        return new Tag();
    }
}
