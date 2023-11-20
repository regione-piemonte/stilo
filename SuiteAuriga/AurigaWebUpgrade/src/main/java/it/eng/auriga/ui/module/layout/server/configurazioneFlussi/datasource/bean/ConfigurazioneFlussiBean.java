/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;

public class ConfigurazioneFlussiBean {
	
	private String codTipoFlusso;
	private String idTask;
	private String nomeTask;
	private Boolean flgValido;	
	private String idFase;
	private String nomeFase;
	private String numeroOrdine;
	private String idFaseOld;
	
	private List<ACLConfigFlussiBean> listaAcl;
	private List<AttributiAddEditabiliXmlBean> listaAttributiAddEditabili;
	private Boolean flgNessuno;
	
	public String getCodTipoFlusso() {
		return codTipoFlusso;
	}
	public void setCodTipoFlusso(String codTipoFlusso) {
		this.codTipoFlusso = codTipoFlusso;
	}
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public String getNomeTask() {
		return nomeTask;
	}
	public void setNomeTask(String nomeTask) {
		this.nomeTask = nomeTask;
	}
	public Boolean getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(Boolean flgValido) {
		this.flgValido = flgValido;
	}
	public String getIdFase() {
		return idFase;
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public String getNomeFase() {
		return nomeFase;
	}
	public void setNomeFase(String nomeFase) {
		this.nomeFase = nomeFase;
	}
	public String getNumeroOrdine() {
		return numeroOrdine;
	}
	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}
	public String getIdFaseOld() {
		return idFaseOld;
	}
	public void setIdFaseOld(String idFaseOld) {
		this.idFaseOld = idFaseOld;
	}
	public List<ACLConfigFlussiBean> getListaAcl() {
		return listaAcl;
	}
	public void setListaAcl(List<ACLConfigFlussiBean> listaAcl) {
		this.listaAcl = listaAcl;
	}
	public List<AttributiAddEditabiliXmlBean> getListaAttributiAddEditabili() {
		return listaAttributiAddEditabili;
	}
	public void setListaAttributiAddEditabili(List<AttributiAddEditabiliXmlBean> listaAttributiAddEditabili) {
		this.listaAttributiAddEditabili = listaAttributiAddEditabili;
	}
	public Boolean getFlgNessuno() {
		return flgNessuno;
	}
	public void setFlgNessuno(Boolean flgNessuno) {
		this.flgNessuno = flgNessuno;
	}
	
}
