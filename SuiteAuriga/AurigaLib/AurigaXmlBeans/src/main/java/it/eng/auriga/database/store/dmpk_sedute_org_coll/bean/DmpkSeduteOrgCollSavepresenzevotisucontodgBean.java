/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkSeduteOrgCollSavepresenzevotisucontodgBean")
public class DmpkSeduteOrgCollSavepresenzevotisucontodgBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SEDUTE_ORG_COLL_SAVEPRESENZEVOTISUCONTODG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idsedutain;
	private java.math.BigDecimal idudargomentoinodgin;
	private java.lang.String presenzevotiin;
	private java.lang.Integer totaleastenutivotoin;
	private java.lang.Integer totalevotiespressiin;
	private java.lang.Integer totalevotifavorevoliin;
	private java.lang.Integer totalevoticontrariin;
	private java.lang.String altripresentiin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String flgpresenzevotiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdsedutain(){return idsedutain;}
    public java.math.BigDecimal getIdudargomentoinodgin(){return idudargomentoinodgin;}
    public java.lang.String getPresenzevotiin(){return presenzevotiin;}
    public java.lang.Integer getTotaleastenutivotoin(){return totaleastenutivotoin;}
    public java.lang.Integer getTotalevotiespressiin(){return totalevotiespressiin;}
    public java.lang.Integer getTotalevotifavorevoliin(){return totalevotifavorevoliin;}
    public java.lang.Integer getTotalevoticontrariin(){return totalevoticontrariin;}
    public java.lang.String getAltripresentiin(){return altripresentiin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFlgpresenzevotiin(){return flgpresenzevotiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsedutain(java.lang.String value){this.idsedutain=value;}
    public void setIdudargomentoinodgin(java.math.BigDecimal value){this.idudargomentoinodgin=value;}
    public void setPresenzevotiin(java.lang.String value){this.presenzevotiin=value;}
    public void setTotaleastenutivotoin(java.lang.Integer value){this.totaleastenutivotoin=value;}
    public void setTotalevotiespressiin(java.lang.Integer value){this.totalevotiespressiin=value;}
    public void setTotalevotifavorevoliin(java.lang.Integer value){this.totalevotifavorevoliin=value;}
    public void setTotalevoticontrariin(java.lang.Integer value){this.totalevoticontrariin=value;}
    public void setAltripresentiin(java.lang.String value){this.altripresentiin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgpresenzevotiin(java.lang.String value){this.flgpresenzevotiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    