/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Interfaccia utilizzata per creare una nuova istanza dello StorageService 
 * 
 * @author Mattia Zanin
 *
 */
public interface GenericStorageInfo {
	
	/**
     * Metodo da implementare che ritorna la stringa relativa all'id dell'utilizzatore dello storage
     *
     */
	public String getUtilizzatoreStorageId();
	
}
