/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocFindmodelloBean")
public class DmpkModelliDocFindmodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_FINDMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String codapplownerin;
	private java.lang.String codistapplownerin;
	private java.math.BigDecimal flgrestrapplownerin;
	private java.math.BigDecimal idmodelloin;
	private java.lang.String nomemodelloin;
	private java.math.BigDecimal idformatoelin;
	private java.lang.String nomeformatoelin;
	private java.lang.String estensioneformatoelin;
	private java.lang.Integer flgsolovldin;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getCodapplownerin(){return codapplownerin;}
    public java.lang.String getCodistapplownerin(){return codistapplownerin;}
    public java.math.BigDecimal getFlgrestrapplownerin(){return flgrestrapplownerin;}
    public java.math.BigDecimal getIdmodelloin(){return idmodelloin;}
    public java.lang.String getNomemodelloin(){return nomemodelloin;}
    public java.math.BigDecimal getIdformatoelin(){return idformatoelin;}
    public java.lang.String getNomeformatoelin(){return nomeformatoelin;}
    public java.lang.String getEstensioneformatoelin(){return estensioneformatoelin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setCodapplownerin(java.lang.String value){this.codapplownerin=value;}
    public void setCodistapplownerin(java.lang.String value){this.codistapplownerin=value;}
    public void setFlgrestrapplownerin(java.math.BigDecimal value){this.flgrestrapplownerin=value;}
    public void setIdmodelloin(java.math.BigDecimal value){this.idmodelloin=value;}
    public void setNomemodelloin(java.lang.String value){this.nomemodelloin=value;}
    public void setIdformatoelin(java.math.BigDecimal value){this.idformatoelin=value;}
    public void setNomeformatoelin(java.lang.String value){this.nomeformatoelin=value;}
    public void setEstensioneformatoelin(java.lang.String value){this.estensioneformatoelin=value;}
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