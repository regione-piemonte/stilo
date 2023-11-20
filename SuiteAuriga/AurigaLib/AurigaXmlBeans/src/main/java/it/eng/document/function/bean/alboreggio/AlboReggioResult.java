/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioResult  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int codice;

    private String messaggio;

    private int progressivo;

    private int anno;

    private String idRecord;

	public int getCodice() {
		return codice;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public int getProgressivo() {
		return progressivo;
	}

	public int getAnno() {
		return anno;
	}

	public String getIdRecord() {
		return idRecord;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public void setIdRecord(String idRecord) {
		this.idRecord = idRecord;
	}

}
