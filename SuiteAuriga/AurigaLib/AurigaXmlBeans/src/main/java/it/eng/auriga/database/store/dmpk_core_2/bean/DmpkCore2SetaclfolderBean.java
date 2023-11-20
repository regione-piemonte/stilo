/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2SetaclfolderBean")
public class DmpkCore2SetaclfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_SETACLFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idfolderin;
	private java.lang.Integer flgereditarietaconsentitain;
	private java.math.BigDecimal idfolderereditadain;
	private java.lang.Integer flgpropagazioneconsentitain;
	private java.lang.String flgmodaclin;
	private java.lang.String aclxmlin;
	private java.lang.String urixaggcontainerout;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgcallbyguiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    public java.lang.Integer getFlgereditarietaconsentitain(){return flgereditarietaconsentitain;}
    public java.math.BigDecimal getIdfolderereditadain(){return idfolderereditadain;}
    public java.lang.Integer getFlgpropagazioneconsentitain(){return flgpropagazioneconsentitain;}
    public java.lang.String getFlgmodaclin(){return flgmodaclin;}
    public java.lang.String getAclxmlin(){return aclxmlin;}
    public java.lang.String getUrixaggcontainerout(){return urixaggcontainerout;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgcallbyguiin(){return flgcallbyguiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    public void setFlgereditarietaconsentitain(java.lang.Integer value){this.flgereditarietaconsentitain=value;}
    public void setIdfolderereditadain(java.math.BigDecimal value){this.idfolderereditadain=value;}
    public void setFlgpropagazioneconsentitain(java.lang.Integer value){this.flgpropagazioneconsentitain=value;}
    public void setFlgmodaclin(java.lang.String value){this.flgmodaclin=value;}
    public void setAclxmlin(java.lang.String value){this.aclxmlin=value;}
    public void setUrixaggcontainerout(java.lang.String value){this.urixaggcontainerout=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgcallbyguiin(java.lang.Integer value){this.flgcallbyguiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    