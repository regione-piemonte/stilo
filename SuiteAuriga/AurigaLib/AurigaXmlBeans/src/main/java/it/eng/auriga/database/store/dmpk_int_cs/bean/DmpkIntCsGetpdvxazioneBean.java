/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsGetpdvxazioneBean")
public class DmpkIntCsGetpdvxazioneBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_GETPDVXAZIONE";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codazionein;
	private java.lang.String idsistconservazionein;
	private java.lang.String identrypointversin;
	private java.math.BigDecimal idspaooin;
	private java.lang.String codapplicazionein;
	private java.lang.String codistapplicazionein;
	private java.lang.String pdvlistout;
	private java.lang.Integer nropdvout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodazionein(){return codazionein;}
    public java.lang.String getIdsistconservazionein(){return idsistconservazionein;}
    public java.lang.String getIdentrypointversin(){return identrypointversin;}
    public java.math.BigDecimal getIdspaooin(){return idspaooin;}
    public java.lang.String getCodapplicazionein(){return codapplicazionein;}
    public java.lang.String getCodistapplicazionein(){return codistapplicazionein;}
    public java.lang.String getPdvlistout(){return pdvlistout;}
    public java.lang.Integer getNropdvout(){return nropdvout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodazionein(java.lang.String value){this.codazionein=value;}
    public void setIdsistconservazionein(java.lang.String value){this.idsistconservazionein=value;}
    public void setIdentrypointversin(java.lang.String value){this.identrypointversin=value;}
    public void setIdspaooin(java.math.BigDecimal value){this.idspaooin=value;}
    public void setCodapplicazionein(java.lang.String value){this.codapplicazionein=value;}
    public void setCodistapplicazionein(java.lang.String value){this.codistapplicazionein=value;}
    public void setPdvlistout(java.lang.String value){this.pdvlistout=value;}
    public void setNropdvout(java.lang.Integer value){this.nropdvout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    