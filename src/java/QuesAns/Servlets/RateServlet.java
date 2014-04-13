
package QuesAns.Servlets;

import QuesAns.Models.Answer;
import QuesAns.Models.Question;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FeisEater
 */
public class RateServlet extends QAServlet {

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
        String typeParam = request.getParameter("type");
        String idParam = request.getParameter("id");
        int id = -1;
        try {id = Integer.parseInt(idParam);} catch (Throwable e){}
        Answer a = Answer.getByID(id);
        if (a != null)  a.rate(!typeParam.equals("f"));
        response.sendRedirect(getPrevURL(request, response));
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
