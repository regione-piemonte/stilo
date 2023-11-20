/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWsTrovadocfolderBean")
public class DmpkWsTrovadocfolderBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WS_TROVADOCFOLDER";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String xmlin;
	private java.lang.String flgudfolderout;
	private java.math.BigDecimal cercainfolderout;
	private java.lang.Integer flgcercainsubfolderout;
	private java.lang.String filtrofulltextout;
	private java.lang.Integer flgtutteleparoleout;
	private java.lang.String attributixricercaftout;
	private java.lang.String criteriavanzatiout;
	private java.lang.String criteripersonalizzatiout;
	private java.lang.String colorderbyout;
	private java.lang.String flgdescorderbyout;
	private java.lang.Integer flgsenzapaginazioneout;
	private java.lang.Integer nropaginaout;
	private java.lang.Integer bachsizeout;
	private java.lang.Integer flgbatchsearchout;
	private java.lang.String coltoreturnout;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getXmlin(){return xmlin;}
    public java.lang.String getFlgudfolderout(){return flgudfolderout;}
    public java.math.BigDecimal getCercainfolderout(){return cercainfolderout;}
    public java.lang.Integer getFlgcercainsubfolderout(){return flgcercainsubfolderout;}
    public java.lang.String getFiltrofulltextout(){return filtrofulltextout;}
    public java.lang.Integer getFlgtutteleparoleout(){return flgtutteleparoleout;}
    public java.lang.String getAttributixricercaftout(){return attributixricercaftout;}
    public java.lang.String getCriteriavanzatiout(){return criteriavanzatiout;}
    public java.lang.String getCriteripersonalizzatiout(){return criteripersonalizzatiout;}
    public java.lang.String getColorderbyout(){return colorderbyout;}
    public java.lang.String getFlgdescorderbyout(){return flgdescorderbyout;}
    public java.lang.Integer getFlgsenzapaginazioneout(){return flgsenzapaginazioneout;}
    public java.lang.Integer getNropaginaout(){return nropaginaout;}
    public java.lang.Integer getBachsizeout(){return bachsizeout;}
    public java.lang.Integer getFlgbatchsearchout(){return flgbatchsearchout;}
    public java.lang.String getColtoreturnout(){return coltoreturnout;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setXmlin(java.lang.String value){this.xmlin=value;}
    public void setFlgudfolderout(java.lang.String value){this.flgudfolderout=value;}
    public void setCercainfolderout(java.math.BigDecimal value){this.cercainfolderout=value;}
    public void setFlgcercainsubfolderout(java.lang.Integer value){this.flgcercainsubfolderout=value;}
    public void setFiltrofulltextout(java.lang.String value){this.filtrofulltextout=value;}
    public void setFlgtutteleparoleout(java.lang.Integer value){this.flgtutteleparoleout=value;}
    public void setAttributixricercaftout(java.lang.String value){this.attributixricercaftout=value;}
    public void setCriteriavanzatiout(java.lang.String value){this.criteriavanzatiout=value;}
    public void setCriteripersonalizzatiout(java.lang.String value){this.criteripersonalizzatiout=value;}
    public void setColorderbyout(java.lang.String value){this.colorderbyout=value;}
    public void setFlgdescorderbyout(java.lang.String value){this.flgdescorderbyout=value;}
    public void setFlgsenzapaginazioneout(java.lang.Integer value){this.flgsenzapaginazioneout=value;}
    public void setNropaginaout(java.lang.Integer value){this.nropaginaout=value;}
    public void setBachsizeout(java.lang.Integer value){this.bachsizeout=value;}
    public void setFlgbatchsearchout(java.lang.Integer value){this.flgbatchsearchout=value;}
    public void setColtoreturnout(java.lang.String value){this.coltoreturnout=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    