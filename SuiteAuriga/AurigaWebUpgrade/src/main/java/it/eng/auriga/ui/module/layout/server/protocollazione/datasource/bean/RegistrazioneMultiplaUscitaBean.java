/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class RegistrazioneMultiplaUscitaBean extends ProtocollazioneBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tipoRegistrazioneMultipla;
	
	private String nomeFileXlsDestinatariDiversiXReg;
	private String uriFileXlsDestinatariDiversiXReg;
	private String idFoglioXlsDestinatariDiversiXReg;
	private List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestinatariDiversiXReg;
	
	private String casellaMittente;
	
	private String flgFilePrincipaleUgualeXTutteReg;
	private Boolean flgApponiTimbroFilePrimariNonFirmati;
	
	private Boolean flgApponiTimbroFileAllegatiNonFirmati;
	
	public String getTipoRegistrazioneMultipla() {
		return tipoRegistrazioneMultipla;
	}
	public void setTipoRegistrazioneMultipla(String tipoRegistrazioneMultipla) {
		this.tipoRegistrazioneMultipla = tipoRegistrazioneMultipla;
	}
	public String getNomeFileXlsDestinatariDiversiXReg() {
		return nomeFileXlsDestinatariDiversiXReg;
	}
	public void setNomeFileXlsDestinatariDiversiXReg(String nomeFileXlsDestinatariDiversiXReg) {
		this.nomeFileXlsDestinatariDiversiXReg = nomeFileXlsDestinatariDiversiXReg;
	}
	public String getUriFileXlsDestinatariDiversiXReg() {
		return uriFileXlsDestinatariDiversiXReg;
	}
	public void setUriFileXlsDestinatariDiversiXReg(String uriFileXlsDestinatariDiversiXReg) {
		this.uriFileXlsDestinatariDiversiXReg = uriFileXlsDestinatariDiversiXReg;
	}
	public String getIdFoglioXlsDestinatariDiversiXReg() {
		return idFoglioXlsDestinatariDiversiXReg;
	}
	public void setIdFoglioXlsDestinatariDiversiXReg(String idFoglioXlsDestinatariDiversiXReg) {
		this.idFoglioXlsDestinatariDiversiXReg = idFoglioXlsDestinatariDiversiXReg;
	}
	public List<DestinatariRegistrazioneMultiplaUscitaXmlBean> getListaDestinatariDiversiXReg() {
		return listaDestinatariDiversiXReg;
	}
	public void setListaDestinatariDiversiXReg(
			List<DestinatariRegistrazioneMultiplaUscitaXmlBean> listaDestinatariDiversiXReg) {
		this.listaDestinatariDiversiXReg = listaDestinatariDiversiXReg;
	}
	public String getCasellaMittente() {
		return casellaMittente;
	}
	public void setCasellaMittente(String casellaMittente) {
		this.casellaMittente = casellaMittente;
	}
	public String getFlgFilePrincipaleUgualeXTutteReg() {
		return flgFilePrincipaleUgualeXTutteReg;
	}
	public void setFlgFilePrincipaleUgualeXTutteReg(String flgFilePrincipaleUgualeXTutteReg) {
		this.flgFilePrincipaleUgualeXTutteReg = flgFilePrincipaleUgualeXTutteReg;
	}
	public Boolean getFlgApponiTimbroFilePrimariNonFirmati() {
		return flgApponiTimbroFilePrimariNonFirmati;
	}
	public void setFlgApponiTimbroFilePrimariNonFirmati(Boolean flgApponiTimbroFilePrimariNonFirmati) {
		this.flgApponiTimbroFilePrimariNonFirmati = flgApponiTimbroFilePrimariNonFirmati;
	}
	public Boolean getFlgApponiTimbroFileAllegatiNonFirmati() {
		return flgApponiTimbroFileAllegatiNonFirmati;
	}
	public void setFlgApponiTimbroFileAllegatiNonFirmati(Boolean flgApponiTimbroFileAllegatiNonFirmati) {
		this.flgApponiTimbroFileAllegatiNonFirmati = flgApponiTimbroFileAllegatiNonFirmati;
	}
	
}
