
package QuesAns.Servlets;

import QuesAns.Models.User;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for registering a user.
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
                User registering = new User(username, email);
                registering.register(password);
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
/**
 * Checks if site is first visited and there's no form to process.
 * @param request
 * @return true if form is not to be processed.
 * @throws ServletException
 * @throws IOException 
 */
    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return request.getParameter("username") == null || request.getParameter("email") == null ||
                request.getParameter("password") == null || request.getParameter("password2") == null;
    }
/**
 * Finds errors from the posted form.
 * @param username User nickname
 * @param email User email
 * @param password User password
 * @param password2 Retyped password
 * @return list of errors.
 */
    private List<String> searchForErrors(String username, String email, String password, String password2)
    {
        List<String> errors = new ArrayList<String>();
        if (username.length() < 3)  errors.add(Error.regNameTooShort);
        if (username.length() > 16) errors.add(Error.regNameTooLong);
        else
        {
            if (Tools.stringOnlyWhitespace(username))    errors.add(Error.regNameOnlyWhitespaces);
            String accepted = Tools.lowerCaseLetters + Tools.upperCaseLetters +
                    Tools.foreignLatinLetters + Tools.numbers + "_-+?!#%&/()=@£${[]},.;:|*";
            if (!Tools.stringHasOnlyDeterminedCharacters(username, accepted))
                errors.add(Error.regNameHasInvalidCharacter(Tools.getInvalidChar(username, accepted)));
        }
        if (email.length() < 3)  errors.add(Error.regEmailTooShort);
        if (email.length() > 64) errors.add(Error.regEmailTooLong);
        else
        {
            if (email.contains(" "))    errors.add(Error.regEmailHasWhitespace);
            if (!email.contains("@"))    errors.add(Error.regEmailMustHaveAt);
            String accepted = Tools.lowerCaseLetters + Tools.upperCaseLetters +
                    Tools.foreignLatinLetters + Tools.numbers + "_-+?!#%&/()=@£${[]},.;:|*";
            if (!Tools.stringHasOnlyDeterminedCharacters(email, accepted))
                errors.add(Error.regEmailHasInvalidCharacter(Tools.getInvalidChar(email, accepted)));
        }
        if (password.length() < 7)  errors.add(Error.regPasswordTooShort);
        if (password.length() > 128)    errors.add(Error.regPasswordTooLong);
        else
        {
            if (!Tools.stringHasUpperCaseCharacters(password))
                errors.add(Error.regPasswordNoUpperCase);
            if (!Tools.stringHasLowerCaseCharacters(password))
                errors.add(Error.regPasswordNoLowerCase);
            if (Tools.stringHasOnlyDeterminedCharacters(password, Tools.lowerCaseLetters + Tools.upperCaseLetters))
                errors.add(Error.regPasswordSpecialCharacter);
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
        return "Servlet for registering a user.";
    }

}
