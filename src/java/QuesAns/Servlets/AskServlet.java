
package QuesAns.Servlets;

import QuesAns.Models.Question;
import QuesAns.Models.Tag;
import QuesAns.Models.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FeisEater
 */
public class AskServlet extends QAServlet {

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
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        String tagline = request.getParameter("tags");
        String[] tags = null;
        if (tagline != null)
            tags = tagline.split(" ");
        
        if (!(title == null && body == null))
        {
            Question q = new Question(title, body);
            q.addToDatabase(loggedIn);
            for (String t : tags)
                new Tag(t).addToDatabase(q);
            request.setAttribute("objectFromID", q);
            response.sendRedirect("question");
        }
        else
        {
            //request.setAttribute("errorMessage", "Invalid input.");
            showPage("ask.jsp", request, response);
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
