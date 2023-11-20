/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoaddettreluouserBean")
public class DmpkDefSecurityLoaddettreluouserBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADDETTRELUOUSER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal iduoio;
	private java.lang.String nrilivelliuoio;
	private java.lang.String denominazioneuoio;
	private java.math.BigDecimal iduserio;
	private java.lang.String desuserio;
	private java.lang.String usernameio;
	private java.lang.String flgtiporelio;
	private java.lang.String dtiniziovldio;
	private java.lang.String dtfinevldout;
	private java.lang.Integer flginclsottouoout;
	private java.lang.Integer flginclscrivvirtout;
	private java.math.BigDecimal idscrivaniaout;
	private java.lang.String intestazionescrivaniaout;
	private java.math.BigDecimal idruoloammio;
	private java.lang.String desruoloammout;
	private java.lang.Integer flgprimarioconruoloout;
	private java.math.BigDecimal idprofiloout;
	private java.lang.String nomeprofiloout;
	private java.lang.String xmldatiprofiloout;
	private java.lang.String xmlgruppiprivout;
	private java.lang.String xmlgruppiappout;
	private java.lang.Integer flguoppout;
	private java.lang.String xmluoppout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.Integer flgpresentidocfascout;
	private java.lang.String flgtipodestdocout;
	private java.math.BigDecimal iduosvdestdocfascout;
	private java.lang.String livelliuodestdocfascout;
	private java.lang.String desuodestdocfascout;
	private java.lang.String statospostdocfascout;
	private java.lang.String tsiniziospostdocfascout;
	private java.lang.String tsfinespostdocfascout;
	private java.math.BigDecimal nrodocassout;
	private java.lang.String tsrilevnrodocassout;
	private java.math.BigDecimal nrofascassout;
	private java.lang.String tsrilevnrofascassout;
	private java.lang.Integer flgaccessodoclimsvout;
	private java.lang.Integer flgregistrazioneeout;
	private java.lang.Integer flgregistrazioneuiout;
	private java.lang.Integer flggestattiout;
	private java.lang.Integer flgvispropattiiniterout;
	private java.lang.Integer flggestattiallout;
	private java.lang.String listaidproctygestattiout;
	private java.lang.Integer flgvispropattiallout;
	private java.lang.String listaidproctyvispropattiout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIduoio(){return iduoio;}
    public java.lang.String getNrilivelliuoio(){return nrilivelliuoio;}
    public java.lang.String getDenominazioneuoio(){return denominazioneuoio;}
    public java.math.BigDecimal getIduserio(){return iduserio;}
    public java.lang.String getDesuserio(){return desuserio;}
    public java.lang.String getUsernameio(){return usernameio;}
    public java.lang.String getFlgtiporelio(){return flgtiporelio;}
    public java.lang.String getDtiniziovldio(){return dtiniziovldio;}
    public java.lang.String getDtfinevldout(){return dtfinevldout;}
    public java.lang.Integer getFlginclsottouoout(){return flginclsottouoout;}
    public java.lang.Integer getFlginclscrivvirtout(){return flginclscrivvirtout;}
    public java.math.BigDecimal getIdscrivaniaout(){return idscrivaniaout;}
    public java.lang.String getIntestazionescrivaniaout(){return intestazionescrivaniaout;}
    public java.math.BigDecimal getIdruoloammio(){return idruoloammio;}
    public java.lang.String getDesruoloammout(){return desruoloammout;}
    public java.lang.Integer getFlgprimarioconruoloout(){return flgprimarioconruoloout;}
    public java.math.BigDecimal getIdprofiloout(){return idprofiloout;}
    public java.lang.String getNomeprofiloout(){return nomeprofiloout;}
    public java.lang.String getXmldatiprofiloout(){return xmldatiprofiloout;}
    public java.lang.String getXmlgruppiprivout(){return xmlgruppiprivout;}
    public java.lang.String getXmlgruppiappout(){return xmlgruppiappout;}
    public java.lang.Integer getFlguoppout(){return flguoppout;}
    public java.lang.String getXmluoppout(){return xmluoppout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.Integer getFlgpresentidocfascout(){return flgpresentidocfascout;}
    public java.lang.String getFlgtipodestdocout(){return flgtipodestdocout;}
    public java.math.BigDecimal getIduosvdestdocfascout(){return iduosvdestdocfascout;}
    public java.lang.String getLivelliuodestdocfascout(){return livelliuodestdocfascout;}
    public java.lang.String getDesuodestdocfascout(){return desuodestdocfascout;}
    public java.lang.String getStatospostdocfascout(){return statospostdocfascout;}
    public java.lang.String getTsiniziospostdocfascout(){return tsiniziospostdocfascout;}
    public java.lang.String getTsfinespostdocfascout(){return tsfinespostdocfascout;}
    public java.math.BigDecimal getNrodocassout(){return nrodocassout;}
    public java.lang.String getTsrilevnrodocassout(){return tsrilevnrodocassout;}
    public java.math.BigDecimal getNrofascassout(){return nrofascassout;}
    public java.lang.String getTsrilevnrofascassout(){return tsrilevnrofascassout;}
    public java.lang.Integer getFlgaccessodoclimsvout(){return flgaccessodoclimsvout;}
    public java.lang.Integer getFlgregistrazioneeout(){return flgregistrazioneeout;}
    public java.lang.Integer getFlgregistrazioneuiout(){return flgregistrazioneuiout;}
    public java.lang.Integer getFlggestattiout(){return flggestattiout;}
    public java.lang.Integer getFlgvispropattiiniterout(){return flgvispropattiiniterout;}
    public java.lang.Integer getFlggestattiallout(){return flggestattiallout;}
    public java.lang.String getListaidproctygestattiout(){return listaidproctygestattiout;}
    public java.lang.Integer getFlgvispropattiallout(){return flgvispropattiallout;}
    public java.lang.String getListaidproctyvispropattiout(){return listaidproctyvispropattiout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIduoio(java.math.BigDecimal value){this.iduoio=value;}
    public void setNrilivelliuoio(java.lang.String value){this.nrilivelliuoio=value;}
    public void setDenominazioneuoio(java.lang.String value){this.denominazioneuoio=value;}
    public void setIduserio(java.math.BigDecimal value){this.iduserio=value;}
    public void setDesuserio(java.lang.String value){this.desuserio=value;}
    public void setUsernameio(java.lang.String value){this.usernameio=value;}
    public void setFlgtiporelio(java.lang.String value){this.flgtiporelio=value;}
    public void setDtiniziovldio(java.lang.String value){this.dtiniziovldio=value;}
    public void setDtfinevldout(java.lang.String value){this.dtfinevldout=value;}
    public void setFlginclsottouoout(java.lang.Integer value){this.flginclsottouoout=value;}
    public void setFlginclscrivvirtout(java.lang.Integer value){this.flginclscrivvirtout=value;}
    public void setIdscrivaniaout(java.math.BigDecimal value){this.idscrivaniaout=value;}
    public void setIntestazionescrivaniaout(java.lang.String value){this.intestazionescrivaniaout=value;}
    public void setIdruoloammio(java.math.BigDecimal value){this.idruoloammio=value;}
    public void setDesruoloammout(java.lang.String value){this.desruoloammout=value;}
    public void setFlgprimarioconruoloout(java.lang.Integer value){this.flgprimarioconruoloout=value;}
    public void setIdprofiloout(java.math.BigDecimal value){this.idprofiloout=value;}
    public void setNomeprofiloout(java.lang.String value){this.nomeprofiloout=value;}
    public void setXmldatiprofiloout(java.lang.String value){this.xmldatiprofiloout=value;}
    public void setXmlgruppiprivout(java.lang.String value){this.xmlgruppiprivout=value;}
    public void setXmlgruppiappout(java.lang.String value){this.xmlgruppiappout=value;}
    public void setFlguoppout(java.lang.Integer value){this.flguoppout=value;}
    public void setXmluoppout(java.lang.String value){this.xmluoppout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setFlgpresentidocfascout(java.lang.Integer value){this.flgpresentidocfascout=value;}
    public void setFlgtipodestdocout(java.lang.String value){this.flgtipodestdocout=value;}
    public void setIduosvdestdocfascout(java.math.BigDecimal value){this.iduosvdestdocfascout=value;}
    public void setLivelliuodestdocfascout(java.lang.String value){this.livelliuodestdocfascout=value;}
    public void setDesuodestdocfascout(java.lang.String value){this.desuodestdocfascout=value;}
    public void setStatospostdocfascout(java.lang.String value){this.statospostdocfascout=value;}
    public void setTsiniziospostdocfascout(java.lang.String value){this.tsiniziospostdocfascout=value;}
    public void setTsfinespostdocfascout(java.lang.String value){this.tsfinespostdocfascout=value;}
    public void setNrodocassout(java.math.BigDecimal value){this.nrodocassout=value;}
    public void setTsrilevnrodocassout(java.lang.String value){this.tsrilevnrodocassout=value;}
    public void setNrofascassout(java.math.BigDecimal value){this.nrofascassout=value;}
    public void setTsrilevnrofascassout(java.lang.String value){this.tsrilevnrofascassout=value;}
    public void setFlgaccessodoclimsvout(java.lang.Integer value){this.flgaccessodoclimsvout=value;}
    public void setFlgregistrazioneeout(java.lang.Integer value){this.flgregistrazioneeout=value;}
    public void setFlgregistrazioneuiout(java.lang.Integer value){this.flgregistrazioneuiout=value;}
    public void setFlggestattiout(java.lang.Integer value){this.flggestattiout=value;}
    public void setFlgvispropattiiniterout(java.lang.Integer value){this.flgvispropattiiniterout=value;}
    public void setFlggestattiallout(java.lang.Integer value){this.flggestattiallout=value;}
    public void setListaidproctygestattiout(java.lang.String value){this.listaidproctygestattiout=value;}
    public void setFlgvispropattiallout(java.lang.Integer value){this.flgvispropattiallout=value;}
    public void setListaidproctyvispropattiout(java.lang.String value){this.listaidproctyvispropattiout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    