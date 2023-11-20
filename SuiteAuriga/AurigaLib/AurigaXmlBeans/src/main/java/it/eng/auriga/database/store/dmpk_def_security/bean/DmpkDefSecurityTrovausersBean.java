/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityTrovausersBean")
public class DmpkDefSecurityTrovausersBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_TROVAUSERS";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String iduserio;
	private java.lang.String usernameio;
	private java.lang.String descrizioneio;
	private java.lang.String qualificaio;
	private java.lang.String matricolaio;
	private java.lang.String ciprovuserio;
	private java.lang.Integer flgaccreditatiindomio;
	private java.lang.String codapplaccredio;
	private java.lang.String codistapplaccredio;
	private java.lang.String codapplnoaccredio;
	private java.lang.String codistapplnoaccredio;
	private java.math.BigDecimal idruoloammio;
	private java.lang.String desruoloammio;
	private java.math.BigDecimal iduoconrelvsuserio;
	private java.lang.String livelliuoconrelvsuserio;
	private java.lang.String desuoconrelvsuserio;
	private java.lang.String flgtiporelconuoio;
	private java.lang.Integer flginclsottouoio;
	private java.math.BigDecimal idgruppoappio;
	private java.lang.String nomegruppoappio;
	private java.math.BigDecimal flgsolovldio;
	private java.lang.String tsvldio;
	private java.lang.Integer flgconaccessoalsistemaio;
	private java.lang.String altricriteriio;
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
	private java.lang.Integer flgabilinsout;
	private java.lang.Integer flgabilupdout;
	private java.lang.Integer flgabildelout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String emailin;
	private java.lang.Integer idprofiloin;
	private java.lang.String rigatagnamein;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getIduserio(){return iduserio;}
    public java.lang.String getUsernameio(){return usernameio;}
    public java.lang.String getDescrizioneio(){return descrizioneio;}
    public java.lang.String getQualificaio(){return qualificaio;}
    public java.lang.String getMatricolaio(){return matricolaio;}
    public java.lang.String getCiprovuserio(){return ciprovuserio;}
    public java.lang.Integer getFlgaccreditatiindomio(){return flgaccreditatiindomio;}
    public java.lang.String getCodapplaccredio(){return codapplaccredio;}
    public java.lang.String getCodistapplaccredio(){return codistapplaccredio;}
    public java.lang.String getCodapplnoaccredio(){return codapplnoaccredio;}
    public java.lang.String getCodistapplnoaccredio(){return codistapplnoaccredio;}
    public java.math.BigDecimal getIdruoloammio(){return idruoloammio;}
    public java.lang.String getDesruoloammio(){return desruoloammio;}
    public java.math.BigDecimal getIduoconrelvsuserio(){return iduoconrelvsuserio;}
    public java.lang.String getLivelliuoconrelvsuserio(){return livelliuoconrelvsuserio;}
    public java.lang.String getDesuoconrelvsuserio(){return desuoconrelvsuserio;}
    public java.lang.String getFlgtiporelconuoio(){return flgtiporelconuoio;}
    public java.lang.Integer getFlginclsottouoio(){return flginclsottouoio;}
    public java.math.BigDecimal getIdgruppoappio(){return idgruppoappio;}
    public java.lang.String getNomegruppoappio(){return nomegruppoappio;}
    public java.math.BigDecimal getFlgsolovldio(){return flgsolovldio;}
    public java.lang.String getTsvldio(){return tsvldio;}
    public java.lang.Integer getFlgconaccessoalsistemaio(){return flgconaccessoalsistemaio;}
    public java.lang.String getAltricriteriio(){return altricriteriio;}
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
    public java.lang.Integer getFlgabilinsout(){return flgabilinsout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.Integer getFlgabildelout(){return flgabildelout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getEmailin(){return emailin;}
    public java.lang.Integer getIdprofiloin(){return idprofiloin;}
    public java.lang.String getRigatagnamein(){return rigatagnamein;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setIduserio(java.lang.String value){this.iduserio=value;}
    public void setUsernameio(java.lang.String value){this.usernameio=value;}
    public void setDescrizioneio(java.lang.String value){this.descrizioneio=value;}
    public void setQualificaio(java.lang.String value){this.qualificaio=value;}
    public void setMatricolaio(java.lang.String value){this.matricolaio=value;}
    public void setCiprovuserio(java.lang.String value){this.ciprovuserio=value;}
    public void setFlgaccreditatiindomio(java.lang.Integer value){this.flgaccreditatiindomio=value;}
    public void setCodapplaccredio(java.lang.String value){this.codapplaccredio=value;}
    public void setCodistapplaccredio(java.lang.String value){this.codistapplaccredio=value;}
    public void setCodapplnoaccredio(java.lang.String value){this.codapplnoaccredio=value;}
    public void setCodistapplnoaccredio(java.lang.String value){this.codistapplnoaccredio=value;}
    public void setIdruoloammio(java.math.BigDecimal value){this.idruoloammio=value;}
    public void setDesruoloammio(java.lang.String value){this.desruoloammio=value;}
    public void setIduoconrelvsuserio(java.math.BigDecimal value){this.iduoconrelvsuserio=value;}
    public void setLivelliuoconrelvsuserio(java.lang.String value){this.livelliuoconrelvsuserio=value;}
    public void setDesuoconrelvsuserio(java.lang.String value){this.desuoconrelvsuserio=value;}
    public void setFlgtiporelconuoio(java.lang.String value){this.flgtiporelconuoio=value;}
    public void setFlginclsottouoio(java.lang.Integer value){this.flginclsottouoio=value;}
    public void setIdgruppoappio(java.math.BigDecimal value){this.idgruppoappio=value;}
    public void setNomegruppoappio(java.lang.String value){this.nomegruppoappio=value;}
    public void setFlgsolovldio(java.math.BigDecimal value){this.flgsolovldio=value;}
    public void setTsvldio(java.lang.String value){this.tsvldio=value;}
    public void setFlgconaccessoalsistemaio(java.lang.Integer value){this.flgconaccessoalsistemaio=value;}
    public void setAltricriteriio(java.lang.String value){this.altricriteriio=value;}
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
    public void setFlgabilinsout(java.lang.Integer value){this.flgabilinsout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setFlgabildelout(java.lang.Integer value){this.flgabildelout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setEmailin(java.lang.String value){this.emailin=value;}
    public void setIdprofiloin(java.lang.Integer value){this.idprofiloin=value;}
    public void setRigatagnamein(java.lang.String value){this.rigatagnamein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    