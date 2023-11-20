/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkGaeVerificadoclottoBean")
public class DmpkGaeVerificadoclottoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_GAE_VERIFICADOCLOTTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idbatchin;
	private java.lang.String idlottoin;
	private java.math.BigDecimal nroposizionedocin;
	private java.lang.String codicierroreout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdbatchin(){return idbatchin;}
    public java.lang.String getIdlottoin(){return idlottoin;}
    public java.math.BigDecimal getNroposizionedocin(){return nroposizionedocin;}
    public java.lang.String getCodicierroreout(){return codicierroreout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdbatchin(java.lang.String value){this.idbatchin=value;}
    public void setIdlottoin(java.lang.String value){this.idlottoin=value;}
    public void setNroposizionedocin(java.math.BigDecimal value){this.nroposizionedocin=value;}
    public void setCodicierroreout(java.lang.String value){this.codicierroreout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    