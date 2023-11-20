/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkWfGetdatinuovoiterattocomecopiaBean")
public class DmpkWfGetdatinuovoiterattocomecopiaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_WF_GETDATINUOVOITERATTOCOMECOPIA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idproctypedaavviarein;
	private java.lang.String flgtiponumerazionein;
	private java.lang.String siglanumerazionein;
	private java.math.BigDecimal annonumerazionein;
	private java.math.BigDecimal numeroin;
	private java.math.BigDecimal iduddacopiareout;
	private java.math.BigDecimal idprocessdacopiareout;
	private java.math.BigDecimal flgdoctypediversoout;
	private java.math.BigDecimal iddoctypedacopiareout;
	private java.lang.String codcatregattodacopiareout;
	private java.lang.String siglaregattodacopiareout;
	private java.math.BigDecimal annoattodacopiareout;
	private java.math.BigDecimal numattodacopiareout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdproctypedaavviarein(){return idproctypedaavviarein;}
    public java.lang.String getFlgtiponumerazionein(){return flgtiponumerazionein;}
    public java.lang.String getSiglanumerazionein(){return siglanumerazionein;}
    public java.math.BigDecimal getAnnonumerazionein(){return annonumerazionein;}
    public java.math.BigDecimal getNumeroin(){return numeroin;}
    public java.math.BigDecimal getIduddacopiareout(){return iduddacopiareout;}
    public java.math.BigDecimal getIdprocessdacopiareout(){return idprocessdacopiareout;}
    public java.math.BigDecimal getFlgdoctypediversoout(){return flgdoctypediversoout;}
    public java.math.BigDecimal getIddoctypedacopiareout(){return iddoctypedacopiareout;}
    public java.lang.String getCodcatregattodacopiareout(){return codcatregattodacopiareout;}
    public java.lang.String getSiglaregattodacopiareout(){return siglaregattodacopiareout;}
    public java.math.BigDecimal getAnnoattodacopiareout(){return annoattodacopiareout;}
    public java.math.BigDecimal getNumattodacopiareout(){return numattodacopiareout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdproctypedaavviarein(java.math.BigDecimal value){this.idproctypedaavviarein=value;}
    public void setFlgtiponumerazionein(java.lang.String value){this.flgtiponumerazionein=value;}
    public void setSiglanumerazionein(java.lang.String value){this.siglanumerazionein=value;}
    public void setAnnonumerazionein(java.math.BigDecimal value){this.annonumerazionein=value;}
    public void setNumeroin(java.math.BigDecimal value){this.numeroin=value;}
    public void setIduddacopiareout(java.math.BigDecimal value){this.iduddacopiareout=value;}
    public void setIdprocessdacopiareout(java.math.BigDecimal value){this.idprocessdacopiareout=value;}
    public void setFlgdoctypediversoout(java.math.BigDecimal value){this.flgdoctypediversoout=value;}
    public void setIddoctypedacopiareout(java.math.BigDecimal value){this.iddoctypedacopiareout=value;}
    public void setCodcatregattodacopiareout(java.lang.String value){this.codcatregattodacopiareout=value;}
    public void setSiglaregattodacopiareout(java.lang.String value){this.siglaregattodacopiareout=value;}
    public void setAnnoattodacopiareout(java.math.BigDecimal value){this.annoattodacopiareout=value;}
    public void setNumattodacopiareout(java.math.BigDecimal value){this.numattodacopiareout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    