/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.io.Serializable;

public class ReportDocAvanzatiFiltriXmlBean implements Serializable {
	
	@XmlVariabile(nome="DataDa", tipo=TipoVariabile.SEMPLICE)
	private String dataDa;
	
	@XmlVariabile(nome="DataA", tipo=TipoVariabile.SEMPLICE)
	private String dataA;
		
	@XmlVariabile(nome="TipoRegistrazione", tipo=TipoVariabile.SEMPLICE)
	private String tipoRegistrazione;
	
	@XmlVariabile(nome="CategoriaRegistrazione", tipo=TipoVariabile.SEMPLICE)
	private String categoriaRegistrazione;
	
	@XmlVariabile(nome="SiglaRegistro", tipo=TipoVariabile.SEMPLICE)
	private String registroNumerazione;
	
	@XmlVariabile(nome="IdSpAOO", tipo=TipoVariabile.SEMPLICE)
	private String idEnteAoo;
	
	@XmlVariabile(nome="IdUO", tipo=TipoVariabile.SEMPLICE)
	private String idUO;
	
	@XmlVariabile(nome="FlgIncluseSottoUO", tipo=TipoVariabile.SEMPLICE)
	private String flgIncluseSottoUO;
	
	@XmlVariabile(nome="IdUser", tipo=TipoVariabile.SEMPLICE)
	private String idUtente;
		
	@XmlVariabile(nome="CodApplicazioneReg", tipo=TipoVariabile.SEMPLICE)
	private String applicazioniEsterne;

	@XmlVariabile(nome="SupportoOriginale", tipo=TipoVariabile.SEMPLICE)
	private String supporto;

	@XmlVariabile(nome="FlgPresenzaFile", tipo=TipoVariabile.SEMPLICE)
	private String presenzaFile;
	
	@XmlVariabile(nome="CodCanale", tipo=TipoVariabile.SEMPLICE)
	private String mezzoTrasmissione;
	
	@XmlVariabile(nome="LivRiservatezza", tipo=TipoVariabile.SEMPLICE)
	private String livelliRiservatezza;

	public String getDataDa() {
		return dataDa;
	}

	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	public String getDataA() {
		return dataA;
	}

	public void setDataA(String dataA) {
		this.dataA = dataA;
	}

	public String getCategoriaRegistrazione() {
		return categoriaRegistrazione;
	}

	public void setCategoriaRegistrazione(String categoriaRegistrazione) {
		this.categoriaRegistrazione = categoriaRegistrazione;
	}

	public String getRegistroNumerazione() {
		return registroNumerazione;
	}

	public void setRegistroNumerazione(String registroNumerazione) {
		this.registroNumerazione = registroNumerazione;
	}

	public String getIdEnteAoo() {
		return idEnteAoo;
	}

	public void setIdEnteAoo(String idEnteAoo) {
		this.idEnteAoo = idEnteAoo;
	}

	public String getIdUO() {
		return idUO;
	}

	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}

	

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getApplicazioniEsterne() {
		return applicazioniEsterne;
	}

	public void setApplicazioniEsterne(String applicazioniEsterne) {
		this.applicazioniEsterne = applicazioniEsterne;
	}

	public String getSupporto() {
		return supporto;
	}

	public void setSupporto(String supporto) {
		this.supporto = supporto;
	}

	public String getPresenzaFile() {
		return presenzaFile;
	}

	public void setPresenzaFile(String presenzaFile) {
		this.presenzaFile = presenzaFile;
	}

	public String getMezzoTrasmissione() {
		return mezzoTrasmissione;
	}

	public void setMezzoTrasmissione(String mezzoTrasmissione) {
		this.mezzoTrasmissione = mezzoTrasmissione;
	}

	public String getLivelliRiservatezza() {
		return livelliRiservatezza;
	}

	public void setLivelliRiservatezza(String livelliRiservatezza) {
		this.livelliRiservatezza = livelliRiservatezza;
	}

	public String getFlgIncluseSottoUO() {
		return flgIncluseSottoUO;
	}

	public void setFlgIncluseSottoUO(String flgIncluseSottoUO) {
		this.flgIncluseSottoUO = flgIncluseSottoUO;
	}

	public String getTipoRegistrazione() {
		return tipoRegistrazione;
	}

	public void setTipoRegistrazione(String tipoRegistrazione) {
		this.tipoRegistrazione = tipoRegistrazione;
	}
}