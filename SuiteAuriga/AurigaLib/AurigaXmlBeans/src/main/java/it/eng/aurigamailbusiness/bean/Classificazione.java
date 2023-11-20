/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Classificazione {
	
	STANDARD_FOLDER_PEC("standard.arrivo.pec_non_interoperabili"),
	STANDARD_FOLDER_PEO("standard.arrivo.peo"),
	STANDARD_FOLDER_PEO_SPAM("standard.arrivo.peo.spam"),
	STANDARD_FOLDER_MAIL_AUTOMATICHE("standard.arrivo.mail_automatiche"),
	STANDARD_FOLDER_ARCHIVIATA_RICEVUTE("standard.archiviata.ricevute_notifiche"),
	STANDARD_FOLDER_ARRIVO_NOTIF_NON_ASSOCIATE("standard.arrivo.ricevute_notifiche_non_associate"),
	STANDARD_FOLDER_ARRIVO_INTEROP_CONFORMI("standard.arrivo.interoperabili.conformi"),
	STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_TRATT("standard.arrivo.interoperabili.non_conformi.trattabili"),
	STANDARD_FOLDER_ARRIVO_INTEROP_NON_CONF_NON_TRATT("standard.arrivo.interoperabili.non_conformi.non_trattabili"),
	STANDARD_FOLDER_INVIO_PEC_DA_CONTROLLARE("standard.invio.pec.da_controllare"),
	STANDARD_FOLDER_ARCHIVIATA_INVIO("standard.archiviata.invio"),
	STANDARD_FOLDER_ARCHIVIATA_ARRIVO_PROTOCOLLATE("standard.archiviata.arrivo.protocollate"),
	STANDARD_FOLDER_ARCHIVIATA_ARRIVO_ALTRO("standard.archiviata.arrivo.altro"),
	STANDARD_FOLDER_ARCHIVIATA_ARRIVO_RESPINTE("standard.archiviata.arrivo.respinte");
	
	private String value;
	
	private Classificazione(String value) {
		this.value = value;
	}
	
	public static Classificazione valueOfValue(String name){
		for(Classificazione stato:Classificazione.values()){
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
