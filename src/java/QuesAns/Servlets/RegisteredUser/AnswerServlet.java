
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
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
        String stringId = request.getParameter("id");
        int quesId = -1;
        try {
            quesId = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {}
        Question ques = Question.getByID(quesId);
        request.setAttribute("objectFromID", ques);
        String answer = request.getParameter("answer");
        if (answer != null)
        {
            new Answer(answer).addToDatabase(loggedIn, ques);
            response.sendRedirect("question?id=" + quesId);
        }
        else
            showPage("answer.jsp", request, response);
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
