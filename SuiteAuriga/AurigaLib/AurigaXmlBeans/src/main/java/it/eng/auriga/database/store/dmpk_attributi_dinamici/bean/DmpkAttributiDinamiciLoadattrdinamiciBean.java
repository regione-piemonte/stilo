/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciLoadattrdinamiciBean")
public class DmpkAttributiDinamiciLoadattrdinamiciBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_LOADATTRDINAMICI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nometabellain;
	private java.lang.String rowidin;
	private java.lang.String citipoentitain;
	private java.lang.String listatipiregin;
	private java.lang.String attrvaluesxmlin;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgmostratuttiin;
	private java.lang.Integer flgnomeattrconsuffin;
	private java.lang.String nomeflussowfin;
	private java.lang.String processnamewfin;
	private java.lang.String activitynamewfin;
	private java.lang.Integer flgdatistoricitaskin;
	private java.lang.Integer flgdettudconiterwfin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNometabellain(){return nometabellain;}
    public java.lang.String getRowidin(){return rowidin;}
    public java.lang.String getCitipoentitain(){return citipoentitain;}
    public java.lang.String getListatipiregin(){return listatipiregin;}
    public java.lang.String getAttrvaluesxmlin(){return attrvaluesxmlin;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgmostratuttiin(){return flgmostratuttiin;}
    public java.lang.Integer getFlgnomeattrconsuffin(){return flgnomeattrconsuffin;}
    public java.lang.String getNomeflussowfin(){return nomeflussowfin;}
    public java.lang.String getProcessnamewfin(){return processnamewfin;}
    public java.lang.String getActivitynamewfin(){return activitynamewfin;}
    public java.lang.Integer getFlgdatistoricitaskin(){return flgdatistoricitaskin;}
    public java.lang.Integer getFlgdettudconiterwfin(){return flgdettudconiterwfin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNometabellain(java.lang.String value){this.nometabellain=value;}
    public void setRowidin(java.lang.String value){this.rowidin=value;}
    public void setCitipoentitain(java.lang.String value){this.citipoentitain=value;}
    public void setListatipiregin(java.lang.String value){this.listatipiregin=value;}
    public void setAttrvaluesxmlin(java.lang.String value){this.attrvaluesxmlin=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgmostratuttiin(java.lang.Integer value){this.flgmostratuttiin=value;}
    public void setFlgnomeattrconsuffin(java.lang.Integer value){this.flgnomeattrconsuffin=value;}
    public void setNomeflussowfin(java.lang.String value){this.nomeflussowfin=value;}
    public void setProcessnamewfin(java.lang.String value){this.processnamewfin=value;}
    public void setActivitynamewfin(java.lang.String value){this.activitynamewfin=value;}
    public void setFlgdatistoricitaskin(java.lang.Integer value){this.flgdatistoricitaskin=value;}
    public void setFlgdettudconiterwfin(java.lang.Integer value){this.flgdettudconiterwfin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    