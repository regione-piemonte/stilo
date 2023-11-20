/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityFindclassifexindiceegrBean")
public class DmpkUtilityFindclassifexindiceegrBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_FINDCLASSIFEXINDICEEGR";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idindiceegrin;
	private java.lang.Integer flgsolovldin;
	private java.math.BigDecimal idpianoclassifio;
	private java.math.BigDecimal idspaooio;
	private java.math.BigDecimal idclassificazioneout;
	private java.lang.String livelliclassifout;
	private java.lang.String desclassificazioneout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdindiceegrin(){return idindiceegrin;}
    public java.lang.Integer getFlgsolovldin(){return flgsolovldin;}
    public java.math.BigDecimal getIdpianoclassifio(){return idpianoclassifio;}
    public java.math.BigDecimal getIdspaooio(){return idspaooio;}
    public java.math.BigDecimal getIdclassificazioneout(){return idclassificazioneout;}
    public java.lang.String getLivelliclassifout(){return livelliclassifout;}
    public java.lang.String getDesclassificazioneout(){return desclassificazioneout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdindiceegrin(java.math.BigDecimal value){this.idindiceegrin=value;}
    public void setFlgsolovldin(java.lang.Integer value){this.flgsolovldin=value;}
    public void setIdpianoclassifio(java.math.BigDecimal value){this.idpianoclassifio=value;}
    public void setIdspaooio(java.math.BigDecimal value){this.idspaooio=value;}
    public void setIdclassificazioneout(java.math.BigDecimal value){this.idclassificazioneout=value;}
    public void setLivelliclassifout(java.lang.String value){this.livelliclassifout=value;}
    public void setDesclassificazioneout(java.lang.String value){this.desclassificazioneout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    