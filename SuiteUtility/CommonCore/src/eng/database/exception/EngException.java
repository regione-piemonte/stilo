package eng.database.exception;

public class EngException extends Exception {

    public static final int NO_APPLICATION_ERR = 50000;
    public static final int NO_VALID_FORM = 60000;
    protected int codError;


//    private EngException(){
//		codError = 0;
//	}

	public EngException(int codError){

       this.codError = codError;

	}

	public EngException(String message)	{
   	    super(message);
		System.err.println((new java.util.Date()).toString() + message);
	}

	public int getErrorCod(){
		return codError;
	}

}
