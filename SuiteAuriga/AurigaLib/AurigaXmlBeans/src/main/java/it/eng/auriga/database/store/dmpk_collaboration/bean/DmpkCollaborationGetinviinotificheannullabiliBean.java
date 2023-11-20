/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkCollaborationGetinviinotificheannullabiliBean")
public class DmpkCollaborationGetinviinotificheannullabiliBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_COLLABORATION_GETINVIINOTIFICHEANNULLABILI";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.lang.String flgtypeobjsentin;
	private java.math.BigDecimal idobjsentin;
	private java.lang.String flginvionotificain;
	private java.lang.String inviinotifichexmlout;
	private java.lang.Integer nrototrecout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.lang.String getFlgtypeobjsentin(){return flgtypeobjsentin;}
    public java.math.BigDecimal getIdobjsentin(){return idobjsentin;}
    public java.lang.String getFlginvionotificain(){return flginvionotificain;}
    public java.lang.String getInviinotifichexmlout(){return inviinotifichexmlout;}
    public java.lang.Integer getNrototrecout(){return nrototrecout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setFlgtypeobjsentin(java.lang.String value){this.flgtypeobjsentin=value;}
    public void setIdobjsentin(java.math.BigDecimal value){this.idobjsentin=value;}
    public void setFlginvionotificain(java.lang.String value){this.flginvionotificain=value;}
    public void setInviinotifichexmlout(java.lang.String value){this.inviinotifichexmlout=value;}
    public void setNrototrecout(java.lang.Integer value){this.nrototrecout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    