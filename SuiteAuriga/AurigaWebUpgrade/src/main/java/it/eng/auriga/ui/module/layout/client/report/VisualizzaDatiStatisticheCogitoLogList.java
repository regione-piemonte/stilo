/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class VisualizzaDatiStatisticheCogitoLogList extends CustomList {

	private ListGridField nomeUDField;
	private ListGridField codiceUOField; 
	private ListGridField nomeUOField;
	private ListGridField usernameUtenteField;
	private ListGridField denominazioneUtenteField;
	private ListGridField nriLivelliClassificazioneField;
	private ListGridField denominazioneClassificazioneField;
	private ListGridField periodoField;
	private ListGridField nrDocumentiField;
	private ListGridField percField;
	private ListGridField percArrotondataField;
	
	private String tipoReport;
	
	public VisualizzaDatiStatisticheCogitoLogList(String nomeEntita, final Record pRecord, final String pTipoReport) {

		super(nomeEntita, false);

		getRecordClickHandler().removeHandler();

		setWidth100();
		setBorder("0px");
		setHeight(1);
		setShowAllRecords(true);
		setBodyOverflow(Overflow.VISIBLE);
		setOverflow(Overflow.VISIBLE);
		setLeaveScrollbarGap(false);
		
		this.tipoReport=pTipoReport; 

		// -- 1:  Oggetto Registrazione
		// -- 2:  Codice della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 3:  Nome della UO del raggruppamento (valorizzato se si raggruppa per UO)
		// -- 4:  Username dell'utente del raggruppamento (valorizzato se si raggruppa per User)
		// -- 5:  Cognome e Nome dell'utente del raggruppamento (valorizzato se si raggruppa per User)
		// -- 6:  Numeri livelli classificazione
		// -- 7:  Descrizione classificazione
		// -- 8:  Periodo (valorizzato se si raggruppa per Periodo): è sempre un numero 
		// -- 9:  N.ro di documenti del gruppo
		// --10:  Percentuale che corrisponde al conteggio. In notazione italiana con la , come separatore dei decimali                                                                                                          
		// --11:  Percentuale arrotondata in modo tale che la somma delle varie percentuali sia 100. In notazione italiana con la , come separatore dei decimali
		nomeUDField                       = new ListGridField("nomeUD",                       I18NUtil.getMessages().datiStatisticheCogitoLog_list_nomeUDField_title());                       nomeUDField.setAlign(Alignment.RIGHT); nomeUDField.setAttribute("custom", true);
		codiceUOField                     = new ListGridField("codiceUO",                     I18NUtil.getMessages().datiStatisticheCogitoLog_list_codiceUOField_title());                     codiceUOField.setAlign(Alignment.RIGHT); codiceUOField.setAttribute("custom", true); 
		nomeUOField                       = new ListGridField("nomeUO",                       I18NUtil.getMessages().datiStatisticheCogitoLog_list_nomeUOField_title());                       nomeUOField.setAlign(Alignment.LEFT); nomeUOField.setAttribute("custom", true);
		usernameUtenteField               = new ListGridField("usernameUtente",               I18NUtil.getMessages().datiStatisticheCogitoLog_list_usernameUtenteField_title());               usernameUtenteField.setAlign(Alignment.RIGHT); usernameUtenteField.setAttribute("custom", true);
		denominazioneUtenteField          = new ListGridField("denominazioneUtente",          I18NUtil.getMessages().datiStatisticheCogitoLog_list_denominazioneUtenteField_title());          denominazioneUtenteField.setAlign(Alignment.LEFT); denominazioneUtenteField.setAttribute("custom", true);
		nriLivelliClassificazioneField    = new ListGridField("nriLivelliClassificazione",    I18NUtil.getMessages().datiStatisticheCogitoLog_list_nriLivelliClassificazioneField_title());    nriLivelliClassificazioneField.setAlign(Alignment.RIGHT); nriLivelliClassificazioneField.setAttribute("custom", true);
		denominazioneClassificazioneField = new ListGridField("denominazioneClassificazione", I18NUtil.getMessages().datiStatisticheCogitoLog_list_denominazioneClassificazioneField_title()); denominazioneClassificazioneField.setAlign(Alignment.LEFT); denominazioneClassificazioneField.setAttribute("custom", true);
		periodoField                      = new ListGridField("periodo",                      I18NUtil.getMessages().datiStatisticheCogitoLog_list_periodoField_title());                      periodoField.setAlign(Alignment.RIGHT); periodoField.setAttribute("custom", true);
		nrDocumentiField                  = new ListGridField("nrDocumenti",                  I18NUtil.getMessages().datiStatisticheCogitoLog_list_nrDocumentiField_title());                  nrDocumentiField.setAlign(Alignment.CENTER); nrDocumentiField.setType(ListGridFieldType.INTEGER); nrDocumentiField.setAttribute("custom", true);
		
		percField = new ListGridField("perc", I18NUtil.getMessages().datiStatisticheCogitoLog_list_percField_title());
		percField.setAttribute("custom", true);
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
		
		percArrotondataField = new ListGridField("percArrotondata", I18NUtil.getMessages().datiStatisticheCogitoLog_list_percArrotondataField_title()); 
		percArrotondataField.setAttribute("custom", true);
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

		setFields( nomeUDField,
				   codiceUOField, 
			       nomeUOField, 
			       usernameUtenteField, 
			       denominazioneUtenteField,
			       nriLivelliClassificazioneField,
			       denominazioneClassificazioneField,
			       periodoField, 
			       nrDocumentiField,
			       percField, 
			       percArrotondataField
		         );

	}

	@Override
	public void setFields(ListGridField... fields) {
		for (final ListGridField field : fields) {
			String fieldName = field.getName();
			
			if (tipoReport != null && tipoReport.equals("esito") ) {
				if ("nomeUD".equals(fieldName)) {
					field.setHidden(true);
					field.setCanHide(false);
				}
			}
		}
		super.setFields(fields);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					refreshFields();
				} catch (Exception e) {
				}
				markForRedraw();
			}
		});
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