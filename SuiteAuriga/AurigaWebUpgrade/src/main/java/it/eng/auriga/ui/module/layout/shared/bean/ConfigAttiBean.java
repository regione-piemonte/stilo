/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;
import java.util.Set;

/**
 * Espone la configurazione dei modelli da utilizzare durante le istanze di processo relative agli att delle determine
 * @author massimo malvestio
 *
 */
public class ConfigAttiBean {

	private Map<String,String> nomiModelliAtti;
	private Map<Integer,Set<String>> hiddenFieldsAtti;

	public Map<String,String> getNomiModelliAtti() {
		return nomiModelliAtti;
	}

	public void setNomiModelliAtti(Map<String,String> nomiModelliAtti) {
		this.nomiModelliAtti = nomiModelliAtti;
	}

	public Map<Integer,Set<String>> getHiddenFieldsAtti() {
		return hiddenFieldsAtti;
	}

	public void setHiddenFieldsAtti(Map<Integer,Set<String>> hiddenFieldsAtti) {
		this.hiddenFieldsAtti = hiddenFieldsAtti;
	}
	
}
