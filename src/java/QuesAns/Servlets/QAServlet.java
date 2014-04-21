
package QuesAns.Servlets;

import QuesAns.Models.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet super class, which is inherited by every servlet class in this app.
 * @author FeisEater
 */
public abstract class QAServlet extends HttpServlet {
/**
 * Should be called in the first line of servlet code. Sets correct character encoding.
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException 
 */
    public void preprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
    }
/**
 * Retrieves the user currently logged in according to the session.
 * @param request
 * @param response
 * @return User currently logged in.
 * @throws ServletException
 * @throws IOException 
 */
    public User getUserFromSession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        User loggedIn = (User)session.getAttribute("loggedIn");
        return loggedIn;
    }
/**
 * Reads the html code of the specified jsp.
 * @param jsp name of the jsp file
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException 
 */
    public void showPage(String jsp, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
        HttpSession session = request.getSession();
        session.removeAttribute("errors");
        session.removeAttribute("infos");
    }
/**
 * Saves url for this servlet (with query string), so that other servlets can
 * redirect back here.
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException 
 */
    public void saveURL(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String queryPart = (request.getQueryString() == null) ? "" : "?" + request.getQueryString();
        session.setAttribute("prevURL", request.getRequestURI() + queryPart);
    }
/**
 * Gets the previously stored url.
 * @param request
 * @param response
 * @return String of the previously stored URL. If none was stored, return index.
 * @throws ServletException
 * @throws IOException 
 */
    public String getPrevURL(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String prevURL = (String)session.getAttribute("prevURL");
        if (prevURL == null)    return "index";
        return prevURL;
    }
    
    public void setNotification(String notify, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        List<String> infos = new ArrayList<String>();
        infos.add(notify);
        session.setAttribute("infos", infos);
    }
    
    public void setNotifications(List<String> infos, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        session.setAttribute("infos", infos);
    }

    public void setErrors(List<String> errors, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        session.setAttribute("errors", errors);
    }

    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
