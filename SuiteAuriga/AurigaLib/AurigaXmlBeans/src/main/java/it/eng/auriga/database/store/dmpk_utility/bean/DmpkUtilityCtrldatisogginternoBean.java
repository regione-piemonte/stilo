/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityCtrldatisogginternoBean")
public class DmpkUtilityCtrldatisogginternoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_CTRLDATISOGGINTERNO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String flgsoggperin;
	private java.lang.String datisoggxmlio;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnodecodificasoggin;
	private java.lang.Integer flgcallbyguiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getFlgsoggperin(){return flgsoggperin;}
    public java.lang.String getDatisoggxmlio(){return datisoggxmlio;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnodecodificasoggin(){return flgnodecodificasoggin;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setFlgsoggperin(java.lang.String value){this.flgsoggperin=value;}
    public void setDatisoggxmlio(java.lang.String value){this.datisoggxmlio=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnodecodificasoggin(java.lang.Integer value){this.flgnodecodificasoggin=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    