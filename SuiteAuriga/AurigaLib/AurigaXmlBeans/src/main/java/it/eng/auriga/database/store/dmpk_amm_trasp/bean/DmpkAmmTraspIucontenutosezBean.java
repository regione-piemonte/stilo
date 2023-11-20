/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkAmmTraspIucontenutosezBean")
public class DmpkAmmTraspIucontenutosezBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_AMM_TRASP_IUCONTENUTOSEZ";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idcontenutosezio;
	private java.math.BigDecimal idsezionein;
	private java.lang.String tipocontenutoin;
	private java.lang.Integer nroordineinsezin;
	private java.lang.String titoloin;
	private java.lang.String testohtmlsezin;
	private java.lang.String testohtmlindettaglioin;
	private java.lang.Integer flgtestihtmlugualiin;
	private java.lang.Integer flgmostradatiaggin;
	private java.math.BigDecimal flgexportopendatain;
	private java.math.BigDecimal flgpaginadedicatain;
	private java.lang.String datifilesemplicedasalvarein;
	private java.math.BigDecimal iddocprimarioin;
	private java.math.BigDecimal nroverfileprimarioin;
	private java.lang.String infostrutturatabin;
	private java.lang.String dtpubbldalin;
	private java.lang.String dtpubblalin;
	private java.lang.String statorichpubblin;
	private java.lang.String motivorigettoin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdcontenutosezio(){return idcontenutosezio;}
    public java.math.BigDecimal getIdsezionein(){return idsezionein;}
    public java.lang.String getTipocontenutoin(){return tipocontenutoin;}
    public java.lang.Integer getNroordineinsezin(){return nroordineinsezin;}
    public java.lang.String getTitoloin(){return titoloin;}
    public java.lang.String getTestohtmlsezin(){return testohtmlsezin;}
    public java.lang.String getTestohtmlindettaglioin(){return testohtmlindettaglioin;}
    public java.lang.Integer getFlgtestihtmlugualiin(){return flgtestihtmlugualiin;}
    public java.lang.Integer getFlgmostradatiaggin(){return flgmostradatiaggin;}
    public java.math.BigDecimal getFlgexportopendatain(){return flgexportopendatain;}
    public java.math.BigDecimal getFlgpaginadedicatain(){return flgpaginadedicatain;}
    public java.lang.String getDatifilesemplicedasalvarein(){return datifilesemplicedasalvarein;}
    public java.math.BigDecimal getIddocprimarioin(){return iddocprimarioin;}
    public java.math.BigDecimal getNroverfileprimarioin(){return nroverfileprimarioin;}
    public java.lang.String getInfostrutturatabin(){return infostrutturatabin;}
    public java.lang.String getDtpubbldalin(){return dtpubbldalin;}
    public java.lang.String getDtpubblalin(){return dtpubblalin;}
    public java.lang.String getStatorichpubblin(){return statorichpubblin;}
    public java.lang.String getMotivorigettoin(){return motivorigettoin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdcontenutosezio(java.math.BigDecimal value){this.idcontenutosezio=value;}
    public void setIdsezionein(java.math.BigDecimal value){this.idsezionein=value;}
    public void setTipocontenutoin(java.lang.String value){this.tipocontenutoin=value;}
    public void setNroordineinsezin(java.lang.Integer value){this.nroordineinsezin=value;}
    public void setTitoloin(java.lang.String value){this.titoloin=value;}
    public void setTestohtmlsezin(java.lang.String value){this.testohtmlsezin=value;}
    public void setTestohtmlindettaglioin(java.lang.String value){this.testohtmlindettaglioin=value;}
    public void setFlgtestihtmlugualiin(java.lang.Integer value){this.flgtestihtmlugualiin=value;}
    public void setFlgmostradatiaggin(java.lang.Integer value){this.flgmostradatiaggin=value;}
    public void setFlgexportopendatain(java.math.BigDecimal value){this.flgexportopendatain=value;}
    public void setFlgpaginadedicatain(java.math.BigDecimal value){this.flgpaginadedicatain=value;}
    public void setDatifilesemplicedasalvarein(java.lang.String value){this.datifilesemplicedasalvarein=value;}
    public void setIddocprimarioin(java.math.BigDecimal value){this.iddocprimarioin=value;}
    public void setNroverfileprimarioin(java.math.BigDecimal value){this.nroverfileprimarioin=value;}
    public void setInfostrutturatabin(java.lang.String value){this.infostrutturatabin=value;}
    public void setDtpubbldalin(java.lang.String value){this.dtpubbldalin=value;}
    public void setDtpubblalin(java.lang.String value){this.dtpubblalin=value;}
    public void setStatorichpubblin(java.lang.String value){this.statorichpubblin=value;}
    public void setMotivorigettoin(java.lang.String value){this.motivorigettoin=value;}
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