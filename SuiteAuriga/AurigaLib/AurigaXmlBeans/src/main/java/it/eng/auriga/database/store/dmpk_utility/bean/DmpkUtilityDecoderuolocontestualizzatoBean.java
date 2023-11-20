/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityDecoderuolocontestualizzatoBean")
public class DmpkUtilityDecoderuolocontestualizzatoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_DECODERUOLOCONTESTUALIZZATO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idruoloammin;
	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal vslivellosoin;
	private java.lang.String vscodtipouoin;
	private java.math.BigDecimal vsiduoin;
	private java.lang.Integer flginclsottouoin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdruoloammin(){return idruoloammin;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getVslivellosoin(){return vslivellosoin;}
    public java.lang.String getVscodtipouoin(){return vscodtipouoin;}
    public java.math.BigDecimal getVsiduoin(){return vsiduoin;}
    public java.lang.Integer getFlginclsottouoin(){return flginclsottouoin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIdruoloammin(java.math.BigDecimal value){this.idruoloammin=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setVslivellosoin(java.math.BigDecimal value){this.vslivellosoin=value;}
    public void setVscodtipouoin(java.lang.String value){this.vscodtipouoin=value;}
    public void setVsiduoin(java.math.BigDecimal value){this.vsiduoin=value;}
    public void setFlginclsottouoin(java.lang.Integer value){this.flginclsottouoin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    