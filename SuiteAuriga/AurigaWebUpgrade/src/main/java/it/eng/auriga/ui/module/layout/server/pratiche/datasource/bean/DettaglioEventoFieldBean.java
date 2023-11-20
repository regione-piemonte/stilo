/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DettaglioEventoFieldBean {

	@NumeroColonna(numero = "1")
	private int numero;
	@NumeroColonna(numero = "2")
	private String nome;
	@NumeroColonna(numero = "3")
	private String label;
	@NumeroColonna(numero = "4")
	private String tipo;
	@NumeroColonna(numero = "5")
	private int maxLength;
	@NumeroColonna(numero = "7")
	private String value;
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
