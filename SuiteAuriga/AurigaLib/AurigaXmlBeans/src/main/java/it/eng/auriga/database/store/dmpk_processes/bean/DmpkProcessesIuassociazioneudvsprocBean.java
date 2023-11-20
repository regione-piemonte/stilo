/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesIuassociazioneudvsprocBean")
public class DmpkProcessesIuassociazioneudvsprocBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_IUASSOCIAZIONEUDVSPROC";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.math.BigDecimal idudin;
	private java.lang.Integer nroordinevsprocin;
	private java.lang.String flgacqprodin;
	private java.lang.String codruolodocinprocin;
	private java.lang.String codstatoudinprocin;
	private java.lang.String noteassociazionein;
	private java.lang.Integer flglockedin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.Integer getNroordinevsprocin(){return nroordinevsprocin;}
    public java.lang.String getFlgacqprodin(){return flgacqprodin;}
    public java.lang.String getCodruolodocinprocin(){return codruolodocinprocin;}
    public java.lang.String getCodstatoudinprocin(){return codstatoudinprocin;}
    public java.lang.String getNoteassociazionein(){return noteassociazionein;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setNroordinevsprocin(java.lang.Integer value){this.nroordinevsprocin=value;}
    public void setFlgacqprodin(java.lang.String value){this.flgacqprodin=value;}
    public void setCodruolodocinprocin(java.lang.String value){this.codruolodocinprocin=value;}
    public void setCodstatoudinprocin(java.lang.String value){this.codstatoudinprocin=value;}
    public void setNoteassociazionein(java.lang.String value){this.noteassociazionein=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
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