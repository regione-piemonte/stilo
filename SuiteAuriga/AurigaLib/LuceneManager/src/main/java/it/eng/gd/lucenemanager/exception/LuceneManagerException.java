/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class LuceneManagerException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6930187841015608605L;

	protected String errMessage;

	protected Exception errException;

	public LuceneManagerException(String message, Exception e) {
		super(message + " " + e.getMessage(), e);
		this.errMessage = message;
		this.errException = e;
	}

	public LuceneManagerException(String message) {
		super(message);
		this.errMessage = message;
	}
}
