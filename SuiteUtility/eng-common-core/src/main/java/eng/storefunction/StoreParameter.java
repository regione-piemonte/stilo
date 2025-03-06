// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.database.exception.EngSqlNoApplException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.math.BigDecimal;

public class StoreParameter
{
    public static final int IN = 1;
    public static final int OUT = 2;
    public static final int IN_OUT = 3;
    protected int inOut;
    protected int type;
    protected Object value;
    protected String name;
    protected int index;
    
    public StoreParameter(final int index, final String name, final Object value, final int type, final int inout) {
        this.inOut = 1;
        this.type = 12;
        this.value = null;
        this.name = null;
        this.index = 0;
        this.index = index;
        this.name = name;
        this.value = value;
        this.type = type;
        this.inOut = inout;
    }
    
    public StoreParameter(final int index, final String name, final int type, final int inout) {
        this(index, name, null, type, inout);
    }
    
    public StoreParameter(final int index, final int type) {
        this(index, null, null, type, 2);
    }
    
    public StoreParameter(final int index) {
        this(index, null, null, 12, 2);
    }
    
    public StoreParameter(final int index, final Object val) {
        this(index, null, null, 12, 1);
    }
    
    public StoreParameter(final int index, final Object val, final int type) {
        this(index, null, null, 12, type);
    }
    
    public int getInOut() {
        return this.inOut;
    }
    
    public void setInOut(final int inOut) {
        this.inOut = inOut;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        if (this.type != 4) {
            return this.value;
        }
        if (this.value instanceof BigDecimal) {
            return new Integer(((BigDecimal)this.value).intValue());
        }
        return this.value;
    }
    
    public void prepareStore(final CallableStatement stmt) throws SQLException, EngSqlNoApplException {
        if ((this.getInOut() & 0x1) != 0x0) {
            this.prepareStore((PreparedStatement)stmt);
        }
        if ((this.getInOut() & 0x2) != 0x0) {
            stmt.registerOutParameter(this.index, this.type);
        }
    }
    
    public void prepareStore(final PreparedStatement stmt) throws SQLException, EngSqlNoApplException {
        if ((this.getInOut() & 0x1) != 0x0 && this.value != null && !this.value.equals("")) {
            switch (this.type) {
                case 4: {
                    stmt.setInt(this.index, Integer.parseInt(this.value + ""));
                    break;
                }
                case 2: {
                    stmt.setLong(this.index, Long.parseLong(this.value + ""));
                    break;
                }
                case 12: {
                    stmt.setString(this.index, this.value + "");
                    break;
                }
                default: {
                    stmt.setNull(this.index, this.type);
                    break;
                }
            }
        }
        else if ((this.getInOut() & 0x1) != 0x0) {
            stmt.setNull(this.index, this.type);
        }
    }
    
    public StoreParameter cloneMe() {
        return new StoreParameter(this.index, this.name, this.value, this.type, this.inOut);
    }
    
    public Object getAttributeValue() {
        return this.getValue();
    }
    
    public int getType() {
        final int ret = new Integer(this.type);
        return ret;
    }
}
