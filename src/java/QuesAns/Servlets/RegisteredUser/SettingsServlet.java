
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

/**
 *
 * @author FeisEater
 */
public class SettingsServlet extends QAServlet {

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
        if (loggedIn == null)
        {
            setError(Error.settingsNotLoggedIn, request, response);
            response.sendRedirect(getPrevURL(request, response));
            return;
        }
        
        if (firstTimeVisiting(request))
        {
            showPage("accsettings.jsp", request, response);
            return;
        }

        String password = "";
        String password2 = "";
        byte[] avatar = null;
        List<String> errors = new ArrayList<String>();
        List<String> infos = new ArrayList<String>();
        List<FileItem> items = getItems(request, errors);
        if (items == null)
        {
            setErrors(errors, request, response);
            response.sendRedirect("accsettings");
            return;
        }
        for (FileItem item : items)
        {
            if (item.isFormField())
            {
                if (item.getFieldName().equals("username") && !item.getString().isEmpty())
                {
                    String username = item.getString();
                    List<String> e = searchForUsernameErrors(username);
                    errors.addAll(e);
                    if (e.isEmpty())
                    {
                        infos.add(Info.nameChanged(username));
                        loggedIn.setName(username);
                    }
                }
                if (item.getFieldName().equals("email") && !item.getString().isEmpty())
                {
                    String email = item.getString();
                    List<String> e = searchForEmailErrors(email);
                    errors.addAll(e);
                    if (e.isEmpty())
                    {
                        infos.add(Info.emailChanged(email));
                        loggedIn.setEmail(email);
                    }
                }
                if (item.getFieldName().equals("password"))
                    password = item.getString();
                if (item.getFieldName().equals("password2"))
                    password2 = item.getString();
            }
            else
            {
                List<String> e = searchForAvatarErrors(item);
                errors.addAll(e);
                if (e.isEmpty())
                {
                    avatar = item.get();
                    if (avatarChanged(avatar))
                        infos.add(Info.avatarChanged);
                }
            }
        }
        if (!password.isEmpty())
        {
            List<String> e = searchForPasswordErrors(password, password2);
            errors.addAll(e);
            if (e.isEmpty())
            {
                infos.add(Info.passwordChanged);
                loggedIn.setPassword(password);
            }
        }
        if (!errors.isEmpty())
            setErrors(errors, request, response);
        if (!infos.isEmpty())
            setNotifications(infos, request, response);
        loggedIn.changeSettings();
        if (avatarChanged(avatar))    loggedIn.setAvatar(avatar);
        if (errors.isEmpty())
            response.sendRedirect("accquestions");
        else
            showPage("accsettings.jsp", request, response);
    }

    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return !ServletFileUpload.isMultipartContent(request);
    }

    private List<FileItem> getItems(HttpServletRequest request, List<String> errors)
            throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 256);
        try {
            List<FileItem> items = upload.parseRequest(request);
            return items;
        } catch (SizeLimitExceededException e) {
            errors.add(Error.avatarTooBig);
            return null;
        } catch (FileUploadException e) {
            return null;
        }
    }
    
    private boolean avatarChanged(byte[] avatar)
    {
        return avatar != null && avatar.length > 0;
    }
    
    private List<String> searchForUsernameErrors(String username)
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
        return errors;
    }
    private List<String> searchForEmailErrors(String email)
    {
        List<String> errors = new ArrayList<String>();
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
        return errors;
    }
    private List<String> searchForPasswordErrors(String password, String password2)
    {
        List<String> errors = new ArrayList<String>();
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
    private List<String> searchForAvatarErrors(FileItem item)
    {
        List<String> errors = new ArrayList<String>();
        if (!avatarChanged(item.get())) return errors;
        if (item.getSize() > 1024 * 256)    errors.add(Error.avatarTooBig);
        if (!item.getContentType().contains("image/")) errors.add(Error.avatarNotPicture);
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
