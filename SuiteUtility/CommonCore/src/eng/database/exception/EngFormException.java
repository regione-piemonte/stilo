package eng.database.exception;

public class EngFormException extends EngException {
	public EngFormException(String message)	{
		super(message);
		codError = NO_VALID_FORM;
	}
}
