// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import java.util.Date;
import eng.util.XMLUtil;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import eng.database.modal.EngConnectionForSelection;

public class TableErrorManager
{
    private static final String LOCATION_SESSIONE_SCADUTA = "/jsp/comuni/VerificaSessione.jsp?errore=interno";
    private static final String LOCATION_DISPATCHER = "/jsp/comuni/VerificaSessioneScaduta.jsp?errore=interno";
    private static final String SCRIPT_ALERT_ERROR_1 = "<script> alert(\"";
    private static final String SCRIPT_ALERT_ERROR_2 = "\"); opener.document.form1.submit(); window.history.go(-1);</script>";
    
    public static void executeSessioneScaduta(final EngConnectionForSelection connection, final HttpServletRequest request, final HttpServletResponse response, final Exception ex) throws IOException {
        request.setAttribute("sessioneScaduta", (Object)"1");
        execute(connection, request, response, ex, "/jsp/comuni/VerificaSessione.jsp?errore=interno");
    }
    
    public static void executeDispatcher(final EngConnectionForSelection connection, final HttpServletRequest request, final HttpServletResponse response, final Exception ex) throws IOException {
        execute(connection, request, response, ex, "/jsp/comuni/VerificaSessioneScaduta.jsp?errore=interno");
    }
    
    public static void viewAlert(final HttpServletRequest request, final HttpServletResponse response, final Exception ex) throws IOException {
        final PrintWriter out = response.getWriter();
        final String message = ex.getMessage();
        out.println("<script> alert(\"" + message + "\"); opener.document.form1.submit(); window.history.go(-1);</script>");
    }
    
    private static void execute(final EngConnectionForSelection connection, final HttpServletRequest request, final HttpServletResponse response, final Exception ex, final String redirectLocation) throws IOException {
        if (connection != null) {
            try {
                connection.rollback();
            }
            catch (Exception ex2) {}
        }
        final String message = XMLUtil.nvl(ex.getMessage(), "Errore sconosciuto, probabilmente NullPointerException.");
        request.getSession().setAttribute("errore", (Object)message);
        response.sendRedirect(request.getContextPath() + redirectLocation);
    }
    
    private static String makeMessage(final HttpServletRequest req, final Exception ex) {
        final StringBuffer m = new StringBuffer();
        m.append(new Date().toString());
        m.append("\n - Method: " + req.getMethod());
        m.append("\n - Request URI: " + req.getRequestURI());
        m.append("\n - PathInfo: " + req.getPathInfo());
        m.append("\n - Remote Address: " + req.getRemoteAddr());
        m.append("\n - Exception: " + ex.getMessage());
        m.append("\n* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return m.toString();
    }
}
