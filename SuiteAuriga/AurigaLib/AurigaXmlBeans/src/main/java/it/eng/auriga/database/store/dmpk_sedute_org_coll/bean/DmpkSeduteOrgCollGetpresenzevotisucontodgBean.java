/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkSeduteOrgCollGetpresenzevotisucontodgBean")
public class DmpkSeduteOrgCollGetpresenzevotisucontodgBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SEDUTE_ORG_COLL_GETPRESENZEVOTISUCONTODG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idsedutain;
	private java.math.BigDecimal idudargomentoinodgin;
	private java.lang.String presenzevotiout;
	private java.lang.Integer totaleastenutivotoout;
	private java.lang.Integer totalevotiespressiout;
	private java.lang.Integer totalevotifavorevoliout;
	private java.lang.Integer totalevoticontrariout;
	private java.lang.String altripresentiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String flgpresenzevotiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdsedutain(){return idsedutain;}
    public java.math.BigDecimal getIdudargomentoinodgin(){return idudargomentoinodgin;}
    public java.lang.String getPresenzevotiout(){return presenzevotiout;}
    public java.lang.Integer getTotaleastenutivotoout(){return totaleastenutivotoout;}
    public java.lang.Integer getTotalevotiespressiout(){return totalevotiespressiout;}
    public java.lang.Integer getTotalevotifavorevoliout(){return totalevotifavorevoliout;}
    public java.lang.Integer getTotalevoticontrariout(){return totalevoticontrariout;}
    public java.lang.String getAltripresentiout(){return altripresentiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFlgpresenzevotiin(){return flgpresenzevotiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsedutain(java.lang.String value){this.idsedutain=value;}
    public void setIdudargomentoinodgin(java.math.BigDecimal value){this.idudargomentoinodgin=value;}
    public void setPresenzevotiout(java.lang.String value){this.presenzevotiout=value;}
    public void setTotaleastenutivotoout(java.lang.Integer value){this.totaleastenutivotoout=value;}
    public void setTotalevotiespressiout(java.lang.Integer value){this.totalevotiespressiout=value;}
    public void setTotalevotifavorevoliout(java.lang.Integer value){this.totalevotifavorevoliout=value;}
    public void setTotalevoticontrariout(java.lang.Integer value){this.totalevoticontrariout=value;}
    public void setAltripresentiout(java.lang.String value){this.altripresentiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgpresenzevotiin(java.lang.String value){this.flgpresenzevotiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    