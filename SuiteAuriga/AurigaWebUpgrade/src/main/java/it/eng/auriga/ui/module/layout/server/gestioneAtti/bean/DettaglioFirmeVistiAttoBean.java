/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * 
 * @author dbe4235
 *
 */

public class DettaglioFirmeVistiAttoBean {
	
	private String estremiAtto;
	
	private List<FirmeVistiAttoXmlBean> listaFirmeVisti;

	public String getEstremiAtto() {
		return estremiAtto;
	}

	public void setEstremiAtto(String estremiAtto) {
		this.estremiAtto = estremiAtto;
	}

	public List<FirmeVistiAttoXmlBean> getListaFirmeVisti() {
		return listaFirmeVisti;
	}

	public void setListaFirmeVisti(List<FirmeVistiAttoXmlBean> listaFirmeVisti) {
		this.listaFirmeVisti = listaFirmeVisti;
	}

}