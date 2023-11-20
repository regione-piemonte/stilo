/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindintopograficoBean")
public class DmpkUtilityFindintopograficoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDINTOPOGRAFICO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String datitoponimoxmlio;
	private java.lang.Integer flgsolovldin;
	private java.lang.Integer flgcompletadatiin;
	private java.math.BigDecimal idtoponimoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getDatitoponimoxmlio(){return datitoponimoxmlio;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.Integer getFlgcompletadatiin(){return flgcompletadatiin;}
    public java.math.BigDecimal getIdtoponimoout(){return idtoponimoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setDatitoponimoxmlio(java.lang.String value){this.datitoponimoxmlio=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setFlgcompletadatiin(java.lang.Integer value){this.flgcompletadatiin=value;}
    public void setIdtoponimoout(java.math.BigDecimal value){this.idtoponimoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    