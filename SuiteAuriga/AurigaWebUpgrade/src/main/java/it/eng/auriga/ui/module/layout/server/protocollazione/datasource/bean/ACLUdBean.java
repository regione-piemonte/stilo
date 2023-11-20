/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.Flag;

public class ACLUdBean {
	
	@NumeroColonna(numero = "1")
	private String tipoDestinatario;
	@NumeroColonna(numero = "2")
	private String idDestinatario;
	@NumeroColonna(numero = "5")	
	private String codiceRapido;		
	@NumeroColonna(numero = "17")
	private Flag flgVisualizzaMetadati;
	@NumeroColonna(numero = "18")
	private Flag flgVisualizzaFile;
	@NumeroColonna(numero = "19")
	private Flag flgModificaMetadati;
	@NumeroColonna(numero = "20")
	private Flag flgModificaFile;
	@NumeroColonna(numero = "21")
	private Flag flgModificaACL;
	@NumeroColonna(numero = "22")
	private Flag flgCopiaUd;
	@NumeroColonna(numero = "23")
	private Flag flgEliminaUd;
	@NumeroColonna(numero = "24")
	private Flag flgRipristinoUd;	
	@NumeroColonna(numero = "30")
	private Flag flgEreditata;

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

	public Flag getFlgVisualizzaFile() {
		return flgVisualizzaFile;
	}

	public void setFlgVisualizzaFile(Flag flgVisualizzaFile) {
		this.flgVisualizzaFile = flgVisualizzaFile;
	}

	public Flag getFlgModificaMetadati() {
		return flgModificaMetadati;
	}

	public void setFlgModificaMetadati(Flag flgModificaMetadati) {
		this.flgModificaMetadati = flgModificaMetadati;
	}

	public Flag getFlgModificaFile() {
		return flgModificaFile;
	}

	public void setFlgModificaFile(Flag flgModificaFile) {
		this.flgModificaFile = flgModificaFile;
	}

	public Flag getFlgModificaACL() {
		return flgModificaACL;
	}

	public void setFlgModificaACL(Flag flgModificaACL) {
		this.flgModificaACL = flgModificaACL;
	}

	public Flag getFlgCopiaUd() {
		return flgCopiaUd;
	}

	public void setFlgCopiaUd(Flag flgCopiaUd) {
		this.flgCopiaUd = flgCopiaUd;
	}

	public Flag getFlgEliminaUd() {
		return flgEliminaUd;
	}

	public void setFlgEliminaUd(Flag flgEliminaUd) {
		this.flgEliminaUd = flgEliminaUd;
	}

	public Flag getFlgRipristinoUd() {
		return flgRipristinoUd;
	}

	public void setFlgRipristinoUd(Flag flgRipristinoUd) {
		this.flgRipristinoUd = flgRipristinoUd;
	}

	public Flag getFlgEreditata() {
		return flgEreditata;
	}

	public void setFlgEreditata(Flag flgEreditata) {
		this.flgEreditata = flgEreditata;
	}	
	
}
