/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.auriga.database.store.bean.StoreBean;
/**
 * @author Procedure Wrapper 0.1.0
 */
@XmlRootElement
@XmlType(name = "DmpkIntMgoEmailPreparaconfermaautomaticaBean")
public class DmpkIntMgoEmailPreparaconfermaautomaticaBean extends StoreBean implements Serializable{

	private static final String storeName = "DMPK_INT_MGO_EMAIL_PREPARACONFERMAAUTOMATICA";

	private static final long serialVersionUID = 1L;
	private Integer parametro_1;

	private java.lang.String idutentepecin;
	private java.lang.String idemailin;
	private java.math.BigDecimal idudin;
	private java.lang.String xmlsegnaturain;
	private java.lang.String versionedtdin;
	private java.lang.String destinatarimailnotifout;
	private java.lang.String destinatariccmailnotifout;
	private java.lang.String subjectmailnotifout;
	private java.lang.String bodymailnotifout;
	private java.lang.String flgricevutacbsout;
	private java.lang.String xmlconfermaout;
	private java.lang.String errcontextout;
	private java.lang.Integer errcodeout;
	private java.lang.String errmsgout;
	public java.lang.Integer getParametro_1(){return parametro_1;}
    public java.lang.String getIdutentepecin(){return idutentepecin;}
    public java.lang.String getIdemailin(){return idemailin;}
    public java.math.BigDecimal getIdudin(){return idudin;}
    public java.lang.String getXmlsegnaturain(){return xmlsegnaturain;}
    public java.lang.String getVersionedtdin(){return versionedtdin;}
    public java.lang.String getDestinatarimailnotifout(){return destinatarimailnotifout;}
    public java.lang.String getDestinatariccmailnotifout(){return destinatariccmailnotifout;}
    public java.lang.String getSubjectmailnotifout(){return subjectmailnotifout;}
    public java.lang.String getBodymailnotifout(){return bodymailnotifout;}
    public java.lang.String getFlgricevutacbsout(){return flgricevutacbsout;}
    public java.lang.String getXmlconfermaout(){return xmlconfermaout;}
    public java.lang.String getErrcontextout(){return errcontextout;}
    public java.lang.Integer getErrcodeout(){return errcodeout;}
    public java.lang.String getErrmsgout(){return errmsgout;}
    
	public void setParametro_1(java.lang.Integer value){this.parametro_1=value;}
    public void setIdutentepecin(java.lang.String value){this.idutentepecin=value;}
    public void setIdemailin(java.lang.String value){this.idemailin=value;}
    public void setIdudin(java.math.BigDecimal value){this.idudin=value;}
    public void setXmlsegnaturain(java.lang.String value){this.xmlsegnaturain=value;}
    public void setVersionedtdin(java.lang.String value){this.versionedtdin=value;}
    public void setDestinatarimailnotifout(java.lang.String value){this.destinatarimailnotifout=value;}
    public void setDestinatariccmailnotifout(java.lang.String value){this.destinatariccmailnotifout=value;}
    public void setSubjectmailnotifout(java.lang.String value){this.subjectmailnotifout=value;}
    public void setBodymailnotifout(java.lang.String value){this.bodymailnotifout=value;}
    public void setFlgricevutacbsout(java.lang.String value){this.flgricevutacbsout=value;}
    public void setXmlconfermaout(java.lang.String value){this.xmlconfermaout=value;}
    public void setErrcontextout(java.lang.String value){this.errcontextout=value;}
    public void setErrcodeout(java.lang.Integer value){this.errcodeout=value;}
    public void setErrmsgout(java.lang.String value){this.errmsgout=value;}
    
    public String getStoreName(){
    	return storeName;
    }
	public StoreType getType() { return StoreType.STORE; }

}    