package it.eng.hsm.client.bean.sign;

import it.eng.hsm.client.bean.MessageBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class SignatureBean {
	
	private MessageBean message;
	private CertificateBean certificato;
	private MarcaBean marca;
	private int nrLivello;
	private String tipoFirma;
	/*
	 * signType - tipo di firma (PADEX, CADES, XADES)
	 */
	private String tipo; 
	/*
	 * digestAlg - tipo di algoritmo usato per il calcolo del digest
	 */
	private String algoritmoDigest;
	/*
	 * signTime - timestamp della firma (corrispondente alla marca temporale se presente)
	 */
	private String timestamp;
	/*
	 * signatureField – informazioni sul campo firma (solo per firme pades)
	 */
	private InformazioniFirma informazioni;
	/*
	 * valid – boolean indicante se la firma risulta valida in tutti i suoi aspetti
	 */
	private Boolean isValidaCompleta;
	/*
	 * validSign – boolean indicante se la firma è valida ossia il documento non è stato modificato e la firma è corretta
	 */
	private Boolean isValida;
	/*
	 * signErrCode – eventuale codice di errore nella validazione della firma
	 */
	private String signErrCode;
	/*
	 * p7mLevel – livello della controfirma se p7m
	 */
	private Integer p7mLevel;
	
	public MessageBean getMessage() {
		return message;
	}
	public void setMessage(MessageBean message) {
		this.message = message;
	}
	public CertificateBean getCertificato() {
		return certificato;
	}
	public void setCertificato(CertificateBean certificato) {
		this.certificato = certificato;
	}
	public MarcaBean getMarca() {
		return marca;
	}
	public void setMarca(MarcaBean marca) {
		this.marca = marca;
	}
	public int getNrLivello() {
		return nrLivello;
	}
	public void setNrLivello(int nrLivello) {
		this.nrLivello = nrLivello;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAlgoritmoDigest() {
		return algoritmoDigest;
	}
	public void setAlgoritmoDigest(String algoritmoDigest) {
		this.algoritmoDigest = algoritmoDigest;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public InformazioniFirma getInformazioni() {
		return informazioni;
	}
	public void setInformazioni(InformazioniFirma informazioni) {
		this.informazioni = informazioni;
	}
	public Boolean getIsValidaCompleta() {
		return isValidaCompleta;
	}
	public void setIsValidaCompleta(Boolean isValidaCompleta) {
		this.isValidaCompleta = isValidaCompleta;
	}
	public Boolean getIsValida() {
		return isValida;
	}
	public void setIsValida(Boolean isValida) {
		this.isValida = isValida;
	}
	public String getSignErrCode() {
		return signErrCode;
	}
	public void setSignErrCode(String signErrCode) {
		this.signErrCode = signErrCode;
	}
	public Integer getP7mLevel() {
		return p7mLevel;
	}
	public void setP7mLevel(Integer p7mLevel) {
		this.p7mLevel = p7mLevel;
	}
	public String getTipoFirma() {
		return tipoFirma;
	}
	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}
	
			
}