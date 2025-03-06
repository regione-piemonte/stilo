// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import com.engiweb.mcframework.kernel.DBManager;
import javax.servlet.http.HttpServlet;

public class DBManagerStarter extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html";
    
    public void init() throws ServletException {
        final String pathDbConfig = this.getServletContext().getInitParameter("pathDbConfig");
        final String realPath = this.getServletContext().getRealPath(pathDbConfig);
        try {
            DBManager.setFilePath(pathDbConfig, realPath);
            DBManager.register((Object)this);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        final PrintWriter pw = response.getWriter();
        pw.println("DBManager configurato");
    }
}
