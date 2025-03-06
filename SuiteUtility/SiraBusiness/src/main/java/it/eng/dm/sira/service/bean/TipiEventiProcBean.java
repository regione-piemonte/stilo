package it.eng.dm.sira.service.bean;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipiEventiProcBean {
		
	private BigDecimal idProcessType;
	private String nomeProcessType;
	private BigDecimal idEventType;
	private String desEventType;
	private BigDecimal nroPosizione;
	
	public BigDecimal getIdProcessType() {
		return idProcessType;
	}
	public void setIdProcessType(BigDecimal idProcessType) {
		this.idProcessType = idProcessType;
	}
	public String getNomeProcessType() {
		return nomeProcessType;
	}
	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}
	public BigDecimal getIdEventType() {
		return idEventType;
	}
	public void setIdEventType(BigDecimal idEventType) {
		this.idEventType = idEventType;
	}
	public String getDesEventType() {
		return desEventType;
	}
	public void setDesEventType(String desEventType) {
		this.desEventType = desEventType;
	}
	public BigDecimal getNroPosizione() {
		return nroPosizione;
	}
	public void setNroPosizione(BigDecimal nroPosizione) {
		this.nroPosizione = nroPosizione;
	}
	
}
