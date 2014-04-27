
package QuesAns.Servlets.Moderator;

import QuesAns.Models.Tag;
import QuesAns.Models.User;
import QuesAns.Servlets.QAServlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for listing tags for moderator to see.
 * @author FeisEater
 */
public class ListTagsServlet extends QAServlet {

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
        if (loggedIn == null || !loggedIn.getModerator())
        {
            setError(QuesAns.utils.Error.modPage, request, response);
            response.sendRedirect(getPrevURL(request, response, true));
            return;
        }
        saveURL(request, response);
        List<Tag> tags = Tag.getAllTags();
        int colheight = (int)Math.ceil((double)(tags.size() / 6.0));
        request.setAttribute("taglist", subList(tags, 0, colheight));
        request.setAttribute("taglist2", subList(tags, colheight, 2*colheight));
        request.setAttribute("taglist3", subList(tags, 2*colheight, 3*colheight));
        request.setAttribute("taglist4", subList(tags, 3*colheight, 4*colheight));
        request.setAttribute("taglist5", subList(tags, 4*colheight, 5*colheight));
        request.setAttribute("taglist6", subList(tags, 5*colheight, tags.size()));
        showPage("modtags.jsp", request, response);
    }
/**
 * Same as List.subList(begin, end), except it is 'out of bounds'-proof.
 * @param tags List of tags.
 * @param begin beginning index.
 * @param end ending index
 * @return sublist.
 */
    private List<Tag> subList(List<Tag> tags, int begin, int end)
    {
        if (begin >= tags.size())   return null;
        if (end > tags.size()) end = tags.size();
        return tags.subList(begin, end);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for listing tags for moderator to see.";
    }

}
