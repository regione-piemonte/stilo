/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocGetinfobookmarkinmodelloBean")
public class DmpkModelliDocGetinfobookmarkinmodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_GETINFOBOOKMARKINMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String nomebookmarkin;
	private java.lang.Integer flgtabellareout;
	private java.lang.String separatorevaloriout;
	private java.lang.String queryvaloriout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getNomebookmarkin(){return nomebookmarkin;}
    public java.lang.Integer getFlgtabellareout(){return flgtabellareout;}
    public java.lang.String getSeparatorevaloriout(){return separatorevaloriout;}
    public java.lang.String getQueryvaloriout(){return queryvaloriout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setNomebookmarkin(java.lang.String value){this.nomebookmarkin=value;}
    public void setFlgtabellareout(java.lang.Integer value){this.flgtabellareout=value;}
    public void setSeparatorevaloriout(java.lang.String value){this.separatorevaloriout=value;}
    public void setQueryvaloriout(java.lang.String value){this.queryvaloriout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    