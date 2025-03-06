// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;
import eng.database.exception.EngSqlNoApplException;
import java.io.Writer;
import java.sql.SQLException;
import oracle.sql.CLOB;
import it.eng.auriga.repository2.xml.sezionecache.SezioneCache;
import java.sql.Connection;
import eng.database.exception.EngException;

public class SezioneCacheClob extends Parameter
{
    public SezioneCacheClob(final int position) throws EngException {
        this(position, null);
    }
    
    public SezioneCacheClob(final int position, final String name) throws EngException {
        super(position, 2005, name);
    }
    
    public static CLOB makeCLOB(final Connection connection, final SezioneCache sc) throws SQLException, EngSqlNoApplException {
        CLOB tempClob = null;
        try {
            tempClob = CLOB.createTemporary(connection, true, 10);
            tempClob.open(1);
            final Writer tempClobWriter = tempClob.getCharacterOutputStream();
            sc.marshal(tempClobWriter);
            tempClobWriter.flush();
            tempClobWriter.close();
            tempClob.close();
        }
        catch (SQLException sqlexp) {
            tempClob.freeTemporary();
            sqlexp.printStackTrace();
        }
        catch (Exception exp) {
            tempClob.freeTemporary();
            exp.printStackTrace();
        }
        return tempClob;
    }
    
    public void executeSetValueArray(final Vector lines) throws EngException {
        this.setValueArray(lines);
    }
    
    @Override
    public void makeValue(final Object httpRequest) throws EngException {
        final String name = this.getName();
        final SezioneCache sc = (SezioneCache)((HttpServletRequest)httpRequest).getAttribute(name);
        this.setValueSezioneCache(sc);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(HttpClob:");
        sb.append(super.toString() + ",");
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Parameter cloneMe() throws EngException {
        final SezioneCacheClob p = new SezioneCacheClob(this.getPosition(), this.getName());
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
}
