/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class AttachmentInvioNotEmailBean {
	
	@NumeroColonna(numero = "1")
	private String uri;
	
	@NumeroColonna(numero = "2")
	private String nomeFile;

	@NumeroColonna(numero = "3")
	private String flgTimbrato;
	
	@NumeroColonna(numero = "4")
	private String mimetype;

	@NumeroColonna(numero = "5")
	private String flgConvertibile;

	@NumeroColonna(numero = "6")
	private String flgFirmato;

	@NumeroColonna(numero = "7")
	private String tipoFirma; //  (valori CAdES o PAdES o XAdES)

	@NumeroColonna(numero = "8")
	private String nroAllegato; 
	
	@NumeroColonna(numero = "9")
	private String flgFirmaValida;

	private MimeTypeFirmaBean infoFile;

	public String getFlgFirmaValida() {
		return flgFirmaValida;
	}

	public void setFlgFirmaValida(String flgFirmaValida) {
		this.flgFirmaValida = flgFirmaValida;
	}

	public String getNroAllegato() {
		return nroAllegato;
	}

	public void setNroAllegato(String nroAllegato) {
		this.nroAllegato = nroAllegato;
	}

	public String getMimetype() {
		return mimetype;
	}

	public String getFlgConvertibile() {
		return flgConvertibile;
	}

	public String getFlgFirmato() {
		return flgFirmato;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public void setFlgConvertibile(String flgConvertibile) {
		this.flgConvertibile = flgConvertibile;
	}

	public void setFlgFirmato(String flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getFlgTimbrato() {
		return flgTimbrato;
	}

	public void setFlgTimbrato(String flgTimbrato) {
		this.flgTimbrato = flgTimbrato;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	
}