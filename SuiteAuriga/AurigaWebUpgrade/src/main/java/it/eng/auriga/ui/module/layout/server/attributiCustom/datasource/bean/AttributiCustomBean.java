/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.attributiCustom.datasource.OpzioniListaSceltaBean;

import java.util.Date;
import java.util.List;


public class AttributiCustomBean {
	
	private String 	     rowid;
	private String 	 	 nome;
	private String 		 etichetta;
	private String 		 tipo;
	private String 		 descrizione;
	private String 		 appartenenza;
	private String 		 altezzaVideoCaratteriItem;
	private String 		 definizioneValoriAmmessi;
	private String 		 formatNumberItem;
	private String 		 larghezzaVideoCaratteriItem;
	private String 		 attrQueryXValues;
	private String 		 espressioneRegolareItem;
	private String 		 defaultValue;
	private String 		 defaultTextAreaValue;
	private String 		 defaultCKeditorValue;
	private String 		 maxNumeroCifreItem;
	private String 		 numMinValue;
	private Date 		 defaultDateValueItem;
	private Date 		 maxDateValueItem;
	private Date 		 minDateValueItem;
	private Date 		 defaultDateTimeValueItem;
	private Date 		 maxDateTimeValueItem;
	private Date 		 minDateTimeValueItem;
	private String 		 maxNumeroCaratteriItem;
	private String 		 caseItem;
	private String 	 	 nrDecimaleItem;
	private List<OpzioniListaSceltaBean> xmlValoriPossibiliIn;
	private Integer 	 valido;
	private Boolean 	 flgEscludiSottoAttr;
	private Boolean 	 flgInclAnnullati; 
	private Boolean 	 flgObblInSupItem;
	private Boolean      flgDaIndicizzare;
	private String       tipoLista;
	
	private Integer      nroOrdine;
	private Integer      nroRigaInAttrAppIn;
	private Boolean      flgProtectedIn;
	private Boolean      flgValoriUnivociIn;
	private Boolean      flgValoreObbligatorio;
	private String       uRLWSValoriPossibiliIn;
	private String       xMLInWSValoriPossibiliIn;	
	private String       tipoEditorHtml;

	public String getTipoLista() {
		return tipoLista;
	}
	public void setTipoLista(String tipoLista) {
		this.tipoLista = tipoLista;
	}
	public List<OpzioniListaSceltaBean> getXmlValoriPossibiliIn() {
		return xmlValoriPossibiliIn;
	}
	public void setXmlValoriPossibiliIn(List<OpzioniListaSceltaBean> xmlValoriPossibiliIn) {
		this.xmlValoriPossibiliIn = xmlValoriPossibiliIn;
	}
	public Boolean getFlgEscludiSottoAttr() {
		return flgEscludiSottoAttr;
	}
	public void setFlgEscludiSottoAttr(Boolean flgEscludiSottoAttr) {
		this.flgEscludiSottoAttr = flgEscludiSottoAttr;
	}
	public Boolean getFlgInclAnnullati() {
		return flgInclAnnullati;
	}
	public void setFlgInclAnnullati(Boolean flgInclAnnullati) {
		this.flgInclAnnullati = flgInclAnnullati;
	}
	public Boolean getFlgObblInSupItem() {
		return flgObblInSupItem;
	}
	public void setFlgObblInSupItem(Boolean flgObblInSupItem) {
		this.flgObblInSupItem = flgObblInSupItem;
	}
	public Boolean getFlgDaIndicizzare() {
		return flgDaIndicizzare;
	}
	public void setFlgDaIndicizzare(Boolean flgDaIndicizzare) {
		this.flgDaIndicizzare = flgDaIndicizzare;
	}	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEtichetta() {
		return etichetta;
	}
	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getAppartenenza() {
		return appartenenza;
	}
	public void setAppartenenza(String appartenenza) {
		this.appartenenza = appartenenza;
	}
	public String getMaxNumeroCifreItem() {
		return maxNumeroCifreItem;
	}
	public void setMaxNumeroCifreItem(String maxNumeroCifreItem) {
		this.maxNumeroCifreItem = maxNumeroCifreItem;
	}
	public String getAltezzaVideoCaratteriItem() {
		return altezzaVideoCaratteriItem;
	}
	public void setAltezzaVideoCaratteriItem(String altezzaVideoCaratteriItem) {
		this.altezzaVideoCaratteriItem = altezzaVideoCaratteriItem;
	}
	public String getDefinizioneValoriAmmessi() {
		return definizioneValoriAmmessi;
	}
	public void setDefinizioneValoriAmmessi(String definizioneValoriAmmessi) {
		this.definizioneValoriAmmessi = definizioneValoriAmmessi;
	}
	public String getFormatNumberItem() {
		return formatNumberItem;
	}
	public void setFormatNumberItem(String formatNumberItem) {
		this.formatNumberItem = formatNumberItem;
	}
	public String getLarghezzaVideoCaratteriItem() {
		return larghezzaVideoCaratteriItem;
	}
	public void setLarghezzaVideoCaratteriItem(String larghezzaVideoCaratteriItem) {
		this.larghezzaVideoCaratteriItem = larghezzaVideoCaratteriItem;
	}
	public String getAttrQueryXValues() {
		return attrQueryXValues;
	}
	public void setAttrQueryXValues(String attrQueryXValues) {
		this.attrQueryXValues = attrQueryXValues;
	}
	public String getEspressioneRegolareItem() {
		return espressioneRegolareItem;
	}
	public void setEspressioneRegolareItem(String espressioneRegolareItem) {
		this.espressioneRegolareItem = espressioneRegolareItem;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(
			String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultTextAreaValue() {
		return defaultTextAreaValue;
	}
	public void setDefaultTextAreaValue(String defaultTextAreaValue) {
		this.defaultTextAreaValue = defaultTextAreaValue;
	}
	public String getDefaultCKeditorValue() {
		return defaultCKeditorValue;
	}
	public void setDefaultCKeditorValue(String defaultCKeditorValue) {
		this.defaultCKeditorValue = defaultCKeditorValue;
	}
	public String getMaxNumeroCaratteriItem() {
		return maxNumeroCaratteriItem;
	}
	public void setMaxNumeroCaratteriItem(String maxNumeroCaratteriItem) {
		this.maxNumeroCaratteriItem = maxNumeroCaratteriItem;
	}
	public String getNumMinValue() {
		return numMinValue;
	}
	public void setNumMinValue(String numMinValue) {
		this.numMinValue = numMinValue;
	}
	public String getCaseItem() {
		return caseItem;
	}
	public void setCaseItem(String caseItem) {
		this.caseItem = caseItem;
	}
	public String getNrDecimaleItem() {
		return nrDecimaleItem;
	}
	public void setNrDecimaleItem(String nrDecimaleItem) {
		this.nrDecimaleItem = nrDecimaleItem;
	}
	public Integer getValido() {
		return valido;
	}
	public void setValido(Integer valido) {
		this.valido = valido;
	}
	public Date getDefaultDateValueItem() {
		return defaultDateValueItem;
	}
	public void setDefaultDateValueItem(Date defaultDateValueItem) {
		this.defaultDateValueItem = defaultDateValueItem;
	}
	public Date getMaxDateValueItem() {
		return maxDateValueItem;
	}
	public void setMaxDateValueItem(Date maxDateValueItem) {
		this.maxDateValueItem = maxDateValueItem;
	}
	public Date getMinDateValueItem() {
		return minDateValueItem;
	}
	public void setMinDateValueItem(Date minDateValueItem) {
		this.minDateValueItem = minDateValueItem;
	}
	public Date getDefaultDateTimeValueItem() {
		return defaultDateTimeValueItem;
	}
	public void setDefaultDateTimeValueItem(Date defaultDateTimeValueItem) {
		this.defaultDateTimeValueItem = defaultDateTimeValueItem;
	}
	public Date getMaxDateTimeValueItem() {
		return maxDateTimeValueItem;
	}
	public void setMaxDateTimeValueItem(Date maxDateTimeValueItem) {
		this.maxDateTimeValueItem = maxDateTimeValueItem;
	}
	public Date getMinDateTimeValueItem() {
		return minDateTimeValueItem;
	}
	public void setMinDateTimeValueItem(Date minDateTimeValueItem) {
		this.minDateTimeValueItem = minDateTimeValueItem;
	}
	public Integer getNroRigaInAttrAppIn() {
		return nroRigaInAttrAppIn;
	}
	public void setNroRigaInAttrAppIn(Integer nroRigaInAttrAppIn) {
		this.nroRigaInAttrAppIn = nroRigaInAttrAppIn;
	}
	public Boolean getFlgProtectedIn() {
		return flgProtectedIn;
	}
	public void setFlgProtectedIn(Boolean flgProtectedIn) {
		this.flgProtectedIn = flgProtectedIn;
	}
	public Boolean getFlgValoriUnivociIn() {
		return flgValoriUnivociIn;
	}
	public void setFlgValoriUnivociIn(Boolean flgValoriUnivociIn) {
		this.flgValoriUnivociIn = flgValoriUnivociIn;
	}
	public String getuRLWSValoriPossibiliIn() {
		return uRLWSValoriPossibiliIn;
	}
	public void setuRLWSValoriPossibiliIn(String uRLWSValoriPossibiliIn) {
		this.uRLWSValoriPossibiliIn = uRLWSValoriPossibiliIn;
	}
	public String getxMLInWSValoriPossibiliIn() {
		return xMLInWSValoriPossibiliIn;
	}
	public void setxMLInWSValoriPossibiliIn(String xMLInWSValoriPossibiliIn) {
		this.xMLInWSValoriPossibiliIn = xMLInWSValoriPossibiliIn;
	}
	public Integer getNroOrdine() {
		return nroOrdine;
	}
	public void setNroOrdine(Integer nroOrdine) {
		this.nroOrdine = nroOrdine;
	}
	public Boolean getFlgValoreObbligatorio() {
		return flgValoreObbligatorio;
	}
	public void setFlgValoreObbligatorio(Boolean flgValoreObbligatorio) {
		this.flgValoreObbligatorio = flgValoreObbligatorio;
	}
	public String getTipoEditorHtml() {
		return tipoEditorHtml;
	}
	public void setTipoEditorHtml(String tipoEditorHtml) {
		this.tipoEditorHtml = tipoEditorHtml;
	}
}
