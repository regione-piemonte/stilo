/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Bean contenete i risultati della ricerca di lucene
 * 
 * @author Rigo Michele
 * 
 */
@Entity
public class DocumentLuceneBean implements Serializable {

	private static final long serialVersionUID = 3500401033951092380L;

	/**
	 * identifica l'id dell'oggetto salvato
	 */
	@GeneratedValue
	private String objectid;

	/**
	 * restituisce lo score di ricerca della query da 0 a 1
	 */
	private float score;

	/**
	 * metadati del documento indicizzati
	 */
	private Map<String, String> metadata;

	public DocumentLuceneBean(String objectid, float score) {
		super();
		this.objectid = objectid;
		this.score = score;
	}

	@Id
	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object obj) {
		if (objectid.equals(((DocumentLuceneBean) obj).getObjectid()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return objectid.hashCode();
	}

}
