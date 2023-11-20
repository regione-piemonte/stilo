/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class VisualizzaDatiStatisticheDocumentiList extends CustomList {

	public VisualizzaDatiStatisticheDocumentiList(String nomeEntita, final Record pRecord) {

		super(nomeEntita, false);

		getRecordClickHandler().removeHandler();

		setWidth100();
		setBorder("0px");
		setHeight(1);
		setShowAllRecords(true);
		setBodyOverflow(Overflow.VISIBLE);
		setOverflow(Overflow.VISIBLE);
		setLeaveScrollbarGap(false);

		// -- 1: Id. del soggetto produttore/AOO cui si riferiscono dati e
		// percentuali del record (valorizzato se si raggruppa per SpAOO)
		// -- 2: Nome del soggetto produttore/AOO cui si riferiscono dati e
		// percentuali del record (valorizzato se si raggruppa per SpAOO)
		// -- 3: Codice della UO del raggruppamento (valorizzato se si raggruppa
		// per UO)
		// -- 4: Nome della UO del raggruppamento (valorizzato se si raggruppa
		// per UO)
		// -- 5: Cod. dell'applicazione del raggruppamento (valorizzato se si
		// raggruppa per ApplicazioneReg)
		// -- 6: Nome dell'applicazione del raggruppamento (valorizzato se si
		// raggruppa per ApplicazioneReg)
		// -- 7: Username dell'utente del raggruppamento (valorizzato se si
		// raggruppa per User)
		// -- 8: Cognome e Nome dell'utente del raggruppamento (valorizzato se
		// si raggruppa per User)
		// -- 9: Tipo di registrazione: E (Entrata), U (Uscita) e I (Interni)
		// (valorizzato se si raggruppa per TipoRegistrazione)
		// -- 10: Categoria di registrazione : PG = Protocollo Generale, PP =
		// Protocollo Particolare, R = Repertorio, A = Altro tipo di numerazione
		// (valorizzato se si raggruppa per CategoriaRegistrazione)
		// -- 11: Sigla registro di registrazione (valorizzato se si raggruppa
		// per SiglaRegistro)
		// -- 12: Supporto originale : digitale, analogico, misto (valorizzato
		// se si raggruppa per SupportoOriginale)
		// -- 13: Indicazione 1/0 della presenza o meno di file sui documenti
		// del gruppo (valorizzato se si raggruppa per PresenzaFile)
		// -- 14: Canale di ricezione/trasmissione (valorizzato se si raggruppa
		// per Canale)
		// -- 15: Livello di riservatezza (valorizzato se si raggruppa per
		// Riservatezza)
		// -- 16: Periodo (valorizzato se si raggruppa per Periodo): è sempre un
		// numero
		// -- 17: N.ro di documenti del gruppo
		// -- 18: Percentuale che corrisponde al conteggio di col 17. In
		// notazione italiana con la , come separatore dei decimali
		// -- 19: Percentuale arrotondata in modo tale che la somma delle varie
		// percentuali sia 100. In notazione italiana con la , come separatore
		// dei decimali
		// -- 20: Reg. valide/annullate: ha i valori VALIDE, ANNULLATE o vuoto
				
		ListGridField idEnteAooField = new ListGridField("idEnteAoo", I18NUtil.getMessages().datiStatisticheDocumenti_list_idEnteAooField_title());
		idEnteAooField.setHidden(true);
		idEnteAooField.setCanHide(false);
		idEnteAooField.setCanGroupBy(false);
		
		ListGridField nomeEnteAooField = new ListGridField("nomeEnteAoo", I18NUtil.getMessages().datiStatisticheDocumenti_list_nomeEnteAooField_title());
		nomeEnteAooField.setHidden(true);
		nomeEnteAooField.setCanHide(false);
		nomeEnteAooField.setCanGroupBy(false);
		
		ListGridField codiceUOField = new ListGridField("codiceUO", I18NUtil.getMessages().datiStatisticheDocumenti_list_codiceUOField_title());
		
		ListGridField nomeUOField = new ListGridField("nomeUO", I18NUtil.getMessages().datiStatisticheDocumenti_list_nomeUOField_title());
		
		ListGridField codiceApplicazioneField = new ListGridField("codiceApplicazione", I18NUtil.getMessages().datiStatisticheDocumenti_list_codiceApplicazioneField_title());
		
		ListGridField nomeApplicazioneField = new ListGridField("nomeApplicazione", I18NUtil.getMessages().datiStatisticheDocumenti_list_nomeApplicazioneField_title());
		
		ListGridField usernameUtenteField = new ListGridField("usernameUtente", I18NUtil.getMessages().datiStatisticheDocumenti_list_usernameUtenteField_title());
		
		ListGridField denominazioneUtenteField = new ListGridField("denominazioneUtente", I18NUtil.getMessages().datiStatisticheDocumenti_list_denominazioneUtenteField_title());
		
		ListGridField tipoRegistrazioneField = new ListGridField("tipoRegistrazione", I18NUtil.getMessages().datiStatisticheDocumenti_list_tipoRegistrazioneField_title());
		
		ListGridField categoriaRegistrazioneField = new ListGridField("categoriaRegistrazione", I18NUtil.getMessages().datiStatisticheDocumenti_list_categoriaRegistrazioneField_title());
		
		ListGridField siglaRegistroField = new ListGridField("siglaRegistro", I18NUtil.getMessages().datiStatisticheDocumenti_list_siglaRegistroField_title());
		
		ListGridField supportoField = new ListGridField("supporto", I18NUtil.getMessages().datiStatisticheDocumenti_list_supportoField_title());
		
		ListGridField presenzaFileField = new ListGridField("presenzaFile", I18NUtil.getMessages().datiStatisticheDocumenti_list_presenzaFileField_title());
		
		ListGridField mezzoTrasmissioneField = new ListGridField("mezzoTrasmissione", I18NUtil.getMessages().datiStatisticheDocumenti_list_mezzoTrasmissioneField_title());
		
		ListGridField livelloRiservatezzaField = new ListGridField("livelloRiservatezza", I18NUtil.getMessages().datiStatisticheDocumenti_list_livelloRiservatezzaField_title());
		
		ListGridField periodoField = new ListGridField("periodo", I18NUtil.getMessages().datiStatisticheDocumenti_list_periodoField_title());
		periodoField.setAlign(Alignment.RIGHT);
		
		ListGridField nrDocumentiField = new ListGridField("nrDocumenti", I18NUtil.getMessages().datiStatisticheDocumenti_list_nrDocumentiField_title());
		nrDocumentiField.setAlign(Alignment.RIGHT);
		nrDocumentiField.setType(ListGridFieldType.INTEGER);
		
		ListGridField percField = new ListGridField("perc", I18NUtil.getMessages().datiStatisticheDocumenti_list_percField_title());
		percField.setAlign(Alignment.CENTER);
		percField.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				String value = record.getAttribute(fieldName);
				if(value != null && !"".equals(value)) {
					String tempValue = value.replace(",", ".");
					BigDecimal valueXOrd = new BigDecimal(tempValue).multiply(new BigDecimal(100));
					return valueXOrd;
				}
				return null;
			}
		});
		
		ListGridField percArrotondataField = new ListGridField("percArrotondata", I18NUtil.getMessages().datiStatisticheDocumenti_list_percArrotondataField_title());
		percArrotondataField.setAlign(Alignment.CENTER);
		percArrotondataField.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				String value = record.getAttribute(fieldName);
				if(value != null && !"".equals(value)) {
					String tempValue = value.replace(",", ".");
					BigDecimal valueXOrd = new BigDecimal(tempValue).multiply(new BigDecimal(100));
					return valueXOrd;
				}
				return null;
			}
		});

		ListGridField regValideAnnullateField = new ListGridField("regValideAnnullate", I18NUtil.getMessages().datiStatisticheDocumenti_list_regValideAnnullateField_title());
		regValideAnnullateField.setAlign(Alignment.CENTER);

		setFields( 
			// Colonne nascoste
			idEnteAooField, 
			nomeEnteAooField,
			// Colonne visualizzate
			codiceUOField, 
			nomeUOField, 
			codiceApplicazioneField,
			nomeApplicazioneField,
			usernameUtenteField, 
			denominazioneUtenteField,
			tipoRegistrazioneField, 
			categoriaRegistrazioneField, 
			siglaRegistroField, 
			supportoField,
			presenzaFileField, 
			mezzoTrasmissioneField, 
			livelloRiservatezzaField, 
			periodoField, 
			nrDocumentiField,
			percField, 
			percArrotondataField,
			regValideAnnullateField
		);

	}

	protected com.smartgwt.client.widgets.Canvas createRecordComponent(
			com.smartgwt.client.widgets.grid.ListGridRecord record, Integer colNum) {
		return null;
	}

	@Override
	public boolean isDisableRecordComponent() {
		return false;
	}

}