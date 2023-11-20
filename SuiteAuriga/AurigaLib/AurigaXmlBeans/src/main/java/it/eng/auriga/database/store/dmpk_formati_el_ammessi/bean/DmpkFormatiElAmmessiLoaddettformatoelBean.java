/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkFormatiElAmmessiLoaddettformatoelBean")
public class DmpkFormatiElAmmessiLoaddettformatoelBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_FORMATI_EL_AMMESSI_LOADDETTFORMATOEL";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idformatoelio;
	private java.lang.String nomeformatoelout;
	private java.lang.String estensioneformatoelout;
	private java.lang.String dtinizioammout;
	private java.lang.String dtfineammout;
	private java.math.BigDecimal flgbinarioout;
	private java.math.BigDecimal flgdaindicizzareout;
	private java.lang.String ciprovformatoelout;
	private java.lang.String mimetypeout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdformatoelio(){return idformatoelio;}
    public java.lang.String getNomeformatoelout(){return nomeformatoelout;}
    public java.lang.String getEstensioneformatoelout(){return estensioneformatoelout;}
    public java.lang.String getDtinizioammout(){return dtinizioammout;}
    public java.lang.String getDtfineammout(){return dtfineammout;}
    public java.math.BigDecimal getFlgbinarioout(){return flgbinarioout;}
    public java.math.BigDecimal getFlgdaindicizzareout(){return flgdaindicizzareout;}
    public java.lang.String getCiprovformatoelout(){return ciprovformatoelout;}
    public java.lang.String getMimetypeout(){return mimetypeout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdformatoelio(java.math.BigDecimal value){this.idformatoelio=value;}
    public void setNomeformatoelout(java.lang.String value){this.nomeformatoelout=value;}
    public void setEstensioneformatoelout(java.lang.String value){this.estensioneformatoelout=value;}
    public void setDtinizioammout(java.lang.String value){this.dtinizioammout=value;}
    public void setDtfineammout(java.lang.String value){this.dtfineammout=value;}
    public void setFlgbinarioout(java.math.BigDecimal value){this.flgbinarioout=value;}
    public void setFlgdaindicizzareout(java.math.BigDecimal value){this.flgdaindicizzareout=value;}
    public void setCiprovformatoelout(java.lang.String value){this.ciprovformatoelout=value;}
    public void setMimetypeout(java.lang.String value){this.mimetypeout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    