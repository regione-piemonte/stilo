/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlEnum;

/*
 * E' stata implementata in questo modo per far funzionare la generazione automatica del client.
 * si può ordinare solo per le colonne da 1 a 10 + 21, 22, 23, 36, 40, 87)
 */
@XmlEnum(String.class)
public enum EmailMetadata {
	
	/*@XmlEnumValue("1")*/ ID("1", "PK interna"),
	/*@XmlEnumValue("2")*/ MESSAGE_ID("2", "MessageId della busta S/MIME"),
	/*@XmlEnumValue("3")*/ IN_OUT("3", "Flag In/Out: I = e-mail ricevuta; O = e-mail inviata"),
	/*@XmlEnumValue("4")*/ ACCOUNT_ID("4", "Id.(MAILBOX_ACCOUNT.ID_ACCOUNT) della casella che ha ricevuto (se colonna 3 = I) o inviato (se colonna 3 = O) la mail"),
	/*@XmlEnumValue("5")*/ ACCOUNT_INDIRIZZO("5", "Indirizzo della casella che ha ricevuto (se colonna 3 = I) o inviato (se colonna 3 = O) la mail"),
	/*@XmlEnumValue("6")*/ CATEGORIA("6", "Categoria dell'e-mail"),
	/*@XmlEnumValue("7")*/ DATA_ORA_SCARICO("7", "Data e ora di scarico"),
	/*@XmlEnumValue("8")*/ DATA_ORA_INVIO("8", "Data e ora di invio dal client, valorizzata anche per le mail ricevute"),
	/*@XmlEnumValue("9")*/ DATA_ORA_INVIO_ENTRATA_DA_PEC("9", "Data e ora di invio attestata dal provider PEC (solo per le mail ricevute provenienti da PEC)"),
	/*@XmlEnumValue("10")*/ DIMENSIONE("10", "Dimensione dell'e-mail in bytes"),
	/*@XmlEnumValue("11")*/ EML_URI("11", "URI a cui reperire l'eml della mail (con notazione di StorageUtil)"),
	/*@XmlEnumValue("12")*/ E_SPAM("12", "Indica se la mail è stata marcata (=1) come spam o no (=0)"),
	/*@XmlEnumValue("13")*/ STATO_SE_SPAM("13", "Stato della mail se marcata come spam; valori possibili: B = bloccata, RS = richiesto sblocco, S = sbloccata, R = rigettata a seguoto dello sblocco"),
	/*@XmlEnumValue("14")*/ STATO_SE_RICEVUTA_PEC("14", "Indica se la mail, qualora sia una ricevuta PEC, è: C = Completa, S = Sintetica, B = Breve"),
	/*@XmlEnumValue("15")*/ STATO_CONSOLIDAMENTO_INVIATA("15", "Stato di consolidamento della mail (se inviata); valori possibili: accettata; non accettata; avvertimenti in consegna, errori in consegna, consegnata, consegnata parzialmente"),
	/*@XmlEnumValue("16")*/ ALLEGATI_NRO("16", "N.ro complessivo di allegati della mail"),
	/*@XmlEnumValue("17")*/ ALLEGATI_FIRMATI_NRO("17", "N.ro di allegati firmati digitalmente"),
	/*@XmlEnumValue("18")*/ ALLEGATI_NON_FIRMATI_NRO("18", "N.ro di allegati NON firmati digitalmente"),
	/*@XmlEnumValue("19")*/ E_FIRMATA("19", "Indica se la mail è firmata (=1) o meno (=0)"),
	/*@XmlEnumValue("20")*/ E_AUTOASSOCIAZIONE_FALLITA("20", "Se 1 significa che qualora l'e-mail ricada in una delle categorie ricevute PEC, notifiche interoperabili e delivery status notification il sistema non è stato in grado di associarla in automatico all'e-mail inviata cui si riferisce"),
	/*@XmlEnumValue("21")*/ MITTENTE_ENTRATA("21", "Indirizzo mittente (da cui provine la mail; valorizzato solo per e-mail in entrata)"),
	/*@XmlEnumValue("22")*/ OGGETTO("22", "Oggetto della mail"),
	/*@XmlEnumValue("23")*/ CORPO("23", "Corpo della mail"),
	/*@XmlEnumValue("24")*/ DESTINATARI("24", "Indirizzi destinatari della mail (tutti, sia primari che in conoscenza), separati da , se più di uno"),
	/*@XmlEnumValue("25")*/ DESTINATARI_A("25", "Indirizzi destinatari primari, separati da , se più di uno"),
	/*@XmlEnumValue("26")*/ DESTINATARI_CC("26", "Indirizzi destinatari in cc, separati da , se più di uno"),
	/*@XmlEnumValue("27")*/ DESTINATARI_CCN("27", "Indirizzi destinatari in ccn, separati da , se più di uno"),
	/*@XmlEnumValue("28")*/ AVVERTIMENTI_ENTRATA("28", "Avvertimenti sulla mail (solo se in entrata) rilevati e memorizzati dal componente che scarica le e-mail dalla casella"),
	/*@XmlEnumValue("29")*/ DETENTORE_LOCK("29", "Indica chi ha il lock sulla mail (Cognome e Nome + username)"),
	/*@XmlEnumValue("30")*/ E_RICHIESTA_DI_CONFERMA("30", "Indica se e-mail con richiesta di conferma (sia se mail in entrata, sia se e-mail inviata; nel caso di mail interoperabile è la richiesta di conferma della Segnatura.xml)"),
	/*@XmlEnumValue("31")*/ ASSEGNAZIONE_DATA_ORA("31", "Data e ora di ultima assegnazione dell'e-mail"),
	/*@XmlEnumValue("32")*/ ASSEGNATARIO("32", "Attuale assegnatario dell'e-mail"),
	/*@XmlEnumValue("33")*/ E_RISPOSTA("33", "Indica se e-mail per cui è stata inviata risposta (=1) o meno (=0)"),
	/*@XmlEnumValue("34")*/ E_INOLTRATA("34", "Indica se e-mail inoltrata (=1) o meno (=0)"),
	/*@XmlEnumValue("35")*/ STATO_PROTOCOLLAZIONE("35", "Stato di protocollazione della mail (valorizzato solo per mail ricevute). Valori possibili: C = Completa, ovvero protocollati tutti i file, P = Parziale, ovvero protocollati solo alcuni file, valore vuoto = non protocollata"),
	/*@XmlEnumValue("36")*/ VERIFICA_CONTRASSEGNO("36", "Contrassegno (decodificato)"),
	/*@XmlEnumValue("37")*/ E_NOTIFICHE_ECCEZIONE_INVIATA("37", "Indica se sono pervenute notifiche di eccezione (=1) o meno (=0) a fronte della mail (inviata)"),
	/*@XmlEnumValue("38")*/ E_NOTIFICHE_CONFERMA_INVIATA("38", "Indica se sono pervenute notifiche di conferma (=1) o meno (=0) a fronte della mail (inviata)"),
	/*@XmlEnumValue("39")*/ E_NOTIFICHE_AGGIORNAMENTO_INVIATA("39", "Indica se sono pervenute notifiche di aggiornamento(=1) o meno (=0) a fronte della mail (inviata)"),
	/*@XmlEnumValue("40")*/ LIVELLO_PRIORITA("40", "Livello di priorità della mail: alta, normale, bassa"),
	/*@XmlEnumValue("41")*/ DESTINATARI_("41", "Destinatari della mail come indicati in fase di invio, eventualmente anche come liste di distribuzione e nominativi della rubrica (tutti, sia primari che in conoscenza), separati da , se più di uno"),
	/*@XmlEnumValue("42")*/ DESTINATARI_INDICATI_A("42", "Indirizzi destinatari primari come indicati in fase di invio, separati da , se più di uno"),
	/*@XmlEnumValue("43")*/ DESTINATARI_INDICATI_CC("43", "Indirizzi destinatari in cc come indicati in fase di invio, separati da , se più di uno"),
	/*@XmlEnumValue("44")*/ DESTINATARI_INDICATI_CCN("44", "Indirizzi destinatari in ccn come indicati in fase di invio, separati da , se più di uno"),
	/*@XmlEnumValue("45")*/ FOLDER("45", "Nome del folder in cui è collocata la mail (valorizzato solo se il folder è uno solo e diverso da quello con la classifica eventualmente indicata nel filtro ClassificaFolder)"),
	/*@XmlEnumValue("46")*/ PROTO_REG_ESTREMI("46", "Estremi dei protocolli/registrazioni - effettuati dal soggetto che ha ricevuto la mail, se e-mail in entrata, o l'ha inviata, se e-mail in uscita - associati alla mail (sia per e-mail in entrata che in uscita). Se più di una sono separate da ;"),
	/*@XmlEnumValue("47")*/ UNITA_DOCUMENTARIE("47", "Lista di ID_UD delle unità documentarie cui la mail ha dato luogo (se in entrata) o i cui dati e/o file sono stati trasmessi con la mail (se in uscita). Se più di una sono separate da ;"),
	/*@XmlEnumValue("48")*/ PROTOC_REG_ESTREMI_INTEROP_INVIATA("48", "Estremi del/i protocolli/registrazioni esterni associati alla mail (solo per e-mail interoperabili inviate)"),
	/*@XmlEnumValue("49")*/ ID_PARENT("49", "Id. email dell'email da cui quella corrente deriva (se è una ricevuta PEC o notifica interoperabile o una delivery status notification o una mail di conferma ricezione)"),
	/*@XmlEnumValue("50")*/ STATO("50", "Stato dell'e-mail per l'utente di lavoro/collegato"),
	/*@XmlEnumValue("51")*/ E_COMMENTATA("51", "Se 1 indica che sulla mail sono presenti commenti destinati all'utente di lavoro/collegato"),
	/*@XmlEnumValue("52")*/ E_ASSEGNATA_PERSONALMENTE_UTENTE("52", "Indica se e-mail assegnata personalmente all'utente (1) o no (=0)"),
	/*@XmlEnumValue("53")*/ E_ASSEGNATA_UTENTE_UO("53", "Indica se e-mail assegnata all'utente o ad una sua UO (=1) o meno (=0)"),
	/*@XmlEnumValue("54")*/ E_NOTIFICHE_ANNULLAMENTO_REG_INVIATA("54", "Indica se sono pervenute notifiche di annullamento di registrazione (=1) o meno (=0) a fronte della mail (inviata)"),
	/*@XmlEnumValue("55")*/ ALLEGATI_PRESENZA("55", "flag che indica presenza di allegati firmati (=AF), presenza di alleegati tutti non firmati (=A) o assenza di allegati (se NULL)"),
	/*@XmlEnumValue("56")*/ ALLEGATI_PRESENZA_NRO("56", "Serve come alt della colonna precedente per indicare il n.ro totale di allegati, quanti firmati e quanti no"),
	/*@XmlEnumValue("57")*/ PROTOC_REG_ESTREMI_MITT_INTEROP_RICEVUTA("57", "Estremi del protocollo/registrazione del mittente della mail ricevuta (se interoperabile)"),
	/*@XmlEnumValue("58")*/ PROTOC_REG_OGGETTO_MITT_INTEROP_RICEVUTA("58", "Oggetto del protocollo/registrazione del mittente della mail ricevuta (se interoperabile)"),
	/*@XmlEnumValue("59")*/ TIPO("59", "Tipo dell'email"),
	/*@XmlEnumValue("60")*/ SOTTOTIPO("60", "Sotto-tipo dell'email"),
	/*@XmlEnumValue("61")*/ E_CERTIFICATA("61", "Flg mail certificata. La certificazione si riferisce al mittente"),
	/*@XmlEnumValue("62")*/ E_INTEROPERABILE("62", "Flg mail di interoperabilità tra protocolli"),
	/*@XmlEnumValue("63")*/ ICONA("63", "Icona da utilizzare (della macro+microcategoria) per la mail"),
	/*@XmlEnumValue("64")*/ STATO_LAVORAZIONE("64", "Stato di lavorazione della mail"),
	/*@XmlEnumValue("65")*/ VERIFICA_GRADO("65", "(valori 2/1/0) Grado di urgenza della verifica da effettuare se presente contrassegno di verifica urgente"),
	/*@XmlEnumValue("66")*/ VERIFICA_CONTRASSEGNO_ID("66", "Id. del contrassegno di verifica urgente da effettuare (la decodifica è in colonna 36)"),
	/*@XmlEnumValue("67")*/ ASSEGNAZIONE_MESSAGGIO("67", "Messaggio a corredo ultima assegnazione della mail"),
	/*@XmlEnumValue("68")*/ AZIONE("68", "Azione da fare sulla mail (decodifica)"),
	/*@XmlEnumValue("69")*/ AZIONE_CODICE("69", "Cod. azione da fare sulla mail"),
	/*@XmlEnumValue("70")*/ AZIONE_DETTAGLIO("70", "Dettaglio azione da fare sulla mail"),
	/*@XmlEnumValue("71")*/ PROGRESSIVO("71", "Progressivo della mail da mostrare nella GUI(ID univoco)"),
	/*@XmlEnumValue("72")*/ PROGRESSIVO_DOWNLOAD_STAMPA("72", "Progressivo della mail da usare per download/stampa"),
	/*@XmlEnumValue("73")*/ PROGRESSIVO_ORDINAMENTO("73", "Progressivo della mail da usare per ordinare"),
	/*@XmlEnumValue("74")*/ DATA_ORA_SALVATAGGIO("74", "Data e ora di salvataggio della mail in tabella T_EMAIL_MGO"),
	/*@XmlEnumValue("75")*/ NRO_GIORNI_LAVORAZIONE_APERTA("75", "N.ro di giorni (arrotondato agli interi) da cui lo stato di lavorazione è \"lavorazione_aperta\""),
	/*@XmlEnumValue("76")*/ STATO_INVIO_CODICE("76", "Codice stato di invio. Valori possibili: OK (inviata), KO (invio fallito), IP (invio in corso)"),
	/*@XmlEnumValue("77")*/ STATO_ACCETTAZIONE_CODICE("77", "Codice stato di accettazione. Valori possibili: OK (accettata), KO (non accettata), W (presunta mancata accettazione), IP (accettazione in corso) ND (non ancora valorizzato)"),
	/*@XmlEnumValue("78")*/ STATO_CONSEGNA_CODICE("78", "Codice stato di consegna. Valori possibili: OK (consegnata), KO (consegna fallita), W (presunta mancata consegna), IP (consegna in corso) ND (non ancora valorizzato)"),
	/*@XmlEnumValue("79")*/ STATO_INVIO("79", "Stato di invio (decodificato)"),
	/*@XmlEnumValue("80")*/ STATO_ACCETTAZIONE("80", "Stato di accettazione (decodificato)"),
	/*@XmlEnumValue("81")*/ STATO_CONSEGNA("81", "Stato di consegna (decodificato)"),
	/*@XmlEnumValue("82")*/ DATA_ORA_LOCK("82", "Data e ora da cui c'è il lock sulla mail"),
	/*@XmlEnumValue("83")*/ DATA_ORA_STATO_LAVORAZIONE("83", "Data e ora di ultimo aggiornamento dello stato di lavorazione"),
	/*@XmlEnumValue("84")*/ ANNOTAZIONI("84", "Annotazioni associate alla mail (in ordine dalla 1 in su)"),
	/*@XmlEnumValue("85")*/ E_CONFERMA_LETTURA("85", "Flag di conferma di lettura"),
	/*@XmlEnumValue("86")*/ TAG_APPOSTI("86", "Tag apposto/i sulla mail (in ordine dalla 1 in su)"),
	/*@XmlEnumValue("87")*/ DATA_ORA_CHIUSURA("87", "Data e ora di chiusura/archiviazione della mail"),
	/*@XmlEnumValue("88")*/ OPERATORE_CHIUSURA("88", "Nominativo dell'operatore che ha chiuso/archiviato la mail")
    ;
	
//	@ApiModelProperty(allowableValues="range[1, 88]", readOnly=true) 
	private final String index;
	private final String description;
	
	private EmailMetadata(String index, String description) {
		this.index = index;
		this.description = description;
	}

	public String getIndex() {
		return index;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	/* non modificare! */
	public final String toString() {
		return getIndex();
	}
	
	public static EmailMetadata valueOfByIndex(String index) {
		for (EmailMetadata value: values()) {
		   if ( value.getIndex().equalsIgnoreCase(index) ) {
		      return value;
		   }
		}
		return null;
    }
	
}//EmailMetadata
