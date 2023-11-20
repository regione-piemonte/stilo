/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
//ATTR_EVENT_TYPE 
@XmlRootElement
public class AttrEventTypeBean implements Serializable {

	private static long serialVersionUID = -483078932590126871L;

	@XmlVariabile(nome = "SIGLA_PROPOSTA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegProvvisoria;

	@XmlVariabile(nome = "NUMERO_PROPOSTA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String numeroRegProvvisoria;

	@XmlVariabile(nome = "SIGLA_NUM_DEFINITIVA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String siglaRegistrazione;

	@XmlVariabile(nome = "NUMERO_DEFINITIVO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String numeroRegistrazione;
    
	@XmlVariabile(nome = "ANNO_PROPOSTA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String annoRegProvvisoria;
	
	@XmlVariabile(nome = "DATA_PROPOSTA_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String dataRegProvvisoria;
	
	@XmlVariabile(nome = "ANNO_DEFINITIV0_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String annoAttoDefinitivo;
	
	@XmlVariabile(nome = "DATA_DEFINITIVO_ATTO", tipo = TipoVariabile.SEMPLICE)
	private String dataRegistrazione;
	
	public String getSiglaRegProvvisoria() {
		return siglaRegProvvisoria;
	}

	public void setSiglaRegProvvisoria(String siglaRegProvvisoria) {
		this.siglaRegProvvisoria = siglaRegProvvisoria;
	}

	public String getNumeroRegProvvisoria() {
		return numeroRegProvvisoria;
	}

	public void setNumeroRegProvvisoria(String numeroRegProvvisoria) {
		this.numeroRegProvvisoria = numeroRegProvvisoria;
	}

	public String getSiglaRegistrazione() {
		return siglaRegistrazione;
	}

	public void setSiglaRegistrazione(String siglaRegistrazione) {
		this.siglaRegistrazione = siglaRegistrazione;
	}

	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}

	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}

	public String getAnnoRegProvvisoria() {
		return annoRegProvvisoria;
	}

	public void setAnnoRegProvvisoria(String annoRegProvvisoria) {
		this.annoRegProvvisoria = annoRegProvvisoria;
	}

	public String getDataRegProvvisoria() {
		return dataRegProvvisoria;
	}

	public void setDataRegProvvisoria(String dataRegProvvisoria) {
		this.dataRegProvvisoria = dataRegProvvisoria;
	}

	public String getAnnoAttoDefinitivo() {
		return annoAttoDefinitivo;
	}

	public void setAnnoAttoDefinitivo(String annoAttoDefinitivo) {
		this.annoAttoDefinitivo = annoAttoDefinitivo;
	}

	public String getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
	
	

	
}