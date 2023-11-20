/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestacludgrantaccessBean")
public class DmpkPolicyTestacludgrantaccessBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTACLUDGRANTACCESS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idspaooin;
	private java.lang.Object aclin;
	private java.lang.String accesstypein;
	private java.math.BigDecimal iddoctargetin;
	private java.lang.Integer nrovertargetin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.Object getAclin(){return aclin;}
    public java.lang.String getAccesstypein(){return accesstypein;}
    public java.math.BigDecimal getIddoctargetin(){return iddoctargetin;}
    public java.lang.Integer getNrovertargetin(){return nrovertargetin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setAclin(java.lang.Object value){this.aclin=value;}
    public void setAccesstypein(java.lang.String value){this.accesstypein=value;}
    public void setIddoctargetin(java.math.BigDecimal value){this.iddoctargetin=value;}
    public void setNrovertargetin(java.lang.Integer value){this.nrovertargetin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    