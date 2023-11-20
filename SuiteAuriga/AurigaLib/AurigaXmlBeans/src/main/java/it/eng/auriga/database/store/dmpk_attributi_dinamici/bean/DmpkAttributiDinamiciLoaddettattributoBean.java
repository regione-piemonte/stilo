/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAttributiDinamiciLoaddettattributoBean")
public class DmpkAttributiDinamiciLoaddettattributoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_ATTRIBUTI_DINAMICI_LOADDETTATTRIBUTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String nomeio;
	private java.lang.String labelout;
	private java.lang.String tipoout;
	private java.lang.String subtipoout;
	private java.lang.String descrizioneout;
	private java.lang.String nomeattrappout;
	private java.lang.String labelattrappout;
	private java.math.BigDecimal nroordinattrappout;
	private java.math.BigDecimal nrorigainattrappout;
	private java.math.BigDecimal flgobbliginattrappout;
	private java.math.BigDecimal nrocifrecaratteriout;
	private java.math.BigDecimal precisionedecimaleout;
	private java.lang.String formatonumericoout;
	private java.lang.String valoreminout;
	private java.lang.String valoremaxout;
	private java.lang.String restrizionisulcaseout;
	private java.math.BigDecimal larghguiout;
	private java.math.BigDecimal altezzaguiout;
	private java.lang.String valoredefaultout;
	private java.lang.String querypervaloripossibiliout;
	private java.lang.String urlwsvaloripossibiliout;
	private java.lang.String xmlinwsvaloripossibiliout;
	private java.math.BigDecimal flgdaindicizzareout;
	private java.math.BigDecimal flgprotectedout;
	private java.math.BigDecimal flgvaloriunivociout;
	private java.lang.String regularexprout;
	private java.lang.String xmlvaloripossibiliout;
	private java.lang.String xmlaclout;
	private java.lang.String nomeidlookupout;
	private java.lang.String deslookupout;
	private java.math.BigDecimal nrocollookupout;
	private java.lang.String descollookupout;
	private java.lang.String filtroinlookupout;
	private java.lang.Integer flgsolovaldalookupout;
	private java.lang.String rowidout;
	private java.lang.Integer flgutilizzatoout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaclout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getNomeio(){return nomeio;}
    public java.lang.String getLabelout(){return labelout;}
    public java.lang.String getTipoout(){return tipoout;}
    public java.lang.String getSubtipoout(){return subtipoout;}
    public java.lang.String getDescrizioneout(){return descrizioneout;}
    public java.lang.String getNomeattrappout(){return nomeattrappout;}
    public java.lang.String getLabelattrappout(){return labelattrappout;}
    public java.math.BigDecimal getNroordinattrappout(){return nroordinattrappout;}
    public java.math.BigDecimal getNrorigainattrappout(){return nrorigainattrappout;}
    public java.math.BigDecimal getFlgobbliginattrappout(){return flgobbliginattrappout;}
    public java.math.BigDecimal getNrocifrecaratteriout(){return nrocifrecaratteriout;}
    public java.math.BigDecimal getPrecisionedecimaleout(){return precisionedecimaleout;}
    public java.lang.String getFormatonumericoout(){return formatonumericoout;}
    public java.lang.String getValoreminout(){return valoreminout;}
    public java.lang.String getValoremaxout(){return valoremaxout;}
    public java.lang.String getRestrizionisulcaseout(){return restrizionisulcaseout;}
    public java.math.BigDecimal getLarghguiout(){return larghguiout;}
    public java.math.BigDecimal getAltezzaguiout(){return altezzaguiout;}
    public java.lang.String getValoredefaultout(){return valoredefaultout;}
    public java.lang.String getQuerypervaloripossibiliout(){return querypervaloripossibiliout;}
    public java.lang.String getUrlwsvaloripossibiliout(){return urlwsvaloripossibiliout;}
    public java.lang.String getXmlinwsvaloripossibiliout(){return xmlinwsvaloripossibiliout;}
    public java.math.BigDecimal getFlgdaindicizzareout(){return flgdaindicizzareout;}
    public java.math.BigDecimal getFlgprotectedout(){return flgprotectedout;}
    public java.math.BigDecimal getFlgvaloriunivociout(){return flgvaloriunivociout;}
    public java.lang.String getRegularexprout(){return regularexprout;}
    public java.lang.String getXmlvaloripossibiliout(){return xmlvaloripossibiliout;}
    public java.lang.String getXmlaclout(){return xmlaclout;}
    public java.lang.String getNomeidlookupout(){return nomeidlookupout;}
    public java.lang.String getDeslookupout(){return deslookupout;}
    public java.math.BigDecimal getNrocollookupout(){return nrocollookupout;}
    public java.lang.String getDescollookupout(){return descollookupout;}
    public java.lang.String getFiltroinlookupout(){return filtroinlookupout;}
    public java.lang.Integer getFlgsolovaldalookupout(){return flgsolovaldalookupout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.Integer getFlgutilizzatoout(){return flgutilizzatoout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaclout(){return flgmostraaclout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setNomeio(java.lang.String value){this.nomeio=value;}
    public void setLabelout(java.lang.String value){this.labelout=value;}
    public void setTipoout(java.lang.String value){this.tipoout=value;}
    public void setSubtipoout(java.lang.String value){this.subtipoout=value;}
    public void setDescrizioneout(java.lang.String value){this.descrizioneout=value;}
    public void setNomeattrappout(java.lang.String value){this.nomeattrappout=value;}
    public void setLabelattrappout(java.lang.String value){this.labelattrappout=value;}
    public void setNroordinattrappout(java.math.BigDecimal value){this.nroordinattrappout=value;}
    public void setNrorigainattrappout(java.math.BigDecimal value){this.nrorigainattrappout=value;}
    public void setFlgobbliginattrappout(java.math.BigDecimal value){this.flgobbliginattrappout=value;}
    public void setNrocifrecaratteriout(java.math.BigDecimal value){this.nrocifrecaratteriout=value;}
    public void setPrecisionedecimaleout(java.math.BigDecimal value){this.precisionedecimaleout=value;}
    public void setFormatonumericoout(java.lang.String value){this.formatonumericoout=value;}
    public void setValoreminout(java.lang.String value){this.valoreminout=value;}
    public void setValoremaxout(java.lang.String value){this.valoremaxout=value;}
    public void setRestrizionisulcaseout(java.lang.String value){this.restrizionisulcaseout=value;}
    public void setLarghguiout(java.math.BigDecimal value){this.larghguiout=value;}
    public void setAltezzaguiout(java.math.BigDecimal value){this.altezzaguiout=value;}
    public void setValoredefaultout(java.lang.String value){this.valoredefaultout=value;}
    public void setQuerypervaloripossibiliout(java.lang.String value){this.querypervaloripossibiliout=value;}
    public void setUrlwsvaloripossibiliout(java.lang.String value){this.urlwsvaloripossibiliout=value;}
    public void setXmlinwsvaloripossibiliout(java.lang.String value){this.xmlinwsvaloripossibiliout=value;}
    public void setFlgdaindicizzareout(java.math.BigDecimal value){this.flgdaindicizzareout=value;}
    public void setFlgprotectedout(java.math.BigDecimal value){this.flgprotectedout=value;}
    public void setFlgvaloriunivociout(java.math.BigDecimal value){this.flgvaloriunivociout=value;}
    public void setRegularexprout(java.lang.String value){this.regularexprout=value;}
    public void setXmlvaloripossibiliout(java.lang.String value){this.xmlvaloripossibiliout=value;}
    public void setXmlaclout(java.lang.String value){this.xmlaclout=value;}
    public void setNomeidlookupout(java.lang.String value){this.nomeidlookupout=value;}
    public void setDeslookupout(java.lang.String value){this.deslookupout=value;}
    public void setNrocollookupout(java.math.BigDecimal value){this.nrocollookupout=value;}
    public void setDescollookupout(java.lang.String value){this.descollookupout=value;}
    public void setFiltroinlookupout(java.lang.String value){this.filtroinlookupout=value;}
    public void setFlgsolovaldalookupout(java.lang.Integer value){this.flgsolovaldalookupout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setFlgutilizzatoout(java.lang.Integer value){this.flgutilizzatoout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaclout(java.lang.Integer value){this.flgmostraaclout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    