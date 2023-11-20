/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TUtentiModPecBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String codFiscale;
	private String cognome;
	private boolean flgAmministratore;
	private boolean flgAttivo;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String idUtente;
	private String nome;
	private String password;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String username;

	public String getCodFiscale(){ return codFiscale;}

	public void setCodFiscale(String codFiscale){ this.codFiscale=codFiscale;}

	public String getCognome(){ return cognome;}

	public void setCognome(String cognome){ this.cognome=cognome;}

	public boolean getFlgAmministratore(){ return flgAmministratore;}

	public void setFlgAmministratore(boolean flgAmministratore){ this.flgAmministratore=flgAmministratore;}

	public boolean getFlgAttivo(){ return flgAttivo;}

	public void setFlgAttivo(boolean flgAttivo){ this.flgAttivo=flgAttivo;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public String getIdUtente(){ return idUtente;}

	public void setIdUtente(String idUtente){ this.idUtente=idUtente;}

	public String getNome(){ return nome;}

	public void setNome(String nome){ this.nome=nome;}

	public String getPassword(){ return password;}

	public void setPassword(String password){ this.password=password;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}

	public String getUsername(){ return username;}

	public void setUsername(String username){ this.username=username;}


}    