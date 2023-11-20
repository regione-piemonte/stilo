/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class UoProtocollanteBean {

	@NumeroColonna(numero = "1")
	private String idUo;
	
	@NumeroColonna(numero = "2")
	private String descrizione; // livelli - descrizione
	
	//idRubrica?
	
	@NumeroColonna(numero = "7")
	private String flgPreimpDestProtEntrata;	
	
	@NumeroColonna(numero = "8")
	private String flgUoRegistrazione;	
	
	@NumeroColonna(numero = "9")
	private String flgUoProponenteAtti;	
	
	@NumeroColonna(numero = "10")
	private String flgUfficioGare;
	
	@NumeroColonna(numero = "11")
	private String flgSoloMezzoTrasmissionePEC;
	
	@NumeroColonna(numero = "12")
	private String flgInibitaScansioneMassiva;
	
		
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getFlgPreimpDestProtEntrata() {
		return flgPreimpDestProtEntrata;
	}
	public void setFlgPreimpDestProtEntrata(String flgPreimpDestProtEntrata) {
		this.flgPreimpDestProtEntrata = flgPreimpDestProtEntrata;
	}
	public String getFlgUoRegistrazione() {
		return flgUoRegistrazione;
	}
	public void setFlgUoRegistrazione(String flgUoRegistrazione) {
		this.flgUoRegistrazione = flgUoRegistrazione;
	}
	public String getFlgUoProponenteAtti() {
		return flgUoProponenteAtti;
	}
	public void setFlgUoProponenteAtti(String flgUoProponenteAtti) {
		this.flgUoProponenteAtti = flgUoProponenteAtti;
	}
	public String getFlgUfficioGare() {
		return flgUfficioGare;
	}
	public void setFlgUfficioGare(String flgUfficioGare) {
		this.flgUfficioGare = flgUfficioGare;
	}
	public String getFlgSoloMezzoTrasmissionePEC() {
		return flgSoloMezzoTrasmissionePEC;
	}
	public void setFlgSoloMezzoTrasmissionePEC(String flgSoloMezzoTrasmissionePEC) {
		this.flgSoloMezzoTrasmissionePEC = flgSoloMezzoTrasmissionePEC;
	}
	public String getFlgInibitaScansioneMassiva() {
		return flgInibitaScansioneMassiva;
	}
	public void setFlgInibitaScansioneMassiva(String flgInibitaScansioneMassiva) {
		this.flgInibitaScansioneMassiva = flgInibitaScansioneMassiva;
	}
	
}
