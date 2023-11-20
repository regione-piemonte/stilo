/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlAttributiCustom;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DatiIstCedAutotutelaBean implements Serializable {

	private static final long serialVersionUID = -2608303787963511153L;

	@NumeroColonna(numero = "1")
	@XmlVariabile(nome = "NOME_TRIBUTO", tipo = TipoVariabile.SEMPLICE)
	private String nomeTributo;
	
	@NumeroColonna(numero = "2")
	@XmlVariabile(nome = "ANNO_TRIBUTO", tipo = TipoVariabile.SEMPLICE)
	private String annoTributo;
	
	@NumeroColonna(numero = "3")
	@XmlVariabile(nome = "TIPO_DOC_RIF", tipo = TipoVariabile.SEMPLICE)
	private String tipoDocRif;
	
	@NumeroColonna(numero = "4")
	@XmlVariabile(nome = "NRO_DOC_RIF", tipo = TipoVariabile.SEMPLICE)
	private String nroDocRif;
	
	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome = "DT_DOC_RIF", tipo = TipoVariabile.SEMPLICE)
	private Date dtDocRif;

	public String getNomeTributo() {
		return nomeTributo;
	}

	public void setNomeTributo(String nomeTributo) {
		this.nomeTributo = nomeTributo;
	}

	public String getAnnoTributo() {
		return annoTributo;
	}

	public void setAnnoTributo(String annoTributo) {
		this.annoTributo = annoTributo;
	}

	public String getTipoDocRif() {
		return tipoDocRif;
	}

	public void setTipoDocRif(String tipoDocRif) {
		this.tipoDocRif = tipoDocRif;
	}

	public String getNroDocRif() {
		return nroDocRif;
	}

	public void setNroDocRif(String nroDocRif) {
		this.nroDocRif = nroDocRif;
	}

	public Date getDtDocRif() {
		return dtDocRif;
	}

	public void setDtDocRif(Date dtDocRif) {
		this.dtDocRif = dtDocRif;
	}
	
	

}
