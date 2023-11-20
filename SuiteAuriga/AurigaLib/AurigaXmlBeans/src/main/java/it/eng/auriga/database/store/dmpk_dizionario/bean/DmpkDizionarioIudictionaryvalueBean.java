/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDizionarioIudictionaryvalueBean")
public class DmpkDizionarioIudictionaryvalueBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DIZIONARIO_IUDICTIONARYVALUE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String dictionaryentryin;
	private java.lang.String oldvaluein;
	private java.lang.String valuein;
	private java.lang.String codvaluein;
	private java.lang.String meaningin;
	private java.lang.String valuegenvincoloin;
	private java.lang.String dtiniziovldin;
	private java.lang.String dtfinevldin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal iduoin;
	private java.lang.Integer flgvisibsottouoin;
	private java.lang.Integer flggestsottouoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getDictionaryentryin(){return dictionaryentryin;}
    public java.lang.String getOldvaluein(){return oldvaluein;}
    public java.lang.String getValuein(){return valuein;}
    public java.lang.String getCodvaluein(){return codvaluein;}
    public java.lang.String getMeaningin(){return meaningin;}
    public java.lang.String getValuegenvincoloin(){return valuegenvincoloin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getDtfinevldin(){return dtfinevldin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.Integer getFlgvisibsottouoin(){return flgvisibsottouoin;}
    public java.lang.Integer getFlggestsottouoin(){return flggestsottouoin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setDictionaryentryin(java.lang.String value){this.dictionaryentryin=value;}
    public void setOldvaluein(java.lang.String value){this.oldvaluein=value;}
    public void setValuein(java.lang.String value){this.valuein=value;}
    public void setCodvaluein(java.lang.String value){this.codvaluein=value;}
    public void setMeaningin(java.lang.String value){this.meaningin=value;}
    public void setValuegenvincoloin(java.lang.String value){this.valuegenvincoloin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setDtfinevldin(java.lang.String value){this.dtfinevldin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setFlgvisibsottouoin(java.lang.Integer value){this.flgvisibsottouoin=value;}
    public void setFlggestsottouoin(java.lang.Integer value){this.flggestsottouoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    