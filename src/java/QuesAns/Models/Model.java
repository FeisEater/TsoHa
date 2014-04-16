
package QuesAns.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pavel
 */
public interface Model {
    public void getObjectFromResults(ResultSet result) throws SQLException;
    public Model newModel();
}
