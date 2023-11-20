/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AttributiUdClassFasc {

	@NumeroColonna(numero = "1")
	private String idClassifica;
	
	@NumeroColonna(numero = "4")
	private String annoFascicolo;
	
	private String indice;
	
	private String nomeFascicolo;
	
	@NumeroColonna(numero = "5")
	private String nroFascicolo;
	
	@NumeroColonna(numero = "6")
	private String nroSottofascicolo;	
	
	@NumeroColonna(numero = "9")
	private String tipoRelazione;
	
	@NumeroColonna(numero = "12")
	private String idFolderFascicolo;
	
	private String classifiche;
	
	@NumeroColonna(numero = "17")
	private String flgAttiva;

	public String getIdClassifica() {
		return idClassifica;
	}

	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}

	public String getAnnoFascicolo() {
		return annoFascicolo;
	}

	public void setAnnoFascicolo(String annoFascicolo) {
		this.annoFascicolo = annoFascicolo;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getNomeFascicolo() {
		return nomeFascicolo;
	}

	public void setNomeFascicolo(String nomeFascicolo) {
		this.nomeFascicolo = nomeFascicolo;
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

	public String getTipoRelazione() {
		return tipoRelazione;
	}

	public void setTipoRelazione(String tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}

	public String getIdFolderFascicolo() {
		return idFolderFascicolo;
	}

	public void setIdFolderFascicolo(String idFolderFascicolo) {
		this.idFolderFascicolo = idFolderFascicolo;
	}

	public String getClassifiche() {
		return classifiche;
	}

	public void setClassifiche(String classifiche) {
		this.classifiche = classifiche;
	}

	public String getFlgAttiva() {
		return flgAttiva;
	}

	public void setFlgAttiva(String flgAttiva) {
		this.flgAttiva = flgAttiva;
	}
	
}
