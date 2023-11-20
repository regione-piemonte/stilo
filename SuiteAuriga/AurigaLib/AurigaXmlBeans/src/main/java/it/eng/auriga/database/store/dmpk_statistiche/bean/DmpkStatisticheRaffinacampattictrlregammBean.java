/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkStatisticheRaffinacampattictrlregammBean")
public class DmpkStatisticheRaffinacampattictrlregammBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_STATISTICHE_RAFFINACAMPATTICTRLREGAMM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String tiporaggruppamentoin;
	private java.math.BigDecimal percentualein;
	private java.lang.String filtroin;
	private java.lang.String gruppiattiin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getTiporaggruppamentoin(){return tiporaggruppamentoin;}
    public java.math.BigDecimal getPercentualein(){return percentualein;}
    public java.lang.String getFiltroin(){return filtroin;}
    public java.lang.String getGruppiattiin(){return gruppiattiin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setTiporaggruppamentoin(java.lang.String value){this.tiporaggruppamentoin=value;}
    public void setPercentualein(java.math.BigDecimal value){this.percentualein=value;}
    public void setFiltroin(java.lang.String value){this.filtroin=value;}
    public void setGruppiattiin(java.lang.String value){this.gruppiattiin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    