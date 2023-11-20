/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesIueventocustomBean")
public class DmpkProcessesIueventocustomBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_IUEVENTOCUSTOM";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.math.BigDecimal ideventoio;
	private java.math.BigDecimal idtipoeventoin;
	private java.lang.String deseventoin;
	private java.lang.String tsinizioeventoin;
	private java.math.BigDecimal iduserinianomediin;
	private java.lang.String desuserinianomediin;
	private java.math.BigDecimal iduserinidain;
	private java.lang.String desuserinidain;
	private java.lang.String tscompleventoin;
	private java.math.BigDecimal idusercomplanomediin;
	private java.lang.String desusercomplanomediin;
	private java.math.BigDecimal idusercompldain;
	private java.lang.String desusercompldain;
	private java.lang.String desesitoin;
	private java.lang.String messaggioin;
	private java.math.BigDecimal idudassociatain;
	private java.math.BigDecimal duratamaxeventoin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal iduserrespprocin;
	private java.lang.Integer flgpubblicatoin;
	private java.lang.Integer flgignoraobbligin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.math.BigDecimal getIdeventoio(){return ideventoio;}
    public java.math.BigDecimal getIdtipoeventoin(){return idtipoeventoin;}
    public java.lang.String getDeseventoin(){return deseventoin;}
    public java.lang.String getTsinizioeventoin(){return tsinizioeventoin;}
    public java.math.BigDecimal getIduserinianomediin(){return iduserinianomediin;}
    public java.lang.String getDesuserinianomediin(){return desuserinianomediin;}
    public java.math.BigDecimal getIduserinidain(){return iduserinidain;}
    public java.lang.String getDesuserinidain(){return desuserinidain;}
    public java.lang.String getTscompleventoin(){return tscompleventoin;}
    public java.math.BigDecimal getIdusercomplanomediin(){return idusercomplanomediin;}
    public java.lang.String getDesusercomplanomediin(){return desusercomplanomediin;}
    public java.math.BigDecimal getIdusercompldain(){return idusercompldain;}
    public java.lang.String getDesusercompldain(){return desusercompldain;}
    public java.lang.String getDesesitoin(){return desesitoin;}
    public java.lang.String getMessaggioin(){return messaggioin;}
    public java.math.BigDecimal getIdudassociatain(){return idudassociatain;}
    public java.math.BigDecimal getDuratamaxeventoin(){return duratamaxeventoin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIduserrespprocin(){return iduserrespprocin;}
    public java.lang.Integer getFlgpubblicatoin(){return flgpubblicatoin;}
    public java.lang.Integer getFlgignoraobbligin(){return flgignoraobbligin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setIdeventoio(java.math.BigDecimal value){this.ideventoio=value;}
    public void setIdtipoeventoin(java.math.BigDecimal value){this.idtipoeventoin=value;}
    public void setDeseventoin(java.lang.String value){this.deseventoin=value;}
    public void setTsinizioeventoin(java.lang.String value){this.tsinizioeventoin=value;}
    public void setIduserinianomediin(java.math.BigDecimal value){this.iduserinianomediin=value;}
    public void setDesuserinianomediin(java.lang.String value){this.desuserinianomediin=value;}
    public void setIduserinidain(java.math.BigDecimal value){this.iduserinidain=value;}
    public void setDesuserinidain(java.lang.String value){this.desuserinidain=value;}
    public void setTscompleventoin(java.lang.String value){this.tscompleventoin=value;}
    public void setIdusercomplanomediin(java.math.BigDecimal value){this.idusercomplanomediin=value;}
    public void setDesusercomplanomediin(java.lang.String value){this.desusercomplanomediin=value;}
    public void setIdusercompldain(java.math.BigDecimal value){this.idusercompldain=value;}
    public void setDesusercompldain(java.lang.String value){this.desusercompldain=value;}
    public void setDesesitoin(java.lang.String value){this.desesitoin=value;}
    public void setMessaggioin(java.lang.String value){this.messaggioin=value;}
    public void setIdudassociatain(java.math.BigDecimal value){this.idudassociatain=value;}
    public void setDuratamaxeventoin(java.math.BigDecimal value){this.duratamaxeventoin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIduserrespprocin(java.math.BigDecimal value){this.iduserrespprocin=value;}
    public void setFlgpubblicatoin(java.lang.Integer value){this.flgpubblicatoin=value;}
    public void setFlgignoraobbligin(java.lang.Integer value){this.flgignoraobbligin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    