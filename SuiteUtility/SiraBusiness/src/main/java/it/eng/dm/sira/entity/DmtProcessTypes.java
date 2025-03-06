package it.eng.dm.sira.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DMT_PROCESS_TYPES")
public class DmtProcessTypes {
	
	private Long idProcessType;

	private String nomeProcessType;

	private String desProcessType;
	
	private Integer flgAnn; 
	
	@Id
	@Column(name = "ID_PROCESS_TYPE", unique = true, nullable = false, precision = 38, scale = 0)
	public Long getIdProcessType() {
		return idProcessType;
	}

	public void setIdProcessType(Long idProcessType) {
		this.idProcessType = idProcessType;
	}
	
	@Column(name = "NOME_PROCESS_TYPE", length = 100)
	public String getNomeProcessType() {
		return nomeProcessType;
	}

	public void setNomeProcessType(String nomeProcessType) {
		this.nomeProcessType = nomeProcessType;
	}
	
	@Column(name = "DES_PROCESS_TYPE", length = 1000)
	public String getDesProcessType() {
		return desProcessType;
	}

	public void setDesProcessType(String desProcessType) {
		this.desProcessType = desProcessType;
	}
	
	@Column(name = "FLG_ANN", nullable = false, precision = 1, scale = 0)
	public Integer getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(Integer flgAnn) {
		this.flgAnn = flgAnn;
	}

}
