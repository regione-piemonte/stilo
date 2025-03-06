package it.eng.core.business.enums;

/**
 * Enumeration che identifica il tipo di operazione DAO.
 * <br>
 * Viene utilizzata nei metodi di gestione eccezioni in 
 * caso di unique constraints violati.
 * @author upescato
 *
 */

public enum OperationType {
	
	SAVE,
	UPDATE;
	
}
