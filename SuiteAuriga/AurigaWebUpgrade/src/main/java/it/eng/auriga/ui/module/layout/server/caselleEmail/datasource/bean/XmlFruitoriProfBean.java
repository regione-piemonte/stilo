/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class XmlFruitoriProfBean {
	
	// 1: Id. del fruitore (ID_FRUITORE_CASELLA)	
	@NumeroColonna(numero = "1")
	private String idFruitoreCasella;
	
	// 2: Tipo di fruitore: ENTE, AOO, UO		
	@NumeroColonna(numero = "2")
	private String tipo;

	// 3: ID_SP_AOO se il fruitore è un ENTE o AOO e ID_UO se è un'unità  organizzativa		
	@NumeroColonna(numero = "3")
	private String idSpAooUo;
	
	// 4: (valori 1/0) String di profilo "smistatore" (gestione di tutte le mail ricevute)			
	@NumeroColonna(numero = "4")
	private String flgSmistatore;
	
	// 5: (valori 1/0 String di profilo "mittente" (= abilitato all'invio di e-mail dalla casella)	
	@NumeroColonna(numero = "5")
	private String flgMittente;
	
	// 6: (valori 1/0) String di profilo "amministratore"		
	@NumeroColonna(numero = "6")
	private String flgAmministratore;
	
	// 7: (valori 1/0) String di inclusione dei fruitori gerarchicamente subordinati			
	@NumeroColonna(numero = "7")
	private String flgIncludiSubordinati;
	
	// 8: Codice del fruitore	
	@NumeroColonna(numero = "8")
	private String codice;
	
	// 9: Denominazione del fruitore		
	@NumeroColonna(numero = "9")
	private String denominazione;
	

	public String getIdFruitoreCasella() {
		return idFruitoreCasella;
	}

	public void setIdFruitoreCasella(String idFruitoreCasella) {
		this.idFruitoreCasella = idFruitoreCasella;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdSpAooUo() {
		return idSpAooUo;
	}

	public void setIdSpAooUo(String idSpAooUo) {
		this.idSpAooUo = idSpAooUo;
	}

	public String getFlgSmistatore() {
		return flgSmistatore;
	}

	public void setFlgSmistatore(String flgSmistatore) {
		this.flgSmistatore = flgSmistatore;
	}

	public String getFlgMittente() {
		return flgMittente;
	}

	public void setFlgMittente(String flgMittente) {
		this.flgMittente = flgMittente;
	}

	public String getFlgAmministratore() {
		return flgAmministratore;
	}

	public void setFlgAmministratore(String flgAmministratore) {
		this.flgAmministratore = flgAmministratore;
	}

	public String getFlgIncludiSubordinati() {
		return flgIncludiSubordinati;
	}

	public void setFlgIncludiSubordinati(String flgIncludiSubordinati) {
		this.flgIncludiSubordinati = flgIncludiSubordinati;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
}

