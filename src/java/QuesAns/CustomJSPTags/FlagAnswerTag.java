
package QuesAns.CustomJSPTags;

import QuesAns.Models.Answer;
import QuesAns.Models.User;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Pavel
 */
public class FlagAnswerTag extends SimpleTagSupport {
    private User loggedIn;
    private Answer ans;
    public void setLoggedIn(User li)
    {
        loggedIn = li;
    }
    public void setAnswer(Answer a)
    {
        ans = a;
    }
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        if (loggedIn.hasFlaggedAnswer(ans))
            out.println("Unflag");
        else
            out.println("<span class=\"glyphicon glyphicon-flag\"></span>");
    }
}
