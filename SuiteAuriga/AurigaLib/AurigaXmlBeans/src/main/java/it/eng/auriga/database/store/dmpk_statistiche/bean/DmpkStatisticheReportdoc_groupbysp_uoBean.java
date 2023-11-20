/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkStatisticheReportdoc_groupbysp_uoBean")
public class DmpkStatisticheReportdoc_groupbysp_uoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_STATISTICHE_REPORTDOC_GROUPBYSP_UO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String tiporeportin;
	private java.lang.Integer livellodettreportin;
	private java.lang.Integer idspaoouoreportonin;
	private java.lang.String datadain;
	private java.lang.String dataain;
	private java.lang.String tipiregistrazioniin;
	private java.lang.String reporttitleout;
	private java.lang.String reportcontentsxmlout;
	private java.lang.Integer nrorecordout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getTiporeportin(){return tiporeportin;}
    public java.lang.Integer getLivellodettreportin(){return livellodettreportin;}
    public java.lang.Integer getIdspaoouoreportonin(){return idspaoouoreportonin;}
    public java.lang.String getDatadain(){return datadain;}
    public java.lang.String getDataain(){return dataain;}
    public java.lang.String getTipiregistrazioniin(){return tipiregistrazioniin;}
    public java.lang.String getReporttitleout(){return reporttitleout;}
    public java.lang.String getReportcontentsxmlout(){return reportcontentsxmlout;}
    public java.lang.Integer getNrorecordout(){return nrorecordout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setTiporeportin(java.lang.String value){this.tiporeportin=value;}
    public void setLivellodettreportin(java.lang.Integer value){this.livellodettreportin=value;}
    public void setIdspaoouoreportonin(java.lang.Integer value){this.idspaoouoreportonin=value;}
    public void setDatadain(java.lang.String value){this.datadain=value;}
    public void setDataain(java.lang.String value){this.dataain=value;}
    public void setTipiregistrazioniin(java.lang.String value){this.tipiregistrazioniin=value;}
    public void setReporttitleout(java.lang.String value){this.reporttitleout=value;}
    public void setReportcontentsxmlout(java.lang.String value){this.reportcontentsxmlout=value;}
    public void setNrorecordout(java.lang.Integer value){this.nrorecordout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    