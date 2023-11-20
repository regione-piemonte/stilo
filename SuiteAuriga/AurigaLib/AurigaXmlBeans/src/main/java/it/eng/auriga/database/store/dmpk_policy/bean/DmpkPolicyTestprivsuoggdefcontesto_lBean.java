/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestprivsuoggdefcontesto_lBean")
public class DmpkPolicyTestprivsuoggdefcontesto_lBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTPRIVSUOGGDEFCONTESTO_L";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flgtpobjprivonin;
	private java.lang.String ciobjprivonin;
	private java.lang.String flgtpobjprivtoin;
	private java.math.BigDecimal idobjprivtoin;
	private java.lang.Integer flgtpdominioin;
	private java.math.BigDecimal iddominioin;
	private java.lang.String opzprivilegioin;
	private java.math.BigDecimal idspappin;
	private java.lang.Object uocollegsvutentetabin;
	private java.lang.Object svcollegutentetabin;
	private java.lang.Object ruolitabin;
	private java.math.BigDecimal idprofiloin;
	private java.lang.Object gruppiprivtabin;
	private java.lang.Integer flgrichabilxvisualizzin;
	private java.lang.Integer flgrichabilxtrattin;
	private java.lang.Integer flgrichabilxassegnin;
	private java.lang.Integer flgrichabilxfirmain;
	private java.lang.Integer flgrichabilxapfascin;
	private java.math.BigDecimal idobjprivonsupin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlgtpobjprivonin(){return flgtpobjprivonin;}
    public java.lang.String getCiobjprivonin(){return ciobjprivonin;}
    public java.lang.String getFlgtpobjprivtoin(){return flgtpobjprivtoin;}
    public java.math.BigDecimal getIdobjprivtoin(){return idobjprivtoin;}
    public java.lang.Integer getFlgtpdominioin(){return flgtpdominioin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getOpzprivilegioin(){return opzprivilegioin;}
    public java.math.BigDecimal getIdspappin(){return idspappin;}
    public java.lang.Object getUocollegsvutentetabin(){return uocollegsvutentetabin;}
    public java.lang.Object getSvcollegutentetabin(){return svcollegutentetabin;}
    public java.lang.Object getRuolitabin(){return ruolitabin;}
    public java.math.BigDecimal getIdprofiloin(){return idprofiloin;}
    public java.lang.Object getGruppiprivtabin(){return gruppiprivtabin;}
    public java.lang.Integer getFlgrichabilxvisualizzin(){return flgrichabilxvisualizzin;}
    public java.lang.Integer getFlgrichabilxtrattin(){return flgrichabilxtrattin;}
    public java.lang.Integer getFlgrichabilxassegnin(){return flgrichabilxassegnin;}
    public java.lang.Integer getFlgrichabilxfirmain(){return flgrichabilxfirmain;}
    public java.lang.Integer getFlgrichabilxapfascin(){return flgrichabilxapfascin;}
    public java.math.BigDecimal getIdobjprivonsupin(){return idobjprivonsupin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpobjprivonin(java.lang.String value){this.flgtpobjprivonin=value;}
    public void setCiobjprivonin(java.lang.String value){this.ciobjprivonin=value;}
    public void setFlgtpobjprivtoin(java.lang.String value){this.flgtpobjprivtoin=value;}
    public void setIdobjprivtoin(java.math.BigDecimal value){this.idobjprivtoin=value;}
    public void setFlgtpdominioin(java.lang.Integer value){this.flgtpdominioin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setOpzprivilegioin(java.lang.String value){this.opzprivilegioin=value;}
    public void setIdspappin(java.math.BigDecimal value){this.idspappin=value;}
    public void setUocollegsvutentetabin(java.lang.Object value){this.uocollegsvutentetabin=value;}
    public void setSvcollegutentetabin(java.lang.Object value){this.svcollegutentetabin=value;}
    public void setRuolitabin(java.lang.Object value){this.ruolitabin=value;}
    public void setIdprofiloin(java.math.BigDecimal value){this.idprofiloin=value;}
    public void setGruppiprivtabin(java.lang.Object value){this.gruppiprivtabin=value;}
    public void setFlgrichabilxvisualizzin(java.lang.Integer value){this.flgrichabilxvisualizzin=value;}
    public void setFlgrichabilxtrattin(java.lang.Integer value){this.flgrichabilxtrattin=value;}
    public void setFlgrichabilxassegnin(java.lang.Integer value){this.flgrichabilxassegnin=value;}
    public void setFlgrichabilxfirmain(java.lang.Integer value){this.flgrichabilxfirmain=value;}
    public void setFlgrichabilxapfascin(java.lang.Integer value){this.flgrichabilxapfascin=value;}
    public void setIdobjprivonsupin(java.math.BigDecimal value){this.idobjprivonsupin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    