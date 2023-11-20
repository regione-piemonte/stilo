/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestaclattivitaprocgrantaccessBean")
public class DmpkPolicyTestaclattivitaprocgrantaccessBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTACLATTIVITAPROCGRANTACCESS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduserin;
	private java.math.BigDecimal idprocessin;
	private java.lang.String faseprocessnamein;
	private java.lang.String activitynamein;
	private java.lang.Object aclin;
	private java.lang.String accesstypein;
	private java.lang.String motivononeseguibileout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getFaseprocessnamein(){return faseprocessnamein;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.Object getAclin(){return aclin;}
    public java.lang.String getAccesstypein(){return accesstypein;}
    public java.lang.String getMotivononeseguibileout(){return motivononeseguibileout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setFaseprocessnamein(java.lang.String value){this.faseprocessnamein=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setAclin(java.lang.Object value){this.aclin=value;}
    public void setAccesstypein(java.lang.String value){this.accesstypein=value;}
    public void setMotivononeseguibileout(java.lang.String value){this.motivononeseguibileout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    