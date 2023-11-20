/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDizionarioLoaddettdictionaryvalueBean")
public class DmpkDizionarioLoaddettdictionaryvalueBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DIZIONARIO_LOADDETTDICTIONARYVALUE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String dictionaryentryio;
	private java.lang.String valueio;
	private java.lang.String codvalueio;
	private java.lang.Integer flgcodobbligatorioout;
	private java.lang.String meaningout;
	private java.lang.String valuegenvincoloout;
	private java.lang.String dictentryvincoloout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.Integer flglockedout;
	private java.lang.Integer flgvaluereferencedout;
	private java.lang.Integer flgcodvalreferencedout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal iduoout;
	private java.lang.String livelliuoout;
	private java.lang.String desuoout;
	private java.lang.Integer flgvisibsottouoout;
	private java.lang.Integer flggestsottouoout;
	private java.lang.Integer flgabilmodificaout;
	private java.lang.Integer flgabileliminazioneout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getDictionaryentryio(){return dictionaryentryio;}
    public java.lang.String getValueio(){return valueio;}
    public java.lang.String getCodvalueio(){return codvalueio;}
    public java.lang.Integer getFlgcodobbligatorioout(){return flgcodobbligatorioout;}
    public java.lang.String getMeaningout(){return meaningout;}
    public java.lang.String getValuegenvincoloout(){return valuegenvincoloout;}
    public java.lang.String getDictentryvincoloout(){return dictentryvincoloout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.Integer getFlgvaluereferencedout(){return flgvaluereferencedout;}
    public java.lang.Integer getFlgcodvalreferencedout(){return flgcodvalreferencedout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIduoout(){return iduoout;}
    public java.lang.String getLivelliuoout(){return livelliuoout;}
    public java.lang.String getDesuoout(){return desuoout;}
    public java.lang.Integer getFlgvisibsottouoout(){return flgvisibsottouoout;}
    public java.lang.Integer getFlggestsottouoout(){return flggestsottouoout;}
    public java.lang.Integer getFlgabilmodificaout(){return flgabilmodificaout;}
    public java.lang.Integer getFlgabileliminazioneout(){return flgabileliminazioneout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setDictionaryentryio(java.lang.String value){this.dictionaryentryio=value;}
    public void setValueio(java.lang.String value){this.valueio=value;}
    public void setCodvalueio(java.lang.String value){this.codvalueio=value;}
    public void setFlgcodobbligatorioout(java.lang.Integer value){this.flgcodobbligatorioout=value;}
    public void setMeaningout(java.lang.String value){this.meaningout=value;}
    public void setValuegenvincoloout(java.lang.String value){this.valuegenvincoloout=value;}
    public void setDictentryvincoloout(java.lang.String value){this.dictentryvincoloout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setFlgvaluereferencedout(java.lang.Integer value){this.flgvaluereferencedout=value;}
    public void setFlgcodvalreferencedout(java.lang.Integer value){this.flgcodvalreferencedout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIduoout(java.math.BigDecimal value){this.iduoout=value;}
    public void setLivelliuoout(java.lang.String value){this.livelliuoout=value;}
    public void setDesuoout(java.lang.String value){this.desuoout=value;}
    public void setFlgvisibsottouoout(java.lang.Integer value){this.flgvisibsottouoout=value;}
    public void setFlggestsottouoout(java.lang.Integer value){this.flggestsottouoout=value;}
    public void setFlgabilmodificaout(java.lang.Integer value){this.flgabilmodificaout=value;}
    public void setFlgabileliminazioneout(java.lang.Integer value){this.flgabileliminazioneout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    