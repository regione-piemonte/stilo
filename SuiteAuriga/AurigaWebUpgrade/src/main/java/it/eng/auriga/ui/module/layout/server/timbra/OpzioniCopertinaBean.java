/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class OpzioniCopertinaBean {


	private String posizioneIntestazione;
	private String posizioneTestoInChiaro;
	private String rotazioneTimbro;
	private String posizioneTimbro;
	private String rotazioneTimbroPref;
	private String posizioneTimbroPref;
	private String idUd;
	private String idDoc;
	private String numeroAllegato;
	private String numeroAllegati;
	private String codifica;
	private String testoIntestazione; // Testo in chiaro
	private String testo;// Barcode
	/**
	 *  Copertina con segnatura (barcode)  GetEtichettaReg 
	 *  Copertina con timbro  GetTimbroSpecXTipo 
	 */
	private String tipoTimbroCopertina;
	private String skipScelteOpzioniCopertina;
	private MimeTypeFirmaBean infoFile;
	
	private Boolean isMultiplo;
	private String  provenienza;
	private String  posizionale;
	
	// PRATICA PREGRESSA
	private String barcodePraticaPregressa;
	private String idFolder;
	private String sezionePratica;
	
	public String getPosizioneIntestazione() {
		return posizioneIntestazione;
	}
	public void setPosizioneIntestazione(String posizioneIntestazione) {
		this.posizioneIntestazione = posizioneIntestazione;
	}
	public String getRotazioneTimbro() {
		return rotazioneTimbro;
	}
	public void setRotazioneTimbro(String rotazioneTimbro) {
		this.rotazioneTimbro = rotazioneTimbro;
	}
	public String getPosizioneTimbro() {
		return posizioneTimbro;
	}
	public void setPosizioneTimbro(String posizioneTimbro) {
		this.posizioneTimbro = posizioneTimbro;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}
	public String getNumeroAllegato() {
		return numeroAllegato;
	}
	public void setNumeroAllegato(String numeroAllegato) {
		this.numeroAllegato = numeroAllegato;
	}
	public String getTipoTimbroCopertina() {
		return tipoTimbroCopertina;
	}
	public void setTipoTimbroCopertina(String tipoTimbroCopertina) {
		this.tipoTimbroCopertina = tipoTimbroCopertina;
	}
	public String getCodifica() {
		return codifica;
	}
	public void setCodifica(String codifica) {
		this.codifica = codifica;
	}
	public String getPosizioneTestoInChiaro() {
		return posizioneTestoInChiaro;
	}
	public void setPosizioneTestoInChiaro(String posizioneTestoInChiaro) {
		this.posizioneTestoInChiaro = posizioneTestoInChiaro;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getTestoIntestazione() {
		return testoIntestazione;
	}
	public void setTestoIntestazione(String testoIntestazione) {
		this.testoIntestazione = testoIntestazione;
	}
	public String getRotazioneTimbroPref() {
		return rotazioneTimbroPref;
	}
	public void setRotazioneTimbroPref(String rotazioneTimbroPref) {
		this.rotazioneTimbroPref = rotazioneTimbroPref;
	}
	public String getPosizioneTimbroPref() {
		return posizioneTimbroPref;
	}
	public void setPosizioneTimbroPref(String posizioneTimbroPref) {
		this.posizioneTimbroPref = posizioneTimbroPref;
	}
	public String getSkipScelteOpzioniCopertina() {
		return skipScelteOpzioniCopertina;
	}
	public void setSkipScelteOpzioniCopertina(String skipScelteOpzioniCopertina) {
		this.skipScelteOpzioniCopertina = skipScelteOpzioniCopertina;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public String getNumeroAllegati() {
		return numeroAllegati;
	}
	public void setNumeroAllegati(String numeroAllegati) {
		this.numeroAllegati = numeroAllegati;
	}
	public Boolean getIsMultiplo() {
		return isMultiplo;
	}
	public void setIsMultiplo(Boolean isMultiplo) {
		this.isMultiplo = isMultiplo;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public String getPosizionale() {
		return posizionale;
	}
	public void setPosizionale(String posizionale) {
		this.posizionale = posizionale;
	}
	public String getBarcodePraticaPregressa() {
		return barcodePraticaPregressa;
	}
	public void setBarcodePraticaPregressa(String barcodePraticaPregressa) {
		this.barcodePraticaPregressa = barcodePraticaPregressa;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getSezionePratica() {
		return sezionePratica;
	}
	public void setSezionePratica(String sezionePratica) {
		this.sezionePratica = sezionePratica;
	}
}
