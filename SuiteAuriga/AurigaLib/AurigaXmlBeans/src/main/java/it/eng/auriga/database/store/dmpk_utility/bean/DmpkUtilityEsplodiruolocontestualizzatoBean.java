/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityEsplodiruolocontestualizzatoBean")
public class DmpkUtilityEsplodiruolocontestualizzatoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_ESPLODIRUOLOCONTESTUALIZZATO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idruoloammin;
	private java.lang.String flgtpobjtoextractin;
	private java.math.BigDecimal iddominioin;
	private java.math.BigDecimal livuolimruoloin;
	private java.lang.String codtipouolimruoloin;
	private java.math.BigDecimal iduorifruoloin;
	private java.lang.Integer flginclsottouoin;
	private java.lang.String tsvldin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdruoloammin(){return idruoloammin;}
    public java.lang.String getFlgtpobjtoextractin(){return flgtpobjtoextractin;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.math.BigDecimal getLivuolimruoloin(){return livuolimruoloin;}
    public java.lang.String getCodtipouolimruoloin(){return codtipouolimruoloin;}
    public java.math.BigDecimal getIduorifruoloin(){return iduorifruoloin;}
    public java.lang.Integer getFlginclsottouoin(){return flginclsottouoin;}
    public java.lang.String getTsvldin(){return tsvldin;}
    
    public void setIdruoloammin(java.math.BigDecimal value){this.idruoloammin=value;}
    public void setFlgtpobjtoextractin(java.lang.String value){this.flgtpobjtoextractin=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setLivuolimruoloin(java.math.BigDecimal value){this.livuolimruoloin=value;}
    public void setCodtipouolimruoloin(java.lang.String value){this.codtipouolimruoloin=value;}
    public void setIduorifruoloin(java.math.BigDecimal value){this.iduorifruoloin=value;}
    public void setFlginclsottouoin(java.lang.Integer value){this.flginclsottouoin=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    