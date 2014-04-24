
package QuesAns.CustomJSPTags;

import QuesAns.Models.Question;
import QuesAns.Models.User;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Pavel
 */
public class FlagQuestionTag extends SimpleTagSupport {
    private User loggedIn;
    private Question ques;
    public void setLoggedIn(User li)
    {
        loggedIn = li;
    }
    public void setQuestion(Question q)
    {
        ques = q;
    }
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        if (loggedIn.hasFlaggedQuestion(ques))
            out.println("Unflag <span class=\"glyphicon glyphicon-flag\"></span>");
        else
            out.println("Flag as inappropriate <span class=\"glyphicon glyphicon-flag\"></span>");
    }
}
