/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesTrovaprocessudBean")
public class DmpkProcessesTrovaprocessudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_TROVAPROCESSUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.lang.Integer flgtpopersuudin;
	private java.lang.String activitynamein;
	private java.lang.String flgfmtestremiregio;
	private java.lang.Integer flgpreimpostafiltroin;
	private java.lang.String condsutipodocio;
	private java.lang.String flgacqprodio;
	private java.lang.String codruoloinprocio;
	private java.lang.String desruoloinprocio;
	private java.lang.String codstatoinprocio;
	private java.lang.String desstatoinprocio;
	private java.lang.Integer annoregio;
	private java.lang.Integer nroregio;
	private java.lang.String oggettodocio;
	private java.math.BigDecimal idudio;
	private java.math.BigDecimal iddocio;
	private java.lang.String colorderbyio;
	private java.lang.String flgdescorderbyio;
	private java.lang.Integer flgsenzapaginazionein;
	private java.lang.Integer nropaginaio;
	private java.lang.Integer bachsizeio;
	private java.lang.Integer overflowlimitin;
	private java.lang.Integer flgsenzatotin;
	private java.lang.Integer nrototrecout;
	private java.lang.Integer nrorecinpaginaout;
	private java.lang.String listaxmlout;
	private java.lang.Integer flgabilinsout;
	private java.lang.Integer flgabilupdout;
	private java.lang.Integer flgabildelout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.Integer getFlgtpopersuudin(){return flgtpopersuudin;}
    public java.lang.String getActivitynamein(){return activitynamein;}
    public java.lang.String getFlgfmtestremiregio(){return flgfmtestremiregio;}
    public java.lang.Integer getFlgpreimpostafiltroin(){return flgpreimpostafiltroin;}
    public java.lang.String getCondsutipodocio(){return condsutipodocio;}
    public java.lang.String getFlgacqprodio(){return flgacqprodio;}
    public java.lang.String getCodruoloinprocio(){return codruoloinprocio;}
    public java.lang.String getDesruoloinprocio(){return desruoloinprocio;}
    public java.lang.String getCodstatoinprocio(){return codstatoinprocio;}
    public java.lang.String getDesstatoinprocio(){return desstatoinprocio;}
    public java.lang.Integer getAnnoregio(){return annoregio;}
    public java.lang.Integer getNroregio(){return nroregio;}
    public java.lang.String getOggettodocio(){return oggettodocio;}
    public java.math.BigDecimal getIdudio(){return idudio;}
    public java.math.BigDecimal getIddocio(){return iddocio;}
    public java.lang.String getColorderbyio(){return colorderbyio;}
    public java.lang.String getFlgdescorderbyio(){return flgdescorderbyio;}
    public java.lang.Integer getFlgsenzapaginazionein(){return flgsenzapaginazionein;}
    public java.lang.Integer getNropaginaio(){return nropaginaio;}
    public java.lang.Integer getBachsizeio(){return bachsizeio;}
    public java.lang.Integer getOverflowlimitin(){return overflowlimitin;}
    public java.lang.Integer getFlgsenzatotin(){return flgsenzatotin;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.Integer getNrorecinpaginaout(){return nrorecinpaginaout;}
    public java.lang.String getListaxmlout(){return listaxmlout;}
    public java.lang.Integer getFlgabilinsout(){return flgabilinsout;}
    public java.lang.Integer getFlgabilupdout(){return flgabilupdout;}
    public java.lang.Integer getFlgabildelout(){return flgabildelout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setFlgtpopersuudin(java.lang.Integer value){this.flgtpopersuudin=value;}
    public void setActivitynamein(java.lang.String value){this.activitynamein=value;}
    public void setFlgfmtestremiregio(java.lang.String value){this.flgfmtestremiregio=value;}
    public void setFlgpreimpostafiltroin(java.lang.Integer value){this.flgpreimpostafiltroin=value;}
    public void setCondsutipodocio(java.lang.String value){this.condsutipodocio=value;}
    public void setFlgacqprodio(java.lang.String value){this.flgacqprodio=value;}
    public void setCodruoloinprocio(java.lang.String value){this.codruoloinprocio=value;}
    public void setDesruoloinprocio(java.lang.String value){this.desruoloinprocio=value;}
    public void setCodstatoinprocio(java.lang.String value){this.codstatoinprocio=value;}
    public void setDesstatoinprocio(java.lang.String value){this.desstatoinprocio=value;}
    public void setAnnoregio(java.lang.Integer value){this.annoregio=value;}
    public void setNroregio(java.lang.Integer value){this.nroregio=value;}
    public void setOggettodocio(java.lang.String value){this.oggettodocio=value;}
    public void setIdudio(java.math.BigDecimal value){this.idudio=value;}
    public void setIddocio(java.math.BigDecimal value){this.iddocio=value;}
    public void setColorderbyio(java.lang.String value){this.colorderbyio=value;}
    public void setFlgdescorderbyio(java.lang.String value){this.flgdescorderbyio=value;}
    public void setFlgsenzapaginazionein(java.lang.Integer value){this.flgsenzapaginazionein=value;}
    public void setNropaginaio(java.lang.Integer value){this.nropaginaio=value;}
    public void setBachsizeio(java.lang.Integer value){this.bachsizeio=value;}
    public void setOverflowlimitin(java.lang.Integer value){this.overflowlimitin=value;}
    public void setFlgsenzatotin(java.lang.Integer value){this.flgsenzatotin=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setNrorecinpaginaout(java.lang.Integer value){this.nrorecinpaginaout=value;}
    public void setListaxmlout(java.lang.String value){this.listaxmlout=value;}
    public void setFlgabilinsout(java.lang.Integer value){this.flgabilinsout=value;}
    public void setFlgabilupdout(java.lang.Integer value){this.flgabilupdout=value;}
    public void setFlgabildelout(java.lang.Integer value){this.flgabildelout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    