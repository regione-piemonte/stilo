/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesLoaddettprocessudBean")
public class DmpkProcessesLoaddettprocessudBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_LOADDETTPROCESSUD";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String codidconnectiontokenin;
	private java.math.BigDecimal iduserlavoroin;
	private java.math.BigDecimal idprocessin;
	private java.math.BigDecimal idudio;
	private java.math.BigDecimal iddocprimarioout;
	private java.lang.String siglaprotout;
	private java.lang.Integer annoprotout;
	private java.lang.Integer nroprotout;
	private java.lang.String dtprotout;
	private java.lang.Integer flgprotrichiestaout;
	private java.math.BigDecimal idtipodocprimarioout;
	private java.lang.String nometipodocprimarioout;
	private java.lang.Integer nrolastverprimarioout;
	private java.lang.String displayfilenameprimarioout;
	private java.lang.String oggettoout;
	private java.lang.String mittdestout;
	private java.lang.String flgmittdestout;
	private java.lang.String estremiprocessout;
	private java.lang.String rowidout;
	private java.lang.String attributiaddout;
	private java.lang.Integer flgmostraaltriattrout;
	private java.lang.String flgtipoprovout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getCodidconnectiontokenin(){return codidconnectiontokenin;}
    public java.math.BigDecimal getIduserlavoroin(){return iduserlavoroin;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.math.BigDecimal getIdudio(){return idudio;}
    public java.math.BigDecimal getIddocprimarioout(){return iddocprimarioout;}
    public java.lang.String getSiglaprotout(){return siglaprotout;}
    public java.lang.Integer getAnnoprotout(){return annoprotout;}
    public java.lang.Integer getNroprotout(){return nroprotout;}
    public java.lang.String getDtprotout(){return dtprotout;}
    public java.lang.Integer getFlgprotrichiestaout(){return flgprotrichiestaout;}
    public java.math.BigDecimal getIdtipodocprimarioout(){return idtipodocprimarioout;}
    public java.lang.String getNometipodocprimarioout(){return nometipodocprimarioout;}
    public java.lang.Integer getNrolastverprimarioout(){return nrolastverprimarioout;}
    public java.lang.String getDisplayfilenameprimarioout(){return displayfilenameprimarioout;}
    public java.lang.String getOggettoout(){return oggettoout;}
    public java.lang.String getMittdestout(){return mittdestout;}
    public java.lang.String getFlgmittdestout(){return flgmittdestout;}
    public java.lang.String getEstremiprocessout(){return estremiprocessout;}
    public java.lang.String getRowidout(){return rowidout;}
    public java.lang.String getAttributiaddout(){return attributiaddout;}
    public java.lang.Integer getFlgmostraaltriattrout(){return flgmostraaltriattrout;}
    public java.lang.String getFlgtipoprovout(){return flgtipoprovout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setCodidconnectiontokenin(java.lang.String value){this.codidconnectiontokenin=value;}
    public void setIduserlavoroin(java.math.BigDecimal value){this.iduserlavoroin=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setIdudio(java.math.BigDecimal value){this.idudio=value;}
    public void setIddocprimarioout(java.math.BigDecimal value){this.iddocprimarioout=value;}
    public void setSiglaprotout(java.lang.String value){this.siglaprotout=value;}
    public void setAnnoprotout(java.lang.Integer value){this.annoprotout=value;}
    public void setNroprotout(java.lang.Integer value){this.nroprotout=value;}
    public void setDtprotout(java.lang.String value){this.dtprotout=value;}
    public void setFlgprotrichiestaout(java.lang.Integer value){this.flgprotrichiestaout=value;}
    public void setIdtipodocprimarioout(java.math.BigDecimal value){this.idtipodocprimarioout=value;}
    public void setNometipodocprimarioout(java.lang.String value){this.nometipodocprimarioout=value;}
    public void setNrolastverprimarioout(java.lang.Integer value){this.nrolastverprimarioout=value;}
    public void setDisplayfilenameprimarioout(java.lang.String value){this.displayfilenameprimarioout=value;}
    public void setOggettoout(java.lang.String value){this.oggettoout=value;}
    public void setMittdestout(java.lang.String value){this.mittdestout=value;}
    public void setFlgmittdestout(java.lang.String value){this.flgmittdestout=value;}
    public void setEstremiprocessout(java.lang.String value){this.estremiprocessout=value;}
    public void setRowidout(java.lang.String value){this.rowidout=value;}
    public void setAttributiaddout(java.lang.String value){this.attributiaddout=value;}
    public void setFlgmostraaltriattrout(java.lang.Integer value){this.flgmostraaltriattrout=value;}
    public void setFlgtipoprovout(java.lang.String value){this.flgtipoprovout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    