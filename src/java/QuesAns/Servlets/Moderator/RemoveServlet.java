
package QuesAns.Servlets.Moderator;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.Tag;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet of removing a model object.
 * @author Pavel
 */
public class RemoveServlet extends QAServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        preprocess(request, response);
        User loggedIn = getUserFromSession(request, response);
        if (loggedIn == null || !loggedIn.getModerator())
        {
            setError(Error.modCantRemove, request, response);
            prevPage(request, response);
            return;
        }
        String typeParam = request.getParameter("type");
        int id = Tools.stringToInt(request.getParameter("id"));
        if (typeParam.equals("ques"))
        {
            Question q = Question.getByID(id);
            if (q == null)
                setError(Error.modNoQues, request, response);
            else
            {
                q.removeFromDatabase();
                setNotification(Info.modQuesRemoved(q.getTitle()), request, response);
            }
        }
        else if (typeParam.equals("ans"))
        {
            Answer a = Answer.getByID(id);
            if (a == null)
                setError(Error.modNoAns, request, response);
            else
            {
                a.removeFromDatabase();
                setNotification(Info.modAnsRemoved(a.getShortBody()), request, response);
            }
        }
        else if (typeParam.equals("user"))
        {
            User u = User.getByID(id);
            if (u == null)
                setError(Error.modNoUser, request, response);
            else
            {
                u.removeFromDatabase();
                setNotification(Info.modUserBanned(u.getName()), request, response);
            }
        }
        else if (typeParam.equals("tags"))
        {
            Tag t = Tag.getByID(id);
            if (t == null)
                setError(Error.modNoTag, request, response);
            else
            {
                t.removeFromDatabase();
                setNotification(Info.modTagRemoved(t.getText()), request, response);
            }
        }
        else
            setError(Error.invalidUrl, request, response);
        prevPage(request, response);
    }
/**
 * Redirects to previously saved page.
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException 
 */
    private void prevPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(getPrevURL(request, response));
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet of removing a model object.";
    }

}
