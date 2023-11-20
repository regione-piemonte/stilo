/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModFonteInBean implements Serializable {
	
	private static final long serialVersionUID = 8827729163041008691L;	
	  	
	@XmlVariabile(nome="#ProvCIFonte", tipo = TipoVariabile.SEMPLICE)
	private String provCIFonte;
	
	@XmlVariabile(nome="#CodTipoFonte", tipo = TipoVariabile.SEMPLICE)
	private String codTipoFonte;
	
	@XmlVariabile(nome="#TipoFonte", tipo = TipoVariabile.SEMPLICE)
	private String tipoFonte;
	
	@XmlVariabile(nome="#NroFonte", tipo = TipoVariabile.SEMPLICE)	
	private String nroFonte;
	
	@XmlVariabile(nome="#AnnoFonte", tipo = TipoVariabile.SEMPLICE)
	private String annoFonte;
	
	@XmlVariabile(nome="#DesFonte", tipo = TipoVariabile.SEMPLICE)
	private String desFonte;
	
	@XmlVariabile(nome="#IdEnteEmittente", tipo = TipoVariabile.SEMPLICE)
	private String idEnteEmittente;
	
	@XmlVariabile(nome="#DesEnteEmittente", tipo = TipoVariabile.SEMPLICE)
	private String desEnteEmittente;
	
	@XmlVariabile(nome="#IdProcOrig", tipo = TipoVariabile.SEMPLICE)	
	private String idProcOrig;
	
	@XmlVariabile(nome="#Note", tipo = TipoVariabile.SEMPLICE)
	private String note;
	
	@XmlVariabile(nome="#FlgValida", tipo = TipoVariabile.SEMPLICE)
	private String flgValida = "1";	
	
	@XmlVariabile(nome="@#ProcDomainObjRef", tipo = TipoVariabile.LISTA)
	private List<ProcDomainObjRef> listaProcDomainObjRef;
	
	@XmlVariabile(nome="#Append_@#ProcDomainObjRef", tipo = TipoVariabile.SEMPLICE)
	private String append_listaProcDomainObjRef = "1";
	
	
	public String getProvCIFonte() {
		return provCIFonte;
	}
	public void setProvCIFonte(String provCIFonte) {
		this.provCIFonte = provCIFonte;
	}
	public String getCodTipoFonte() {
		return codTipoFonte;
	}
	public void setCodTipoFonte(String codTipoFonte) {
		this.codTipoFonte = codTipoFonte;
	}
	public String getTipoFonte() {
		return tipoFonte;
	}
	public void setTipoFonte(String tipoFonte) {
		this.tipoFonte = tipoFonte;
	}
	public String getNroFonte() {
		return nroFonte;
	}
	public void setNroFonte(String nroFonte) {
		this.nroFonte = nroFonte;
	}
	public String getAnnoFonte() {
		return annoFonte;
	}
	public void setAnnoFonte(String annoFonte) {
		this.annoFonte = annoFonte;
	}
	public String getDesFonte() {
		return desFonte;
	}
	public void setDesFonte(String desFonte) {
		this.desFonte = desFonte;
	}
	public String getIdEnteEmittente() {
		return idEnteEmittente;
	}
	public void setIdEnteEmittente(String idEnteEmittente) {
		this.idEnteEmittente = idEnteEmittente;
	}
	public String getDesEnteEmittente() {
		return desEnteEmittente;
	}
	public void setDesEnteEmittente(String desEnteEmittente) {
		this.desEnteEmittente = desEnteEmittente;
	}
	public String getIdProcOrig() {
		return idProcOrig;
	}
	public void setIdProcOrig(String idProcOrig) {
		this.idProcOrig = idProcOrig;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getFlgValida() {
		return flgValida;
	}
	public void setFlgValida(String flgValida) {
		this.flgValida = flgValida;
	}
	public List<ProcDomainObjRef> getListaProcDomainObjRef() {
		return listaProcDomainObjRef;
	}
	public void setListaProcDomainObjRef(
			List<ProcDomainObjRef> listaProcDomainObjRef) {
		this.listaProcDomainObjRef = listaProcDomainObjRef;
	}
	public String getAppend_listaProcDomainObjRef() {
		return append_listaProcDomainObjRef;
	}
	public void setAppend_listaProcDomainObjRef(String append_listaProcDomainObjRef) {
		this.append_listaProcDomainObjRef = append_listaProcDomainObjRef;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
