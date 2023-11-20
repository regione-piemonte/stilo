/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetindirizzosoggrubricaBean")
public class DmpkUtilityGetindirizzosoggrubricaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETINDIRIZZOSOGGRUBRICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idsoggrubricain;
	private java.lang.String codtipoindin;
	private java.lang.Integer flgultimoin;
	private java.lang.String tsvldin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdsoggrubricain(){return idsoggrubricain;}
    public java.lang.String getCodtipoindin(){return codtipoindin;}
    public java.lang.Integer getFlgultimoin(){return flgultimoin;}
    public java.lang.String getTsvldin(){return tsvldin;}
    
    public void setIdsoggrubricain(java.math.BigDecimal value){this.idsoggrubricain=value;}
    public void setCodtipoindin(java.lang.String value){this.codtipoindin=value;}
    public void setFlgultimoin(java.lang.Integer value){this.flgultimoin=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    