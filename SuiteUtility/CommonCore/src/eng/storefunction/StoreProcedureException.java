package eng.storefunction;


import java.util.*;

/**
  Classe che permette l'esecuzione di una store procedure semplificando la creazione
  della store.
*/
public class StoreProcedureException extends java.lang.Exception {

	protected int returnCode = 0;

	public StoreProcedureException(int returnCode, String message)
	{
		super(message);
		this.returnCode = returnCode;
	}

	public int getReturnCode()
	{
		return returnCode;
	}

}
