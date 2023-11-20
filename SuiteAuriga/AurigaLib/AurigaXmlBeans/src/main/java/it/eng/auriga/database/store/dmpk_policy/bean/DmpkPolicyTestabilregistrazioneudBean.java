/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkPolicyTestabilregistrazioneudBean")
public class DmpkPolicyTestabilregistrazioneudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_POLICY_TESTABILREGISTRAZIONEUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.Integer flgtpdominioautin;
	private java.math.BigDecimal iddominioautin;
	private java.math.BigDecimal iduserin;
	private java.lang.String codcategoriain;
	private java.lang.String siglaregin;
	private java.lang.String tsregin;
	private java.lang.Integer flgctrldoctypein;
	private java.math.BigDecimal iddoctypein;
	private java.lang.Integer flgtpreggestinternaout;
	private java.util.Date dtiniziogestinternaout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.Integer getFlgtpdominioautin(){return flgtpdominioautin;}
    public java.math.BigDecimal getIddominioautin(){return iddominioautin;}
    public java.math.BigDecimal getIduserin(){return iduserin;}
    public java.lang.String getCodcategoriain(){return codcategoriain;}
    public java.lang.String getSiglaregin(){return siglaregin;}
    public java.lang.String getTsregin(){return tsregin;}
    public java.lang.Integer getFlgctrldoctypein(){return flgctrldoctypein;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    public java.lang.Integer getFlgtpreggestinternaout(){return flgtpreggestinternaout;}
    public java.util.Date getDtiniziogestinternaout(){return dtiniziogestinternaout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setFlgtpdominioautin(java.lang.Integer value){this.flgtpdominioautin=value;}
    public void setIddominioautin(java.math.BigDecimal value){this.iddominioautin=value;}
    public void setIduserin(java.math.BigDecimal value){this.iduserin=value;}
    public void setCodcategoriain(java.lang.String value){this.codcategoriain=value;}
    public void setSiglaregin(java.lang.String value){this.siglaregin=value;}
    public void setTsregin(java.lang.String value){this.tsregin=value;}
    public void setFlgctrldoctypein(java.lang.Integer value){this.flgctrldoctypein=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    public void setFlgtpreggestinternaout(java.lang.Integer value){this.flgtpreggestinternaout=value;}
    public void setDtiniziogestinternaout(java.util.Date value){this.dtiniziogestinternaout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    