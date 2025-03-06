package it.eng.core.business.converter;

/**
 * Interfaccia che espone il metodo per popolare i valori di un bean in maniera custom
 * @author Administrator
 *
 */
public interface IBeanPopulate<S,O> {

	/**
	 * Popolo il bean
	 * @param src
	 * @param dest
	 */
	public void populate(S src, O dest) throws Exception;
	
	/**
	 * Le implementazioni di questo metodo dovranno gestire gli eventuali update a property complesse (i.e.: Foreign keys) con il
	 * meccanismo dell'HashSet di AbstractBean.
	 * <br>
	 * Il pattern implementativo e' illustrato nel seguente pseudocode:
	 * <br><br>
	 * <code>
	 * Per ogni property complessa si verifica se e' stata modificata e, in caso affermativo, si aggiorna @param dest<br>
	 * if(src.hasPropertyBeenModified("PropertyComplessaModificata")) {<br>
	 *  		dest.setPropertyComplessa(getPropertyComplessaModificata);<br>
	 * 		}
	 * </code>
	 * @param src
	 * @param dest
	 * @throws Exception
	 */
	public void populateForUpdate(S src, O dest) throws Exception;

	//public void populateReverse(O dest, S src) throws Exception;
	
}
