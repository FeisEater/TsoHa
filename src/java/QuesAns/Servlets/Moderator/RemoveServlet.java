
package QuesAns.Servlets.Moderator;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import QuesAns.Models.Tag;
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
        String typeParam = request.getParameter("type");
        String idParam = request.getParameter("id");
        int id = -1;
        try {id = Integer.parseInt(idParam);} catch (Throwable e){}
        if (typeParam.equals("ques"))
        {
            Question q = Question.getByID(id);
            if (q != null)  q.removeFromDatabase();
            response.sendRedirect("modquestions");
        }
        if (typeParam.equals("ans"))
        {
            Answer a = Answer.getByID(id);
            if (a != null)  a.removeFromDatabase();
            response.sendRedirect("modanswers");
        }
        if (typeParam.equals("user"))
        {
            User u = User.getByID(id);
            if (u != null)  u.removeFromDatabase();
            response.sendRedirect("modusers");
        }
        if (typeParam.equals("tags"))
        {
            Tag t = Tag.getByID(id);
            if (t != null)  t.removeFromDatabase();
            response.sendRedirect("modtags");
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
