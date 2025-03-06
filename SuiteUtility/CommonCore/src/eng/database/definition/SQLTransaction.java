package eng.database.definition;

public interface SQLTransaction{
    ResultSelection execute(eng.util.Properties prop) 
	    throws eng.database.exception.EngSqlNoApplException,
	           eng.database.exception.EngSqlApplException;
			
    public void commit()    throws eng.database.exception.EngSqlNoApplException;
	public void rollback()  throws eng.database.exception.EngSqlNoApplException;
}
