/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityDreluouserBean")
public class DmpkDefSecurityDreluouserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_DRELUOUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduoin;
	private java.lang.String nrilivelliuoin;
	private java.lang.String denominazioneuoin;
	private java.math.BigDecimal iduserin;
	private java.lang.String desuserin;
	private java.lang.String usernamein;
	private java.lang.String flgtiporelin;
	private java.lang.String dtiniziovldin;
	private java.lang.String idruoloammin;
	private java.lang.String desruoloammin;
	private java.lang.String motiviin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.String getNrilivelliuoin(){return nrilivelliuoin;}
    public java.lang.String getDenominazioneuoin(){return denominazioneuoin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getFlgtiporelin(){return flgtiporelin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getIdruoloammin(){return idruoloammin;}
    public java.lang.String getDesruoloammin(){return desruoloammin;}
    public java.lang.String getMotiviin(){return motiviin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setNrilivelliuoin(java.lang.String value){this.nrilivelliuoin=value;}
    public void setDenominazioneuoin(java.lang.String value){this.denominazioneuoin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setFlgtiporelin(java.lang.String value){this.flgtiporelin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setIdruoloammin(java.lang.String value){this.idruoloammin=value;}
    public void setDesruoloammin(java.lang.String value){this.desruoloammin=value;}
    public void setMotiviin(java.lang.String value){this.motiviin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    