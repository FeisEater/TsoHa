
package QuesAns.Servlets;

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
public class LoginServlet extends QAServlet {

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
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User loggedIn = User.getByLoginInfo(username, password);
        if (loggedIn == null)
        {
            if (!(username == null && password == null))
            {
                request.setAttribute("errorMessage", "Log in failed. Check your username, email or password.");
                request.setAttribute("givenName", username);
            }
            showPage("signin.jsp", request, response);
        }
        else
        {
            session.setAttribute("loggedIn", loggedIn);
            response.sendRedirect("index");
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
