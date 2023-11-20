/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkBmanagerEseguiopermassivaBean")
public class DmpkBmanagerEseguiopermassivaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_BMANAGER_ESEGUIOPERMASSIVA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal iddominioin;
	private java.lang.String tipooperazionein;
	private java.lang.String tipoobjoperonin;
	private java.lang.Integer flgopdariprocessarein;
	public Integer getParametro_1() { return 1; }
    public java.math.BigDecimal getIddominioin(){return iddominioin;}
    public java.lang.String getTipooperazionein(){return tipooperazionein;}
    public java.lang.String getTipoobjoperonin(){return tipoobjoperonin;}
    public java.lang.Integer getFlgopdariprocessarein(){return flgopdariprocessarein;}
    
    public void setIddominioin(java.math.BigDecimal value){this.iddominioin=value;}
    public void setTipooperazionein(java.lang.String value){this.tipooperazionein=value;}
    public void setTipoobjoperonin(java.lang.String value){this.tipoobjoperonin=value;}
    public void setFlgopdariprocessarein(java.lang.Integer value){this.flgopdariprocessarein=value;}
    
    public String getStoreName(){
    	return storeName;
    }
    public String getErrcontextout() {return null;}
    
    public Integer getErrcodeout() {return null;}
    
    public String getErrmsgout() {return null;}
    
    public StoreType getType() { return StoreType.FUNCTION; }

}    