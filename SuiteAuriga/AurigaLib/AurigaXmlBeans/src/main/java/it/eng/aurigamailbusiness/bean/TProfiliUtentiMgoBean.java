/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TProfiliUtentiMgoBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private boolean flgAnn;
	private String idRelFruitoreCasella;
	private String idRelUtenteFruitore;
	private String profilo;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsIns;
	private Date tsUltimoAgg;

	public boolean getFlgAnn(){ return flgAnn;}

	public void setFlgAnn(boolean flgAnn){ this.flgAnn=flgAnn;}

	public String getIdRelFruitoreCasella(){ return idRelFruitoreCasella;}

	public void setIdRelFruitoreCasella(String idRelFruitoreCasella){ this.idRelFruitoreCasella=idRelFruitoreCasella;}

	public String getIdRelUtenteFruitore(){ return idRelUtenteFruitore;}

	public void setIdRelUtenteFruitore(String idRelUtenteFruitore){ this.idRelUtenteFruitore=idRelUtenteFruitore;}

	public String getProfilo(){ return profilo;}

	public void setProfilo(String profilo){ this.profilo=profilo;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}


}    