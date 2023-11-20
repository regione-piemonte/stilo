/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkLoadComboDmfn_load_comboBean")
public class DmpkLoadComboDmfn_load_comboBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_LOAD_COMBO_DMFN_LOAD_COMBO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.lang.String tipocomboin;
	private java.math.BigDecimal flgsolovldin;
	private java.lang.String tsvldin;
	private java.lang.String pkrecin;
	private java.lang.String altriparametriin;
	private java.lang.String listaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.lang.String getTipocomboin(){return tipocomboin;}
    public java.math.BigDecimal getFlgsolovldin(){return flgsolovldin;}
    public java.lang.String getTsvldin(){return tsvldin;}
    public java.lang.String getPkrecin(){return pkrecin;}
    public java.lang.String getAltriparametriin(){return altriparametriin;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setTipocomboin(java.lang.String value){this.tipocomboin=value;}
    public void setFlgsolovldin(java.math.BigDecimal value){this.flgsolovldin=value;}
    public void setTsvldin(java.lang.String value){this.tsvldin=value;}
    public void setPkrecin(java.lang.String value){this.pkrecin=value;}
    public void setAltriparametriin(java.lang.String value){this.altriparametriin=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    