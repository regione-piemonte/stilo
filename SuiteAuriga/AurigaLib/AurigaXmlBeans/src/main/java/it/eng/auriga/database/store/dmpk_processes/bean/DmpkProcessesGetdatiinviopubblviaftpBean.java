/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkProcessesGetdatiinviopubblviaftpBean")
public class DmpkProcessesGetdatiinviopubblviaftpBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_PROCESSES_GETDATIINVIOPUBBLVIAFTP";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.math.BigDecimal idprocessin;
	private java.lang.String xmlpubblicazioneout;
	private java.lang.String nomexmlout;
	private java.lang.String nomecartellaout;
	private java.lang.String filedapubblicareout;
	private java.lang.String useridaccessoftpout;
	private java.lang.String passwordaccessoftpout;
	private java.lang.String nomediraccessoftpout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.math.BigDecimal getIdprocessin(){return idprocessin;}
    public java.lang.String getXmlpubblicazioneout(){return xmlpubblicazioneout;}
    public java.lang.String getNomexmlout(){return nomexmlout;}
    public java.lang.String getNomecartellaout(){return nomecartellaout;}
    public java.lang.String getFiledapubblicareout(){return filedapubblicareout;}
    public java.lang.String getUseridaccessoftpout(){return useridaccessoftpout;}
    public java.lang.String getPasswordaccessoftpout(){return passwordaccessoftpout;}
    public java.lang.String getNomediraccessoftpout(){return nomediraccessoftpout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdprocessin(java.math.BigDecimal value){this.idprocessin=value;}
    public void setXmlpubblicazioneout(java.lang.String value){this.xmlpubblicazioneout=value;}
    public void setNomexmlout(java.lang.String value){this.nomexmlout=value;}
    public void setNomecartellaout(java.lang.String value){this.nomecartellaout=value;}
    public void setFiledapubblicareout(java.lang.String value){this.filedapubblicareout=value;}
    public void setUseridaccessoftpout(java.lang.String value){this.useridaccessoftpout=value;}
    public void setPasswordaccessoftpout(java.lang.String value){this.passwordaccessoftpout=value;}
    public void setNomediraccessoftpout(java.lang.String value){this.nomediraccessoftpout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    