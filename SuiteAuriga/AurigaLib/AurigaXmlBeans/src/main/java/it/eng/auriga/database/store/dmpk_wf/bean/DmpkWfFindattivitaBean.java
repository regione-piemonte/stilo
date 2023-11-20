/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfFindattivitaBean")
public class DmpkWfFindattivitaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_FINDATTIVITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String citypeflussowfio;
	private java.lang.String nometypeflussowfin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String flgtipoattivitain;
	private java.lang.String activitynamein;
	private java.lang.String displaynameattin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getCitypeflussowfio(){return citypeflussowfio;}
    public java.lang.String getNometypeflussowfin(){return nometypeflussowfin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getFlgtipoattivitain(){return flgtipoattivitain;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getDisplaynameattin(){return displaynameattin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setCitypeflussowfio(java.lang.String value){this.citypeflussowfio=value;}
    public void setNometypeflussowfin(java.lang.String value){this.nometypeflussowfin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setFlgtipoattivitain(java.lang.String value){this.flgtipoattivitain=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setDisplaynameattin(java.lang.String value){this.displaynameattin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    