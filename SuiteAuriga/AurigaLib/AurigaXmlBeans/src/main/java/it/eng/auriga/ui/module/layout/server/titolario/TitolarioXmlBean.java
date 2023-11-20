/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class TitolarioXmlBean {
	
	@NumeroColonna(numero = "1")
	private String nroLivello;
	
	@NumeroColonna(numero = "2")
	private String idClassificazione;
	
	@NumeroColonna(numero = "3")
	private String descrizione;
	
	@NumeroColonna(numero = "4")
	private String tipo;
	
	@NumeroColonna(numero = "5")
	private String descrizioneEstesa;
	
	@NumeroColonna(numero = "6")
	private String paroleChiave;
	
	@NumeroColonna(numero = "7")
	private String indice;
	
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsValidaDal;
	
	@NumeroColonna(numero = "9")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date tsValidaFinoAl;
	
	@NumeroColonna(numero = "17")
	private String periodoConservAnni;
	
	@NumeroColonna(numero = "19")
	private Boolean flgSelXFinalita;
	
	@NumeroColonna(numero = "20")
	private String idClassificaSup;
	
	@NumeroColonna(numero = "21")
	private String desClassificaSup;
	
	@NumeroColonna(numero = "24")
	private BigDecimal score;
	
	@NumeroColonna(numero = "25")
	private String indiceXOrd;

	public String getNroLivello() {
		return nroLivello;
	}

	public void setNroLivello(String nroLivello) {
		this.nroLivello = nroLivello;
	}

	public String getIdClassificazione() {
		return idClassificazione;
	}

	public void setIdClassificazione(String idClassificazione) {
		this.idClassificazione = idClassificazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public String getParoleChiave() {
		return paroleChiave;
	}

	public void setParoleChiave(String paroleChiave) {
		this.paroleChiave = paroleChiave;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Date getTsValidaDal() {
		return tsValidaDal;
	}

	public void setTsValidaDal(Date tsValidaDal) {
		this.tsValidaDal = tsValidaDal;
	}

	public Date getTsValidaFinoAl() {
		return tsValidaFinoAl;
	}

	public void setTsValidaFinoAl(Date tsValidaFinoAl) {
		this.tsValidaFinoAl = tsValidaFinoAl;
	}

	public String getPeriodoConservAnni() {
		return periodoConservAnni;
	}

	public void setPeriodoConservAnni(String periodoConservAnni) {
		this.periodoConservAnni = periodoConservAnni;
	}

	public Boolean getFlgSelXFinalita() {
		return flgSelXFinalita;
	}

	public void setFlgSelXFinalita(Boolean flgSelXFinalita) {
		this.flgSelXFinalita = flgSelXFinalita;
	}

	public String getIdClassificaSup() {
		return idClassificaSup;
	}

	public void setIdClassificaSup(String idClassificaSup) {
		this.idClassificaSup = idClassificaSup;
	}

	public String getDesClassificaSup() {
		return desClassificaSup;
	}

	public void setDesClassificaSup(String desClassificaSup) {
		this.desClassificaSup = desClassificaSup;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getIndiceXOrd() {
		return indiceXOrd;
	}

	public void setIndiceXOrd(String indiceXOrd) {
		this.indiceXOrd = indiceXOrd;
	}

}