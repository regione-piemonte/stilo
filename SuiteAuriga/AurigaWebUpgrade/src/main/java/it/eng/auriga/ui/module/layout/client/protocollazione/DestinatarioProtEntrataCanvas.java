/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
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
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.MembriGruppoUdWindow;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SalvaInRubricaPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SoggettiDetail;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;

public class DestinatarioProtEntrataCanvas extends IndirizzoCanvas {

	public static final String FINALITA_SELECT_ORGANIGRAMMA = "DESTINATARI_ENTRATA";

	private HiddenItem idDestinatarioItem;
	private HiddenItem idSoggettoItem; // id. in rubrica
	private HiddenItem idUoSoggettoItem;
	private HiddenItem idUserSoggettoItem;
	private HiddenItem idScrivaniaSoggettoItem;
	private HiddenItem idAssegnatarioItem;
	private HiddenItem idDestInvioCCItem;
	private HiddenItem flgSelXAssegnazioneItem;
	private HiddenItem flgAssegnaAlDestinatarioXNuovaProtComeCopiaItem;
	private HiddenItem codRapidoChangedHiddenItem;
	private SelectItem tipoDestinatarioItem;
	private ExtendedTextItem codRapidoDestinatarioItem;
	private FilteredSelectItemWithDisplay organigrammaDestinatarioItem;
	private CheckboxItem flgPCItem;
	private HiddenItem flgPCReadOnlyHiddenItem;
	private ImgButtonItem opzioniInvioPCButton;
	private HiddenItem opzioniInvioCondivisioneHiddenItem;
	private ExtendedTextItem denominazioneDestinatarioItem;
	private ExtendedTextItem cognomeDestinatarioItem;
	private ExtendedTextItem nomeDestinatarioItem;
	private ExtendedTextItem codfiscaleDestinatarioItem;
	private ExtendedTextItem indirizzoMailDestinatarioItem;
	private ImgButtonItem salvaInRubricaButton;
	private ImgButtonItem visualizzaInRubricaButton;
	private HiddenItem idGruppoInternoItem;
	private HiddenItem idGruppoEsternoItem;
	private FilteredSelectItemWithDisplay gruppiDestinatarioItem;
	private SelectItemWithDisplay destinatariPreferitiItem;
	private CheckboxItem flgAssegnaAlDestinatarioItem;
	private HiddenItem flgAssegnaAlDestinatarioReadOnlyHiddenItem;
	private ImgButtonItem opzioniInvioAssegnazioneButton;
	private HiddenItem opzioniInvioAssegnazioneHiddenItem;
	private ImgButtonItem showMembriGruppoButton;
	private HiddenItem gruppoSalvatoItem;
	
	public boolean showFlgAssegnaAlDestinatario = false;
	public boolean showIndirizzoMailDestinatario = false;

	public DestinatarioProtEntrataCanvas(ReplicableItem item) {
		super(item);
	}
	
	public boolean showSelectOrganigramma() {		
		return showSelectOrganigramma(tipoDestinatarioItem.getValueAsString());
	}
	
	public boolean showSelectOrganigramma(String tipoDestinatario) {
		if(AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT")) {
			// essendo il destinatario di una prot. in entrata quando il tipo non è valorizzato di default deve comparire la select organigramma (soggetto interno)
			return tipoDestinatario == null || tipoDestinatario.equals("") || tipoDestinatario.equals("UP_UOI");
		}
		return false;
	}
		
	public String getTipoAssegnatari() {
		return "UO;SV";					
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idDestinatarioItem = new HiddenItem("idDestinatario");

		idSoggettoItem = new HiddenItem("idSoggetto");
		idUoSoggettoItem = new HiddenItem("idUoSoggetto");
		idUserSoggettoItem = new HiddenItem("idUserSoggetto");
		idScrivaniaSoggettoItem = new HiddenItem("idScrivaniaSoggetto");
		idAssegnatarioItem = new HiddenItem("idAssegnatario");
		idDestInvioCCItem = new HiddenItem("idDestInvioCC");
		flgSelXAssegnazioneItem = new HiddenItem("flgSelXAssegnazione");
		flgAssegnaAlDestinatarioXNuovaProtComeCopiaItem = new HiddenItem("flgAssegnaAlDestinatarioXNuovaProtComeCopia");
		gruppoSalvatoItem = new HiddenItem("gruppoSalvato");
		codRapidoChangedHiddenItem = new HiddenItem("codRapidoChanged");
		
		// tipo
		tipoDestinatarioItem = new SelectItem("tipoDestinatario", "Tipo destinatario");
		LinkedHashMap<String, String> tipoValueMap = new LinkedHashMap<String, String>();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tipoDestinatarioItem.setShowTitle(true);
			tipoValueMap.put("", I18NUtil.getMessages().protocollazione_select_listmap_generic_value());
			tipoDestinatarioItem.setDefaultValue("");
		} else {
			tipoDestinatarioItem.setShowTitle(false);
		}
		tipoDestinatarioItem.setAllowEmptyValue(!AurigaLayout.getIsAttivaAccessibilita());
		if(AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT")) {
			tipoValueMap.put("UP_UOI", I18NUtil.getMessages().protocollazione_select_listmap_UP_UOI_value());
		} else {
			tipoValueMap.put("UOI", I18NUtil.getMessages().protocollazione_select_listmap_UOI_value());
			tipoValueMap.put("UP", I18NUtil.getMessages().protocollazione_select_listmap_UP_value());
		}
		tipoValueMap.put("LD", I18NUtil.getMessages().protocollazione_select_listmap_LD_value());
		if(AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT")) {
			// se DEST_INT_CON_SELECT = true solo per registrazione in entrata e interne (NON le bozze), ovvero solo nei casi in cui i destinatari sono solo interni, appaiono anche i "Preferiti"
			tipoValueMap.put("PREF", I18NUtil.getMessages().protocollazione_select_listmap_PREF_value());
		}
		tipoDestinatarioItem.setValueMap(tipoValueMap);
		tipoDestinatarioItem.setWidth(150);
		tipoDestinatarioItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				tipoDestinatarioItem.setAttribute("oldValue", event.getOldValue());								
			}
		});
		tipoDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearErrors(true);
				String oldValue = tipoDestinatarioItem.getAttribute("oldValue") != null ? tipoDestinatarioItem.getAttribute("oldValue") : "";
				String value = event.getValue() != null ? (String) event.getValue() : "";				
				// controllo se rispetto a prima sia cambiato il fatto che si veda o meno il campo denominazione
				boolean isChangedShowDenominazione = !(("".equals(oldValue) && toShowDenominazione(value)) || (toShowDenominazione(oldValue) && "".equals(value)) || (toShowDenominazione(oldValue) && toShowDenominazione(value)));
				// non faccio l'altro controllo perchè qui il soggetto è sempre interno				
				if (isChangedShowDenominazione) {
					Record lRecord = mDynamicForm.getValuesAsRecord();
					lRecord.setAttribute("codRapidoDestinatario", "");
					lRecord.setAttribute("denominazioneDestinatario", "");
					lRecord.setAttribute("cognomeDestinatario", "");
					lRecord.setAttribute("nomeDestinatario", "");
					lRecord.setAttribute("codfiscaleDestinatario", "");
//					lRecord.setAttribute("flgAssegnaAlDestinatario", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
					 														 // il valore di default, quindi utilizzo il clearValue()
					lRecord.setAttribute("gruppiDestinatario", "");
					lRecord.setAttribute("idGruppoInterno", "");
					lRecord.setAttribute("idGruppoEsterno", "");
					lRecord.setAttribute("idDestinatario", "");
					lRecord.setAttribute("idSoggetto", "");
					lRecord.setAttribute("idUoSoggetto", "");
					lRecord.setAttribute("idUserSoggetto", "");
					lRecord.setAttribute("idScrivaniaSoggetto", "");
					lRecord.setAttribute("flgSelXAssegnazione", "");
					mDynamicForm.setValues(lRecord.toMap());
					mDynamicForm.clearValue("flgAssegnaAlDestinatario");		
					((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
					showHideFlgAssegnaAlDestinatario(true);
					((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
				}
			}
		});
		
		
		//Select destinatari preferiti
		SelectGWTRestDataSource destinatariPreferitiDS = new SelectGWTRestDataSource("DestinatariPreferitiProtDataSource", "idDestPref", FieldType.TEXT, new String[] { "denominazione" }, true);

		destinatariPreferitiItem = new SelectItemWithDisplay("idDestPref", destinatariPreferitiDS){
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("idSoggetto", record.getAttributeAsString("idRubrica"));
				if(record.getAttributeAsString("idDestPref") != null) {
					if(record.getAttributeAsString("idDestPref").startsWith("UO")) {
						mDynamicForm.setValue("idUoSoggetto", record.getAttributeAsString("idDestPref").substring(2));
					} 
					if(record.getAttributeAsString("idDestPref").startsWith("SV")) {
						mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttributeAsString("idDestPref").substring(2));
					}
				}
				mDynamicForm.setValue("codRapidoDestinatario", record.getAttribute("codRapido"));
				mDynamicForm.setValue("denominazioneDestinatario", record.getAttribute("denominazione"));
				mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
				markForRedraw();
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("idSoggetto", "");
					mDynamicForm.setValue("idUoSoggetto", "");
					mDynamicForm.setValue("idScrivaniaSoggetto", "");					
					mDynamicForm.setValue("codRapidoDestinatario", "");
					mDynamicForm.setValue("denominazioneDestinatario", "");
					mDynamicForm.setValue("flgSelXAssegnazione", "");								
				}
				markForRedraw();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("codRapidoDestinatario", "");
				mDynamicForm.setValue("denominazioneDestinatario", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");
				markForRedraw();
			};
		};
		destinatariPreferitiItem.setShowTitle(false);
		destinatariPreferitiItem.setOptionDataSource(destinatariPreferitiDS); 		
		ListGridField idDestPrefField = new ListGridField("idDestPref");
		idDestPrefField.setHidden(true);
		ListGridField codRapidoField = new ListGridField("codRapido", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoField.setWidth(80);
		ListGridField denominazioneField = new ListGridField("denominazione", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
		denominazioneField.setWidth("*");
		destinatariPreferitiItem.setPickListFields(idDestPrefField, codRapidoField, denominazioneField);
		destinatariPreferitiItem.setWidth(450);			
		destinatariPreferitiItem.setValueField("idDestPref");		
		destinatariPreferitiItem.setDisplayField("denominazione");
		destinatariPreferitiItem.setAutoFetchData(false);
		destinatariPreferitiItem.setFetchMissingValues(true);
		destinatariPreferitiItem.setAlwaysFetchMissingValues(true);
		destinatariPreferitiItem.setCachePickListResults(false);
		destinatariPreferitiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return toShowDestPref(tipoDestinatarioItem.getValueAsString());
			}
		});
		destinatariPreferitiItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());	
				showHideFlgAssegnaAlDestinatario(true);
				((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
			}
		});

		ImgItem iconaFlgInOrganigrammaItem = new ImgItem("iconaFlgInOrganigramma", "anagrafiche/soggetti/soggInOrg.png", "Presente in organigramma");
		iconaFlgInOrganigrammaItem.setColSpan(1);
		iconaFlgInOrganigrammaItem.setIconWidth(16);
		iconaFlgInOrganigrammaItem.setIconHeight(16);
		iconaFlgInOrganigrammaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaFlgInOrganigrammaItem.setAlign(Alignment.LEFT);
		iconaFlgInOrganigrammaItem.setWidth(16);
		iconaFlgInOrganigrammaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return isPresenteInOrganigramma();
			}
		});
		
		CustomValidator lSoggPresenteInOrganigrammaValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {				
				if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
					if(isDestinatarioValorizzato() && !isPresenteInOrganigramma()) {
						return false;
					}
				}
				return true;
			}
		};
		lSoggPresenteInOrganigrammaValidator.setErrorMessage("Soggetto non presente in organigramma");

		// cod.rapido
		codRapidoDestinatarioItem = new ExtendedTextItem("codRapidoDestinatario", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoDestinatarioItem.setWidth(120);
		codRapidoDestinatarioItem.setColSpan(1);
		codRapidoDestinatarioItem.setValidators(lSoggPresenteInOrganigrammaValidator);
		codRapidoDestinatarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				mDynamicForm.setValue("idDestinatario", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");			
				mDynamicForm.clearValue("flgAssegnaAlDestinatario");	
				((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
				if (showSelectOrganigramma()) {
					mDynamicForm.setValue("codRapidoChanged", true);
					mDynamicForm.setValue("organigrammaDestinatario", (String) null);
					mDynamicForm.setValue("tipoDestinatario", (String) null);						
					mDynamicForm.setValue("denominazioneDestinatario", (String) null);
					mDynamicForm.clearErrors(true);
					if (event.getValue() != null && !"".equals(event.getValue())) {
						organigrammaDestinatarioItem.fetchData(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								boolean trovato = false;
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codice = data.get(i).getAttribute("codice");
										String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
										if (event.getValue().equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
											mDynamicForm.setValue("organigrammaDestinatario", data.get(i).getAttribute("id"));
											mDynamicForm.setValue("idDestinatario", data.get(i).getAttribute("idUo"));
											mDynamicForm.setValue("idSoggetto", data.get(i).getAttribute("idRubrica"));
											String typeNodo = data.get(i).getAttribute("typeNodo");
											if(typeNodo != null) {
												if(typeNodo.equals("UO")) {
													mDynamicForm.setValue("idUoSoggetto", data.get(i).getAttribute("idUo"));
													mDynamicForm.setValue("tipoDestinatario", AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UOI");														
												} else if(typeNodo.equals("SV")) {
													mDynamicForm.setValue("idScrivaniaSoggetto", data.get(i).getAttribute("idUo"));
													mDynamicForm.setValue("tipoDestinatario", AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UP");												
												}
											}											
											mDynamicForm.setValue("flgSelXAssegnazione", data.get(i).getAttribute("flgSelXAssegnazione"));
											mDynamicForm.setValue("denominazioneDestinatario", data.get(i).getAttribute("descrizioneOrig"));
											mDynamicForm.setValue("cognomeDestinatario", data.get(i).getAttribute("cognome"));
											mDynamicForm.setValue("nomeDestinatario", data.get(i).getAttribute("nome"));
											mDynamicForm.setValue("codfiscaleDestinatario", data.get(i).getAttribute("codFiscale"));											
											mDynamicForm.clearErrors(true);
											trovato = true;
											((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());	
											showHideFlgAssegnaAlDestinatario(true);
											((DestinatarioProtEntrataItem) getItem()).manageOnChanged();
											break;
										}
									}
								}
								if (!trovato) {
									codRapidoDestinatarioItem.validate();
									codRapidoDestinatarioItem.blurItem();
								}
							}
						});
					} else {
						organigrammaDestinatarioItem.fetchData();
					}
				} else if (event.getValue() != null && !"".equals(event.getValue())) {
					String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
					if (tipoDestinatario != null && tipoDestinatario.equals("LD")) {
						cercaSoggettoLD();
					} else {
						Record lRecord = new Record();
						lRecord.setAttribute("codRapidoSoggetto", event.getValue());
						if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
							lRecord.setAttribute("flgInOrganigramma", "UO;UT");
							if (tipoDestinatarioItem.getValue() != null) {
								if ("UOI".equals(tipoDestinatarioItem.getValue())) {
									lRecord.setAttribute("flgInOrganigramma", "UO");
								} else if ("UP".equals(tipoDestinatarioItem.getValue())) {
									lRecord.setAttribute("flgInOrganigramma", "UT");
								}
							}
						}
						cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {

							@Override
							public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
								// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in
								// entrata/ uscita / interna>
								if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
									mDynamicForm.setFieldErrors("codRapidoDestinatario", "Soggetto non presente in organigramma");
								} else {									
									mDynamicForm.setFieldErrors("codRapidoDestinatario", I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("destinatario", "in entrata"));
								}
							}
						});
					}
				}
			}
		});
		codRapidoDestinatarioItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (codRapidoDestinatarioItem.getValue() != null && !"".equals(codRapidoDestinatarioItem.getValueAsString().trim())) {
					if(showSelectOrganigramma()	&& organigrammaDestinatarioItem.getValue() == null) {
						return false;						
					} 
//					else {
//						String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
//						if (tipoDestinatario != null && tipoDestinatario.equals("LD") && gruppiDestinatarioItem.getValue() == null) {
//							return false;							
//						}
//					}
				}
				return true;
			}
		});
//		codRapidoDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {
//			
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {				
//				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
//				if(tipoDestinatario != null && "PREF".equalsIgnoreCase(tipoDestinatario)) {
//					return false;
//				}
//				return true;
//			}
//		});
		
		SelectGWTRestDataSource organigrammaDestinatarioDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		organigrammaDestinatarioDS.addParam("tipoAssegnatari", getTipoAssegnatari());
		organigrammaDestinatarioDS.addParam("finalita", FINALITA_SELECT_ORGANIGRAMMA);		
		organigrammaDestinatarioDS.addParam("idUd", ((DestinatarioProtItem) getItem()).getIdUdProtocollo());
		
		organigrammaDestinatarioItem = new FilteredSelectItemWithDisplay("organigrammaDestinatario", organigrammaDestinatarioDS) {

			@Override
			public void manageOnCellClick(CellClickEvent event) {
				String flgSelXFinalita = event.getRecord().getAttributeAsString("flgSelXFinalita");
				if (flgSelXFinalita == null || "1".equals(flgSelXFinalita)) {
					onOptionClick(event.getRecord());
				} else {
					event.cancel();
					Layout.addMessage(new MessageBean("Valore non selezionabile", "", MessageType.ERROR));
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							clearSelect();
						}
					});
				}
			}

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
//				mDynamicForm.setValue("organigrammaDestinatario", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codRapidoDestinatario", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("idDestinatario", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("idSoggetto", record.getAttribute("idRubrica"));				
				String typeNodo = record.getAttributeAsString("typeNodo");
				if(typeNodo != null) {
					if(typeNodo.equals("UO")) {
						mDynamicForm.setValue("tipoDestinatario", AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UOI");
						mDynamicForm.setValue("idUoSoggetto", record.getAttributeAsString("idUo"));
					} else if(typeNodo.equals("SV")) {
						mDynamicForm.setValue("tipoDestinatario", AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UP");						
						mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttributeAsString("idUo"));
					}					
				}
				mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
				mDynamicForm.setValue("denominazioneDestinatario", record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.setValue("cognomeDestinatario", record.getAttribute("cognome"));
				mDynamicForm.setValue("nomeDestinatario", record.getAttribute("nome"));
				mDynamicForm.setValue("codfiscaleDestinatario", record.getAttribute("codFiscale"));
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaDestinatarioItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("organigrammaDestinatario", "");
				if(((DestinatarioProtEntrataItem)getItem()).isDimOrganigrammaNonStd()) {
					mDynamicForm.setValue("codRapidoDestinatario", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codRapidoDestinatario", "");
				}
				mDynamicForm.setValue("idDestinatario", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("tipoDestinatario", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");
				mDynamicForm.setValue("denominazioneDestinatario", "");
				mDynamicForm.setValue("cognomeDestinatario", "");
				mDynamicForm.setValue("nomeDestinatario", "");
				mDynamicForm.setValue("codfiscaleDestinatario", "");
				mDynamicForm.clearErrors(true);				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaDestinatarioItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("organigrammaDestinatario", "");
					if(((DestinatarioProtEntrataItem)getItem()).isDimOrganigrammaNonStd()) {
						mDynamicForm.setValue("codRapidoDestinatario", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codRapidoDestinatario", "");
					}
					mDynamicForm.setValue("idDestinatario", "");
					mDynamicForm.setValue("idSoggetto", "");
					mDynamicForm.setValue("idUoSoggetto", "");
					mDynamicForm.setValue("idUserSoggetto", "");
					mDynamicForm.setValue("idScrivaniaSoggetto", "");
					mDynamicForm.setValue("tipoDestinatario", "");
					mDynamicForm.setValue("flgSelXAssegnazione", "");
					mDynamicForm.setValue("denominazioneDestinatario", "");
					mDynamicForm.setValue("cognomeDestinatario", "");
					mDynamicForm.setValue("nomeDestinatario", "");
					mDynamicForm.setValue("codfiscaleDestinatario", "");
					mDynamicForm.clearErrors(true);				
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaDestinatarioItem.fetchData();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaDestinatarioPickListFields = new ArrayList<ListGridField>();
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			ListGridField typeNodoField = new ListGridField("iconaTypeNodo", "Tipo");
			typeNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			typeNodoField.setAlign(Alignment.CENTER);
			typeNodoField.setWidth(30);
			typeNodoField.setShowHover(false);
			typeNodoField.setCanFilter(false);
			typeNodoField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("iconaTypeNodo") != null && !"".equals(record.getAttributeAsString("iconaTypeNodo"))) {
						return "<div align=\"center\"><img src=\"images/organigramma/tipo/" + record.getAttribute("iconaTypeNodo")
								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			organigrammaDestinatarioPickListFields.add(typeNodoField);
		}
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(80);
		codiceField.setShowHover(true);
		codiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record != null ? record.getAttribute("descrizioneEstesa") : null;
			}
		});
		if (((DestinatarioProtEntrataItem)getItem()).isDimOrganigrammaNonStd()) {
			codiceField.setCanFilter(false);
			// TextItem codiceFilterEditorType = new TextItem();
			// codiceFilterEditorType.setCanEdit(false);
			// codiceField.setFilterEditorType(codiceFilterEditorType);
		}
		organigrammaDestinatarioPickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		organigrammaDestinatarioPickListFields.add(descrizioneField);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			ListGridField flgPuntoProtocolloField = new ListGridField("flgPuntoProtocollo", "Punto di Protocollo");
			flgPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			flgPuntoProtocolloField.setAlign(Alignment.CENTER);
			flgPuntoProtocolloField.setWidth(30);
			flgPuntoProtocolloField.setShowHover(true);
			flgPuntoProtocolloField.setCanFilter(false);
			flgPuntoProtocolloField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			flgPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record != null && record != null && record.getAttribute("flgPuntoProtocollo") != null
							&& record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "Punto di Protocollo";
					}
					return null;
				}
			});
			organigrammaDestinatarioPickListFields.add(flgPuntoProtocolloField);
		}
		organigrammaDestinatarioItem.setPickListFields(organigrammaDestinatarioPickListFields.toArray(new ListGridField[organigrammaDestinatarioPickListFields.size()]));
		if (((DestinatarioProtEntrataItem)getItem()).isDimOrganigrammaNonStd()) {
			organigrammaDestinatarioItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());
		} else {
			organigrammaDestinatarioItem.setFilterLocally(true);
		}
		
		organigrammaDestinatarioItem.setAutoFetchData(false);
		organigrammaDestinatarioItem.setFetchMissingValues(true);
		organigrammaDestinatarioItem.setAlwaysFetchMissingValues(true);
		organigrammaDestinatarioItem.setCachePickListResults(false);		
		organigrammaDestinatarioItem.setValueField("id");
		organigrammaDestinatarioItem.setOptionDataSource(organigrammaDestinatarioDS);
		organigrammaDestinatarioItem.setShowTitle(false);
		organigrammaDestinatarioItem.setWidth(450);
		organigrammaDestinatarioItem.setClearable(true);
		organigrammaDestinatarioItem.setShowIcons(true);
		organigrammaDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
				((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());	
				showHideFlgAssegnaAlDestinatario(true);
				((DestinatarioProtEntrataItem) getItem()).manageOnChanged();
			}
		});
		organigrammaDestinatarioItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSelectOrganigramma();
			}
		});
		organigrammaDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				Boolean notReplicable = getItem().getNotReplicable() != null ? getItem().getNotReplicable() : false;
				Boolean obbligatorio = getItem().getAttributeAsBoolean("obbligatorio") != null ? getItem().getAttributeAsBoolean("obbligatorio") : false;
				if(notReplicable && !obbligatorio) {
					return false;
				}
				return showSelectOrganigramma();
			}
		}));
		ListGrid organigrammaDestinatarioPickListProperties = organigrammaDestinatarioItem.getPickListProperties();
		organigrammaDestinatarioPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapidoDestinatario = mDynamicForm.getValueAsString("codRapidoDestinatario");
//				if (isDimOrgranigrammaNonStd() && (codRapidoDestinatario == null || "".equals(codRapidoDestinatario))) {
//					if(codRapidoDestinatarioItem.getCanEdit() != null && codRapidoDestinatarioItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors("codRapidoDestinatario", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}				
				GWTRestDataSource organigrammaDestinatarioDS = (GWTRestDataSource) organigrammaDestinatarioItem.getOptionDataSource();
				// organigrammaDestinatarioDS.addParam("ciToAdd", mDynamicForm.getValueAsString("organigrammaDestinatario"));
				organigrammaDestinatarioDS.addParam("codice", codRapidoDestinatario);
				organigrammaDestinatarioDS.addParam("finalita", FINALITA_SELECT_ORGANIGRAMMA);				
				organigrammaDestinatarioDS.addParam("idUd", ((DestinatarioProtItem) getItem()).getIdUdProtocollo());
				organigrammaDestinatarioDS.addParam("tipoAssegnatari", getTipoAssegnatari());
				organigrammaDestinatarioItem.setOptionDataSource(organigrammaDestinatarioDS);
				organigrammaDestinatarioItem.invalidateDisplayValueCache();
			}
		});
		organigrammaDestinatarioItem.setPickListProperties(organigrammaDestinatarioPickListProperties);

		// denominazione
		denominazioneDestinatarioItem = new ExtendedTextItem("denominazioneDestinatario", I18NUtil.getMessages()
				.protocollazione_detail_denominazioneItem_title());
		denominazioneDestinatarioItem.setWidth(200);
		denominazioneDestinatarioItem.setAttribute("obbligatorio", true);
		denominazioneDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return toShowDenominazione(tipoDestinatarioItem.getValueAsString());
			}
		});
		denominazioneDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showSelectOrganigramma()) return false;
				return toShowDenominazione(tipoDestinatarioItem.getValueAsString());
			}
		}));
		denominazioneDestinatarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
				if(denominazioneDestinatarioItem.getValue() != null && !"".equals(denominazioneDestinatarioItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("denominazioneSoggetto", denominazioneDestinatarioItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoDestinatarioItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		denominazioneDestinatarioItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cognome
		cognomeDestinatarioItem = new ExtendedTextItem("cognomeDestinatario", I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeDestinatarioItem.setWidth(150);
		cognomeDestinatarioItem.setAttribute("obbligatorio", true);
		cognomeDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoDestinatarioItem.getValueAsString());
			}
		});
		cognomeDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoDestinatarioItem.getValueAsString());
			}
		}));
		cognomeDestinatarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();				
				((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
				if(cognomeDestinatarioItem.getValue() != null && !"".equals(cognomeDestinatarioItem.getValue()) &&
				   nomeDestinatarioItem.getValue() != null && !"".equals(nomeDestinatarioItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeDestinatarioItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeDestinatarioItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoDestinatarioItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		cognomeDestinatarioItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// nome
		nomeDestinatarioItem = new ExtendedTextItem("nomeDestinatario", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeDestinatarioItem.setWidth(150);
		nomeDestinatarioItem.setAttribute("obbligatorio", true);
		nomeDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoDestinatarioItem.getValueAsString());
			}
		});
		nomeDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoDestinatarioItem.getValueAsString());
			}
		}));
		nomeDestinatarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
				if(cognomeDestinatarioItem.getValue() != null && !"".equals(cognomeDestinatarioItem.getValue()) &&
				   nomeDestinatarioItem.getValue() != null && !"".equals(nomeDestinatarioItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeDestinatarioItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeDestinatarioItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoDestinatarioItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		nomeDestinatarioItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cod.fiscale
		codfiscaleDestinatarioItem = new ExtendedTextItem("codfiscaleDestinatario", I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codfiscaleDestinatarioItem.setWidth(150);
		codfiscaleDestinatarioItem.setCharacterCasing(CharacterCasing.UPPER);
		codfiscaleDestinatarioItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value) || AurigaLayout.getParametroDBAsBoolean("DISATTIVA_CTRL_CF_PIVA_EMDI")) {
					return true;
				}
				if (isPersonaFisica(tipoDestinatarioItem.getValueAsString())) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}
			}
		});		
		codfiscaleDestinatarioItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
				if(codfiscaleDestinatarioItem.getValue() != null && !"".equals(codfiscaleDestinatarioItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("codfiscaleSoggetto", codfiscaleDestinatarioItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoDestinatarioItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		codfiscaleDestinatarioItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});
		codfiscaleDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				if(showSelectOrganigramma()) return false;
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				if (isPersonaFisica(tipoDestinatario)) {
					codfiscaleDestinatarioItem.setLength(16);
					codfiscaleDestinatarioItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
				} else {
					codfiscaleDestinatarioItem.setLength(28);
					codfiscaleDestinatarioItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscalePIVAItem_title());
				}
				return toShowDenominazione(tipoDestinatario) || isPersonaFisica(tipoDestinatario);
			}
		});
		
		ImgButtonItem showIndirizzoMailDestinatarioButton = new ImgButtonItem("showIndirizzoMailDestinatarioButton", "lookup/rubricaemail.png", "Mostra/nascondi indirizzo e-mail");
		showIndirizzoMailDestinatarioButton.setAlwaysEnabled(true);
		showIndirizzoMailDestinatarioButton.setColSpan(1);
		showIndirizzoMailDestinatarioButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !getEditing() && indirizzoMailDestinatarioItem.getValueAsString() != null && !"".equals(indirizzoMailDestinatarioItem.getValueAsString());
			}
		});
		showIndirizzoMailDestinatarioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				if(showIndirizzoMailDestinatario) {
					showIndirizzoMailDestinatario = false;
				} else {
					showIndirizzoMailDestinatario = true;
				}
				mDynamicForm.markForRedraw();
			}
		});
		
		indirizzoMailDestinatarioItem = new ExtendedTextItem("indirizzoMailDestinatario");
		indirizzoMailDestinatarioItem.setShowTitle(false);
		indirizzoMailDestinatarioItem.setWidth(200);
		indirizzoMailDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(!getEditing() && showIndirizzoMailDestinatario) {
					return indirizzoMailDestinatarioItem.getValueAsString() != null && !"".equals(indirizzoMailDestinatarioItem.getValueAsString());
				}
				return false;
			}
		});

		// gruppi
		SelectGWTRestDataSource lGwtRestDataSourceGruppo = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
				new String[] { "nomeGruppo" }, true);		
		lGwtRestDataSourceGruppo.extraparam.put("isGruppiDestinatari", "true");
		lGwtRestDataSourceGruppo.extraparam.put("usaFlagSoggettiInterni", "S");
		if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
			lGwtRestDataSourceGruppo.extraparam.put("flgSoloGruppiSoggInt", "2");
		}
		lGwtRestDataSourceGruppo.extraparam.put("abilSelDestinatariReg", "true");
		
		gruppiDestinatarioItem = new FilteredSelectItemWithDisplay("gruppiDestinatario", lGwtRestDataSourceGruppo) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codRapidoDestinatario", record.getAttributeAsString("codiceRapidoGruppo"));
				mDynamicForm.setValue("idGruppoInterno", record.getAttributeAsString("idSoggettiInterni"));
				mDynamicForm.setValue("idGruppoEsterno", record.getAttributeAsString("idSoggettiNonInterni"));
				mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));				
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("gruppiDestinatario", "");
				mDynamicForm.setValue("codRapidoDestinatario", "");
				mDynamicForm.setValue("idGruppoInterno", "");
				mDynamicForm.setValue("idGruppoEsterno", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");				
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("gruppiDestinatario", "");
					mDynamicForm.setValue("codRapidoDestinatario", "");
					mDynamicForm.setValue("idGruppoInterno", "");
					mDynamicForm.setValue("idGruppoEsterno", "");
					mDynamicForm.setValue("flgSelXAssegnazione", "");
				}
            }
		};
		gruppiDestinatarioItem.setAutoFetchData(false);
		gruppiDestinatarioItem.setFetchMissingValues(true);

		ListGridField idSoggettiInterniField = new ListGridField("idSoggettiInterni");
		idSoggettiInterniField.setHidden(true);

		ListGridField idSoggettiNonInterniField = new ListGridField("idSoggettiNonInterni");
		idSoggettiNonInterniField.setHidden(true);

		ListGridField codiceRapidoGruppoField = new ListGridField("codiceRapidoGruppo", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codiceRapidoGruppoField.setWidth(80);

		ListGridField nomeGruppoField = new ListGridField("nomeGruppo", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeGruppoField.setWidth("*");

		ListGridField flagSoggettiGruppoField = new ListGridField("flagSoggettiGruppo", I18NUtil.getMessages().protocollazione_detail_tipoItem_title());
		flagSoggettiGruppoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flagSoggettiGruppoField.setType(ListGridFieldType.ICON);
		flagSoggettiGruppoField.setWidth(30);
		flagSoggettiGruppoField.setIconWidth(16);
		flagSoggettiGruppoField.setIconHeight(16);
		Map<String, String> flagSoggettiGruppoValueIcons = new HashMap<String, String>();
		flagSoggettiGruppoValueIcons.put("E", "protocollazione/flagSoggettiGruppo/E.png");
		flagSoggettiGruppoValueIcons.put("I", "protocollazione/flagSoggettiGruppo/I.png");
		flagSoggettiGruppoValueIcons.put("M", "protocollazione/flagSoggettiGruppo/M.png");
		flagSoggettiGruppoValueIcons.put("O", "protocollazione/flagSoggettiGruppo/O.png");
		flagSoggettiGruppoValueIcons.put("", "blank.png");
		flagSoggettiGruppoField.setValueIcons(flagSoggettiGruppoValueIcons);
		flagSoggettiGruppoField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("flagSoggettiGruppo") != null) {
					if ("E".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_E_value();
					} else if ("I".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_I_value();
					} else if ("M".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_M_value();
					} else if ("O".equals(record.getAttribute("flagSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_O_value();
					}
				}
				return null;
			}
		});

		gruppiDestinatarioItem.setPickListFields(idSoggettiInterniField, idSoggettiNonInterniField, codiceRapidoGruppoField, nomeGruppoField,
				flagSoggettiGruppoField);
		gruppiDestinatarioItem.setFilterLocally(true);
		gruppiDestinatarioItem.setValueField("idGruppo");
		gruppiDestinatarioItem.setOptionDataSource(lGwtRestDataSourceGruppo);
		gruppiDestinatarioItem.setShowTitle(false);
		gruppiDestinatarioItem.setWidth(450);
		gruppiDestinatarioItem.setClearable(true);
		gruppiDestinatarioItem.setShowIcons(true);
		gruppiDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "LD".equalsIgnoreCase(tipoDestinatario);
			}
		});
		gruppiDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario != null && "LD".equalsIgnoreCase(tipoDestinatario);
			}
		}));
		gruppiDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {				
				markForRedraw();
				((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());	
				showHideFlgAssegnaAlDestinatario(true);
				((DestinatarioProtEntrataItem) getItem()).manageOnChanged();
			}
		});

		idGruppoInternoItem = new HiddenItem("idGruppoInterno");
		idGruppoEsternoItem = new HiddenItem("idGruppoEsterno");

		// bottone seleziona da rubrica
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
					return false;
				}
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario == null || "".equals(tipoDestinatario) || isPersonaFisica(tipoDestinatario) || isPersonaGiuridica(tipoDestinatario);
			}
		});
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(false);				
				DestinatarioLookupSoggettiPopup lookupRubricaPopup = new DestinatarioLookupSoggettiPopup(null, tipoDestinatarioItem.getValueAsString());
				lookupRubricaPopup.show();
			}
		});

		// bottone seleziona da organigramma
		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario == null || "".equals(tipoDestinatario) || "UP_UOI".equals(tipoDestinatario) || "UOI".equals(tipoDestinatario) || "UP".equals(tipoDestinatario);
			}
		});
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(false);				
				DestinatarioLookupOrganigramma lookupOrganigrammaPopup = new DestinatarioLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});

		// bottone cerca in rubrica
		ImgButtonItem cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInRubricaButton_prompt());
		cercaInRubricaButton.setColSpan(1);
		cercaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
					return false;
				}
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario == null || "".equals(tipoDestinatario) || isPersonaFisica(tipoDestinatario)
						|| (isPersonaGiuridica(tipoDestinatario) && !"AOOI".equalsIgnoreCase(tipoDestinatario));
			}
		});
		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(false);			
				cercaInRubrica();
			}
		});

		// bottone cerca in organigramma
		ImgButtonItem cercaInOrganigrammaButton = new ImgButtonItem("cercaInOrganigrammaButton", "lookup/organigrammasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInOrganigrammaButton_prompt());
		cercaInOrganigrammaButton.setColSpan(1);
		cercaInOrganigrammaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return tipoDestinatario == null || "".equals(tipoDestinatario) || "UOI".equals(tipoDestinatario) || "UP".equals(tipoDestinatario);
			}
		});
		cercaInOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(false);			
				cercaInOrganigramma();
			}
		});

		// bottone salva in rubrica
		salvaInRubricaButton = new ImgButtonItem("salvaInRubricaButton", "buttons/saveIn.png", I18NUtil.getMessages()
				.protocollazione_detail_salvaInRubricaButton_prompt());
		salvaInRubricaButton.setColSpan(1);
		salvaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
					return false;
				}
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return Layout.isPrivilegioAttivo("GRS/S/UO;I")
						&& (tipoDestinatario == null || "".equals(tipoDestinatario) || ((isPersonaFisica(tipoDestinatario) || isPersonaGiuridica(tipoDestinatario))
								&& !"AOOI".equalsIgnoreCase(tipoDestinatario) && !"PA".equalsIgnoreCase(tipoDestinatario)))
						&& !(idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue()));
			}
		});
		salvaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String tipo = tipoDestinatarioItem.getValueAsString();
				String codRapido = codRapidoDestinatarioItem.getValueAsString();
				String denominazione = denominazioneDestinatarioItem.isVisible() ? denominazioneDestinatarioItem.getValueAsString() : null;
				String cognome = cognomeDestinatarioItem.isVisible() ? cognomeDestinatarioItem.getValueAsString() : null;
				String nome = nomeDestinatarioItem.isVisible() ? nomeDestinatarioItem.getValueAsString() : null;
				String codiceFiscale = codfiscaleDestinatarioItem.isVisible() ? codfiscaleDestinatarioItem.getValueAsString() : null;
				
				Record recordDestinatario = new Record();
				recordDestinatario.setAttribute("tipo", tipo);
				recordDestinatario.setAttribute("codRapido", codRapido);
				recordDestinatario.setAttribute("denominazione", denominazione);
				recordDestinatario.setAttribute("cognome", cognome);
				recordDestinatario.setAttribute("nome", nome);
				recordDestinatario.setAttribute("codiceFiscale", codiceFiscale);
				
				Record recordIndirizzo = getFormValuesAsRecord();
				SalvaInRubricaPopup salvaInRubricaPopup = new SalvaInRubricaPopup("SEL_SOGG_INT", recordDestinatario, recordIndirizzo) {

					@Override
					public void manageLookupBack(Record record) {
						setFormValuesFromRecordRubrica(record);
					}
				};
				salvaInRubricaPopup.show();
			}
		});
		
		//bottone visualizza nominativo in rubrica
		visualizzaInRubricaButton = new ImgButtonItem("visualizzaInRubricaDestinatario", "buttons/detail.png", I18NUtil.getMessages()
				.protocollazione_detail_visualizzaInRubricaButton_prompt());
		visualizzaInRubricaButton.setAlwaysEnabled(true);
		visualizzaInRubricaButton.setColSpan(1);
		visualizzaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoDestinatario = tipoDestinatarioItem.getValueAsString();
				return idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue())
						&& (tipoDestinatario == null || "".equals(tipoDestinatario) || isPersonaFisica(tipoDestinatario) || isPersonaGiuridica(tipoDestinatario));
			}
		});

		visualizzaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				((DestinatarioProtEntrataItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String idSoggetto = mDynamicForm.getValueAsString("idSoggetto");

				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idSoggetto", idSoggetto);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AnagraficaSoggettiDataSource",
						"idSoggetto", FieldType.TEXT);
				lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record detailRecord = response.getData()[0];
							SoggettiDetail detail = new SoggettiDetail("anagrafiche_soggetti.detail");
							detail.editRecord(detailRecord);
							detail.viewMode();
							String nomeDettaglio = !mDynamicForm.getValueAsString("denominazioneDestinatario").equals("") ? 
									mDynamicForm.getValueAsString("denominazioneDestinatario") : mDynamicForm.getValueAsString("cognomeDestinatario") + " " + mDynamicForm.getValueAsString("nomeDestinatario");
							Layout.addModalWindow("dettaglio_soggetto", "Dettaglio " + nomeDettaglio, "buttons/detail.png", detail);
						}
					}
				});
			}
		});
		
		// check p.c.
		flgPCItem = new CheckboxItem("flgPC", getTitleFlgPCItem());
		flgPCItem.setRequired(false);
		flgPCItem.setColSpan(1);
		flgPCItem.setWidth(25);
		flgPCItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				boolean checked = event.getValue() != null && (Boolean) event.getValue();
				if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi()) {
					if (checked) {										
						mDynamicForm.setValue("flgAssegnaAlDestinatario", false);
					}
				}
				if(((DestinatarioProtItem) getItem()).isForzaAssegnazioneCondivisione()) {
					if (!checked) {										
						mDynamicForm.setValue("flgAssegnaAlDestinatario", true);
					}
				}				
				mDynamicForm.redraw();
			}
		});
		flgPCItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("SHOW_CC_IN_DEST_REG")) {
					item.setCanEdit(getEditing());
					if(isEffettuatoInvioInCC()) {
						boolean flgPC = mDynamicForm.getValue("flgPC") != null ? (Boolean) mDynamicForm.getValue("flgPC") : false;
						if(!flgPC) {
							mDynamicForm.setValue("flgPC", true);	
						}
						item.setCanEdit(false);
					}
					if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi() && isEffettuataAssegnazione()) {
						item.setCanEdit(false);
					}
					if(mDynamicForm.getValue("flgPCReadOnly") != null && (Boolean) mDynamicForm.getValue("flgPCReadOnly")) {
						item.setCanEdit(false);
					}
				}
				return showFlgPCItem();
			}
		});
		
		flgPCReadOnlyHiddenItem = new HiddenItem("flgPCReadOnly");
		
		opzioniInvioCondivisioneHiddenItem = new HiddenItem("opzioniInvioCondivisione");
		
		// BOTTONE : opzioni invio per p.c/cc
		opzioniInvioPCButton = new ImgButtonItem("opzioniInvioPCButton", "buttons/altriDati.png", "Opzioni invio " + getTitleFlgPCItem());
		opzioniInvioPCButton.setColSpan(1);
		opzioniInvioPCButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				String tipoAssegnatario = new Record(mDynamicForm.getValues()).getAttribute("tipoDestinatario");
				Record recordOpzioniInvio = new Record(mDynamicForm.getValues()).getAttributeAsRecord("opzioniInvioCondivisione");
				OpzioniInvioCondivisionePopup opzioniInvioCondivisionePopup = new OpzioniInvioCondivisionePopup(recordOpzioniInvio, tipoAssegnatario, getEditing()) {
					
					@Override
					public String getFlgUdFolder() {
						return "U";
					}
					
					@Override
					public void onClickOkButton(Record record) {
						mDynamicForm.setValue("opzioniInvioCondivisione", record);	
					}
				};
				opzioniInvioCondivisionePopup.show();
			}
		});
		opzioniInvioPCButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isInBozza()) {
					return false;
				}
				return !isEffettuatoInvioInCC() && showFlgPCItem() && flgPCItem.getValueAsBoolean();
			}
		});
				
		// check assegna
		flgAssegnaAlDestinatarioItem = new CheckboxItem("flgAssegnaAlDestinatario", getTitleFlgAssegnaAlDestinatarioItem());
		flgAssegnaAlDestinatarioItem.setRequired(false);
		flgAssegnaAlDestinatarioItem.setColSpan(1);
		flgAssegnaAlDestinatarioItem.setWidth(70);
		flgAssegnaAlDestinatarioItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				((DestinatarioProtItem) getItem()).manageChangeFlgAssegnaAlDestinatario(event);					
			}
		});
		flgAssegnaAlDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
				boolean checked = event.getValue() != null && (Boolean) event.getValue();
				if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi()) {
					if (checked) {					
						mDynamicForm.setValue("flgPC", false);
					}
				}
				if(((DestinatarioProtItem) getItem()).isForzaAssegnazioneCondivisione()) {
					if (!checked) {					
						mDynamicForm.setValue("flgPC", true);
					}
				}
				mDynamicForm.redraw();
			}
		});
		flgAssegnaAlDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {	
				item.setTitle(getTitleFlgAssegnaAlDestinatarioItem());
				item.setCanEdit(getEditing());
				if(isEffettuataAssegnazione()) {
					boolean flgAssegnaAlDestinatario = mDynamicForm.getValue("flgAssegnaAlDestinatario") != null ? (Boolean) mDynamicForm.getValue("flgAssegnaAlDestinatario") : false;
					if(!flgAssegnaAlDestinatario) {
						mDynamicForm.setValue("flgAssegnaAlDestinatario", true);						
						((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
					}
					item.setTitle(I18NUtil.getMessages().protocollazione_detail_flgAssegnataItem_title());
					item.setCanEdit(false);
				}			
				if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi() && isEffettuatoInvioInCC()) {
					item.setCanEdit(false);
				}
				if(mDynamicForm.getValue("flgAssegnaAlDestinatarioReadOnly") != null && (Boolean) mDynamicForm.getValue("flgAssegnaAlDestinatarioReadOnly")) {
					item.setCanEdit(false);
				}
				//TODO per decommentare questa parte devo fare in modo che flgSelXAssegnazione venga caricato nel dettaglio
//				boolean flgSelXAssegnazione = mDynamicForm.getValueAsString("flgSelXAssegnazione") != null && "1".equals(mDynamicForm.getValueAsString("flgSelXAssegnazione"));				
//				if(/*!fromLoadDett && */!flgSelXAssegnazione) {
//					mDynamicForm.setValue("flgAssegnaAlDestinatario", false);
//					((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
//					item.setCanEdit(false);
//				}
				return showFlgAssegnaAlDestinatarioItem();
			}
		});
		
		flgAssegnaAlDestinatarioReadOnlyHiddenItem = new HiddenItem("flgAssegnaAlDestinatarioReadOnly");

		opzioniInvioAssegnazioneHiddenItem = new HiddenItem("opzioniInvioAssegnazione");
		
		// BOTTONE : opzioni invio sull'assegnazione
		opzioniInvioAssegnazioneButton = new ImgButtonItem("opzioniInvioAssegnazioneButton", "buttons/altriDati.png", I18NUtil.getMessages()
				.protocollazione_detail_opzioniInvioAssegnazioneButton_prompt());
		opzioniInvioAssegnazioneButton.setColSpan(1);
		opzioniInvioAssegnazioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((DestinatarioProtItem) getItem()).setCercaInRubricaAfterChanged(false);	
				String tipoDestinatario = new Record(mDynamicForm.getValues()).getAttribute("tipoDestinatario");
				boolean flgUo = tipoDestinatario != null && "UOI".equals(tipoDestinatario);
				Record recordOpzioniInvio = new Record(mDynamicForm.getValues()).getAttributeAsRecord("opzioniInvioAssegnazione");
				OpzioniInvioAssegnazionePopup opzioniInvioAssegnazionePopup = new OpzioniInvioAssegnazionePopup(flgUo, tipoDestinatario, recordOpzioniInvio, getEditing()) {

					@Override
					public String getIdUd() {
						return ((DestinatarioProtItem) getItem()).getIdUdProtocollo();
					}
				
					@Override
					public String getFlgUdFolder() {
						return "U";
					}
					
					@Override
					public void onClickOkButton(Record record) {
						mDynamicForm.setValue("opzioniInvioAssegnazione", record);	
					}
				};
				opzioniInvioAssegnazionePopup.show();
			}
		});
		opzioniInvioAssegnazioneButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !isEffettuataAssegnazione() && showFlgAssegnaAlDestinatario && flgAssegnaAlDestinatarioItem.getValueAsBoolean() /*&& !((DestinatarioProtEntrataItem) getItem()).getShowAggiungiDestInAssegnatariButton()*/;
			}
		});

//		final ImgButtonItem aggiungiDestInAssegnatariButton = new ImgButtonItem("aggiungiDestInAssegnatariButton", "protocollazione/addAssegnatari.png",
//				((DestinatarioProtEntrataItem) getItem()).getTitleAggiungiDestInAssegnatariButton());
//		aggiungiDestInAssegnatariButton.setAlwaysEnabled(true);
//		aggiungiDestInAssegnatariButton.setColSpan(1);
//		aggiungiDestInAssegnatariButton.addIconClickHandler(new IconClickHandler() {
//
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				cercaInRubricaAfterChanged = false;				
//				if (flgAssegnaAlDestinatarioItem.getValue() != null && flgAssegnaAlDestinatarioItem.getValueAsBoolean()) {
//					((DestinatarioProtEntrataItem) getItem()).manageAssegnaAlDestinatario(new Record(mDynamicForm.getValues()));
//				}
//			}
//		});
//		aggiungiDestInAssegnatariButton.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				aggiungiDestInAssegnatariButton.setPrompt(((DestinatarioProtEntrataItem) getItem()).getTitleAggiungiDestInAssegnatariButton());
//				return showFlgAssegnaAlDestinatario
//						&& ((DestinatarioProtEntrataItem) getItem()).getShowAggiungiDestInAssegnatariButtonInCanvas(mDynamicForm.getValuesAsRecord());
//			}
//		});
		
		showMembriGruppoButton = new ImgButtonItem("showMembriGruppoButton", "menu/gruppi_soggetti.png", "Visualizza membri gruppo");
		showMembriGruppoButton.setAlwaysEnabled(true);
		showMembriGruppoButton.setColSpan(1);
		showMembriGruppoButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showMembriGruppo();
			}
		});
		showMembriGruppoButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				
				Record record = new Record();
				
				if(mDynamicForm.getValueAsString("gruppoSalvato") != null && gruppiDestinatarioItem.getValueAsString() != null && 
						mDynamicForm.getValueAsString("gruppoSalvato").equals(gruppiDestinatarioItem.getValueAsString())) {
						record.setAttribute("idUd", ((DestinatarioProtItem) getItem()).getIdUdProtocollo() != null ?
							((DestinatarioProtItem) getItem()).getIdUdProtocollo() : "");
				}
				record.setAttribute("gruppo", gruppiDestinatarioItem.getValueAsString());
				record.setAttribute("nomeGruppo", gruppiDestinatarioItem.getDisplayValue());
				record.setAttribute("flgAssCondDest", "D");
				record.setAttribute("tipologiaMembro", "D");
				new MembriGruppoUdWindow(record);
			}
		});

		mDynamicForm.setFields(
			idDestinatarioItem, 
			idSoggettoItem, 
			idUoSoggettoItem, 
			idUserSoggettoItem, 
			idScrivaniaSoggettoItem, 
			idAssegnatarioItem, 
			idDestInvioCCItem,
			flgSelXAssegnazioneItem,
			flgAssegnaAlDestinatarioXNuovaProtComeCopiaItem,
			gruppoSalvatoItem,
			codRapidoChangedHiddenItem,
			tipoDestinatarioItem,
			iconaFlgInOrganigrammaItem, 
			codRapidoDestinatarioItem, 
			lookupRubricaButton, 
			lookupOrganigrammaButton, 
			organigrammaDestinatarioItem,
			denominazioneDestinatarioItem,
			cognomeDestinatarioItem, 
			nomeDestinatarioItem, 
			codfiscaleDestinatarioItem,
			showIndirizzoMailDestinatarioButton,
			indirizzoMailDestinatarioItem,
			gruppiDestinatarioItem, 
			idGruppoInternoItem, 
			idGruppoEsternoItem,
			showMembriGruppoButton, 				
			destinatariPreferitiItem,
			cercaInRubricaButton, 
			cercaInOrganigrammaButton, 
			salvaInRubricaButton, 
			visualizzaInRubricaButton, 
			// check assegnazione
			flgAssegnaAlDestinatarioItem, 
			flgAssegnaAlDestinatarioReadOnlyHiddenItem,
			opzioniInvioAssegnazioneHiddenItem, 
			opzioniInvioAssegnazioneButton,
			// check c.c.
			opzioniInvioCondivisioneHiddenItem,
			flgPCItem, 
			flgPCReadOnlyHiddenItem,
			opzioniInvioPCButton
			
		);		

		mDynamicForm.setNumCols(30);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100");

	}
	
	public String getTitleFlgAssegnaAlDestinatarioItem() {
		return I18NUtil.getMessages().protocollazione_detail_flgAssegnaItem_title();
	}
	
	public boolean showFlgAssegnaAlDestinatarioItem() {
		if(isEffettuataAssegnazione()) {
			return true;
		}			
		if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi() && isEffettuatoInvioInCC()) {
			return false;
		}
		return showFlgAssegnaAlDestinatario /*&& !((DestinatarioProtEntrataItem) getItem()).getShowAggiungiDestInAssegnatariButton()*/;
	}
	
	public String getTitleFlgPCItem() {
		return AurigaLayout.getParametroDBAsBoolean("FLG_CC_SOLO_PER_DEST_IN_ORGANIGRAMMA") ? I18NUtil.getMessages().protocollazione_detail_flgCCItem_title() : I18NUtil.getMessages().protocollazione_detail_flgPCItem_title();		
	}
	
	public boolean showFlgPCItem() {
		if(AurigaLayout.getParametroDBAsBoolean("SHOW_CC_IN_DEST_REG")) {
			if(isEffettuatoInvioInCC()) {
				return true;
			}
			if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi() && isEffettuataAssegnazione()) {
				return false;
			}		
			if(AurigaLayout.getParametroDBAsBoolean("FLG_CC_SOLO_PER_DEST_IN_ORGANIGRAMMA")) {											
				return showFlgAssegnaAlDestinatario;
			} else {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean validate() {
		
		boolean valid = true;
		mDynamicForm.setShowInlineErrors(true);

		if(((DestinatarioProtItem) getItem()).isForzaAssegnazioneCondivisione() && showFlgAssegnaAlDestinatarioItem() && showFlgPCItem()) {
			boolean flgAssegnaAlDestinatario = mDynamicForm.getValue("flgAssegnaAlDestinatario") != null ? (Boolean) mDynamicForm.getValue("flgAssegnaAlDestinatario") : false;
			boolean flgPC = mDynamicForm.getValue("flgPC") != null ? (Boolean) mDynamicForm.getValue("flgPC") : false;
			if(!flgAssegnaAlDestinatario && !flgPC) {
				String error = "Obbligatorio spuntare uno tra \"" + getTitleFlgAssegnaAlDestinatarioItem() + "\" e \"" + getTitleFlgPCItem() + "\"";
				mDynamicForm.setFieldErrors("flgAssegnaAlDestinatario", error);
				mDynamicForm.setFieldErrors("flgPC", error);
				valid = false;
			} 
		}
	
		return valid;
	}
	
	public boolean isEffettuataAssegnazione() {
		//TODO invece di guardare idAssegnatario posso guardare tutti i record di listaAssegnazioniSalvate
		String idAssegnatario = mDynamicForm.getValueAsString("idAssegnatario") != null ? mDynamicForm.getValueAsString("idAssegnatario") : null;
		if(idAssegnatario != null && !"".equals(idAssegnatario)) {
			String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto") != null ? mDynamicForm.getValueAsString("idUoSoggetto") : "";
			String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto") != null ? mDynamicForm.getValueAsString("idScrivaniaSoggetto") : "";
			String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto") != null ? mDynamicForm.getValueAsString("idUserSoggetto") : "";
			if((idAssegnatario.startsWith("UO") && idUoSoggetto.equals(idAssegnatario.substring(2))) ||
			   (idAssegnatario.startsWith("SV") && idScrivaniaSoggetto.equals(idAssegnatario.substring(2))) ||
			   (idAssegnatario.startsWith("UT") && idUserSoggetto.equals(idAssegnatario.substring(2)))) {
				return true;
			}
			String idGruppoInterno = ((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma() && mDynamicForm.getValueAsString("idGruppoInterno") != null ? mDynamicForm.getValueAsString("idGruppoInterno") : "";
			if(idAssegnatario.startsWith("LD") && idGruppoInterno.equals(idAssegnatario.substring(2))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEffettuatoInvioInCC() {
		//TODO invece di guardare idDestInvioCC posso guardare tutti i record di listaDestInvioCCSalvati
		String idDestInvioCC = mDynamicForm.getValueAsString("idDestInvioCC") != null ? mDynamicForm.getValueAsString("idDestInvioCC") : null;
		if(idDestInvioCC != null && !"".equals(idDestInvioCC)) {
			String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto") != null ? mDynamicForm.getValueAsString("idUoSoggetto") : "";
			String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto") != null ? mDynamicForm.getValueAsString("idScrivaniaSoggetto") : "";
			String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto") != null ? mDynamicForm.getValueAsString("idUserSoggetto") : "";
			if((idDestInvioCC.startsWith("UO") && idUoSoggetto.equals(idDestInvioCC.substring(2))) ||
			   (idDestInvioCC.startsWith("SV") && idScrivaniaSoggetto.equals(idDestInvioCC.substring(2))) ||
			   (idDestInvioCC.startsWith("UT") && idUserSoggetto.equals(idDestInvioCC.substring(2)))) {				
				return true;
			}
			String idGruppoInterno = ((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma() && mDynamicForm.getValueAsString("idGruppoInterno") != null ? mDynamicForm.getValueAsString("idGruppoInterno") : "";
			if(idDestInvioCC.startsWith("LD") && idGruppoInterno.equals(idDestInvioCC.substring(2))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPresenteInOrganigramma() {
		String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto");
		if (idUoSoggetto == null)
			idUoSoggetto = "";
		String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto");
		if (idUserSoggetto == null)
			idUserSoggetto = "";
		String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto");
		if (idScrivaniaSoggetto == null)
			idScrivaniaSoggetto = "";
		// Visualizzo solo se uno dei 3 campi idUoSoggetto,idUserSoggetto,idScrivaniaSoggetto e' valorizzato
		return (!idUoSoggetto.equalsIgnoreCase("") || !idUserSoggetto.equalsIgnoreCase("") || !idScrivaniaSoggetto.equalsIgnoreCase(""));
	}

	public class DestinatarioLookupSoggettiPopup extends LookupSoggettiPopup {

		public DestinatarioLookupSoggettiPopup(Record record, String tipoDestinatario) {
			super(record, tipoDestinatario, true);
		}
		
		@Override
		public String getFinalita() {	
			return "SEL_SOGG_INT";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordRubrica(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

		@Override
		public String[] getTipiAmmessi() {
			return new String[] { "UOI", "UP" };
		}

	}

	public class DestinatarioLookupOrganigramma extends LookupOrganigrammaPopup {

		public DestinatarioLookupOrganigramma(Record record) {
			super(record, true);
		}
		
		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordOrganigramma(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
		
		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVDest");
		}
		
		@Override
		public String getFinalita() {	
			if(showSelectOrganigramma()) {
				return FINALITA_SELECT_ORGANIGRAMMA;
			}
			if(((DestinatarioProtItem) getItem()).isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
				return ((DestinatarioProtItem) getItem()).isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO";
			} 	
			return "MITT_DEST";
		}
		
		@Override
		public String getIdUd() {
			return ((DestinatarioProtItem) getItem()).getIdUdProtocollo();
		}

	}

	protected void clearFormSoggettoValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoDestinatario", "");
		lRecord.setAttribute("denominazioneDestinatario", "");
		lRecord.setAttribute("cognomeDestinatario", "");
		lRecord.setAttribute("nomeDestinatario", "");
		lRecord.setAttribute("codfiscaleDestinatario", "");		
		lRecord.setAttribute("indirizzoMailDestinatario", "");
		mDynamicForm.setValues(lRecord.toMap());
		((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
	}

	protected void clearIdSoggetto() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("idDestinatario", "");		
		lRecord.setAttribute("idSoggetto", "");
		lRecord.setAttribute("idUoSoggetto", "");
		lRecord.setAttribute("idUserSoggetto", "");
		lRecord.setAttribute("idScrivaniaSoggetto", "");
		lRecord.setAttribute("flgSelXAssegnazione", "");		
		lRecord.setAttribute("codRapidoDestinatario", "");
		mDynamicForm.setValues(lRecord.toMap());
		mDynamicForm.clearValue("flgAssegnaAlDestinatario");
		((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
	}

	protected boolean toShowDenominazione(String tipoSoggetto) {
		return tipoSoggetto == null || "".equals(tipoSoggetto) || (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
	}
	
	protected boolean toShowDestPref(String tipoSoggetto) {
		return tipoSoggetto != null && !"".equals(tipoSoggetto)  && "PREF".equalsIgnoreCase(tipoSoggetto);
	}

	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		return ((DestinatarioProtItem) getItem()).isPersonaGiuridica(tipoSoggetto);
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		return ((DestinatarioProtItem) getItem()).isPersonaFisica(tipoSoggetto);
	}
	
	protected void cercaInRubrica() {
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", denominazioneDestinatarioItem.isVisible() ? denominazioneDestinatarioItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto", cognomeDestinatarioItem.isVisible() ? cognomeDestinatarioItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeDestinatarioItem.isVisible() ? nomeDestinatarioItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codfiscaleDestinatarioItem.isVisible() ? codfiscaleDestinatarioItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", tipoDestinatarioItem.getValue());
		cercaInRubrica(lRecord, true);
	}
	
	protected void cercaInRubricaAfterChangedField(final Record record) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CERCA_IN_RUBRICA")) {			
			Timer t1 = new Timer() {
				public void run() {
					if(((DestinatarioProtItem) getItem()).isCercaInRubricaAfterChanged()) {
						if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
							cercaInOrganigramma(record, false);
						} else {
							cercaInRubrica(record, false);
						}
					}
				}
			};
			String delay = AurigaLayout.getParametroDB("CERCA_IN_RUBRICA_DELAY");
			t1.schedule((delay != null && !"".equals(delay)) ? Integer.parseInt(delay) : 1000);		
		}
	}
	
	protected void cercaInRubrica(final Record record, final boolean showLookupWithNoResults) {
		cercaSoggetto(record, new CercaSoggettoServiceCallback() {
			
			@Override
			public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
				if(showLookupWithNoResults || trovatiSoggMultipliInRicerca) {
					DestinatarioLookupSoggettiPopup lookupRubricaPopup = new DestinatarioLookupSoggettiPopup(record, tipoDestinatarioItem.getValueAsString());
					lookupRubricaPopup.show();
				}
			}
		});
	}
	
	protected void cercaInOrganigramma() {
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", denominazioneDestinatarioItem.isVisible() ? denominazioneDestinatarioItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto", cognomeDestinatarioItem.isVisible() ? cognomeDestinatarioItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeDestinatarioItem.isVisible() ? nomeDestinatarioItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codfiscaleDestinatarioItem.isVisible() ? codfiscaleDestinatarioItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", tipoDestinatarioItem.getValue());
		cercaInOrganigramma(lRecord, true);
	}
	
	protected void cercaInOrganigramma(final Record record, final boolean showLookupWithNoResults) {
		record.setAttribute("flgInOrganigramma", "UO;UT");
		if (tipoDestinatarioItem.getValue() != null) {
			if ("UOI".equals(tipoDestinatarioItem.getValue())) {
				record.setAttribute("flgInOrganigramma", "UO");
			} else if ("UP".equals(tipoDestinatarioItem.getValue())) {
				record.setAttribute("flgInOrganigramma", "UT");
			}
		}
		cercaSoggetto(record, new CercaSoggettoServiceCallback() {

			@Override
			public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
				if(showLookupWithNoResults || trovatiSoggMultipliInRicerca) {						
					DestinatarioLookupOrganigramma lookupOrganigrammaPopup = new DestinatarioLookupOrganigramma(record);
					lookupOrganigrammaPopup.show();
				} else if(!showLookupWithNoResults && ((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
					Layout.addMessage(new MessageBean("Soggetto non presente in organigramma", "", MessageType.ERROR));
					mDynamicForm.setFieldErrors("codRapidoDestinatario", "Soggetto non presente in organigramma");
				}
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("tipiAmmessi", "UOI,UP");
		if(((DestinatarioProtItem) getItem()).isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
			lGwtRestService.addParam("finalita", ((DestinatarioProtItem) getItem()).isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO");
		} else {
			lGwtRestService.addParam("finalita", "MITT_DEST");
		}		
		lGwtRestService.addParam("idUd", ((DestinatarioProtItem) getItem()).getIdUdProtocollo());
		lGwtRestService.call(lRecord, callback);
	}

	protected void cercaSoggettoLD() {
		mDynamicForm.setValue("gruppiDestinatario", (String) null);
		mDynamicForm.setValue("idGruppoInterno", (String) null);
		mDynamicForm.setValue("idGruppoEsterno", (String) null);
		mDynamicForm.setValue("flgSelXAssegnazione", "");
		mDynamicForm.clearErrors(true);
		final String value = codRapidoDestinatarioItem.getValueAsString().toUpperCase();
		if (value != null && !"".equals(value)) {
			SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboGruppoSoggettiDataSource", "idGruppo", FieldType.TEXT,
					new String[] { "codiceRapidoGruppo", "nomeGruppo" }, true);
			lGwtRestDataSource.extraparam.put("isGruppiDestinatari", "true");
			lGwtRestDataSource.extraparam.put("usaFlagSoggettiInterni", "S");
			if(((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma()) {
				lGwtRestDataSource.extraparam.put("flgSoloGruppiSoggInt", "2");
			}			
			lGwtRestDataSource.extraparam.put("codiceRapidoGruppo", value);
			lGwtRestDataSource.extraparam.put("abilSelDestinatariReg", "true");
			lGwtRestDataSource.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String codiceRapido = data.get(i).getAttribute("codiceRapidoGruppo");
							if (codiceRapido != null && value.equalsIgnoreCase(codiceRapido)) {
								mDynamicForm.setValue("codRapidoDestinatario", data.get(i).getAttribute("codiceRapidoGruppo"));
								mDynamicForm.setValue("gruppiDestinatario", data.get(i).getAttribute("idGruppo"));
								mDynamicForm.setValue("idGruppoInterno", data.get(i).getAttribute("idSoggettiInterni"));
								mDynamicForm.setValue("idGruppoEsterno", data.get(i).getAttribute("idSoggettiNonInterni"));
								mDynamicForm.setValue("flgSelXAssegnazione", data.get(i).getAttribute("flgSelXAssegnazione"));
								showHideFlgAssegnaAlDestinatario(true);								
								((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setFieldErrors("codRapidoDestinatario", "Gruppo inesistente");						
					}
				}
			});
		}
	}

	// public String calcolaTipoSoggetto(String flgPersonaFisica, String codTipoSoggetto) {
	// String tipoSoggetto = null;
	// if(flgPersonaFisica==null) flgPersonaFisica = "";
	// if(codTipoSoggetto==null) codTipoSoggetto = "";
	// if (codTipoSoggetto.equalsIgnoreCase("UOI")){
	// tipoSoggetto = "UOI";
	// }
	// else if (codTipoSoggetto.equalsIgnoreCase("UP")){
	// tipoSoggetto = "UP";
	// }
	// else if (codTipoSoggetto.equalsIgnoreCase("LD")){
	// tipoSoggetto = "LD";
	// }
	// return tipoSoggetto;
	// }

	public String calcolaTipoSoggetto(String tipo) {
		String tipoSoggetto = null;
		if ("UO;UOI".equals(tipo)) {
			tipoSoggetto = AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UOI";			
		} else if ("UP".equals(tipo)) {
			tipoSoggetto = AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UP";
		} /*
		 * else if("LD".equals(tipo)){ tipoSoggetto = "LD"; }
		 */
		return tipoSoggetto;
	}

	public boolean isValorizzatoSoggPerAssegnazione() {
		String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto");
		if (idUoSoggetto == null) {
			idUoSoggetto = "";
		}
//		String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto");
//		if (idUserSoggetto == null) {
//	 		idUserSoggetto = "";
//		}
		String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto");
		if (idScrivaniaSoggetto == null) {
			idScrivaniaSoggetto = "";
		}
		String idGruppoInterno = ((DestinatarioProtItem) getItem()).getFlgSoloInOrganigramma() ? mDynamicForm.getValueAsString("idGruppoInterno") : null;
		if (idGruppoInterno == null) {
			idGruppoInterno = "";
		}
		String supportoOriginale = ((DestinatarioProtItem) getItem()).getSupportoOriginaleProt();
		if(supportoOriginale != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "cartaceo".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "digitale".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "misto".equals(supportoOriginale))) {
				return !idUoSoggetto.equalsIgnoreCase("");
			}
		}
		return (!idUoSoggetto.equalsIgnoreCase("") /* || !idUserSoggetto.equalsIgnoreCase("") */|| !idScrivaniaSoggetto.equalsIgnoreCase("") || !idGruppoInterno.equalsIgnoreCase(""));
	}
	
	public void showHideFlgAssegnaAlDestinatario(boolean afterChangedDest) {		
		// Visualizzo solo se uno dei 3 campi idUoSoggetto, idUserSoggetto, idScrivaniaSoggetto e' valorizzato
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if (((DestinatarioProtItem) getItem()).getShowFlgAssegnaAlDestinatario() &&  isValorizzatoSoggPerAssegnazione()) {
			if (!fromLoadDett) {
				boolean flgSelXAssegnazione = mDynamicForm.getValueAsString("flgSelXAssegnazione") != null && "1".equals(mDynamicForm.getValueAsString("flgSelXAssegnazione"));
				if(((DestinatarioProtItem) getItem()).isAttivoAssegnatarioUnicoProt() && ((DestinatarioProtItem) getItem()).getNroAssegnazioniProt() > 0) {
					flgSelXAssegnazione = false;
				} 
				if(((DestinatarioProtItem) getItem()).isProtInModalitaWizard()) {
					if(((DestinatarioProtItem) getItem()).isSupportoOriginaleProtValorizzato() && ((DestinatarioProtItem) getItem()).isAttivoAssegnatarioUnicoCartaceoProt() && ((DestinatarioProtItem) getItem()).getNroAssegnazioniProt() > 0) {
						flgSelXAssegnazione = false;
					}
				}
				if (mDynamicForm.getValue("flgAssegnaAlDestinatario") == null) {
					if(mDynamicForm.getValue("flgAssegnaAlDestinatarioXNuovaProtComeCopia") != null) {
						boolean flgAssegnaAlDestinatarioXNuovaProtComeCopia = (Boolean) mDynamicForm.getValue("flgAssegnaAlDestinatarioXNuovaProtComeCopia");
						mDynamicForm.setValue("flgAssegnaAlDestinatario", flgAssegnaAlDestinatarioXNuovaProtComeCopia);
						((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
					} else {
						// se il soggetto è selezionabile per l'assegnazione allora setto il check al valore di default
						if(flgSelXAssegnazione) {
							mDynamicForm.setValue("flgAssegnaAlDestinatario", ((DestinatarioProtItem) getItem()).getFlgAssegnaAlDestinatarioDefault());
							if(((DestinatarioProtItem) getItem()).isFlgAssegnazioneCondivisioneMutuamenteEsclusivi() && ((DestinatarioProtItem) getItem()).getFlgAssegnaAlDestinatarioDefault()) {
								mDynamicForm.setValue("flgPC", false);
							}
							((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
						}
					}
				} else if(afterChangedDest && !flgSelXAssegnazione) {
//					mDynamicForm.setValue("flgAssegnaAlDestinatario", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
					  														  // il valore di default, quindi utilizzo il clearValue()
					mDynamicForm.clearValue("flgAssegnaAlDestinatario");
					((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
				}
			}
			showFlgAssegnaAlDestinatario = true;
		} else {
//			mDynamicForm.setValue("flgAssegnaAlDestinatario", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
			  														  // il valore di default, quindi utilizzo il clearValue()
			mDynamicForm.clearValue("flgAssegnaAlDestinatario");
			((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
			showFlgAssegnaAlDestinatario = false;
		}
		mDynamicForm.markForRedraw();
	}

	public void setFormValuesFromRecordRubrica(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdSoggetto();
		clearFormSoggettoValues();
		mDynamicForm.clearErrors(true);
		// String flgPersFisica = record.getAttribute("flgPersFisica") != null ? record.getAttribute("flgPersFisica") : "";
		// String codTipoSoggetto = record.getAttribute("codTipoSoggInt");
		// if(codTipoSoggetto == null || "".equals(codTipoSoggetto)) {
		// if("1".equals(flgPersFisica)) {
		// if(record.getAttributeAsBoolean("flgUnitaDiPersonale")) {
		// codTipoSoggetto = "UP";
		// }
		// } else {
		// codTipoSoggetto = record.getAttribute("tipologia");
		// if(codTipoSoggetto != null && !"".equals(codTipoSoggetto)) {
		// String[] tokens = new StringTokenizer(codTipoSoggetto, ";").getTokens();
		// if(tokens.length == 2) {
		// codTipoSoggetto = tokens[1];
		// }
		// }
		// }
		// }
		//
		// String tipoDestinatario = calcolaTipoSoggetto(record.getAttribute("flgPersFisica"), codTipoSoggetto);
		String idSoggetto = record.getAttribute("idSoggetto");
		String tipoDestinatario = calcolaTipoSoggetto(record.getAttribute("tipo"));
		if (tipoDestinatario != null) {
			mDynamicForm.setValue("tipoDestinatario", tipoDestinatario);
			mDynamicForm.setValue("codRapidoDestinatario", record.getAttribute("codiceRapido"));
			if (isPersonaGiuridica(tipoDestinatario)) {
				mDynamicForm.setValue("denominazioneDestinatario", record.getAttribute("denominazione"));
			} else if (isPersonaFisica(tipoDestinatario)) {
				mDynamicForm.setValue("cognomeDestinatario", record.getAttribute("cognome"));
				mDynamicForm.setValue("nomeDestinatario", record.getAttribute("nome"));
				mDynamicForm.setValue("codfiscaleDestinatario", record.getAttribute("codiceFiscale"));
			}
			mDynamicForm.setValue("idDestinatario", idSoggetto);
			mDynamicForm.setValue("idSoggetto", idSoggetto);
			mDynamicForm.setValue("idUoSoggetto", record.getAttribute("idUo"));
			mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
			mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttribute("idScrivania"));
			mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
			mDynamicForm.clearValue("flgAssegnaAlDestinatario");
			((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
			setIndirizzoCompletoFromRecordRubrica(record);
			showHideFlgAssegnaAlDestinatario(false);
			((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
		} else {
			// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/ uscita /
			// interna>
			mDynamicForm.setFieldErrors("codRapidoDestinatario", I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("destinatario", "in entrata"));
		}
	}

	public void setFormValuesFromRecordOrganigramma(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdSoggetto();
		clearFormSoggettoValues();
		mDynamicForm.clearErrors(true);
		String idUoSvUt = record.getAttribute("idUoSvUt");
		if (idUoSvUt == null || "".equals(idUoSvUt)) {
			idUoSvUt = record.getAttribute("idFolder");
		}
		mDynamicForm.setValue("idDestinatario", idUoSvUt);
		mDynamicForm.setValue("idSoggetto", record.getAttribute("idRubrica"));
		String tipo = record.getAttribute("tipo");
		if (tipo.startsWith("UO")) {			
			mDynamicForm.setValue("tipoDestinatario", AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UOI");
			mDynamicForm.setValue("codRapidoDestinatario", record.getAttribute("codRapidoUo"));
			mDynamicForm.setValue("denominazioneDestinatario", record.getAttribute("descrUoSvUt"));
			mDynamicForm.setValue("idUoSoggetto", idUoSvUt);
		} else {
			mDynamicForm.setValue("tipoDestinatario", AurigaLayout.getParametroDBAsBoolean("DEST_INT_CON_SELECT") ? "UP_UOI" : "UP");		
			if(record.getAttribute("codRapidoSvUt") != null && !"".equals(record.getAttribute("codRapidoSvUt"))) {
				mDynamicForm.setValue("codRapidoDestinatario", record.getAttribute("codRapidoSvUt"));
			} else {
				mDynamicForm.setValue("codRapidoDestinatario", record.getAttribute("codRapidoUo"));
			}
			String cognomeNome = record.getAttribute("descrUoSvUt");
			mDynamicForm.setValue("denominazioneDestinatario", cognomeNome);
			if (cognomeNome != null && !"".equals(cognomeNome)) {
				String[] tokens = new StringSplitterClient(cognomeNome, "|").getTokens();
				if (tokens.length == 2) {
					mDynamicForm.setValue("cognomeDestinatario", tokens[0].trim());
					mDynamicForm.setValue("nomeDestinatario", tokens[1].trim());
				}
			}
			mDynamicForm.setValue("codfiscaleDestinatario", record.getAttribute("codFiscale"));
			if (tipo.startsWith("UT")) {
				mDynamicForm.setValue("idUserSoggetto", idUoSvUt);
			} else if (tipo.startsWith("SV")) {
				mDynamicForm.setValue("idScrivaniaSoggetto", idUoSvUt);
			}
		}
		if(showSelectOrganigramma()) {
			String flgUoSvUt = tipo;
			int pos = flgUoSvUt.indexOf("_");
			if (pos != -1) {
				flgUoSvUt = flgUoSvUt.substring(0, pos);
			}
			mDynamicForm.setValue("organigrammaDestinatario", flgUoSvUt + idUoSvUt);
			manageLoadSelectOrganigrammaInEditRecord(mDynamicForm.getValuesAsRecord());
		}
		mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
		mDynamicForm.clearValue("flgAssegnaAlDestinatario");
		((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
		showHideFlgAssegnaAlDestinatario(false);
		((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
		if(showSelectOrganigramma()) {	
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
				@Override
				public void execute() {
					organigrammaDestinatarioItem.fetchData();
				}
			});
		}
		mDynamicForm.markForRedraw();
	}

	@Override
	public void editRecord(Record record) {
		if ("LD".equalsIgnoreCase(record.getAttribute("tipoDestinatario"))) {
			if (record.getAttribute("gruppiDestinatario") != null && !"".equals(record.getAttributeAsString("gruppiDestinatario")) &&
				record.getAttribute("denominazioneDestinatario") != null && !"".equals(record.getAttributeAsString("denominazioneDestinatario")) ) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("gruppiDestinatario"), record.getAttribute("denominazioneDestinatario"));
				gruppiDestinatarioItem.setValueMap(valueMap);
			}
			GWTRestDataSource gruppoDS = (GWTRestDataSource) gruppiDestinatarioItem.getOptionDataSource();
			if (record.getAttribute("gruppiDestinatario") != null && !"".equals(record.getAttributeAsString("gruppiDestinatario"))) {
				gruppoDS.addParam("idGruppo", record.getAttributeAsString("gruppiDestinatario"));
			} else {
				gruppoDS.addParam("idGruppo", null);
			}
			gruppiDestinatarioItem.setOptionDataSource(gruppoDS);
		} else if ("PREF".equalsIgnoreCase(record.getAttribute("tipoDestinatario"))) {
			if (record.getAttribute("idDestPref") != null && !"".equals(record.getAttributeAsString("idDestPref")) &&
				record.getAttribute("denominazioneDestinatario") != null && !"".equals(record.getAttributeAsString("denominazioneDestinatario")) ) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("idDestPref"), record.getAttribute("denominazioneDestinatario"));
				destinatariPreferitiItem.setValueMap(valueMap);
			}
			GWTRestDataSource destinatariPreferitiDS = (GWTRestDataSource) destinatariPreferitiItem.getOptionDataSource();
			if (record.getAttribute("idDestPref") != null && !"".equals(record.getAttributeAsString("idDestPref"))) {
				destinatariPreferitiDS.addParam("idDestPref", record.getAttributeAsString("idDestPref"));
			} else {
				destinatariPreferitiDS.addParam("idDestPref", null);
			}
			destinatariPreferitiItem.setOptionDataSource(destinatariPreferitiDS);
		}
		showFlgAssegnaAlDestinatario = false;
		if(showSelectOrganigramma(record.getAttribute("tipoDestinatario"))) {			
			manageLoadSelectOrganigrammaInEditRecord(record);
			if(record.getAttribute("tipoDestinatario") != null	&& ("UOI".equals(record.getAttribute("tipoDestinatario")) || "UP".equals(record.getAttribute("tipoDestinatario")))) {
				record.setAttribute("tipoDestinatario", "UP_UOI");
			}
		} else {
			record.setAttribute("organigrammaDestinatario", ""); // per evitare che poi in salvataggio venga passato il valore caricato da dettaglio, nel caso in cui la select da organigramma non sia attiva 
		}
		super.editRecord(record);
		showHideFlgAssegnaAlDestinatario(false);
		if(isChangedRecord(record)) {			
			((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
		}
	}
	
	public void manageLoadSelectOrganigrammaInEditRecord(Record record) {
		String tipoDestinatario = record.getAttribute("tipoDestinatario") != null ? record.getAttribute("tipoDestinatario") : "";
		if (record.getAttribute("organigrammaDestinatario") != null && !"".equals(record.getAttributeAsString("organigrammaDestinatario"))
				&& record.getAttribute("denominazioneDestinatario") != null && !"".equals(record.getAttributeAsString("denominazioneDestinatario"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			if ("UP_UOI".equals(tipoDestinatario) && record.getAttribute("organigrammaDestinatario").startsWith("UO")) {
				valueMap.put(record.getAttribute("organigrammaDestinatario"), "<b>" + record.getAttribute("denominazioneDestinatario") + "</b>");
			} else if ("UOI".equals(tipoDestinatario)) {
				valueMap.put(record.getAttribute("organigrammaDestinatario"), "<b>" + record.getAttribute("denominazioneDestinatario") + "</b>");
			} else {
				valueMap.put(record.getAttribute("organigrammaDestinatario"), record.getAttribute("denominazioneDestinatario"));
			}
			organigrammaDestinatarioItem.setValueMap(valueMap);
		}
		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaDestinatarioItem.getOptionDataSource();
		if (record.getAttribute("organigrammaDestinatario") != null && !"".equals(record.getAttributeAsString("organigrammaDestinatario"))
				&& ("UOI".equals(tipoDestinatario) || "UP".equals(tipoDestinatario))) {
			organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idDestinatario"));
			if(tipoDestinatario.equals("UOI")) {
				organigrammaDS.addParam("flgUoSv", "UO");					
			} else if(tipoDestinatario.equals("UP")) {
				organigrammaDS.addParam("flgUoSv", "SV");										
			}			
		} else {
			organigrammaDS.addParam("idUoSv", null);
			organigrammaDS.addParam("flgUoSv", null);
		}
		organigrammaDestinatarioItem.setOptionDataSource(organigrammaDS);
	}

	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);
			// String tipoDestinatario = calcolaTipoSoggetto(object.getAttribute("tipoSoggetto"), object.getAttribute("codTipoSoggetto"));
			String tipoDestinatario = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));
			if (tipoDestinatario != null) {
				mDynamicForm.setValue("tipoDestinatario", tipoDestinatario);
				// Pulisco i dati del soggetto
				mDynamicForm.setValue("idDestinatario", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");		
				mDynamicForm.clearValue("flgAssegnaAlDestinatario");
				((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
				mDynamicForm.setValue("denominazioneDestinatario", "");
				mDynamicForm.setValue("cognomeDestinatario", "");
				mDynamicForm.setValue("nomeDestinatario", "");
				mDynamicForm.setValue("codfiscaleDestinatario", "");
				mDynamicForm.setValue("indirizzoMailDestinatario", "");							
				if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codRapidoDestinatario", object.getAttribute("codRapidoSoggetto"));
				}
				if (object.getAttribute("denominazioneSoggetto") != null && !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("denominazioneDestinatario", object.getAttribute("denominazioneSoggetto"));
				}
				if (object.getAttribute("cognomeSoggetto") != null && !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("cognomeDestinatario", object.getAttribute("cognomeSoggetto"));
				}
				if (object.getAttribute("nomeSoggetto") != null && !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("nomeDestinatario", object.getAttribute("nomeSoggetto"));
				}
				if (object.getAttribute("codfiscaleSoggetto") != null && !object.getAttribute("codfiscaleSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codfiscaleDestinatario", object.getAttribute("codfiscaleSoggetto"));
				}
				if (object.getAttribute("idSoggetto") != null && !object.getAttribute("idSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idDestinatario", object.getAttribute("idSoggetto"));					
					mDynamicForm.setValue("idSoggetto", object.getAttribute("idSoggetto"));
				}
				if (object.getAttribute("idUoSoggetto") != null && !object.getAttribute("idUoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idUoSoggetto", object.getAttribute("idUoSoggetto"));
				}
				if (object.getAttribute("idUserSoggetto") != null && !object.getAttribute("idUserSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idUserSoggetto", object.getAttribute("idUserSoggetto"));
				}
				if (object.getAttribute("idScrivaniaSoggetto") != null && !object.getAttribute("idScrivaniaSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idScrivaniaSoggetto", object.getAttribute("idScrivaniaSoggetto"));
				}
				if (object.getAttribute("flgSelXAssegnazione") != null && !object.getAttribute("flgSelXAssegnazione").equalsIgnoreCase("")) {
					mDynamicForm.setValue("flgSelXAssegnazione", object.getAttribute("flgSelXAssegnazione"));
				}
				if (showItemsIndirizzo()) {
					setIndirizzoAfterFindSoggettoService(object);
				}				
				showHideFlgAssegnaAlDestinatario(true);
				((DestinatarioProtEntrataItem)getItem()).manageOnChanged();
				mDynamicForm.markForRedraw();
			} else {
				// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/ uscita /
				// interna>
				executeOnError(object.getAttribute("trovatiSoggMultipliInRicerca") != null && object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"));
			}
		}
	}
	
	public boolean isDestinatarioValorizzato() {
		if(showSelectOrganigramma()) {
			return organigrammaDestinatarioItem.getValueAsString() != null && !"".equals(organigrammaDestinatarioItem.getValueAsString());
		}
		String denominazione = denominazioneDestinatarioItem.isVisible() ? denominazioneDestinatarioItem.getValueAsString() : null;
		String cognome = cognomeDestinatarioItem.isVisible() ? cognomeDestinatarioItem.getValueAsString() : null;
		String nome = nomeDestinatarioItem.isVisible() ? nomeDestinatarioItem.getValueAsString() : null;
		return ((denominazione != null && !"".equals(denominazione)) || ((cognome != null && !"".equals(cognome)) && (nome != null && !"".equals(nome))));
	}

	public boolean isChangedAndValid() {
		return isChanged() && isDestinatarioValorizzato();
	}

	public boolean showItemsIndirizzo() {
		return ((DestinatarioProtEntrataItem) getItem()).getShowItemsIndirizzo();
	}
	
	public boolean isInBozza() {
		return ((DestinatarioProtItem) getItem()).isInBozza();
	}
	
	public void resetDefaultValueFlgAssegnaAlDestinatario() {
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if(!fromLoadDett) {
			mDynamicForm.clearValue("flgAssegnaAlDestinatario");
			((DestinatarioProtItem) getItem()).manageChangedFlgAssegnaAlDestinatario(mDynamicForm.getValuesAsRecord());
		}
	}

	@Override
	public void redraw() {
		super.redraw();
		showHideFlgAssegnaAlDestinatario(false);
	}
	
	/**
	 * Metodo per la visualizzazione del bottone Visualizza membri gruppo che si abilita se si è selezionata una 
	 * Lista di distribuzione valorizzata correttamente
	 */
	private boolean showMembriGruppo() {
		return ((tipoDestinatarioItem != null && tipoDestinatarioItem.getValueAsString() != null &&
				"LD".equals(tipoDestinatarioItem.getValueAsString())) && 
				(gruppiDestinatarioItem != null && gruppiDestinatarioItem.getValueAsString() != null
				 && !"".equals(gruppiDestinatarioItem.getValueAsString())));			
	}

}