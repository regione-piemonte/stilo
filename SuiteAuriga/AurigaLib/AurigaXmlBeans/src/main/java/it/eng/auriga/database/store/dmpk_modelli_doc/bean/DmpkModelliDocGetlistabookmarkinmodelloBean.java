/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocGetlistabookmarkinmodelloBean")
public class DmpkModelliDocGetlistabookmarkinmodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_GETLISTABOOKMARKINMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String codapplicazionein;
	private java.lang.String codistapplicazionein;
	private java.math.BigDecimal idmodelloin;
	private java.lang.String nomemodelloin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getCodapplicazionein(){return codapplicazionein;}
    public java.lang.String getCodistapplicazionein(){return codistapplicazionein;}
    public java.math.BigDecimal getIdmodelloin(){return idmodelloin;}
    public java.lang.String getNomemodelloin(){return nomemodelloin;}
    
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setCodapplicazionein(java.lang.String value){this.codapplicazionein=value;}
    public void setCodistapplicazionein(java.lang.String value){this.codistapplicazionein=value;}
    public void setIdmodelloin(java.math.BigDecimal value){this.idmodelloin=value;}
    public void setNomemodelloin(java.lang.String value){this.nomemodelloin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    