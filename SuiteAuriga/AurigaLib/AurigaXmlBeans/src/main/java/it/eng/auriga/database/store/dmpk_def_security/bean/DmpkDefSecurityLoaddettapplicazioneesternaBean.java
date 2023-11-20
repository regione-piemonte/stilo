/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkDefSecurityLoaddettapplicazioneesternaBean")
public class DmpkDefSecurityLoaddettapplicazioneesternaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_DEF_SECURITY_LOADDETTAPPLICAZIONEESTERNA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String codapplicazionein;
	private java.lang.String codistanzaapplicazionein;
	private java.lang.String descrizioneapplistanzaout;
	private java.math.BigDecimal idspaooout;
	private java.lang.Integer flgusacredenzialiproprieout;
	private java.lang.String tsfirstlogonout;
	private java.lang.String tslastlogonout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getCodapplicazionein(){return codapplicazionein;}
    public java.lang.String getCodistanzaapplicazionein(){return codistanzaapplicazionein;}
    public java.lang.String getDescrizioneapplistanzaout(){return descrizioneapplistanzaout;}
    public java.math.BigDecimal getIdspaooout(){return idspaooout;}
    public java.lang.Integer getFlgusacredenzialiproprieout(){return flgusacredenzialiproprieout;}
    public java.lang.String getTsfirstlogonout(){return tsfirstlogonout;}
    public java.lang.String getTslastlogonout(){return tslastlogonout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setCodapplicazionein(java.lang.String value){this.codapplicazionein=value;}
    public void setCodistanzaapplicazionein(java.lang.String value){this.codistanzaapplicazionein=value;}
    public void setDescrizioneapplistanzaout(java.lang.String value){this.descrizioneapplistanzaout=value;}
    public void setIdspaooout(java.math.BigDecimal value){this.idspaooout=value;}
    public void setFlgusacredenzialiproprieout(java.lang.Integer value){this.flgusacredenzialiproprieout=value;}
    public void setTsfirstlogonout(java.lang.String value){this.tsfirstlogonout=value;}
    public void setTslastlogonout(java.lang.String value){this.tslastlogonout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    