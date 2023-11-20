/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TCapComuniBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String cap;
	private String nomeComune;
	private String targaProvincia;

	public String getCap(){ return cap;}

	public void setCap(String cap){ this.cap=cap;}

	public String getNomeComune(){ return nomeComune;}

	public void setNomeComune(String nomeComune){ this.nomeComune=nomeComune;}

	public String getTargaProvincia(){ return targaProvincia;}

	public void setTargaProvincia(String targaProvincia){ this.targaProvincia=targaProvincia;}


}    