/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreaModDocumentoInBDBBean extends CreaModDocumentoInBean {

	private static final long serialVersionUID = 8827729163041118691L;
	
	 @XmlVariabile(nome = "#Cod_Ver", tipo = TipoVariabile.SEMPLICE)
	 private String codVer;
	 
	 @XmlVariabile(nome = "COD_BANCA_BDB_Doc", tipo = TipoVariabile.SEMPLICE)
     private String codBanca;
	 
	 @XmlVariabile(nome = "COD_AGENZIA_BANCA_BDB_Doc", tipo = TipoVariabile.SEMPLICE)
	 private String codAgenziaBanca;
	 
	 @TipoData(tipo = Tipo.DATA_SENZA_ORA)
	 @XmlVariabile(nome = "DT_ELABORAZIONE_BDB_Doc", tipo = TipoVariabile.SEMPLICE)
	 private Date dtElaborazione;

	 @XmlVariabile(nome = "ACCOUNT_NUM_BDB_Doc", tipo = TipoVariabile.SEMPLICE)	
	 private String accountNum;
	 
	 @XmlVariabile(nome = "ACCOUNT_CLASS_BDB_Doc", tipo = TipoVariabile.SEMPLICE)		 
	 private String accountClass;
	 
	 @XmlVariabile(nome = "COD_TEMPLATE_BDB_Doc", tipo = TipoVariabile.SEMPLICE)		 	 
	 private String codTemplate;
	 
	 @XmlVariabile(nome = "HISTORY_BDB_Doc", tipo = TipoVariabile.SEMPLICE)		 	 
	 private String history;
	 
	  @XmlVariabile(nome = "FLG_STAMPA_CLIENTE_BDB_Doc", tipo = TipoVariabile.SEMPLICE)		 	 
	 private String flgStampaCliente;
	 
	  @XmlVariabile(nome = "FLG_STAMPA_SEDE_BDB_Doc", tipo = TipoVariabile.SEMPLICE)		 	 
	 private String flgStampaSede;
	 
	  @XmlVariabile(nome = "FLG_TEMPLATE_DEF_BDB_Doc", tipo = TipoVariabile.SEMPLICE)		 	 
	 private String flgTemplateDef;

	
	public String getCodVer() {
		return codVer;
	}

	
	public void setCodVer(String codVer) {
		this.codVer = codVer;
	}

	
	public String getCodBanca() {
		return codBanca;
	}

	
	public void setCodBanca(String codBanca) {
		this.codBanca = codBanca;
	}

	
	public String getCodAgenziaBanca() {
		return codAgenziaBanca;
	}

	
	public void setCodAgenziaBanca(String codAgenziaBanca) {
		this.codAgenziaBanca = codAgenziaBanca;
	}

	
	public Date getDtElaborazione() {
		return dtElaborazione;
	}

	
	public void setDtElaborazione(Date dtElaborazione) {
		this.dtElaborazione = dtElaborazione;
	}

	
	public String getAccountNum() {
		return accountNum;
	}

	
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	
	public String getAccountClass() {
		return accountClass;
	}

	
	public void setAccountClass(String accountClass) {
		this.accountClass = accountClass;
	}

	
	public String getCodTemplate() {
		return codTemplate;
	}

	
	public void setCodTemplate(String codTemplate) {
		this.codTemplate = codTemplate;
	}

	
	public String getHistory() {
		return history;
	}

	
	public void setHistory(String history) {
		this.history = history;
	}

	
	public String getFlgStampaCliente() {
		return flgStampaCliente;
	}

	
	public void setFlgStampaCliente(String flgStampaCliente) {
		this.flgStampaCliente = flgStampaCliente;
	}

	
	public String getFlgStampaSede() {
		return flgStampaSede;
	}

	
	public void setFlgStampaSede(String flgStampaSede) {
		this.flgStampaSede = flgStampaSede;
	}

	
	public String getFlgTemplateDef() {
		return flgTemplateDef;
	}

	
	public void setFlgTemplateDef(String flgTemplateDef) {
		this.flgTemplateDef = flgTemplateDef;
	}
}
