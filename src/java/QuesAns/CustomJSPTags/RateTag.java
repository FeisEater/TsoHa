
package QuesAns.CustomJSPTags;

import QuesAns.Models.Answer;
import QuesAns.Models.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Pavel
 */
public class RateTag extends SimpleTagSupport {
    private User loggedIn;
    private List<Answer> rated;
    private Answer ans;
    public void setLoggedIn(User li)
    {
        loggedIn = li;
    }
    public void setRateList(List<Answer> rl)
    {
        rated = (rl == null ? new ArrayList<Answer>() : rl);
    }
    public void setAnswer(Answer a)
    {
        ans = a;
    }
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        String showRateButtons = "<a href=\"rate?type=t&id=${unit.ID}\" type=\"button\" class=\"btn btn-xs btn-default\">"
                + "<span class=\"glyphicon glyphicon-thumbs-up\"></span></a>" +
                "<a href=\"rate?type=f&id=${unit.ID}\" type=\"button\" class=\"btn btn-xs btn-default\">"
                + "<span class=\"glyphicon glyphicon-thumbs-down\"></span></a>";
        if (loggedIn == null)
        {
            if (rated.contains(ans))
                out.println("Undo");
            else
                out.println(showRateButtons);
            return;
        }
        if (loggedIn.hasFlaggedAnswer(ans))
            out.println("Undo");
        else
            out.println(showRateButtons);
    }
}
