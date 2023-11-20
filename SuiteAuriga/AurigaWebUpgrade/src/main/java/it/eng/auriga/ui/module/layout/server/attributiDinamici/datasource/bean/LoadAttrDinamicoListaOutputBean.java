/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

public class LoadAttrDinamicoListaOutputBean {
	
	private String titoloLista;
	private List<DettColonnaAttributoListaBean> datiDettLista;
	private List<HashMap<String, String>> valoriLista;
	
	
	public String getTitoloLista() {
		return titoloLista;
	}
	public void setTitoloLista(String titoloLista) {
		this.titoloLista = titoloLista;
	}
	public List<DettColonnaAttributoListaBean> getDatiDettLista() {
		return datiDettLista;
	}
	public void setDatiDettLista(List<DettColonnaAttributoListaBean> datiDettLista) {
		this.datiDettLista = datiDettLista;
	}
	public List<HashMap<String, String>> getValoriLista() {
		return valoriLista;
	}
	public void setValoriLista(List<HashMap<String, String>> valoriLista) {
		this.valoriLista = valoriLista;
	}
		
	
}
