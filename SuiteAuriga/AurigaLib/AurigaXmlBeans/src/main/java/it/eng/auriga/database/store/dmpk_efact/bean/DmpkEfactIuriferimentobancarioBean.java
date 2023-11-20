/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactIuriferimentobancarioBean")
public class DmpkEfactIuriferimentobancarioBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_IURIFERIMENTOBANCARIO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idriferimentoio;
	private java.lang.String descrizioneriferimentoin;
	private java.lang.String ragionesocialeistitutofinanzin;
	private java.lang.String codiceibanin;
	private java.lang.String codiceabiin;
	private java.lang.String codicecabin;
	private java.lang.Integer flgannullatoin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdriferimentoio(){return idriferimentoio;}
    public java.lang.String getDescrizioneriferimentoin(){return descrizioneriferimentoin;}
    public java.lang.String getRagionesocialeistitutofinanzin(){return ragionesocialeistitutofinanzin;}
    public java.lang.String getCodiceibanin(){return codiceibanin;}
    public java.lang.String getCodiceabiin(){return codiceabiin;}
    public java.lang.String getCodicecabin(){return codicecabin;}
    public java.lang.Integer getFlgannullatoin(){return flgannullatoin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdriferimentoio(java.lang.String value){this.idriferimentoio=value;}
    public void setDescrizioneriferimentoin(java.lang.String value){this.descrizioneriferimentoin=value;}
    public void setRagionesocialeistitutofinanzin(java.lang.String value){this.ragionesocialeistitutofinanzin=value;}
    public void setCodiceibanin(java.lang.String value){this.codiceibanin=value;}
    public void setCodiceabiin(java.lang.String value){this.codiceabiin=value;}
    public void setCodicecabin(java.lang.String value){this.codicecabin=value;}
    public void setFlgannullatoin(java.lang.Integer value){this.flgannullatoin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    