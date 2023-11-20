/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFinduserBean")
public class DmpkUtilityFinduserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioautin;
	private java.math.BigDecimal iduserin;
	private java.lang.String desuserin;
	private java.lang.String usernamein;
	private java.lang.String nromatricolain;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getDesuserin(){return desuserin;}
    public java.lang.String getUsernamein(){return usernamein;}
    public java.lang.String getNromatricolain(){return nromatricolain;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setDesuserin(java.lang.String value){this.desuserin=value;}
    public void setUsernamein(java.lang.String value){this.usernamein=value;}
    public void setNromatricolain(java.lang.String value){this.nromatricolain=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    