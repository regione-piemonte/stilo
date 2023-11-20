/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspTrovacontenutifoBean")
public class DmpkAmmTraspTrovacontenutifoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_TROVACONTENUTIFO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String matchwordlistin;
	private java.math.BigDecimal idsezionein;
	private java.math.BigDecimal livsezioniconteggioin;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer pagesizeio;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String conteggixsezioneout;
	private java.lang.String resultout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getMatchwordlistin(){return matchwordlistin;}
    public java.math.BigDecimal getIdsezionein(){return idsezionein;}
    public java.math.BigDecimal getLivsezioniconteggioin(){return livsezioniconteggioin;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getPagesizeio(){return pagesizeio;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getConteggixsezioneout(){return conteggixsezioneout;}
    public java.lang.String getResultout(){return resultout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setMatchwordlistin(java.lang.String value){this.matchwordlistin=value;}
    public void setIdsezionein(java.math.BigDecimal value){this.idsezionein=value;}
    public void setLivsezioniconteggioin(java.math.BigDecimal value){this.livsezioniconteggioin=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setPagesizeio(java.lang.Integer value){this.pagesizeio=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setConteggixsezioneout(java.lang.String value){this.conteggixsezioneout=value;}
    public void setResultout(java.lang.String value){this.resultout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
	public StoreType getType() { return StoreType.STORE; }

}    