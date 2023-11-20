/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityCtrldatiindirizzoBean")
public class DmpkUtilityCtrldatiindirizzoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_CTRLDATIINDIRIZZO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String datiindirizzoxmlio;
	private java.lang.String tsvldin;
	private java.lang.Integer flgindobbligin;
	private java.lang.Integer flgsoloindinviarioin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getDatiindirizzoxmlio(){return datiindirizzoxmlio;}
    public java.lang.String getTsvldin(){return tsvldin;}
    public java.lang.Integer getFlgindobbligin(){return flgindobbligin;}
    public java.lang.Integer getFlgsoloindinviarioin(){return flgsoloindinviarioin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setDatiindirizzoxmlio(java.lang.String value){this.datiindirizzoxmlio=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    public void setFlgindobbligin(java.lang.Integer value){this.flgindobbligin=value;}
    public void setFlgsoloindinviarioin(java.lang.Integer value){this.flgsoloindinviarioin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    