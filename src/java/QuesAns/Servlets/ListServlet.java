
package QuesAns.Servlets;

import QuesAns.Models.Question;
import QuesAns.Models.User;
import QuesAns.utils.Error;
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
 *
 * @author FeisEater
 */
public class ListServlet extends QAServlet {

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
        getUserFromSession(request, response);
        saveURL(request, response);
        String[] tagParam = getSearchTerms(request, response);
        List<Question> questions;
        if (tagParam != null && tagParam.length > 0)
            questions = Question.getQuestionsByTags(tagParam);
        else
            questions = Question.getQuestions("order by asked desc");
        request.setAttribute("list", questions);
        showPage("index.jsp", request, response);
    }

    private String[] getSearchTerms(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tagString = request.getParameter("tags");
        if (tagString == null)  return null;
        tagString = tagString.toLowerCase().replace("+", " ");
        String t = tagString.replace(" ", "");
        if (t.isEmpty())    return null;
        List<String> errors = searchForErrors(tagString);
        if (!errors.isEmpty())
        {
            setErrors(errors, request, response);
            return null;
        }
        return tagString.split(" ");
    }
    
    private List<String> searchForErrors(String search)
    {
        List<String> errors = new ArrayList<String>();
        if (search.length() > 1024) errors.add(Error.searchTooLong);
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
