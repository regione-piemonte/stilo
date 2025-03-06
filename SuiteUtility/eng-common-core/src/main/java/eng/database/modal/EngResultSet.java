// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.modal;

import eng.util.XMLUtil;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import eng.database.exception.EngSqlNoApplException;
import java.sql.ResultSet;
import java.util.Vector;
import java.io.Serializable;
import eng.database.definition.ResultSelection;

public class EngResultSet implements ResultSelection, Serializable
{
    private static final long serialVersionUID = 1L;
    private Vector rows;
    private Vector column;
    private int position;
    private int numColumn;
    private boolean tooManyRows;
    
    public EngResultSet() {
        this.rows = null;
        this.column = null;
        this.position = 0;
        this.numColumn = 0;
        this.tooManyRows = false;
        this.rows = new Vector();
        this.column = new Vector();
    }
    
    public EngResultSet(final boolean isManyRows) {
        this();
        this.tooManyRows = isManyRows;
    }
    
    public EngResultSet(final ResultSet rs, final boolean isManyRows) throws EngSqlNoApplException {
        this(rs);
        this.tooManyRows = isManyRows;
    }
    
    public EngResultSet(final ResultSet rs) throws EngSqlNoApplException {
        this.rows = null;
        this.column = null;
        this.position = 0;
        this.numColumn = 0;
        this.tooManyRows = false;
        if (rs != null) {
            try {
                this.position = 0;
                final ResultSetMetaData rsm = rs.getMetaData();
                this.numColumn = rsm.getColumnCount();
                boolean find = true;
                while (rs.next()) {
                    if (find) {
                        this.rows = new Vector();
                        find = false;
                    }
                    this.column = new Vector();
                    for (int i = 1; i <= this.numColumn; ++i) {
                        this.column.addElement(rs.getString(i));
                    }
                    this.rows.addElement(this.column);
                }
            }
            catch (SQLException e) {
                throw new EngSqlNoApplException("Engineering. " + e.getMessage());
            }
        }
    }
    
    @Override
    public boolean next() {
        boolean nextElement = false;
        if (this.rows != null && this.rows.size() >= 0 && this.position < this.rows.size()) {
            ++this.position;
            nextElement = true;
        }
        else {
            nextElement = false;
        }
        return nextElement;
    }
    
    @Override
    public String getElement(final int i) throws EngSqlNoApplException {
        try {
            return this.rows.elementAt(this.position - 1).elementAt(i - 1);
        }
        catch (ArrayIndexOutOfBoundsException aioob) {
            throw new EngSqlNoApplException("Engineering. Stack overflow:" + i + ". Column number selected:" + this.numColumn);
        }
    }
    
    public void setElement(final String elem, final int i) throws EngSqlNoApplException {
        try {
            this.rows.elementAt(this.position - 1).setElementAt(elem, i - 1);
        }
        catch (ArrayIndexOutOfBoundsException aioob) {
            throw new EngSqlNoApplException("Engineering. Stack overflow:" + i + ". Column number selected:" + this.numColumn);
        }
    }
    
    public String getElementAsNumber(final int i, final int decimali, final boolean usaPunteggiatura) throws EngSqlNoApplException {
        try {
            final String s = this.rows.elementAt(this.position - 1).elementAt(i - 1);
            String pattern = "";
            pattern = (usaPunteggiatura ? "#,##0" : "0");
            if (decimali > 0) {
                pattern += ".";
                for (int k = 0; k < decimali; ++k) {
                    pattern += "0";
                }
            }
            final DecimalFormat df = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.ITALY));
            try {
                return df.format(Double.parseDouble(s));
            }
            catch (Exception ex) {
                return s;
            }
        }
        catch (ArrayIndexOutOfBoundsException aioob) {
            throw new EngSqlNoApplException("Engineering. Stack overflow:" + i + ". Column number selected:" + this.numColumn);
        }
    }
    
    public String getHtmlElement(final int i) throws EngSqlNoApplException {
        try {
            final String res = this.rows.elementAt(this.position - 1).elementAt(i - 1);
            return XMLUtil.xmlEscape(res);
        }
        catch (ArrayIndexOutOfBoundsException aioob) {
            throw new EngSqlNoApplException("Engineering. Stack overflow:" + i + ". Column number selected:" + this.numColumn);
        }
    }
    
    @Override
    public void addColumn(final String obj) {
        this.column.addElement(obj);
    }
    
    @Override
    public void addRow() {
        this.rows.addElement(this.column);
        this.column = new Vector();
    }
    
    @Override
    public void addRow(final Vector row) {
        this.rows.addElement(row);
    }
    
    @Override
    public Vector getRow(final int index) {
        return this.rows.elementAt(index - 1);
    }
    
    @Override
    public boolean isEmpty() {
        return this.rows.size() == 0;
    }
    
    public int numberColumns() {
        return this.column.size();
    }
    
    public int numberRows() {
        return this.rows.size();
    }
    
    public void setTooManyRows(final boolean tooManyRows) {
        this.tooManyRows = tooManyRows;
    }
    
    public boolean isTooManyRows() {
        return this.tooManyRows;
    }
    
    public void setPosition(final int pos) {
        this.position = pos;
    }
}
