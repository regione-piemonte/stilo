/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TApplicazioniModPecBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String codApplicazione;
	private String descrizione;
	private boolean flgAttiva;
	private String idApplicazione;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String nome;
	private String password;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getCodApplicazione(){ return codApplicazione;}

	public void setCodApplicazione(String codApplicazione){ this.codApplicazione=codApplicazione;}

	public String getDescrizione(){ return descrizione;}

	public void setDescrizione(String descrizione){ this.descrizione=descrizione;}

	public boolean getFlgAttiva(){ return flgAttiva;}

	public void setFlgAttiva(boolean flgAttiva){ this.flgAttiva=flgAttiva;}

	public String getIdApplicazione(){ return idApplicazione;}

	public void setIdApplicazione(String idApplicazione){ this.idApplicazione=idApplicazione;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public String getNome(){ return nome;}

	public void setNome(String nome){ this.nome=nome;}

	public String getPassword(){ return password;}

	public void setPassword(String password){ this.password=password;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}


}    