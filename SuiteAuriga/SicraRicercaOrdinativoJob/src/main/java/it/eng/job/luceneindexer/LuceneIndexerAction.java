/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

/**
 * racchiude tutte le informazioni utili per indicizzare
 * il nuovo file
 * @author jravagnan
 *
 */
public class LuceneIndexerAction {

	private String ciObj;

	private String ts;

	private String dominio;

	private Map<String, Object> attributiDaIndicizzare;
	
	private Map<String, Object> attributiNonIndicizzabili;

	private String note;

	private String categoria;

	public LuceneIndexerAction(String ciObj, String ts, String dominio, Map<String, Object> propsInd, Map<String, Object> propsNonInd, String note,
			String categoria) {
		setCiObj(ciObj);
		setTs(ts);
		setDominio(dominio);
		setAttributiDaIndicizzare(propsInd);
		setAttributiNonIndicizzabili(propsNonInd);
		setNote(note);
		setCategoria(categoria);
	}

	public String getCiObj() {
		return ciObj;
	}

	public void setCiObj(String ciObj) {
		this.ciObj = ciObj;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Map<String, Object> getAttributiDaIndicizzare() {
		return attributiDaIndicizzare;
	}

	public void setAttributiDaIndicizzare(Map<String, Object> attributiDaIndicizzare) {
		this.attributiDaIndicizzare = attributiDaIndicizzare;
	}

	public Map<String, Object> getAttributiNonIndicizzabili() {
		return attributiNonIndicizzabili;
	}

	public void setAttributiNonIndicizzabili(Map<String, Object> attributiNonIndicizzabili) {
		this.attributiNonIndicizzabili = attributiNonIndicizzabili;
	}
}
