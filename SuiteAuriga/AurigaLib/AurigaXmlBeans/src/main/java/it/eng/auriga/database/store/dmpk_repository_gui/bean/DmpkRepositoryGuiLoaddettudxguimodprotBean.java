/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRepositoryGuiLoaddettudxguimodprotBean")
public class DmpkRepositoryGuiLoaddettudxguimodprotBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REPOSITORY_GUI_LOADDETTUDXGUIMODPROT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idudin;
	private java.lang.String datiudxmlout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgsoloabilazioniin;
	private java.lang.String tsanndatiin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String tasknamein;
	private java.lang.String idpubblicazionein;
	private java.lang.String cinodoscrivaniain;
	private java.lang.String listaidudscansionidaassin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getDatiudxmlout(){return datiudxmlout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgsoloabilazioniin(){return flgsoloabilazioniin;}
    public java.lang.String getTsanndatiin(){return tsanndatiin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getTasknamein(){return tasknamein;}
    public java.lang.String getIdpubblicazionein(){return idpubblicazionein;}
    public java.lang.String getCinodoscrivaniain(){return cinodoscrivaniain;}
    public java.lang.String getListaidudscansionidaassin(){return listaidudscansionidaassin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setDatiudxmlout(java.lang.String value){this.datiudxmlout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgsoloabilazioniin(java.lang.Integer value){this.flgsoloabilazioniin=value;}
    public void setTsanndatiin(java.lang.String value){this.tsanndatiin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setTasknamein(java.lang.String value){this.tasknamein=value;}
    public void setIdpubblicazionein(java.lang.String value){this.idpubblicazionein=value;}
    public void setCinodoscrivaniain(java.lang.String value){this.cinodoscrivaniain=value;}
    public void setListaidudscansionidaassin(java.lang.String value){this.listaidudscansionidaassin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    