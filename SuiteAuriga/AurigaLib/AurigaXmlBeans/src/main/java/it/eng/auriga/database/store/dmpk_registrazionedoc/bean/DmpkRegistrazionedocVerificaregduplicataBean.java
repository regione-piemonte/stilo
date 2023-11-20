/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkRegistrazionedocVerificaregduplicataBean")
public class DmpkRegistrazionedocVerificaregduplicataBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_REGISTRAZIONEDOC_VERIFICAREGDUPLICATA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String xmldatiregin;
	private java.lang.Integer nromatchtrovatiout;
	private java.lang.String possibiliregduplicatexmlout;
	private java.lang.String statoduplicazioneout;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getXmldatiregin(){return xmldatiregin;}
    public java.lang.Integer getNromatchtrovatiout(){return nromatchtrovatiout;}
    public java.lang.String getPossibiliregduplicatexmlout(){return possibiliregduplicatexmlout;}
    public java.lang.String getStatoduplicazioneout(){return statoduplicazioneout;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setXmldatiregin(java.lang.String value){this.xmldatiregin=value;}
    public void setNromatchtrovatiout(java.lang.Integer value){this.nromatchtrovatiout=value;}
    public void setPossibiliregduplicatexmlout(java.lang.String value){this.possibiliregduplicatexmlout=value;}
    public void setStatoduplicazioneout(java.lang.String value){this.statoduplicazioneout=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    