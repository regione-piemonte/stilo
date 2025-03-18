/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum Evento {

	ADOZIONE("adozione"), AGGIORNAMENTO("aggiornamento"), AGGIUDICA("aggiudica"), ARCHIVIAZIONE("archiviazione"), VISTO("visto");

	Evento(String nome) {
		this.nome = nome;
	}

	private final String nome;

	public String getNome() {
		return nome;
	}

}
