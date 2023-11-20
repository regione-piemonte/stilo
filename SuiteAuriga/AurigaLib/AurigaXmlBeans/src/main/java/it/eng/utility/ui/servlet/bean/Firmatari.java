/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

public class Firmatari implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subject;
	private String id;
	// Con figlio di si intende il livello gerarchico
	private String figlioDi;
	private String nomeFirmatario;
	private String enteCertificatore;
	private Date dataFirma;
	private Date dataScadenza;
	private Date dataEmissione;
	private String statoCertificato;
	private String numeroCertificato;
	private String serialNumber;
	private String[] qcStatements;
	private String[] keyUsages;
	private String verificationStatus;
	private String certExpiration;
	private String crlResult;
	private String caReliability;
	private boolean firmaValida;
	// Quando creo il firmatario suppongo che sia sempre una firma esterna ad Auriga, sarà compito del chiamante
	// verificare la provenienza della firma
	private boolean firmaExtraAuriga = true;
	private Date dataVerificaFirma;
	private Marca marca;
	private Firmatari controFirma;
	private String tipoFirmaQA;
	private String tipoFirma;
	private String infoFirma;
	private String titolareFirma;
	private String codiceActivityFirma;
	private String idUtenteLavoroFirma;
	private String idUtenteLoggatoFirma;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFiglioDi() {
		return figlioDi;
	}

	public void setFiglioDi(String figlioDi) {
		this.figlioDi = figlioDi;
	}

	public String getNomeFirmatario() {
		return nomeFirmatario;
	}

	public void setNomeFirmatario(String nomeFirmatario) {
		this.nomeFirmatario = nomeFirmatario;
	}

	public String getEnteCertificatore() {
		return enteCertificatore;
	}

	public void setEnteCertificatore(String enteCertificatore) {
		this.enteCertificatore = enteCertificatore;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataEmissione() {
		return dataEmissione;
	}

	public void setDataEmissione(Date dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public String getStatoCertificato() {
		return statoCertificato;
	}

	public void setStatoCertificato(String statoCertificato) {
		this.statoCertificato = statoCertificato;
	}

	public String getNumeroCertificato() {
		return numeroCertificato;
	}

	public void setNumeroCertificato(String numeroCertificato) {
		this.numeroCertificato = numeroCertificato;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String[] getQcStatements() {
		return qcStatements;
	}

	public void setQcStatements(String[] qcStatements) {
		this.qcStatements = qcStatements;
	}

	public String[] getKeyUsages() {
		return keyUsages;
	}

	public void setKeyUsages(String[] keyUsages) {
		this.keyUsages = keyUsages;
	}

	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String getCertExpiration() {
		return certExpiration;
	}

	public void setCertExpiration(String certExpiration) {
		this.certExpiration = certExpiration;
	}

	public String getCrlResult() {
		return crlResult;
	}

	public void setCrlResult(String crlResult) {
		this.crlResult = crlResult;
	}

	public String getCaReliability() {
		return caReliability;
	}

	public void setCaReliability(String caReliability) {
		this.caReliability = caReliability;
	}

	public boolean isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(boolean firmaValida) {
		this.firmaValida = firmaValida;
	}
	
	public boolean isFirmaExtraAuriga() {
		return firmaExtraAuriga;
	}

	public void setFirmaExtraAuriga(boolean firmaExtraAuriga) {
		this.firmaExtraAuriga = firmaExtraAuriga;
	}

	public Date getDataVerificaFirma() {
		return dataVerificaFirma;
	}

	public void setDataVerificaFirma(Date dataVerificaFirma) {
		this.dataVerificaFirma = dataVerificaFirma;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Firmatari getControFirma() {
		return controFirma;
	}

	public void setControFirma(Firmatari controFirma) {
		this.controFirma = controFirma;
	}
	
	public String getTipoFirmaQA() {
		return tipoFirmaQA;
	}

	public void setTipoFirmaQA(String tipoFirmaQA) {
		this.tipoFirmaQA = tipoFirmaQA;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getInfoFirma() {
		return infoFirma;
	}

	public void setInfoFirma(String infoFirma) {
		this.infoFirma = infoFirma;
	}
	
	public String getTitolareFirma() {
		return titolareFirma;
	}
	
	public void setTitolareFirma(String titolareFirma) {
		this.titolareFirma = titolareFirma;
	}

	public String getCodiceActivityFirma() {
		return codiceActivityFirma;
	}
	
	public void setCodiceActivityFirma(String codiceActivityFirma) {
		this.codiceActivityFirma = codiceActivityFirma;
	}
	
	public String getIdUtenteLavoroFirma() {
		return idUtenteLavoroFirma;
	}

	public void setIdUtenteLavoroFirma(String idUtenteLavoroFirma) {
		this.idUtenteLavoroFirma = idUtenteLavoroFirma;
	}

	public String getIdUtenteLoggatoFirma() {
		return idUtenteLoggatoFirma;
	}

	public void setIdUtenteLoggatoFirma(String idUtenteLoggatoFirma) {
		this.idUtenteLoggatoFirma = idUtenteLoggatoFirma;
	}	

}