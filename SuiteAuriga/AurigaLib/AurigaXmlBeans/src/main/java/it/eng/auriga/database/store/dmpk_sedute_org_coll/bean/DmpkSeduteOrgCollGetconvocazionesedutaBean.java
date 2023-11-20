/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkSeduteOrgCollGetconvocazionesedutaBean")
public class DmpkSeduteOrgCollGetconvocazionesedutaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_SEDUTE_ORG_COLL_GETCONVOCAZIONESEDUTA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String organocollegialein;
	private java.lang.String listacommissioniin;
	private java.lang.String idsedutaio;
	private java.lang.Integer nrosedutaout;
	private java.lang.String dataora1aconvocazioneout;
	private java.lang.String luogo1aconvocazioneout;
	private java.lang.String dataora2aconvocazioneout;
	private java.lang.String luogo2aconvocazioneout;
	private java.lang.String tiposessioneout;
	private java.lang.String statosedutaout;
	private java.lang.String odginfoout;
	private java.lang.String convocazioneinfoout;
	private java.lang.String argomentiodgout;
	private java.lang.String presenzeout;
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
    public java.lang.String getDataora1aconvocazioneout(){return dataora1aconvocazioneout;}
    public java.lang.String getLuogo1aconvocazioneout(){return luogo1aconvocazioneout;}
    public java.lang.String getDataora2aconvocazioneout(){return dataora2aconvocazioneout;}
    public java.lang.String getLuogo2aconvocazioneout(){return luogo2aconvocazioneout;}
    public java.lang.String getTiposessioneout(){return tiposessioneout;}
    public java.lang.String getStatosedutaout(){return statosedutaout;}
    public java.lang.String getOdginfoout(){return odginfoout;}
    public java.lang.String getConvocazioneinfoout(){return convocazioneinfoout;}
    public java.lang.String getArgomentiodgout(){return argomentiodgout;}
    public java.lang.String getPresenzeout(){return presenzeout;}
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
    public void setDataora1aconvocazioneout(java.lang.String value){this.dataora1aconvocazioneout=value;}
    public void setLuogo1aconvocazioneout(java.lang.String value){this.luogo1aconvocazioneout=value;}
    public void setDataora2aconvocazioneout(java.lang.String value){this.dataora2aconvocazioneout=value;}
    public void setLuogo2aconvocazioneout(java.lang.String value){this.luogo2aconvocazioneout=value;}
    public void setTiposessioneout(java.lang.String value){this.tiposessioneout=value;}
    public void setStatosedutaout(java.lang.String value){this.statosedutaout=value;}
    public void setOdginfoout(java.lang.String value){this.odginfoout=value;}
    public void setConvocazioneinfoout(java.lang.String value){this.convocazioneinfoout=value;}
    public void setArgomentiodgout(java.lang.String value){this.argomentiodgout=value;}
    public void setPresenzeout(java.lang.String value){this.presenzeout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    