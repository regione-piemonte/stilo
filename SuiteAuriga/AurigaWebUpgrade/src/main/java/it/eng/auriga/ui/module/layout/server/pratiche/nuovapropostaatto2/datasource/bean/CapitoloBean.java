/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class CapitoloBean extends VisualBean {

	private String id;

	@NumeroColonna(numero = "1")
	private String codiceCapitolo;

	@NumeroColonna(numero = "2")
	private String descrizioneCapitolo;

	@NumeroColonna(numero = "3")
	private String codiceMissione;

	@NumeroColonna(numero = "4")
	private String descrizioneMissione;

	@NumeroColonna(numero = "5")
	private String codiceProgramma;
	
	@NumeroColonna(numero = "6")
	private String descrizioneProgramma;

	@NumeroColonna(numero = "7")
	private String codiceTitolo;
	
	@NumeroColonna(numero = "8")
	private String descrizioneTitolo;
	
	@NumeroColonna(numero = "9")
	private String codiceMacroAggregato;	

	@NumeroColonna(numero = "10")
	private String descrizioneMacroAggregato;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}

	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}

	public String getDescrizioneCapitolo() {
		return descrizioneCapitolo;
	}

	public void setDescrizioneCapitolo(String descrizioneCapitolo) {
		this.descrizioneCapitolo = descrizioneCapitolo;
	}

	public String getCodiceMissione() {
		return codiceMissione;
	}

	public void setCodiceMissione(String codiceMissione) {
		this.codiceMissione = codiceMissione;
	}

	public String getDescrizioneMissione() {
		return descrizioneMissione;
	}

	public void setDescrizioneMissione(String descrizioneMissione) {
		this.descrizioneMissione = descrizioneMissione;
	}

	public String getCodiceProgramma() {
		return codiceProgramma;
	}

	public void setCodiceProgramma(String codiceProgramma) {
		this.codiceProgramma = codiceProgramma;
	}

	public String getDescrizioneProgramma() {
		return descrizioneProgramma;
	}

	public void setDescrizioneProgramma(String descrizioneProgramma) {
		this.descrizioneProgramma = descrizioneProgramma;
	}

	public String getCodiceTitolo() {
		return codiceTitolo;
	}

	public void setCodiceTitolo(String codiceTitolo) {
		this.codiceTitolo = codiceTitolo;
	}

	public String getDescrizioneTitolo() {
		return descrizioneTitolo;
	}

	public void setDescrizioneTitolo(String descrizioneTitolo) {
		this.descrizioneTitolo = descrizioneTitolo;
	}

	public String getCodiceMacroAggregato() {
		return codiceMacroAggregato;
	}

	public void setCodiceMacroAggregato(String codiceMacroAggregato) {
		this.codiceMacroAggregato = codiceMacroAggregato;
	}

	public String getDescrizioneMacroAggregato() {
		return descrizioneMacroAggregato;
	}

	public void setDescrizioneMacroAggregato(String descrizioneMacroAggregato) {
		this.descrizioneMacroAggregato = descrizioneMacroAggregato;
	}

	
}
