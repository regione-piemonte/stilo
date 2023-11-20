/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;

import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author DANCRIST
 *
 */

public class VisualizzaDatiStatisticheEmailStoricizzateList extends CustomList {

	public VisualizzaDatiStatisticheEmailStoricizzateList(String nomeEntita, final Record pRecord) {		
		super(nomeEntita, false);

		getRecordClickHandler().removeHandler();

		setWidth100();
		setBorder("0px");
		setHeight(1);
		setShowAllRecords(true);
		setBodyOverflow(Overflow.VISIBLE);
		setOverflow(Overflow.VISIBLE);
		setLeaveScrollbarGap(false);


		// -- 3: Codice della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 4: Nome della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 10: Categoria di registrazione : PG = Protocollo Generale, PP = Protocollo Particolare, R = Repertorio, A = Altro tipo di numerazione 
		// (valorizzato se si raggruppa per CategoriaRegistrazione)
		// -- 11: Sigla registro di registrazione (valorizzato se si raggruppa
		// per SiglaRegistro)
		// -- 12: Supporto originale : digitale, analogico, misto (valorizzato
		// se si raggruppa per SupportoOriginale)
		// -- 16: Periodo (valorizzato se si raggruppa per Periodo): è sempre un
		// numero
		// -- 17: N.ro di documenti del gruppo
		// -- 18: Percentuale che corrisponde al conteggio di col 17. In
		// notazione italiana con la , come separatore dei decimali
		// -- 19: Percentuale arrotondata in modo tale che la somma delle varie
		// percentuali sia 100. In notazione italiana con la , come separatore
		// dei decimali
		
		ListGridField codiceUOField = new ListGridField("codiceUO", "Cod. U.O.");
		
		ListGridField nomeUOField = new ListGridField("nomeUO", "Nome U.O.");
		
		ListGridField archivioField = new ListGridField("archivio", "Archivio");
		
		ListGridField casellaField = new ListGridField("casella", "Casella");
		
		ListGridField statoLavorazioneField = new ListGridField("statoLavorazione", "Stato lavorazione");
		
		ListGridField periodoField = new ListGridField("periodo", "Periodo");
		
		ListGridField nrMailField = new ListGridField("nrMail", "N.ro di mail del gruppo");
		nrMailField.setAlign(Alignment.RIGHT);
		nrMailField.setType(ListGridFieldType.INTEGER);

		ListGridField percField = new ListGridField("perc", "% di email (esatta)");
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
		
		ListGridField percArrotondataField = new ListGridField("percArrotondata", "% di email (arrotondata)");
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
		
		setFields ( 
				codiceUOField,nomeUOField,archivioField,casellaField,statoLavorazioneField,periodoField,nrMailField,
				percField,percArrotondataField
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