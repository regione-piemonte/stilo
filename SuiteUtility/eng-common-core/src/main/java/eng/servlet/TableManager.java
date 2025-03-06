// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import eng.database.exception.EngSqlApplException;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import eng.database.definition.ResultSelection;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import eng.database.exception.EngFormException;
import eng.util.Properties;
import eng.database.exception.EngSqlNoApplException;
import eng.database.tablemanager.TableManagerDb;
import eng.util.Logger;
import javax.servlet.http.HttpServlet;

public abstract class TableManager extends HttpServlet
{
    private static final String PROPERTIES_NAME_ACCESS_SCHEMA_NAME = "accessoSchema";
    private static final String LOCATION_SESSIONE_SCADUTA_FINESTRA_2 = "/jsp/comuni/VerificaSessioneScaduta.jsp?errore=interno";
    protected static final String SCRIPT_ALERT_SUCCESS_1 = "<script> alert(\"";
    protected static final String SCRIPT_ALERT_SUCCESS_2 = "\"); opener.document.form1.submit(); window.close();</script>";
    
    public TableManager() {
        Logger.getLogger().info((Object)"BSEO.TableManager.constructor:");
    }
    
    protected abstract TableManagerDb createTableManagerDb() throws EngSqlNoApplException;
    
    protected abstract TableManagerDb createTableManagerDb(final int p0) throws EngSqlNoApplException;
    
    protected abstract Properties makeProperty(final Commarea p0, final String p1, final String p2) throws EngSqlNoApplException, EngFormException;
    
    public void init() throws ServletException {
        Logger.getLogger().info((Object)"BSEO.TableManager.init:");
    }
    
    public void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        Logger.getLogger().info((Object)("BSEO.request=" + request + "."));
        Commarea commarea = new Commarea();
        commarea.request = request;
        commarea.response = response;
        try {
            Logger.getLogger().info((Object)("-------------------------------------- Esecuzione di " + this.getServletName() + " >>> accessoSchema=" + request.getParameter("accessoSchema")));
            this.myServiceBegin(commarea);
            this.myServiceLife(commarea, "eng.commercio.singleton.TableStoreFunctionDefinitionSingleton");
            this.myServiceEnd(commarea);
            Logger.getLogger().info((Object)("-------------------------------------- Fine di " + this.getServletName() + " >>> accessoSchema=" + request.getParameter("accessoSchema")));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TableErrorManager.executeSessioneScaduta(commarea.connection, commarea.request, commarea.response, ex);
        }
        finally {
            Logger.getLogger().info((Object)"------>Finally di TableManager");
            if (commarea != null) {
                commarea.pulisci();
                commarea = null;
            }
        }
    }
    
    protected void adminSuccess(final Commarea commarea, final Properties p, final ResultSelection rs) throws EngSqlNoApplException {
    }
    
    protected void adminErrorApplicative(final Commarea commarea, final PrintWriter out, final Properties p, final ResultSelection rs) throws EngSqlNoApplException {
    }
    
    protected void adminErrorSystem(final Commarea commarea, final PrintWriter out, final Properties p, final ResultSelection rs, final Exception ex) throws EngSqlNoApplException {
    }
    
    protected void myServiceBegin(final Commarea commarea) throws ServletException, IOException, EngSqlNoApplException {
        final HttpSession session = commarea.request.getSession(true);
        commarea.resultSelection = null;
        commarea.connection = null;
        commarea.accessSchemaName = commarea.request.getParameter("accessoSchema");
        if (commarea.accessSchemaName == null) {
            throw new ServletException();
        }
    }
    
    protected void myServiceLife(final Commarea commarea, final String singletonName) throws ServletException, IOException, EngSqlNoApplException {
        commarea.connection = this.createTableManagerDb(-1);
        try {
            commarea.accessSchemaDefinition = this.makeProperty(commarea, commarea.accessSchemaName, singletonName);
        }
        catch (EngSqlNoApplException ex) {
            TableErrorManager.executeSessioneScaduta(commarea.connection, commarea.request, commarea.response, ex);
        }
        catch (Exception ex3) {
            commarea.response.sendRedirect("/jsp/comuni/VerificaSessioneScaduta.jsp?errore=interno");
        }
        try {
            commarea.resultSelection = commarea.connection.execute(commarea.accessSchemaDefinition);
        }
        catch (EngSqlNoApplException ex) {
            TableErrorManager.viewAlert(commarea.request, commarea.response, ex);
        }
        catch (EngSqlApplException ex2) {
            TableErrorManager.viewAlert(commarea.request, commarea.response, ex2);
        }
    }
    
    protected void myServiceEnd(final Commarea commarea) throws ServletException, IOException, EngSqlNoApplException {
        while (commarea.resultSelection != null && commarea.resultSelection.next()) {
            this.adminSuccess(commarea, commarea.accessSchemaDefinition, commarea.resultSelection);
        }
    }
    
    static {
        Logger.getLogger().info((Object)"BSEO.TableManager.block initializer static:");
    }
}
