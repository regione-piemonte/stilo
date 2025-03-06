package eng.database;

/**
 * DbOperations.java
 *
 * Costanti per indicare le operazioni ai servizi. Per aggiungere una
 * nuova operazione indicare la nuova costante nel package
 * CRMPK_GENERIC.pck, modificare il servizio di verifica che
 * l'operazione sia consentita e quindi aggiungere la costante a
 * questa classe.
 *
 * Creata: Tue Aug 17 17:19:43 2004
 * Ultima modifica del $Date: 2007/06/14 13:51:32 $ di $Author: ghf $
 *
 * @author <a href="mailto: saint@eng.it">Gian Uberto Lauri</a>
 * @version $Revision: 1.1.1.1 $
 */

public class DbOperations
{
    public final static String INSERIMENTO = "INS";
    public final static String AGGIORNAMENTO = "MOD";
    public final static String ELIMINAZIONE = "DEL";
}
