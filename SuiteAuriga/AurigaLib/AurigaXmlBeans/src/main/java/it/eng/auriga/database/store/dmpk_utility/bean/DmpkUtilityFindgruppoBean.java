/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindgruppoBean")
public class DmpkUtilityFindgruppoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDGRUPPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idgruppoin;
	private java.lang.String nomegruppoin;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	private java.lang.Integer flgnopercfinalesunomein;
	private java.lang.String provcigruppoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdgruppoin(){return idgruppoin;}
    public java.lang.String getNomegruppoin(){return nomegruppoin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlgnopercfinalesunomein(){return flgnopercfinalesunomein;}
    public java.lang.String getProvcigruppoin(){return provcigruppoin;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdgruppoin(java.math.BigDecimal value){this.idgruppoin=value;}
    public void setNomegruppoin(java.lang.String value){this.nomegruppoin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlgnopercfinalesunomein(java.lang.Integer value){this.flgnopercfinalesunomein=value;}
    public void setProvcigruppoin(java.lang.String value){this.provcigruppoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    