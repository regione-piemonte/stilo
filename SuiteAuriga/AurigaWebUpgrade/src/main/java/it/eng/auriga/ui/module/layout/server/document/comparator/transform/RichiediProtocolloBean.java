/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

import java.util.List;

public class RichiediProtocolloBean {

	private String oggetto;
	private List<RichiestaProtocolloDestinatarioBean> destinatari;
	private String nomeFilePrimario;
	private String uriFilePrimario;
	private Boolean remoteUriFilePrimario;
	private MimeTypeFirmaBean infoFile;
	private String note;
	private String idPratica;
	private String idAttivita;
	private String idRichiesta;
	private Boolean protocollata;
	private List<RichiestaProtocolloAllegatoBean> listaAllegati;
	private Boolean chiudePratica;
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public List<RichiestaProtocolloDestinatarioBean> getDestinatari() {
		return destinatari;
	}
	public void setDestinatari(List<RichiestaProtocolloDestinatarioBean> destinatari) {
		this.destinatari = destinatari;
	}
	public String getNomeFilePrimario() {
		return nomeFilePrimario;
	}
	public void setNomeFilePrimario(String nomeFilePrimario) {
		this.nomeFilePrimario = nomeFilePrimario;
	}
	public String getUriFilePrimario() {
		return uriFilePrimario;
	}
	public void setUriFilePrimario(String uriFilePrimario) {
		this.uriFilePrimario = uriFilePrimario;
	}
	public Boolean getRemoteUriFilePrimario() {
		return remoteUriFilePrimario;
	}
	public void setRemoteUriFilePrimario(Boolean remoteUriFilePrimario) {
		this.remoteUriFilePrimario = remoteUriFilePrimario;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public List<RichiestaProtocolloAllegatoBean> getListaAllegati() {
		return listaAllegati;
	}
	public void setListaAllegati(List<RichiestaProtocolloAllegatoBean> listaAllegati) {
		this.listaAllegati = listaAllegati;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}
	public String getIdAttivita() {
		return idAttivita;
	}
	public void setIdAttivita(String idAttivita) {
		this.idAttivita = idAttivita;
	}
	public String getIdRichiesta() {
		return idRichiesta;
	}
	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	public Boolean getProtocollata() {
		return protocollata;
	}
	public void setProtocollata(Boolean protocollata) {
		this.protocollata = protocollata;
	}
	public void setChiudePratica(Boolean chiudePratica) {
		this.chiudePratica = chiudePratica;
	}
	public Boolean getChiudePratica() {
		return chiudePratica;
	}
}
