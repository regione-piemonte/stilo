/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class FileVerbaleSedutaBean {

	private String idDoc;
	private String uri;
	private boolean flgDaFirmare;
	private String displayFilename;

	private String nomeModello;
	private String idModello;
	private String uriModello;
	private String tipoModello;

	private MimeTypeFirmaBean infoFileVerbale;

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isFlgDaFirmare() {
		return flgDaFirmare;
	}

	public void setFlgDaFirmare(boolean flgDaFirmare) {
		this.flgDaFirmare = flgDaFirmare;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getNomeModello() {
		return nomeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}

	public String getIdModello() {
		return idModello;
	}

	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}

	public String getUriModello() {
		return uriModello;
	}

	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public MimeTypeFirmaBean getInfoFileVerbale() {
		return infoFileVerbale;
	}

	public void setInfoFileVerbale(MimeTypeFirmaBean infoFileVerbale) {
		this.infoFileVerbale = infoFileVerbale;
	}

}