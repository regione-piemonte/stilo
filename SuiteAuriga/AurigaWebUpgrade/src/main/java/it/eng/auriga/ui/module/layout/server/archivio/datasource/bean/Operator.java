/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum Operator {

	EQUALS("uguale"),						// ricerca esatta
	LIKE("simile a (case-sensitive)"), 		// ricerca case-sensitive in like
	ILIKE("simile a (case-insensitive)"), 	// ricerca case-insensitive in like e che ignora i caratteri quali spazi, ., ecc
	GREATER_THAN("maggiore"), 				// se attributo numerico o data
	GREATER_OR_EQUAL("maggiore o uguale"), 	// se attributo numerico o data
	LESS_THAN("minore"),					// se attributo numerico o data
	LESS_OR_EQUAL("minore o uguale"), 		// se attributo numerico o data
	BETWEEN("tra"), 						// se attributo numerico o data
	IS_NULL("non valorizzato"),
	IS_NOT_NULL("valorizzato"),
	CHECKED("spuntato"), 					// se attributo di tipo casella di spunta
	UNCHECKED("non spuntato"); 				// se attributo di tipo casella di spunta
	
	private String value;

	Operator(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}