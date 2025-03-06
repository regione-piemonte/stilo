package eng.singleton.stores;

import eng.database.exception.EngException;
import java.util.Hashtable;

/**
 * Describe interface StoredDefInterface here.
 *
 *
 * Created: Wed Oct 12 16:08:07 2005
 *
 * @author <a href="mailto:saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision: 1.1.1.1 $
 */
public interface StoredDefInterface {
  static final String
  MESSAGE_SUCCESS_GENERIC = "Operazione avvenuta con successo;";

  static final String
  MESSAGE_SUCCESS_INSERT = "Inserimento avvenuto con successo;";

  static final String
  MESSAGE_SUCCESS_MODIFY = "Modifica avvenuta con successo";


  static final String
  MESSAGE_SUCCESS_DELETE = "Cancellazione avvenuta con successo";

  void addNewStoredFunction(Hashtable map) 
	  throws EngException;
}
