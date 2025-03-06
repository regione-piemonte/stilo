// 
// Decompiled by Procyon v0.5.36
// 

package eng.servlet;

import eng.storefunction.ActionForward;
import eng.storefunction.ActionScript;
import eng.storefunction.Action;
import eng.storefunction.ErrorCode;
import eng.storefunction.ErrorContext;
import eng.storefunction.ErrorMessage;
import eng.database.definition.ResultSelection;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import eng.database.modal.EngResultSet;
import java.io.IOException;
import eng.storefunction.Parameter;
import eng.storefunction.StoreFunction;
import eng.database.exception.EngException;
import eng.database.exception.EngFormException;
import eng.util.XMLUtil;
import eng.storefunction.HttpArray;
import eng.storefunction.SezioneCacheClob;
import eng.storefunction.HttpClob;
import eng.storefunction.HttpPrimitive;
import eng.storefunction.UserLavoro;
import eng.storefunction.ConnectionToken;
import eng.storefunction.EnteCode;
import eng.util.GetProperties;
import eng.storefunction.UserCode;
import eng.util.Logger;
import eng.storefunction.singleton.TableStoreFunctionDefinitionSingleton;
import eng.util.Properties;
import javax.servlet.ServletException;
import eng.database.exception.EngSqlNoApplException;
import eng.database.tablemanager.TableManagerDbProgress;
import eng.database.tablemanager.TableManagerDb;

public class TableManagerProgress extends TableManager
{
    public static final String _GENERIC_RETURN_VALUE = "0";
    public static final String _GENERIC_ERROR_CONTEXT = "";
    public static final String _GENERIC_ERROR_CODE = "0";
    public static final String _GENERIC_ERROR_MESSAGE = "";
    public static final String _RETURN_VALUE_ATTRIBUTE = "returnValue";
    public static final String _ERROR_CONTEXT_ATTRIBUTE = "errorContext";
    public static final String _ERROR_CODE_ATTRIBUTE = "errorCode";
    public static final String _ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    public static final String _INFO_MESSAGE = "infoMessage";
    public static final String _WARNING_MESSAGE = "WarningMessage";
    public static final String _PN_OLD_INOUT_PARAM_PREFIX = "OLD_INOUT_";
    public static final String _PN_EXECUTED_SINGLETON_CLASSNAME = "EXEC_SINGLETON_CLASSNAME";
    public static String STORFUNCTIONDEF_PACK;
    
    @Override
    protected TableManagerDb createTableManagerDb() throws EngSqlNoApplException {
        return new TableManagerDbProgress();
    }
    
    @Override
    protected TableManagerDb createTableManagerDb(final int a) throws EngSqlNoApplException {
        return new TableManagerDbProgress(a);
    }
    
    protected TableManagerDb createTableManagerDb(final String a) throws EngSqlNoApplException {
        return new TableManagerDbProgress(Integer.parseInt(a));
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        final String name = this.getServletConfig().getInitParameter("storeDefClasspath");
        if (name != null) {
            TableManagerProgress.STORFUNCTIONDEF_PACK = name + ".";
        }
    }
    
    @Override
    protected Properties makeProperty(final Commarea commarea, final String accessSchemaName, final String classname) throws EngSqlNoApplException, EngFormException {
        int noValidNumberParam = 0;
        String noValidNumberParamName = "";
        int requiredParam = 0;
        String requiredParamName = "";
        String exMsg = "";
        final StringBuffer fullyQualifiedName = new StringBuffer();
        fullyQualifiedName.append(TableManagerProgress.STORFUNCTIONDEF_PACK);
        fullyQualifiedName.append(accessSchemaName);
        StoreFunction sf = null;
        try {
            sf = TableStoreFunctionDefinitionSingleton.getInstance().searchFromName(fullyQualifiedName.toString());
        }
        catch (Exception ex) {
            Logger.getLogger().error((Object)ex.getMessage(), (Throwable)ex);
            ex.printStackTrace();
        }
        if (sf == null) {
            throw new EngSqlNoApplException("Store function progress non trovata in TableStoreFunctionDefinitionSingleton(" + fullyQualifiedName.toString() + ")");
        }
        try {
            for (int i = 0; i < sf.getParametersNumber(); ++i) {
                final Parameter p = sf.getParameter(i);
                if (p.isModeIn() || p.isModeInOut()) {
                    if (p instanceof UserCode) {
                        commarea.putParameter(p, GetProperties.getUserProperty("UP_ID_UTENTE", commarea.request));
                    }
                    else if (p instanceof EnteCode) {
                        commarea.putParameter(p, GetProperties.getUserProperty("UP_ID_ENTE", commarea.request));
                    }
                    else if (p instanceof ConnectionToken) {
                        commarea.putParameter(p, commarea.request.getSession().getAttribute("CodIdConnectionToken"));
                    }
                    else if (p instanceof UserLavoro) {
                        commarea.putParameter(p, commarea.request.getSession().getAttribute("IdUserLavoro"));
                    }
                    else {
                        if (!(p instanceof HttpPrimitive) && !(p instanceof HttpClob) && !(p instanceof SezioneCacheClob) && !(p instanceof HttpArray)) {
                            throw new EngSqlNoApplException("tipo parametro non previsto(" + p.getClass().getName() + ")");
                        }
                        String pValue = null;
                        if (p instanceof HttpPrimitive) {
                            pValue = ((commarea.request.getParameter(p.getName()) == null) ? "" : commarea.request.getParameter(p.getName()));
                            if (pValue.equals("")) {
                                pValue = (String)((commarea.request.getAttribute(p.getName()) == null) ? "" : commarea.request.getAttribute(p.getName()));
                            }
                        }
                        if (p instanceof HttpPrimitive && p.isRequired() && pValue.equals("")) {
                            if (++requiredParam == 1) {
                                requiredParamName = p.getExtendedName();
                            }
                            else {
                                requiredParamName = requiredParamName + ", " + p.getExtendedName();
                            }
                        }
                        if (p instanceof HttpPrimitive && p.getType() == 4 && !pValue.equals("")) {
                            int tmpIntValue = 0;
                            try {
                                tmpIntValue = Integer.parseInt(pValue);
                            }
                            catch (Exception ex3) {
                                ++noValidNumberParam;
                                String tmpName = p.getExtendedName();
                                if (tmpName.equals("")) {
                                    tmpName = p.getName();
                                }
                                if (noValidNumberParam == 1) {
                                    noValidNumberParamName = tmpName;
                                }
                                else {
                                    noValidNumberParamName = noValidNumberParamName + ", " + tmpName;
                                }
                            }
                        }
                        if (p instanceof HttpPrimitive && p.getType() == 2 && !pValue.equals("")) {
                            double tmpIntValue2 = 0.0;
                            try {
                                tmpIntValue2 = Double.parseDouble(pValue);
                            }
                            catch (Exception ex4) {
                                ++noValidNumberParam;
                                String tmpName2 = p.getExtendedName();
                                if (tmpName2.equals("")) {
                                    tmpName2 = p.getName();
                                }
                                if (noValidNumberParam == 1) {
                                    noValidNumberParamName = tmpName2;
                                }
                                else {
                                    noValidNumberParamName = noValidNumberParamName + ", " + tmpName2;
                                }
                            }
                        }
                        commarea.putParameter(p, commarea.request);
                    }
                }
                else {
                    commarea.putParameter(p);
                }
            }
            if (requiredParam > 0) {
                if (requiredParam == 1) {
                    exMsg = "E' necessario inserire il valore del campo ' " + XMLUtil.xmlEscape(requiredParamName, 2) + "'";
                }
                else {
                    exMsg = "E' necessario inserire il valore dei campi ' " + XMLUtil.xmlEscape(requiredParamName, 2) + "'";
                }
            }
            if (noValidNumberParam > 0) {
                if (noValidNumberParam == 1) {
                    exMsg = "Il campo '" + XMLUtil.xmlEscape(noValidNumberParamName, 2) + "' deve essere un numerico";
                }
                else {
                    exMsg = "I campi '" + XMLUtil.xmlEscape(noValidNumberParamName, 2) + "' devono essere dei numerici";
                }
            }
            if (requiredParam > 0 || noValidNumberParam > 0) {
                throw new EngFormException(exMsg);
            }
        }
        catch (EngFormException ex2) {
            throw ex2;
        }
        catch (EngException ex5) {
            throw new EngSqlNoApplException("valorizzazione parametri in errore");
        }
        return sf;
    }
    
    @Override
    protected void myServiceLife(final Commarea commarea, final String singletonName) throws ServletException, IOException, EngSqlNoApplException {
        try {
            final String ente = (String)commarea.request.getSession().getAttribute("idEnte");
            Logger.getLogger().info((Object)("Creazione connessione con ente :" + ente));
            commarea.connection = this.createTableManagerDb(ente);
            commarea.accessSchemaDefinition = this.makeProperty(commarea, commarea.accessSchemaName, singletonName);
            final StoreFunction storeFunction = (StoreFunction)commarea.accessSchemaDefinition;
            commarea.accessSchemaDefinition.put("COMMAREA", commarea);
            this.saveInOutParametersInRequest(commarea);
            commarea.resultSelection = commarea.connection.execute(commarea.accessSchemaDefinition);
            if (commarea.resultSelection == null) {
                this.adminErrorApplicative(commarea, commarea.accessSchemaDefinition, commarea.resultSelection);
            }
            else {
                storeFunction.createOutputInRequest(commarea, commarea.request);
                this.adminSuccess(commarea, commarea.accessSchemaDefinition, commarea.resultSelection);
            }
        }
        catch (EngSqlNoApplException ex2) {
            this.adminErrorSystem(commarea, commarea.accessSchemaDefinition, commarea.resultSelection, ex2);
        }
        catch (EngFormException ex3) {
            commarea.request.setAttribute("ErrorMessage", (Object)ex3.getMessage());
            final StringBuffer fullyQualifiedName = new StringBuffer();
            fullyQualifiedName.append(TableManagerProgress.STORFUNCTIONDEF_PACK);
            fullyQualifiedName.append(commarea.accessSchemaName);
            final StoreFunction sf = TableStoreFunctionDefinitionSingleton.getInstance().searchFromName(fullyQualifiedName.toString());
            this.adminErrorSystem(commarea, sf, commarea.resultSelection, ex3);
        }
        catch (Exception ex4) {
            this.adminErrorSystem(commarea, commarea.accessSchemaDefinition, commarea.resultSelection, ex4);
        }
    }
    
    protected void saveInOutParametersInRequest(final Commarea commarea) throws EngSqlNoApplException {
        final Iterator it = commarea.getParametersIterator();
        final HttpServletRequest request = commarea.request;
        request.setAttribute("EXEC_SINGLETON_CLASSNAME", (Object)(TableManagerProgress.STORFUNCTIONDEF_PACK + commarea.accessSchemaName));
        while (it.hasNext()) {
            final Parameter p = it.next();
            if (p.isModeInOut()) {
                final String name = p.getName();
                if (name == null) {
                    continue;
                }
                if (p instanceof HttpPrimitive) {
                    request.setAttribute("OLD_INOUT_" + name, (Object)p.getValuePrimitive());
                }
                else if (p instanceof HttpArray) {
                    final EngResultSet result = new EngResultSet();
                    if (p.getValueArray() != null) {
                        final Iterator lines = p.getValueArray().iterator();
                        int cline = 0;
                        while (lines.hasNext()) {
                            final String line = lines.next();
                            final Vector columns = HttpArray.makeColumns(line);
                            for (int i = 0; i < columns.size(); ++i) {
                                result.addColumn(columns.elementAt(i));
                            }
                            result.addRow();
                            ++cline;
                        }
                    }
                    request.setAttribute("OLD_INOUT_" + name, (Object)result);
                }
                else {
                    if (!(p instanceof HttpClob)) {
                        throw new EngSqlNoApplException("Tipo parametro illogico." + p + ".");
                    }
                    final EngResultSet result = new EngResultSet();
                    if (p.getValueArray() != null) {
                        final Iterator lines = p.getValueArray().iterator();
                        int cline2 = 0;
                        while (lines.hasNext()) {
                            final String[] line2 = lines.next();
                            for (int j = 0; j < line2.length; ++j) {
                                result.addColumn(line2[j]);
                            }
                            result.addRow();
                            ++cline2;
                        }
                    }
                    request.setAttribute("OLD_INOUT_" + name, (Object)result);
                }
            }
        }
    }
    
    @Override
    protected void myServiceEnd(final Commarea commarea) throws ServletException, IOException, EngSqlNoApplException {
    }
    
    @Override
    protected void adminSuccess(final Commarea commarea, final Properties p, final ResultSelection rs) throws EngSqlNoApplException {
        final StoreFunction storeFunction = (StoreFunction)p;
        final Parameter errorMessage = commarea.getParameter(storeFunction.getParameterIndex(ErrorMessage.class));
        this.adminActionX(commarea, storeFunction.getActionSuccess(), p);
    }
    
    protected void adminErrorApplicative(final Commarea commarea, final Properties p, final ResultSelection rs) throws EngSqlNoApplException {
        final StoreFunction storeFunction = (StoreFunction)p;
        final Parameter returnValue = commarea.getParameter(0);
        final Parameter errorContext = commarea.getParameter(storeFunction.getParameterIndex(ErrorContext.class));
        final Parameter errorCode = commarea.getParameter(storeFunction.getParameterIndex(ErrorCode.class));
        final Parameter errorMessage = commarea.getParameter(storeFunction.getParameterIndex(ErrorMessage.class));
        String value;
        if (returnValue == null) {
            value = "0";
        }
        else {
            value = returnValue.getValuePrimitive();
        }
        String context;
        if (errorContext == null) {
            context = "";
        }
        else {
            context = errorContext.getValuePrimitive();
        }
        String code;
        if (errorCode == null) {
            code = "0";
        }
        else {
            code = errorCode.getValuePrimitive();
        }
        String message;
        if (errorMessage == null) {
            message = "";
        }
        else {
            message = errorMessage.getValuePrimitive();
        }
        this.adminActionErrorX(commarea, storeFunction.getActionErrorApplicative(), value, context, code, message, p);
    }
    
    protected void adminErrorSystem(final Commarea commarea, final Properties p, final ResultSelection rs, final Exception ex) throws EngSqlNoApplException {
        final StoreFunction storeFunction = (StoreFunction)p;
        this.adminActionErrorX(commarea, storeFunction.getActionErrorSystem(), "0", "", "0", ex.getMessage(), p);
    }
    
    private void adminActionErrorX(final Commarea commarea, final Action action, final String returnValue, final String errorContext, final String errorCode, final String errorMessage, final Properties p) throws EngSqlNoApplException {
        if (!(action instanceof ActionScript)) {
            if (action instanceof ActionForward) {
                Logger.getLogger().error((Object)"Errore stored");
                Logger.getLogger().error((Object)("--- Return Value: " + returnValue));
                Logger.getLogger().error((Object)("--- Error Context: " + errorContext));
                Logger.getLogger().error((Object)("--- Error Code: " + errorCode));
                Logger.getLogger().error((Object)("--- Error Message: " + errorMessage));
                commarea.request.setAttribute("returnValue", (Object)returnValue);
                commarea.request.setAttribute("errorContext", (Object)errorContext);
                commarea.request.setAttribute("errorCode", (Object)errorCode);
                commarea.request.setAttribute("errorMessage", (Object)errorMessage);
            }
        }
        this.adminActionX(commarea, action, p);
    }
    
    private void adminActionX(final Commarea commarea, final Action action, final Properties p) throws EngSqlNoApplException {
        if (action != null) {
            final StoreFunction storeFunction = (StoreFunction)p;
            if (!(action instanceof ActionForward)) {
                throw new EngSqlNoApplException("Errore illogico(234)");
            }
            storeFunction.createOutputInRequest(commarea, commarea.request);
            ((ActionForward)action).execute(this.getServletContext(), commarea.request, commarea.response);
        }
    }
    
    static {
        TableManagerProgress.STORFUNCTIONDEF_PACK = "";
    }
}
