/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindformatoelammessoBean")
public class DmpkUtilityFindformatoelammessoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDFORMATOELAMMESSO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idformatoelin;
	private java.lang.String nomeformatoelin;
	private java.lang.String estensionein;
	private java.lang.Integer flgsoloammin;
	private java.lang.String tsrifin;
	private java.lang.Integer flgnopercfinalesunomein;
	private java.lang.String mimetypein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdformatoelin(){return idformatoelin;}
    public java.lang.String getNomeformatoelin(){return nomeformatoelin;}
    public java.lang.String getEstensionein(){return estensionein;}
    public java.lang.Integer getFlgsoloammin(){return flgsoloammin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    public java.lang.Integer getFlgnopercfinalesunomein(){return flgnopercfinalesunomein;}
    public java.lang.String getMimetypein(){return mimetypein;}
    
	public void setParametro_1(java.math.BigDecimal value){this.parametro_1=value.intValue();}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdformatoelin(java.math.BigDecimal value){this.idformatoelin=value;}
    public void setNomeformatoelin(java.lang.String value){this.nomeformatoelin=value;}
    public void setEstensionein(java.lang.String value){this.estensionein=value;}
    public void setFlgsoloammin(java.lang.Integer value){this.flgsoloammin=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    public void setFlgnopercfinalesunomein(java.lang.Integer value){this.flgnopercfinalesunomein=value;}
    public void setMimetypein(java.lang.String value){this.mimetypein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    