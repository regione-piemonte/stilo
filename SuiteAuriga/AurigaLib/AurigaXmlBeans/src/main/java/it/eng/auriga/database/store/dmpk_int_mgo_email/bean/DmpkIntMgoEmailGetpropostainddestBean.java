/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailGetpropostainddestBean")
public class DmpkIntMgoEmailGetpropostainddestBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_GETPROPOSTAINDDEST";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String destinatariio;
	private java.lang.String restringipecpeoin;
	private java.lang.String propostaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String flgdestinterniesterniin;
	private java.math.BigDecimal idsoggrubricain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getDestinatariio(){return destinatariio;}
    public java.lang.String getRestringipecpeoin(){return restringipecpeoin;}
    public java.lang.String getPropostaxmlout(){return propostaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFlgdestinterniesterniin(){return flgdestinterniesterniin;}
    public java.math.BigDecimal getIdsoggrubricain(){return idsoggrubricain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setDestinatariio(java.lang.String value){this.destinatariio=value;}
    public void setRestringipecpeoin(java.lang.String value){this.restringipecpeoin=value;}
    public void setPropostaxmlout(java.lang.String value){this.propostaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgdestinterniesterniin(java.lang.String value){this.flgdestinterniesterniin=value;}
    public void setIdsoggrubricain(java.math.BigDecimal value){this.idsoggrubricain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    