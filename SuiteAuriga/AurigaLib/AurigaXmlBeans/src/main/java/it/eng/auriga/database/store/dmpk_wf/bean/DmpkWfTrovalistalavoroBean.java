/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfTrovalistalavoroBean")
public class DmpkWfTrovalistalavoroBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_TROVALISTALAVORO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String codapplicazioneio;
	private java.lang.String codistanzaapplio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String nomeprocesstypeio;
	private java.lang.String citypeflussowfio;
	private java.lang.String ciprovprocessio;
	private java.lang.String oggettoio;
	private java.lang.String dtavviodaio;
	private java.lang.String dtavvioaio;
	private java.lang.String dtdecorrenzadaio;
	private java.lang.String dtdecorrenzaaio;
	private java.lang.String flgstatoprocio;
	private java.lang.Integer flgavviouserlavio;
	private java.lang.String wfnamefasecorrio;
	private java.lang.String wfnameattesegio;
	private java.lang.String criteriitersvoltoio;
	private java.lang.String criteriiternonsvoltoio;
	private java.lang.String tpscadio;
	private java.lang.Integer scadentronggio;
	private java.lang.Integer scaddaminnggio;
	private java.lang.Integer flgnoscadconevtfinio;
	private java.lang.String noteprocio;
	private java.lang.String attributiprocio;
	private java.lang.String soggettiintio;
	private java.lang.Integer flgassuserlavio;
	private java.math.BigDecimal iduserassio;
	private java.lang.String desuserassio;
	private java.lang.String soggettiestio;
	private java.lang.String udprocio;
	private java.lang.String folderprocio;
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
	private java.lang.String criteriavanzatiin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getCodapplicazioneio(){return codapplicazioneio;}
    public java.lang.String getCodistanzaapplio(){return codistanzaapplio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getNomeprocesstypeio(){return nomeprocesstypeio;}
    public java.lang.String getCitypeflussowfio(){return citypeflussowfio;}
    public java.lang.String getCiprovprocessio(){return ciprovprocessio;}
    public java.lang.String getOggettoio(){return oggettoio;}
    public java.lang.String getDtavviodaio(){return dtavviodaio;}
    public java.lang.String getDtavvioaio(){return dtavvioaio;}
    public java.lang.String getDtdecorrenzadaio(){return dtdecorrenzadaio;}
    public java.lang.String getDtdecorrenzaaio(){return dtdecorrenzaaio;}
    public java.lang.String getFlgstatoprocio(){return flgstatoprocio;}
    public java.lang.Integer getFlgavviouserlavio(){return flgavviouserlavio;}
    public java.lang.String getWfnamefasecorrio(){return wfnamefasecorrio;}
    public java.lang.String getWfnameattesegio(){return wfnameattesegio;}
    public java.lang.String getCriteriitersvoltoio(){return criteriitersvoltoio;}
    public java.lang.String getCriteriiternonsvoltoio(){return criteriiternonsvoltoio;}
    public java.lang.String getTpscadio(){return tpscadio;}
    public java.lang.Integer getScadentronggio(){return scadentronggio;}
    public java.lang.Integer getScaddaminnggio(){return scaddaminnggio;}
    public java.lang.Integer getFlgnoscadconevtfinio(){return flgnoscadconevtfinio;}
    public java.lang.String getNoteprocio(){return noteprocio;}
    public java.lang.String getAttributiprocio(){return attributiprocio;}
    public java.lang.String getSoggettiintio(){return soggettiintio;}
    public java.lang.Integer getFlgassuserlavio(){return flgassuserlavio;}
    public java.math.BigDecimal getIduserassio(){return iduserassio;}
    public java.lang.String getDesuserassio(){return desuserassio;}
    public java.lang.String getSoggettiestio(){return soggettiestio;}
    public java.lang.String getUdprocio(){return udprocio;}
    public java.lang.String getFolderprocio(){return folderprocio;}
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
    public java.lang.String getCriteriavanzatiin(){return criteriavanzatiin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setCodapplicazioneio(java.lang.String value){this.codapplicazioneio=value;}
    public void setCodistanzaapplio(java.lang.String value){this.codistanzaapplio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setNomeprocesstypeio(java.lang.String value){this.nomeprocesstypeio=value;}
    public void setCitypeflussowfio(java.lang.String value){this.citypeflussowfio=value;}
    public void setCiprovprocessio(java.lang.String value){this.ciprovprocessio=value;}
    public void setOggettoio(java.lang.String value){this.oggettoio=value;}
    public void setDtavviodaio(java.lang.String value){this.dtavviodaio=value;}
    public void setDtavvioaio(java.lang.String value){this.dtavvioaio=value;}
    public void setDtdecorrenzadaio(java.lang.String value){this.dtdecorrenzadaio=value;}
    public void setDtdecorrenzaaio(java.lang.String value){this.dtdecorrenzaaio=value;}
    public void setFlgstatoprocio(java.lang.String value){this.flgstatoprocio=value;}
    public void setFlgavviouserlavio(java.lang.Integer value){this.flgavviouserlavio=value;}
    public void setWfnamefasecorrio(java.lang.String value){this.wfnamefasecorrio=value;}
    public void setWfnameattesegio(java.lang.String value){this.wfnameattesegio=value;}
    public void setCriteriitersvoltoio(java.lang.String value){this.criteriitersvoltoio=value;}
    public void setCriteriiternonsvoltoio(java.lang.String value){this.criteriiternonsvoltoio=value;}
    public void setTpscadio(java.lang.String value){this.tpscadio=value;}
    public void setScadentronggio(java.lang.Integer value){this.scadentronggio=value;}
    public void setScaddaminnggio(java.lang.Integer value){this.scaddaminnggio=value;}
    public void setFlgnoscadconevtfinio(java.lang.Integer value){this.flgnoscadconevtfinio=value;}
    public void setNoteprocio(java.lang.String value){this.noteprocio=value;}
    public void setAttributiprocio(java.lang.String value){this.attributiprocio=value;}
    public void setSoggettiintio(java.lang.String value){this.soggettiintio=value;}
    public void setFlgassuserlavio(java.lang.Integer value){this.flgassuserlavio=value;}
    public void setIduserassio(java.math.BigDecimal value){this.iduserassio=value;}
    public void setDesuserassio(java.lang.String value){this.desuserassio=value;}
    public void setSoggettiestio(java.lang.String value){this.soggettiestio=value;}
    public void setUdprocio(java.lang.String value){this.udprocio=value;}
    public void setFolderprocio(java.lang.String value){this.folderprocio=value;}
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
    public void setCriteriavanzatiin(java.lang.String value){this.criteriavanzatiin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    