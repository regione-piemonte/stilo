/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkSeduteOrgCollSavediscussionesedutaBean")
public class DmpkSeduteOrgCollSavediscussionesedutaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SEDUTE_ORG_COLL_SAVEDISCUSSIONESEDUTA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idsedutain;
	private java.lang.String statosedutaio;
	private java.lang.String argomentiodgio;
	private java.lang.String info1aconvocazioneio;
	private java.lang.String info2aconvocazioneio;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdsedutain(){return idsedutain;}
    public java.lang.String getStatosedutaio(){return statosedutaio;}
    public java.lang.String getArgomentiodgio(){return argomentiodgio;}
    public java.lang.String getInfo1aconvocazioneio(){return info1aconvocazioneio;}
    public java.lang.String getInfo2aconvocazioneio(){return info2aconvocazioneio;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsedutain(java.lang.String value){this.idsedutain=value;}
    public void setStatosedutaio(java.lang.String value){this.statosedutaio=value;}
    public void setArgomentiodgio(java.lang.String value){this.argomentiodgio=value;}
    public void setInfo1aconvocazioneio(java.lang.String value){this.info1aconvocazioneio=value;}
    public void setInfo2aconvocazioneio(java.lang.String value){this.info2aconvocazioneio=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    