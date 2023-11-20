/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCoreAddtorecentBean")
public class DmpkCoreAddtorecentBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_ADDTORECENT";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.math.BigDecimal iduserin;
	private java.lang.String flgtipoobjin;
	private java.math.BigDecimal idobjin;
	private java.lang.String flgvlin;
	private java.lang.String motiviin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal iddocdownloadedin;
	private java.lang.Integer nroverdownloadedin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getFlgtipoobjin(){return flgtipoobjin;}
    public java.math.BigDecimal getIdobjin(){return idobjin;}
    public java.lang.String getFlgvlin(){return flgvlin;}
    public java.lang.String getMotiviin(){return motiviin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIddocdownloadedin(){return iddocdownloadedin;}
    public java.lang.Integer getNroverdownloadedin(){return nroverdownloadedin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpdominioautin(java.lang.Integer value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setFlgtipoobjin(java.lang.String value){this.flgtipoobjin=value;}
    public void setIdobjin(java.math.BigDecimal value){this.idobjin=value;}
    public void setFlgvlin(java.lang.String value){this.flgvlin=value;}
    public void setMotiviin(java.lang.String value){this.motiviin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIddocdownloadedin(java.math.BigDecimal value){this.iddocdownloadedin=value;}
    public void setNroverdownloadedin(java.lang.Integer value){this.nroverdownloadedin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    