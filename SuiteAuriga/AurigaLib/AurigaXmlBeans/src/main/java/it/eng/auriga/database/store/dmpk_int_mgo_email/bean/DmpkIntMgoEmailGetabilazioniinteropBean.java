/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailGetabilazioniinteropBean")
public class DmpkIntMgoEmailGetabilazioniinteropBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_GETABILAZIONIINTEROP";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idemailin;
	private java.lang.Integer flgabilprotentrataout;
	private java.lang.Integer flgabilinvioeccezioneout;
	private java.lang.Integer flgabilinvioconfermaout;
	private java.lang.Integer flgabilinvioaggiornamentoout;
	private java.lang.Integer flgabilinvioannullamentoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.lang.Integer getFlgabilprotentrataout(){return flgabilprotentrataout;}
    public java.lang.Integer getFlgabilinvioeccezioneout(){return flgabilinvioeccezioneout;}
    public java.lang.Integer getFlgabilinvioconfermaout(){return flgabilinvioconfermaout;}
    public java.lang.Integer getFlgabilinvioaggiornamentoout(){return flgabilinvioaggiornamentoout;}
    public java.lang.Integer getFlgabilinvioannullamentoout(){return flgabilinvioannullamentoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setFlgabilprotentrataout(java.lang.Integer value){this.flgabilprotentrataout=value;}
    public void setFlgabilinvioeccezioneout(java.lang.Integer value){this.flgabilinvioeccezioneout=value;}
    public void setFlgabilinvioconfermaout(java.lang.Integer value){this.flgabilinvioconfermaout=value;}
    public void setFlgabilinvioaggiornamentoout(java.lang.Integer value){this.flgabilinvioaggiornamentoout=value;}
    public void setFlgabilinvioannullamentoout(java.lang.Integer value){this.flgabilinvioannullamentoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    