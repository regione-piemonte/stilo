/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.lucene;

public class LuceneOverflowException extends LuceneException {

	public LuceneOverflowException(String message) {
		super(message);
	}

}
