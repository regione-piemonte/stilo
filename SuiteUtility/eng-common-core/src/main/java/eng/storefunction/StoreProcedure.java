// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import eng.storefunction.parameters.ErrorMessageParameter;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.sql.CallableStatement;
import eng.util.Logger;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import eng.database.modal.EngResultSet;
import java.sql.Connection;
import java.util.Vector;

public class StoreProcedure
{
    Vector parameters;
    String storeName;
    Connection con;
    
    public StoreProcedure(final String storeName, Vector parameters, final Connection con) {
        this.storeName = "";
        this.con = null;
        this.storeName = storeName;
        this.parameters = parameters;
        this.con = con;
        if (parameters == null) {
            parameters = new Vector();
        }
    }
    
    public StoreProcedure(final Connection con) {
        this(null, null, con);
    }
    
    public StoreProcedure(final Vector parameters, final Connection con) {
        this(null, parameters, con);
    }
    
    public StoreProcedure(final String storeName, final Connection con) {
        this(storeName, null, con);
    }
    
    public StoreProcedure(final String storeName) {
        this(storeName, null, null);
    }
    
    public StoreProcedure(final String storeName, final Vector parameters) {
        this(storeName, parameters, null);
    }
    
    public StoreProcedure(final String storeName, final StoreParameter[] parameters) {
        this(storeName, null, null);
        if (parameters != null) {
            this.parameters = new Vector();
            for (int i = 0; i < parameters.length; ++i) {
                this.parameters.add(parameters[i]);
            }
        }
    }
    
    public EngResultSet executeQuery(final String sql) throws StoreProcedureException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (sql == null) {
            throw new StoreProcedureException(-100, "SQL non specificato o nullo.");
        }
        if (this.con == null) {
            throw new StoreProcedureException(-101, "Connessione non specificata o nulla.");
        }
        try {
            stmt = this.con.prepareStatement(sql);
            this.prepareStore(stmt);
            rs = stmt.executeQuery();
            return new EngResultSet(rs);
        }
        catch (StoreProcedureException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new StoreProcedureException(-103, ex2.getMessage());
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception ex3) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (Exception ex4) {}
            }
        }
    }
    
    public void execute(final String sql) throws StoreProcedureException {
        PreparedStatement stmt = null;
        final ResultSet rs = null;
        if (sql == null) {
            throw new StoreProcedureException(-100, "SQL non specificato o nullo.");
        }
        if (this.con == null) {
            throw new StoreProcedureException(-101, "Connessione non specificata o nulla.");
        }
        try {
            stmt = this.con.prepareStatement(sql);
            this.prepareStore(stmt);
            stmt.execute(sql);
        }
        catch (StoreProcedureException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new StoreProcedureException(-103, ex2.getMessage());
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception ex3) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (Exception ex4) {}
            }
        }
    }
    
    public void execute() throws StoreProcedureException {
        CallableStatement stmt = null;
        if (this.storeName == null) {
            throw new StoreProcedureException(-100, "Nome della store non specificato o nullo.");
        }
        if (this.con == null) {
            throw new StoreProcedureException(-101, "Connessione non specificata o nulla.");
        }
        final int lastIndex = this.getMaxIndex(this.parameters);
        int resultVal = 0;
        String sql = "{call ? := " + this.storeName + "(";
        for (int i = 1; i < lastIndex; ++i) {
            if (i > 1) {
                sql += ",";
            }
            sql += "?";
        }
        sql += ")}";
        try {
            stmt = this.con.prepareCall(sql);
            this.prepareStore(stmt);
            stmt.execute();
            resultVal = stmt.getInt(1);
            for (int i = 0; i < this.parameters.size(); ++i) {
                if (this.parameters.elementAt(i) instanceof StoreParameter) {
                    final StoreParameter storeParameter = this.parameters.elementAt(i);
                    if ((storeParameter.getInOut() & 0x2) != 0x0) {
                        storeParameter.setValue(stmt.getObject(storeParameter.getIndex()));
                    }
                }
            }
            if (resultVal == 0) {
                final String msg = stmt.getString(this.getErrorMessageIndex());
                Logger.getLogger().error((Object)("Command SQL = " + sql + ". Exception[" + resultVal + "]=" + msg));
                throw new StoreProcedureException(resultVal, "" + msg);
            }
        }
        catch (StoreProcedureException ex) {
            Logger.getLogger().info((Object)ex.getMessage());
            throw ex;
        }
        catch (Exception ex2) {
            Logger.getLogger().fatal((Object)("Errore durante l'esecuzione della store: " + sql), (Throwable)ex2);
            throw new StoreProcedureException(-103, ex2.getMessage());
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception ex3) {}
            }
        }
    }
    
    protected void prepareStore(final CallableStatement stmt) throws StoreProcedureException {
        StoreParameter p = null;
        try {
            for (int i = 0; i < this.parameters.size(); ++i) {
                p = this.parameters.elementAt(i);
                this.parameters.elementAt(i).prepareStore(stmt);
            }
        }
        catch (Exception ex) {
            throw new StoreProcedureException(-102, ex.getMessage());
        }
    }
    
    protected void prepareStore(final PreparedStatement stmt) throws StoreProcedureException {
        try {
            for (int i = 0; i < this.parameters.size(); ++i) {
                this.parameters.elementAt(i).prepareStore(stmt);
            }
        }
        catch (Exception ex) {
            throw new StoreProcedureException(-102, ex.getMessage());
        }
    }
    
    protected int getMaxIndex(final Vector params) {
        int k = 0;
        for (int i = 0; i < this.parameters.size(); ++i) {
            if (this.parameters.elementAt(i) instanceof StoreParameter) {
                k = ((k < this.parameters.elementAt(i).getIndex()) ? this.parameters.elementAt(i).getIndex() : k);
            }
        }
        return k;
    }
    
    public void setConnection(final Connection con) {
        this.con = con;
    }
    
    public void fillWebParameters(final HttpServletRequest request) {
        this.fillWebParameters(request, null);
    }
    
    public void fillWebParameters(final HttpSession session) {
        this.fillWebParameters(null, session);
    }
    
    public void fillWebParameters(final HttpServletRequest request, final HttpSession session) {
        for (int i = 0; i < this.parameters.size(); ++i) {
            if (request != null && this.parameters.elementAt(i) instanceof HttpRequestParameter) {
                this.parameters.elementAt(i).setHttpRequest(request);
            }
            if (session != null && this.parameters.elementAt(i) instanceof HttpSessionParameter) {
                this.parameters.elementAt(i).setHttpSession(session);
            }
        }
    }
    
    public void fillWebAttributes(final HttpServletRequest request) {
        for (int i = 0; i < this.parameters.size(); ++i) {
            final StoreParameter sp = this.parameters.elementAt(i);
            if (sp.getName() != null) {
                final Object atr = sp.getAttributeValue();
                if (atr != null) {
                    request.setAttribute(sp.getName(), atr);
                }
            }
        }
    }
    
    public String getStoreName() {
        return this.storeName;
    }
    
    public StoreProcedure cloneMe() {
        final Vector v = new Vector();
        for (int i = 0; i < this.parameters.size(); ++i) {
            v.add(this.parameters.elementAt(i).cloneMe());
        }
        return new StoreProcedure(this.storeName, v);
    }
    
    public StoreParameter getParameterByName(final String name) {
        for (int i = 0; i < this.parameters.size(); ++i) {
            if (this.parameters.elementAt(i).getName() != null && this.parameters.elementAt(i).getName().equals(name)) {
                return this.parameters.elementAt(i);
            }
        }
        return null;
    }
    
    public StoreParameter getParameter(final int i) {
        if (i <= 0) {
            return null;
        }
        if (i - 1 < this.parameters.size()) {
            return this.parameters.elementAt(i - 1);
        }
        return null;
    }
    
    public int size() {
        if (this.parameters == null) {
            return -1;
        }
        return this.parameters.size();
    }
    
    protected int getErrorMessageIndex() {
        int k = 0;
        for (int i = 0; i < this.parameters.size(); ++i) {
            if (this.parameters.elementAt(i) instanceof ErrorMessageParameter) {
                k = this.parameters.elementAt(i).getIndex();
                break;
            }
        }
        if (k == 0) {
            return this.getMaxIndex(this.parameters);
        }
        return k;
    }
}
