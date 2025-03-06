package it.eng.dm.sirabusiness.bean;

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class SiraSoggettoBeanIn extends AbstractBean implements Serializable{
	
	private static final long serialVersionUID = 175503336348726521L;
	
	private Boolean giuridico;

	private BigDecimal idSoggetto;

	public Boolean getGiuridico() {
		return giuridico;
	}

	public void setGiuridico(Boolean giuridico) {
		this.giuridico = giuridico;
	}

	public BigDecimal getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(BigDecimal idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

}
