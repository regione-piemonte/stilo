/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum OperazioneDocEnum {

	VALIDAZIONE_XML_NOTIFICA("validazione_xml", "Validazione xml notifica"), FIRMA_AUTOMATICA("Firma Automatica", "Firma Automatica"), ARCHIVIAZIONE_DOC(
			"Archiviazione Documento", "Archiviazione Documento"), INVIO_PEC("Invio PEC",
					"Invio PEC"), INVIO_PEO("Invio PEO", "Invio PEO"), ARCHIVIAZIONE_EMAIL("Archiviazione email", "Archiviazione email");

	private final String nome;
	private final String descrizione;

	private OperazioneDocEnum(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public static final OperazioneDocEnum fromNome(String nome) {
		for (OperazioneDocEnum value : values()) {
			if (value.getNome().equals(nome))
				return value;
		}
		return null;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

}
