/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinduobyliv_jBean")
public class DmpkUtilityFinduobyliv_jBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDUOBYLIV_J";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String livellistrin;
	private java.lang.Integer flgsolovldin;
	private java.math.BigDecimal iduoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getLivellistrin(){return livellistrin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.math.BigDecimal getIduoout(){return iduoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setLivellistrin(java.lang.String value){this.livellistrin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setIduoout(java.math.BigDecimal value){this.iduoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    