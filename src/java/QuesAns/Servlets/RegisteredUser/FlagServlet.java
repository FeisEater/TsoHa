
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
import javax.servlet.http.HttpSession;

/**
 *
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
        String typeParam = request.getParameter("type");
        String idParam = request.getParameter("id");
        int id = -1;
        try {id = Integer.parseInt(idParam);} catch (Throwable e){}
        if (typeParam.equals("ques"))
        {
            Question q = Question.getByID(id);
            if (q != null)  q.addFlag();
        }
        else if (typeParam.equals("ans"))
        {
            Answer a = Answer.getByID(id);
            if (a != null)  a.addFlag();
        }
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
