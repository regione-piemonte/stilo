/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesLoaddettattributoproctypeBean")
public class DmpkProcessTypesLoaddettattributoproctypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_LOADDETTATTRIBUTOPROCTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String rowidio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String nomeprocesstypeout;
	private java.lang.String nomeio;
	private java.lang.String labelout;
	private java.lang.Integer nroposizioneout;
	private java.lang.Integer flgobbligout;
	private java.math.BigDecimal maxnumvaluesout;
	private java.lang.String dtiniziovldout;
	private java.lang.String dtfinevldout;
	private java.lang.Integer flglockedout;
	private java.lang.String xmlcontrolliout;
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
    public java.lang.String getNomeio(){return nomeio;}
    public java.lang.String getLabelout(){return labelout;}
    public java.lang.Integer getNroposizioneout(){return nroposizioneout;}
    public java.lang.Integer getFlgobbligout(){return flgobbligout;}
    public java.math.BigDecimal getMaxnumvaluesout(){return maxnumvaluesout;}
    public java.lang.String getDtiniziovldout(){return dtiniziovldout;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.Integer getFlglockedout(){return flglockedout;}
    public java.lang.String getXmlcontrolliout(){return xmlcontrolliout;}
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
    public void setNomeio(java.lang.String value){this.nomeio=value;}
    public void setLabelout(java.lang.String value){this.labelout=value;}
    public void setNroposizioneout(java.lang.Integer value){this.nroposizioneout=value;}
    public void setFlgobbligout(java.lang.Integer value){this.flgobbligout=value;}
    public void setMaxnumvaluesout(java.math.BigDecimal value){this.maxnumvaluesout=value;}
    public void setDtiniziovldout(java.lang.String value){this.dtiniziovldout=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setFlglockedout(java.lang.Integer value){this.flglockedout=value;}
    public void setXmlcontrolliout(java.lang.String value){this.xmlcontrolliout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    