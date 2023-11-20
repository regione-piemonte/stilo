/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DatiFascTitolarioXMLIOBean {

	@NumeroColonna(numero = "1")
	private String nomeFascicolo;

	@NumeroColonna(numero = "2")
	private String annoFascicolo;

	@NumeroColonna(numero = "3")
	private String idClassifica;

	@NumeroColonna(numero = "4")
	private String indiceClassifica;

	@NumeroColonna(numero = "5")
	private String nroFascicolo;

	@NumeroColonna(numero = "6")
	private String nroSottofascicolo;

	@NumeroColonna(numero = "7")
	private String nroInserto;

	@NumeroColonna(numero = "8")
	private String idFolderFascicolo;

	@NumeroColonna(numero = "11")
	private String nroSecondario;

	@NumeroColonna(numero = "12")
	private String livelloRiservatezza;

	@NumeroColonna(numero = "13")
	private String estremiDocCapofila;

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
	}

	public String getAnnoFascicolo() {
		return annoFascicolo;
	}

	public void setAnnoFascicolo(String annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}

	public String getIdClassifica() {
		return idClassifica;
	}

	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}

	public String getIndiceClassifica() {
		return indiceClassifica;
	}

	public void setIndiceClassifica(String indiceClassifica) {
		this.indiceClassifica = indiceClassifica;
	}

	public String getNroFascicolo() {
		return nroFascicolo;
	}

	public void setNroFascicolo(String nroFascicolo) {
		this.nroFascicolo = nroFascicolo;
	}

	public String getNroSottofascicolo() {
		return nroSottofascicolo;
	}

	public void setNroSottofascicolo(String nroSottofascicolo) {
		this.nroSottofascicolo = nroSottofascicolo;
	}

	public String getIdFolderFascicolo() {
		return idFolderFascicolo;
	}

	public void setIdFolderFascicolo(String idFolderFascicolo) {
		this.idFolderFascicolo = idFolderFascicolo;
	}

	public String getNroSecondario() {
		return nroSecondario;
	}

	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}

	public String getLivelloRiservatezza() {
		return livelloRiservatezza;
	}

	public void setLivelloRiservatezza(String livelloRiservatezza) {
		this.livelloRiservatezza = livelloRiservatezza;
	}

	public String getEstremiDocCapofila() {
		return estremiDocCapofila;
	}

	public void setEstremiDocCapofila(String estremiDocCapofila) {
		this.estremiDocCapofila = estremiDocCapofila;
	}

	public String getNroInserto() {
		return nroInserto;
	}

	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}

}
