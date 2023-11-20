/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class LogOperazioniList extends CustomList {

	private ListGridField idLogOperazioneField;
	private ListGridField nroProgrOperazioneField;
	private ListGridField tsOperazioneField;
	private ListGridField tipoOperazioneField;
	private ListGridField descUtenteOperazioneField;
	private ListGridField descUtenteDelegatoOperazioneField;
	private ListGridField dettagliOperazioneField;
	private ListGridField esitoOperazioneField;

	protected ControlListGridField viewDettagliOperazioneButtonField;

	public LogOperazioniList(String nomeEntita) {

		super(nomeEntita);

		// Visibili
		nroProgrOperazioneField = new ListGridField("nroProgrOperazione",I18NUtil.getMessages().log_operazioni_list_tsOperazioneField_title());
		nroProgrOperazioneField.setDisplayField("tsOperazione");
		nroProgrOperazioneField.setSortByDisplayField(false);
		
		tipoOperazioneField = new ListGridField("tipoOperazione",I18NUtil.getMessages().log_operazioni_list_tipoOperazioneField_title());
		
		descUtenteOperazioneField = new ListGridField("descUtenteOperazione",I18NUtil.getMessages().log_operazioni_list_descUtenteOperazioneField_title());
		
		descUtenteDelegatoOperazioneField = new ListGridField("descUtenteDelegatoOperazione",I18NUtil.getMessages().log_operazioni_list_descUtenteDelegatoOperazioneField_title());
		
		dettagliOperazioneField = new ListGridField("dettagliOperazione",I18NUtil.getMessages().log_operazioni_list_dettagliOperazioneField_title());
		
		esitoOperazioneField = new ListGridField("esitoOperazione",I18NUtil.getMessages().log_operazioni_list_esitoOperazioneField_title());

		// Hidden
		idLogOperazioneField = new ListGridField("idLogOperazione");
		idLogOperazioneField.setHidden(true);
		idLogOperazioneField.setCanHide(false);
		
		tsOperazioneField = new ListGridField("tsOperazione");
		tsOperazioneField.setHidden(true);
		tsOperazioneField.setCanHide(false);


		setFields(new ListGridField[] { 
				// Visibili
				nroProgrOperazioneField, 
				tipoOperazioneField, 
				descUtenteOperazioneField, 
				descUtenteDelegatoOperazioneField,
				dettagliOperazioneField, 
				esitoOperazioneField,
				// Hidden
				tsOperazioneField, 
				idLogOperazioneField 
				});
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 100;
	}

	/********************************
	 * NUOVA GESTIONE CONTROLLI BOTTONI
	 ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		return LogOperazioniLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return LogOperazioniLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		return LogOperazioniLayout.isRecordAbilToMod();
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		return LogOperazioniLayout.isRecordAbilToDel();
	}

	/********************************
	 * FINE NUOVA GESTIONE CONTROLLI BOTTONI
	 ********************************/

	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsFields = super.getButtonsFields();
		if (showViewDettagliOperazioneButtonField()) {
			if (viewDettagliOperazioneButtonField == null) {
				viewDettagliOperazioneButtonField = new ControlListGridField("viewOperazioniButton");
				viewDettagliOperazioneButtonField.setAttribute("custom", true);
				viewDettagliOperazioneButtonField.setShowHover(true);
				viewDettagliOperazioneButtonField.setCanReorder(false);
				viewDettagliOperazioneButtonField.setCellFormatter(new CellFormatter() {
					@Override
					public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
						if (isRecordAbilToViewDettagliOperazione(record)) {
							return buildImgButtonHtml("buttons/view.png");
						}
						return null;
					}
				});
				viewDettagliOperazioneButtonField.setHoverCustomizer(new HoverCustomizer() {
					@Override
					public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
						if (isRecordAbilToViewDettagliOperazione(record)) {
							return I18NUtil.getMessages().log_operazioni_list_viewOperazioniButton_prompt();
						}
						return null;
					}
				});
				viewDettagliOperazioneButtonField.addRecordClickHandler(new RecordClickHandler() {
					@Override
					public void onRecordClick(RecordClickEvent event) {
						event.cancel();
						final ListGridRecord record = getRecord(event.getRecordNum());
						if (isRecordAbilToViewDettagliOperazione(record)) {
							manageViewDettagliOperazioneButtonClick(record);
						}
					}
				});
			}
			buttonsFields.add(viewDettagliOperazioneButtonField);
		}
		return buttonsFields;
	}

	protected boolean showViewDettagliOperazioneButtonField() {
		return true;
	}

	protected void manageViewDettagliOperazioneButtonClick(ListGridRecord record) {
		LogOperazioniDettaglioPopup logOperazioniDettaglioPopup = new LogOperazioniDettaglioPopup(
				record.getAttribute("dettagliOperazione"));
		logOperazioniDettaglioPopup.show();
	}

	protected boolean isRecordAbilToViewDettagliOperazione(ListGridRecord record) {
		return record.getAttributeAsString("dettagliOperazione") != null
				&& !"".equals(record.getAttributeAsString("dettagliOperazione"));
	}
}
