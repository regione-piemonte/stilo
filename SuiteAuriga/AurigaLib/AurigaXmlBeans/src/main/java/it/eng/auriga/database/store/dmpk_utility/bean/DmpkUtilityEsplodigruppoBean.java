/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityEsplodigruppoBean")
public class DmpkUtilityEsplodigruppoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_ESPLODIGRUPPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idgruppoin;
	private java.lang.String lsttpobjtoextractin;
	private java.lang.String tsvldin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdgruppoin(){return idgruppoin;}
    public java.lang.String getLsttpobjtoextractin(){return lsttpobjtoextractin;}
    public java.lang.String getTsvldin(){return tsvldin;}
    
    public void setIdgruppoin(java.math.BigDecimal value){this.idgruppoin=value;}
    public void setLsttpobjtoextractin(java.lang.String value){this.lsttpobjtoextractin=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    