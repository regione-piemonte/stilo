/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlboReggioAtto  implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static long serialVersionUID = 1L;

	private String id;

    private int numero;

    private String idType;

    private int anno;

    private String oggetto;

    private String idUtente;

    private String manualeImport;

    private String dataInserimento;

    private String dataInizio;

    private String dataFine;

    private String data;

    private String idUo;

    private int progressivo;

    private String provenienza;

    private String dataAnnullamento;

    private String idUtenteAnnullamento;

    private String estremiAnnullamento;

    private String violazioneLegge;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public int getNumero() {
		return numero;
	}

	public String getIdType() {
		return idType;
	}

	public int getAnno() {
		return anno;
	}

	public String getOggetto() {
		return oggetto;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public String getManualeImport() {
		return manualeImport;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public String getData() {
		return data;
	}

	public String getIdUo() {
		return idUo;
	}

	public int getProgressivo() {
		return progressivo;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public String getDataAnnullamento() {
		return dataAnnullamento;
	}

	public String getIdUtenteAnnullamento() {
		return idUtenteAnnullamento;
	}

	public String getEstremiAnnullamento() {
		return estremiAnnullamento;
	}

	public String getViolazioneLegge() {
		return violazioneLegge;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public void setManualeImport(String manualeImport) {
		this.manualeImport = manualeImport;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public void setDataAnnullamento(String dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

	public void setIdUtenteAnnullamento(String idUtenteAnnullamento) {
		this.idUtenteAnnullamento = idUtenteAnnullamento;
	}

	public void setEstremiAnnullamento(String estremiAnnullamento) {
		this.estremiAnnullamento = estremiAnnullamento;
	}

	public void setViolazioneLegge(String violazioneLegge) {
		this.violazioneLegge = violazioneLegge;
	}

}
