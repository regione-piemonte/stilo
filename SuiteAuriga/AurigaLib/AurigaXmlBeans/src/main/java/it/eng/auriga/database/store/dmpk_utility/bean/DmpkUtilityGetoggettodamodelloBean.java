/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkUtilityGetoggettodamodelloBean")
public class DmpkUtilityGetoggettodamodelloBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_UTILITY_GETOGGETTODAMODELLO";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String cinomemodelloin;
	private java.lang.String provcimodelloin;
	private java.math.BigDecimal idmodelloin;
	private java.lang.String flgversoregistrazionein;
	private java.lang.String categoriaregin;
	private java.lang.String siglaregistrazionein;
	private java.lang.String oggettoout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCinomemodelloin(){return cinomemodelloin;}
    public java.lang.String getProvcimodelloin(){return provcimodelloin;}
    public java.math.BigDecimal getIdmodelloin(){return idmodelloin;}
    public java.lang.String getFlgversoregistrazionein(){return flgversoregistrazionein;}
    public java.lang.String getCategoriaregin(){return categoriaregin;}
    public java.lang.String getSiglaregistrazionein(){return siglaregistrazionein;}
    public java.lang.String getOggettoout(){return oggettoout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCinomemodelloin(java.lang.String value){this.cinomemodelloin=value;}
    public void setProvcimodelloin(java.lang.String value){this.provcimodelloin=value;}
    public void setIdmodelloin(java.math.BigDecimal value){this.idmodelloin=value;}
    public void setFlgversoregistrazionein(java.lang.String value){this.flgversoregistrazionein=value;}
    public void setCategoriaregin(java.lang.String value){this.categoriaregin=value;}
    public void setSiglaregistrazionein(java.lang.String value){this.siglaregistrazionein=value;}
    public void setOggettoout(java.lang.String value){this.oggettoout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    