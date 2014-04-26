
package QuesAns.Servlets;

import QuesAns.Models.User;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import java.io.IOException;
import javax.servlet.ServletException;
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
        if (firstTimeVisiting(request))
            showPage("signin.jsp", request, response);
        else
        {
            User loggedIn = User.getByLoginInfo(username, password);
            if (loggedIn == null)
            {
                setError(Error.loginFail, request, response);
                request.setAttribute("givenName", username);
                showPage("signin.jsp", request, response);
            }
            else
            {
                setNotification(Info.loginSuccess(username), request, response);
                session.setAttribute("loggedIn", loggedIn);
                response.sendRedirect(getPrevURL(request, response));
            }
        }
    }

    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return request.getParameter("username") == null || request.getParameter("password") == null;
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
