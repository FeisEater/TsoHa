
package QuesAns.Servlets;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.utils.Error;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FeisEater
 */
public class RateServlet extends QAServlet {

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
        String typeParam = request.getParameter("type");
        int id = Tools.stringToInt(request.getParameter("id"));
        Answer a = Answer.getByID(id);
        if (a == null)
        {
            setError(Error.rateInvalidId, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        HttpSession session = request.getSession();
        User loggedIn = (User)session.getAttribute("loggedIn");
        if (loggedIn == null)
        {
            List<Answer> rated = getRatedAnswers(session);
            if (rated.contains(a))
            {
                a.undoRate(null);
                rated.remove(a);
            }
            else
            {
                a.getRated(null, !typeParam.equals("f"));
                rated.add(a);
            }
            session.setAttribute("rated", rated);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        if (loggedIn.hasRated(a))
            a.undoRate(loggedIn);
        else
            a.getRated(loggedIn, !typeParam.equals("f"));
        response.sendRedirect(getPrevURL(request, response));
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
