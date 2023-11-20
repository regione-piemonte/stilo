/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiGetinfodocfromuriverBean")
public class DmpkRepositoryGuiGetinfodocfromuriverBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_GETINFODOCFROMURIVER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String uriin;
	private java.math.BigDecimal iddocout;
	private java.math.BigDecimal idudout;
	private java.math.BigDecimal iddoctypeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getUriin(){return uriin;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.math.BigDecimal getIdudout(){return idudout;}
    public java.math.BigDecimal getIddoctypeout(){return iddoctypeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setUriin(java.lang.String value){this.uriin=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setIdudout(java.math.BigDecimal value){this.idudout=value;}
    public void setIddoctypeout(java.math.BigDecimal value){this.iddoctypeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    