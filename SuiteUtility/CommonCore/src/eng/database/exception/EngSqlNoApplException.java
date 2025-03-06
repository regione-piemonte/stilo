package eng.database.exception;

public class EngSqlNoApplException extends EngException {
	public EngSqlNoApplException(String message)	{
		super(message);
		codError = NO_APPLICATION_ERR;
	}
}
