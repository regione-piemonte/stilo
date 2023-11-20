/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaEsplodigruppoBean")
public class DmpkAnagraficaEsplodigruppoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_ESPLODIGRUPPO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal idspappin;
	private java.math.BigDecimal idgruppoin;
	private java.lang.String tpobjtoextractin;
	private java.lang.String tsvldin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getIdspappin(){return idspappin;}
    public java.math.BigDecimal getIdgruppoin(){return idgruppoin;}
    public java.lang.String getTpobjtoextractin(){return tpobjtoextractin;}
    public java.lang.String getTsvldin(){return tsvldin;}
    
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setIdspappin(java.math.BigDecimal value){this.idspappin=value;}
    public void setIdgruppoin(java.math.BigDecimal value){this.idgruppoin=value;}
    public void setTpobjtoextractin(java.lang.String value){this.tpobjtoextractin=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    