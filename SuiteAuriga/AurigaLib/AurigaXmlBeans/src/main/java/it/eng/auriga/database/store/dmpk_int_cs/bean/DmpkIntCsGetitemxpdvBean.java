/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntCsGetitemxpdvBean")
public class DmpkIntCsGetitemxpdvBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_CS_GETITEMXPDV";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String identrypointversout;
	private java.math.BigDecimal idspaooout;
	private java.lang.String codapplicazioneout;
	private java.lang.String codistapplicazioneout;
	private java.lang.String idsistconservazioneout;
	private java.lang.String dettentrypointversout;
	private java.lang.String itemlistout;
	private java.lang.Integer nroitemout;
	private java.lang.String iditemgroupout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdentrypointversout(){return identrypointversout;}
    public java.math.BigDecimal getIdspaooout(){return idspaooout;}
    public java.lang.String getCodapplicazioneout(){return codapplicazioneout;}
    public java.lang.String getCodistapplicazioneout(){return codistapplicazioneout;}
    public java.lang.String getIdsistconservazioneout(){return idsistconservazioneout;}
    public java.lang.String getDettentrypointversout(){return dettentrypointversout;}
    public java.lang.String getItemlistout(){return itemlistout;}
    public java.lang.Integer getNroitemout(){return nroitemout;}
    public java.lang.String getIditemgroupout(){return iditemgroupout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdentrypointversout(java.lang.String value){this.identrypointversout=value;}
    public void setIdspaooout(java.math.BigDecimal value){this.idspaooout=value;}
    public void setCodapplicazioneout(java.lang.String value){this.codapplicazioneout=value;}
    public void setCodistapplicazioneout(java.lang.String value){this.codistapplicazioneout=value;}
    public void setIdsistconservazioneout(java.lang.String value){this.idsistconservazioneout=value;}
    public void setDettentrypointversout(java.lang.String value){this.dettentrypointversout=value;}
    public void setItemlistout(java.lang.String value){this.itemlistout=value;}
    public void setNroitemout(java.lang.Integer value){this.nroitemout=value;}
    public void setIditemgroupout(java.lang.String value){this.iditemgroupout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    