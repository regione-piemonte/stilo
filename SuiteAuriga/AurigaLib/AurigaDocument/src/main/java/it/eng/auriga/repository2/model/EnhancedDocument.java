/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import org.apache.lucene.document.Document;

/**
 * oggetto che rappresenta il documento arricchito usato da Lucene per conservare anche i dati riguardanti lo score e la gestione dei campi
 * protetti
 * 
 * @author jravagnan
 * 
 */
public class EnhancedDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	private Document doc;

	private String score;

	private boolean privacyDamaging;

	public EnhancedDocument(Document doc, String score, boolean privacy) {
		this.doc = doc;
		this.score = score;
		this.privacyDamaging = privacy;
	}

	public org.apache.lucene.document.Document getDoc() {
		return doc;
	}

	public void setDoc(org.apache.lucene.document.Document doc) {
		this.doc = doc;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public boolean isPrivacyDamaging() {
		return privacyDamaging;
	}

	public void setPrivacyDamaging(boolean privacyDamaging) {
		this.privacyDamaging = privacyDamaging;
	}

}
