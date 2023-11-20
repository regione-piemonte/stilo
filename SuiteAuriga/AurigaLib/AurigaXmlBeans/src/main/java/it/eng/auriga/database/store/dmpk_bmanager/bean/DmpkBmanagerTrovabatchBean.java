/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerTrovabatchBean")
public class DmpkBmanagerTrovabatchBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_TROVABATCH";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.math.BigDecimal idjobio;
	private java.lang.Integer flgsolosottomessiutentelavio;
	private java.lang.String desutentesottomissioneio;
	private java.math.BigDecimal idutentesottomissioneio;
	private java.lang.String codapplicazioneio;
	private java.lang.String codistapplicazioneio;
	private java.lang.String tipojobio;
	private java.lang.String flgstatijobio;
	private java.lang.String submitdaio;
	private java.lang.String submitaio;
	private java.lang.String scheduletimedaio;
	private java.lang.String scheduletimeaio;
	private java.lang.String incorsodaio;
	private java.lang.String incorsoaio;
	private java.lang.String exportfilenameio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.math.BigDecimal getIdjobio(){return idjobio;}
    public java.lang.Integer getFlgsolosottomessiutentelavio(){return flgsolosottomessiutentelavio;}
    public java.lang.String getDesutentesottomissioneio(){return desutentesottomissioneio;}
    public java.math.BigDecimal getIdutentesottomissioneio(){return idutentesottomissioneio;}
    public java.lang.String getCodapplicazioneio(){return codapplicazioneio;}
    public java.lang.String getCodistapplicazioneio(){return codistapplicazioneio;}
    public java.lang.String getTipojobio(){return tipojobio;}
    public java.lang.String getFlgstatijobio(){return flgstatijobio;}
    public java.lang.String getSubmitdaio(){return submitdaio;}
    public java.lang.String getSubmitaio(){return submitaio;}
    public java.lang.String getScheduletimedaio(){return scheduletimedaio;}
    public java.lang.String getScheduletimeaio(){return scheduletimeaio;}
    public java.lang.String getIncorsodaio(){return incorsodaio;}
    public java.lang.String getIncorsoaio(){return incorsoaio;}
    public java.lang.String getExportfilenameio(){return exportfilenameio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIdjobio(java.math.BigDecimal value){this.idjobio=value;}
    public void setFlgsolosottomessiutentelavio(java.lang.Integer value){this.flgsolosottomessiutentelavio=value;}
    public void setDesutentesottomissioneio(java.lang.String value){this.desutentesottomissioneio=value;}
    public void setIdutentesottomissioneio(java.math.BigDecimal value){this.idutentesottomissioneio=value;}
    public void setCodapplicazioneio(java.lang.String value){this.codapplicazioneio=value;}
    public void setCodistapplicazioneio(java.lang.String value){this.codistapplicazioneio=value;}
    public void setTipojobio(java.lang.String value){this.tipojobio=value;}
    public void setFlgstatijobio(java.lang.String value){this.flgstatijobio=value;}
    public void setSubmitdaio(java.lang.String value){this.submitdaio=value;}
    public void setSubmitaio(java.lang.String value){this.submitaio=value;}
    public void setScheduletimedaio(java.lang.String value){this.scheduletimedaio=value;}
    public void setScheduletimeaio(java.lang.String value){this.scheduletimeaio=value;}
    public void setIncorsodaio(java.lang.String value){this.incorsodaio=value;}
    public void setIncorsoaio(java.lang.String value){this.incorsoaio=value;}
    public void setExportfilenameio(java.lang.String value){this.exportfilenameio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    