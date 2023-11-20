/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRuoliAmmLoaddettruoloammBean")
public class DmpkRuoliAmmLoaddettruoloammBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_RUOLI_AMM_LOADDETTRUOLOAMM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idruoloio;
	private java.lang.String desruoloout;
	private java.math.BigDecimal flglimuoout;
	private java.lang.String ciprovruoloout;
	private java.lang.Integer flglockedout;
	private java.lang.String rowidout;
	private java.lang.String xmlruoliinclusiout;
	private java.lang.Integer bachsizeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdruoloio(){return idruoloio;}
    public java.lang.String getDesruoloout(){return desruoloout;}
    public java.math.BigDecimal getFlglimuoout(){return flglimuoout;}
    public java.lang.String getCiprovruoloout(){return ciprovruoloout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getXmlruoliinclusiout(){return xmlruoliinclusiout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdruoloio(java.math.BigDecimal value){this.idruoloio=value;}
    public void setDesruoloout(java.lang.String value){this.desruoloout=value;}
    public void setFlglimuoout(java.math.BigDecimal value){this.flglimuoout=value;}
    public void setCiprovruoloout(java.lang.String value){this.ciprovruoloout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setXmlruoliinclusiout(java.lang.String value){this.xmlruoliinclusiout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    