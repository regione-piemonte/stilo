/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesLoaddetteventoderivatoBean")
public class DmpkProcessTypesLoaddetteventoderivatoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_LOADDETTEVENTODERIVATO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String rowideventoderivio;
	private java.math.BigDecimal ideventtypemasterio;
	private java.math.BigDecimal idprocesstypeio;
	private java.lang.String deseventtypemasterout;
	private java.lang.Integer flgdurativoevtmasterout;
	private java.lang.String nomeprocesstypeout;
	private java.lang.String flgtipoevtderivout;
	private java.lang.String tipodataderivout;
	private java.lang.String dettdataderivout;
	private java.lang.String listaesitiderivout;
	private java.math.BigDecimal duratamaxevtderivout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getRowideventoderivio(){return rowideventoderivio;}
    public java.math.BigDecimal getIdeventtypemasterio(){return ideventtypemasterio;}
    public java.math.BigDecimal getIdprocesstypeio(){return idprocesstypeio;}
    public java.lang.String getDeseventtypemasterout(){return deseventtypemasterout;}
    public java.lang.Integer getFlgdurativoevtmasterout(){return flgdurativoevtmasterout;}
    public java.lang.String getNomeprocesstypeout(){return nomeprocesstypeout;}
    public java.lang.String getFlgtipoevtderivout(){return flgtipoevtderivout;}
    public java.lang.String getTipodataderivout(){return tipodataderivout;}
    public java.lang.String getDettdataderivout(){return dettdataderivout;}
    public java.lang.String getListaesitiderivout(){return listaesitiderivout;}
    public java.math.BigDecimal getDuratamaxevtderivout(){return duratamaxevtderivout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setRowideventoderivio(java.lang.String value){this.rowideventoderivio=value;}
    public void setIdeventtypemasterio(java.math.BigDecimal value){this.ideventtypemasterio=value;}
    public void setIdprocesstypeio(java.math.BigDecimal value){this.idprocesstypeio=value;}
    public void setDeseventtypemasterout(java.lang.String value){this.deseventtypemasterout=value;}
    public void setFlgdurativoevtmasterout(java.lang.Integer value){this.flgdurativoevtmasterout=value;}
    public void setNomeprocesstypeout(java.lang.String value){this.nomeprocesstypeout=value;}
    public void setFlgtipoevtderivout(java.lang.String value){this.flgtipoevtderivout=value;}
    public void setTipodataderivout(java.lang.String value){this.tipodataderivout=value;}
    public void setDettdataderivout(java.lang.String value){this.dettdataderivout=value;}
    public void setListaesitiderivout(java.lang.String value){this.listaesitiderivout=value;}
    public void setDuratamaxevtderivout(java.math.BigDecimal value){this.duratamaxevtderivout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    