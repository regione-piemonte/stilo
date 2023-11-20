/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.iterAtti.datasource.bean.AttoConFlussoWFXmlBean;

public class NuovoAttoComeCopiaBean extends AttoConFlussoWFXmlBean{

	private String tipoNumerazioneCopia;
	private String siglaCopia;
	private String numeroCopia;
	private String annoCopia;
	private Boolean flgCopiaAllegatiPareri;
	
	public String getTipoNumerazioneCopia() {
		return tipoNumerazioneCopia;
	}
	public void setTipoNumerazioneCopia(String tipoNumerazioneCopia) {
		this.tipoNumerazioneCopia = tipoNumerazioneCopia;
	}
	public String getSiglaCopia() {
		return siglaCopia;
	}
	public void setSiglaCopia(String siglaCopia) {
		this.siglaCopia = siglaCopia;
	}
	public String getNumeroCopia() {
		return numeroCopia;
	}
	public void setNumeroCopia(String numeroCopia) {
		this.numeroCopia = numeroCopia;
	}
	public String getAnnoCopia() {
		return annoCopia;
	}
	public void setAnnoCopia(String annoCopia) {
		this.annoCopia = annoCopia;
	}
	public Boolean getFlgCopiaAllegatiPareri() {
		return flgCopiaAllegatiPareri;
	}
	public void setFlgCopiaAllegatiPareri(Boolean flgCopiaAllegatiPareri) {
		this.flgCopiaAllegatiPareri = flgCopiaAllegatiPareri;
	}
	
}
