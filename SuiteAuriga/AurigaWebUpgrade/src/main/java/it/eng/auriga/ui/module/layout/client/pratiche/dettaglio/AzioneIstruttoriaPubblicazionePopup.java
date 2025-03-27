/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.DocumentItem;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.nuovapropostaatto2.items.DatiContabiliStoriciWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class AzioneIstruttoriaPubblicazionePopup extends Window {
	
	public static final String _AVVIO_COMPARATIVO_ACTION = "Avvio comparativo";
	public static final String _AVVIO_ACTION = "Avvio";
	public static final String _PROSEGUIMENTO_ISTRUTTORIA_CON_INTERRUZIONE_TERMINI_ACTION = "Proseguimento istruttoria con interruzione termini";
	public static final String _PROSEGUIMENTO_ISTRUTTORIA_SENZA_INTERRUZIONE_TERMINI_ACTION = "Proseguimento istruttoria senza interruzione termini";
	public static final String _RIPUBBLICAZIONE_ACTION = "Ripubblicazione";
	public static final String _PUBBLICAZIONE_ACTION = "Pubblicazione";
	
	private AzioneIstruttoriaPubblicazionePopup instance;

	private ValuesManager vm;
	
	private DynamicForm form;
	private HiddenItem idUdAvvioHiddenItem;
	private HiddenItem idDocAvvioHiddenItem;
	private HiddenItem nomeFileDocAvvioHiddenItem;
	private HiddenItem listaAllegatiAvvioHiddenItem;
	private HiddenItem dataFinePubblicazioneHiddenItem;
	private HiddenItem listaDatiIstanzeConcorrentiHiddenItem;
	private HiddenItem codPraticheConcorrentiHiddenItem;
	private SelectItem sceltaGiorniItem;
	private ExtendedDateItem dataPubblDalItem;
	private ExtendedNumericItem numGiorniPubblItem;
	private ExtendedDateItem dataPubblAlItem;
	
	private DynamicForm formDocumento;
	private DocumentItem fileDocumentoItem;
	private TextItem nroProtocolloAvvioItem;
	private TextItem dataProtocolloAvvioItem;
	private ImgButtonItem apriDettaglioDocAvvioButton;
	private ImgButtonItem apriDatiIstanzeConcorrentiButton;
	
	private DynamicForm formAllegati;
	private AllegatiItem fileAllegatiItem;
	
	private Button confermaButton;
	private Button annullaButton;
	
	private String idUd;
	private String idDoc;
	private String azione;
	
	public AzioneIstruttoriaPubblicazionePopup(final String idUd, final String idDoc, final String azione, final Record recordIstruttoriaPubbl, final ServiceCallback<Record> reloadCallback) {
		
		instance = this;

		this.idUd = idUd;
		this.idDoc = idDoc;
		this.azione = azione;
		
		this.vm = new ValuesManager();
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle(azione);
		setShowTitle(true);
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		
		if(azione != null) {
			if(azione.equals(_PROSEGUIMENTO_ISTRUTTORIA_CON_INTERRUZIONE_TERMINI_ACTION) ) {
				setHeight(100);
				setWidth(400);
			} else if(azione.equals(_AVVIO_COMPARATIVO_ACTION) || azione.equals(_AVVIO_ACTION)) {
				setHeight(200);
				setWidth(1200);
			} else if(azione.equals(_PUBBLICAZIONE_ACTION) || azione.equals(_RIPUBBLICAZIONE_ACTION)) {
				setHeight(100);
				setWidth(800);
			}
		}

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(20);
		form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		form.setBorder("1px solid grey");
		form.setMargin(5);
		
		List<FormItem> items = new ArrayList<FormItem>();
		
		idUdAvvioHiddenItem = new HiddenItem("idUdAvvio");
		items.add(idUdAvvioHiddenItem);
		
		idDocAvvioHiddenItem = new HiddenItem("idDocAvvio");
		items.add(idDocAvvioHiddenItem);
		
		nomeFileDocAvvioHiddenItem = new HiddenItem("nomeFileDocAvvio");
		items.add(nomeFileDocAvvioHiddenItem);
		
		listaAllegatiAvvioHiddenItem = new HiddenItem("listaAllegatiAvvio");
		items.add(listaAllegatiAvvioHiddenItem);
		
		dataFinePubblicazioneHiddenItem = new HiddenItem("dataFinePubblicazione");
		items.add(dataFinePubblicazioneHiddenItem);

		listaDatiIstanzeConcorrentiHiddenItem = new HiddenItem("listaDatiIstanzeConcorrenti");
		items.add(listaDatiIstanzeConcorrentiHiddenItem);
		
		codPraticheConcorrentiHiddenItem = new HiddenItem("codPraticheConcorrenti");
		items.add(codPraticheConcorrentiHiddenItem);
		
		if(showSceltaGiorniItem()) {
			sceltaGiorniItem = new SelectItem("sceltaGiorni", azione != null && azione.equals(_PROSEGUIMENTO_ISTRUTTORIA_CON_INTERRUZIONE_TERMINI_ACTION) ? "Scelta termini" : "Scelta tempi");
			sceltaGiorniItem.setWrapTitle(false);
			LinkedHashMap<String, String> sceltaGiorniValueMap = new LinkedHashMap<String, String>();
			sceltaGiorniValueMap.put("90", "90 giorni");
			sceltaGiorniValueMap.put("180", "180 giorni");
			sceltaGiorniItem.setValueMap(sceltaGiorniValueMap);
			sceltaGiorniItem.setColSpan(1);
			sceltaGiorniItem.setWidth(150);
			sceltaGiorniItem.setRequired(true);
			items.add(sceltaGiorniItem);
		}
		
		if(showPubblicazioneItems()) {
			dataPubblDalItem = new ExtendedDateItem("dataPubblDal", azione != null && azione.equals(_RIPUBBLICAZIONE_ACTION) ? "Data inizio ripubblicazione" : "Data inizio pubblicazione");
			dataPubblDalItem.setWrapTitle(false);
			dataPubblDalItem.setColSpan(1);
			dataPubblDalItem.setDefaultValue((Date) null);
			dataPubblDalItem.setRequired(true);
			dataPubblDalItem.addChangedHandler(new ChangedHandler() {
					
				@Override
				public void onChanged(final ChangedEvent event) {
					manageOnChangedPeriodoPubbl("dataPubblDal");
				}
			});
			items.add(dataPubblDalItem);
			numGiorniPubblItem = new ExtendedNumericItem("numGiorniPubbl", azione != null && azione.equals(_RIPUBBLICAZIONE_ACTION) ? "N° giorni ripubblicazione" : "N° giorni pubblicazione", false);
			numGiorniPubblItem.setWrapTitle(false);
			numGiorniPubblItem.setColSpan(1);
			numGiorniPubblItem.setWidth(70);
			numGiorniPubblItem.setDefaultValue((String) null);
			numGiorniPubblItem.setRequired(true);
			numGiorniPubblItem.addChangedBlurHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(final ChangedEvent event) {
					manageOnChangedPeriodoPubbl("numGiorniPubbl");
				}
			});
			items.add(numGiorniPubblItem);
			dataPubblAlItem = new ExtendedDateItem("dataPubblAl", azione != null && azione.equals(_RIPUBBLICAZIONE_ACTION) ? "Data fine ripubblicazione" : "Data fine pubblicazione");
			dataPubblAlItem.setWrapTitle(false);
			dataPubblAlItem.setColSpan(1);
			dataPubblAlItem.setDefaultValue((Date) null);		
			dataPubblAlItem.setRequired(true);
			CustomValidator lPeriodoPubblicazioneValidator = new CustomValidator() {
				
				@Override
				protected boolean condition(Object value) {
					Date dataInizioPubbl = dataPubblDalItem.getValueAsDate();
			    	Date dataFinePubbl = dataPubblAlItem.getValueAsDate();
			    	if(dataInizioPubbl != null && dataFinePubbl != null) {
			    		Integer differenceDays = CalendarUtil.getDaysBetween(dataInizioPubbl, dataFinePubbl);
			    		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
			    			return (differenceDays >= 0);
			    		} else {
			    			return (differenceDays > 0);
			    		}
			    	}		    	
			    	return true;
				}
			};
			if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
				if(azione != null && azione.equals(_RIPUBBLICAZIONE_ACTION)) {
					lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine ripubblicazione non può essere antecedente a quella di inizio ripubblicazione");
				} else {
					lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione non può essere antecedente a quella di inizio pubblicazione");	
				}		
			} else {
				if(azione != null && azione.equals(_RIPUBBLICAZIONE_ACTION)) {
					lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine ripubblicazione deve essere successiva a quella di inizio ripubblicazione");
				} else {
					lPeriodoPubblicazioneValidator.setErrorMessage("La data di fine pubblicazione deve essere successiva a quella di inizio pubblicazione");	
				}
			}		
			//TODO La data di fine pubblicazione non può essere una data passata
			dataPubblAlItem.setValidators(lPeriodoPubblicazioneValidator);
			dataPubblAlItem.addChangedHandler(new ChangedHandler() {
			
				@Override
				public void onChanged(final ChangedEvent event) {
					manageOnChangedPeriodoPubbl("dataPubblAl");
				}
			});
			items.add(dataPubblAlItem);
		}
		
		form.setFields(items.toArray(new FormItem[items.size()]));
		
		if(showFileDocumentoItem()) {
			formDocumento = new DynamicForm();
			formDocumento.setValuesManager(vm);
			formDocumento.setKeepInParentRect(true);
			formDocumento.setWidth100();
			formDocumento.setHeight(1);
			formDocumento.setNumCols(20);
			formDocumento.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
			formDocumento.setCellPadding(5);
			formDocumento.setAlign(Alignment.CENTER);
			formDocumento.setOverflow(Overflow.VISIBLE);
			formDocumento.setTop(50);
			formDocumento.setBorder("1px solid grey");
			formDocumento.setMargin(5);
			
			fileDocumentoItem = new DocumentItem() {
				
				@Override
				public int getWidth() {
					return 250;
				}
				
				@Override
				public boolean showVisualizzaVersioniMenuItem() {
					return false;
				}
				
				@Override
				public boolean showAcquisisciDaScannerMenuItem() {
					return false;
				}
				
				@Override
				public boolean showFirmaMenuItem() {
					return false;
				}
				
//				@Override
//				public boolean isFormatoAmmesso(InfoFileRecord info) {	
//					String correctName = info != null ? info.getCorrectFileName() : "";
//					return correctName.toLowerCase().endsWith(".pdf");
//				}
			};
			fileDocumentoItem.setName("fileDocumento");
			fileDocumentoItem.setTitle("Carica documento");
			fileDocumentoItem.setWrapTitle(false);
			fileDocumentoItem.setRequired(true);
			fileDocumentoItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idUdAvvio = idUdAvvioHiddenItem.getValue() != null ? String.valueOf(idUdAvvioHiddenItem.getValue()) : null;
					return idUdAvvio == null || "".equals(idUdAvvio);
				}
			});
			
			nroProtocolloAvvioItem = new TextItem("nroProtocolloAvvio", "Prot. avvio N°") {
				
				@Override
				public void setCanEdit(Boolean canEdit) {
					super.setCanEdit(false);
				};
			};
			nroProtocolloAvvioItem.setCanEdit(false);
			nroProtocolloAvvioItem.setWrapTitle(false);
			nroProtocolloAvvioItem.setWidth(100);
			nroProtocolloAvvioItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idUdAvvio = idUdAvvioHiddenItem.getValue() != null ? String.valueOf(idUdAvvioHiddenItem.getValue()) : null;
					return idUdAvvio != null && !"".equals(idUdAvvio);	 
				}
			});
			
			dataProtocolloAvvioItem = new TextItem("dataProtocolloAvvio", "del") {
				
				@Override
				public void setCanEdit(Boolean canEdit) {
					super.setCanEdit(false);
				};
			};
			dataProtocolloAvvioItem.setCanEdit(false);
			dataProtocolloAvvioItem.setWidth(100);
			dataProtocolloAvvioItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idUdAvvio = idUdAvvioHiddenItem.getValue() != null ? String.valueOf(idUdAvvioHiddenItem.getValue()) : null;
					return idUdAvvio != null && !"".equals(idUdAvvio);		 
				}
			});
			
			apriDettaglioDocAvvioButton = new ImgButtonItem("apriDettaglioDocAvvioButton", "buttons/detail.png", "Visualizza dettaglio protocollo avvio");
			apriDettaglioDocAvvioButton.setAlwaysEnabled(true);
			apriDettaglioDocAvvioButton.setColSpan(2);
			apriDettaglioDocAvvioButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idUdAvvio = idUdAvvioHiddenItem.getValue() != null ? String.valueOf(idUdAvvioHiddenItem.getValue()) : null;
					return idUdAvvio != null && !"".equals(idUdAvvio);
				}
			});
			apriDettaglioDocAvvioButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					String idUdAvvio = idUdAvvioHiddenItem.getValue() != null ? String.valueOf(idUdAvvioHiddenItem.getValue()) : null;
					String estremiAvvio = "N° ";
					if (nroProtocolloAvvioItem.getValueAsString() != null && !"".equals(nroProtocolloAvvioItem.getValueAsString())) {
						estremiAvvio += nroProtocolloAvvioItem.getValueAsString() + " ";
					}
					if (dataProtocolloAvvioItem.getValueAsString() != null && !"".equals(dataProtocolloAvvioItem.getValueAsString())) {
						estremiAvvio += "del " + dataProtocolloAvvioItem.getValueAsString();
					}
					Record record = new Record();
					record.setAttribute("idUd", idUdAvvio);
					new DettaglioRegProtAssociatoWindow(record, "Dettaglio protocollo avvio " + estremiAvvio);
				}
			});
			
			apriDatiIstanzeConcorrentiButton  = new ImgButtonItem("apriDatiIstanzeConcorrentiButton", "buttons/altriDati.png", "Istanze concorrenti in avvio comparativo");
			apriDatiIstanzeConcorrentiButton.setAlwaysEnabled(true);
			apriDatiIstanzeConcorrentiButton.setColSpan(2);
			apriDatiIstanzeConcorrentiButton.setShowIfCondition(new FormItemIfFunction() {

				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					RecordList listaDatiIstanzeConcorrenti = new Record(vm.getValues()).getAttributeAsRecordList("listaDatiIstanzeConcorrenti");
					return listaDatiIstanzeConcorrenti != null && listaDatiIstanzeConcorrenti.getLength() > 0;
				}
			});
			apriDatiIstanzeConcorrentiButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					RecordList listaDatiIstanzeConcorrenti = new Record(vm.getValues()).getAttributeAsRecordList("listaDatiIstanzeConcorrenti");
					DatiIstanzeConcorrentiSUAADSPWindow lDatiIstanzeConcorrentiSUAADSPWindow = new DatiIstanzeConcorrentiSUAADSPWindow("datiIstanzeConcorrentiSUAADSPWindow", listaDatiIstanzeConcorrenti);
					lDatiIstanzeConcorrentiSUAADSPWindow.show();
				}
			});
			
			formDocumento.setFields(fileDocumentoItem, nroProtocolloAvvioItem, dataProtocolloAvvioItem, apriDettaglioDocAvvioButton, apriDatiIstanzeConcorrentiButton);
			
			formAllegati = new DynamicForm();
			formAllegati.setValuesManager(vm);
			formAllegati.setKeepInParentRect(true);
			formAllegati.setWidth100();
			formAllegati.setHeight(1);
			formAllegati.setNumCols(20);
			formAllegati.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
			formAllegati.setCellPadding(5);
			formAllegati.setAlign(Alignment.CENTER);
			formAllegati.setOverflow(Overflow.VISIBLE);
			formAllegati.setTop(50);
			formAllegati.setBorder("1px solid grey");
			formAllegati.setMargin(5);
			
			fileAllegatiItem = new AllegatiItem() {
				
				@Override
				public boolean getShowVersioneOmissis() {
					return false;
				}
				
				@Override
				public boolean isShowModalPreview() {
					return !AurigaLayout.getParametroDBAsBoolean("PREVIEW_NON_MODALE");
				}
			};
			fileAllegatiItem.setName("listaAllegati");
			fileAllegatiItem.setTitle("Allegati");
			fileAllegatiItem.setStartRow(true);
			fileAllegatiItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					String idUdAvvio = idUdAvvioHiddenItem.getValue() != null ? String.valueOf(idUdAvvioHiddenItem.getValue()) : null;
					return idUdAvvio == null || "".equals(idUdAvvio);
				}
			});
			
			formAllegati.setFields(fileAllegatiItem);
		}
		
		confermaButton = new Button("Ok");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (validate()) {
					Record lRecord = getRecordToSave();
					Layout.showWaitPopup(azione + " in corso: potrebbe richiedere qualche secondo. Attendere...");		
					new GWTRestService<Record, Record>("AzioneIstruttoriaPubblicazioneDataSource").call(lRecord, new ServiceCallback<Record>() {
						@Override
						public void execute(Record object) {
							Layout.hideWaitPopup();
							if(object.getAttribute("errore") != null && !"".equals(object.getAttribute("errore"))) {
								AurigaLayout.addMessage(new MessageBean(object.getAttribute("errore"), "", MessageType.ERROR));
								if(object.getAttributeAsBoolean("flgToReload") != null && object.getAttributeAsBoolean("flgToReload")) {
									//Con salvataggio parziale occorre comunque ricaricare i dati
									if(reloadCallback != null) {
										reloadCallback.execute(object);
									}
									//Chiudo la finestra
									instance.markForDestroy();
								}
							} else {
								if(azione != null && (azione.equals(_RIPUBBLICAZIONE_ACTION) || azione.equals(_PUBBLICAZIONE_ACTION))) {
									AurigaLayout.addMessage(new MessageBean(azione + " effettuata con successo", "", MessageType.INFO));										
								} else {
									AurigaLayout.addMessage(new MessageBean(azione + " effettuato con successo", "", MessageType.INFO));
								}
								if(reloadCallback != null) {
									reloadCallback.execute(object);
								}
								//Chiudo la finestra
								instance.markForDestroy();	
							}
						}
					});
				}
			}
		});
		
		annullaButton = new Button("Annulla"); 
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				instance.markForDestroy();
			}
		});

		HStack buttons = new HStack(5);
		buttons.setWidth100();
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(confermaButton);
		buttons.addMember(annullaButton);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		
		// Aggiungo al layout il form e i button
		if(showFileDocumentoItem()) {
			layout.setMembers(form, formDocumento, formAllegati, buttons);
		} else {
			layout.setMembers(form, buttons);
		}
		
		addItem(layout);
		
		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
		if(recordIstruttoriaPubbl != null) {
			editRecord(recordIstruttoriaPubbl);
		}

		draw();
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = new Record(vm.getValues());
		lRecordToSave.setAttribute("idUd", idUd);
		lRecordToSave.setAttribute("idDoc", idDoc);
		lRecordToSave.setAttribute("azione", azione);
		RecordList lRecordListAllegati = null;
		if (formAllegati != null) {
			if (formAllegati.getValueAsRecordList("listaAllegati") != null) {
				RecordList listaAllegati = formAllegati.getValueAsRecordList("listaAllegati");
				lRecordListAllegati = new RecordList();
				if(listaAllegati != null) {
					for (int i = 0; i < listaAllegati.getLength(); i++) {
						Record lRecordAllegato = listaAllegati.get(i);
						InfoFileRecord lInfoFileRecordAllegato = InfoFileRecord.buildInfoFileRecord(listaAllegati.get(i).getAttributeAsJavaScriptObject("infoFile"));
						lRecordAllegato.setAttribute("infoFile", lInfoFileRecordAllegato);
						lRecordListAllegati.add(lRecordAllegato);
					}
				}
			}
		}
		lRecordToSave.setAttribute("listaAllegati", lRecordListAllegati);
		return lRecordToSave;
	}
	
	public void clearErrors() {
		form.clearErrors(true);
		for (FormItem item : form.getFields()) {
			if(item != null && (item instanceof ReplicableItem)) {
				ReplicableItem lReplicableItem = (ReplicableItem) item;
				lReplicableItem.clearErrors();
			} else if(item != null && (item instanceof IDocumentItem)) {
				IDocumentItem lIDocumentItem = (IDocumentItem) item;
				lIDocumentItem.clearErrors();
			}
		}	
	}
	
	final public Boolean validate() {
		clearErrors();
		Boolean valid = form.validate();
		for (FormItem item : form.getFields()) {
			if (item instanceof ReplicableItem) {
				ReplicableItem lReplicableItem = (ReplicableItem) item;
				boolean itemValid = lReplicableItem.validate();
				valid = itemValid && valid;
			} else if (item instanceof IDocumentItem) {
				IDocumentItem lIDocumentItem = (IDocumentItem) item;
				boolean itemValid = lIDocumentItem.validate();
				valid = itemValid && valid;
			} else {
				boolean itemValid = item.validate();
				valid = itemValid && valid;
			}
		}
		return valid;
	}	
	
	private boolean showSceltaGiorniItem() {
		return azione != null && (azione.equals(_AVVIO_COMPARATIVO_ACTION) || azione.equals(_AVVIO_ACTION) || azione.equals(_PROSEGUIMENTO_ISTRUTTORIA_CON_INTERRUZIONE_TERMINI_ACTION));
	}
	
	private boolean showPubblicazioneItems() {
		return azione != null && (azione.equals(_RIPUBBLICAZIONE_ACTION) || azione.equals(_PUBBLICAZIONE_ACTION));
	}
	
	private boolean showFileDocumentoItem() {
		return azione != null && (azione.equals(_AVVIO_COMPARATIVO_ACTION) || azione.equals(_AVVIO_ACTION));
	}

	// Metodo che indica se nel conteggio dei giorni di pubblicazione il giorno di pubblicazione viene sempre considerato come 1 giorno intero, altrimenti no	 
	public boolean isConteggiaInteroGiornoCorrenteXPeriodoPubbl() {
		return AurigaLayout.getParametroDBAsBoolean("CONTEGGIA_INTERO_GIORNO_CORRENTE_X_PERIODO_PUBBL");
	}
		
	private void manageOnChangedPeriodoPubbl(String fieldName) {
    	Integer giorniPubblicazione = null;
    	if(numGiorniPubblItem.getValueAsString() != null && !"".equals(numGiorniPubblItem.getValueAsString())) {
    		giorniPubblicazione = Integer.parseInt(numGiorniPubblItem.getValueAsString());
		}   
    	Date dataInizioPubbl = dataPubblDalItem.getValueAsDate();
    	Date dataFinePubbl = dataPubblAlItem.getValueAsDate();
    	if(fieldName != null) {
			if("dataPubblDal".equals(fieldName)) {
				if(dataInizioPubbl != null && giorniPubblicazione != null) {
					calcolaDataFinePubbl(dataInizioPubbl, giorniPubblicazione);
					dataPubblAlItem.validate();
				} else if(dataInizioPubbl != null && dataFinePubbl != null) {
					if(dataPubblAlItem.validate()) {
						calcolaGiorniPubbl(dataInizioPubbl, dataFinePubbl);
					}
				}
			} else if("numGiorniPubbl".equals(fieldName)) {
				if(dataInizioPubbl != null && giorniPubblicazione != null) {
					calcolaDataFinePubbl(dataInizioPubbl, giorniPubblicazione);
					dataPubblAlItem.validate();
				}
			} else if("dataPubblAl".equals(fieldName)) {
				if(dataInizioPubbl != null && dataFinePubbl != null) {
					if(dataPubblAlItem.validate()) {
						calcolaGiorniPubbl(dataInizioPubbl, dataFinePubbl);
					}
			    } 
			}
    	}
    }
	
	private void calcolaDataFinePubbl(Date dataInizioPubbl, Integer giorniPubblicazione) {
    	if(dataInizioPubbl != null && giorniPubblicazione != null) {
			Date dataFinePubbl = dataInizioPubbl;
			if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
        		CalendarUtil.addDaysToDate(dataFinePubbl, giorniPubblicazione - 1);
        	} else {
        		CalendarUtil.addDaysToDate(dataFinePubbl, giorniPubblicazione);
        	}
			dataPubblAlItem.setValue(dataFinePubbl); 
		}
    }
    
    private void calcolaGiorniPubbl(Date dataInizioPubbl, Date dataFinePubbl) {
    	if(dataInizioPubbl != null && dataFinePubbl != null) {
    		Integer differenceDays = CalendarUtil.getDaysBetween(dataInizioPubbl, dataFinePubbl);
    		if(isConteggiaInteroGiornoCorrenteXPeriodoPubbl()) {
    			if(differenceDays >= 0) {
    				numGiorniPubblItem.setValue("" + (differenceDays + 1));
    			}
			} else {
				if(differenceDays > 0) {
					numGiorniPubblItem.setValue("" + differenceDays);
				}
			}
	    }
    }
    
    public void editRecord(Record record) {
    	vm.editRecord(record);
    	if(formAllegati != null) {
			if(record.getAttribute("idUdAvvio") != null && !"".equals(record.getAttribute("idUdAvvio"))) {
				formAllegati.hide();
			} else {
				formAllegati.show();
			}
    	}
    }
	
}
