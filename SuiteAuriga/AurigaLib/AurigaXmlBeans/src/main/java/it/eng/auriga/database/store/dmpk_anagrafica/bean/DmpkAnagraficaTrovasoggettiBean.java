/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaTrovasoggettiBean")
public class DmpkAnagraficaTrovasoggettiBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_TROVASOGGETTI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String matchbyindexerin;
	private java.lang.Integer flgindexeroverflowin;
	private java.lang.Integer flg2ndcallin;
	private java.lang.String denominazioneio;
	private java.lang.Integer flginclaltredenomio;
	private java.lang.Integer flgincldenomstoricheio;
	private java.lang.String cfio;
	private java.lang.String pivaio;
	private java.lang.String flgfisicagiuridicain;
	private java.lang.Integer flgnotcodtipisottotipiin;
	private java.lang.String listacodtipisottotipiio;
	private java.lang.String emailio;
	private java.lang.String codrapidoio;
	private java.lang.String ciprovsoggio;
	private java.lang.Integer flgsolovldio;
	private java.lang.String tsvldio;
	private java.lang.String codapplownerio;
	private java.lang.String codistapplownerio;
	private java.lang.Integer flgrestrapplownerio;
	private java.lang.Integer flgcertificatiio;
	private java.lang.Integer flginclannullatiio;
	private java.math.BigDecimal idsoggrubricaio;
	private java.lang.Integer flginindicepaio;
	private java.lang.String codammipaio;
	private java.lang.String codaooipaio;
	private java.math.BigDecimal issoggrubricaappio;
	private java.math.BigDecimal idgruppoappio;
	private java.lang.String nomegruppoappio;
	private java.lang.String strindenominazionein;
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
	private java.lang.String listaxmlout;
	private java.lang.Integer flgabilinsout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String finalitain;
	private java.lang.String codistatcomuneindin;
	private java.lang.String descittaindin;
	private java.lang.String restringiarubricadiuoin;
	private java.lang.String filtriavanzatiin;
	private java.lang.String codindpain;
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getMatchbyindexerin(){return matchbyindexerin;}
    public java.lang.Integer getFlgindexeroverflowin(){return flgindexeroverflowin;}
    public java.lang.Integer getFlg2ndcallin(){return flg2ndcallin;}
    public java.lang.String getDenominazioneio(){return denominazioneio;}
    public java.lang.Integer getFlginclaltredenomio(){return flginclaltredenomio;}
    public java.lang.Integer getFlgincldenomstoricheio(){return flgincldenomstoricheio;}
    public java.lang.String getCfio(){return cfio;}
    public java.lang.String getPivaio(){return pivaio;}
    public java.lang.String getFlgfisicagiuridicain(){return flgfisicagiuridicain;}
    public java.lang.Integer getFlgnotcodtipisottotipiin(){return flgnotcodtipisottotipiin;}
    public java.lang.String getListacodtipisottotipiio(){return listacodtipisottotipiio;}
    public java.lang.String getEmailio(){return emailio;}
    public java.lang.String getCodrapidoio(){return codrapidoio;}
    public java.lang.String getCiprovsoggio(){return ciprovsoggio;}
    public java.lang.Integer getFlgsolovldio(){return flgsolovldio;}
    public java.lang.String getTsvldio(){return tsvldio;}
    public java.lang.String getCodapplownerio(){return codapplownerio;}
    public java.lang.String getCodistapplownerio(){return codistapplownerio;}
    public java.lang.Integer getFlgrestrapplownerio(){return flgrestrapplownerio;}
    public java.lang.Integer getFlgcertificatiio(){return flgcertificatiio;}
    public java.lang.Integer getFlginclannullatiio(){return flginclannullatiio;}
    public java.math.BigDecimal getIdsoggrubricaio(){return idsoggrubricaio;}
    public java.lang.Integer getFlginindicepaio(){return flginindicepaio;}
    public java.lang.String getCodammipaio(){return codammipaio;}
    public java.lang.String getCodaooipaio(){return codaooipaio;}
    public java.math.BigDecimal getIssoggrubricaappio(){return issoggrubricaappio;}
    public java.math.BigDecimal getIdgruppoappio(){return idgruppoappio;}
    public java.lang.String getNomegruppoappio(){return nomegruppoappio;}
    public java.lang.String getStrindenominazionein(){return strindenominazionein;}
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
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.Integer getFlgabilinsout(){return flgabilinsout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFinalitain(){return finalitain;}
    public java.lang.String getCodistatcomuneindin(){return codistatcomuneindin;}
    public java.lang.String getDescittaindin(){return descittaindin;}
    public java.lang.String getRestringiarubricadiuoin(){return restringiarubricadiuoin;}
    public java.lang.String getFiltriavanzatiin(){return filtriavanzatiin;}
    public java.lang.String getCodindpain(){return codindpain;}
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setMatchbyindexerin(java.lang.String value){this.matchbyindexerin=value;}
    public void setFlgindexeroverflowin(java.lang.Integer value){this.flgindexeroverflowin=value;}
    public void setFlg2ndcallin(java.lang.Integer value){this.flg2ndcallin=value;}
    public void setDenominazioneio(java.lang.String value){this.denominazioneio=value;}
    public void setFlginclaltredenomio(java.lang.Integer value){this.flginclaltredenomio=value;}
    public void setFlgincldenomstoricheio(java.lang.Integer value){this.flgincldenomstoricheio=value;}
    public void setCfio(java.lang.String value){this.cfio=value;}
    public void setPivaio(java.lang.String value){this.pivaio=value;}
    public void setFlgfisicagiuridicain(java.lang.String value){this.flgfisicagiuridicain=value;}
    public void setFlgnotcodtipisottotipiin(java.lang.Integer value){this.flgnotcodtipisottotipiin=value;}
    public void setListacodtipisottotipiio(java.lang.String value){this.listacodtipisottotipiio=value;}
    public void setEmailio(java.lang.String value){this.emailio=value;}
    public void setCodrapidoio(java.lang.String value){this.codrapidoio=value;}
    public void setCiprovsoggio(java.lang.String value){this.ciprovsoggio=value;}
    public void setFlgsolovldio(java.lang.Integer value){this.flgsolovldio=value;}
    public void setTsvldio(java.lang.String value){this.tsvldio=value;}
    public void setCodapplownerio(java.lang.String value){this.codapplownerio=value;}
    public void setCodistapplownerio(java.lang.String value){this.codistapplownerio=value;}
    public void setFlgrestrapplownerio(java.lang.Integer value){this.flgrestrapplownerio=value;}
    public void setFlgcertificatiio(java.lang.Integer value){this.flgcertificatiio=value;}
    public void setFlginclannullatiio(java.lang.Integer value){this.flginclannullatiio=value;}
    public void setIdsoggrubricaio(java.math.BigDecimal value){this.idsoggrubricaio=value;}
    public void setFlginindicepaio(java.lang.Integer value){this.flginindicepaio=value;}
    public void setCodammipaio(java.lang.String value){this.codammipaio=value;}
    public void setCodaooipaio(java.lang.String value){this.codaooipaio=value;}
    public void setIssoggrubricaappio(java.math.BigDecimal value){this.issoggrubricaappio=value;}
    public void setIdgruppoappio(java.math.BigDecimal value){this.idgruppoappio=value;}
    public void setNomegruppoappio(java.lang.String value){this.nomegruppoappio=value;}
    public void setStrindenominazionein(java.lang.String value){this.strindenominazionein=value;}
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
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setFlgabilinsout(java.lang.Integer value){this.flgabilinsout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    public void setCodistatcomuneindin(java.lang.String value){this.codistatcomuneindin=value;}
    public void setDescittaindin(java.lang.String value){this.descittaindin=value;}
    public void setRestringiarubricadiuoin(java.lang.String value){this.restringiarubricadiuoin=value;}
    public void setFiltriavanzatiin(java.lang.String value){this.filtriavanzatiin=value;}
    public void setCodindpain(java.lang.String value){this.codindpain=value;}
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    