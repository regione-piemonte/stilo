/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCore2TrovarepositoryobjBean")
public class DmpkCore2TrovarepositoryobjBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORE_2_TROVAREPOSITORYOBJ";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String matchbyindexerin;
	private java.lang.Integer flgindexeroverflowin;
	private java.lang.Integer flg2ndcallin;
	private java.lang.String flgfmtestremiregio;
	private java.lang.String flgudfolderio;
	private java.math.BigDecimal cercainfolderio;
	private java.lang.Integer flgcercainsubfolderio;
	private java.lang.String criteriavanzatiio;
	private java.lang.String criteripersonalizzatiio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.Integer flgbatchsearchin;
	private java.lang.String coltoreturnin;
	private java.lang.String resultout;
	private java.lang.String percorsoricercaxmlio;
	private java.lang.String dettaglicercainfolderout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String finalitain;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getMatchbyindexerin(){return matchbyindexerin;}
    public java.lang.Integer getFlgindexeroverflowin(){return flgindexeroverflowin;}
    public java.lang.Integer getFlg2ndcallin(){return flg2ndcallin;}
    public java.lang.String getFlgfmtestremiregio(){return flgfmtestremiregio;}
    public java.lang.String getFlgudfolderio(){return flgudfolderio;}
    public java.math.BigDecimal getCercainfolderio(){return cercainfolderio;}
    public java.lang.Integer getFlgcercainsubfolderio(){return flgcercainsubfolderio;}
    public java.lang.String getCriteriavanzatiio(){return criteriavanzatiio;}
    public java.lang.String getCriteripersonalizzatiio(){return criteripersonalizzatiio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.Integer getFlgbatchsearchin(){return flgbatchsearchin;}
    public java.lang.String getColtoreturnin(){return coltoreturnin;}
    public java.lang.String getResultout(){return resultout;}
    public java.lang.String getPercorsoricercaxmlio(){return percorsoricercaxmlio;}
    public java.lang.String getDettaglicercainfolderout(){return dettaglicercainfolderout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFinalitain(){return finalitain;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setMatchbyindexerin(java.lang.String value){this.matchbyindexerin=value;}
    public void setFlgindexeroverflowin(java.lang.Integer value){this.flgindexeroverflowin=value;}
    public void setFlg2ndcallin(java.lang.Integer value){this.flg2ndcallin=value;}
    public void setFlgfmtestremiregio(java.lang.String value){this.flgfmtestremiregio=value;}
    public void setFlgudfolderio(java.lang.String value){this.flgudfolderio=value;}
    public void setCercainfolderio(java.math.BigDecimal value){this.cercainfolderio=value;}
    public void setFlgcercainsubfolderio(java.lang.Integer value){this.flgcercainsubfolderio=value;}
    public void setCriteriavanzatiio(java.lang.String value){this.criteriavanzatiio=value;}
    public void setCriteripersonalizzatiio(java.lang.String value){this.criteripersonalizzatiio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setFlgbatchsearchin(java.lang.Integer value){this.flgbatchsearchin=value;}
    public void setColtoreturnin(java.lang.String value){this.coltoreturnin=value;}
    public void setResultout(java.lang.String value){this.resultout=value;}
    public void setPercorsoricercaxmlio(java.lang.String value){this.percorsoricercaxmlio=value;}
    public void setDettaglicercainfolderout(java.lang.String value){this.dettaglicercainfolderout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    