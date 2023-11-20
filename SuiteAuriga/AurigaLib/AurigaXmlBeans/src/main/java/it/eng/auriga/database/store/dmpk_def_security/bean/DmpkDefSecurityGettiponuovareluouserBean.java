/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityGettiponuovareluouserBean")
public class DmpkDefSecurityGettiponuovareluouserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_GETTIPONUOVARELUOUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iduoin;
	private java.math.BigDecimal iduserin;
	private java.lang.String dtiniziovldin;
	private java.lang.String cireluseruotosubstin;
	private java.lang.String flgtiporelout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getDtiniziovldin(){return dtiniziovldin;}
    public java.lang.String getCireluseruotosubstin(){return cireluseruotosubstin;}
    public java.lang.String getFlgtiporelout(){return flgtiporelout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setDtiniziovldin(java.lang.String value){this.dtiniziovldin=value;}
    public void setCireluseruotosubstin(java.lang.String value){this.cireluseruotosubstin=value;}
    public void setFlgtiporelout(java.lang.String value){this.flgtiporelout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    