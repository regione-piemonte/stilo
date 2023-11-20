/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import java.util.Date;

public class VersioneDocBean {
	
	@NumeroColonna(numero = "1")
	private String nroVersione;
	@NumeroColonna(numero = "2")
	private String nomeFile;
	@NumeroColonna(numero = "3")
	private String uriFile;
	@NumeroColonna(numero = "4")
	private String impronta;
	@NumeroColonna(numero = "7")
	private String flgValida;
	@NumeroColonna(numero = "19")
	@TipoData(tipo = Tipo.DATA)
	private Date tsCreazione;
	@NumeroColonna(numero = "25")
	private String dimensione;
	@NumeroColonna(numero = "26")
	private String mimetype;
	@NumeroColonna(numero = "27")
	private String flgFirmato;	
	
	private MimeTypeFirmaBean infoFile;
	private Boolean remoteUri = true;
	
	public String getNroVersione() {
		return nroVersione;
	}
	public void setNroVersione(String nroVersione) {
		this.nroVersione = nroVersione;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getImpronta() {
		return impronta;
	}
	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	public String getFlgValida() {
		return flgValida;
	}
	public void setFlgValida(String flgValida) {
		this.flgValida = flgValida;
	}
	public Date getTsCreazione() {
		return tsCreazione;
	}
	public void setTsCreazione(Date tsCreazione) {
		this.tsCreazione = tsCreazione;
	}
	public String getDimensione() {
		return dimensione;
	}
	public void setDimensione(String dimensione) {
		this.dimensione = dimensione;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getFlgFirmato() {
		return flgFirmato;
	}
	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	public Boolean getRemoteUri() {
		return remoteUri;
	}
	public void setRemoteUri(Boolean remoteUri) {
		this.remoteUri = remoteUri;
	}	
	
}
