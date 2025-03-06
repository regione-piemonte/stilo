// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.modal;

import it.eng.dbpoolmanager.DBPoolManager;
import java.sql.SQLException;
import eng.database.exception.EngSqlNoApplException;
import com.engiweb.mcframework.kernel.DBManager;
import java.sql.Connection;
import eng.database.definition.SQLTransaction;

public abstract class EngConnectionForSelection implements SQLTransaction
{
    protected Connection con;
    protected DBManager dbMan;
    protected int idComune;
    protected String idComuneStr;
    
    protected EngConnectionForSelection(final int nIdComune) throws EngSqlNoApplException {
        this.con = null;
        this.dbMan = null;
        this.idComune = -1;
        this.idComuneStr = "";
        this.idComune = nIdComune;
    }
    
    protected EngConnectionForSelection(final String nIdComune) throws EngSqlNoApplException {
        this.con = null;
        this.dbMan = null;
        this.idComune = -1;
        this.idComuneStr = "";
        this.idComuneStr = nIdComune;
    }
    
    protected EngConnectionForSelection() throws EngSqlNoApplException {
        this.con = null;
        this.dbMan = null;
        this.idComune = -1;
        this.idComuneStr = "";
        this.idComune = -1;
    }
    
    protected EngConnectionForSelection(final boolean debug) throws EngSqlNoApplException {
        this.con = null;
        this.dbMan = null;
        this.idComune = -1;
        this.idComuneStr = "";
        this.con = null;
    }
    
    @Override
    public void commit() throws EngSqlNoApplException {
        try {
            this.con.commit();
        }
        catch (SQLException sqle) {
            throw new EngSqlNoApplException("Engineering. Command SQL = commit. Exception=" + sqle.getMessage());
        }
    }
    
    @Override
    public void rollback() throws EngSqlNoApplException {
        try {
            this.con.rollback();
        }
        catch (SQLException sqle) {
            throw new EngSqlNoApplException("Engineering. Command SQL = rollback. Exception=" + sqle.getMessage());
        }
    }
    
    public void getPooledConnection() throws EngSqlNoApplException {
        if (this.isDbPoolActive()) {
            try {
                this.con = DBPoolManager.createDBPoolManagerConnection(this.idComuneStr);
                return;
            }
            catch (Exception e2) {
                if (this.con != null) {
                    try {
                        this.con.close();
                    }
                    catch (Exception e1) {
                        throw new EngSqlNoApplException(e1.getMessage());
                    }
                }
                throw new EngSqlNoApplException(e2.getMessage());
            }
        }
        try {
            this.dbMan = DBManager.createDBManager(this.idComune);
            this.con = this.dbMan.getConnection();
        }
        catch (Exception e2) {
            if (this.dbMan != null) {
                try {
                    this.dbMan.closeConnection();
                    DBManager.showAllConnections();
                    this.con = null;
                    this.dbMan = null;
                }
                catch (Exception e1) {
                    throw new EngSqlNoApplException(e1.getMessage());
                }
            }
            throw new EngSqlNoApplException(e2.getMessage());
        }
    }
    
    public void releasePooledConnection() throws EngSqlNoApplException {
        if (this.isDbPoolActive()) {
            if (this.con == null) {
                return;
            }
            try {
                this.con.close();
                return;
            }
            catch (Exception e) {
                throw new EngSqlNoApplException(e.getMessage());
            }
        }
        if (this.dbMan != null) {
            try {
                this.dbMan.closeConnection();
                DBManager.showAllConnections();
                this.con = null;
                this.dbMan = null;
            }
            catch (Exception e) {
                throw new EngSqlNoApplException("Engineering. Fallito rilascio connessione al DBManager. Exception=" + e.getMessage());
            }
        }
    }
    
    public int getIdComune() {
        return this.idComune;
    }
    
    public void setIdComune(final int idComune) {
        this.idComune = idComune;
    }
    
    private boolean isDbPoolActive() {
        boolean res = false;
        if (!this.idComuneStr.equals("")) {
            res = true;
        }
        return res;
    }
}
