/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;

public class StampaEtichettaFaldoneLayout extends VLayout {

	private HiddenItem idUdItem;
	
	protected DetailSection detailSectionEstremiProtocollo;

	private DynamicForm _form;
	
	protected ExtendedTextItem numProtocolloGeneraleItem;
	protected AnnoItem annoProtocolloGeneraleItem;
	private NumericItem nroEtichette;

	public StampaEtichettaFaldoneLayout(StampaEtichettaFaldoneWindow pStampaEtichettaProtocolloWindow) {

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(10);
		_form.setColWidths("*");
		_form.setWrapItemTitles(false);
		
		idUdItem = new HiddenItem("idUd");
		
		CustomValidator estremiProtocolloValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
					if (value == null || value.equals("")) return true;
					String idUd = _form.getValueAsString("idUd");
					if (idUd != null && !"".equals(idUd)) {
						return true;
					} else {
						return false;
					}
			}
		};
		estremiProtocolloValidator.setErrorMessage("Estremi di protocollo non validi");
				
		numProtocolloGeneraleItem = new ExtendedTextItem("numProtocolloGenerale", "N° protocollo") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		numProtocolloGeneraleItem.setWidth(100);
		numProtocolloGeneraleItem.setLength(50);
		numProtocolloGeneraleItem.setKeyPressFilter("[0-9]");
		numProtocolloGeneraleItem.setRequired(true);
		numProtocolloGeneraleItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				_form.setValue("idUd", "");
			}
		});
		numProtocolloGeneraleItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				// recuperaIdProtocolloGenerale();
			}
		});
		numProtocolloGeneraleItem.setValidators(estremiProtocolloValidator);
		
		annoProtocolloGeneraleItem = new AnnoItem("annoProtocolloGenerale", "Anno") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		annoProtocolloGeneraleItem.setRequired(true);
		annoProtocolloGeneraleItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				_form.setValue("idUd", "");
			}
		});
		annoProtocolloGeneraleItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				// recuperaIdProtocolloGenerale();
			}
		});
		annoProtocolloGeneraleItem.setValidators(estremiProtocolloValidator);
		
		SpacerItem hSpace = new SpacerItem();
		hSpace.setWidth(10);
		
		nroEtichette = new NumericItem("nroEtichette", "N.ro copie");
		nroEtichette.setRequired(true);
		nroEtichette.setStartRow(false);
		nroEtichette.setDefaultValue(1);
		nroEtichette.setWidth(50);

		_form.setFields(idUdItem, numProtocolloGeneraleItem, annoProtocolloGeneraleItem, hSpace, nroEtichette);
		
		detailSectionEstremiProtocollo = new DetailSection("Estremi protocollo", false, true, true, _form);

		final Button stampaButton = new Button("Stampa");
		stampaButton.setIcon("ok.png");
		stampaButton.setIconSize(16);
		stampaButton.setAutoFit(false);
		stampaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				stampaButton.focusAfterGroup();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						
						recuperaIdProtocolloGenerale();

					}
				});
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setName("bottoni");
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(stampaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(detailSectionEstremiProtocollo);

		addMember(layout);
		addMember(_buttons);
	}

	public DynamicForm getForm() {
		return _form;
	}
	
	protected void recuperaIdProtocolloGenerale() {
		_form.clearErrors(true);
		if (isDatiProtocolloInseriti()) {
			Layout.showWaitPopup("Verifica estremi protocollo in corso...");
			Record lRecord = new Record();
			lRecord.setAttribute("nroProtocollo", numProtocolloGeneraleItem.getValueAsString());
			lRecord.setAttribute("annoProtocollo", annoProtocolloGeneraleItem.getValueAsString());
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSource.addParam("hideMessageError", "true");
			lGwtRestDataSource.executecustom("recuperaIdUd", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						if (record != null) {
							_form.setValue("idUd", record.getAttribute("idUd"));
						} else {
							_form.setValue("idUd", "");
						}							
					} else {
						_form.setValue("idUd", "");
					}
					avviaStampa();
				}
			});
		} else {
			// Serve solo a dare l'evidenza degli errori validando il for,
			_form.validate();
		}
	}
	
	public void avviaStampa() {
		if (_form.validate()) {
			final String nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
			String numeroProtocollo = numProtocolloGeneraleItem.getValueAsString();
			String annoProtocollo = annoProtocolloGeneraleItem.getValueAsString();
			String numeroEtichetteDaStampare = nroEtichette.getValue() != null && !"".equalsIgnoreCase(nroEtichette.getValueAsString()) ? nroEtichette.getValueAsString() : "1";
			
			Record etichetta = new Record();
			etichetta.setAttribute("testoFaldone", "PG " + numeroProtocollo + "/" + annoProtocollo);
			for (int j = numeroProtocollo.length() ; j < 7; j++) {
				numeroProtocollo = "0" + numeroProtocollo;
			}
			etichetta.setAttribute("barcodeFaldone", numeroProtocollo + "000" + annoProtocollo + "000");
			
			Integer sizeEtichette = Integer.valueOf(numeroEtichetteDaStampare);
			Record[] etichette = new Record[sizeEtichette];
			for(int i = 0; i <= sizeEtichette; i++) {
				etichette[i] = etichetta;
			}
			StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numeroEtichetteDaStampare, new StampaEtichettaCallback() {

				@Override
				public void execute() {
				
				}
			});
		}	
	}
	
	protected boolean isDatiProtocolloInseriti() {
		boolean hasNumProtocolloGeneraleEmpty = numProtocolloGeneraleItem.getValue() == null || "".equals(numProtocolloGeneraleItem.getValueAsString().trim());
		boolean hasAnnoProtocolloGeneraleEmpty = annoProtocolloGeneraleItem.getValue() == null || "".equals(annoProtocolloGeneraleItem.getValueAsString().trim());
		return !hasNumProtocolloGeneraleEmpty && !hasAnnoProtocolloGeneraleEmpty;
	}
	
}
