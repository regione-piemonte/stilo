/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityAdddelegavsuserBean")
public class DmpkDefSecurityAdddelegavsuserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_ADDDELEGAVSUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal delda_iduserin;
	private java.lang.String delda_desuserin;
	private java.lang.String delda_usernamein;
	private java.math.BigDecimal delvs_iduserin;
	private java.lang.String delvs_desuserin;
	private java.lang.String delvs_usernamein;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.String naturamotiviin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getDelda_iduserin(){return delda_iduserin;}
    public java.lang.String getDelda_desuserin(){return delda_desuserin;}
    public java.lang.String getDelda_usernamein(){return delda_usernamein;}
    public java.math.BigDecimal getDelvs_iduserin(){return delvs_iduserin;}
    public java.lang.String getDelvs_desuserin(){return delvs_desuserin;}
    public java.lang.String getDelvs_usernamein(){return delvs_usernamein;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.String getNaturamotiviin(){return naturamotiviin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setDelda_iduserin(java.math.BigDecimal value){this.delda_iduserin=value;}
    public void setDelda_desuserin(java.lang.String value){this.delda_desuserin=value;}
    public void setDelda_usernamein(java.lang.String value){this.delda_usernamein=value;}
    public void setDelvs_iduserin(java.math.BigDecimal value){this.delvs_iduserin=value;}
    public void setDelvs_desuserin(java.lang.String value){this.delvs_desuserin=value;}
    public void setDelvs_usernamein(java.lang.String value){this.delvs_usernamein=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setNaturamotiviin(java.lang.String value){this.naturamotiviin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    