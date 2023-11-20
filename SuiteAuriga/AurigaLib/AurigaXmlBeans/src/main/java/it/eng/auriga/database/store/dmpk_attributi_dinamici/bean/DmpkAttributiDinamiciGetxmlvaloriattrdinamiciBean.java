/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciGetxmlvaloriattrdinamiciBean")
public class DmpkAttributiDinamiciGetxmlvaloriattrdinamiciBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_GETXMLVALORIATTRDINAMICI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nometabellain;
	private java.lang.String rowidin;
	private java.lang.String nomeattrtagin;
	private java.lang.String attributiaddxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgnomeattrconsuffin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNometabellain(){return nometabellain;}
    public java.lang.String getRowidin(){return rowidin;}
    public java.lang.String getNomeattrtagin(){return nomeattrtagin;}
    public java.lang.String getAttributiaddxmlout(){return attributiaddxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgnomeattrconsuffin(){return flgnomeattrconsuffin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNometabellain(java.lang.String value){this.nometabellain=value;}
    public void setRowidin(java.lang.String value){this.rowidin=value;}
    public void setNomeattrtagin(java.lang.String value){this.nomeattrtagin=value;}
    public void setAttributiaddxmlout(java.lang.String value){this.attributiaddxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgnomeattrconsuffin(java.lang.Integer value){this.flgnomeattrconsuffin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    