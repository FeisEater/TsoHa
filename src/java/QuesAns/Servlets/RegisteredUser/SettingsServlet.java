
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

/**
 * Servlet for changing user settings.
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
            response.sendRedirect(getPrevURL(request, response, true));
            return;
        }
        
        if (firstTimeVisiting(request))
        {
            showPage("accsettings.jsp", request, response);
            return;
        }

        String password = "";
        String password2 = "";
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
                    changeName(loggedIn, item, errors, infos);
                if (item.getFieldName().equals("email") && !item.getString().isEmpty())
                    changeEmail(loggedIn, item, errors, infos);
                if (item.getFieldName().equals("password"))
                    password = item.getString();
                if (item.getFieldName().equals("password2"))
                    password2 = item.getString();
            }
            else
                changeAvatar(loggedIn, item, errors, infos);
        }
        if (!password.isEmpty())
            changePassword(loggedIn, password, password2, errors, infos);
        
        if (!errors.isEmpty())
            setErrors(errors, request, response);
        if (!infos.isEmpty())
            setNotifications(infos, request, response);
        loggedIn.changeSettings();
        
        if (errors.isEmpty())
            response.sendRedirect("accquestions");
        else
            showPage("accsettings.jsp", request, response);
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
        return !ServletFileUpload.isMultipartContent(request);
    }
/**
 * Retrieves all form items in the page.
 * @param request
 * @param errors error list.
 * @return form item list.
 * @throws ServletException
 * @throws IOException 
 */
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
/**
 * Checks if avatar was changed.
 * @param avatar byte array of picture file.
 * @return true if avatar was changed.
 */
    private boolean avatarChanged(byte[] avatar)
    {
        return avatar != null && avatar.length > 0;
    }
/**
 * Changes username.
 * @param loggedIn logged in user.
 * @param item form item.
 * @param errors error list.
 * @param infos info list.
 */
    private void changeName(User loggedIn, FileItem item, List<String> errors, List<String> infos)
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
/**
 * Changes email.
 * @param loggedIn logged in user.
 * @param item form item.
 * @param errors error list.
 * @param infos info list.
 */
    private void changeEmail(User loggedIn, FileItem item, List<String> errors, List<String> infos)
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
/**
 * Changes avatar.
 * @param loggedIn logged in user.
 * @param item form item.
 * @param errors error list.
 * @param infos info list.
 */
    private void changeAvatar(User loggedIn, FileItem item, List<String> errors, List<String> infos)
    {
        List<String> e = searchForAvatarErrors(item);
        errors.addAll(e);
        if (e.isEmpty())
        {
            if (avatarChanged(item.get()))
            {
                loggedIn.setAvatar(item.get());
                infos.add(Info.avatarChanged);
            }
        }
    }
/**
 * Changes password.
 * @param loggedIn logged in user.
 * @param password password string.
 * @param password2 retyped password.
 * @param errors error list.
 * @param infos info list.
 */
    private void changePassword(User loggedIn, String password, String password2, List<String> errors, List<String> infos)
    {
        List<String> e = searchForPasswordErrors(password, password2);
        errors.addAll(e);
        if (e.isEmpty())
        {
            loggedIn.changePassword(password);
            infos.add(Info.passwordChanged);
        }
    }
/**
 * Searches errors from username string.
 * @param username username string.
 * @return list of errors.
 */
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
/**
 * Searches errors from email string.
 * @param email email string.
 * @return list of errors.
 */
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
/**
 * Searches errors from password strings.
 * @param password password string.
 * @param password2 retyped password.
 * @return list of errors.
 */
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
/**
 * Searches errors from avatar byte array.
 * @param item avatar file.
 * @return list of errors.
 */
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
        return "Servlet for changing user settings.";
    }

}
