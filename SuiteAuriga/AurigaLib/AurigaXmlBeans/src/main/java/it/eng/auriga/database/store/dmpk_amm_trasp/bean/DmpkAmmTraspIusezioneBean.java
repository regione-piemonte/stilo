/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspIusezioneBean")
public class DmpkAmmTraspIusezioneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_IUSEZIONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idsezioneio;
	private java.math.BigDecimal idsezionepadrein;
	private java.lang.String nomesezionein;
	private java.math.BigDecimal idsezioneprecin;
	private java.math.BigDecimal flgdismessain;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.Integer flgpubblapertain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdsezioneio(){return idsezioneio;}
    public java.math.BigDecimal getIdsezionepadrein(){return idsezionepadrein;}
    public java.lang.String getNomesezionein(){return nomesezionein;}
    public java.math.BigDecimal getIdsezioneprecin(){return idsezioneprecin;}
    public java.math.BigDecimal getFlgdismessain(){return flgdismessain;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.Integer getFlgpubblapertain(){return flgpubblapertain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsezioneio(java.math.BigDecimal value){this.idsezioneio=value;}
    public void setIdsezionepadrein(java.math.BigDecimal value){this.idsezionepadrein=value;}
    public void setNomesezionein(java.lang.String value){this.nomesezionein=value;}
    public void setIdsezioneprecin(java.math.BigDecimal value){this.idsezioneprecin=value;}
    public void setFlgdismessain(java.math.BigDecimal value){this.flgdismessain=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFlgpubblapertain(java.lang.Integer value){this.flgpubblapertain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    