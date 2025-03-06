package eng.storefunction.singleton;

/**
 * Describe class StoreFunctionDefinitionError here.
 *
 *
 * Created: Wed Oct 12 16:05:35 2005
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision: 1.1.1.1 $
 */
public class StoreFunctionDefinitionError extends Error {

	/**
	 * Crea una nuova istanza di <code>StoreFunctionDefinitionError</code> .
	 *
	 * @param cnfe a <code>Throwable</code> l'eccezione scatenante
	 */
	public StoreFunctionDefinitionError(Throwable cnfe) {
		super(cnfe);
	}

	/**
	 * Crea una nuova istanza di <code>StoreFunctionDefinitionError</code> .
	 *
	 * @param cnfe a <code>String</code> Messaggio esplicativo
	 */
	public StoreFunctionDefinitionError(String cnfe) {
		super(cnfe);
	}
}
