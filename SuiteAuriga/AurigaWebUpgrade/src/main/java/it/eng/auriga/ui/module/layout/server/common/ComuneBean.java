/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class ComuneBean extends VisualBean{

	@NumeroColonna(numero = "1")
	private String codIstatComune;
	@NumeroColonna(numero = "2")
	private String nomeComune;	
	@NumeroColonna(numero = "3")
	private String targaProvincia;
	
	private String cap;
	
	@NumeroColonna(numero = "4")
	private String codComuneAlg;
	
	@NumeroColonna(numero = "5")
	private String codIstatRegione;

	@NumeroColonna(numero = "6")
	private String descRegione;
	
	@NumeroColonna(numero = "7")
	private String descProvincia;


	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	public String getTargaProvincia() {
		return targaProvincia;
	}
	public void setTargaProvincia(String targaProvincia) {
		this.targaProvincia = targaProvincia;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCodIstatComune() {
		return codIstatComune;
	}
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}
	public String getCodComuneAlg() {
		return codComuneAlg;
	}
	public void setCodComuneAlg(String codComuneAlg) {
		this.codComuneAlg = codComuneAlg;
	}
	public String getCodIstatRegione() {
		return codIstatRegione;
	}
	public void setCodIstatRegione(String codIstatRegione) {
		this.codIstatRegione = codIstatRegione;
	}
	public String getDescRegione() {
		return descRegione;
	}
	public void setDescRegione(String descRegione) {
		this.descRegione = descRegione;
	}
	public String getDescProvincia() {
		return descProvincia;
	}
	public void setDescProvincia(String descProvincia) {
		this.descProvincia = descProvincia;
	}		
	
}
