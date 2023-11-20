/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAnagraficaLoaddettsoggettoBean")
public class DmpkAnagraficaLoaddettsoggettoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ANAGRAFICA_LOADDETTSOGGETTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idsoggrubrio;
	private java.math.BigDecimal flgpersonafisicaout;
	private java.lang.String denomcognomeout;
	private java.lang.String nomeout;
	private java.lang.String cfout;
	private java.lang.String pivaout;
	private java.lang.String codtiposottotipoout;
	private java.lang.String codcondgiuridicaout;
	private java.lang.String dtnascitaout;
	private java.lang.String codistatcomunenascout;
	private java.lang.String nomecomunenascout;
	private java.lang.String targaprovnascout;
	private java.lang.String codistatstatonascout;
	private java.lang.String nomestatonascout;
	private java.lang.String dtcessazioneout;
	private java.lang.String codcausalecessazioneout;
	private java.lang.String flgsexout;
	private java.lang.String codistatstatocittout;
	private java.lang.String nomestatocittout;
	private java.lang.String titoloout;
	private java.lang.String codindpaout;
	private java.lang.String codammipaout;
	private java.lang.String codaooipaout;
	private java.math.BigDecimal idsoggappout;
	private java.lang.String ciprovsoggout;
	private java.lang.String usernameout;
	private java.lang.String codrapidoout;
	private java.math.BigDecimal flglockedout;
	private java.lang.String rowidout;
	private java.lang.String indirizziout;
	private java.lang.String xmlaltredenominazioniout;
	private java.lang.String xmlcontattiout;
	private java.lang.String xmlgruppiappout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String finalitain;
	private java.lang.Integer flgselezionabileout;
	private java.lang.Integer flgattivoout;
	private java.math.BigDecimal iduoout;
	private java.lang.String livelliuoout;
	private java.lang.String desuoout;
	private java.lang.Integer flgvisibsottouoout;
	private java.lang.Integer flggestsottouoout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdsoggrubrio(){return idsoggrubrio;}
    public java.math.BigDecimal getFlgpersonafisicaout(){return flgpersonafisicaout;}
    public java.lang.String getDenomcognomeout(){return denomcognomeout;}
    public java.lang.String getNomeout(){return nomeout;}
    public java.lang.String getCfout(){return cfout;}
    public java.lang.String getPivaout(){return pivaout;}
    public java.lang.String getCodtiposottotipoout(){return codtiposottotipoout;}
    public java.lang.String getCodcondgiuridicaout(){return codcondgiuridicaout;}
    public java.lang.String getDtnascitaout(){return dtnascitaout;}
    public java.lang.String getCodistatcomunenascout(){return codistatcomunenascout;}
    public java.lang.String getNomecomunenascout(){return nomecomunenascout;}
    public java.lang.String getTargaprovnascout(){return targaprovnascout;}
    public java.lang.String getCodistatstatonascout(){return codistatstatonascout;}
    public java.lang.String getNomestatonascout(){return nomestatonascout;}
    public java.lang.String getDtcessazioneout(){return dtcessazioneout;}
    public java.lang.String getCodcausalecessazioneout(){return codcausalecessazioneout;}
    public java.lang.String getFlgsexout(){return flgsexout;}
    public java.lang.String getCodistatstatocittout(){return codistatstatocittout;}
    public java.lang.String getNomestatocittout(){return nomestatocittout;}
    public java.lang.String getTitoloout(){return titoloout;}
    public java.lang.String getCodindpaout(){return codindpaout;}
    public java.lang.String getCodammipaout(){return codammipaout;}
    public java.lang.String getCodaooipaout(){return codaooipaout;}
    public java.math.BigDecimal getIdsoggappout(){return idsoggappout;}
    public java.lang.String getCiprovsoggout(){return ciprovsoggout;}
    public java.lang.String getUsernameout(){return usernameout;}
    public java.lang.String getCodrapidoout(){return codrapidoout;}
    public java.math.BigDecimal getFlglockedout(){return flglockedout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getIndirizziout(){return indirizziout;}
    public java.lang.String getXmlaltredenominazioniout(){return xmlaltredenominazioniout;}
    public java.lang.String getXmlcontattiout(){return xmlcontattiout;}
    public java.lang.String getXmlgruppiappout(){return xmlgruppiappout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getFinalitain(){return finalitain;}
    public java.lang.Integer getFlgselezionabileout(){return flgselezionabileout;}
    public java.lang.Integer getFlgattivoout(){return flgattivoout;}
    public java.math.BigDecimal getIduoout(){return iduoout;}
    public java.lang.String getLivelliuoout(){return livelliuoout;}
    public java.lang.String getDesuoout(){return desuoout;}
    public java.lang.Integer getFlgvisibsottouoout(){return flgvisibsottouoout;}
    public java.lang.Integer getFlggestsottouoout(){return flggestsottouoout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsoggrubrio(java.math.BigDecimal value){this.idsoggrubrio=value;}
    public void setFlgpersonafisicaout(java.math.BigDecimal value){this.flgpersonafisicaout=value;}
    public void setDenomcognomeout(java.lang.String value){this.denomcognomeout=value;}
    public void setNomeout(java.lang.String value){this.nomeout=value;}
    public void setCfout(java.lang.String value){this.cfout=value;}
    public void setPivaout(java.lang.String value){this.pivaout=value;}
    public void setCodtiposottotipoout(java.lang.String value){this.codtiposottotipoout=value;}
    public void setCodcondgiuridicaout(java.lang.String value){this.codcondgiuridicaout=value;}
    public void setDtnascitaout(java.lang.String value){this.dtnascitaout=value;}
    public void setCodistatcomunenascout(java.lang.String value){this.codistatcomunenascout=value;}
    public void setNomecomunenascout(java.lang.String value){this.nomecomunenascout=value;}
    public void setTargaprovnascout(java.lang.String value){this.targaprovnascout=value;}
    public void setCodistatstatonascout(java.lang.String value){this.codistatstatonascout=value;}
    public void setNomestatonascout(java.lang.String value){this.nomestatonascout=value;}
    public void setDtcessazioneout(java.lang.String value){this.dtcessazioneout=value;}
    public void setCodcausalecessazioneout(java.lang.String value){this.codcausalecessazioneout=value;}
    public void setFlgsexout(java.lang.String value){this.flgsexout=value;}
    public void setCodistatstatocittout(java.lang.String value){this.codistatstatocittout=value;}
    public void setNomestatocittout(java.lang.String value){this.nomestatocittout=value;}
    public void setTitoloout(java.lang.String value){this.titoloout=value;}
    public void setCodindpaout(java.lang.String value){this.codindpaout=value;}
    public void setCodammipaout(java.lang.String value){this.codammipaout=value;}
    public void setCodaooipaout(java.lang.String value){this.codaooipaout=value;}
    public void setIdsoggappout(java.math.BigDecimal value){this.idsoggappout=value;}
    public void setCiprovsoggout(java.lang.String value){this.ciprovsoggout=value;}
    public void setUsernameout(java.lang.String value){this.usernameout=value;}
    public void setCodrapidoout(java.lang.String value){this.codrapidoout=value;}
    public void setFlglockedout(java.math.BigDecimal value){this.flglockedout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setIndirizziout(java.lang.String value){this.indirizziout=value;}
    public void setXmlaltredenominazioniout(java.lang.String value){this.xmlaltredenominazioniout=value;}
    public void setXmlcontattiout(java.lang.String value){this.xmlcontattiout=value;}
    public void setXmlgruppiappout(java.lang.String value){this.xmlgruppiappout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setFinalitain(java.lang.String value){this.finalitain=value;}
    public void setFlgselezionabileout(java.lang.Integer value){this.flgselezionabileout=value;}
    public void setFlgattivoout(java.lang.Integer value){this.flgattivoout=value;}
    public void setIduoout(java.math.BigDecimal value){this.iduoout=value;}
    public void setLivelliuoout(java.lang.String value){this.livelliuoout=value;}
    public void setDesuoout(java.lang.String value){this.desuoout=value;}
    public void setFlgvisibsottouoout(java.lang.Integer value){this.flgvisibsottouoout=value;}
    public void setFlggestsottouoout(java.lang.Integer value){this.flggestsottouoout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    