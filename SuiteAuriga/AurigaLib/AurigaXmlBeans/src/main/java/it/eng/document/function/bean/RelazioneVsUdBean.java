/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RelazioneVsUdBean implements Serializable{

	private static final long serialVersionUID = 5611310655086324291L;
		
	@NumeroColonna(numero = "1")
	private String idUdCollegata;
	@NumeroColonna(numero = "2")
	private String categoria;
	@NumeroColonna(numero = "3")
	private String siglaProt;
	@NumeroColonna(numero = "4")
	private String annoProt;
	@NumeroColonna(numero = "5")
	private String nroProt;
	@NumeroColonna(numero = "6")
	private String valoreFisso;
	@NumeroColonna(numero = "12")
	private String motiviCollegamento;
	
	
	public String getIdUdCollegata() {
		return idUdCollegata;
	}
	public void setIdUdCollegata(String idUdCollegata) {
		this.idUdCollegata = idUdCollegata;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSiglaProt() {
		return siglaProt;
	}
	public void setSiglaProt(String siglaProt) {
		this.siglaProt = siglaProt;
	}
	public String getAnnoProt() {
		return annoProt;
	}
	public void setAnnoProt(String annoProt) {
		this.annoProt = annoProt;
	}
	public String getNroProt() {
		return nroProt;
	}
	public void setNroProt(String nroProt) {
		this.nroProt = nroProt;
	}
	public String getValoreFisso() {
		return valoreFisso;
	}
	public void setValoreFisso(String valoreFisso) {
		this.valoreFisso = valoreFisso;
	}
	public String getMotiviCollegamento() {
		return motiviCollegamento;
	}
	public void setMotiviCollegamento(String motiviCollegamento) {
		this.motiviCollegamento = motiviCollegamento;
	}
	
}
