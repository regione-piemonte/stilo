/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcDomainObjRef implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String idProcObj;
	
    @NumeroColonna(numero = "2")
	private String idOst;
    
    @NumeroColonna(numero = "3")
	private String idCCost;
    
    @NumeroColonna(numero = "4")
	private String codNaturaRel;
    
    @NumeroColonna(numero = "5")
	private String desNaturaRel;
    
    @NumeroColonna(numero = "6")
	private String dettagliRel;
    
    @NumeroColonna(numero = "7")
    @TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataIniVld;
    
    @NumeroColonna(numero = "8")
    @TipoData(tipo=Tipo.DATA_SENZA_ORA)
	private Date dataFineVld;
    
    @NumeroColonna(numero = "9")
	private Flag flgValido;
    
    
	public String getIdProcObj() {
		return idProcObj;
	}
	public void setIdProcObj(String idProcObj) {
		this.idProcObj = idProcObj;
	}
	public String getIdOst() {
		return idOst;
	}
	public void setIdOst(String idOst) {
		this.idOst = idOst;
	}
	public String getIdCCost() {
		return idCCost;
	}
	public void setIdCCost(String idCCost) {
		this.idCCost = idCCost;
	}
	public String getCodNaturaRel() {
		return codNaturaRel;
	}
	public void setCodNaturaRel(String codNaturaRel) {
		this.codNaturaRel = codNaturaRel;
	}
	public String getDesNaturaRel() {
		return desNaturaRel;
	}
	public void setDesNaturaRel(String desNaturaRel) {
		this.desNaturaRel = desNaturaRel;
	}
	public String getDettagliRel() {
		return dettagliRel;
	}
	public void setDettagliRel(String dettagliRel) {
		this.dettagliRel = dettagliRel;
	}
	public Date getDataIniVld() {
		return dataIniVld;
	}
	public void setDataIniVld(Date dataIniVld) {
		this.dataIniVld = dataIniVld;
	}
	public Date getDataFineVld() {
		return dataFineVld;
	}
	public void setDataFineVld(Date dataFineVld) {
		this.dataFineVld = dataFineVld;
	}
	public Flag getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(Flag flgValido) {
		this.flgValido = flgValido;
	}
    
}
