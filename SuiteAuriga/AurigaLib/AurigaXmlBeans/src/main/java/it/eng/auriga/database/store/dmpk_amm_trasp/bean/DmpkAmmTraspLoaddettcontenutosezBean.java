/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspLoaddettcontenutosezBean")
public class DmpkAmmTraspLoaddettcontenutosezBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_LOADDETTCONTENUTOSEZ";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idcontenutosezin;
	private java.math.BigDecimal idsezioneio;
	private java.lang.String tipocontenutoio;
	private java.lang.Integer nroordineinsezout;
	private java.lang.String titoloout;
	private java.lang.String testohtmlsezout;
	private java.lang.String testohtmlindettaglioout;
	private java.lang.Integer flgtestihtmlugualiout;
	private java.lang.Integer flgmostradatiaggout;
	private java.lang.String datiaggout;
	private java.lang.Integer flgexportopendataout;
	private java.lang.Integer flgpaginadedicataout;
	private java.lang.Integer idudout;
	private java.lang.String codcategoriaregout;
	private java.lang.String siglaregistroregout;
	private java.math.BigDecimal annoregout;
	private java.lang.String dataregout;
	private java.math.BigDecimal nroregout;
	private java.math.BigDecimal iddocprimarioout;
	private java.math.BigDecimal nroverfileprimarioout;
	private java.lang.String datifileprimarioout;
	private java.lang.String datifileallegatiout;
	private java.lang.String infostrutturatabout;
	private java.math.BigDecimal nrorecpubblintabellaout;
	private java.math.BigDecimal nrorecdapubblicareout;
	private java.lang.String dtpubbldalout;
	private java.lang.String dtpubblalout;
	private java.lang.String statorichpubblout;
	private java.lang.String motivorigettoout;
	private java.lang.Integer flgabilauotorizzrichout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdcontenutosezin(){return idcontenutosezin;}
    public java.math.BigDecimal getIdsezioneio(){return idsezioneio;}
    public java.lang.String getTipocontenutoio(){return tipocontenutoio;}
    public java.lang.Integer getNroordineinsezout(){return nroordineinsezout;}
    public java.lang.String getTitoloout(){return titoloout;}
    public java.lang.String getTestohtmlsezout(){return testohtmlsezout;}
    public java.lang.String getTestohtmlindettaglioout(){return testohtmlindettaglioout;}
    public java.lang.Integer getFlgtestihtmlugualiout(){return flgtestihtmlugualiout;}
    public java.lang.Integer getFlgmostradatiaggout(){return flgmostradatiaggout;}
    public java.lang.String getDatiaggout(){return datiaggout;}
    public java.lang.Integer getFlgexportopendataout(){return flgexportopendataout;}
    public java.lang.Integer getFlgpaginadedicataout(){return flgpaginadedicataout;}
    public java.lang.Integer getIdudout(){return idudout;}
    public java.lang.String getCodcategoriaregout(){return codcategoriaregout;}
    public java.lang.String getSiglaregistroregout(){return siglaregistroregout;}
    public java.math.BigDecimal getAnnoregout(){return annoregout;}
    public java.lang.String getDataregout(){return dataregout;}
    public java.math.BigDecimal getNroregout(){return nroregout;}
    public java.math.BigDecimal getIddocprimarioout(){return iddocprimarioout;}
    public java.math.BigDecimal getNroverfileprimarioout(){return nroverfileprimarioout;}
    public java.lang.String getDatifileprimarioout(){return datifileprimarioout;}
    public java.lang.String getDatifileallegatiout(){return datifileallegatiout;}
    public java.lang.String getInfostrutturatabout(){return infostrutturatabout;}
    public java.math.BigDecimal getNrorecpubblintabellaout(){return nrorecpubblintabellaout;}
    public java.math.BigDecimal getNrorecdapubblicareout(){return nrorecdapubblicareout;}
    public java.lang.String getDtpubbldalout(){return dtpubbldalout;}
    public java.lang.String getDtpubblalout(){return dtpubblalout;}
    public java.lang.String getStatorichpubblout(){return statorichpubblout;}
    public java.lang.String getMotivorigettoout(){return motivorigettoout;}
    public java.lang.Integer getFlgabilauotorizzrichout(){return flgabilauotorizzrichout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdcontenutosezin(java.math.BigDecimal value){this.idcontenutosezin=value;}
    public void setIdsezioneio(java.math.BigDecimal value){this.idsezioneio=value;}
    public void setTipocontenutoio(java.lang.String value){this.tipocontenutoio=value;}
    public void setNroordineinsezout(java.lang.Integer value){this.nroordineinsezout=value;}
    public void setTitoloout(java.lang.String value){this.titoloout=value;}
    public void setTestohtmlsezout(java.lang.String value){this.testohtmlsezout=value;}
    public void setTestohtmlindettaglioout(java.lang.String value){this.testohtmlindettaglioout=value;}
    public void setFlgtestihtmlugualiout(java.lang.Integer value){this.flgtestihtmlugualiout=value;}
    public void setFlgmostradatiaggout(java.lang.Integer value){this.flgmostradatiaggout=value;}
    public void setDatiaggout(java.lang.String value){this.datiaggout=value;}
    public void setFlgexportopendataout(java.lang.Integer value){this.flgexportopendataout=value;}
    public void setFlgpaginadedicataout(java.lang.Integer value){this.flgpaginadedicataout=value;}
    public void setIdudout(java.lang.Integer value){this.idudout=value;}
    public void setCodcategoriaregout(java.lang.String value){this.codcategoriaregout=value;}
    public void setSiglaregistroregout(java.lang.String value){this.siglaregistroregout=value;}
    public void setAnnoregout(java.math.BigDecimal value){this.annoregout=value;}
    public void setDataregout(java.lang.String value){this.dataregout=value;}
    public void setNroregout(java.math.BigDecimal value){this.nroregout=value;}
    public void setIddocprimarioout(java.math.BigDecimal value){this.iddocprimarioout=value;}
    public void setNroverfileprimarioout(java.math.BigDecimal value){this.nroverfileprimarioout=value;}
    public void setDatifileprimarioout(java.lang.String value){this.datifileprimarioout=value;}
    public void setDatifileallegatiout(java.lang.String value){this.datifileallegatiout=value;}
    public void setInfostrutturatabout(java.lang.String value){this.infostrutturatabout=value;}
    public void setNrorecpubblintabellaout(java.math.BigDecimal value){this.nrorecpubblintabellaout=value;}
    public void setNrorecdapubblicareout(java.math.BigDecimal value){this.nrorecdapubblicareout=value;}
    public void setDtpubbldalout(java.lang.String value){this.dtpubbldalout=value;}
    public void setDtpubblalout(java.lang.String value){this.dtpubblalout=value;}
    public void setStatorichpubblout(java.lang.String value){this.statorichpubblout=value;}
    public void setMotivorigettoout(java.lang.String value){this.motivorigettoout=value;}
    public void setFlgabilauotorizzrichout(java.lang.Integer value){this.flgabilauotorizzrichout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    