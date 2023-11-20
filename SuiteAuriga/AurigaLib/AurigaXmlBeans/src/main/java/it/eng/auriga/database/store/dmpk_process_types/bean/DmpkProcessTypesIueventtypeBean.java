/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessTypesIueventtypeBean")
public class DmpkProcessTypesIueventtypeBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESS_TYPES_IUEVENTTYPE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal ideventtypeio;
	private java.lang.String deseventin;
	private java.lang.String categoriain;
	private java.lang.Integer flgdurativoin;
	private java.math.BigDecimal duratamaxin;
	private java.lang.Integer flgvldxtuttiprocammin;
	private java.math.BigDecimal iddoctypein;
	private java.lang.String nomedoctypein;
	private java.lang.String codtipodatareldocin;
	private java.lang.String annotazioniin;
	private java.lang.String ciproveventtypein;
	private java.lang.Integer flglockedin;
	private java.lang.String flgmodstoredfuncin;
	private java.lang.String xmlstoredfuncin;
	private java.lang.String flgmodattraddxevtdeltipoin;
	private java.lang.String xmlattraddxevtdeltipoin;
	private java.lang.String attributiaddin;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdeventtypeio(){return ideventtypeio;}
    public java.lang.String getDeseventin(){return deseventin;}
    public java.lang.String getCategoriain(){return categoriain;}
    public java.lang.Integer getFlgdurativoin(){return flgdurativoin;}
    public java.math.BigDecimal getDuratamaxin(){return duratamaxin;}
    public java.lang.Integer getFlgvldxtuttiprocammin(){return flgvldxtuttiprocammin;}
    public java.math.BigDecimal getIddoctypein(){return iddoctypein;}
    public java.lang.String getNomedoctypein(){return nomedoctypein;}
    public java.lang.String getCodtipodatareldocin(){return codtipodatareldocin;}
    public java.lang.String getAnnotazioniin(){return annotazioniin;}
    public java.lang.String getCiproveventtypein(){return ciproveventtypein;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getFlgmodstoredfuncin(){return flgmodstoredfuncin;}
    public java.lang.String getXmlstoredfuncin(){return xmlstoredfuncin;}
    public java.lang.String getFlgmodattraddxevtdeltipoin(){return flgmodattraddxevtdeltipoin;}
    public java.lang.String getXmlattraddxevtdeltipoin(){return xmlattraddxevtdeltipoin;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdeventtypeio(java.math.BigDecimal value){this.ideventtypeio=value;}
    public void setDeseventin(java.lang.String value){this.deseventin=value;}
    public void setCategoriain(java.lang.String value){this.categoriain=value;}
    public void setFlgdurativoin(java.lang.Integer value){this.flgdurativoin=value;}
    public void setDuratamaxin(java.math.BigDecimal value){this.duratamaxin=value;}
    public void setFlgvldxtuttiprocammin(java.lang.Integer value){this.flgvldxtuttiprocammin=value;}
    public void setIddoctypein(java.math.BigDecimal value){this.iddoctypein=value;}
    public void setNomedoctypein(java.lang.String value){this.nomedoctypein=value;}
    public void setCodtipodatareldocin(java.lang.String value){this.codtipodatareldocin=value;}
    public void setAnnotazioniin(java.lang.String value){this.annotazioniin=value;}
    public void setCiproveventtypein(java.lang.String value){this.ciproveventtypein=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setFlgmodstoredfuncin(java.lang.String value){this.flgmodstoredfuncin=value;}
    public void setXmlstoredfuncin(java.lang.String value){this.xmlstoredfuncin=value;}
    public void setFlgmodattraddxevtdeltipoin(java.lang.String value){this.flgmodattraddxevtdeltipoin=value;}
    public void setXmlattraddxevtdeltipoin(java.lang.String value){this.xmlattraddxevtdeltipoin=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    