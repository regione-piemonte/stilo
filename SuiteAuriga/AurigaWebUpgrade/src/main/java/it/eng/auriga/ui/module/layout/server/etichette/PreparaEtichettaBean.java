/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;

public class PreparaEtichettaBean {

	private String idUd;
	private String idDoc;
	private List<AllegatoProtocolloBean> listaAllegati;
	
	private List<EtichettaBean> etichette;
	
	private String nomeStampante;
	private String portaStampanteTimbraturaCartaceo;
	private String nrAllegato;
	private String nroEtichette;
	
	private Boolean flgRicevutaXMittente;
	private Boolean flgPrimario;
	private Boolean flgAllegati;
	private Boolean flgSegnRegPrincipale;
	private Boolean flgSegnRegSecondaria;
	private String  notazioneCopiaOriginale;
	private Boolean flgHideBarcode;
	private Boolean isEtichettaFromRepe;
	private Boolean isEtichettaFromModAss;
	
	// PRATICA PREGRESSA
	private String barcodePraticaPregressa;
	private String idFolder;
	private String sezionePratica;

	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public List<AllegatoProtocolloBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<AllegatoProtocolloBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public String getNomeStampante() {
		return nomeStampante;
	}
	public void setNomeStampante(String nomeStampante) {
		this.nomeStampante = nomeStampante;
	}	
	public String getPortaStampanteTimbraturaCartaceo() {
		return portaStampanteTimbraturaCartaceo;
	}
	public void setPortaStampanteTimbraturaCartaceo(String portaStampanteTimbraturaCartaceo) {
		this.portaStampanteTimbraturaCartaceo = portaStampanteTimbraturaCartaceo;
	}
	public List<EtichettaBean> getEtichette() {
		return etichette;
	}
	public void setEtichette(List<EtichettaBean> etichette) {
		this.etichette = etichette;
	}
	public String getNrAllegato() {
		return nrAllegato;
	}
	public void setNrAllegato(String nrAllegato) {
		this.nrAllegato = nrAllegato;
	}
	public String getNroEtichette() {
		return nroEtichette;
	}
	public void setNroEtichette(String nroEtichette) {
		this.nroEtichette = nroEtichette;
	}
	public Boolean getFlgRicevutaXMittente() {
		return flgRicevutaXMittente;
	}
	public void setFlgRicevutaXMittente(Boolean flgRicevutaXMittente) {
		this.flgRicevutaXMittente = flgRicevutaXMittente;
	}
	public Boolean getFlgPrimario() {
		return flgPrimario;
	}
	public void setFlgPrimario(Boolean flgPrimario) {
		this.flgPrimario = flgPrimario;
	}
	public Boolean getFlgAllegati() {
		return flgAllegati;
	}
	public void setFlgAllegati(Boolean flgAllegati) {
		this.flgAllegati = flgAllegati;
	}
	public String getNotazioneCopiaOriginale() {
		return notazioneCopiaOriginale;
	}
	public void setNotazioneCopiaOriginale(String notazioneCopiaOriginale) {
		this.notazioneCopiaOriginale = notazioneCopiaOriginale;
	}
	public String getIdDoc() {
		return idDoc;
	}
	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
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
	public Boolean getFlgHideBarcode() {
		return flgHideBarcode;
	}
	public void setFlgHideBarcode(Boolean flgHideBarcode) {
		this.flgHideBarcode = flgHideBarcode;
	}
	public Boolean getFlgSegnRegPrincipale() {
		return flgSegnRegPrincipale;
	}
	public void setFlgSegnRegPrincipale(Boolean flgSegnRegPrincipale) {
		this.flgSegnRegPrincipale = flgSegnRegPrincipale;
	}
	public Boolean getFlgSegnRegSecondaria() {
		return flgSegnRegSecondaria;
	}
	public void setFlgSegnRegSecondaria(Boolean flgSegnRegSecondaria) {
		this.flgSegnRegSecondaria = flgSegnRegSecondaria;
	}
	public Boolean getIsEtichettaFromRepe() {
		return isEtichettaFromRepe;
	}
	public void setIsEtichettaFromRepe(Boolean isEtichettaFromRepe) {
		this.isEtichettaFromRepe = isEtichettaFromRepe;
	}
	public Boolean getIsEtichettaFromModAss() {
		return isEtichettaFromModAss;
	}
	public void setIsEtichettaFromModAss(Boolean isEtichettaFromModAss) {
		this.isEtichettaFromModAss = isEtichettaFromModAss;
	}
}