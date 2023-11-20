/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

@XmlRootElement
public class ACLFolderBean {
	
	@NumeroColonna(numero = "1")
	private String tipoDestinatario;
	@NumeroColonna(numero = "2")
	private String idDestinatario;
	@NumeroColonna(numero = "3")
	private String denominazioneDestinatario;	
	@NumeroColonna(numero = "5")	
	private String codiceRapido;		
	@NumeroColonna(numero = "17")
	private Flag flgVisualizzaMetadati;
	@NumeroColonna(numero = "18")
	private Flag flgModificaMetadati;
	@NumeroColonna(numero = "19")
	private Flag flgModificaACL;
	@NumeroColonna(numero = "20")
	private Flag flgCopiaFolder;
	@NumeroColonna(numero = "21")
	private Flag flgEliminaFolder;
	@NumeroColonna(numero = "22")
	private Flag flgRipristinoFolder;	
	@NumeroColonna(numero = "23")
	private Flag flgModificaContenutoUd;
	@NumeroColonna(numero = "24")
	private Flag flgModificaContenutoFolder;
	@NumeroColonna(numero = "30")
	private Flag flgEreditata;
	@NumeroColonna(numero = "31")
	private String denominazioneEstesaDestinatario;
	@NumeroColonna(numero = "32")
	private Flag flgAssegnatario;
	@NumeroColonna(numero = "33")
	private Flag flgInvioPerConoscenza;


	public String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getCodiceRapido() {
		return codiceRapido;
	}

	public void setCodiceRapido(String codiceRapido) {
		this.codiceRapido = codiceRapido;
	}

	public Flag getFlgVisualizzaMetadati() {
		return flgVisualizzaMetadati;
	}

	public void setFlgVisualizzaMetadati(Flag flgVisualizzaMetadati) {
		this.flgVisualizzaMetadati = flgVisualizzaMetadati;
	}

	public Flag getFlgModificaMetadati() {
		return flgModificaMetadati;
	}

	public void setFlgModificaMetadati(Flag flgModificaMetadati) {
		this.flgModificaMetadati = flgModificaMetadati;
	}

	public Flag getFlgModificaACL() {
		return flgModificaACL;
	}

	public void setFlgModificaACL(Flag flgModificaACL) {
		this.flgModificaACL = flgModificaACL;
	}

	public Flag getFlgCopiaFolder() {
		return flgCopiaFolder;
	}

	public void setFlgCopiaFolder(Flag flgCopiaFolder) {
		this.flgCopiaFolder = flgCopiaFolder;
	}

	public Flag getFlgEliminaFolder() {
		return flgEliminaFolder;
	}

	public void setFlgEliminaFolder(Flag flgEliminaFolder) {
		this.flgEliminaFolder = flgEliminaFolder;
	}

	public Flag getFlgRipristinoFolder() {
		return flgRipristinoFolder;
	}

	public void setFlgRipristinoFolder(Flag flgRipristinoFolder) {
		this.flgRipristinoFolder = flgRipristinoFolder;
	}

	public Flag getFlgModificaContenutoUd() {
		return flgModificaContenutoUd;
	}

	public void setFlgModificaContenutoUd(Flag flgModificaContenutoUd) {
		this.flgModificaContenutoUd = flgModificaContenutoUd;
	}

	public Flag getFlgModificaContenutoFolder() {
		return flgModificaContenutoFolder;
	}

	public void setFlgModificaContenutoFolder(Flag flgModificaContenutoFolder) {
		this.flgModificaContenutoFolder = flgModificaContenutoFolder;
	}

	public Flag getFlgEreditata() {
		return flgEreditata;
	}

	public void setFlgEreditata(Flag flgEreditata) {
		this.flgEreditata = flgEreditata;
	}

	public String getDenominazioneDestinatario() {
		return denominazioneDestinatario;
	}

	public void setDenominazioneDestinatario(String denominazioneDestinatario) {
		this.denominazioneDestinatario = denominazioneDestinatario;
	}
	
	public String getDenominazioneEstesaDestinatario() {
		return denominazioneEstesaDestinatario;
	}

	public void setDenominazioneEstesaDestinatario(
			String denominazioneEstesaDestinatario) {
		this.denominazioneEstesaDestinatario = denominazioneEstesaDestinatario;
	}

	public Flag getFlgAssegnatario() {
		return flgAssegnatario;
	}

	public void setFlgAssegnatario(Flag flgAssegnatario) {
		this.flgAssegnatario = flgAssegnatario;
	}

	public Flag getFlgInvioPerConoscenza() {
		return flgInvioPerConoscenza;
	}

	public void setFlgInvioPerConoscenza(Flag flgInvioPerConoscenza) {
		this.flgInvioPerConoscenza = flgInvioPerConoscenza;
	}
	
}
