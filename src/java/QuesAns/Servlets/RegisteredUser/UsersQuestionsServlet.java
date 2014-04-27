
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for listing user's questions.
 * @author FeisEater
 */
public class UsersQuestionsServlet extends QAServlet {

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
            setError(QuesAns.utils.Error.accNotLoggedIn, request, response);
            response.sendRedirect(getPrevURL(request, response, true));
            return;
        }
        saveURL(request, response);
        int page = Tools.stringToInt(request.getParameter("page"));
        if (page <= 0)  page = 1;
        List<Question> questions = loggedIn.getQuestions(page);
        request.setAttribute("list", questions);
        showPage("accquestions.jsp", request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for listing user's answers.";
    }

}
