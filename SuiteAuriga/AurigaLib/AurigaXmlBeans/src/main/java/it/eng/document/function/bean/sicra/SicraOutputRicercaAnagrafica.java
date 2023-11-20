/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraOutputRicercaAnagrafica implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private EsitoChiamata esitoChiamata;
	private List<SicraAnagrafica> listaAnagrafiche;
	
	public EsitoChiamata getEsitoChiamata() {
		return esitoChiamata;
	}
	
	public void setEsitoChiamata(EsitoChiamata esitoChiamata) {
		this.esitoChiamata = esitoChiamata;
	}
	
	public List<SicraAnagrafica> getListaAnagrafiche() {
		return listaAnagrafiche;
	}
	
	public void setListaAnagrafiche(List<SicraAnagrafica> listaAnagrafiche) {
		this.listaAnagrafiche = listaAnagrafiche;
	}
	
}
