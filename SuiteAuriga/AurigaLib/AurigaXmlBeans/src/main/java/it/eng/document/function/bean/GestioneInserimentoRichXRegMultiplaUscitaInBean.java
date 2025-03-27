/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GestioneInserimentoRichXRegMultiplaUscitaInBean implements Serializable {

	private static final long serialVersionUID = 9171568434703702472L;
	
	private CreaFoglioXImportInBean xlsXImport;
	private it.eng.jaxb.variabili.Lista dettagliColonneXImportContentFoglio;
	private it.eng.jaxb.variabili.Lista xmlContenutiXImportContentFoglio;
	private CreaDocumentiRegMultiplaUscitaBean pCreaDocumentiRegMultiplaUscitaBean;
	
	public CreaFoglioXImportInBean getXlsXImport() {
		return xlsXImport;
	}
	public void setXlsXImport(CreaFoglioXImportInBean xlsXImport) {
		this.xlsXImport = xlsXImport;
	}
	public it.eng.jaxb.variabili.Lista getDettagliColonneXImportContentFoglio() {
		return dettagliColonneXImportContentFoglio;
	}
	public void setDettagliColonneXImportContentFoglio(it.eng.jaxb.variabili.Lista dettagliColonneXImportContentFoglio) {
		this.dettagliColonneXImportContentFoglio = dettagliColonneXImportContentFoglio;
	}
	public it.eng.jaxb.variabili.Lista getXmlContenutiXImportContentFoglio() {
		return xmlContenutiXImportContentFoglio;
	}
	public void setXmlContenutiXImportContentFoglio(it.eng.jaxb.variabili.Lista xmlContenutiXImportContentFoglio) {
		this.xmlContenutiXImportContentFoglio = xmlContenutiXImportContentFoglio;
	}
	public CreaDocumentiRegMultiplaUscitaBean getpCreaDocumentiRegMultiplaUscitaBean() {
		return pCreaDocumentiRegMultiplaUscitaBean;
	}
	public void setpCreaDocumentiRegMultiplaUscitaBean(
			CreaDocumentiRegMultiplaUscitaBean pCreaDocumentiRegMultiplaUscitaBean) {
		this.pCreaDocumentiRegMultiplaUscitaBean = pCreaDocumentiRegMultiplaUscitaBean;
	}

}
