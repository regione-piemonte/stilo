/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfDecodeesitoattivitaBean")
public class DmpkWfDecodeesitoattivitaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_DECODEESITOATTIVITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String citypeflussowfio;
	private java.math.BigDecimal idprocessin;
	private java.lang.String activitynamein;
	private java.lang.String codesitoin;
	private java.math.BigDecimal iddominioin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCitypeflussowfio(){return citypeflussowfio;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getCodesitoin(){return codesitoin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setCitypeflussowfio(java.lang.String value){this.citypeflussowfio=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setCodesitoin(java.lang.String value){this.codesitoin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    