/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Timestamp;

/**
 * rappresentazione ad oggetti di un record da indicizzare
 * @author jravagnan
 *
 */
public class DataToIndex {
	
	private String ciObj;

	private String attrName;

	private String categoria;

	private String attrValue;

	private Timestamp tsIns;

	private String dominio;

	private String note;

	public String getCiObj() {
		return ciObj;
	}

	public void setCiObj(String ciObj) {
		this.ciObj = ciObj;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public Timestamp getTsIns() {
		return tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
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
	
}
