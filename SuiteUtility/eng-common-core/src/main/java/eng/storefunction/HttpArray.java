// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import java.sql.SQLException;
import eng.database.exception.EngSqlNoApplException;
import oracle.sql.ArrayDescriptor;
import eng.util.XMLUtil;
import oracle.sql.ARRAY;
import java.util.Vector;
import java.sql.Connection;
import eng.util.Logger;
import eng.database.exception.EngException;

public class HttpArray extends Parameter
{
    private static String ARRAY_DESCRIPTOR_NAME;
    private static final int LINES_MAXIMUM = 1000;
    public static final String COLUMN_SEPARATOR = "|*|";
    public static final String LINE_SEPARATOR = "|-|";
    private static final int COLUMN_MAXIMUM = 100;
    private static final boolean JSP_ARRAY_FOR_COLUMN = true;
    private final String[] names;
    private final int namesStartIndex;
    private final int namesCounter;
    
    public HttpArray(final int position) throws EngException {
        this(position, null, null, 0, 0);
    }
    
    public HttpArray(final int position, final String name) throws EngException {
        this(position, name, null, 0, 0);
    }
    
    public HttpArray(final int position, final String name, final String[] names) throws EngException {
        this(position, name, names, 0, names.length);
    }
    
    public HttpArray(final int position, final String name, final String[] names, final int namesStartIndex, final int namesCounter) throws EngException {
        super(position, Parameter.getTypeArray(), name);
        this.configure();
        this.names = names;
        this.namesStartIndex = namesStartIndex;
        this.namesCounter = namesCounter;
    }
    
    public static String getArrayDescriptorName() {
        Logger.getLogger().error((Object)("ARRAY_DESCRIPTOR_NAME: " + HttpArray.ARRAY_DESCRIPTOR_NAME));
        return HttpArray.ARRAY_DESCRIPTOR_NAME;
    }
    
    public void setArrayDescriptorName(final String s) {
        HttpArray.ARRAY_DESCRIPTOR_NAME = s;
    }
    
    public String[] getNames() {
        return this.names;
    }
    
    public static ARRAY makeARRAY(final Connection connection, final Vector array) throws SQLException, EngSqlNoApplException {
        try {
            for (int i = 0; i < array.size(); ++i) {
                array.set(i, XMLUtil.verySpecialEscape(array.get(i).toString()));
            }
            final String[] sarray = array.toArray(new String[0]);
            final ArrayDescriptor d = ArrayDescriptor.createDescriptor(HttpArray.ARRAY_DESCRIPTOR_NAME, connection);
            final ARRAY arrayOracle = new ARRAY(d, connection, (Object)sarray);
            return arrayOracle;
        }
        catch (ArrayStoreException ex) {
            throw new EngSqlNoApplException("Errore interno (105)" + ex);
        }
        catch (SQLException ex2) {
            throw new EngSqlNoApplException("Errore interno (106)");
        }
    }
    
    public String makeStringOutput(final CallableStatement statement) throws SQLException {
        final StringBuffer sb = new StringBuffer();
        final String[] strings = (String[])statement.getArray(this.getPosition()).getArray(1L, 1000);
        for (int i = 0; i < strings.length; ++i) {
            sb.append(strings[i] + "|-|");
        }
        return sb.toString();
    }
    
    public static Vector makeColumns(String line) throws EngSqlNoApplException {
        if (line != null && line.startsWith("0x") && line.indexOf("|*|") < 0) {
            String new_line;
            String cc;
            int i;
            for (new_line = "", line = line.substring(2); line.length() > 0; line = line.substring(2), i = Integer.parseInt(cc, 16), new_line += new String(new char[] { (char)i })) {
                cc = line.substring(0, 2);
            }
            line = new_line;
        }
        final Vector v = new Vector();
        int posPrec = 0;
        if (line == null) {
            line = "";
        }
        int posLast;
        while ((posLast = line.indexOf("|*|", posPrec)) != -1) {
            final String column = line.substring(posPrec, posLast);
            v.add(column);
            posPrec = posLast + "|*|".length();
            if (v.size() > 100) {
                throw new EngSqlNoApplException("Errore interno (131)" + line + ".");
            }
        }
        return v;
    }
    
    @Override
    public void makeValue(final Object httpRequest) throws EngException {
        final Vector lines = this.makeLinesFromNamesColumns((HttpServletRequest)httpRequest, this.names, this.namesStartIndex, this.namesCounter);
        this.setValueArray(lines);
    }
    
    private Vector makeLinesFromNamesColumns(final HttpServletRequest request, final String[] names, final int namesStartIndex, final int namesCounter) throws EngSqlNoApplException {
        final Vector lines = new Vector();
        final String[] columnsValues = new String[namesCounter];
        final int[] columnsValuesIndex = new int[namesCounter];
        final StringBuffer lineValue = new StringBuffer();
        for (int i = 0; i < namesCounter; ++i) {
            final String name = names[namesStartIndex + i];
            if (Parameter.isNameAttribute(name)) {
                final Object valueAttribute = request.getAttribute(name);
                columnsValues[i] = ((valueAttribute instanceof String) ? ((String)valueAttribute) : null);
            }
            else {
                columnsValues[i] = request.getParameter(name);
            }
            if (columnsValues[i] == null) {
                columnsValues[i] = "";
            }
        }
        while (lines.size() < 1000) {
            lineValue.setLength(0);
            int i = 0;
            while (i < namesCounter) {
                if (columnsValuesIndex[i] >= columnsValues[i].length()) {
                    if (i == 0) {
                        return lines;
                    }
                    throw new EngSqlNoApplException("Parametro Jsp di tipo Array con numero linee disallineate (" + names[namesStartIndex + i] + ")," + columnsValues[i] + ".");
                }
                else {
                    final String columnValue = makeNextToken(columnsValues[i], columnsValuesIndex[i], "|*|");
                    if (columnValue == null) {
                        throw new EngSqlNoApplException("Parametro Jsp di tipo Array con valore senza terminatore (" + names[namesStartIndex + i] + ")," + columnsValues[i] + ".");
                    }
                    lineValue.append(columnValue + "|*|");
                    final int[] array = columnsValuesIndex;
                    final int n = i;
                    array[n] += columnValue.length() + "|*|".length();
                    ++i;
                }
            }
            lines.add(lineValue.toString());
        }
        throw new EngSqlNoApplException("Parametro Jsp di tipo Array con troppe linee (" + names[namesStartIndex] + ")");
    }
    
    private Vector makeLinesFromNamesElements(final HttpServletRequest request, final String[] names, final int namesStartIndex, final int namesCounter) throws EngSqlNoApplException {
        final Vector lines = new Vector();
        int lineIndex = 0;
        do {
            final StringBuffer lineValue = new StringBuffer();
            lineValue.setLength(0);
            int columnNullCounter = 0;
            for (int i = 0; i < namesCounter; ++i) {
                final String nameIndexed = this.makeNameIndexed(names[namesStartIndex + i], lineIndex);
                String columnValue;
                if (Parameter.isNameAttribute(nameIndexed)) {
                    final Object valueAttribute = request.getAttribute(nameIndexed);
                    columnValue = ((valueAttribute instanceof String) ? ((String)valueAttribute) : null);
                }
                else {
                    columnValue = request.getParameter(nameIndexed);
                }
                if (columnValue == null) {
                    columnValue = "";
                }
                if (columnValue == null) {
                    ++columnNullCounter;
                }
                else if (columnValue.equals("")) {
                    lineValue.append("|*|");
                    ++columnNullCounter;
                }
                else {
                    lineValue.append(columnValue + "|*|");
                }
            }
            if (columnNullCounter >= namesCounter) {
                break;
            }
            lines.add(lineValue.toString());
        } while (++lineIndex < 1000);
        return lines;
    }
    
    private String makeNameIndexed(final String name, final int index) {
        return name + "_" + (index + 1);
    }
    
    private static String makeNextToken(final String string, final int startIndex, final String token) {
        final int index = string.indexOf(token, startIndex);
        if (index == -1) {
            return null;
        }
        if (index == 0) {
            return "";
        }
        return string.substring(startIndex, index);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(HttpArray:");
        sb.append(super.toString() + ",");
        for (int i = 0; i < this.namesCounter; ++i) {
            sb.append(this.names[this.namesStartIndex + i] + ",");
        }
        sb.append(this.namesStartIndex + "," + this.namesCounter);
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final HttpArray p = new HttpArray(this.getPosition(), this.getName(), this.getNames(), this.getNamesStartIndex(), this.getNamesCounter());
        if (this.isModeIn()) {
            p.setModeIn();
        }
        else if (this.isModeOut()) {
            p.setModeOut();
        }
        else if (this.isModeInOut()) {
            p.setModeInOut();
        }
        return p;
    }
    
    public int getNamesStartIndex() {
        return this.namesStartIndex;
    }
    
    public int getNamesCounter() {
        return this.namesCounter;
    }
    
    public void configure() {
        final Properties props = new Properties();
        try {
            final InputStream fis = this.getClass().getClassLoader().getResourceAsStream("HttpArray.properties");
            if (fis != null) {
                props.load(fis);
                this.setArrayDescriptorName(props.getProperty("ARRAY_DESCRIPTOR_NAME", "DDD_MASTER.DDVA_STRING"));
            }
        }
        catch (Exception ex) {
            Logger.getLogger().error((Object)("Errore durante la lettura del file di properties: " + ex.getMessage()));
        }
    }
    
    static {
        HttpArray.ARRAY_DESCRIPTOR_NAME = "DDD_MASTER.DDVA_STRING";
    }
}
