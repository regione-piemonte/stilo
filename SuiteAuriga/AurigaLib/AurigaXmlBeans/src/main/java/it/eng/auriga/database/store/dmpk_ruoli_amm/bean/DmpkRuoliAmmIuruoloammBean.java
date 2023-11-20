/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRuoliAmmIuruoloammBean")
public class DmpkRuoliAmmIuruoloammBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_RUOLI_AMM_IURUOLOAMM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idruoloio;
	private java.lang.String desruoloin;
	private java.lang.Integer flglimuoin;
	private java.lang.String ciprovruoloin;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodruoliinclusiin;
	private java.lang.String xmlruoliinclusiin;
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
    public java.math.BigDecimal getIdruoloio(){return idruoloio;}
    public java.lang.String getDesruoloin(){return desruoloin;}
    public java.lang.Integer getFlglimuoin(){return flglimuoin;}
    public java.lang.String getCiprovruoloin(){return ciprovruoloin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodruoliinclusiin(){return flgmodruoliinclusiin;}
    public java.lang.String getXmlruoliinclusiin(){return xmlruoliinclusiin;}
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
    public void setIdruoloio(java.math.BigDecimal value){this.idruoloio=value;}
    public void setDesruoloin(java.lang.String value){this.desruoloin=value;}
    public void setFlglimuoin(java.lang.Integer value){this.flglimuoin=value;}
    public void setCiprovruoloin(java.lang.String value){this.ciprovruoloin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodruoliinclusiin(java.lang.String value){this.flgmodruoliinclusiin=value;}
    public void setXmlruoliinclusiin(java.lang.String value){this.xmlruoliinclusiin=value;}
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