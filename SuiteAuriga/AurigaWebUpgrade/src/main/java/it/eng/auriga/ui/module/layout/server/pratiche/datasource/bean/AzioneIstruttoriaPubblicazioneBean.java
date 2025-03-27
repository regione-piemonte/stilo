/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean;

import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.document.function.bean.AllegatiAddDocOutBean;

public class AzioneIstruttoriaPubblicazioneBean {
	
	private String idUd;
	private String idDoc;
	private String azione;
	private String errore;
	private String sceltaGiorni;
	private Date dataPubblDal;
	private String numGiorniPubbl;
	private Date dataPubblAl;
	private DocumentBean fileDocumento;
	private List<AllegatoProtocolloBean> listaAllegati;
	private String idUdAvvio;
	private String idDocAvvio;
	private String nomeFileDocAvvio;
	private List<AllegatiAddDocOutBean> listaAllegatiAvvio;
	private String dataFinePubblicazione;
	private String codPraticheConcorrenti;
	
	private String idUdDaCollegare;
	private Boolean flgToReload;
	
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
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}
	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	public String getSceltaGiorni() {
		return sceltaGiorni;
	}
	public void setSceltaGiorni(String sceltaGiorni) {
		this.sceltaGiorni = sceltaGiorni;
	}
	public Date getDataPubblDal() {
		return dataPubblDal;
	}
	public void setDataPubblDal(Date dataPubblDal) {
		this.dataPubblDal = dataPubblDal;
	}
	public String getNumGiorniPubbl() {
		return numGiorniPubbl;
	}
	public void setNumGiorniPubbl(String numGiorniPubbl) {
		this.numGiorniPubbl = numGiorniPubbl;
	}
	public Date getDataPubblAl() {
		return dataPubblAl;
	}
	public void setDataPubblAl(Date dataPubblAl) {
		this.dataPubblAl = dataPubblAl;
	}
	public DocumentBean getFileDocumento() {
		return fileDocumento;
	}
	public void setFileDocumento(DocumentBean fileDocumento) {
		this.fileDocumento = fileDocumento;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public String getIdUdAvvio() {
		return idUdAvvio;
	}
	public void setIdUdAvvio(String idUdAvvio) {
		this.idUdAvvio = idUdAvvio;
	}
	public String getIdDocAvvio() {
		return idDocAvvio;
	}
	public void setIdDocAvvio(String idDocAvvio) {
		this.idDocAvvio = idDocAvvio;
	}
	public String getNomeFileDocAvvio() {
		return nomeFileDocAvvio;
	}
	public void setNomeFileDocAvvio(String nomeFileDocAvvio) {
		this.nomeFileDocAvvio = nomeFileDocAvvio;
	}
	public List<AllegatiAddDocOutBean> getListaAllegatiAvvio() {
		return listaAllegatiAvvio;
	}
	public void setListaAllegatiAvvio(List<AllegatiAddDocOutBean> listaAllegatiAvvio) {
		this.listaAllegatiAvvio = listaAllegatiAvvio;
	}
	public String getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}
	public void setDataFinePubblicazione(String dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}
	public String getCodPraticheConcorrenti() {
		return codPraticheConcorrenti;
	}
	public void setCodPraticheConcorrenti(String codPraticheConcorrenti) {
		this.codPraticheConcorrenti = codPraticheConcorrenti;
	}
	public String getIdUdDaCollegare() {
		return idUdDaCollegare;
	}
	public void setIdUdDaCollegare(String idUdDaCollegare) {
		this.idUdDaCollegare = idUdDaCollegare;
	}
	public Boolean getFlgToReload() {
		return flgToReload;
	}
	public void setFlgToReload(Boolean flgToReload) {
		this.flgToReload = flgToReload;
	}

}
