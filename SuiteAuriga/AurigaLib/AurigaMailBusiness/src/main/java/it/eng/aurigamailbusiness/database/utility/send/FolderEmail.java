/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum FolderEmail {

	STANDARD_FOLDER_INVIO("standard.invio"),
	STANDARD_FOLDER_INVIO_PEC("standard.invio.pec"),
	STANDARD_FOLDER_INVIO_RISPOSTE("standard.invio.risposte"),
	STANDARD_FOLDER_INVIO_INOLTRO("standard.invio.inoltri"),
	STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE("standard.invio.pec.da_controllare"),
	STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA("standard.invio.pec.notifiche_interoperabilita"),
	STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_CONFERMA("standard.invio.pec.notifiche_interoperabilita.conferme"),
	STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_ECCEZIONE("standard.invio.pec.notifiche_interoperabilita.eccezioni"),
	STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_AGGIORNAMENTO("standard.invio.pec.notifiche_interoperabilita.aggiornamenti"),
	STANDARD_FOLDER_INVIO_NOTIFICHE_INTEROPERABILITA_ANNULLAMENTO("standard.invio.pec.notifiche_interoperabilita.annullamenti"),
	STANDARD_FOLDER_INVIO_PEC_NON_INTEROPERABILI("standard.invio.pec.pec_non_interoperabili"),
	STANDARD_FOLDER_INVIO_PEC_INTEROPERABILI("standard.invio.pec.interoperabili"),
	STANDARD_FOLDER_INVIO_PEO("standard.invio.peo"),
	STANDARD_BOZZE("standard.bozze"),
	STANDARD_FOLDER_ARCHIVIATA_INVIO("standard.archiviata.invio"),
	STANDARD_FOLDER_USCITA("standard.uscita"),
	STANDARD_FOLDER_PEC("standard.arrivo.pec_non_interoperabili"),
	STANDARD_FOLDER_PEO("standard.arrivo.peo"),
	STANDARD_FOLDER_PEO_SPAM("standard.arrivo.peo.spam"),
	STANDARD_FOLDER_MAIL_AUTOMATICHE("standard.arrivo.mail_automatiche"),
	STANDARD_FOLDER_ARCHIVIATA_RICEVUTE("standard.archiviata.ricevute_notifiche"),
	STANDARD_FOLDER_ARCHIVIATA_ARRIVO("standard.archiviata.arrivo"),
	STANDARD_FOLDER_ARRIVO_NOTIF_NON_ASSOCIATE("standard.arrivo.ricevute_notifiche_non_associate"),
	STANDARD_FOLDER_ARRIVO_INTEROP_CONFORMI("standard.arrivo.interoperabili.conformi"),
	STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_TRATT("standard.arrivo.interoperabili.non_conformi.trattabili"),
	STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_NON_TRATT("standard.arrivo.interoperabili.non_conformi.non_trattabili"),
	STANDARD_FOLDER_INVIO_PEO_DA_CONTROLLARE("standard.invio.peo.da_controllare");
	
	private String value;
	
	private FolderEmail(String value) {
		this.value = value;
	}
	
	public static FolderEmail valueOfValue(String name){
		for(FolderEmail stato:FolderEmail.values()){
			if(stato.value.equals(name)){
				return stato;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
	
}
