/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.widgets.grid.ListGridField;

public class ElencoAlboBean {

	private String idElencoAlbo;
	private String rowId;
	private String idSogg;
	private String denomSogg;
	private String targaProvLuogo;
	private String localitaLuogo;
	private String comuneLuogo;
	private String indirizzoLuogo;
	private String tipoAtt;
	private String nroAut;
	private Date dataRilascioAut;
	private Integer durataAut;
	
	private Map<String,Object> attributiDinamici;
	private Map<String,String> tipiAttributiDinamici;
		
	public String getIdElencoAlbo() {
		return idElencoAlbo;
	}
	public void setIdElencoAlbo(String idElencoAlbo) {
		this.idElencoAlbo = idElencoAlbo;
	}
	public String getDenomSogg() {
		return denomSogg;
	}
	public void setDenomSogg(String denomSogg) {
		this.denomSogg = denomSogg;
	}
	public String getIdSogg() {
		return idSogg;
	}
	public void setIdSogg(String idSogg) {
		this.idSogg = idSogg;
	}
	public String getTargaProvLuogo() {
		return targaProvLuogo;
	}
	public void setTargaProvLuogo(String targaProvLuogo) {
		this.targaProvLuogo = targaProvLuogo;
	}
	public String getLocalitaLuogo() {
		return localitaLuogo;
	}
	public void setLocalitaLuogo(String localitaLuogo) {
		this.localitaLuogo = localitaLuogo;
	}
	public String getComuneLuogo() {
		return comuneLuogo;
	}
	public void setComuneLuogo(String comuneLuogo) {
		this.comuneLuogo = comuneLuogo;
	}
	public String getIndirizzoLuogo() {
		return indirizzoLuogo;
	}
	public void setIndirizzoLuogo(String indirizzoLuogo) {
		this.indirizzoLuogo = indirizzoLuogo;
	}
	public String getTipoAtt() {
		return tipoAtt;
	}
	public void setTipoAtt(String tipoAtt) {
		this.tipoAtt = tipoAtt;
	}
	public String getNroAut() {
		return nroAut;
	}
	public void setNroAut(String nroAut) {
		this.nroAut = nroAut;
	}
	public Date getDataRilascioAut() {
		return dataRilascioAut;
	}
	public void setDataRilascioAut(Date dataRilascioAut) {
		this.dataRilascioAut = dataRilascioAut;
	}
	public Integer getDurataAut() {
		return durataAut;
	}
	public void setDurataAut(Integer durataAut) {
		this.durataAut = durataAut;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public Map<String,Object> getAttributiDinamici() {
		return attributiDinamici;
	}
	public void setAttributiDinamici(Map<String,Object> attributiDinamici) {
		this.attributiDinamici = attributiDinamici;
	}
	public Map<String,String> getTipiAttributiDinamici() {
		return tipiAttributiDinamici;
	}
	public void setTipiAttributiDinamici(Map<String,String> tipiAttributiDinamici) {
		this.tipiAttributiDinamici = tipiAttributiDinamici;
	}
	
}
