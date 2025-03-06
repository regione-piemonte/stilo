// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction.parameters;

import eng.util.Logger;
import org.apache.commons.io.IOUtils;
import java.io.Writer;
import java.sql.Connection;
import java.sql.Clob;
import eng.database.exception.EngSqlNoApplException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import oracle.sql.CLOB;
import eng.storefunction.StoreParameter;

public class TxtClobParameter extends StoreParameter
{
    public TxtClobParameter(final int index, final String name, final CLOB value, final int inOut) {
        super(index, name, value, 2005, inOut);
    }
    
    public TxtClobParameter(final int index, final String name) {
        this(index, name, null, 2);
        this.value = null;
    }
    
    public TxtClobParameter(final int index) {
        this(index, null, null, 2);
    }
    
    public TxtClobParameter(final int index, final String name, final CLOB value) {
        this(index, name, value, 1);
    }
    
    public TxtClobParameter(final int index, final CLOB value) {
        this(index, null, value, 1);
    }
    
    @Override
    public void setValue(final Object val) {
        CLOB oldValue = null;
        if (this.value != null) {
            oldValue = (CLOB)this.value;
        }
        try {
            if (val == null) {
                this.value = null;
            }
            else if (val instanceof CLOB) {
                this.value = val;
            }
            else {
                this.value = null;
            }
        }
        catch (Exception e) {
            try {
                ((CLOB)val).close();
            }
            catch (Exception ex) {}
            try {
                ((CLOB)val).freeTemporary();
            }
            catch (Exception ex2) {}
        }
        finally {
            if (oldValue != null) {
                try {
                    oldValue.close();
                }
                catch (Exception ex3) {}
                try {
                    oldValue.freeTemporary();
                }
                catch (Exception ex4) {}
            }
        }
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        if (this.value != null) {
            return this.value.toString();
        }
        return null;
    }
    
    @Override
    public void prepareStore(final CallableStatement stmt) throws SQLException, EngSqlNoApplException {
        if ((this.getInOut() & 0x1) != 0x0) {
            this.prepareStore((PreparedStatement)stmt);
        }
        if ((this.getInOut() & 0x2) != 0x0) {
            stmt.registerOutParameter(this.index, 2005);
        }
    }
    
    @Override
    public void prepareStore(final PreparedStatement stmt) throws SQLException, EngSqlNoApplException {
        if ((this.getInOut() & 0x1) != 0x0 && this.value != null) {
            stmt.setClob(this.index, (Clob)this.value);
        }
        else {
            stmt.setNull(this.index, 2005);
        }
    }
    
    public Writer setNewTemporaryCLOB(final Connection conn) throws SQLException, Exception {
        CLOB tempClob = null;
        try {
            tempClob = getNewTemporaryCLOB(conn);
            this.setValue(tempClob);
            return tempClob.getCharacterOutputStream();
        }
        catch (Exception e) {
            try {
                tempClob.freeTemporary();
            }
            catch (Exception ex) {}
            this.setValue(null);
            if (e instanceof SQLException) {
                throw (SQLException)e;
            }
            throw e;
        }
    }
    
    public void setNewTemporaryCLOBStringValue(final Connection conn, final String sVal) throws SQLException, Exception {
        CLOB tempClob = null;
        Writer wr = null;
        if (sVal == null) {
            this.setValue(null);
        }
        else {
            try {
                tempClob = getNewTemporaryCLOB(conn);
                wr = tempClob.getCharacterOutputStream();
                wr.write(sVal);
                this.setValue(tempClob);
                try {
                    wr.flush();
                }
                catch (Exception ex) {}
                try {
                    wr.close();
                }
                catch (Exception ex2) {}
            }
            catch (Exception e) {
                try {
                    wr.flush();
                }
                catch (Exception ex3) {}
                try {
                    wr.close();
                }
                catch (Exception ex4) {}
                try {
                    tempClob.freeTemporary();
                }
                catch (Exception ex5) {}
                this.setValue(null);
                if (e instanceof SQLException) {
                    throw (SQLException)e;
                }
                throw e;
            }
        }
    }
    
    public String getCLOBStringValueAndClose() throws SQLException, Exception {
        if (this.value == null) {
            return null;
        }
        try {
            return IOUtils.toString(((CLOB)this.value).asciiStreamValue());
        }
        finally {
            try {
                ((CLOB)this.value).close();
            }
            catch (Exception ex) {}
        }
    }
    
    @Override
    public StoreParameter cloneMe() {
        return new TxtClobParameter(this.index, this.name, (CLOB)this.value, this.inOut);
    }
    
    @Override
    public Object getAttributeValue() {
        return super.getAttributeValue();
    }
    
    private static CLOB getNewTemporaryCLOB(final Connection conn) throws SQLException, Exception {
        Logger.getLogger().info((Object)"Dentro getNewTemporaryCLOB");
        CLOB tempClob = null;
        try {
            tempClob = CLOB.createTemporary(conn, true, 10);
            tempClob.open(1);
            return tempClob;
        }
        catch (Exception e) {
            try {
                tempClob.close();
            }
            catch (Exception ex) {}
            try {
                tempClob.freeTemporary();
            }
            catch (Exception ex2) {}
            if (e instanceof SQLException) {
                throw (SQLException)e;
            }
            throw e;
        }
        finally {
            Logger.getLogger().info((Object)"Fine getNewTemporaryCLOB");
        }
    }
}
