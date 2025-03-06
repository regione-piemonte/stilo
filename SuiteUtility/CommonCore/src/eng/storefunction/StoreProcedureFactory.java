package eng.storefunction;

public interface StoreProcedureFactory
{
	/**
		Questo metodo crea una nuova istanza di StoreProcedure replicando un'istanza
		statica. La StoreProcedure ritornata è thread safe.
	*/
	public StoreProcedure getStoreProcedure(String procedureName);
}
