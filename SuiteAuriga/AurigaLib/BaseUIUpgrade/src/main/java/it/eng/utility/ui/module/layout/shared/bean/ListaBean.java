/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;
import java.util.Map;

public class ListaBean {
	
	private String key;
	private Boolean fullScreenDetail;
	private String nroMaxRecord;
	private List<String> colonneDefault;
	private List<String> colonneOrdinabili;
	private Map<String,String> ordinamentoDefault;
	private String tipoRecord;
	private List<String> colonneEstremiRecord;		
	
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setFullScreenDetail(Boolean fullScreenDetail) {
		this.fullScreenDetail = fullScreenDetail;
	}
	public Boolean getFullScreenDetail() {
		return fullScreenDetail;
	}
	public void setNroMaxRecord(String nroMaxRecord) {
		this.nroMaxRecord = nroMaxRecord;
	}
	public String getNroMaxRecord() {
		return nroMaxRecord;
	}
	public void setColonneDefault(List<String> colonneDefault) {
		this.colonneDefault = colonneDefault;
	}
	public List<String> getColonneDefault() {
		return colonneDefault;
	}
	public void setColonneOrdinabili(List<String> colonneOrdinabili) {
		this.colonneOrdinabili = colonneOrdinabili;
	}
	public List<String> getColonneOrdinabili() {
		return colonneOrdinabili;
	}
	public void setOrdinamentoDefault(Map<String,String> ordinamentoDefault) {
		this.ordinamentoDefault = ordinamentoDefault;
	}
	public Map<String,String> getOrdinamentoDefault() {
		return ordinamentoDefault;
	}
	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}
	public String getTipoRecord() {
		return tipoRecord;
	}
	public void setColonneEstremiRecord(List<String> colonneEstremiRecord) {
		this.colonneEstremiRecord = colonneEstremiRecord;
	}
	public List<String> getColonneEstremiRecord() {
		return colonneEstremiRecord;
	}	
	
}
