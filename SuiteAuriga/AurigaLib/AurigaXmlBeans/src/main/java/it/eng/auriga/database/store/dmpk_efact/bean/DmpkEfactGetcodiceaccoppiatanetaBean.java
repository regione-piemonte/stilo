/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkEfactGetcodiceaccoppiatanetaBean")
public class DmpkEfactGetcodiceaccoppiatanetaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_EFACT_GETCODICEACCOPPIATANETA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String tipo_fornitura_in;
	private java.lang.String tipo_servizio_in;
	private java.lang.String tipo_offerta_in;
	private java.lang.String tipo_trattamento_in;
	private java.lang.String flg_energia_verde_in;
	private java.lang.String flg_residenza_in;
	private java.lang.String potenza_in;
	private java.lang.String codiceaccoppiataout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getTipo_fornitura_in(){return tipo_fornitura_in;}
    public java.lang.String getTipo_servizio_in(){return tipo_servizio_in;}
    public java.lang.String getTipo_offerta_in(){return tipo_offerta_in;}
    public java.lang.String getTipo_trattamento_in(){return tipo_trattamento_in;}
    public java.lang.String getFlg_energia_verde_in(){return flg_energia_verde_in;}
    public java.lang.String getFlg_residenza_in(){return flg_residenza_in;}
    public java.lang.String getPotenza_in(){return potenza_in;}
    public java.lang.String getCodiceaccoppiataout(){return codiceaccoppiataout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setTipo_fornitura_in(java.lang.String value){this.tipo_fornitura_in=value;}
    public void setTipo_servizio_in(java.lang.String value){this.tipo_servizio_in=value;}
    public void setTipo_offerta_in(java.lang.String value){this.tipo_offerta_in=value;}
    public void setTipo_trattamento_in(java.lang.String value){this.tipo_trattamento_in=value;}
    public void setFlg_energia_verde_in(java.lang.String value){this.flg_energia_verde_in=value;}
    public void setFlg_residenza_in(java.lang.String value){this.flg_residenza_in=value;}
    public void setPotenza_in(java.lang.String value){this.potenza_in=value;}
    public void setCodiceaccoppiataout(java.lang.String value){this.codiceaccoppiataout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    