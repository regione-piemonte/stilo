/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TAnagFruitoriCaselleBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private String codice;
	private String codiceCompleto;
	private String denominazione;
	private String denominazioneCompleta;
	private boolean flgAttivo;
	private String idFruitoreCasella;
	private String idProvFruitore;
	private String idUteIns;
	private String idUteUltimoAgg;
	private String tipo;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String idFruitoreSup;
	private String idEnteAOO;

	public String getCodice(){ return codice;}

	public void setCodice(String codice){ this.codice=codice;}

	public String getCodiceCompleto(){ return codiceCompleto;}

	public void setCodiceCompleto(String codiceCompleto){ this.codiceCompleto=codiceCompleto;}

	public String getDenominazione(){ return denominazione;}

	public void setDenominazione(String denominazione){ this.denominazione=denominazione;}

	public String getDenominazioneCompleta(){ return denominazioneCompleta;}

	public void setDenominazioneCompleta(String denominazioneCompleta){ this.denominazioneCompleta=denominazioneCompleta;}

	public boolean getFlgAttivo(){ return flgAttivo;}

	public void setFlgAttivo(boolean flgAttivo){ this.flgAttivo=flgAttivo;}

	public String getIdFruitoreCasella(){ return idFruitoreCasella;}

	public void setIdFruitoreCasella(String idFruitoreCasella){ this.idFruitoreCasella=idFruitoreCasella;}

	public String getIdProvFruitore(){ return idProvFruitore;}

	public void setIdProvFruitore(String idProvFruitore){ this.idProvFruitore=idProvFruitore;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public String getTipo(){ return tipo;}

	public void setTipo(String tipo){ this.tipo=tipo;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}

	public String getIdFruitoreSup() {
		return idFruitoreSup;
	}

	public void setIdFruitoreSup(String idFruitoreSup) {
		this.idFruitoreSup = idFruitoreSup;
	}

	public String getIdEnteAOO() {
		return idEnteAOO;
	}

	public void setIdEnteAOO(String idEnteAOO) {
		this.idEnteAOO = idEnteAOO;
	}


}    