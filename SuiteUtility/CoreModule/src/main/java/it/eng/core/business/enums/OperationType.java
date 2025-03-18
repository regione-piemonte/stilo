/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
