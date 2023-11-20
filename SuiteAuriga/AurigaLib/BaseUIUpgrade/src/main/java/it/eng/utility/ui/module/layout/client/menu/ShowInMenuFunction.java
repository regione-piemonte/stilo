/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * Classe che si occupa di stabilire
 * quali siano le condizioni in base al quale mostrare o meno
 * una colonna nel campo delle colonne selezionabili nell'headerContextMenu
 * della lista
 * 
 * @author Rametta
 *
 */
public abstract class ShowInMenuFunction {

	public String[] fields;
	protected CustomList list;
	
	/**
	 * Costruttore: Accetta i fields da condizionare e la relativa lista
	 * @param pListGridField
	 * @param pList
	 */
	public ShowInMenuFunction(ListGridField[] pListGridField, CustomList pList) {
		list = pList;
		fields = new String[pListGridField.length];
		int i = 0;
		for (ListGridField lListGridField : pListGridField){
			fields[i] = lListGridField.getTitle();
			i++;
		}
	}
	
	/**
	 * E' una funzione booleana che decide se la colonna deve essere mostrata o meno 
	 * all'interno dell'headerContextMenu
	 * @return
	 */
	public abstract boolean mustBeShown();

	/**
	 * Dato il nome di un field (il titolo), mi dice se la funzione 
	 * riguarda il relativo field
	 * @param name
	 * @return
	 */
	public boolean contains(String name) {
		for (String lStrField : fields){
			if (lStrField != null && lStrField.equals(name)) return true;
		} 
		return false;
	}
}
