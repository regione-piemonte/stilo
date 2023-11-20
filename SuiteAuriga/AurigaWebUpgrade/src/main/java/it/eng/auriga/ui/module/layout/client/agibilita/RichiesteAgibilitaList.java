/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class RichiesteAgibilitaList extends CustomList {

	private ListGridField idField;
	private ListGridField richiestaDelField;
	private ListGridField codFiscaleField;
	private ListGridField cognomeNomeField;
	private ListGridField eMailField;
	private ListGridField telefonoField;
	private ListGridField motivoRichiestaField;
	private ListGridField statoField;
	private ListGridField dataEvasioneField;
	private ListGridField evasaDaField;
	private ListGridField motivoRespingimentoField;
	private ListGridField vecchioLimiteRichiesta;
	private ListGridField nuovoLimiteRichiesta;
	private ControlListGridField accolgoRichiestaField;
	private ControlListGridField rigettoRichiestaField;

	public RichiesteAgibilitaList(String nomeEntita) {
		this(nomeEntita, null);
	}

	@Override
	protected Object formatDateForSorting(ListGridRecord record, String fieldName) {
		String value = record != null ? record.getAttributeAsString(fieldName) : null;
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy HH:mm");
		return value != null && !"".equals(value) ? dateFormat.parse(value) : null;
	}

	@Override
	public Boolean sort() {
		return super.sort();
	}

	public RichiesteAgibilitaList(String nomeEntita, Object object) {
		super(nomeEntita);

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
		setSelectionType(SelectionStyle.NONE);

		List<ListGridField> fieldsToSet = new ArrayList<ListGridField>();

		idField = new ListGridField("idRichiesta");
		idField.setHidden(true);
		idField.setCanHide(false);
		fieldsToSet.add(idField);

		richiestaDelField = new ListGridField("richiestaDel", "Richiesta del");
		richiestaDelField.setType(ListGridFieldType.DATE);
		richiestaDelField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		richiestaDelField.setWrap(false);
		fieldsToSet.add(richiestaDelField);

		codFiscaleField = new ListGridField("codFiscale", "Codice fiscale");
		fieldsToSet.add(codFiscaleField);
		
		vecchioLimiteRichiesta = new ListGridField("vecchioLimiteRich", "Vecchio limite richiesta");
		fieldsToSet.add(vecchioLimiteRichiesta);
		
		nuovoLimiteRichiesta = new ListGridField("nuovoLimiteRich", "Nuovo limite richiesta");
		fieldsToSet.add(nuovoLimiteRichiesta);

		cognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");
		fieldsToSet.add(cognomeNomeField);

		eMailField = new ListGridField("eMail", "e-mail");
		fieldsToSet.add(eMailField);

		telefonoField = new ListGridField("telefono", "Telefono");
		fieldsToSet.add(telefonoField);

		motivoRichiestaField = new ListGridField("motivoRichiesta", "Motivo richiesta");
		fieldsToSet.add(motivoRichiestaField);
		
		motivoRespingimentoField = new ListGridField("motivoResp", "Motivo respingimento");
		fieldsToSet.add(motivoRespingimentoField);

		/*
		statoField = new ListGridField("stato", "Stato");
		statoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		statoField.setType(ListGridFieldType.ICON);
		statoField.setAlign(Alignment.CENTER);
		statoField.setWrap(false);
		statoField.setWidth(30);
		statoField.setIconWidth(16);
		statoField.setIconHeight(16);
		statoField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				String statoConsolidamento = (String) value;
				if (statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					return buildImgButtonHtml("agibilita/" + statoConsolidamento.replaceAll(" ", "_") + ".png");
				}
				return null;
			}
		});
		statoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return (String) record.getAttribute("stato");
			}
		});
		fieldsToSet.add(statoField);
		*/
		
		statoField = new ListGridField("stato", "Stato");	
		statoField.setType(ListGridFieldType.ICON);
		statoField.setWidth(30);
		statoField.setIconWidth(16);
		statoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("da_evadere", "agibilita/da_evadere.png");
		flgValidoValueIcons.put("accolta", "agibilita/accolta.png");
		flgValidoValueIcons.put("respinta", "agibilita/respinta.png");
		flgValidoValueIcons.put("", "blank.png");
		statoField.setValueIcons(flgValidoValueIcons);		
		statoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("da_evadere".equals(record.getAttribute("stato"))) {
					return "Da evadere";
				}				
				else if("accolta".equals(record.getAttribute("stato"))) {
					return "Accolta";
				}				
				else if("respinta".equals(record.getAttribute("stato"))) {
					return "Respinta";
				}				
				return null;
			}
		});	
		fieldsToSet.add(statoField);

		dataEvasioneField = new ListGridField("dataEvasione", "Data evasione");
		dataEvasioneField.setType(ListGridFieldType.DATE);
		dataEvasioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		dataEvasioneField.setWrap(false);
		fieldsToSet.add(dataEvasioneField);

		evasaDaField = new ListGridField("evasaDa", "Evasa da");
		fieldsToSet.add(evasaDaField);

		accolgoRichiestaField = new ControlListGridField("accolgoRichiesta");
		accolgoRichiestaField.setType(ListGridFieldType.ICON);
		accolgoRichiestaField.setAttribute("custom", true);
		accolgoRichiestaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		accolgoRichiestaField.setWrap(false);
		accolgoRichiestaField.setWidth(30);
		accolgoRichiestaField.setIconWidth(16);
		accolgoRichiestaField.setIconHeight(16);
		accolgoRichiestaField.setShowHover(true);
		accolgoRichiestaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("stato") != null && "da_evadere".equals(record.getAttributeAsString("stato"))) {
					return buildImgButtonHtml("ok.png");
				} else {
					return null;
				}
			}
		});
		accolgoRichiestaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Accolgo richiesta";
			}
		});

		accolgoRichiestaField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final Record record = event.getRecord();
				if (record.getAttribute("stato") != null && "da_evadere".equals(record.getAttributeAsString("stato"))) {
					Record recordToPopup = new Record();
					recordToPopup.setAttribute("vecchioLimiteRich", record.getAttributeAsString("vecchioLimiteRich"));

					AccoltaRichiestaPopup lAccoltaRichiestaPopup = new AccoltaRichiestaPopup(record.getAttributeAsInt("vecchioLimiteRich")) {

						@Override
						public void onClickOkButton(Record object, DSCallback callback) {
							String nuovoLimiteRich = object.getAttributeAsString("nuovoLimiteRich");
							final GWTRestDataSource lRichiesteAgibilitaDS = new GWTRestDataSource("RichiesteAgibilitaDataSource");
							lRichiesteAgibilitaDS.extraparam.put("esito", "accolgo");
							lRichiesteAgibilitaDS.extraparam.put("nuovoLimiteRich", nuovoLimiteRich);							
							lRichiesteAgibilitaDS.executecustom("manageEsitoRichiesta", record, new DSCallback() {

								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean("Richiesta accolta con successo", "", MessageType.INFO));
										layout.hideDetail(true);
										layout.reloadListAndSetCurrentRecord(record);
									} 
									manageOnCloseClick();
								}
							});

						}
					};
					lAccoltaRichiestaPopup.show();	
				
				}

			}
		});

		fieldsToSet.add(accolgoRichiestaField);

		rigettoRichiestaField = new ControlListGridField("rigettoRichiesta");
		rigettoRichiestaField.setType(ListGridFieldType.ICON);
		rigettoRichiestaField.setAttribute("custom", true);
		rigettoRichiestaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		rigettoRichiestaField.setWrap(false);
		rigettoRichiestaField.setWidth(30);
		rigettoRichiestaField.setIconWidth(16);
		rigettoRichiestaField.setIconHeight(16);
		rigettoRichiestaField.setShowHover(true);
		rigettoRichiestaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("stato") != null && "da_evadere".equals(record.getAttributeAsString("stato"))) {
					return buildImgButtonHtml("ko.png");
				} else {
					return null;
				}
			}
		});
		rigettoRichiestaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return "Rigetto richiesta";
			}
		});

		rigettoRichiestaField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final Record record = event.getRecord();
				if (record.getAttribute("stato") != null && "da_evadere".equals(record.getAttributeAsString("stato"))) {
					RigettoRichiestaPopup lRigettoRichiestaPopup = new RigettoRichiestaPopup() {

						@Override
						public void onClickOkButton(Record object, DSCallback callback) {
							String motivazioni = object.getAttributeAsString("motivazione");
							final GWTRestDataSource lRichiesteAgibilitaDS = new GWTRestDataSource("RichiesteAgibilitaDataSource");
							lRichiesteAgibilitaDS.extraparam.put("esito", "rigetto");
							lRichiesteAgibilitaDS.extraparam.put("motivazione", motivazioni);							
							lRichiesteAgibilitaDS.executecustom("manageEsitoRichiesta", record, new DSCallback() {

								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Layout.addMessage(new MessageBean("Richiesta rigettata con successo", "", MessageType.INFO));
										layout.hideDetail(true);
										layout.reloadListAndSetCurrentRecord(record);
									} 
									manageOnCloseClick();
								}
							});

						}
					};
					lRigettoRichiestaPopup.show();	
				}

			}
		});

		fieldsToSet.add(rigettoRichiestaField);

		setFields(fieldsToSet.toArray(new ListGridField[fieldsToSet.size()]));

	}


	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}
}
