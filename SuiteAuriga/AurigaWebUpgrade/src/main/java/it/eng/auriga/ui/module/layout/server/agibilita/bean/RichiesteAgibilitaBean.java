/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class RichiesteAgibilitaBean{
	
	@NumeroColonna(numero = "1")
	@TipoData(tipo = Tipo.DATA)
	private Date richiestaDel;
	
	@NumeroColonna(numero = "2")
	private String codFiscale;
	
	@NumeroColonna(numero = "3")
	private String cognomeNome;
	
	@NumeroColonna(numero = "4")
	private String eMail;
	
	@NumeroColonna(numero = "5")
	private String telefono;
	
	@NumeroColonna(numero = "6")
	private String motivoRichiesta;
	
	@NumeroColonna(numero = "7")
	private String stato;
	
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA)
	private Date dataEvasione;
	
	@NumeroColonna(numero = "9")
	private String evasaDa;
	
	@NumeroColonna(numero = "10")
	private Integer vecchioLimiteRich;
	
	@NumeroColonna(numero = "11")
	private Integer nuovoLimiteRich;
	
	@NumeroColonna(numero = "12")
	private String idRichiesta;
	
	@NumeroColonna(numero = "13")
	private String motivoResp;

	public Date getRichiestaDel() {
		return richiestaDel;
	}

	public String getCodFiscale() {
		return codFiscale;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public String geteMail() {
		return eMail;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getMotivoRichiesta() {
		return motivoRichiesta;
	}

	public String getStato() {
		return stato;
	}

	public Date getDataEvasione() {
		return dataEvasione;
	}

	public String getEvasaDa() {
		return evasaDa;
	}

	public void setRichiestaDel(Date richiestaDel) {
		this.richiestaDel = richiestaDel;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setMotivoRichiesta(String motivoRichiesta) {
		this.motivoRichiesta = motivoRichiesta;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setDataEvasione(Date dataEvasione) {
		this.dataEvasione = dataEvasione;
	}

	public void setEvasaDa(String evasaDa) {
		this.evasaDa = evasaDa;
	}

	public Integer getNuovoLimiteRich() {
		return nuovoLimiteRich;
	}

	public void setNuovoLimiteRich(Integer nuovoLimiteRich) {
		this.nuovoLimiteRich = nuovoLimiteRich;
	}

	public Integer getVecchioLimiteRich() {
		return vecchioLimiteRich;
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public String getMotivoResp() {
		return motivoResp;
	}

	public void setVecchioLimiteRich(Integer vecchioLimiteRich) {
		this.vecchioLimiteRich = vecchioLimiteRich;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
	}

	public void setMotivoResp(String motivoResp) {
		this.motivoResp = motivoResp;
	}

}
