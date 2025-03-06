package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class CertificateBean {
	
	private MessageBean message;
	/*
	 * givenName – nome del firmatario
	 */
	private String nomeFirmatario;
	/*
	 * surName – cognome del firmatario
	 */
	private String cognomeFirmatario;
	/*
	 * fiscalCode – identificativo del firmatario a livello nazionale (codice fiscale nel caso italiano)
	 */
	private String identificativoFirmatario;
	/*
	 * organization – organizzazione di appartenenza specificata nel certificato
	 */
	private String organizzazione;
	/*
	 * orgUnit – unità organizzativa specificata nel certificato
	 */
	private String unitaOrganizzativa;
	/*
	 * certID – identificativo del certificato
	 */
	private String identificativo;
	/*
	 * certType – tipo di certificato
	 */
	private String tipo;
	/*
	 * certSerial – seriale del certificato
	 */
	private String seriale;
	/*
	 * certKeyUsage – tipo di utilizzo del certificato (Firma digitale, non disconoscibilità)
	 */
	private String tipoUtilizzo;
	/*
	 * trustSp – Trust Service Provider
	 */
	private String trustServiceProvider;
	/*
	 * certDateFrom – data di inizio validità del certificato
	 */
	private String tsInizioValidita;
	/*
	 * certDateTo – data di fine validità del certificato
	 */
	private String tsFineValidita;
	/*
	 * validCert – se il certificato è valido, non scaduto, sospeso o revocato
	 */
	private Boolean isValido;
	/*
	 * validTrust – se la CA che ha emesso il certificato è validazione
	 */
	private Boolean isCAValida;
	/*
	 * certErrCode – eventuale codice di errore nella validazione del certificato
	 */
	private String certErrCode;
	/*
	 * trustErrCode – eventuale codice di errore nella validazione della CA
	 */
	private String trustErrCode;
	/*
	 * x509 – certificato x509 in base64
	 */
	private byte[] x509;
	
	public MessageBean getMessage() {
		return message;
	}
	public void setMessage(MessageBean message) {
		this.message = message;
	}
	public String getNomeFirmatario() {
		return nomeFirmatario;
	}
	public void setNomeFirmatario(String nomeFirmatario) {
		this.nomeFirmatario = nomeFirmatario;
	}
	public String getCognomeFirmatario() {
		return cognomeFirmatario;
	}
	public void setCognomeFirmatario(String cognomeFirmatario) {
		this.cognomeFirmatario = cognomeFirmatario;
	}
	public String getIdentificativoFirmatario() {
		return identificativoFirmatario;
	}
	public void setIdentificativoFirmatario(String identificativoFirmatario) {
		this.identificativoFirmatario = identificativoFirmatario;
	}
	public String getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public String getUnitaOrganizzativa() {
		return unitaOrganizzativa;
	}
	public void setUnitaOrganizzativa(String unitaOrganizzativa) {
		this.unitaOrganizzativa = unitaOrganizzativa;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSeriale() {
		return seriale;
	}
	public void setSeriale(String seriale) {
		this.seriale = seriale;
	}
	public String getTipoUtilizzo() {
		return tipoUtilizzo;
	}
	public void setTipoUtilizzo(String tipoUtilizzo) {
		this.tipoUtilizzo = tipoUtilizzo;
	}
	public String getTrustServiceProvider() {
		return trustServiceProvider;
	}
	public void setTrustServiceProvider(String trustServiceProvider) {
		this.trustServiceProvider = trustServiceProvider;
	}
	public String getTsInizioValidita() {
		return tsInizioValidita;
	}
	public void setTsInizioValidita(String tsInizioValidita) {
		this.tsInizioValidita = tsInizioValidita;
	}
	public String getTsFineValidita() {
		return tsFineValidita;
	}
	public void setTsFineValidita(String tsFineValidita) {
		this.tsFineValidita = tsFineValidita;
	}
	public Boolean getIsValido() {
		return isValido;
	}
	public void setIsValido(Boolean isValido) {
		this.isValido = isValido;
	}
	public Boolean getIsCAValida() {
		return isCAValida;
	}
	public void setIsCAValida(Boolean isCAValida) {
		this.isCAValida = isCAValida;
	}
	public String getCertErrCode() {
		return certErrCode;
	}
	public void setCertErrCode(String certErrCode) {
		this.certErrCode = certErrCode;
	}
	public String getTrustErrCode() {
		return trustErrCode;
	}
	public void setTrustErrCode(String trustErrCode) {
		this.trustErrCode = trustErrCode;
	}
	public byte[] getX509() {
		return x509;
	}
	public void setX509(byte[] x509) {
		this.x509 = x509;
	}
		
}