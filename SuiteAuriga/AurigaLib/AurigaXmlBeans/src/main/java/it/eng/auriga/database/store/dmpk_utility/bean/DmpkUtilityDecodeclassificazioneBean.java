/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityDecodeclassificazioneBean")
public class DmpkUtilityDecodeclassificazioneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_DECODECLASSIFICAZIONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idspaooin;
	private java.math.BigDecimal idclassificazionein;
	private java.lang.String tipodecodificain;
	private java.lang.String tsrifin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.math.BigDecimal getIdclassificazionein(){return idclassificazionein;}
    public java.lang.String getTipodecodificain(){return tipodecodificain;}
    public java.lang.String getTsrifin(){return tsrifin;}
    
	public void setParametro_1(java.lang.String value){this.parametro_1=Integer.valueOf(value);}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setIdclassificazionein(java.math.BigDecimal value){this.idclassificazionein=value;}
    public void setTipodecodificain(java.lang.String value){this.tipodecodificain=value;}
    public void setTsrifin(java.lang.String value){this.tsrifin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    