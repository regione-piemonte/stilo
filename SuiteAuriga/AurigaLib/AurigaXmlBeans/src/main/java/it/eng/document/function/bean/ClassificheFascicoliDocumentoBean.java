/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClassificheFascicoliDocumentoBean implements Serializable{

	private static final long serialVersionUID = 5611310655086324291L;
	
	@NumeroColonna(numero = "1")
	private String idClassifica;
	
	@NumeroColonna(numero = "4")
	private String annoFascicolo;
	
	@NumeroColonna(numero = "5")
	private String nroFascicolo;
	
	@NumeroColonna(numero = "6")
	private String nroSottofascicolo;
	
	@NumeroColonna(numero = "7")
	private String nroInserto;
	
	@NumeroColonna(numero = "9")
	private String tipoRelazione;
	
	@NumeroColonna(numero = "12")
	private String idFolderFascicolo;
	
	@NumeroColonna(numero = "17")
	private String flgAttiva;
	
	private String provCIClassif;
	
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

	public String getTipoRelazione() {
		return tipoRelazione;
	}

	public void setTipoRelazione(String tipoRelazione) {
		this.tipoRelazione = tipoRelazione;
	}

	public void setIdFolderFascicolo(String idFolderFascicolo) {
		this.idFolderFascicolo = idFolderFascicolo;
	}

	public String getProvCIClassif() {
		return provCIClassif;
	}

	public void setProvCIClassif(String provCIClassif) {
		this.provCIClassif = provCIClassif;
	}

	public String getNroInserto() {
		return nroInserto;
	}

	public void setNroInserto(String nroInserto) {
		this.nroInserto = nroInserto;
	}

	public String getFlgAttiva() {
		return flgAttiva;
	}

	public void setFlgAttiva(String flgAttiva) {
		this.flgAttiva = flgAttiva;
	}
	
}
