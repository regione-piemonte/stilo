/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.NuovaPropostaAtto2CompletaDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DettaglioOpereAttoPopup extends ModalWindow {

	private DettaglioOpereAttoPopup window;
	
	private NuovaPropostaAtto2CompletaDetail detail;

	private ToolStrip detailToolStrip;
	
	private DetailToolStripButton editButton;
	private DetailToolStripButton saveButton;

	private VLayout detailLayout;

	private String mode;
	
	private String idUd;
	private String idProcess;
	private String activityName;
	private String nomeFlussoWF;
	private String tipoDocumentoCorrente;
	private String rowidDocCorrente;
	private Record recordCallExecAtt;
	private Record recordEvento;
	
	public DettaglioOpereAttoPopup(Record record, String title) {
	
		super("dettaglio_opere_atto", true);

		window = this;
		
		setTitle(title);

		setAutoCenter(true);
		
		setHeight(300);
		setWidth(900);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);					
		
		this.recordCallExecAtt = new Record(record.getJsObj());
		this.idUd = record.getAttributeAsString("idUd");
		this.idProcess = record.getAttributeAsString("idProcess");
		this.activityName = record.getAttributeAsString("activityName");
		String idDefProcFlow = record.getAttributeAsString("idDefProcFlow");
		this.nomeFlussoWF = (idDefProcFlow != null && idDefProcFlow.contains(":")) ? idDefProcFlow.substring(0, idDefProcFlow.indexOf(":")) : idDefProcFlow;
		
		setOverflow(Overflow.AUTO);

		detailLayout = new VLayout();
		detailLayout.setOverflow(Overflow.HIDDEN);
		detailLayout.setHeight100();
		detailLayout.setWidth100();
		setBody(detailLayout);
		
		setIcon("pratiche/task/buttons/modifica_opere_atto.png");
				 
		if (idProcess != null && !"".equals(idProcess)) {
			callExecAtt(recordCallExecAtt, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordEvento) {		
					recordEvento = lRecordEvento;
					String tabIDIniziale = NuovaPropostaAtto2CompletaDetail._TAB_OPERE_ADSP_ID;
					manageLoadDetail(tabIDIniziale, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							
							editMode();
							
							window.show();		
						}
					});
				}
			});
		}		
	}
	
	public void callExecAtt(final Record record, final ServiceCallback<Record> callback) {
		Layout.showWaitPopup("Caricamento dati in corso...");
		GWTRestService<Record, Record> lCallExecAttDatasource = new GWTRestService<Record, Record>("CallExecAttDatasource");
		lCallExecAttDatasource.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(final Record output) {
				String warninMsg = output.getAttribute("warningMsg");
				if (warninMsg != null && !"".equals(warninMsg)) {
					AurigaLayout.showConfirmDialogWithWarning("Attenzione!", warninMsg, "Procedi", "Annulla", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value != null && value) {
								if (callback != null) {
									callback.execute(output);
								}
							}
						}
					});
				} else {
					if (callback != null) {
						callback.execute(output);
					}
				}
			}
		});
	}

	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if (detail.validate()) {
			realSave(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	protected void realSave(final Record record) {
		final DSCallback callback = new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record savedRecord = response.getData()[0];
					if (savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
						try {
							reload(new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									viewMode();
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean("Salvataggio effettuato con successo", "", MessageType.INFO));
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
		realSave(record, callback);
	}
	
	protected void realSave(final Record record, final DSCallback callback) {
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");		
		try {
			final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2DataSource.executecustom("modificaOpereADSP", record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						detail.salvaAttributiDinamiciDoc(false, rowidDocCorrente, activityName, null, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								if (callback != null) {
									callback.execute(response, null, new DSRequest());
								}
							}
						});
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	public void reload(final DSCallback callback) {
		if (idProcess != null && !"".equals(idProcess)) {
			callExecAtt(recordCallExecAtt, new ServiceCallback<Record>() {

				@Override
				public void execute(final Record lRecordEvento) {		
					recordEvento = lRecordEvento;
					manageLoadDetail(null, callback);
				}
			});
		}
	}

	public void manageLoadDetail(final String tabIDIniziale, final DSCallback callback) {
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					createDetailToolStrip();
					LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
					detail = new NuovaPropostaAtto2CompletaDetail("dettaglio_opere_atto", tabs) {
						
						@Override
						public void build() {
							this.recordParametriTipoAtto = recordEvento != null ? recordEvento.getAttributeAsRecord("parametriTipoAtto") : null;
							super.build();
						}
						
						@Override
						public String getIdProcessTask() {
							return idProcess;
						}
						
						@Override
						public Record getRecordEventoXInfoModelli() {
							return recordEvento;
						}
						
						@Override
						public Record getRecordToSave() {
							Record lRecordToSave = super.getRecordToSave();
							lRecordToSave.setAttribute("idProcess", getIdProcessTask());
							lRecordToSave.setAttribute("idModello", getIdModDispositivo());
							lRecordToSave.setAttribute("nomeModello", getNomeModDispositivo());
							lRecordToSave.setAttribute("displayFilenameModello", getDisplayFilenameModDispositivo());
							return lRecordToSave;
						}
						
						@Override
						public void setCanEdit(boolean canEdit) {
							super.setCanEdit(canEdit);
							if (attributiAddDocDetails != null) {
								for (String key : attributiAddDocDetails.keySet()) {
									AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
									detail.setCanEdit(canEdit);
								}
							}
						}
						
						@Override
						public void editRecord(Record record) {
							tipoDocumentoCorrente = record.getAttribute("tipoDocumento");
							rowidDocCorrente = record.getAttribute("rowidDoc");
							super.editRecord(record);
							caricaAttributiDinamiciDoc(nomeFlussoWF, "", activityName, tipoDocumentoCorrente, rowidDocCorrente);
						}
						
						@Override
						protected void afterCaricaAttributiDinamiciDoc() {
							super.afterCaricaAttributiDinamiciDoc();
							if(tabIDIniziale != null && !"".equals(tabIDIniziale)) {								
								tabSet.selectTab(tabSet.getTabWithID(tabIDIniziale));
							}
							if(callback != null) {
								callback.execute(response, null, new DSRequest());
							}
						}
						
						@Override
						public boolean show_TAB_DATI_SCHEDA(){
							return false;
						}	
						
						@Override
						public boolean show_TAB_DATI_DISPOSITIVO(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_DISPOSITIVO_2(){
							return false;
						}	
						
						@Override
						public boolean show_TAB_ALLEGATI(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_PUBBL(){
							return false;
						}
						
						@Override
						public boolean show_TAB_MOVIMENTI_CONTABILI(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_GSA(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_SPESA_CORRENTE(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_SPESA_CONTO_CAPITALE(){
							return false;
						}
						
						@Override
						public boolean show_TAB_AGGREGATO_SMISTAMENTO_ACTA(){
							return false;
						}
						
						@Override
						public boolean show_TAB_OPERE_ADSP(){
							return true;
						}
						
						@Override
						public boolean show_TAB_DATI_CONTABILI_ADSP(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_CONTABILI_AVB(){
							return false;
						}
						
						@Override
						public boolean show_TAB_DATI_TRASP_AVB(){
							return false;
						}
					};
					detail.setHeight100();
					detail.setWidth100();
					detail.editRecord(record);
					detail.getValuesManager().clearErrors(true);
					
					if(detailToolStrip != null) {
						detailLayout.setMembers(detail, detailToolStrip);
					} else {
						detailLayout.setMembers(detail);
					}
				}
			}
		});
	}
	
	public void createDetailToolStrip() {

		List<DetailToolStripButton> listaDetailToolStripButtons = new ArrayList<DetailToolStripButton>();
		
		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editMode();
			}
		});
		listaDetailToolStripButtons.add(editButton);

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onSaveButtonClick();
			}
		});
		listaDetailToolStripButtons.add(saveButton);
		
		if(listaDetailToolStripButtons.size() > 0) {
			detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right
			for(DetailToolStripButton button : listaDetailToolStripButtons) {
				detailToolStrip.addButton(button);
			}
		}
	}
	
	public void viewMode() {
		this.mode = "view";
		detail.setCanEdit(false);
		detail.viewMode();

		editButton.show();
		saveButton.hide();
	}

	public void editMode() {
		this.mode = "edit";
		detail.setCanEdit(true);
		detail.editMode();

		editButton.hide();
		saveButton.show();
	}

	@Override
	public void manageOnCloseClick() {
		if (getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}
	}
}