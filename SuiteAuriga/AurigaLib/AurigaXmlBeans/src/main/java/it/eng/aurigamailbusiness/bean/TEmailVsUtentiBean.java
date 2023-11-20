/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TEmailVsUtentiBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String idEmail;
	private String idUtente;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsAggStato;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getIdEmail(){ return idEmail;}

	public void setIdEmail(String idEmail){ this.idEmail=idEmail;}

	public String getIdUtente(){ return idUtente;}

	public void setIdUtente(String idUtente){ this.idUtente=idUtente;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public Date getTsAggStato(){ return tsAggStato;}

	public void setTsAggStato(Date tsAggStato){ this.tsAggStato=tsAggStato;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}


}    