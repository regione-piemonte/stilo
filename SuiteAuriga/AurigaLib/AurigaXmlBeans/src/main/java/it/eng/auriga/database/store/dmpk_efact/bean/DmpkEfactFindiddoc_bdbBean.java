/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactFindiddoc_bdbBean")
public class DmpkEfactFindiddoc_bdbBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_FINDIDDOC_BDB";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String dtdocumentoin;
	private java.lang.String codclientein;
	private java.lang.String accountnumin;
	private java.lang.String codaccountclassin;
	private java.lang.String provcidoctypein;
	private java.math.BigDecimal iddocout;
	private java.math.BigDecimal idudout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getDtdocumentoin(){return dtdocumentoin;}
    public java.lang.String getCodclientein(){return codclientein;}
    public java.lang.String getAccountnumin(){return accountnumin;}
    public java.lang.String getCodaccountclassin(){return codaccountclassin;}
    public java.lang.String getProvcidoctypein(){return provcidoctypein;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.math.BigDecimal getIdudout(){return idudout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setDtdocumentoin(java.lang.String value){this.dtdocumentoin=value;}
    public void setCodclientein(java.lang.String value){this.codclientein=value;}
    public void setAccountnumin(java.lang.String value){this.accountnumin=value;}
    public void setCodaccountclassin(java.lang.String value){this.codaccountclassin=value;}
    public void setProvcidoctypein(java.lang.String value){this.provcidoctypein=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setIdudout(java.math.BigDecimal value){this.idudout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    