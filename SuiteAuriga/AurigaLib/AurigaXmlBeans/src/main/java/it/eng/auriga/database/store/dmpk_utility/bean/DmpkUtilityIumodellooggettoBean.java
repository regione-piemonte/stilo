/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityIumodellooggettoBean")
public class DmpkUtilityIumodellooggettoBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_IUMODELLOOGGETTO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idmodoggettoio;
	private java.lang.String cinomemodelloin;
	private java.lang.String oggettoin;
	private java.lang.String ciprovmodelloin;
	private java.lang.Integer flgpubblicoin;
	private java.lang.Integer flgxdocinentratain;
	private java.lang.Integer flgxdocinuscitain;
	private java.lang.Integer flgxdocinterniin;
	private java.lang.Integer flglockedin;
	private java.lang.String notemodelloin;
	private java.lang.String flgmodtipiregin;
	private java.lang.String xmltipiregin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.math.BigDecimal iduoin;
	private java.lang.Integer flgvisibsottouoin;
	private java.lang.Integer flggestsottouoin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdmodoggettoio(){return idmodoggettoio;}
    public java.lang.String getCinomemodelloin(){return cinomemodelloin;}
    public java.lang.String getOggettoin(){return oggettoin;}
    public java.lang.String getCiprovmodelloin(){return ciprovmodelloin;}
    public java.lang.Integer getFlgpubblicoin(){return flgpubblicoin;}
    public java.lang.Integer getFlgxdocinentratain(){return flgxdocinentratain;}
    public java.lang.Integer getFlgxdocinuscitain(){return flgxdocinuscitain;}
    public java.lang.Integer getFlgxdocinterniin(){return flgxdocinterniin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.String getNotemodelloin(){return notemodelloin;}
    public java.lang.String getFlgmodtipiregin(){return flgmodtipiregin;}
    public java.lang.String getXmltipiregin(){return xmltipiregin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.math.BigDecimal getIduoin(){return iduoin;}
    public java.lang.Integer getFlgvisibsottouoin(){return flgvisibsottouoin;}
    public java.lang.Integer getFlggestsottouoin(){return flggestsottouoin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdmodoggettoio(java.math.BigDecimal value){this.idmodoggettoio=value;}
    public void setCinomemodelloin(java.lang.String value){this.cinomemodelloin=value;}
    public void setOggettoin(java.lang.String value){this.oggettoin=value;}
    public void setCiprovmodelloin(java.lang.String value){this.ciprovmodelloin=value;}
    public void setFlgpubblicoin(java.lang.Integer value){this.flgpubblicoin=value;}
    public void setFlgxdocinentratain(java.lang.Integer value){this.flgxdocinentratain=value;}
    public void setFlgxdocinuscitain(java.lang.Integer value){this.flgxdocinuscitain=value;}
    public void setFlgxdocinterniin(java.lang.Integer value){this.flgxdocinterniin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setNotemodelloin(java.lang.String value){this.notemodelloin=value;}
    public void setFlgmodtipiregin(java.lang.String value){this.flgmodtipiregin=value;}
    public void setXmltipiregin(java.lang.String value){this.xmltipiregin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setIduoin(java.math.BigDecimal value){this.iduoin=value;}
    public void setFlgvisibsottouoin(java.lang.Integer value){this.flgvisibsottouoin=value;}
    public void setFlggestsottouoin(java.lang.Integer value){this.flggestsottouoin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    