package eng.database.exception;

public class ConnectionFailed extends EngSqlNoApplException {
	public ConnectionFailed(String message)	{
		super(message);
	}
}
