/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TValDizionarioBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String codice;
	private boolean flgAnn;
	private boolean flgDiSistema;
	private boolean flgDismesso;
	private String idRecDiz;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String valore;

	public String getCodice(){ return codice;}

	public void setCodice(String codice){ this.codice=codice;}

	public boolean getFlgAnn(){ return flgAnn;}

	public void setFlgAnn(boolean flgAnn){ this.flgAnn=flgAnn;}

	public boolean getFlgDiSistema(){ return flgDiSistema;}

	public void setFlgDiSistema(boolean flgDiSistema){ this.flgDiSistema=flgDiSistema;}

	public boolean getFlgDismesso(){ return flgDismesso;}

	public void setFlgDismesso(boolean flgDismesso){ this.flgDismesso=flgDismesso;}

	public String getIdRecDiz(){ return idRecDiz;}

	public void setIdRecDiz(String idRecDiz){ this.idRecDiz=idRecDiz;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}

	public String getValore(){ return valore;}

	public void setValore(String valore){ this.valore=valore;}


}    