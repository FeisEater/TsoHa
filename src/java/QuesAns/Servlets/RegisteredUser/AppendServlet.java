
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FeisEater
 */
public class AppendServlet extends QAServlet {

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
        if (loggedIn == null)
        {
            setError(Error.ansNotLoggedIn, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }

        Answer ans = Answer.getByID(Tools.stringToInt(request.getParameter("id")));
        if (ans == null)
        {
            setError(Error.invalidAnswer, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        if (ans.getAnswerer().getID() != loggedIn.getID())
        {
            setError(Error.appendNotYourAnswer, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        
        request.setAttribute("objectFromID", ans);
        if (firstTimeVisiting(request))
        {
            showPage("append.jsp", request, response);
            return;
        }

        String update = request.getParameter("answer");
        List<String> errors = searchForErrors(update);
        if (errors.isEmpty())
        {
            ans.appendAnswer(update);
            setNotification(Info.appendSuccess, request, response);
            response.sendRedirect("question?id=" + ans.getQuestion().getID());
        }
        else
        {
            setErrors(errors, request, response);
            showPage("append.jsp", request, response);
        }
    }

    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return request.getParameter("answer") == null;
    }

    private List<String> searchForErrors(String append)
    {
        List<String> errors = new ArrayList<String>();
        if (append.isEmpty())    errors.add(Error.appendEmpty);
        if (append.length() > 65536)
            errors.add(Error.appendTooLong);
        else
        {
            if (Tools.stringOnlyWhitespace(append))
                errors.add(Error.appendOnlyWhitespace);
        }
        return errors;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
