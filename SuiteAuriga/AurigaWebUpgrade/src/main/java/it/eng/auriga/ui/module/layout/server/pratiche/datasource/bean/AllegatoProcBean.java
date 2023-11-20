/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AllegatoProcBean {
	
//	1) Livello: 1 = cartella, 2 = file
	@NumeroColonna(numero = "1")
	private String livello;
//	2) Id. tipo allegato
	@NumeroColonna(numero = "2")
	private String idTipoAllegato;
//	3) Nome tipo allegato (nome della cartella)
	@NumeroColonna(numero = "3")
	private String nomeTipoAllegato;
//	4) Flag 1/0/-1: 1 indica che ci sono file del tipo, 0 che non ce ne sono e non è obbligatorio, -1 che non ci sono file ed è obbligatorio metterli
	@NumeroColonna(numero = "4")
	private String flgContenuto;
//	5) N.ro file del dato tipo
	@NumeroColonna(numero = "5")
	private String nroFile;
//	6) Nome del file
	@NumeroColonna(numero = "6")
	private String displayName;
//	7) URI file
	@NumeroColonna(numero = "7")
	private String uri;
//	8) Flag 1/0 file firmato
	@NumeroColonna(numero = "8")
	private String flgFirmato;
//	9) Mimetype del file
	@NumeroColonna(numero = "9")
	private String mimetype;
//	10) Flag 1/0 file pdf o convertibile in pdf
	@NumeroColonna(numero = "10")
	private String flgConvertibile;
//	11) ID_DOC del documento allegato
	@NumeroColonna(numero = "11")
	private String idDoc;
//	12)	ID_UD documento allegato
	@NumeroColonna(numero = "12")
	private String idUd;
//	13)	Dimensione dell'allegato (in bytes)
	@NumeroColonna(numero = "13")
	private String dimensione;
//	14) Impronta del file
	@NumeroColonna(numero = "14")
	private String impronta;
//	15)	Algoritmo calcolo impronta
	@NumeroColonna(numero = "15")
	private String algoritmoImpronta;
//	16)	Encoding impronta (hex o base64)
	@NumeroColonna(numero = "16")
	private String encoding;
//	17)	Data e ora di inserimento del file
	@NumeroColonna(numero = "17")
	private String tsIns;
//	18)	Massimo n.ro di documenti del dato tipo consentiti. Se non valorizzato significa "unlimited"
	@NumeroColonna(numero = "18")
	private String maxNroDoc;
//	19)	Quadro a cui si riferiscono gli allegati del dato tipo
	@NumeroColonna(numero = "19")
	private String quadro;
//	20)	Descrizione estesa tipo allegato
	@NumeroColonna(numero = "20")
	private String descrizioneTipoAllegato;
	@NumeroColonna(numero = "21")
	private String prescrizioni;
	
	private Boolean remoteUri;
	private MimeTypeFirmaBean infoFile; 
	private Boolean isChanged;	
	
	public String getLivello() {
		return livello;
	}
	public void setLivello(String livello) {
		this.livello = livello;
	}
	public String getIdTipoAllegato() {
		return idTipoAllegato;
	}
	public void setIdTipoAllegato(String idTipoAllegato) {
		this.idTipoAllegato = idTipoAllegato;
	}
	public String getNomeTipoAllegato() {
		return nomeTipoAllegato;
	}
	public void setNomeTipoAllegato(String nomeTipoAllegato) {
		this.nomeTipoAllegato = nomeTipoAllegato;
	}
	public String getFlgContenuto() {
		return flgContenuto;
	}
	public void setFlgContenuto(String flgContenuto) {
		this.flgContenuto = flgContenuto;
	}
	public String getNroFile() {
		return nroFile;
	}
	public void setNroFile(String nroFile) {
		this.nroFile = nroFile;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getFlgFirmato() {
		return flgFirmato;
	}
	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getFlgConvertibile() {
		return flgConvertibile;
	}
	public void setFlgConvertibile(String flgConvertibile) {
		this.flgConvertibile = flgConvertibile;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getDimensione() {
		return dimensione;
	}
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getAlgoritmoImpronta() {
		return algoritmoImpronta;
	}
	public void setAlgoritmoImpronta(String algoritmoImpronta) {
		this.algoritmoImpronta = algoritmoImpronta;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getTsIns() {
		return tsIns;
	}
	public void setTsIns(String tsIns) {
		this.tsIns = tsIns;
	}
	public String getMaxNroDoc() {
		return maxNroDoc;
	}
	public void setMaxNroDoc(String maxNroDoc) {
		this.maxNroDoc = maxNroDoc;
	}
	public String getQuadro() {
		return quadro;
	}
	public void setQuadro(String quadro) {
		this.quadro = quadro;
	}
	public String getDescrizioneTipoAllegato() {
		return descrizioneTipoAllegato;
	}
	public void setDescrizioneTipoAllegato(String descrizioneTipoAllegato) {
		this.descrizioneTipoAllegato = descrizioneTipoAllegato;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public Boolean getIsChanged() {
		return isChanged;
	}
	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}
	public String getPrescrizioni() {
		return prescrizioni;
	}
	public void setPrescrizioni(String prescrizioni) {
		this.prescrizioni = prescrizioni;
	}

}
