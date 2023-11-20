/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class ProponentiXmlBean implements Serializable{
	
	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private String idScrivaniaRdP;
	
	@NumeroColonna(numero = "3")
	private String idScrivaniaDirigente;
	
	@NumeroColonna(numero = "4")
	private String idScrivaniaDirettore;
	
	@NumeroColonna(numero = "5")
	private String codRapido;
	
	@NumeroColonna(numero = "6")
	private String descrizione;
	
	@NumeroColonna(numero = "7")
	private String desScrivaniaRdP;

	@NumeroColonna(numero = "8")
	private String desScrivaniaDirigente;

	@NumeroColonna(numero = "9")
	private String desScrivaniaDirettore;
	
	@NumeroColonna(numero = "10")
	private String codUoScrivaniaRdP;

	@NumeroColonna(numero = "11")
	private String codUoScrivaniaDirigente;

	@NumeroColonna(numero = "12")
	private String codUoScrivaniaDirettore;

	@NumeroColonna(numero = "13")
	private String flgAggiuntaRdP;
	
	@NumeroColonna(numero = "14")
	private String flgAggiuntaDirigente;

	@NumeroColonna(numero = "15")
	private String flgForzaDispFirmaScrivaniaDirigente;
	
	@NumeroColonna(numero = "16")
	private String flgForzaDispFirmaScrivaniaDirettore;
	
	@NumeroColonna(numero = "17")
	private String tipoVistoScrivaniaDirigente;
	
	@NumeroColonna(numero = "18")
	private String tipoVistoScrivaniaDirettore;
	
	
	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getIdScrivaniaRdP() {
		return idScrivaniaRdP;
	}

	public void setIdScrivaniaRdP(String idScrivaniaRdP) {
		this.idScrivaniaRdP = idScrivaniaRdP;
	}

	public String getIdScrivaniaDirigente() {
		return idScrivaniaDirigente;
	}

	public void setIdScrivaniaDirigente(String idScrivaniaDirigente) {
		this.idScrivaniaDirigente = idScrivaniaDirigente;
	}

	public String getIdScrivaniaDirettore() {
		return idScrivaniaDirettore;
	}

	public void setIdScrivaniaDirettore(String idScrivaniaDirettore) {
		this.idScrivaniaDirettore = idScrivaniaDirettore;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDesScrivaniaRdP() {
		return desScrivaniaRdP;
	}

	public void setDesScrivaniaRdP(String desScrivaniaRdP) {
		this.desScrivaniaRdP = desScrivaniaRdP;
	}

	public String getDesScrivaniaDirigente() {
		return desScrivaniaDirigente;
	}

	public void setDesScrivaniaDirigente(String desScrivaniaDirigente) {
		this.desScrivaniaDirigente = desScrivaniaDirigente;
	}

	public String getDesScrivaniaDirettore() {
		return desScrivaniaDirettore;
	}

	public void setDesScrivaniaDirettore(String desScrivaniaDirettore) {
		this.desScrivaniaDirettore = desScrivaniaDirettore;
	}

	public String getCodUoScrivaniaRdP() {
		return codUoScrivaniaRdP;
	}

	public void setCodUoScrivaniaRdP(String codUoScrivaniaRdP) {
		this.codUoScrivaniaRdP = codUoScrivaniaRdP;
	}

	public String getCodUoScrivaniaDirigente() {
		return codUoScrivaniaDirigente;
	}

	public void setCodUoScrivaniaDirigente(String codUoScrivaniaDirigente) {
		this.codUoScrivaniaDirigente = codUoScrivaniaDirigente;
	}

	public String getCodUoScrivaniaDirettore() {
		return codUoScrivaniaDirettore;
	}

	public void setCodUoScrivaniaDirettore(String codUoScrivaniaDirettore) {
		this.codUoScrivaniaDirettore = codUoScrivaniaDirettore;
	}

	public String getFlgAggiuntaRdP() {
		return flgAggiuntaRdP;
	}

	public void setFlgAggiuntaRdP(String flgAggiuntaRdP) {
		this.flgAggiuntaRdP = flgAggiuntaRdP;
	}

	public String getFlgAggiuntaDirigente() {
		return flgAggiuntaDirigente;
	}

	public void setFlgAggiuntaDirigente(String flgAggiuntaDirigente) {
		this.flgAggiuntaDirigente = flgAggiuntaDirigente;
	}

	public String getFlgForzaDispFirmaScrivaniaDirigente() {
		return flgForzaDispFirmaScrivaniaDirigente;
	}

	public void setFlgForzaDispFirmaScrivaniaDirigente(String flgForzaDispFirmaScrivaniaDirigente) {
		this.flgForzaDispFirmaScrivaniaDirigente = flgForzaDispFirmaScrivaniaDirigente;
	}

	public String getFlgForzaDispFirmaScrivaniaDirettore() {
		return flgForzaDispFirmaScrivaniaDirettore;
	}

	public void setFlgForzaDispFirmaScrivaniaDirettore(String flgForzaDispFirmaScrivaniaDirettore) {
		this.flgForzaDispFirmaScrivaniaDirettore = flgForzaDispFirmaScrivaniaDirettore;
	}

	public String getTipoVistoScrivaniaDirigente() {
		return tipoVistoScrivaniaDirigente;
	}

	public void setTipoVistoScrivaniaDirigente(String tipoVistoScrivaniaDirigente) {
		this.tipoVistoScrivaniaDirigente = tipoVistoScrivaniaDirigente;
	}

	public String getTipoVistoScrivaniaDirettore() {
		return tipoVistoScrivaniaDirettore;
	}

	public void setTipoVistoScrivaniaDirettore(String tipoVistoScrivaniaDirettore) {
		this.tipoVistoScrivaniaDirettore = tipoVistoScrivaniaDirettore;
	}
	
}
