/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestvisibinchiarofolderBean")
public class DmpkPolicyTestvisibinchiarofolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTVISIBINCHIAROFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioautin;
	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idfolderin;
	private java.lang.String tipoidrespfolderin;
	private java.lang.String flgvisibinchiaroin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdfolderin(){return idfolderin;}
    public java.lang.String getTipoidrespfolderin(){return tipoidrespfolderin;}
    public java.lang.String getFlgvisibinchiaroin(){return flgvisibinchiaroin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdfolderin(java.math.BigDecimal value){this.idfolderin=value;}
    public void setTipoidrespfolderin(java.lang.String value){this.tipoidrespfolderin=value;}
    public void setFlgvisibinchiaroin(java.lang.String value){this.flgvisibinchiaroin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    