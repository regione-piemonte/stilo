/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.database.store.dmpk_bmanager.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerCtrlattiiniterannoprecadspBean")
public class DmpkBmanagerCtrlattiiniterannoprecadspBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_CTRLATTIINITERANNOPRECADSP";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal nrodecretiout;
	private java.math.BigDecimal nrodecreticondaticwolout;
	private java.math.BigDecimal nrodecretifaseistrout;
	private java.math.BigDecimal nrodecretifasebilout;
	private java.math.BigDecimal nrodecretifaseperfout;
	private java.math.BigDecimal nrordaout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getNrodecretiout(){return nrodecretiout;}
    public java.math.BigDecimal getNrodecreticondaticwolout(){return nrodecreticondaticwolout;}
    public java.math.BigDecimal getNrodecretifaseistrout(){return nrodecretifaseistrout;}
    public java.math.BigDecimal getNrodecretifasebilout(){return nrodecretifasebilout;}
    public java.math.BigDecimal getNrodecretifaseperfout(){return nrodecretifaseperfout;}
    public java.math.BigDecimal getNrordaout(){return nrordaout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setNrodecretiout(java.math.BigDecimal value){this.nrodecretiout=value;}
    public void setNrodecreticondaticwolout(java.math.BigDecimal value){this.nrodecreticondaticwolout=value;}
    public void setNrodecretifaseistrout(java.math.BigDecimal value){this.nrodecretifaseistrout=value;}
    public void setNrodecretifasebilout(java.math.BigDecimal value){this.nrodecretifasebilout=value;}
    public void setNrodecretifaseperfout(java.math.BigDecimal value){this.nrodecretifaseperfout=value;}
    public void setNrordaout(java.math.BigDecimal value){this.nrordaout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    
