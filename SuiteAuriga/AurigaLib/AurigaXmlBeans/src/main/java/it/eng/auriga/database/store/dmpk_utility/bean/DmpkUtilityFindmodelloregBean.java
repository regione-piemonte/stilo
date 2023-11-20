/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindmodelloregBean")
public class DmpkUtilityFindmodelloregBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDMODELLOREG";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.lang.String codentitatargetin;
	private java.math.BigDecimal idmodelloregin;
	private java.lang.String nomemodelloregin;
	private java.lang.Integer flgsolovldin;
	private java.lang.Integer flgnopercfinalesunomein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getCodentitatargetin(){return codentitatargetin;}
    public java.math.BigDecimal getIdmodelloregin(){return idmodelloregin;}
    public java.lang.String getNomemodelloregin(){return nomemodelloregin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.Integer getFlgnopercfinalesunomein(){return flgnopercfinalesunomein;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setCodentitatargetin(java.lang.String value){this.codentitatargetin=value;}
    public void setIdmodelloregin(java.math.BigDecimal value){this.idmodelloregin=value;}
    public void setNomemodelloregin(java.lang.String value){this.nomemodelloregin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setFlgnopercfinalesunomein(java.lang.Integer value){this.flgnopercfinalesunomein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    