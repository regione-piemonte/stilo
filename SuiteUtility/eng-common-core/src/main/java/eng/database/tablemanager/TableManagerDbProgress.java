// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.tablemanager;

import eng.database.exception.EngSqlApplException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import eng.database.modal.EngResultSet;
import eng.storefunction.StoreFunction;
import eng.servlet.Commarea;
import eng.database.definition.ResultSelection;
import eng.util.Properties;
import eng.database.exception.EngSqlNoApplException;

public class TableManagerDbProgress extends TableManagerDb
{
    private static final String MESSAGE_EXCEPTION_COMMAND_SQL = "Engineering. Command SQL = ";
    private static final String MESSAGE_EXCEPTION_GENERIC = "Engineering. Generic Exception. ";
    
    public TableManagerDbProgress() throws EngSqlNoApplException {
    }
    
    public TableManagerDbProgress(final int a) throws EngSqlNoApplException {
        super(a);
    }
    
    public TableManagerDbProgress(final String a) throws EngSqlNoApplException {
        super(a);
    }
    
    @Override
    public ResultSelection execute(final Properties properties) throws EngSqlNoApplException, EngSqlApplException {
        final Commarea commarea = (Commarea)properties.get("COMMAREA");
        CallableStatement statement = null;
        StoreFunction storeFunction = null;
        try {
            storeFunction = (StoreFunction)properties;
            this.getPooledConnection();
            statement = storeFunction.makeCallableStatement(commarea, this.con);
            statement.execute();
            if (storeFunction.isInErrorApplicative(commarea, statement)) {
                storeFunction.updateValuesParametersOutput(commarea, statement);
                return null;
            }
            this.con.commit();
            storeFunction.updateValuesParametersOutput(commarea, statement);
            return new EngResultSet();
        }
        catch (SQLException ex) {
            throw new EngSqlNoApplException(ex.getMessage());
        }
        catch (EngSqlNoApplException ex2) {
            throw ex2;
        }
        catch (Exception ex3) {
            throw new EngSqlNoApplException(ex3.getMessage());
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            }
            catch (Exception ex4) {}
            try {
                this.releasePooledConnection();
            }
            catch (Exception ex5) {}
        }
    }
}
