
package QuesAns.CustomJSPTags;

import QuesAns.utils.Tools;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * JSP tag used to count the pages for list's length.
 * @author Pavel
 */
public class CountPagesTag extends SimpleTagSupport {
    private int size;
    public void setSize(int i)
    {
        size = i;
    }
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        out.println(((int)Math.floor((size-1)/Tools.elementsPerPage) + 1));
    }
}
