/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

public class ParametroRigaFoglioXmlBean {

	@NumeroColonna(numero = "1")
	private String nome;
	
	@NumeroColonna(numero = "2")
	private String valore;
	
	//Tipo di dato nella colonna: S = Stringa; N = Numero; D = Data
	@NumeroColonna(numero = "3")
	private String tipo;
	
	@NumeroColonna(numero = "4")
	private String titolo;
	
	/*Spiegazione di come compilare la colonna*/
	@NumeroColonna(numero = "5")
	private String infoCompilazione;
	
	/*Tasti/azioni da far apparire accanto al campo. Valori possibili (separati da ; se più di uno)*/
	@NumeroColonna(numero = "6")
	private String azioni;
	
	public String getNome() {
		return nome;
	}

	public String getValore() {
		return valore;
	}

	public String getTipo() {
		return tipo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getInfoCompilazione() {
		return infoCompilazione;
	}

	public String getAzioni() {
		return azioni;
	}

	public void setInfoCompilazione(String infoCompilazione) {
		this.infoCompilazione = infoCompilazione;
	}

	public void setAzioni(String azioni) {
		this.azioni = azioni;
	}
	
}