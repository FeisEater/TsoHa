
package QuesAns.Servlets.RegisteredUser;

import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        byte[] avatar = null;
        if (isMultipart)
        {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext())
                {
                    FileItem item = iter.next();
                    if (item.isFormField())
                    {
                        if (item.getFieldName().equals("username"))
                            loggedIn.setName(item.getString());
                        if (item.getFieldName().equals("email"))
                            loggedIn.setEmail(item.getString());
                        if (item.getFieldName().equals("password"))
                            loggedIn.setPassword(item.getString());
                    }
                    else
                    {
                        avatar = item.get();
                    }
                }
            } catch (FileUploadException e) {
                System.out.println(e);
            }
            loggedIn.changeSettings(avatar);
            response.sendRedirect("index");
        }
        else
        {
            showPage("accsettings.jsp", request, response);
        }
        /*
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (username == null && email == null && password == null)
        {
            showPage("accsettings.jsp", request, response);
        }
        else
        {
            loggedIn.setName(username);
            loggedIn.setEmail(email);
            loggedIn.setPassword(password);
            loggedIn.changeSettings();
            response.sendRedirect("index");
        }*/
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
