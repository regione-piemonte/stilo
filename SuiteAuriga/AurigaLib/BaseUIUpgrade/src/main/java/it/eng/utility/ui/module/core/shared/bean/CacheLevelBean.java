/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;

public class CacheLevelBean {

	private AdvancedCriteria criteria;	
	private Boolean flgMostraContenuti;
	private String nroPagina;
	
	public AdvancedCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(AdvancedCriteria criteria) {
		this.criteria = criteria;
	}
	public Boolean getFlgMostraContenuti() {
		return flgMostraContenuti;
	}
	public void setFlgMostraContenuti(Boolean flgMostraContenuti) {
		this.flgMostraContenuti = flgMostraContenuti;
	}
	public String getNroPagina() {
		return nroPagina;
	}
	public void setNroPagina(String nroPagina) {
		this.nroPagina = nroPagina;
	}
	
}
