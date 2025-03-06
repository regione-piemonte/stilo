// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import eng.util.XMLUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import eng.database.exception.EngSqlNoApplException;
import java.util.Iterator;
import eng.database.modal.EngResultSet;
import java.sql.Array;
import eng.util.Logger;
import java.util.Vector;
import eng.storefunction.StoreParameter;

public class ArrayParameter extends StoreParameter
{
    private static final String ARRAY_DESCRIPTOR_NAME = "DDD_MASTER.DDVA_STRING";
    private static final int LINES_MAXIMUM = 1000;
    public static final String COLUMN_SEPARATOR = "|*|";
    private static final int COLUMN_MAXIMUM = 100;
    
    public ArrayParameter(final int index, final String name, final Vector lines, final int inOut) {
        super(index, name, lines, 2003, inOut);
        if (this.value == null) {
            this.value = new Vector();
        }
    }
    
    public ArrayParameter(final int index, final String name) {
        this(index, name, null, 2);
        this.value = new Vector();
    }
    
    public ArrayParameter(final int index) {
        this(index, null, new Vector(), 2);
    }
    
    public ArrayParameter(final int index, final String name, final Vector lines) {
        this(index, name, lines, 1);
    }
    
    public ArrayParameter(final int index, final Vector lines) {
        this(index, null, lines, 1);
    }
    
    @Override
    public void setValue(final Object value) {
        if (value != null) {
            Logger.getLogger().info((Object)("Imposto valore per Array: " + value.getClass().getName()));
            if (value instanceof Array) {
                try {
                    final Array ar = (Array)value;
                    final String[] strings = (String[])ar.getArray();
                    final Vector v = new Vector();
                    for (int i = 0; i < strings.length; ++i) {
                        final String s = strings[i];
                        v.add(strings[i]);
                    }
                    this.value = v;
                }
                catch (Exception ex) {
                    Logger.getLogger().error((Object)("Exception in ArrayParameter." + ex + "."));
                }
            }
            else if (value instanceof Vector) {
                this.value = value;
            }
            else {
                this.value = new Vector();
                ((Vector)this.value).add("" + value);
            }
        }
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
    public EngResultSet getEngResultSet() throws EngSqlNoApplException {
        final EngResultSet result = new EngResultSet();
        final Iterator lines = ((Vector)this.value).iterator();
        int cline = 0;
        while (lines.hasNext()) {
            final String line = lines.next();
            final Vector columns = makeColumns(line);
            for (int i = 0; i < columns.size(); ++i) {
                result.addColumn(columns.elementAt(i));
            }
            result.addRow();
            ++cline;
        }
        return result;
    }
    
    @Override
    public String toString() {
        final Iterator lines = ((Vector)this.value).iterator();
        String line = "";
        while (lines.hasNext()) {
            line += lines.next();
        }
        return line;
    }
    
    public EngResultSet getEngResultSet(final String col_sep) throws EngSqlNoApplException {
        final EngResultSet result = new EngResultSet();
        final Iterator lines = ((Vector)this.value).iterator();
        int cline = 0;
        while (lines.hasNext()) {
            final String line = lines.next();
            final Vector columns = makeColumns(line, col_sep);
            for (int i = 0; i < columns.size(); ++i) {
                result.addColumn(columns.elementAt(i));
            }
            result.addRow();
            ++cline;
        }
        return result;
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
    
    public static Vector makeColumns(String line, final String col_sep) throws EngSqlNoApplException {
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
        while ((posLast = line.indexOf(col_sep, posPrec)) != -1) {
            final String column = line.substring(posPrec, posLast);
            v.add(column);
            posPrec = posLast + col_sep.length();
            if (v.size() > 100) {
                throw new EngSqlNoApplException("Errore interno (131)" + line + ".");
            }
        }
        return v;
    }
    
    @Override
    public void prepareStore(final CallableStatement stmt) throws SQLException {
        if ((this.getInOut() & 0x1) != 0x0) {
            this.prepareStore((PreparedStatement)stmt);
        }
        if ((this.getInOut() & 0x2) != 0x0) {
            stmt.registerOutParameter(this.index, 2003, "DDD_MASTER.DDVA_STRING");
        }
    }
    
    @Override
    public void prepareStore(final PreparedStatement stmt) throws SQLException {
        if ((this.getInOut() & 0x1) != 0x0 && this.value != null) {
            final Array ar = this.makeARRAY(stmt.getConnection(), (Vector)this.value);
            stmt.setArray(this.index, ar);
        }
        else {
            stmt.setNull(this.index, 2003, "DDD_MASTER.DDVA_STRING");
        }
    }
    
    public Array makeARRAY(final Connection connection, final Vector array) throws SQLException {
        for (int i = 0; i < array.size(); ++i) {
            array.set(i, XMLUtil.verySpecialEscape(array.get(i).toString()));
        }
        final String[] sarray = array.toArray(new String[0]);
        final ArrayDescriptor d = ArrayDescriptor.createDescriptor("DDD_MASTER.DDVA_STRING", connection);
        final ARRAY arrayOracle = new ARRAY(d, connection, (Object)sarray);
        return (Array)arrayOracle;
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new ArrayParameter(this.index, this.name, (Vector)this.value, this.inOut);
    }
    
    @Override
    public Object getAttributeValue() {
        EngResultSet rs = null;
        try {
            rs = this.getEngResultSet();
        }
        catch (Exception ex) {
            rs = null;
        }
        return rs;
    }
}
