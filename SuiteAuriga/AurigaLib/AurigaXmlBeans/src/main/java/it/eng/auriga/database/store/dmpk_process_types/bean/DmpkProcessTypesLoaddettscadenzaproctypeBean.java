/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesLoaddettscadenzaproctypeBean")
public class DmpkProcessTypesLoaddettscadenzaproctypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_LOADDETTSCADENZAPROCTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String rowidio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String nomeprocesstypeout;
	private java.lang.String flgscadpuntualeperiodoout;
	private java.lang.String descrizioneio;
	private java.math.BigDecimal durataout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.String annotazioniout;
	private java.lang.Integer flglockedout;
	private java.lang.String xmlstartout;
	private java.lang.String xmlendout;
	private java.lang.Integer bachsizeout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getRowidio(){return rowidio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getNomeprocesstypeout(){return nomeprocesstypeout;}
    public java.lang.String getFlgscadpuntualeperiodoout(){return flgscadpuntualeperiodoout;}
    public java.lang.String getDescrizioneio(){return descrizioneio;}
    public java.math.BigDecimal getDurataout(){return durataout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.String getAnnotazioniout(){return annotazioniout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getXmlstartout(){return xmlstartout;}
    public java.lang.String getXmlendout(){return xmlendout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setRowidio(java.lang.String value){this.rowidio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setNomeprocesstypeout(java.lang.String value){this.nomeprocesstypeout=value;}
    public void setFlgscadpuntualeperiodoout(java.lang.String value){this.flgscadpuntualeperiodoout=value;}
    public void setDescrizioneio(java.lang.String value){this.descrizioneio=value;}
    public void setDurataout(java.math.BigDecimal value){this.durataout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setAnnotazioniout(java.lang.String value){this.annotazioniout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setXmlstartout(java.lang.String value){this.xmlstartout=value;}
    public void setXmlendout(java.lang.String value){this.xmlendout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    