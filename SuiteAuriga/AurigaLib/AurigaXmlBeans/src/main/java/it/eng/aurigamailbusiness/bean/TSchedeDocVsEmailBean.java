/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TSchedeDocVsEmailBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String idProvSchedaDoc;
	private String idSchedaDocEmail;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getIdProvSchedaDoc(){ return idProvSchedaDoc;}

	public void setIdProvSchedaDoc(String idProvSchedaDoc){ this.idProvSchedaDoc=idProvSchedaDoc;}

	public String getIdSchedaDocEmail(){ return idSchedaDocEmail;}

	public void setIdSchedaDocEmail(String idSchedaDocEmail){ this.idSchedaDocEmail=idSchedaDocEmail;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}


}    