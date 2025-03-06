// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import java.text.DecimalFormat;
import oracle.sql.ARRAY;
import oracle.sql.CLOB;
import java.sql.Clob;
import java.sql.Array;
import java.sql.Date;
import java.text.ParsePosition;
import java.sql.Timestamp;
import java.util.Locale;
import eng.util.XMLUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.Iterator;
import eng.database.exception.EngSqlNoApplException;
import eng.database.modal.EngResultSet;
import javax.servlet.http.HttpServletRequest;
import eng.servlet.Commarea;
import java.util.Vector;
import java.text.SimpleDateFormat;
import eng.util.Properties;

public class StoreFunction extends Properties
{
    private static final long serialVersionUID = -5460509444665956471L;
    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat DATE_FORMAT_SIMPLE;
    private final Vector parameters;
    private final String functionName;
    private final boolean isFunction;
    private final String messageSuccess;
    private final String urlRedirect;
    private final Action actionSuccess;
    private final Action actionErrorApplicative;
    private final Action actionErrorSystem;
    private String sqlCommand;
    
    public StoreFunction(final String functionName, final boolean isFunction, final String messageSuccess, final Parameter[] parameters) {
        this(functionName, isFunction, messageSuccess, null, null, null, null, parameters);
    }
    
    public StoreFunction(final String functionName, final boolean isFunction, final String messageSuccess, final String urlRedirect, final Parameter[] parameters) {
        this(functionName, isFunction, messageSuccess, urlRedirect, null, null, null, parameters);
    }
    
    public StoreFunction(final String functionName, final Action actionSuccess, final Action actionErrorApplicative, final Action actionErrorSystem, final Parameter[] parameters) {
        this(functionName, parameters.length > 0 && parameters[0] instanceof ReturnCode, null, null, actionSuccess, actionErrorApplicative, actionErrorSystem, parameters);
    }
    
    public StoreFunction(final String functionName, final boolean isFunction, final String messageSuccess, final String urlRedirect, final Action actionSuccess, final Action actionErrorApplicative, final Action actionErrorSystem, final Parameter[] parameters) {
        this.sqlCommand = "";
        this.functionName = functionName;
        this.isFunction = isFunction;
        this.messageSuccess = messageSuccess;
        this.urlRedirect = urlRedirect;
        this.actionSuccess = actionSuccess;
        this.actionErrorApplicative = actionErrorApplicative;
        this.actionErrorSystem = actionErrorSystem;
        this.parameters = new Vector();
        for (int i = 0; i < parameters.length; ++i) {
            this.addParameter(parameters[i]);
        }
    }
    
    public void addParameter(final Parameter parameter) {
        this.parameters.add(parameter);
    }
    
    public void createOutputInRequest(final Commarea commarea, final HttpServletRequest request) throws EngSqlNoApplException {
        final Iterator it = commarea.getParametersIterator();
        while (it.hasNext()) {
            final Parameter p = it.next();
            if (p.isModeInOut() || p.isModeOut()) {
                final String name = p.getName();
                if (name == null) {
                    continue;
                }
                if (p instanceof HttpPrimitive) {
                    request.setAttribute(name, (Object)p.getValuePrimitive());
                }
                else if (p instanceof HttpArray) {
                    final EngResultSet result = new EngResultSet();
                    if (p.getValueArray() != null) {
                        final Iterator lines = p.getValueArray().iterator();
                        int cline = 0;
                        while (lines.hasNext()) {
                            final String line = lines.next();
                            final HttpArray httpArray = (HttpArray)p;
                            final Vector columns = HttpArray.makeColumns(line);
                            for (int i = 0; i < columns.size(); ++i) {
                                result.addColumn(columns.elementAt(i));
                            }
                            result.addRow();
                            ++cline;
                        }
                    }
                    request.setAttribute(name, (Object)result);
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
                    request.setAttribute(name, (Object)result);
                }
            }
        }
    }
    
    public Action getActionSuccess() {
        return this.actionSuccess;
    }
    
    public Action getActionErrorApplicative() {
        return this.actionErrorApplicative;
    }
    
    public Action getActionErrorSystem() {
        return this.actionErrorSystem;
    }
    
    public int getParametersNumber() {
        return this.parameters.size();
    }
    
    public Parameter getParameter(final int index) {
        return this.parameters.elementAt(index);
    }
    
    public int getParameterIndex(final Class classParameterSearched) {
        Parameter p = null;
        for (int i = 0; i < this.parameters.size(); ++i) {
            p = this.parameters.elementAt(i);
            if (p.getClass() == classParameterSearched) {
                return i;
            }
        }
        return -1;
    }
    
    public String getFunctionName() {
        return this.functionName;
    }
    
    public String getMessageSuccess() {
        return this.messageSuccess;
    }
    
    public String getSqlCommand() {
        return this.sqlCommand;
    }
    
    public String getUrlRedirect() {
        return this.urlRedirect;
    }
    
    public boolean isFunction() {
        return this.isFunction;
    }
    
    public boolean isInErrorApplicative(final Commarea commarea, final CallableStatement statement) {
        try {
            final ReturnCode returnCode = (ReturnCode)commarea.getParameter(this.getParameterIndex(ReturnCode.class));
            if (returnCode != null) {
                if (statement.getInt(returnCode.getPosition()) == 0) {
                    return true;
                }
            }
            else {
                final ErrorCode errorCode = (ErrorCode)commarea.getParameter(this.getParameterIndex(ErrorCode.class));
                if (errorCode != null && statement.getInt(errorCode.getPosition()) != 0) {
                    return true;
                }
            }
        }
        catch (SQLException ex) {
            return true;
        }
        return false;
    }
    
    private String makeSql() {
        final StringBuffer sql = new StringBuffer();
        int questionMarksNumber = this.getParametersNumber();
        sql.append("{call ");
        if (this.isFunction()) {
            sql.append("? := ");
            --questionMarksNumber;
        }
        sql.append(this.getFunctionName() + " (");
        for (int i = 0; i < questionMarksNumber - 1; ++i) {
            sql.append("?,");
        }
        sql.append("?)");
        sql.append("}");
        return sql.toString();
    }
    
    public CallableStatement makeCallableStatement(final Commarea commarea, final Connection connection) throws SQLException, EngSqlNoApplException {
        final String sqlCommand = this.makeSql();
        final CallableStatement cs = connection.prepareCall(sqlCommand);
        for (int i = 0; i < this.getParametersNumber(); ++i) {
            final Parameter p = commarea.getParameter(i);
            final int pos = p.getPosition();
            if (p.isModeIn()) {
                if (p.isValueNull()) {
                    cs.setNull(pos, p.getType());
                }
                else if (p.isPrimitive()) {
                    String value = p.getValuePrimitive();
                    switch (p.getType()) {
                        case 4: {
                            if (value.indexOf(".") >= 0) {
                                value = XMLUtil.string_replace(".", "", value);
                            }
                            if (value.indexOf(",") >= 0) {
                                value = XMLUtil.string_replace(",", ".", value);
                            }
                            cs.setInt(pos, Integer.parseInt(value));
                            break;
                        }
                        case 2: {
                            if (value.indexOf(".") >= 0) {
                                value = XMLUtil.string_replace(".", "", value);
                            }
                            if (value.indexOf(",") >= 0) {
                                value = XMLUtil.string_replace(",", ".", value);
                            }
                            cs.setDouble(pos, Double.parseDouble(value));
                            break;
                        }
                        case 93: {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
                            boolean converted = false;
                            java.util.Date date = null;
                            try {
                                if (!converted) {
                                    sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
                                    date = sdf.parse(value);
                                    converted = true;
                                }
                            }
                            catch (Exception ex3) {}
                            try {
                                if (!converted) {
                                    sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);
                                    date = sdf.parse(value);
                                    converted = true;
                                }
                            }
                            catch (Exception ex4) {}
                            try {
                                if (!converted) {
                                    sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                                    date = sdf.parse(value);
                                    converted = true;
                                }
                            }
                            catch (Exception ex5) {}
                            if (converted) {
                                cs.setTimestamp(pos, new Timestamp(date.getTime()));
                                break;
                            }
                            throw new EngSqlNoApplException("Errore. Il TImeStamp alla posizione " + pos + " non e' in uno dei seguenti formati:\ndd/MM/yyyy hh:mm:ss\ndd/MM/yyyy hh:mm\ndd/MM/yyyy");
                        }
                        case 3: {
                            if (value.indexOf(".") >= 0) {
                                value = XMLUtil.string_replace(".", "", value);
                            }
                            if (value.indexOf(",") >= 0) {
                                value = XMLUtil.string_replace(",", ".", value);
                            }
                            cs.setDouble(pos, Double.parseDouble(value));
                            break;
                        }
                        case 12: {
                            cs.setString(pos, value);
                            break;
                        }
                        case 91: {
                            cs.setDate(pos, new Date(StoreFunction.DATE_FORMAT_SIMPLE.parse(value, new ParsePosition(0)).getTime()));
                            break;
                        }
                        default: {
                            throw new EngSqlNoApplException("Errore interno (111)");
                        }
                    }
                }
                else if (p.isArray()) {
                    final Vector v = p.getValueArray();
                    for (int xi = 0; xi < v.size(); ++xi) {
                        System.out.println(v.elementAt(xi) + "");
                        System.out.flush();
                    }
                    final ARRAY ar = HttpArray.makeARRAY(connection, v);
                    cs.setArray(pos, (Array)ar);
                    cs.setArray(pos, (Array)HttpArray.makeARRAY(connection, p.getValueArray()));
                }
                else {
                    if (p.getType() != 2005) {
                        throw new EngSqlNoApplException("Errore interno (101)");
                    }
                    if (p instanceof HttpClob) {
                        final Vector v = p.getValueArray();
                        for (int xi = 0; xi < v.size(); ++xi) {
                            System.out.println("StoreFunction.makeCallableStatement:case CLOB" + v.elementAt(xi) + "");
                            System.out.flush();
                        }
                        cs.setClob(pos, (Clob)HttpClob.makeCLOB(connection, p.getValueArray()));
                    }
                    else if (p instanceof SezioneCacheClob) {
                        cs.setClob(pos, (Clob)SezioneCacheClob.makeCLOB(connection, p.getValueSezioneCache()));
                    }
                }
            }
            else if (p.isModeOut()) {
                if (p.isPrimitive()) {
                    cs.registerOutParameter(pos, p.getType());
                }
                else if (p.isArray()) {
                    cs.registerOutParameter(pos, 2003, HttpArray.getArrayDescriptorName());
                }
                else {
                    if (p.getType() != 2005) {
                        throw new EngSqlNoApplException("Errore interno (102)");
                    }
                    cs.registerOutParameter(pos, 2005);
                }
            }
            else {
                if (!p.isModeInOut()) {
                    throw new EngSqlNoApplException("Errore interno (104)");
                }
                if (p.isValueNull()) {
                    cs.setNull(pos, p.getType());
                    cs.registerOutParameter(pos, p.getType());
                }
                else if (p.isPrimitive()) {
                    String value = p.getValuePrimitive();
                    switch (p.getType()) {
                        case 4: {
                            if (value.indexOf(".") >= 0) {
                                value = XMLUtil.string_replace(".", "", value);
                            }
                            if (value.indexOf(",") >= 0) {
                                value = XMLUtil.string_replace(",", ".", value);
                            }
                            cs.setInt(pos, Integer.parseInt(value));
                            break;
                        }
                        case 2: {
                            if (value.indexOf(".") >= 0) {
                                value = XMLUtil.string_replace(".", "", value);
                            }
                            if (value.indexOf(",") >= 0) {
                                value = XMLUtil.string_replace(",", ".", value);
                            }
                            cs.setDouble(pos, Double.parseDouble(value));
                            break;
                        }
                        case 3: {
                            if (value.indexOf(".") >= 0) {
                                value = XMLUtil.string_replace(".", "", value);
                            }
                            if (value.indexOf(",") >= 0) {
                                value = XMLUtil.string_replace(",", ".", value);
                            }
                            cs.setDouble(pos, Double.parseDouble(value));
                            break;
                        }
                        case 12: {
                            cs.setString(pos, value);
                            break;
                        }
                        case 93: {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
                            boolean converted = false;
                            java.util.Date date = null;
                            try {
                                if (!converted) {
                                    sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
                                    date = sdf.parse(value);
                                    converted = true;
                                }
                            }
                            catch (Exception ex6) {}
                            try {
                                if (!converted) {
                                    sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);
                                    date = sdf.parse(value);
                                    converted = true;
                                }
                            }
                            catch (Exception ex7) {}
                            try {
                                if (!converted) {
                                    sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                                    date = sdf.parse(value);
                                    converted = true;
                                }
                            }
                            catch (Exception ex8) {}
                            if (converted) {
                                cs.setTimestamp(pos, new Timestamp(date.getTime()));
                                break;
                            }
                            throw new EngSqlNoApplException("Errore. Il TImeStamp alla posizione " + pos + " non e' in uno dei seguenti formati:\ndd/MM/yyyy hh:mm:ss\ndd/MM/yyyy hh:mm\ndd/MM/yyyy");
                        }
                        case 91: {
                            cs.setDate(pos, new Date(StoreFunction.DATE_FORMAT_SIMPLE.parse(value, new ParsePosition(0)).getTime()));
                            break;
                        }
                        default: {
                            throw new EngSqlNoApplException("Errore interno (113)");
                        }
                    }
                    cs.registerOutParameter(pos, p.getType());
                }
                else if (p.isArray()) {
                    final Vector v = p.getValueArray();
                    try {
                        for (int xi = 0; xi < v.size(); ++xi) {
                            System.out.println(v.elementAt(xi) + "");
                            System.out.flush();
                        }
                        final ARRAY ar = HttpArray.makeARRAY(connection, v);
                        cs.setArray(pos, (Array)ar);
                        cs.registerOutParameter(pos, 2003, HttpArray.getArrayDescriptorName());
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.flush();
                    }
                }
                else {
                    if (p.getType() != 2005) {
                        throw new EngSqlNoApplException("Errore interno (103) Giulio" + p.getName());
                    }
                    final Vector v = p.getValueArray();
                    final CLOB cl = HttpClob.makeCLOB(connection, v);
                    final Clob javaClob = (Clob)p.getValueArray();
                    final CLOB oracleCLOB = (CLOB)javaClob;
                    try {
                        cs.setClob(pos, (Clob)oracleCLOB);
                        cs.registerOutParameter(pos, 2005);
                    }
                    catch (Exception ex2) {
                        ex2.printStackTrace();
                        System.out.flush();
                    }
                }
            }
        }
        return cs;
    }
    
    public void updateValuesErrorApplicative(final Commarea commarea, final CallableStatement statement) throws SQLException, EngSqlNoApplException {
        int index = this.getParameterIndex(ErrorCode.class);
        if (index >= 0) {
            final Parameter errorCode = commarea.getParameter(index);
            errorCode.setValuePrimitive(statement.getString(errorCode.getPosition()));
        }
        index = this.getParameterIndex(ErrorMessage.class);
        if (index >= 0) {
            final Parameter errorMessage = commarea.getParameter(index);
            errorMessage.setValuePrimitive(statement.getString(errorMessage.getPosition()));
        }
    }
    
    public void updateValuesParametersOutput(final Commarea commarea, final CallableStatement statement) throws SQLException, EngSqlNoApplException {
        final Iterator it = commarea.getParametersIterator();
        final DecimalFormat df = new DecimalFormat("######.######");
        while (it.hasNext()) {
            final Parameter p = it.next();
            if (p.isModeInOut() || p.isModeOut()) {
                final String name = p.getName();
                final int position = p.getPosition();
                if (name == null) {
                    continue;
                }
                if (p instanceof HttpPrimitive) {
                    String value = null;
                    switch (p.getType()) {
                        case 4: {
                            value = "" + statement.getInt(position);
                            break;
                        }
                        case 2: {
                            value = "" + statement.getLong(position);
                            break;
                        }
                        case 3: {
                            try {
                                value = df.format(statement.getDouble(position));
                            }
                            catch (Exception e) {
                                value = "";
                                e.printStackTrace();
                            }
                            break;
                        }
                        case 12: {
                            if (p.getName() != null && (p.getName().equals("ErrorMessage") || p.getName().equals("WarningMessage"))) {
                                value = XMLUtil.xmlEscape(statement.getString(position), 1);
                                break;
                            }
                            value = statement.getString(position);
                            break;
                        }
                        case 91: {
                            value = StoreFunction.DATE_FORMAT_SIMPLE.format(statement.getDate(position)).toString();
                            break;
                        }
                        default: {
                            throw new EngSqlNoApplException("Errore interno (142)");
                        }
                    }
                    if (statement.wasNull()) {
                        value = null;
                    }
                    p.setValuePrimitive(value);
                }
                else if (p instanceof HttpArray) {
                    final Object obj = statement.getArray(position).getArray();
                    final String[] strings = (String[])obj;
                    final Vector v = new Vector();
                    for (int i = 0; i < strings.length; ++i) {
                        final String s = strings[i];
                        v.add(strings[i]);
                    }
                    p.setValueArray(v);
                }
                else {
                    if (!(p instanceof HttpClob)) {
                        throw new EngSqlNoApplException("Tipo parametro illogico." + p + ".");
                    }
                    final Clob c = statement.getClob(position);
                    Vector v2 = new Vector();
                    if (c != null && !this.isInErrorApplicative(commarea, statement)) {
                        try {
                            v2 = HttpClob.getVectorFromClob(c);
                        }
                        catch (Exception e2) {
                            e2.printStackTrace();
                            throw new EngSqlNoApplException("Errore durante il l'elaborazione del codice XML");
                        }
                    }
                    p.setValueArray(v2);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(StoreFunction:");
        sb.append(this.functionName + "," + (this.isFunction ? "function" : "procedure") + "," + this.messageSuccess + "," + this.urlRedirect + "\n");
        sb.append("(ActionSuccess:" + this.actionSuccess + ")");
        sb.append("(ActionErrorApplicative:" + this.actionErrorApplicative + ")");
        sb.append("(ActionErrorSystem:" + this.actionErrorSystem + ")");
        for (int i = 0; i < this.parameters.size(); ++i) {
            sb.append("\n" + this.parameters.elementAt(i) + ",");
        }
        sb.append("\n" + this.sqlCommand + ",");
        sb.append(")");
        return sb.toString();
    }
    
    static {
        DATE_FORMAT_SIMPLE = new SimpleDateFormat("dd/MM/yyyy");
    }
}
