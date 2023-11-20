/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.archivio.ArchivioPopup;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.DettaglioFolderCustomWindow;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.toponomastica.LookupViarioPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ContenutoFoglioImportatoDetail extends CustomDetail {

	protected ContenutoFoglioImportatoDetail instance;
	protected ContenutoFoglioImportatoWindow window;

	protected DynamicForm hiddenForm;
	protected HiddenItem idFoglioItem;
	protected HiddenItem nrRigaItem;
	protected HiddenItem nomeFileItem;

	protected String uri;
	protected String displayFilename;
	protected String dimensioneFile;
	protected String idUD;
	protected String idUDCapofila;
	protected String idFolder;
	protected String idFoglioRecCapofila;
	protected String nroRigaRecCapofila;
	protected String annoProt;
	protected String nroProt;
	
	protected DynamicForm parametriForm;

	public ContenutoFoglioImportatoDetail(String nomeEntita) {
		this(nomeEntita, null);
	}

	public ContenutoFoglioImportatoDetail(String nomeEntita, Record record) {
		super(nomeEntita);

		instance = this;

		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
		hiddenForm.setWidth("100%");
		hiddenForm.setHeight("5");

		idFoglioItem = new HiddenItem("idFoglio");
		nrRigaItem = new HiddenItem("nrRiga");
		nomeFileItem = new HiddenItem("nomeFile");

		hiddenForm.setItems(idFoglioItem, nrRigaItem, nomeFileItem);

		parametriForm = new DynamicForm();
		parametriForm.setValuesManager(vm);
		parametriForm.setWidth("100%");
		parametriForm.setHeight("5");
		parametriForm.setPadding(5);
		parametriForm.setNumCols(6);
		parametriForm.setColWidths(10, 100, 10, 100, 10, "*");
		parametriForm.setWrapItemTitles(false);

		if (record != null) {
			List<FormItem> parametriItems = new ArrayList<FormItem>();
			RecordList parametriRiga = record.getAttributeAsRecordList("parametriRiga");
			for (int i = 0; i < parametriRiga.getLength(); i++) {
				Record param = parametriRiga.get(i);
				parametriItems.addAll(buildParametroCasellaFormItem(param));
			}
			parametriForm.setFields(parametriItems.toArray(new FormItem[0]));
		}
		setMembers(hiddenForm, parametriForm);
	}

	public ArrayList<FormItem> buildParametroCasellaFormItem(final Record param) {
		ArrayList<FormItem> listItems = new ArrayList<FormItem>();
		
		if (param.getAttributeAsString("nome") != null && !"".equalsIgnoreCase(param.getAttributeAsString("nome"))
				&& param.getAttributeAsString("nome").startsWith("#")) {
			if ("#URI".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				uri = param.getAttributeAsString("valore");
			} else if ("#DisplayFilename".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				displayFilename = param.getAttributeAsString("valore");
			} else if ("#DimensioneFile".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				dimensioneFile = param.getAttributeAsString("valore");
			} else if ("#IdUD".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				idUD = param.getAttributeAsString("valore");
			} else if ("#IdUDCapofila".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				idUDCapofila = param.getAttributeAsString("valore");
			} else if ("#IdFolder".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				idFolder = param.getAttributeAsString("valore");
			} else if ("#IdFoglioRecCapofila".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				idFoglioRecCapofila = param.getAttributeAsString("valore");
			} else if ("#AnnoProt".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				annoProt = param.getAttributeAsString("valore");
			} else if ("#NroProt".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				nroProt = param.getAttributeAsString("valore");
			} else if ("#NroRigaRecCapofila".equalsIgnoreCase(param.getAttributeAsString("nome"))) {
				nroRigaRecCapofila = param.getAttributeAsString("valore");
			}
		} else {
			
			FormItem item = null;
			FormItem lookupIndirizzoButton = null;
			
			if (param.getAttribute("tipo") != null) {
				if (param.getAttribute("tipo").equalsIgnoreCase("S")) {
					item = new TextItem(param.getAttribute("nome"), param.getAttribute("titolo"));				
					// Se la label e' "Cod. via" mostro il bottone per accedere al viario
					if (param.getAttribute("titolo") !=null && param.getAttribute("titolo").replaceAll("\\s", "").toUpperCase().startsWith("COD.VIA")){
						lookupIndirizzoButton = new ImgButtonItem("lookupIndirizzoButton_" + param.getAttribute("nome"), "lookup/indirizzo.png", "Seleziona indirizzo dal viario");
						lookupIndirizzoButton.setColSpan(1);
						lookupIndirizzoButton.setEndRow(true);
						lookupIndirizzoButton.setShowIfCondition(new FormItemIfFunction() {

							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {
								return true;
							}
						});
						lookupIndirizzoButton.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {
								manageClickLookupIndirizzoButton(param.getAttribute("nome"));
							}
						});
						
					}	
				} else if (param.getAttribute("tipo").equalsIgnoreCase("N")) {
					item = new NumericItem(param.getAttribute("nome"), param.getAttribute("titolo"), false);
				} else if (param.getAttribute("tipo").equalsIgnoreCase("D")) {
					item = new DateItem(param.getAttribute("nome"), param.getAttribute("titolo"));
				}
			}

			if (item == null) {
				item = new TextItem(param.getAttribute("nome"), param.getAttribute("titolo"));
			}

			item.setRequired(param.getAttribute("flgObblig") != null && param.getAttribute("flgObblig").equals("1"));
			if (item instanceof SelectItem) {
				((SelectItem) item).setAllowEmptyValue(!item.getRequired());
			}
			
			item.setStartRow(true);
			listItems.add(item);
			
			if(lookupIndirizzoButton != null){
				listItems.add(lookupIndirizzoButton);
			}
			   
			if (param.getAttribute("infoCompilazione") != null && !"".equalsIgnoreCase(param.getAttribute("infoCompilazione"))) {
				ImgButtonItem infoCompilazioneItem = new ImgButtonItem("", "message/information.png", "Info compilazione");
				infoCompilazioneItem.setColSpan(1);
				infoCompilazioneItem.setIconWidth(16);
				infoCompilazioneItem.setIconHeight(16);
				infoCompilazioneItem.setIconVAlign(VerticalAlignment.BOTTOM);
				infoCompilazioneItem.setAlign(Alignment.LEFT);
				infoCompilazioneItem.setWidth(16);
				infoCompilazioneItem.setRedrawOnChange(true);
				infoCompilazioneItem.setAlwaysEnabled(true);
				infoCompilazioneItem.addIconClickHandler(new IconClickHandler() {

					@Override
					public void onIconClick(IconClickEvent event) {
						String infoPerCompilaizone = param.getAttributeAsString("infoCompilazione");
						SC.say(infoPerCompilaizone);
					}
				});
				infoCompilazioneItem.setShowIfCondition(new FormItemIfFunction() {
					
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {						
						return true;
					}
				});

				listItems.add(infoCompilazioneItem);
			}

			if (param.getAttribute("azioni") != null && !"".equalsIgnoreCase(param.getAttribute("azioni"))) {
				
				String[] azioni = param.getAttribute("azioni").split(";");
				
				for (String azione : azioni){

					if ("SHOW_FILE".equalsIgnoreCase(azione)) {
						ImgButtonItem showPreviewItem = new ImgButtonItem("", "file/preview.png", "Anteprima file");
						showPreviewItem.setColSpan(1);
						showPreviewItem.setIconWidth(16);
						showPreviewItem.setIconHeight(16);
						showPreviewItem.setIconVAlign(VerticalAlignment.BOTTOM);
						showPreviewItem.setAlign(Alignment.LEFT);
						showPreviewItem.setWidth(16);
						showPreviewItem.setRedrawOnChange(true);
						showPreviewItem.setAlwaysEnabled(true);
						showPreviewItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {
								Record record = new Record();
								record.setAttribute("uri", uri);
								record.setAttribute("displayFilnename", displayFilename);

								final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ContenutoFoglioImportatoDataSource");
								lGwtRestDataSource.executecustom("calcolaInfoFile", record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record record = response.getData()[0];
											InfoFileRecord infoFile = new InfoFileRecord(record);
											PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", displayFilename);
										}
									}
								});
							}
						});
						showPreviewItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {								
								return uri != null && !"".equalsIgnoreCase(uri);
							}
						});

						listItems.add(showPreviewItem);

						ImgButtonItem downloadItem = new ImgButtonItem("", "file/download_manager.png", "Download file");
						downloadItem.setColSpan(1);
						downloadItem.setIconWidth(16);
						downloadItem.setIconHeight(16);
						downloadItem.setIconVAlign(VerticalAlignment.BOTTOM);
						downloadItem.setAlign(Alignment.LEFT);
						downloadItem.setWidth(16);
						downloadItem.setRedrawOnChange(true);
						downloadItem.setAlwaysEnabled(true);
						downloadItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {
								Record lRecord = new Record();
								lRecord.setAttribute("displayFilename", displayFilename);
								lRecord.setAttribute("uri", uri);
								lRecord.setAttribute("sbustato", "false");
								lRecord.setAttribute("remoteUri", "true");
								DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
							}
						});
						downloadItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {								
								return uri != null && !"".equalsIgnoreCase(uri);
							}
						});

						listItems.add(downloadItem);

					} else if ("SHOW_DETT_UD_CAPOFILA".equalsIgnoreCase(azione)) {
						ImgButtonItem showDettUdCapofilaItem = new ImgButtonItem("", "buttons/detail.png", "Dettaglio protocollo");
						showDettUdCapofilaItem.setColSpan(1);
						showDettUdCapofilaItem.setIconWidth(16);
						showDettUdCapofilaItem.setIconHeight(16);
						showDettUdCapofilaItem.setIconVAlign(VerticalAlignment.BOTTOM);
						showDettUdCapofilaItem.setAlign(Alignment.LEFT);
						showDettUdCapofilaItem.setWidth(16);
						showDettUdCapofilaItem.setRedrawOnChange(true);
						showDettUdCapofilaItem.setAlwaysEnabled(true);
						showDettUdCapofilaItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {
								manageDettaglioUD(idUDCapofila);
							}
						});
						showDettUdCapofilaItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {								
								return idUDCapofila != null && !"".equalsIgnoreCase(idUDCapofila);
							}
						});

						listItems.add(showDettUdCapofilaItem);

					} else if ("SHOW_DETT_FASC".equalsIgnoreCase(azione)) {
						ImgButtonItem showDettFascItem = new ImgButtonItem("", "buttons/visDocFasc.png", "Dettaglio fascicolo");
						showDettFascItem.setColSpan(1);
						showDettFascItem.setIconWidth(16);
						showDettFascItem.setIconHeight(16);
						showDettFascItem.setIconVAlign(VerticalAlignment.BOTTOM);
						showDettFascItem.setAlign(Alignment.LEFT);
						showDettFascItem.setWidth(16);
						showDettFascItem.setRedrawOnChange(true);
						showDettFascItem.setAlwaysEnabled(true);
						showDettFascItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {							
								manageDettaglioFascicolo();
							}
						});
						showDettFascItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {								
								return idFolder != null && !"".equalsIgnoreCase(idFolder);
							}
						});

						listItems.add(showDettFascItem);

					} else if ("SHOW_DETT_UD".equalsIgnoreCase(azione)) {
						ImgButtonItem showDettUdItem = new ImgButtonItem("", "buttons/detail.png", "Dettaglio protocollo");
						showDettUdItem.setColSpan(1);
						showDettUdItem.setIconWidth(16);
						showDettUdItem.setIconHeight(16);
						showDettUdItem.setIconVAlign(VerticalAlignment.BOTTOM);
						showDettUdItem.setAlign(Alignment.LEFT);
						showDettUdItem.setWidth(16);
						showDettUdItem.setRedrawOnChange(true);
						showDettUdItem.setAlwaysEnabled(true);
						showDettUdItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {
								manageDettaglioUD(idUD);
							}
						});
						showDettUdItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {								
								return idUD != null && !"".equalsIgnoreCase(idUD);
							}
						});

						listItems.add(showDettUdItem);
					} else if ("VAI_A_RIGA_CAPOFILA".equalsIgnoreCase(azione)) {
						ImgButtonItem showRigaCapofilaItem = new ImgButtonItem("", "menu/contenuto_foglio_importato.png", "Vai alla riga del PG capofila");
						showRigaCapofilaItem.setColSpan(1);
						showRigaCapofilaItem.setIconWidth(16);
						showRigaCapofilaItem.setIconHeight(16);
						showRigaCapofilaItem.setIconVAlign(VerticalAlignment.BOTTOM);
						showRigaCapofilaItem.setAlign(Alignment.LEFT);
						showRigaCapofilaItem.setWidth(16);
						showRigaCapofilaItem.setRedrawOnChange(true);
						showRigaCapofilaItem.setAlwaysEnabled(true);
						showRigaCapofilaItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {							
								manageDettRigaCapofila();
							}
						});
						showRigaCapofilaItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {
								return ((nroRigaRecCapofila != null && !"".equals(nroRigaRecCapofila)) && (idFoglioRecCapofila != null && !"".equals(idFoglioRecCapofila)));
							}
						});

						listItems.add(showRigaCapofilaItem);
					} else if ("RICERCA_PROT".equalsIgnoreCase(azione)) {
						ImgButtonItem showRicercaProtItem = new ImgButtonItem("", "lookup/archivio.png", "Cerca protocollo");
						showRicercaProtItem.setColSpan(1);
						showRicercaProtItem.setIconWidth(16);
						showRicercaProtItem.setIconHeight(16);
						showRicercaProtItem.setIconVAlign(VerticalAlignment.BOTTOM);
						showRicercaProtItem.setAlign(Alignment.LEFT);
						showRicercaProtItem.setWidth(16);
						showRicercaProtItem.setRedrawOnChange(true);
						showRicercaProtItem.setAlwaysEnabled(true);
						showRicercaProtItem.addIconClickHandler(new IconClickHandler() {

							@Override
							public void onIconClick(IconClickEvent event) {
								manageCercaUD();
							}
						});
						showRicercaProtItem.setShowIfCondition(new FormItemIfFunction() {
							
							@Override
							public boolean execute(FormItem item, Object value, DynamicForm form) {								
								return ((nroProt != null && !"".equalsIgnoreCase(nroProt)) && (annoProt != null && !"".equalsIgnoreCase(annoProt)));
							}
						});

						listItems.add(showRicercaProtItem);
					}
				}
			}
		}

		return listItems;
	}

	@Override
	public void editRecord(Record record) {

		Record values = new Record();
		values.setAttribute("nrRiga", record.getAttribute("nrRiga"));
		values.setAttribute("idFoglio", record.getAttribute("idFoglio"));
		values.setAttribute("nomeFile", record.getAttribute("nomeFile"));

		RecordList parametriCasella = record.getAttributeAsRecordList("parametriRiga");

		if (parametriCasella != null) {
			for (int i = 0; i < parametriCasella.getLength(); i++) {
				Record param = parametriCasella.get(i);
				values.setAttribute(param.getAttribute("nome"), param.getAttribute("valore"));
			}
		}
		// ATTENZIONE: alcuni dei nomi dei campi a maschera contengono il punto e
		// l'editRecord non
		// funziona correttamente, perciò devo settare manualmente i valori sugli item
		// super.editRecord(values);
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				item.setValue(values.getAttribute(item.getName()));
			}
		}
	}
	
	public ContenutoFoglioImportatoWindow getWindow() {
		return window;
	}

	public void setWindow(ContenutoFoglioImportatoWindow window) {
		this.window = window;
	}

	public Record getRecordToSave() {
		Record record = new Record();
		record.setAttribute("idFoglio", hiddenForm.getValue("idFoglio"));
		record.setAttribute("nrRiga", hiddenForm.getValue("nrRiga"));
		record.setAttribute("nomeFile", hiddenForm.getValue("nomeFile"));
		RecordList parametriRiga = new RecordList();
		for (FormItem item : parametriForm.getFields()) {
			Record param = new Record();
			param.setAttribute("nome", item.getName());
			param.setAttribute("valore", parametriForm.getValue(item.getName()));
			parametriRiga.add(param);
		}
		record.setAttribute("parametriRiga", parametriRiga);
		return record;
	}
	
	private void manageCercaUD() {
		Record recordToSearch = new Record();
		recordToSearch.setAttribute("nroProt", nroProt);
		recordToSearch.setAttribute("annoProt", annoProt);
		ArchivioPopup lArchivioPopup = new ArchivioPopup(recordToSearch, "RICERCA DOCUMENTI E FASCICOLI");
		lArchivioPopup.show();
	}
	
	private void manageDettaglioUD(String idUd) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		new DettaglioRegProtAssociatoWindow(lRecord, "Dettaglio prot. N° " + nroProt + "/" + annoProt);
	}
	
	private void manageDettaglioFascicolo() {
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT); 			  
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUdFolder", idFolder);
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					Record lRecord = response.getData()[0];				
					DettaglioFolderCustomWindow lDettaglioFolderCustomWindow = new DettaglioFolderCustomWindow(lRecord);
					lDettaglioFolderCustomWindow.show();
				}
			}
		});
	}
	
	private void manageDettRigaCapofila() {
		Record record = new Record();
		record.setAttribute("nrRiga", nroRigaRecCapofila);
		record.setAttribute("idFoglio", idFoglioRecCapofila);
		final GWTRestDataSource gWTRestDataSource = ((GWTRestDataSource)getDataSource());
		gWTRestDataSource.getData(record, new DSCallback() {
		
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record detailRecord = response.getData()[0];			
					ContenutoFoglioImportatoWindow lContenutoFoglioImportatoWindow = new ContenutoFoglioImportatoWindow(detailRecord);
					lContenutoFoglioImportatoWindow.show();
				}
			}
		});
	}
	
	
	private void manageClickLookupIndirizzoButton(String codiceViarioItemName) {
		LookupViario lookupViario = new LookupViario(codiceViarioItemName, null);
		lookupViario.show();
	}
	
	
	/**
	 * 
	 * Lookup VIARIO - CIVICI
	 *
	 */

	public class LookupViario extends LookupViarioPopup {
		
		private String codiceViarioItemName;

		public LookupViario(String codiceViarioItemName, String indirizzo) {
			super(indirizzo, null, true);
			this.codiceViarioItemName = codiceViarioItemName;
		}
		
		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordViario(record, codiceViarioItemName);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}
	
	public void setFormValuesFromRecordViario(Record record, String codiceViarioItemName) {
		String codiceViarioToponimo = record.getAttribute("codiceViarioToponimo");
		if (codiceViarioToponimo != null && !"".equals(codiceViarioToponimo)) {
			if(codiceViarioItemName != null && !"".equals(codiceViarioItemName)) {
				parametriForm.setValue(codiceViarioItemName, codiceViarioToponimo);
			}
		}
	}	
}