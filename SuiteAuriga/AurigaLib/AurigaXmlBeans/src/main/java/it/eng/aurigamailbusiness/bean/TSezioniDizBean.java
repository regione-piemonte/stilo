/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TSezioniDizBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String contenuti;
	private boolean flgAnn;
	private boolean flgDiSistema;
	private String idSezDiz;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String nome;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getContenuti(){ return contenuti;}

	public void setContenuti(String contenuti){ this.contenuti=contenuti;}

	public boolean getFlgAnn(){ return flgAnn;}

	public void setFlgAnn(boolean flgAnn){ this.flgAnn=flgAnn;}

	public boolean getFlgDiSistema(){ return flgDiSistema;}

	public void setFlgDiSistema(boolean flgDiSistema){ this.flgDiSistema=flgDiSistema;}

	public String getIdSezDiz(){ return idSezDiz;}

	public void setIdSezDiz(String idSezDiz){ this.idSezDiz=idSezDiz;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public String getNome(){ return nome;}

	public void setNome(String nome){ this.nome=nome;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}


}    