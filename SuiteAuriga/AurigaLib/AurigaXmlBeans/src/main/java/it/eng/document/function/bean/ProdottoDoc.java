/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;

public class ProdottoDoc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6474346243547841272L;
	@NumeroColonna(numero = "1")
	private String nome;
	@NumeroColonna(numero = "2")
    private String quantita;
	@NumeroColonna(numero = "3")
    private String prezzo;
	@NumeroColonna(numero = "4")
    private ImportoDoc importoDoc;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public ImportoDoc getImportoDoc() {
		return importoDoc;
	}
	public void setImportoDoc(ImportoDoc importoDoc) {
		this.importoDoc = importoDoc;
	}
}
