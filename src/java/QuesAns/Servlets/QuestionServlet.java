
package QuesAns.Servlets;

import QuesAns.Models.Question;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pavel
 */
public class QuestionServlet extends QAServlet {

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
        String idParam = request.getParameter("id");
        int id = -1;
        try {id = Integer.parseInt(idParam);} catch (Throwable e){}
        Question q = Question.getByID(id);
        request.setAttribute("objectFromID", q);
        if (q == null)
        {
            request.setAttribute("errorMessage", "Invalid ID");
            response.sendRedirect("index");
        }
        else
        {
            request.setAttribute("list", q.getAnswers());
            request.setAttribute("taglist", q.getTags());
            request.setAttribute("pageTitle", q.getTitle());
            showPage("question.jsp", request, response);
        }
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
