
package QuesAns.Servlets;

import QuesAns.Models.Question;
import QuesAns.Models.Tag;
import QuesAns.Models.User;
import QuesAns.utils.Error;
import QuesAns.utils.Info;
import QuesAns.utils.Tools;
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
        saveURL(request, response);
        
        if (firstTimeVisiting(request))
            showPage("ask.jsp", request, response);
        else
        {
            String title = request.getParameter("title");
            String body = request.getParameter("body");
            String[] tags = extractTags(request);
            List<String> errors = searchForErrors(title, body, tags);
            if (errors.isEmpty())
            {
                Question q = new Question(title, body);
                q.addToDatabase(loggedIn);
                for (String t : tags)
                    new Tag(t).addToDatabase(q);
                setNotification(Info.quesSuccess, request, response);
                response.sendRedirect("question?id=" + q.getID());
            }
            else
            {
                setErrors(errors, request, response);
                showPage("ask.jsp", request, response);
            }
        }
    }
    
    private boolean firstTimeVisiting(HttpServletRequest request)
            throws ServletException, IOException {
        return request.getParameter("title") == null || request.getParameter("body") == null ||
                request.getParameter("tags") == null;
    }

    private String[] extractTags(HttpServletRequest request)
            throws ServletException, IOException {
        String tagline = request.getParameter("tags").toLowerCase();
        String[] tags = {};
        if (!tagline.isEmpty())
            tags = tagline.split(" ");
        return tags;
    }
    
    private List<String> searchForErrors(String title, String body, String[] tags)
    {
        List<String> result = new ArrayList<String>();
        if (title.isEmpty())    result.add(Error.quesTitleEmpty);
        if (title.length() > 96)
            result.add(Error.quesTitleTooLong);
        else
        {
            if (Tools.stringOnlyWhitespace(title))
                result.add(Error.quesTitleOnlyWhitespaces);
        }
        if (body.length() > 65536)
            result.add(Error.quesBodyTooLong);
        if (tags.length > 1024)
            result.add(Error.quesTooManyTags);
        else
        {
            for (String tag : tags)
            {
                if (tag.length() > 12)
                    result.add(Error.quesTagTooLong(tag.substring(0, 32)));
                else
                {
                    String accepted = Tools.lowerCaseLetters + Tools.numbers + "_";
                    if (!Tools.stringHasOnlyDeterminedCharacters(tag, accepted))
                        result.add(Error.quesTagHasInvalidCharacter(tag, Tools.getInvalidChar(tag, accepted)));
                }
            }
        }
        return result;
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
