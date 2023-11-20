/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlAttributiCustom;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RichiestaAutotutelaDocumentoBean extends CreaModDocumentoInBean implements Serializable {

	private static final long serialVersionUID = -7881063601476018663L;
	
	
	@XmlVariabile(nome = "DATI_IST_CED_AUTOTUTELA_Doc", tipo = TipoVariabile.LISTA)
	private List<DatiIstCedAutotutelaBean> datiIstCedAutotutelaDoc;


	public List<DatiIstCedAutotutelaBean> getDatiIstCedAutotutelaDoc() {
		return datiIstCedAutotutelaDoc;
	}


	public void setDatiIstCedAutotutelaDoc(List<DatiIstCedAutotutelaBean> datiIstCedAutotutelaDoc) {
		this.datiIstCedAutotutelaDoc = datiIstCedAutotutelaDoc;
	}

//	@XmlVariabile(nome = "NRO_DOC_RIF_Doc", tipo = TipoVariabile.SEMPLICE)
//	private String nroDocRifDoc;
//	
//	@XmlVariabile(nome = "ANNO_TRIBUTO_Doc", tipo = TipoVariabile.LISTA)
//	private List<String> annoTributoDoc;
//	
//	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
//	@XmlVariabile(nome = "DT_DOC_RIF_Doc", tipo = TipoVariabile.SEMPLICE)
//	private Date dtDocRifDoc;
//	
//	@XmlVariabile(nome = "NOME_TRIBUTO_Doc", tipo = TipoVariabile.SEMPLICE)
//	private String nomeTributoDoc;
	
	
	
	

//	public String getNomeTributoDoc() {
//		return nomeTributoDoc;
//	}
//
//	public void setNomeTributoDoc(String nomeTributoDoc) {
//		this.nomeTributoDoc = nomeTributoDoc;
//	}
//
//	public Date getDtDocRifDoc() {
//		return dtDocRifDoc;
//	}
//
//	public void setDtDocRifDoc(Date dtDocRifDoc) {
//		this.dtDocRifDoc = dtDocRifDoc;
//	}
//
//	public List<String> getAnnoTributoDoc() {
//		return annoTributoDoc;
//	}
//
//	public void setAnnoTributoDoc(List<String> annoTributoDoc) {
//		this.annoTributoDoc = annoTributoDoc;
//	}
//
//	public String getNroDocRifDoc() {
//		return nroDocRifDoc;
//	}
//
//	public void setNroDocRifDoc(String nroDocRifDoc) {
//		this.nroDocRifDoc = nroDocRifDoc;
}