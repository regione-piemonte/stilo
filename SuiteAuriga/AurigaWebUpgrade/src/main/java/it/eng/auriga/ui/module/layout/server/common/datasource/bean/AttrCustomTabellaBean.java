/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import it.eng.document.NumeroColonna;

public class AttrCustomTabellaBean {

	private String nomeTabella;

	private LinkedHashMap<String, String> gruppiAttributiCustomTabella;

	public String getNomeTabella() {
		return nomeTabella;
	}

	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	public LinkedHashMap<String, String> getGruppiAttributiCustomTabella() {
		return gruppiAttributiCustomTabella;
	}

	public void setGruppiAttributiCustomTabella(LinkedHashMap<String, String> gruppiAttributiCustomTabella) {
		this.gruppiAttributiCustomTabella = gruppiAttributiCustomTabella;
	}
	
}
