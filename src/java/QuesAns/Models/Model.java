
package QuesAns.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for all models in the app.
 * @author Pavel
 */
public interface Model {
    public void getObjectFromResults(ResultSet result) throws SQLException;
    public Model newModel();
}
