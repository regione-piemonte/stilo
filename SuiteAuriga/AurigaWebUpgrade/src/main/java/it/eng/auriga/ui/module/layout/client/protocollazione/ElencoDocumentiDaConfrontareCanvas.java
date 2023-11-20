/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class ElencoDocumentiDaConfrontareCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
	
	private ImgButtonItem mostraASinistraItem;
	private ImgButtonItem mostraADestraItem;
	
	private HiddenItem idUdItem;
	protected ExtendedTextItem siglaItem;
	protected ExtendedNumericItem numeroItem;
	protected AnnoItem annoItem;
	protected ImgButtonItem lookupArchivioButton;
	
	public static final DateTimeFormat anno_date_format = DateTimeFormat.getFormat("yyyy");
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(10);
		mDynamicForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "*");	

		idUdItem = new HiddenItem("idUd");
				
		mostraASinistraItem = new ImgButtonItem("mostraASinistra", "buttons/showMenu.png", "Mostra a sinistra");
		mostraASinistraItem.setColSpan(1);
		mostraASinistraItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageOnClickMostraDocumento("sinistra");
			}
		});
		
		mostraADestraItem = new ImgButtonItem("mostraADestraItem", "buttons/hideMenu.png", "Mostra a destra");
		mostraADestraItem.setColSpan(1);
		mostraADestraItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				manageOnClickMostraDocumento("destra");
			}
		});	
		
		CustomValidator attoRiferimentoASistemaRequiredValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(((ElencoDocumentiDaConfrontareItem)getItem()).isRequiredAttoRiferimento()) {					
					String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
					String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
					String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";
					return !"".equals(sigla) && !"".equals(numero) && !"".equals(anno);
				}
				return true;
			}
		};
		attoRiferimentoASistemaRequiredValidator.setErrorMessage("Estremi di registrazione obbligatori");	
		
		CustomValidator attoRiferimentoASistemaEsistenteValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
				String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
				String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";					
				if(!"".equals(sigla) && !"".equals(numero) && !"".equals(anno)) {
					String idUd = idUdItem.getValue() != null ? String.valueOf(idUdItem.getValue()) : null;
					return idUd != null && !"".equals(idUd);
				}
				return true;
			}
		};
		attoRiferimentoASistemaEsistenteValidator.setErrorMessage("Atto non presente in Auriga");			
		
		siglaItem = new ExtendedTextItem("sigla", "Sigla");
		siglaItem.setWidth(100);
		siglaItem.setRowSpan(3);
		siglaItem.setColSpan(1);
		siglaItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", "");
				recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUd) {
						if(idUd != null && !"".equals(idUd)) {
							event.getForm().setValue("idUd", idUd);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});
		
		siglaItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				pulisciIdUd();
				
			}
		});
		
		numeroItem = new ExtendedNumericItem("numero", "N.", false);
//		numeroItem.setValidators(attoRiferimentoNonASistemaRequiredValidator);
		numeroItem.setRowSpan(3);
		numeroItem.setColSpan(1);
		numeroItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", "");
				recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUd) {
						if(idUd != null && !"".equals(idUd)) {
							event.getForm().setValue("idUd", idUd);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});	
		
		numeroItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				pulisciIdUd();
				
			}
		});
		
		annoItem = new AnnoItem("anno", "/");
		annoItem.setRowSpan(3);
		annoItem.setColSpan(1);
		annoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				event.getForm().clearErrors(true);
				event.getForm().setValue("idUd", "");
				recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
					
					@Override
					public void execute(String idUd) {
						if(idUd != null && !"".equals(idUd)) {
							event.getForm().setValue("idUd", idUd);							
						}
						event.getForm().markForRedraw();
					}
					
					@Override
					public void manageError() {
						event.getForm().markForRedraw();
					}
				});
			}
		});
		
		annoItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				pulisciIdUd();
				
			}
		});

		
		ImgButtonItem visualizzaDettButton = new ImgButtonItem("visualizzaDettButton", "buttons/detail.png", "Visualizza dettaglio atto");
		visualizzaDettButton.setAlwaysEnabled(true);
		visualizzaDettButton.setEndRow(false);
		visualizzaDettButton.setColSpan(1);
		visualizzaDettButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = idUdItem.getValue() != null ? String.valueOf(idUdItem.getValue()) : null;				
				String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
				String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
				String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUd);
				String title = "Dettaglio atto " + sigla + " " + numero + "/" + anno;
				new DettaglioRegProtAssociatoWindow(lRecord, title);
			}
		});		
		visualizzaDettButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String idUd = idUdItem.getValue() != null ? String.valueOf(idUdItem.getValue()) : null;
				String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
				String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
				String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
				if ((!"".equals(sigla)) && !"".equals(numero) && !"".equals(anno)) {
					return idUd != null && !"".equals(idUd);	
				}
				return false;
			}
		});
		
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Seleziona da archivio");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				ImportaDocumentiDaConfrontareLookupArchivio lookupArchivioPopup = new ImportaDocumentiDaConfrontareLookupArchivio(event.getForm().getValuesAsRecord(), "/");
				lookupArchivioPopup.show();
			}
		});	
		lookupArchivioButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true;
			}
		});
		
		mDynamicForm.setFields(idUdItem, siglaItem, numeroItem, annoItem, visualizzaDettButton, lookupArchivioButton, mostraASinistraItem, mostraADestraItem);

		addChild(mDynamicForm);

	}
	
	private void manageOnClickMostraDocumento(final String schermata) {
		final String idUd = mDynamicForm.getValueAsString("idUd");
		if (idUd == null && "".equals(idUd)) {
			recuperaIdUdAttoRiferimento(new ServiceCallback<String>() {
				
				@Override
				public void execute(String idUd) {
					if(idUd != null && !"".equals(idUd)) {
						mDynamicForm.setValue("idUd", idUd);
						mDynamicForm.markForRedraw();
						showMenu(idUd, schermata);
					} else {
						mDynamicForm.markForRedraw();
					}
				}
			});
		} else {
			showMenu(idUd, schermata);
		}
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(true);
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	public void setFormValuesFromRecordAggiungiDocumento(Record record) {
		String idUd = record.getAttribute("idUd");
		String siglaProposta = record.getAttribute("siglaProtocollo");
		String numeroProposta = record.getAttribute("nroProtocollo");
		String annoProposta = record.getAttribute("annoProtocollo");
		
		mDynamicForm.setValue("idUd", idUd);
		mDynamicForm.setValue("sigla", siglaProposta);
		mDynamicForm.setValue("numero", numeroProposta);
		mDynamicForm.setValue("anno", annoProposta);
	}
	
	public boolean isValid() {
		String idUd = mDynamicForm.getValueAsString("idUd");
		return idUd != null && !"".equalsIgnoreCase(idUd);
	}
	
	public String getIdUdDocumento() {
		if (mDynamicForm != null) {
			return mDynamicForm.getValueAsString("idUd");
		} else {
			return null;
		}
	}
	
	public void recuperaIdUdAttoRiferimento(final ServiceCallback<String> callback) {
		String sigla = siglaItem.getValueAsString() != null ? siglaItem.getValueAsString() : "";
		String numero = numeroItem.getValueAsString() != null ? numeroItem.getValueAsString() : "";
		String anno = annoItem.getValueAsString() != null ? annoItem.getValueAsString() : "";									
		if (!"".equals(sigla) && !"".equals(numero) && !"".equals(anno)) {
			Record lRecord = new Record();		
			lRecord.setAttribute("sigla", sigla);
			lRecord.setAttribute("numero", numero);
			lRecord.setAttribute("anno", anno);			
			final GWTRestDataSource lNuovaPropostaAtto2CompletaDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			lNuovaPropostaAtto2CompletaDataSource.performCustomOperation("recuperaIdUdAttoRiferimento", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						if(callback != null) {
							callback.execute(response.getData()[0].getAttributeAsString("idUd"));
						} 
					} else {
						if(callback != null) {
							callback.execute(null);
						} 
					}
				}
			});
		}		
	}	
	
	private void pulisciIdUd() {
		mDynamicForm.setValue("idUd", "");
	}
	
	private void showMenu (String idUd, final String schermata) {
		GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
		lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", idUd);
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {					
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					final Record detailRecord = response.getData()[0];						
					Menu contextMenu = buildContextMenu(schermata, detailRecord);
					if(contextMenu.getItems() != null) {
						contextMenu.showContextMenu();
					}
				} else {
					// TODO ERROR
				}
			}
		});
	}
	
	private Menu buildContextMenu (String schermata, Record detailRecord) {
		Menu contextMenu = new Menu();
		
		MenuItem schedaDatiMenuItem = buildSchedaDatiMenuItem(schermata, detailRecord);
		if(schedaDatiMenuItem != null) {
			contextMenu.addItem(schedaDatiMenuItem);
		}
		
		MenuItem schedaDatiTestoAttoMenuItem = buildSchedaDatiTestoAttoMenu(schermata, detailRecord);
		if(schedaDatiTestoAttoMenuItem != null) {
			contextMenu.addItem(schedaDatiTestoAttoMenuItem);
		}
		
		MenuItem testoAttoMenuItem = buildTestoAttoMenu(schermata, detailRecord);
		if(testoAttoMenuItem != null) {
			contextMenu.addItem(testoAttoMenuItem);
		}
		
		MenuItem allegatiMenuItem = buildAllegatiMenu(schermata, detailRecord);
		if(allegatiMenuItem != null) {
			contextMenu.addItem(allegatiMenuItem);
		}
		
		return contextMenu;
	}
	
	private MenuItem buildSchedaDatiMenuItem(final String schermata, final Record detailRecord) {
		MenuItem schedaDatiMenuItem = new MenuItem ("Scheda dati");
		schedaDatiMenuItem.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				// Genero la scheda dati atto
				Layout.showWaitPopup("Generazione scheda dati in corso. Attendere...");
				GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lGwtRestDataSourceProtocollo.executecustom("generaSchedaDatiAttoPerConfronto", detailRecord, new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
						if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record schedaAttoTestoAtto = dsResponse.getData()[0];
							Record rec = new Record();
							rec.setAttribute("nomeFile", getPrefixRegNum(detailRecord) + schedaAttoTestoAtto.getAttributeAsString("nomeFile")); 
							rec.setAttribute("uri", schedaAttoTestoAtto.getAttributeAsString("uri")); 
							rec.setAttribute("remote", schedaAttoTestoAtto.getAttributeAsString("remoteUri")); 
							InfoFileRecord info = new InfoFileRecord(schedaAttoTestoAtto.getAttributeAsObject("infoFile"));
							rec.setAttribute("infoFile", info); 
							rec.setAttribute("recordType", "FileToExtractBean"); 
							((ElencoDocumentiDaConfrontareItem) getItem()).visualizzaDocumento(rec, schermata);
						}
						Layout.hideWaitPopup();
					}
				});
			}
		});
		
		return schedaDatiMenuItem;
	}
	
	private MenuItem buildSchedaDatiTestoAttoMenu(String schermata, Record detailRecord) {
		MenuItem schedaDatiTestoAttoMenuItem = new MenuItem("Scheda dati con testo atto");
		
		Menu fileAssociatiSubMenu = new Menu();
		fileAssociatiSubMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");		
				
		//Testo atto senza omissis
		if((detailRecord.getAttributeAsString("uriFilePrimario")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && (detailRecord.getAttributeAsString("uriFilePrimarioOmissis")==null || "".equals(detailRecord.getAttributeAsString("uriFilePrimarioOmissis")))) {
			
			MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Scheda dati con testo atto - versione integrale");
			buildSchedaDatiTestoAttoMenuItem(detailRecord, fileAllegatoIntegraleMenuItem, true, schermata);
			return fileAllegatoIntegraleMenuItem;
		}
		
		//Entrambi versioni di testo atto
		else if((detailRecord.getAttributeAsString("uriFilePrimario")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && (detailRecord.getAttributeAsString("uriFilePrimarioOmissis")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimarioOmissis")))) {
			
			//Allegato integrale
			MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Versione integrale");
			
			buildSchedaDatiTestoAttoMenuItem(detailRecord, fileAllegatoIntegraleMenuItem, true, schermata);
			fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
			
			//Alegato omissis
			MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Versione pubblicabile/pubblicata");
			
			buildSchedaDatiTestoAttoMenuItem(detailRecord, fileAllegatoOmissisMenuItem, false, schermata);
			fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
		}
		
		//Testo atto solo omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario")==null || "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && (detailRecord.getAttributeAsString("uriFilePrimarioOmissis")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimarioOmissis")))) {
			
			MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Scheda dati con testo atto - versione pubblicabile/pubblicata");
			
			buildSchedaDatiTestoAttoMenuItem(detailRecord, fileAllegatoOmissisMenuItem, false, schermata);
			return fileAllegatoOmissisMenuItem;
		}
		
		schedaDatiTestoAttoMenuItem.setSubmenu(fileAssociatiSubMenu);
		return schedaDatiTestoAttoMenuItem;
	}
	
	private MenuItem buildTestoAttoMenu(String schermata, Record detailRecord) {
		MenuItem testAttoMenuItem = new MenuItem("Testo atto");
		
		Menu fileAssociatiSubMenu = new Menu();
		fileAssociatiSubMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");		
				
		//Testo atto senza omissis
		if((detailRecord.getAttributeAsString("uriFilePrimario")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && (detailRecord.getAttributeAsString("uriFilePrimarioOmissis")==null || "".equals(detailRecord.getAttributeAsString("uriFilePrimarioOmissis")))) {
			
			MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Testo atto - versione integrale");
			buildTestoAttoMenuItem(detailRecord, fileAllegatoIntegraleMenuItem, true, schermata);
			return fileAllegatoIntegraleMenuItem;
		}
		
		//Entrambi versioni di testo atto
		else if((detailRecord.getAttributeAsString("uriFilePrimario")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && (detailRecord.getAttributeAsString("uriFilePrimarioOmissis")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimarioOmissis")))) {
			
			//Allegato integrale
			MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Versione integrale");
			
			buildTestoAttoMenuItem(detailRecord, fileAllegatoIntegraleMenuItem, true, schermata);
			fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
			
			//Alegato omissis
			MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Versione pubblicabile/pubblicata");
			
			buildTestoAttoMenuItem(detailRecord, fileAllegatoOmissisMenuItem, false, schermata);
			fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
		}
		
		//Testo atto solo omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario")==null || "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && (detailRecord.getAttributeAsString("uriFilePrimarioOmissis")!=null && !"".equals(detailRecord.getAttributeAsString("uriFilePrimarioOmissis")))) {
			
			MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Testo atto - versione pubblicabile/pubblicata");
			
			buildTestoAttoMenuItem(detailRecord, fileAllegatoOmissisMenuItem, false, schermata);
			return fileAllegatoOmissisMenuItem;
		}
		
		testAttoMenuItem.setSubmenu(fileAssociatiSubMenu);
		return testAttoMenuItem;
	}
	
	private MenuItem buildAllegatiMenu(String schermata, Record detailRecord) {
		MenuItem allegatiMenuItem = new MenuItem("Allegati");
		
		Menu fileAssociatiSubMenu = new Menu();
		fileAssociatiSubMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
		
		//Aggiungo al menu gli allegati
		RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
		
		if (listaAllegati != null) {
			for (int n = 0; n < listaAllegati.getLength(); n++) {
				final int nroAllegato = n;
				final Record allegatoRecord = listaAllegati.get(n);
				
				boolean flgParteDispositivo = allegatoRecord.getAttribute("flgParteDispositivo") != null && "true".equals(allegatoRecord.getAttribute("flgParteDispositivo"));					
				
				//Allegato senza omissis
				if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato, true, schermata);
					fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
				}
				
				//Entrambi versioni di allegati
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					//Allegato integrale
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. integrale) - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					if(flgParteDispositivo) {
						fileAllegatoIntegraleMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato,true, schermata);
					fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
					
					//Alegato omissis
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false, schermata);
					fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
				}
				
				//Allegato solo omissis
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N° " + allegatoRecord.getAttributeAsString("numeroProgrAllegato") + " (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					if(flgParteDispositivo) {
						fileAllegatoOmissisMenuItem.setIcon("attiInLavorazione/parteIntegrante.png");
					}
					buildAllegatiMenuItem(detailRecord, allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false, schermata);
					fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
				}
			}
		} else {
			return null;
		}
		allegatiMenuItem.setSubmenu(fileAssociatiSubMenu);
		return allegatiMenuItem;
	}
	
	private void buildTestoAttoMenuItem(final Record detailRecord, MenuItem testoAttoMenuItem, final boolean testoAttoIntegrale, final String layoutSxDx) {
		
		
		InfoFileRecord lInfoFileRecord;
		//se è un allegato integrale
		if(testoAttoIntegrale) {
			lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFilePrimario"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFilePrimarioOmissis"));
		}
		
		testoAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						Record rec = new Record();
						//se è un allegato integrale
						if(testoAttoIntegrale) {
							rec.setAttribute("nomeFile", getPrefixRegNum(detailRecord) + "Versione integrale.pdf");
							rec.setAttribute("uri", detailRecord.getAttributeAsString("uriFilePrimario")); 
							rec.setAttribute("remote", detailRecord.getAttributeAsString("remoteUriFilePrimario")); 
							InfoFileRecord info = new InfoFileRecord(detailRecord.getAttributeAsObject("infoFilePrimario"));
							rec.setAttribute("infoFile", info); 
							rec.setAttribute("recordType", "FileToExtractBean"); 
							((ElencoDocumentiDaConfrontareItem) getItem()).visualizzaDocumento(rec, layoutSxDx);
						}
						//versione con omissis
						else{
							rec.setAttribute("nomeFile", getPrefixRegNum(detailRecord) + "Versione pubblicabile/pubblicata.pdf");
							rec.setAttribute("uri", detailRecord.getAttributeAsString("uriFilePrimarioOmissis")); 
							rec.setAttribute("remote", detailRecord.getAttributeAsString("remoteUriFilePrimarioOmissis")); 
							InfoFileRecord info = new InfoFileRecord(detailRecord.getAttributeAsObject("infoFilePrimarioOmissis"));
							rec.setAttribute("infoFile", info); 
							rec.setAttribute("recordType", "FileToExtractBean"); 
							((ElencoDocumentiDaConfrontareItem) getItem()).visualizzaDocumento(rec, layoutSxDx);
						}
					}
				});
		testoAttoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
	}
	
	private void buildSchedaDatiTestoAttoMenuItem(final Record detailRecord, MenuItem SchedaDatiTestoAttoMenuItem, final boolean testoAttoIntegrale, final String layoutSxDx) {
		
		SchedaDatiTestoAttoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				Layout.showWaitPopup("Generazione scheda dati e testo atto in corso. Attendere...");
				detailRecord.setAttribute("flgMostraDatiSensibili", testoAttoIntegrale); 
				// Genero la scheda dati atto con testo atto
				GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lGwtRestDataSourceProtocollo.executecustom("generaSchedaDatiAttoConTestoAttoPerConfronto", detailRecord, new DSCallback() {
					@Override
					public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
						if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record schedaAttoTestoAtto = dsResponse.getData()[0];
							Record rec = new Record();
							rec.setAttribute("nomeFile", getPrefixRegNum(detailRecord) + schedaAttoTestoAtto.getAttributeAsString("nomeFile")); 
							rec.setAttribute("uri", schedaAttoTestoAtto.getAttributeAsString("uri")); 
							rec.setAttribute("remote", schedaAttoTestoAtto.getAttributeAsString("remoteUri")); 
							InfoFileRecord info = new InfoFileRecord(schedaAttoTestoAtto.getAttributeAsObject("infoFile"));
							rec.setAttribute("infoFile", info); 
							rec.setAttribute("recordType", "FileToExtractBean"); 
							((ElencoDocumentiDaConfrontareItem) getItem()).visualizzaDocumento(rec, layoutSxDx);
						}
						Layout.hideWaitPopup();
					}
				});
			}
		});
	}
	
	private void buildAllegatiMenuItem(final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale, final String layoutSxDx) {
		
		InfoFileRecord lInfoFileRecord;
		//se è un allegato integrale
		if(allegatoIntegrale) {
			lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
		}
		
		fileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						Record rec = new Record();
						//se è un allegato integrale
						if(allegatoIntegrale) {
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							rec.setAttribute("nomeFile", getPrefixRegNum(detailRecord) + allegatoRecord.getAttributeAsString("nomeFileAllegato")); 
							rec.setAttribute("uri", allegatoRecord.getAttributeAsString("uriFileAllegato")); 
							rec.setAttribute("remote", allegatoRecord.getAttributeAsString("remoteUri")); 
							InfoFileRecord info = new InfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
							rec.setAttribute("infoFile", info); 
							rec.setAttribute("recordType", "FileToExtractBean"); 
							((ElencoDocumentiDaConfrontareItem) getItem()).visualizzaDocumento(rec, layoutSxDx);
						}
						//versione con omissis
						else{
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							rec.setAttribute("nomeFile", getPrefixRegNum(detailRecord) + allegatoRecord.getAttributeAsString("nomeFileOmissis")); 
							rec.setAttribute("uri", allegatoRecord.getAttributeAsString("uriFileOmissis")); 
							rec.setAttribute("remote", allegatoRecord.getAttributeAsString("remoteUriOmissis")); 
							InfoFileRecord info = new InfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
							rec.setAttribute("infoFile", info); 
							rec.setAttribute("recordType", "FileToExtractBean"); 
							((ElencoDocumentiDaConfrontareItem) getItem()).visualizzaDocumento(rec, layoutSxDx);
						}
					}
				});
		fileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
	}
	
	public String getPrefixRegNum(Record nuovaPropostaAtto2CompletaDetailRecord) {
		if (nuovaPropostaAtto2CompletaDetailRecord.getAttribute("numeroRegistrazione") != null && !"".equalsIgnoreCase(nuovaPropostaAtto2CompletaDetailRecord.getAttribute("numeroRegistrazione"))) {
			Date dataRegistrazione = nuovaPropostaAtto2CompletaDetailRecord.getAttribute("dataRegistrazione") != null ? nuovaPropostaAtto2CompletaDetailRecord.getAttributeAsDate("dataRegistrazione") : null;
			String annoRegistrazione = dataRegistrazione != null ? anno_date_format.format(dataRegistrazione) : nuovaPropostaAtto2CompletaDetailRecord.getAttribute("annoRegistrazione");
			return nuovaPropostaAtto2CompletaDetailRecord.getAttribute("siglaRegistrazione") + "_" + nuovaPropostaAtto2CompletaDetailRecord.getAttribute("numeroRegistrazione") + "_" + annoRegistrazione + "_";
		} else if (nuovaPropostaAtto2CompletaDetailRecord.getAttribute("numeroRegProvvisoria") != null && !"".equalsIgnoreCase(nuovaPropostaAtto2CompletaDetailRecord.getAttribute("numeroRegProvvisoria"))) {
			Date dataRegistrazioneProvvisoria = nuovaPropostaAtto2CompletaDetailRecord.getAttribute("dataRegProvvisoria") != null ? nuovaPropostaAtto2CompletaDetailRecord.getAttributeAsDate("dataRegProvvisoria") : null;
			String annoRegistrazioneProvvisoria = dataRegistrazioneProvvisoria != null ? anno_date_format.format(dataRegistrazioneProvvisoria) : nuovaPropostaAtto2CompletaDetailRecord.getAttribute("annoRegProvvisoria");
			return nuovaPropostaAtto2CompletaDetailRecord.getAttribute("siglaRegProvvisoria") + "_" + nuovaPropostaAtto2CompletaDetailRecord.getAttribute("numeroRegProvvisoria") + "_" + annoRegistrazioneProvvisoria + "_";
		}
		return "";
	}
	
	public class ImportaDocumentiDaConfrontareLookupArchivio extends LookupArchivioPopup {

		public ImportaDocumentiDaConfrontareLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona il documento da confrontare";
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaAggiungiDocumentoDaConfrontare();
		}

		@Override
		public void manageLookupBack(Record record) {
			super.manageOnCloseClick();
			Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
			RecordList lRecordListDocumentToImport = new RecordList();
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
			lRecordListDocumentToImport.add(lRecordToLoad);
			Record recordMassivo = new Record();
			recordMassivo.setAttribute("listaRecord", lRecordListDocumentToImport);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
			Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
			lGwtRestDataSource.performCustomOperation("getEstremiUnitaDocumentarie", recordMassivo, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
						for (int i = 0; i < resultRecordList.getLength(); i++) {
							Record resultRecord = resultRecordList.get(i);
							setFormValuesFromRecordAggiungiDocumento(resultRecord);
						}
					}
					Layout.hideWaitPopup();
				}
			}, new DSRequest());
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}
	
	public String getFinalitaAggiungiDocumentoDaConfrontare() {
		return "SEL_ATTI";
	}

}
