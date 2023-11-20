/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.CallbackGenericFunction;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.ListaAttributoDinamicoListaItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DatiContenutiTabellaPopup extends ModalWindow {
	
	private DatiContenutiTabellaPopup window;	
	private DynamicForm form; 
	private ValuesManager vm;
	private ListaAttributoDinamicoListaItem listaContenutiTabellaGridItem;
	private RecordList datiDettLista;
	private RecordList filtriColonneLista;
	private RecordList defaultFiltriColonneLista;
	private RecordList lastModifiedFiltriColonneLista;
	private String idContenuto;
	private String flgRecordFuoriPubbl;
	private String nroRecTotali;
	protected ImgButtonItem importaXlsButton;
	protected ImgButtonItem filtraDatiTabellaButton;
	
	protected Img filtroAttivoImg;
	protected Label nroRecordLabel;
	
	protected Button chiudiButton;
	private String mode;
	
	
	public DatiContenutiTabellaPopup(Record attrLista, RecordList datiDettLista, RecordList valoriAttrLista, String mode, String nroRecTotali) {
		
		super("dati_contenuti_tabella");
		
		this.datiDettLista = datiDettLista;
		this.flgRecordFuoriPubbl = attrLista.getAttribute("flgRecordFuoriPubbl");
		this.idContenuto = attrLista.getAttribute("idContenuto");
		this.nroRecTotali = nroRecTotali;
		
		window = this;
		this.vm = new ValuesManager();
		
		setMode(mode);
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setHeight(300);
		setWidth(1300);	
		setKeepInParentRect(true);		
		setTitle("Dati contenuti tabella");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(100, "*");
		form.setCellPadding(5);
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		
		form.setValuesManager(vm);
		
		listaContenutiTabellaGridItem = buildListaGridItem(attrLista, datiDettLista, valoriAttrLista);
		form.setItems(listaContenutiTabellaGridItem);
		
		addItem(form);		
		
		filtroAttivoImg = new Img("buttons/imbuto.png");
		// filtroAttivoImg.setAppImgDir("buttons/");
		filtroAttivoImg.setWidth(16);
		filtroAttivoImg.setHeight(16);
		filtroAttivoImg.setAlign(Alignment.CENTER);
		filtroAttivoImg.setValign(VerticalAlignment.CENTER);
		filtroAttivoImg.setPrompt(I18NUtil.getMessages().filtroAttivoImg_prompt());
		filtroAttivoImg.setVisible(false);

		nroRecordLabel = new Label();
		nroRecordLabel.setAlign(Alignment.CENTER);
		nroRecordLabel.setValign(VerticalAlignment.CENTER);
		nroRecordLabel.setWrap(false);		
		nroRecordLabel.setContents(I18NUtil.getMessages().nroRecordLabel_prefix() + ": " + nroRecTotali );
		
		ToolStrip bottomListToolStrip = new ToolStrip();
		bottomListToolStrip.setWidth100();
		bottomListToolStrip.setHeight(30);
		bottomListToolStrip.setBackgroundColor("transparent");
		bottomListToolStrip.setBackgroundImage("blank.png");
		bottomListToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		bottomListToolStrip.setBorder("0px");
		bottomListToolStrip.addFill(); // push all buttons to the right
		bottomListToolStrip.addMember(filtroAttivoImg);
		bottomListToolStrip.addSpacer(0);
		bottomListToolStrip.addMember(nroRecordLabel);
		bottomListToolStrip.addSpacer(0);
		
		addItem(bottomListToolStrip);
		
		// Bottoni		
		chiudiButton = new Button("Chiudi");   
		chiudiButton.setIcon("annulla.png");
		chiudiButton.setIconSize(16); 
		chiudiButton.setAutoFit(false);
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				window.markForDestroy();	
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(chiudiButton);	
		_buttons.setAutoDraw(false);
		
		addItem(_buttons);
		
		settingsMenu.removeItem(autoSearchMenuItem);
	}
	
	protected ListaAttributoDinamicoListaItem buildListaGridItem(final Record attrLista, final RecordList datiDettLista, RecordList valoriAttrLista) {
		CallbackGenericFunction callback = createCallback();
		ListaAttributoDinamicoListaItem listaDinamicaItem = new ListaAttributoDinamicoListaItem(attrLista, datiDettLista, callback) {
			
//			faccio override di createFieldsMap facendo tornare una mappa vuota che poi mi costruisco in aggiungiColonneNascosteSuGrid
//			in modo da esportare tutte le colonne, anche quelle nascoste, nell'ordine in cui vengono compilate nella maschera di dettaglio
//			prima venivano esportate nell'ordine a video nella lista e le colonne nascoste venivano messe in fondo facendo fallire un eventuale import
			@Override
			protected LinkedHashMap<String, String> createFieldsMap(Boolean includeXord) {
				LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();
				return mappa;
			}
			
//			faccio override di aggiungiColonneNascosteSuGrid per costruire l'export dell'excel prendendo i campi non dalla ListGrid ma da mappaColonne.
//			così facendo esporto tutte le colonne, anche quelle nascoste, nell'ordine in cui vengono compilate nella maschera di dettaglio
			@Override
			protected void aggiungiColonneNascosteSuGrid(LinkedHashMap<String, String> mappa) {
				for (int i = 0; i < colonne.size(); i++) {

					Integer nroColonna = colonne.get(i);

					final Record dett = mappaColonne.get(nroColonna);


					if (dett.getAttribute("nome").endsWith("_hidden#") == false) {

						ListGridField field = new ListGridField(dett.getAttribute("nome"), dett.getAttribute("label"));
						String fieldName = field.getName();
						String fieldTitle = field.getTitle();

						/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
						if (field.getDisplayField() != null)
							fieldName = field.getDisplayField();

						if (!(field instanceof ControlListGridField) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
							mappa.put(fieldName, fieldTitle);
						}
					}
				}
			}
			
			@Override
			public List<Canvas> buildCustomEditCanvas() {
				
				importaXlsButton = new ImgButtonItem("importaXlsButton", "menu/import_excel.png", "Importa dati da Excel");
				importaXlsButton.addIconClickHandler(new IconClickHandler() {
					
					@Override
					public void onIconClick(IconClickEvent event) {
						
						// Passo la lista delle colonne VISIBILI
						RecordList datiDettListaVisible = new RecordList();
						if (datiDettLista!=null && datiDettLista.getLength()>0){
							for (int i = 0; i < datiDettLista.getLength(); i++) {
								Record datiDettRec = datiDettLista.get(i);
								String flgSkipImport = (datiDettRec.getAttribute("flgSkipImport") !=null ? datiDettRec.getAttributeAsString("flgSkipImport") : "0");
								if (flgSkipImport==null) 
									flgSkipImport = "";
								if (flgSkipImport.equalsIgnoreCase(""))
									flgSkipImport= "0";
								// Se e' una colonna visibile la prendo, altrimenti la scarto
								if (flgSkipImport.equalsIgnoreCase("0")){
									datiDettListaVisible.add(datiDettRec);
								}								
							}	
						}
						
						ImportXlsPopup importXlsPopup = new ImportXlsPopup(datiDettListaVisible){
							@Override
							public void onClickOkButton(Record values) {
								if (values.getAttribute("datiExcel") !=null ){
									Record datiExcel = new Record(values.getAttributeAsJavaScriptObject("datiExcel"));
									Date tsPubblDal = (datiExcel.getAttributeAsDate("tsPubblDal") !=null ? (datiExcel.getAttributeAsDate("tsPubblDal")) : null);
									Date tsPubblAl  = (datiExcel.getAttributeAsDate("tsPubblAl")  !=null ? (datiExcel.getAttributeAsDate("tsPubblAl"))  : null);
									RecordList valoriAttrLista = new RecordList(datiExcel.getAttributeAsJavaScriptObject("tabellaAmmTrasparente"));
									inserisciRecordListInTabella(valoriAttrLista, tsPubblDal, tsPubblAl);
									
								}								
								markForRedraw();
							}
						};				
						importXlsPopup.show();						
					}
					
				});	
				
				importaXlsButton.setShowIfCondition(new FormItemIfFunction() {

					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return mode !=null && mode.equalsIgnoreCase("edit");
					}
				});
				
				
				importaXlsButton.setCanFocus(false);
				importaXlsButton.setTabIndex(-1);
				
				filtraDatiTabellaButton = new ImgButtonItem("filtraDatiTabellaButton", "buttons/imbuto.png", "Filtra");
				filtraDatiTabellaButton.addIconClickHandler(new IconClickHandler() {
					
					@Override
					public void onIconClick(IconClickEvent event) {
						
						// Passo la lista delle colonne VISIBILI
						if (filtriColonneLista == null && datiDettLista!=null && datiDettLista.getLength()>0){
							filtriColonneLista = new RecordList();
							defaultFiltriColonneLista = new RecordList();
							lastModifiedFiltriColonneLista = new RecordList();
							for (int i = 0; i < datiDettLista.getLength(); i++) {
								Record filtroDaInserire = new Record();
								Record datiDettRec = datiDettLista.get(i);
								if (datiDettRec.getAttribute("tipo") != null && !datiDettRec.getAttribute("tipo").equals("DOCUMENTLIST") && 
										datiDettRec.getAttribute("nome") != null && !datiDettRec.getAttribute("nome").equals("#DataPubblicazioneDa_hidden#") && !datiDettRec.getAttribute("nome").equals("#DataPubblicazioneA_hidden#")) {
									filtroDaInserire.setAttribute("nome", datiDettRec.getAttribute("nome") != null ? datiDettRec.getAttribute("nome") : "");
									filtroDaInserire.setAttribute("inColonna", datiDettRec.getAttribute("label") != null ? datiDettRec.getAttribute("label") : "");
									filtroDaInserire.setAttribute("cerca", "");
									filtriColonneLista.add(new Record(filtroDaInserire.toMap()));
									defaultFiltriColonneLista.add(new Record(filtroDaInserire.toMap()));
									lastModifiedFiltriColonneLista.add(new Record(filtroDaInserire.toMap()));
								}
							}	
						}
						
						FiltraDatiContenutiTabellaPopup filtraDatiPopup = new FiltraDatiContenutiTabellaPopup() {
							@Override
							public void actionChiudiButton() {
								super.actionChiudiButton();
//								filtriColonneLista = new RecordList(lastModifiedFiltriColonneLista.getJsObj());
								filtriColonneLista = cloneRecordList(lastModifiedFiltriColonneLista);
							}
							
							@Override
							public void actionApplicaFiltriButton(Record values) {
								super.actionApplicaFiltriButton(values);
//								lastModifiedFiltriColonneLista = new RecordList(filtriColonneLista.getJsObj());
								filtriColonneLista = values != null ? values.getAttributeAsRecordList("listaFiltraDatiContenutiTabella") : null;
								lastModifiedFiltriColonneLista = cloneRecordList(filtriColonneLista);								
								ricaricaContenutoTabella();
							}
							
							@Override
							public void actionRimuoviFiltriButton() {
								super.actionRimuoviFiltriButton();
//								filtriColonneLista = new RecordList(defaultFiltriColonneLista.getJsObj());
								filtriColonneLista = cloneRecordList(defaultFiltriColonneLista);
								lastModifiedFiltriColonneLista = cloneRecordList(defaultFiltriColonneLista);
								ricaricaContenutoTabella();
							}
						};
						Record values = new Record();
						values.setAttribute("listaFiltraDatiContenutiTabella", filtriColonneLista);
						filtraDatiPopup.setValues(values);
						filtraDatiPopup.markForRedraw();
						filtraDatiPopup.show();						
					}
					
				});	
				
				filtraDatiTabellaButton.setShowIfCondition(new FormItemIfFunction() {
					
					@Override
					public boolean execute(FormItem item, Object value, DynamicForm form) {
						return mode !=null && mode.equalsIgnoreCase("edit");
					}
				});
				
				
				filtraDatiTabellaButton.setCanFocus(false);
				filtraDatiTabellaButton.setTabIndex(-1);
				List<FormItem> listaOtherNewButtonsFormFields = new ArrayList<FormItem>();
				listaOtherNewButtonsFormFields.add(importaXlsButton);
				listaOtherNewButtonsFormFields.add(filtraDatiTabellaButton);
				listaOtherNewButtonsFormFields.addAll(getCustmOtherNewButtons());
				
				DynamicForm otherNewButtonsForm = new DynamicForm();
				otherNewButtonsForm.setHeight(1);
				otherNewButtonsForm.setWidth(1);
				otherNewButtonsForm.setOverflow(Overflow.VISIBLE);
				otherNewButtonsForm.setPrefix("otherNewButtons");
				otherNewButtonsForm.setNumCols(20);
				otherNewButtonsForm.setFields(listaOtherNewButtonsFormFields.toArray(new FormItem[listaOtherNewButtonsFormFields.size()]));
				
				List<Canvas> editCanvas = new ArrayList<Canvas>();
				editCanvas.add(otherNewButtonsForm);
				return editCanvas;				
			}
			
			@Override
			public boolean isShowImportXlsButton() {
				return true;
			}
			
			@Override
			public boolean isAlwaysShowDetailButtom() {
				return true;
			}
			
			@Override
			public boolean isShowEditButtons() {
				//return true;
				return (mode !=null && mode.equalsIgnoreCase("edit"));
			}
			
			@Override
			public boolean isShowNewButton() {
				//return true;
				return (mode !=null && mode.equalsIgnoreCase("edit"));
			}
			
			@Override
			public boolean isShowDeleteButton() {
				return true;
			}
			
			@Override
			public boolean isShowModifyButton() {
				return true;
			}
			
			@Override
			public boolean isEditable() {
				return true;
			}
			
			@Override
			public boolean isShowPreference() {
				return false;
			}

			@Override
			public boolean isGrigliaEditabile() {
				return true;
			}
			
			@Override
			public boolean isHideLayoutPreference() {
				return true;
			}
			
			@Override
			public void onClickImportXlsButton(final String uriFileImportExcel, final String mimetype) {
			}

			@Override
			public void addData(Record record) {
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				inserisciRecordInTabella(record, null, null);	
			}
			
			@Override
			public void updateData(Record record, Record oldRecord) {
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				int index = Integer.parseInt((oldRecord.getAttribute("#NroRiga_hidden#")));
				aggiornaRecordInTabella(index, record, oldRecord);
			}
			
			@Override
			public void removeData(Record record) {
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				int index = Integer.parseInt((record.getAttribute("#NroRiga_hidden#")));
				eliminaRecordInTabella(index, record);
			}
			
			@Override
			public List<ListGridField> getListaListGridFieldsCustom() {
				List<ListGridField> listaListGridFieldsCustom = new ArrayList<ListGridField>();
				ListGridField nroRiga = new ListGridField("#NroRiga_hidden#");
				nroRiga.setHidden(Boolean.TRUE);
				nroRiga.setCanHide(Boolean.FALSE); 
				listaListGridFieldsCustom.add(nroRiga);
				return listaListGridFieldsCustom;
			}
			
			@Override
			public String getListaAttributoDinamicoListaWindowSaveButtonLabel() {
				return I18NUtil.getMessages().saveButton_prompt();
			}
			
			@Override
			public String getListaAttributoDinamicoListaWindowSaveButtonIcon() {
				return "buttons/save.png";
			}	
			
			@Override
			public boolean isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp() {
				return true;
			}	
			
			@Override
			public boolean customValidateItemTabellaTrasp(Record recordValori) {
				for (int i = 0; i < datiDettLista.getLength(); i++) {
					Record datiDettRec = datiDettLista.get(i);
					if (datiDettRec.getAttribute("tipo") != null && datiDettRec.getAttribute("tipo").equals("DOCUMENTLIST")) {
						boolean valid = isRecordValid(recordValori);
						if (!valid){
							return false;
						}
					}
				}	
				return true;
			}
		};
		
		listaDinamicaItem.setName(attrLista.getAttribute("nome"));
		listaDinamicaItem.setTitle(attrLista.getAttribute("label"));
		if (attrLista.getAttribute("altezza") != null && !"".equals(attrLista.getAttribute("altezza"))) {
			listaDinamicaItem.setHeight(new Integer(attrLista.getAttribute("altezza")));
		} else {
			listaDinamicaItem.setHeight(245);
		}		
		listaDinamicaItem.setStartRow(true);
		listaDinamicaItem.setEndRow(true);
		return listaDinamicaItem;
	}
	
	protected CallbackGenericFunction createCallback() {
		return null;
	}
	
	public void setValues(Record values) {
		form.editRecord(values);
	}
	
	// Inserisce N record
	private void inserisciRecordListInTabella(RecordList valoriAttrLista, Date tsPubblDal, Date tsPubblAl) {		
		Layout.showWaitPopup("Salvataggio dati in corso...");
		Record recordToPass = getRecordToPass(valoriAttrLista);
		recordToPass.setAttribute("tsPubblDal", tsPubblDal);
		recordToPass.setAttribute("tsPubblAl", tsPubblAl);
		
		String uoLavoro = AurigaLayout.getUoLavoro();	
		recordToPass.setAttribute("uoLavoro", uoLavoro);
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("aggiungiDatoContTabella", recordToPass, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				Layout.hideWaitPopup();
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					SC.say("Operazione effettuata con successo","Sono state importate con successo tutte le righe del foglio.", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							Layout.showWaitPopup("Refresh dati in corso...");
							ricaricaContenutoTabella();
						}
					});
					
				}
			}
		});
	}
	
	private RecordList cloneRecordList (RecordList rlDaClonare) {
		RecordList result = new RecordList();
		for (int i=0; i<rlDaClonare.getLength(); i++) {
			result.add(new Record(rlDaClonare.get(i).toMap()));
		}
		return result;
	}
	
	// Inserisce 1 record
	private void inserisciRecordInTabella(Record nuovoRecord,Date tsPubblDal, Date tsPubblAl) {
		Record recordToPass = getRecordToPass(nuovoRecord);
		recordToPass.setAttribute("tsPubblDal", tsPubblDal);
		recordToPass.setAttribute("tsPubblAl", tsPubblAl);
		
		String uoLavoro = AurigaLayout.getUoLavoro();	
		recordToPass.setAttribute("uoLavoro", uoLavoro);
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("aggiungiDatoContTabella", recordToPass, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				// Ricarico i dati indipendentemente dall'esito della chiamata a datasource
				ricaricaContenutoTabella();
			}
		});
	}

	private void aggiornaRecordInTabella(int numRigaAggiornata, Record recordAggiornato, Record recordOriginale) {
		Record recordToPass = getRecordToPass(recordAggiornato);
		recordToPass.setAttribute("numeroRiga", numRigaAggiornata);
		
		String uoLavoro = AurigaLayout.getUoLavoro();	
		recordToPass.setAttribute("uoLavoro", uoLavoro);
		
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("modificaDatoContTabella", recordToPass, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				//  Ricarico i dati indipendentemente dall'esito della chiamata a datasource
				ricaricaContenutoTabella();
			}
		});
	}
	
	public Record getRecordToPass(Record recordValori) {
		Record recordToPass = new Record();
		
		if (datiDettLista != null) {
			for (int i = 0; i < datiDettLista.getLength(); i++) {
				Record datiColonna = datiDettLista.get(i);
				if ("DOCUMENTLIST".equals(datiColonna.getAttribute("tipo"))) {
					List<Map> value = new ArrayList<Map>();
					Object objDocumentList = recordValori.getAttributeAsObject(datiColonna.getAttribute("nome"));
					if (objDocumentList != null && objDocumentList.toString() != null && !objDocumentList.toString().equalsIgnoreCase("")) {
						RecordList recordListDocumentList = new RecordList(recordValori.getAttributeAsJavaScriptObject(datiColonna.getAttribute("nome")));
						for (int j = 0; j < recordListDocumentList.getLength(); j++) {
							Map mapDocumentValue = recordListDocumentList.get(j).getAttributeAsMap("documento");
							value.add(mapDocumentValue);
						}
					}
					recordValori.setAttribute(datiColonna.getAttribute("nome"), value);
				} 
			}
		}
		
		recordToPass.setAttribute("idContenuto", idContenuto);
		recordToPass.setAttribute("listDettColonnaAttributoLista", datiDettLista);
		recordToPass.setAttribute("contenuto", recordValori);
		return recordToPass; 
	}
	
	
	public boolean isRecordValid(Record recordValori) {
		
		boolean returnOut = true;
		
		if (datiDettLista != null) {
			for (int i = 0; i < datiDettLista.getLength(); i++) {
				Record datiColonna = datiDettLista.get(i);
				if ("DOCUMENTLIST".equals(datiColonna.getAttribute("tipo"))) {
					List<Map> value = new ArrayList<Map>();
					Object objDocumentList = recordValori.getAttributeAsObject(datiColonna.getAttribute("nome"));
					if (objDocumentList != null && objDocumentList.toString() != null && !objDocumentList.toString().equalsIgnoreCase("")) {
						RecordList recordListDocumentList = new RecordList(recordValori.getAttributeAsJavaScriptObject(datiColonna.getAttribute("nome")));
						for (int j = 0; j < recordListDocumentList.getLength(); j++) {
							Map mapDocumentValue = recordListDocumentList.get(j).getAttributeAsMap("documento");
							
							if (mapDocumentValue != null) {
								String nomeFile  = (String) mapDocumentValue.get("nomeFile");
								String uriFile   = (String) mapDocumentValue.get("uriFile");
								String nomeCampo = datiColonna.getAttribute("nome");
								
								// Se il nome del file e' valorizzato ma il file non e' stato caricato
								if (!isBlank(nomeFile) && isBlank(uriFile)) { 
									String msg = "Il file per il campo '" + nomeCampo + "' va obbligatoriamente caricato.";
									Layout.addMessage(new MessageBean(msg, "", MessageType.ERROR));
									returnOut=false;
									break;
								}
								
								// Se il nome del file NON e' valorizzato e il file e' stato caricato
								if (isBlank(nomeFile) && !isBlank(uriFile)) {
									String msg = "Il nome del file per il campo '" + nomeCampo + "' va obbligatoriamente inserito.";
									Layout.addMessage(new MessageBean(msg, "", MessageType.ERROR));
									returnOut=false;
									break;
								}								
							}							
						}
					}
				} 
			}
		}
		
		return returnOut;
	}
	
	public Record getRecordToPass(RecordList recordValori) {
		Record recordToPass = new Record();
		recordToPass.setAttribute("idContenuto", idContenuto);
		recordToPass.setAttribute("listDettColonnaAttributoLista", datiDettLista);
		recordToPass.setAttribute("contenutoList", recordValori);
		return recordToPass; 
	}
	
	private void eliminaRecordInTabella(int numeroRigaEliminata, Record rcordDaEliminare) {
		Record recordToPass = new Record();
		recordToPass.setAttribute("idContenuto", idContenuto);
		recordToPass.setAttribute("listDettColonnaAttributoLista", datiDettLista);
		recordToPass.setAttribute("numeroRiga", numeroRigaEliminata);
		recordToPass.setAttribute("idRiga", rcordDaEliminare.getAttribute("idRiga"));
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("eliminaDatoContTabella", recordToPass, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				// Ricarico i dati indipendentemente dall'esito della chiamata a datasource
				ricaricaContenutoTabella();
			}
		});	
	}
	
	public void ricaricaContenutoTabella() {
	
		RecordList recordList = null;
		if(filtriColonneLista != null) {
			recordList = new RecordList();
			for (int i=0; i<filtriColonneLista.getLength(); i++) {
				Record record = filtriColonneLista.get(i);
				if (record.getAttribute("cerca") != null && !record.getAttribute("cerca").equals("")) {
					Record filtroDiRicerca = new Record();
					filtroDiRicerca.setAttribute("nomeFiltro", record.getAttribute("nome"));
					filtroDiRicerca.setAttribute("valoreFiltro", record.getAttribute("cerca"));
					recordList.add(filtroDiRicerca);
				}
			}
		}
	
		Record recordToPass = new Record();
		recordToPass.setAttribute("flgRecordFuoriPubbl", flgRecordFuoriPubbl);
		recordToPass.setAttribute("idContenuto", idContenuto);
		recordToPass.setAttribute("listaFiltriContenutiTabella", recordList);
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		lGWTRestDataSource.executecustom("leggiDatiContTabella", recordToPass, new DSCallback() {						
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (response.getData() != null) {
						Record result = response.getData()[0];
						Record values = new Record();
						RecordList valoriAttrLista = result.getAttributeAsRecordList("valoriAttrLista");
						values.setAttribute("tabellaAmmTrasparente", valoriAttrLista);
						nroRecTotali = result.getAttribute("nroRecTotali");
						nroRecordLabel.setContents(I18NUtil.getMessages().nroRecordLabel_prefix() + ": " + nroRecTotali);						
						filtroAttivoImg.hide();
						if(filtriColonneLista != null) {
							for (int i=0; i<filtriColonneLista.getLength(); i++) {
								Record record = filtriColonneLista.get(i);
								if (record.getAttribute("cerca") != null && !record.getAttribute("cerca").equals("")) {
									filtroAttivoImg.show();
									break;
								}
							}
						}						
					    setValues(values);
					    Layout.hideWaitPopup();
					}
				}
				Layout.hideWaitPopup();
			}
		});
	}
	
	public List<FormItem> getCustmOtherNewButtons() {
		return new ArrayList<FormItem>();
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	private boolean isBlank(Object value) {
		return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
	}
}