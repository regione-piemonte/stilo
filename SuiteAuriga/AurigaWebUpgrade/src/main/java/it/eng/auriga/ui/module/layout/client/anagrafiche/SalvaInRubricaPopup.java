/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import java.util.Date;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;

public abstract class SalvaInRubricaPopup extends ModalWindow {

	private SalvaInRubricaPopup _window;

	private SoggettiDetail detail;
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;
	protected DetailToolStripButton lookupButton;

	protected String mode;
	private static String PA = "PA";
	private static String PG = "PG";
	private static String UOI = "UOI";
	private static String AOOI = "AOOI";
	private static String PF = "PF";
	private static String UP = "UP";

	public SalvaInRubricaPopup(String finalita, Record recordSoggetto, Record recordIndirizzo) {

		super("anagrafiche_soggetti.detail", true);

		setTitle("Compila dati soggetto");

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnagraficaSoggettiDataSource", "idSoggetto", FieldType.TEXT);

		detail = new SoggettiDetail("anagrafiche_soggetti.detail");
		detail.setHeight100();
		detail.setDataSource(lGwtRestDataSource);

		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editMode();
			}
		});

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onSaveButtonClick();
			}
		});

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						editMode();
					}
				});
			}
		});

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						viewMode();
					}
				});
			}
		});

		lookupButton = new DetailToolStripButton(I18NUtil.getMessages().selezionaButton_prompt(), "buttons/seleziona.png");
		lookupButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				Record record = new Record(detail.getValuesManager().getValues());
				manageLookupBack(record);
				_window.markForDestroy();
			}
		});

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);
		detailToolStrip.addButton(lookupButton);
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addBackButton();
			detail.setHeight(450);
		}

		VLayout detailLayout = new VLayout();
		detailLayout.setOverflow(Overflow.HIDDEN);
		setOverflow(Overflow.AUTO);

		detailLayout.setMembers(detail, detailToolStrip);

		detailLayout.setHeight100();
		detailLayout.setWidth100();
		setBody(detailLayout);

		newMode();

		buildValuesFormSoggetti(detail, recordSoggetto, recordIndirizzo);

		setIcon("menu/soggetti.png");

	}

	public void buildValuesFormSoggetti(SoggettiDetail detail, Record recordSoggetto, Record recordIndirizzo) {
		String tipo = recordSoggetto.getAttributeAsString("tipo");
		String denominazione = recordSoggetto.getAttributeAsString("denominazione");
		String cognome = recordSoggetto.getAttributeAsString("cognome");
		String nome = recordSoggetto.getAttributeAsString("nome");
		String codRapido = recordSoggetto.getAttributeAsString("codRapido");
		String codiceFiscale = recordSoggetto.getAttributeAsString("codiceFiscale");

		Record soggettiDetailRecord = new Record();

		/*
		 * DATI SOGGETTO
		 */

		if (tipo == null || "".equals(tipo) || PA.equals(tipo) || PG.equals(tipo) || UOI.equals(tipo) || AOOI.equals(tipo)) {
			soggettiDetailRecord.setAttribute("denominazione", denominazione);
		} else if (PF.equals(tipo) || UP.equals(tipo)) {
			soggettiDetailRecord.setAttribute("cognome", cognome);
			soggettiDetailRecord.setAttribute("nome", nome);
		}

		if (tipo != null && !"".equals(tipo)) {
			soggettiDetailRecord.setAttribute("tipo", (getTipologiaFromTipoSoggetto(tipo)));
		}

		soggettiDetailRecord.setAttribute("codiceRapido", codRapido);
		soggettiDetailRecord.setAttribute("codiceFiscale", codiceFiscale);

		/**
		 * DATI INDIRIZZO
		 */

		if (showIndirizzoSection(recordIndirizzo)) {

			RecordList listaIndirizzi = new RecordList();
			recordIndirizzo.setAttribute("dataValidoDal", new Date());
			listaIndirizzi.add(recordIndirizzo);

			soggettiDetailRecord.setAttribute("listaIndirizzi", listaIndirizzi);
		}

		detail.editRecord(soggettiDetailRecord);
	}

	private Boolean showIndirizzoSection(Record recordIndirizzo) {
		Boolean isVisible = false;
		if ((recordIndirizzo.getAttributeAsString("toponimo") != null && !"".equals(recordIndirizzo.getAttributeAsString("toponimo")))
				|| (recordIndirizzo.getAttributeAsString("indirizzo") != null && !"".equals(recordIndirizzo.getAttributeAsString("indirizzo")))) {
			isVisible = true;
		}
		return isVisible;
	}

	public String getTipologiaFromTipoSoggetto(String tipoSoggetto) {
		if (tipoSoggetto != null && !"".equals(tipoSoggetto)) {
			if ("PA".equals(tipoSoggetto)) {
				return "#APA";
			} else if ("AOOI".equals(tipoSoggetto)) {
				return "#IAMM";
			} else if ("UOI".equals(tipoSoggetto)) {
				return "UO;UOI";
			} else if ("UP".equals(tipoSoggetto)) {
				return "UP";
			} else if ("PF".equals(tipoSoggetto)) {
				return "#AF";
			} else if ("PG".equals(tipoSoggetto)) {
				return "#AG";
			}
		}
		return null;
	}

	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if (detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	protected void realSave(final Record record) {
		DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					if (savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
						try {
							manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									viewMode();
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])),
											"", MessageType.INFO));
								}
							});
						} catch (Exception e) {
							Layout.hideWaitPopup();
						}
					} else {
						detail.getValuesManager().setValue("flgIgnoreWarning", "1");
						Layout.hideWaitPopup();
					}

				} else {
					Layout.hideWaitPopup();
				}
			}
		};
		try {
			if ((saveOperationType != null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idSoggetto") == null
					|| record.getAttribute("idSoggetto").equals("")) {
				detail.getDataSource().addData(record, callback);
			} else {
				detail.getDataSource().updateData(record, callback);
			}
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}

	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		((GWTRestDataSource) detail.getDataSource()).getData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					detail.editRecord(record, recordNum);
					detail.getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		});
	}

	public String getTipoEstremiRecord(Record record) {
		
		if (record.getAttributeAsString("tipo") != null && !"".equals(record.getAttributeAsString("tipo"))) {
			if ("UP".equals(record.getAttributeAsString("tipo")) || "#AF".equals(record.getAttributeAsString("tipo"))) {
				return record.getAttributeAsString("cognome") + " " + record.getAttributeAsString("nome");
			} else {
				return record.getAttributeAsString("denominazione");
			}
		}
		return null;
	}

	public void reload(final DSCallback callback) {
		if (this.mode.equals("new")) {
			detail.editNewRecord();
			detail.getValuesManager().clearErrors(true);
		} else {
			Record record = new Record(detail.getValuesManager().getValues());
			((GWTRestDataSource) detail.getDataSource()).getData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						detail.editRecord(record);
						detail.getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					}
				}
			});
		}
	}

	public boolean isRecordAbilToMod(boolean flgDiSistema) {
		
		return !flgDiSistema && Layout.isPrivilegioAttivo("GRS/S/UO;M");
	}

	public void newMode() {
		this.mode = "new";
		detail.setCanEdit(true);
		detail.newMode();
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();
		lookupButton.hide();
	}

	public void viewMode() {
		this.mode = "view";
		detail.setCanEdit(false);
		detail.viewMode();
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();
		Record record = new Record(detail.getValuesManager().getValues());
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		if (isRecordAbilToMod(flgDiSistema)) {
			editButton.show();
		} else {
			editButton.hide();
		}
		if (record.getAttributeAsBoolean("flgSelXFinalita") != null && record.getAttributeAsBoolean("flgSelXFinalita")) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
	}

	public void editMode() {
		this.mode = "edit";
		detail.setCanEdit(true);
		detail.editMode();
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();
		lookupButton.hide();
	}

	@Override
	public void manageOnCloseClick() {
		
		if (getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}
	}

	public abstract void manageLookupBack(Record record);
	
	private void addBackButton() {
		DetailToolStripButton closeModalWindow = new DetailToolStripButton(I18NUtil.getMessages().backButton_prompt(), "buttons/back.png");
		closeModalWindow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_window.close();
			}
		});
		
		detailToolStrip.addButton(closeModalWindow);
	}

}
