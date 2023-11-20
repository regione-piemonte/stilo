/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkModelliDocIumodelloBean")
public class DmpkModelliDocIumodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_MODELLI_DOC_IUMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idmodelloio;
	private java.lang.String nomein;
	private java.lang.String descrizionein;
	private java.lang.String annotazioniin;
	private java.lang.String ciprovmodelloin;
	private java.lang.Integer flglockedin;
	private java.lang.Integer nroordinein;
	private java.lang.String attributiaddin;
	private java.math.BigDecimal iddocout;
	private java.lang.Integer iddocxmlout;
	private java.lang.Integer iddocpdfout;
	private java.lang.Integer iddochtmlout;
	private java.lang.Integer flgignorewarningin;
	private java.lang.Integer flgrollbckfullin;
	private java.lang.Integer flgautocommitin;
	private java.lang.String warningmsgout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	private java.lang.String tipomodelloin;
	private java.lang.String tipoenitaassociatain;
	private java.math.BigDecimal identitaassociatain;
	private java.math.BigDecimal flggeneraformatopdfin;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdmodelloio(){return idmodelloio;}
    public java.lang.String getNomein(){return nomein;}
    public java.lang.String getDescrizionein(){return descrizionein;}
    public java.lang.String getAnnotazioniin(){return annotazioniin;}
    public java.lang.String getCiprovmodelloin(){return ciprovmodelloin;}
    public java.lang.Integer getFlglockedin(){return flglockedin;}
    public java.lang.Integer getNroordinein(){return nroordinein;}
    public java.lang.String getAttributiaddin(){return attributiaddin;}
    public java.math.BigDecimal getIddocout(){return iddocout;}
    public java.lang.Integer getIddocxmlout(){return iddocxmlout;}
    public java.lang.Integer getIddocpdfout(){return iddocpdfout;}
    public java.lang.Integer getIddochtmlout(){return iddochtmlout;}
    public java.lang.Integer getFlgignorewarningin(){return flgignorewarningin;}
    public java.lang.Integer getFlgrollbckfullin(){return flgrollbckfullin;}
    public java.lang.Integer getFlgautocommitin(){return flgautocommitin;}
    public java.lang.String getWarningmsgout(){return warningmsgout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    public java.lang.String getTipomodelloin(){return tipomodelloin;}
    public java.lang.String getTipoenitaassociatain(){return tipoenitaassociatain;}
    public java.math.BigDecimal getIdentitaassociatain(){return identitaassociatain;}
    public java.math.BigDecimal getFlggeneraformatopdfin(){return flggeneraformatopdfin;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdmodelloio(java.math.BigDecimal value){this.idmodelloio=value;}
    public void setNomein(java.lang.String value){this.nomein=value;}
    public void setDescrizionein(java.lang.String value){this.descrizionein=value;}
    public void setAnnotazioniin(java.lang.String value){this.annotazioniin=value;}
    public void setCiprovmodelloin(java.lang.String value){this.ciprovmodelloin=value;}
    public void setFlglockedin(java.lang.Integer value){this.flglockedin=value;}
    public void setNroordinein(java.lang.Integer value){this.nroordinein=value;}
    public void setAttributiaddin(java.lang.String value){this.attributiaddin=value;}
    public void setIddocout(java.math.BigDecimal value){this.iddocout=value;}
    public void setIddocxmlout(java.lang.Integer value){this.iddocxmlout=value;}
    public void setIddocpdfout(java.lang.Integer value){this.iddocpdfout=value;}
    public void setIddochtmlout(java.lang.Integer value){this.iddochtmlout=value;}
    public void setFlgignorewarningin(java.lang.Integer value){this.flgignorewarningin=value;}
    public void setFlgrollbckfullin(java.lang.Integer value){this.flgrollbckfullin=value;}
    public void setFlgautocommitin(java.lang.Integer value){this.flgautocommitin=value;}
    public void setWarningmsgout(java.lang.String value){this.warningmsgout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    public void setTipomodelloin(java.lang.String value){this.tipomodelloin=value;}
    public void setTipoenitaassociatain(java.lang.String value){this.tipoenitaassociatain=value;}
    public void setIdentitaassociatain(java.math.BigDecimal value){this.identitaassociatain=value;}
    public void setFlggeneraformatopdfin(java.math.BigDecimal value){this.flggeneraformatopdfin=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    