
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for answering question.
 * @author FeisEater
 */
public class AnswerServlet extends QAServlet {

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
        
        int quesId = Tools.stringToInt(request.getParameter("id"));
        Question ques = Question.getByID(quesId);
        if (ques == null)
        {
            setError(Error.invalidQuesToAnswer, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        
        request.setAttribute("objectFromID", ques);
        if (firstTimeVisiting(request))
        {
            showPage("answer.jsp", request, response);
            return;
        }

        String answer = request.getParameter("answer");
        List<String> errors = searchForErrors(answer);
        if (errors.isEmpty())
        {
            new Answer(answer).addToDatabase(loggedIn, ques);
            setNotification(Info.answerSuccess, request, response);
            response.sendRedirect("question?id=" + quesId);
        }
        else
        {
            setErrors(errors, request, response);
            showPage("answer.jsp", request, response);
        }
    }
/**
 * Checks if site is first visited and there's no form to process.
 * @param request
 * @return true if form is not to be processed.
 * @throws ServletException
 * @throws IOException 
 */
    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return request.getParameter("answer") == null;
    }
/**
 * Finds errors from the posted form.
 * @param answer answer text
 * @return list of errors.
 */
    private List<String> searchForErrors(String answer)
    {
        List<String> errors = new ArrayList<String>();
        if (answer.isEmpty())    errors.add(Error.ansEmpty);
        if (answer.length() > 65536)
            errors.add(Error.ansTooLong);
        else
        {
            if (Tools.stringOnlyWhitespace(answer))
                errors.add(Error.ansOnlyWhitespaces);
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
        return "Servlet for answering question.";
    }

}
