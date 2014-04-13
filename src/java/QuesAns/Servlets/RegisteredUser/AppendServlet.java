
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
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
        getUserFromSession(request, response);
        String stringId = request.getParameter("id");
        int ansId = -1;
        try {
            ansId = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {}
        Answer ans = Answer.getByID(ansId);
        request.setAttribute("objectFromID", ans);
        String update = request.getParameter("answer");
        if (update != null)
        {
            ans.appendAnswer(update);
            response.sendRedirect("question?id=" + ans.getQuestion().getID());
        }
        else
            showPage("append.jsp", request, response);
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
