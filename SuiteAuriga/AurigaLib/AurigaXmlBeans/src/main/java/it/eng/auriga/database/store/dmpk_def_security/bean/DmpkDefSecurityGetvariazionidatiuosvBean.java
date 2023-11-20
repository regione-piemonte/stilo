/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityGetvariazionidatiuosvBean")
public class DmpkDefSecurityGetvariazionidatiuosvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_GETVARIAZIONIDATIUOSV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String flguosvin;
	private java.math.BigDecimal iduosvin;
	private java.lang.String variazionidalin;
	private java.lang.String variazionialin;
	private java.lang.String variazionimlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getFlguosvin(){return flguosvin;}
    public java.math.BigDecimal getIduosvin(){return iduosvin;}
    public java.lang.String getVariazionidalin(){return variazionidalin;}
    public java.lang.String getVariazionialin(){return variazionialin;}
    public java.lang.String getVariazionimlout(){return variazionimlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlguosvin(java.lang.String value){this.flguosvin=value;}
    public void setIduosvin(java.math.BigDecimal value){this.iduosvin=value;}
    public void setVariazionidalin(java.lang.String value){this.variazionidalin=value;}
    public void setVariazionialin(java.lang.String value){this.variazionialin=value;}
    public void setVariazionimlout(java.lang.String value){this.variazionimlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    