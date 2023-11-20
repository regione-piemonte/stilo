/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Elenca le direttive gestite che si possono utilizzare nelle variabili di tipo
 * form
 * 
 * @author massimo malvestio
 *
 */
public enum DirectiveEnum {

	/**
	 * usato per aggiungere righe nelle tabelle
	 */
	ADD_ROWS("add_rows"),
	/**
	 * usato per rimuovere checkbox dalle tabelle
	 */
	REMOVE_ON_EMPTY("remove_on_empty"),
	/**
	 * usato per rimuovere checkbox dalle tabelle
	 */
	REMOVE_ON_FALSE("remove_on_false"),
	/**
	 * se impostato rimpiazza il contenuto del controllo di tipo text con il
	 * valore specificato, altrimenti lo accoda
	 */
	APPEND_CONTENT("append_content"),
	/**
	 * se impostato, indica che la tabella è replicabile, ovvero rappresenta un
	 * template di tabella cui nel sezione cache corrispondono uno o più
	 * variabili complesse
	 */
	REPLICATE("replicate"),
	/**
	 * se impostato e la variabile a cui è associato è "true"/"1", allora
	 * l'intera tabella viene rimossa dal documento
	 */
	REMOVE_TABLE("removeTable");

	private String key;

	private DirectiveEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public static DirectiveEnum get(String key) {

		if (key.equals("add_rows")) {
			return ADD_ROWS;
		} else if (key.equals("remove_on_empty")) {
			return REMOVE_ON_EMPTY;
		} else if (key.equals("remove_on_false")) {
			return REMOVE_ON_FALSE;
		} else if (key.equals("append_content")) {
			return APPEND_CONTENT;
		} else if (key.equals("replicate")) {
			return REPLICATE;
		} else if (key.equals("removeTable")) {
			return REMOVE_TABLE;
		} else {
			return null;
		}
	}
}
