
package QuesAns.Servlets;

import QuesAns.Models.User;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FeisEater
 */
public class RegisterServlet extends QAServlet {

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
        
        if (firstTimeVisiting(request))
        {
            showPage("register.jsp", request, response);
        }
        else
        {
            String username = request.getParameter("username").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password");
            String password2 = request.getParameter("password2");
            List<String> errors = searchForErrors(username, email, password, password2);
            if (errors.isEmpty())
            {
                User registering = new User(username, email, password);
                registering.register();
                session.setAttribute("loggedIn", registering);
                setNotification(Info.registerSuccess(username), request, response);
                response.sendRedirect("index");
            }
            else
            {
                setErrors(errors, request, response);
                showPage("register.jsp", request, response);
            }
        }
    }

    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return request.getParameter("username") == null || request.getParameter("email") == null ||
                request.getParameter("password") == null || request.getParameter("password2") == null;
    }

    private List<String> searchForErrors(String username, String email, String password, String password2)
    {
        List<String> errors = new ArrayList<String>();
        if (username.length() < 3)  errors.add(Error.regNameTooShort);
        if (username.length() > 16) errors.add(Error.regNameTooLong);
        else
        {
            String n = username.replace(" ", "");
            if (n.isEmpty())    errors.add(Error.regNameOnlyWhitespaces);
            String accepted = "abcdefghijklmnopqrstuvwxyzåäö_0123456789-+?!#%&/()=@£${[]},.;:|*"
                    + "áàâãéèëêóòôõíìïîúùüûýÿÁÀÂÃÉÈËÊÓÒÔÕÍÌÏÎÚÙÜÛÝ"
                    + "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
            for (int i = 0; i < accepted.length(); i++)
                n = n.replace(""+accepted.charAt(i), "");
            if (!n.isEmpty())
                errors.add(Error.regNameHasInvalidCharacter(n.charAt(0)));

        }
        if (email.length() < 3)  errors.add(Error.regEmailTooShort);
        if (username.length() > 64) errors.add(Error.regEmailTooLong);
        else
        {
            if (email.contains(" "))    errors.add(Error.regEmailHasWhitespace);
            if (!email.contains("@"))    errors.add(Error.regEmailMustHaveAt);
            String e = email;
            String accepted = "abcdefghijklmnopqrstuvwxyzåäö_0123456789-+?!#%&/()=@£${[]},.;:|*"
                    + "áàâãéèëêóòôõíìïîúùüûýÿÁÀÂÃÉÈËÊÓÒÔÕÍÌÏÎÚÙÜÛÝ"
                    + "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
            for (int i = 0; i < accepted.length(); i++)
                e = e.replace(""+accepted.charAt(i), "");
            if (!e.isEmpty())
                errors.add(Error.regEmailHasInvalidCharacter(e.charAt(0)));
        }
        if (password.length() < 7)  errors.add(Error.regPasswordTooShort);
        if (password.length() > 128)    errors.add(Error.regPasswordTooLong);
        else
        {
            String p = password.toLowerCase();
            if (password.equals(p)) errors.add(Error.regPasswordNoUpperCase);
            p = password.toUpperCase();
            if (password.equals(p)) errors.add(Error.regPasswordNoLowerCase);
            String eliminate = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";
            for (int i = 0; i < eliminate.length(); i++)
                p = p.replace(""+eliminate.charAt(i), "");
            if (p.isEmpty())    errors.add(Error.regPasswordSpecialCharacter);
            if (!password.equals(password2))    errors.add(Error.regPasswordMismatch);
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
        return "Short description";
    }

}
