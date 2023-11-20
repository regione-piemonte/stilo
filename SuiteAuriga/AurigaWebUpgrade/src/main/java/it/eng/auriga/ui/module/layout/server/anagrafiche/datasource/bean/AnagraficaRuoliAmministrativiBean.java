/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class AnagraficaRuoliAmministrativiBean  extends AnagraficaRuoliAmministrativiXmlBean{

	private String ciProvRuolo;
	private List<String> listaRuoliInclusi;
	
	public String getCiProvRuolo() {
		return ciProvRuolo;
	}

	public void setCiProvRuolo(String ciProvRuolo) {
		this.ciProvRuolo = ciProvRuolo;
	}

	public List<String> getListaRuoliInclusi() {
		return listaRuoliInclusi;
	}

	public void setListaRuoliInclusi(List<String> listaRuoliInclusi) {
		this.listaRuoliInclusi = listaRuoliInclusi;
	} 
}