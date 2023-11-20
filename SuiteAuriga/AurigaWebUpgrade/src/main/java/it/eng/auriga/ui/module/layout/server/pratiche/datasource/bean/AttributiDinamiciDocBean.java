/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class AttributiDinamiciDocBean {

	private String rowId;	
	private Map<String, Object> valori;
	private Map<String, String> tipiValori;
	private String activityNameWF;
	private String esitoActivityWF;
	
	public Map<String, Object> getValori() {
		return valori;
	}
	public void setValori(Map<String, Object> valori) {
		this.valori = valori;
	}
	public Map<String, String> getTipiValori() {
		return tipiValori;
	}
	public void setTipiValori(Map<String, String> tipiValori) {
		this.tipiValori = tipiValori;
	}	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getActivityNameWF() {
		return activityNameWF;
	}
	public void setActivityNameWF(String activityNameWF) {
		this.activityNameWF = activityNameWF;
	}
	public String getEsitoActivityWF() {
		return esitoActivityWF;
	}
	public void setEsitoActivityWF(String esitoActivityWF) {
		this.esitoActivityWF = esitoActivityWF;
	}	
	
}
