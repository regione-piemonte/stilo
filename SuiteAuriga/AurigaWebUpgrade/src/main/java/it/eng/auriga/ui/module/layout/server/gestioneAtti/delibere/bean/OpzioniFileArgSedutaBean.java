/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author dbe4235
 *
 */

public class OpzioniFileArgSedutaBean {
	
	private String flgInclusiPareri;
	private String flgInclAllegatiPI;	
	private String flgIntXPubbl;
	private List<ArgomentiOdgXmlBean> listaArgomentiOdgXmlBean;
	
	private String uriUnioneFile;

	public String getFlgInclusiPareri() {
		return flgInclusiPareri;
	}

	public String getFlgInclAllegatiPI() {
		return flgInclAllegatiPI;
	}

	public String getFlgIntXPubbl() {
		return flgIntXPubbl;
	}

	public List<ArgomentiOdgXmlBean> getListaArgomentiOdgXmlBean() {
		return listaArgomentiOdgXmlBean;
	}

	public String getUriUnioneFile() {
		return uriUnioneFile;
	}

	public void setFlgInclusiPareri(String flgInclusiPareri) {
		this.flgInclusiPareri = flgInclusiPareri;
	}

	public void setFlgInclAllegatiPI(String flgInclAllegatiPI) {
		this.flgInclAllegatiPI = flgInclAllegatiPI;
	}

	public void setFlgIntXPubbl(String flgIntXPubbl) {
		this.flgIntXPubbl = flgIntXPubbl;
	}

	public void setListaArgomentiOdgXmlBean(List<ArgomentiOdgXmlBean> listaArgomentiOdgXmlBean) {
		this.listaArgomentiOdgXmlBean = listaArgomentiOdgXmlBean;
	}

	public void setUriUnioneFile(String uriUnioneFile) {
		this.uriUnioneFile = uriUnioneFile;
	}

}