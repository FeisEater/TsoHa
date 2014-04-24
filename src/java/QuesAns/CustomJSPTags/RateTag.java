
package QuesAns.CustomJSPTags;

import QuesAns.Models.Answer;
import QuesAns.Models.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Pavel
 */
public class RateTag extends SimpleTagSupport {
    private User loggedIn;
    private Map<Answer, Boolean> rated;
    private Answer ans;
    public void setLoggedIn(User li)
    {
        loggedIn = li;
    }
    public void setRateList(Map<Answer, Boolean> rl)
    {
        rated = (rl == null ? new HashMap<Answer, Boolean>() : rl);
    }
    public void setAnswer(Answer a)
    {
        ans = a;
    }
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        String showRateButtons = "<a href=\"rate?type=t&id=" +
                ans.getID() + "\" type=\"button\" class=\"btn btn-xs btn-default\">"
                + "<span class=\"glyphicon glyphicon-thumbs-up\"></span></a>" +
                "<a href=\"rate?type=f&id=" + ans.getID() +
                "\" type=\"button\" class=\"btn btn-xs btn-default\">"
                + "<span class=\"glyphicon glyphicon-thumbs-down\"></span></a>";
        String showUndoButton = "<a href=\"rate?type=f&id=" + ans.getID() + 
                "\" type=\"button\" class=\"btn btn-xs btn-default\">Undo</a>";
        if (loggedIn == null)
        {
            if (rated.containsKey(ans))
                out.println(showUndoButton);
            else
                out.println(showRateButtons);
            return;
        }
        if (loggedIn.hasRated(ans))
            out.println(showUndoButton);
        else
            out.println(showRateButtons);
    }
}
