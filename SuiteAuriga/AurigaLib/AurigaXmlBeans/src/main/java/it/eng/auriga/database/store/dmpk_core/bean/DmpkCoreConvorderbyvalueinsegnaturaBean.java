/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreConvorderbyvalueinsegnaturaBean")
public class DmpkCoreConvorderbyvalueinsegnaturaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_CONVORDERBYVALUEINSEGNATURA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String orderbyvaluein;
	private java.math.BigDecimal idpianoclassifin;
	private java.math.BigDecimal idpianodeflivellitittabin;
	private java.lang.Object deflivellitittabio;
	private java.lang.String seplivelliin;
	private java.lang.Integer flgpaddinglivtitin;
	private java.lang.Integer lengthnrolivelloin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getOrderbyvaluein(){return orderbyvaluein;}
    public java.math.BigDecimal getIdpianoclassifin(){return idpianoclassifin;}
    public java.math.BigDecimal getIdpianodeflivellitittabin(){return idpianodeflivellitittabin;}
    public java.lang.Object getDeflivellitittabio(){return deflivellitittabio;}
    public java.lang.String getSeplivelliin(){return seplivelliin;}
    public java.lang.Integer getFlgpaddinglivtitin(){return flgpaddinglivtitin;}
    public java.lang.Integer getLengthnrolivelloin(){return lengthnrolivelloin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setOrderbyvaluein(java.lang.String value){this.orderbyvaluein=value;}
    public void setIdpianoclassifin(java.math.BigDecimal value){this.idpianoclassifin=value;}
    public void setIdpianodeflivellitittabin(java.math.BigDecimal value){this.idpianodeflivellitittabin=value;}
    public void setDeflivellitittabio(java.lang.Object value){this.deflivellitittabio=value;}
    public void setSeplivelliin(java.lang.String value){this.seplivelliin=value;}
    public void setFlgpaddinglivtitin(java.lang.Integer value){this.flgpaddinglivtitin=value;}
    public void setLengthnrolivelloin(java.lang.Integer value){this.lengthnrolivelloin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    