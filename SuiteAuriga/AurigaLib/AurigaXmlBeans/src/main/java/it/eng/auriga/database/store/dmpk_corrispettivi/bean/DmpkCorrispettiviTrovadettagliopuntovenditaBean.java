/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCorrispettiviTrovadettagliopuntovenditaBean")
public class DmpkCorrispettiviTrovadettagliopuntovenditaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_CORRISPETTIVI_TROVADETTAGLIOPUNTOVENDITA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String idsoggrubricapuntovenditain;
	private java.lang.String idordersourcepuntovenditain;
	private java.lang.String puntovenditaxmlout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getIdsoggrubricapuntovenditain(){return idsoggrubricapuntovenditain;}
    public java.lang.String getIdordersourcepuntovenditain(){return idordersourcepuntovenditain;}
    public java.lang.String getPuntovenditaxmlout(){return puntovenditaxmlout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdsoggrubricapuntovenditain(java.lang.String value){this.idsoggrubricapuntovenditain=value;}
    public void setIdordersourcepuntovenditain(java.lang.String value){this.idordersourcepuntovenditain=value;}
    public void setPuntovenditaxmlout(java.lang.String value){this.puntovenditaxmlout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    