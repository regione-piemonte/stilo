/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.shared.bean.NavigatorBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Navigator {
	
	private List<NavigatorBean> percorso;
	private Boolean flgMostraContenuti;
	private HashMap<String,String> altriParametri;	
	
	public List<NavigatorBean> getPercorso() {
		return percorso;
	}
	public void setPercorso(List<NavigatorBean> percorso) {
		this.percorso = percorso;
	}
	public Boolean getFlgMostraContenuti() {
		return flgMostraContenuti;
	}
	public void setFlgMostraContenuti(Boolean flgMostraContenuti) {
		this.flgMostraContenuti = flgMostraContenuti;
	}
	public HashMap<String,String> getAltriParametri() {
		return altriParametri;
	}
	public void setAltriParametri(HashMap<String,String> altriParametri) {
		this.altriParametri = altriParametri;
	}	
	
}

