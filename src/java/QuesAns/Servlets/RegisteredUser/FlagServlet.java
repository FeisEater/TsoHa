
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for flagging a question or answer.
 * @author Pavel
 */
public class FlagServlet extends QAServlet {

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
        HttpSession session = request.getSession();
        User loggedIn = (User)session.getAttribute("loggedIn");
        if (loggedIn == null)
        {
            setError(Error.flagNotLoggedIn, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        
        String typeParam = request.getParameter("type");
        int id = Tools.stringToInt(request.getParameter("id"));
        if (typeParam.equals("ques"))
        {
            Question q = Question.getByID(id);
            if (q == null)
                setError(Error.flagInvalidQues, request, response);
            else
            {
                if (loggedIn.hasFlaggedQuestion(q))
                {
                    loggedIn.removeFlagFromQuestion(q);
                    setNotification(Info.flagQuestionUndo, request, response);
                }
                else
                {
                    loggedIn.addFlagToQuestion(q);
                    setNotification(Info.flagQuestion, request, response);
                }
            }
        }
        else if (typeParam.equals("ans"))
        {
            Answer a = Answer.getByID(id);
            if (a == null)
                setError(Error.flagInvalidAns, request, response);
            else
            {
                if (loggedIn.hasFlaggedAnswer(a))
                {
                    loggedIn.removeFlagFromAnswer(a);
                    setNotification(Info.flagAnswerUndo, request, response);
                }
                else
                {
                    loggedIn.addFlagToAnswer(a);
                    setNotification(Info.flagAnswer, request, response);
                }
            }
        }
        else
            setError(Error.invalidUrl, request, response);
        response.sendRedirect(getPrevURL(request, response));
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for flagging a question or answer.";
    }

}
