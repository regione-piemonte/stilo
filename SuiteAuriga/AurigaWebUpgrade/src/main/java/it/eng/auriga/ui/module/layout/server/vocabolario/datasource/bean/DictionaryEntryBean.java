/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class DictionaryEntryBean extends VisualBean {

	@NumeroColonna(numero = "1")
	private String key;
	@NumeroColonna(numero = "2")
	private String value;	
	@NumeroColonna(numero = "3")
	private String dictionaryEntryVincolo;
	@NumeroColonna(numero = "4")
	private String flgCodObbligatorio;
	@NumeroColonna(numero = "5")
	private String flgValoriCkeditor;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDictionaryEntryVincolo() {
		return dictionaryEntryVincolo;
	}
	public void setDictionaryEntryVincolo(String dictionaryEntryVincolo) {
		this.dictionaryEntryVincolo = dictionaryEntryVincolo;
	}
	public String getFlgCodObbligatorio() {
		return flgCodObbligatorio;
	}
	public void setFlgCodObbligatorio(String flgCodObbligatorio) {
		this.flgCodObbligatorio = flgCodObbligatorio;
	}
	public String getFlgValoriCkeditor() {
		return flgValoriCkeditor;
	}
	public void setFlgValoriCkeditor(String flgValoriCkeditor) {
		this.flgValoriCkeditor = flgValoriCkeditor;
	}	
	
}