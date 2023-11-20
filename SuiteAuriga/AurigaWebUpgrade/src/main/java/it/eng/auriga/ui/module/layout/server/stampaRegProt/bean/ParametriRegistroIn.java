/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;

import java.io.Serializable;
import java.util.Date;

public class ParametriRegistroIn implements Serializable {

	@XmlVariabile(nome="CodCategoriaRegistro", tipo=TipoVariabile.SEMPLICE)
	private String codCategoriaRegistro;
	@XmlVariabile(nome="SiglaRegistro", tipo=TipoVariabile.SEMPLICE)
	private String siglaRegistro;	
	@XmlVariabile(nome="AnnoRegistro", tipo=TipoVariabile.SEMPLICE)
	private String annoRegistro;
	@XmlVariabile(nome="DataRegistrazioneDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRegistrazioneDa;
	@XmlVariabile(nome="DataRegistrazioneA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRegistrazioneA;
	@XmlVariabile(nome="NroRegistrazioneDa", tipo=TipoVariabile.SEMPLICE)
	private String nroRegistrazioneDa;
	@XmlVariabile(nome="NroRegistrazioneA", tipo=TipoVariabile.SEMPLICE)
	private String nroRegistrazioneA;	
	@XmlVariabile(nome="SchermaRiservati", tipo=TipoVariabile.SEMPLICE)
	private Flag schermaRiservati;
	
	public String getCodCategoriaRegistro() {
		return codCategoriaRegistro;
	}
	public void setCodCategoriaRegistro(String codCategoriaRegistro) {
		this.codCategoriaRegistro = codCategoriaRegistro;
	}
	public String getSiglaRegistro() {
		return siglaRegistro;
	}
	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	public String getAnnoRegistro() {
		return annoRegistro;
	}
	public void setAnnoRegistro(String annoRegistro) {
		this.annoRegistro = annoRegistro;
	}
	public Date getDataRegistrazioneDa() {
		return dataRegistrazioneDa;
	}
	public void setDataRegistrazioneDa(Date dataRegistrazioneDa) {
		this.dataRegistrazioneDa = dataRegistrazioneDa;
	}
	public Date getDataRegistrazioneA() {
		return dataRegistrazioneA;
	}
	public void setDataRegistrazioneA(Date dataRegistrazioneA) {
		this.dataRegistrazioneA = dataRegistrazioneA;
	}
	public String getNroRegistrazioneDa() {
		return nroRegistrazioneDa;
	}
	public void setNroRegistrazioneDa(String nroRegistrazioneDa) {
		this.nroRegistrazioneDa = nroRegistrazioneDa;
	}
	public String getNroRegistrazioneA() {
		return nroRegistrazioneA;
	}
	public void setNroRegistrazioneA(String nroRegistrazioneA) {
		this.nroRegistrazioneA = nroRegistrazioneA;
	}
	public Flag getSchermaRiservati() {
		return schermaRiservati;
	}
	public void setSchermaRiservati(Flag schermaRiservati) {
		this.schermaRiservati = schermaRiservati;
	}
	
}
