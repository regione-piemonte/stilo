package it.eng.utility.client.sib.determine.data;

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
