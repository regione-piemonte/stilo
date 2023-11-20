/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraPropostaAttoLiquidazione implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger numeroAtto;
	private BigInteger annoAtto;
	private String siglaTipoAtto;
	private Calendar dataAtto;
	private String settoreAtto;
	
	public BigInteger getNumeroAtto() {
		return numeroAtto;
	}
	
	public void setNumeroAtto(BigInteger numeroAtto) {
		this.numeroAtto = numeroAtto;
	}
	
	public BigInteger getAnnoAtto() {
		return annoAtto;
	}
	
	public void setAnnoAtto(BigInteger annoAtto) {
		this.annoAtto = annoAtto;
	}
	
	public String getSiglaTipoAtto() {
		return siglaTipoAtto;
	}
	
	public void setSiglaTipoAtto(String siglaTipoAtto) {
		this.siglaTipoAtto = siglaTipoAtto;
	}
	
	public Calendar getDataAtto() {
		return dataAtto;
	}
	
	public void setDataAtto(Calendar dataAtto) {
		this.dataAtto = dataAtto;
	}
	
	public String getSettoreAtto() {
		return settoreAtto;
	}
	
	public void setSettoreAtto(String settoreAtto) {
		this.settoreAtto = settoreAtto;
	}
		
}
