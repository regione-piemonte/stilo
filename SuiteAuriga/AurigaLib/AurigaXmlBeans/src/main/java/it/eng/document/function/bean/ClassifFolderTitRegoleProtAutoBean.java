/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class ClassifFolderTitRegoleProtAutoBean implements Serializable {

	private static final long serialVersionUID = 5816825702713482268L;

	@NumeroColonna(numero = "1")
	private String flgEsistente;
	
	@NumeroColonna(numero = "2")
	private String idFolder;
	
	@NumeroColonna(numero = "3")
	private String idClassfica;
	
	@NumeroColonna(numero = "4")
	private String nomeFolder;
	
	@NumeroColonna(numero = "5")
	private String annoApertura;
	
	@NumeroColonna(numero = "6")
	private String nroProgFasc;
	
	@NumeroColonna(numero = "7")
	private String nroSottoFasc;
	
	@NumeroColonna(numero = "8")
	private String nroInserto;
	
	@NumeroColonna(numero = "9")
	private String codSecondario;
	
	@NumeroColonna(numero = "10")
	private String indiceClass;
	
	@NumeroColonna(numero = "11")
	private String descClass;
	
	@NumeroColonna(numero = "12")
	private String fascCapofila;

	public String getFlgEsistente() {
		return flgEsistente;
	}

	public void setFlgEsistente(String flgEsistente) {
		this.flgEsistente = flgEsistente;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getIdClassfica() {
		return idClassfica;
	}

	public void setIdClassfica(String idClassfica) {
		this.idClassfica = idClassfica;
	}

	public String getNomeFolder() {
		return nomeFolder;
	}

	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}

	public String getAnnoApertura() {
		return annoApertura;
	}

	public void setAnnoApertura(String annoApertura) {
		this.annoApertura = annoApertura;
	}

	public String getNroProgFasc() {
		return nroProgFasc;
	}

	public void setNroProgFasc(String nroProgFasc) {
		this.nroProgFasc = nroProgFasc;
	}

	public String getNroSottoFasc() {
		return nroSottoFasc;
	}

	public void setNroSottoFasc(String nroSottoFasc) {
		this.nroSottoFasc = nroSottoFasc;
	}

	public String getNroInserto() {
		return nroInserto;
	}

	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}

	public String getCodSecondario() {
		return codSecondario;
	}

	public void setCodSecondario(String codSecondario) {
		this.codSecondario = codSecondario;
	}

	public String getIndiceClass() {
		return indiceClass;
	}

	public void setIndiceClass(String indiceClass) {
		this.indiceClass = indiceClass;
	}

	public String getDescClass() {
		return descClass;
	}

	public void setDescClass(String descClass) {
		this.descClass = descClass;
	}

	public String getFascCapofila() {
		return fascCapofila;
	}

	public void setFascCapofila(String fascCapofila) {
		this.fascCapofila = fascCapofila;
	}

}
