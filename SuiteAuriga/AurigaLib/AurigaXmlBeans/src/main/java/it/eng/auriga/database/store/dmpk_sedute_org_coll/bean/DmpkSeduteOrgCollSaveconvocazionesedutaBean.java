/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkSeduteOrgCollSaveconvocazionesedutaBean")
public class DmpkSeduteOrgCollSaveconvocazionesedutaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SEDUTE_ORG_COLL_SAVECONVOCAZIONESEDUTA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String organocollegialein;
	private java.lang.String listacommissioniin;
	private java.lang.String idsedutaio;
	private java.lang.Integer nrosedutaout;
	private java.lang.String dataora1aconvocazionein;
	private java.lang.String luogo1aconvocazionein;
	private java.lang.String dataora2aconvocazionein;
	private java.lang.String luogo2aconvocazionein;
	private java.lang.String tiposessionein;
	private java.lang.String statosedutain;
	private java.lang.String odginfoio;
	private java.lang.String convocazioneinfoin;
	private java.lang.String argomentiodgin;
	private java.lang.String presenzein;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getOrganocollegialein(){return organocollegialein;}
    public java.lang.String getListacommissioniin(){return listacommissioniin;}
    public java.lang.String getIdsedutaio(){return idsedutaio;}
    public java.lang.Integer getNrosedutaout(){return nrosedutaout;}
    public java.lang.String getDataora1aconvocazionein(){return dataora1aconvocazionein;}
    public java.lang.String getLuogo1aconvocazionein(){return luogo1aconvocazionein;}
    public java.lang.String getDataora2aconvocazionein(){return dataora2aconvocazionein;}
    public java.lang.String getLuogo2aconvocazionein(){return luogo2aconvocazionein;}
    public java.lang.String getTiposessionein(){return tiposessionein;}
    public java.lang.String getStatosedutain(){return statosedutain;}
    public java.lang.String getOdginfoio(){return odginfoio;}
    public java.lang.String getConvocazioneinfoin(){return convocazioneinfoin;}
    public java.lang.String getArgomentiodgin(){return argomentiodgin;}
    public java.lang.String getPresenzein(){return presenzein;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setOrganocollegialein(java.lang.String value){this.organocollegialein=value;}
    public void setListacommissioniin(java.lang.String value){this.listacommissioniin=value;}
    public void setIdsedutaio(java.lang.String value){this.idsedutaio=value;}
    public void setNrosedutaout(java.lang.Integer value){this.nrosedutaout=value;}
    public void setDataora1aconvocazionein(java.lang.String value){this.dataora1aconvocazionein=value;}
    public void setLuogo1aconvocazionein(java.lang.String value){this.luogo1aconvocazionein=value;}
    public void setDataora2aconvocazionein(java.lang.String value){this.dataora2aconvocazionein=value;}
    public void setLuogo2aconvocazionein(java.lang.String value){this.luogo2aconvocazionein=value;}
    public void setTiposessionein(java.lang.String value){this.tiposessionein=value;}
    public void setStatosedutain(java.lang.String value){this.statosedutain=value;}
    public void setOdginfoio(java.lang.String value){this.odginfoio=value;}
    public void setConvocazioneinfoin(java.lang.String value){this.convocazioneinfoin=value;}
    public void setArgomentiodgin(java.lang.String value){this.argomentiodgin=value;}
    public void setPresenzein(java.lang.String value){this.presenzein=value;}
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