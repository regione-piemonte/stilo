/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum TemplateVarsEnum {
	SIGLA_REGISTRO("siglaRegistro"),
	ANNO_REGISTRO("annoRegistro"),
	MESE_REGISTRO("meseRegistro"),
	DATA_REGISTRO("dataRegistro"),
	DATA_ORA_REGISTRO("dataoraRegistro"),
	NUMERO_REGISTRO("numeroRegistro"),
	LINK_DOCUMENTO("linkDocumento"),
	LINK_SITO_WEB("linkSitoWeb"), 
	SOTTOCODICE_SOCIETA("sottoCodiceSocieta"), 
	NUM_TOT_DOCUMENTI("numTotDocumenti"), 
	NUM_PEC_ESITO_POS("numPecEsitoPos"), 
	NUM_PEC_ESITO_NEG("numPecEsitoNeg"),
	RAG_SOC_DESTINATARIO("ragioneSocialeDestinatario"),
	INDIRIZZI_PER_SEGNALAZIONI("indirizziPerSegnalazioni"),
	ORDINE_ACQUISTO("ordineAcquisto"),
	NAZIONE("NAZIONE")
	;
	
	private String placeholder;

	private TemplateVarsEnum(String placeholder) {
		this.placeholder = placeholder;
	}
	
	public String getPlaceholder() {
		return placeholder;
	}
}
