/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

public class MittenteProtUscitaCanvas extends IndirizzoCanvas {
	
	public static final String FINALITA_SELECT_ORGANIGRAMMA = "MITTENTE_REG";

	private HiddenItem idMittenteItem;
	private HiddenItem idSoggettoItem; // id. in rubrica
	private HiddenItem idUoSoggettoItem;
	private HiddenItem idUserSoggettoItem;
	private HiddenItem idScrivaniaSoggettoItem;
	private HiddenItem idAssegnatarioItem;
	private HiddenItem flgSelXAssegnazioneItem;
	private HiddenItem flgAssegnaAlMittenteXNuovaProtComeCopiaItem;
	private HiddenItem codRapidoChangedHiddenItem;
	private SelectItem tipoMittenteItem;
	private ExtendedTextItem codRapidoMittenteItem;
	private FilteredSelectItemWithDisplay organigrammaMittenteItem;
	private ExtendedTextItem denominazioneMittenteItem;
	private ExtendedTextItem cognomeMittenteItem;
	private ExtendedTextItem nomeMittenteItem;
	private ExtendedTextItem codfiscaleMittenteItem;
	private ImgButtonItem salvaInRubricaButton;
	private ImgButtonItem visualizzaInRubricaButton;
	private CheckboxItem flgAssegnaAlMittenteItem;
	private HiddenItem opzioniInvioHiddenItem;
	
	public boolean showFlgAssegnaAlMittente = false;

	public MittenteProtUscitaCanvas(ReplicableItem item) {
		super(item);
	}
	
	public boolean showSelectOrganigramma() {
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if (fromLoadDett && !isPresenteInOrganigramma()) return false;
		return ((MittenteProtItem)getItem()).getFlgSoloInOrganigramma();
	}
	
	public String getTipoAssegnatari() {
		if (AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG")) {
			return "UO;SV";			
		}
		return "UO";
	}

	@Override
	public void buildMainForm() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idMittenteItem = new HiddenItem("idMittente");

		idSoggettoItem = new HiddenItem("idSoggetto");
		idUoSoggettoItem = new HiddenItem("idUoSoggetto");
		idUserSoggettoItem = new HiddenItem("idUserSoggetto");
		idScrivaniaSoggettoItem = new HiddenItem("idScrivaniaSoggetto");
		idAssegnatarioItem = new HiddenItem("idAssegnatario");
		flgSelXAssegnazioneItem = new HiddenItem("flgSelXAssegnazione");
		flgAssegnaAlMittenteXNuovaProtComeCopiaItem = new HiddenItem("flgAssegnaAlMittenteXNuovaProtComeCopia");
		codRapidoChangedHiddenItem = new HiddenItem("codRapidoChanged");
		
		// tipo
		tipoMittenteItem = new SelectItem("tipoMittente","Tipo mittente");
		LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tipoMittenteItem.setShowTitle(true);
			styleMap.put("", I18NUtil.getMessages().protocollazione_select_listmap_generic_value());
			tipoMittenteItem.setDefaultValue("");
		} else {
			tipoMittenteItem.setShowTitle(false);
		}		
		styleMap.put("UOI", I18NUtil.getMessages().protocollazione_select_listmap_UOI_value());
		if (AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG")) {
			styleMap.put("UP", I18NUtil.getMessages().protocollazione_select_listmap_UP_value());
		}
		tipoMittenteItem.setAllowEmptyValue(!AurigaLayout.getIsAttivaAccessibilita());
		// tipoMittenteItem.setRequired(true);
		tipoMittenteItem.setValueMap(styleMap);
		tipoMittenteItem.setWidth(150);
		tipoMittenteItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				tipoMittenteItem.setAttribute("oldValue", event.getOldValue());				
			}
		});
		tipoMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearErrors(true);
				String oldValue = tipoMittenteItem.getAttribute("oldValue") != null ? tipoMittenteItem.getAttribute("oldValue") : "";
				String value = event.getValue() != null ? (String) event.getValue() : "";
				// controllo se rispetto a prima sia cambiato il fatto che si veda o meno il campo denominazione
				boolean isChangedShowDenominazione = !(("".equals(oldValue) && toShowDenominazione(value)) || (toShowDenominazione(oldValue) && "".equals(value)) || (toShowDenominazione(oldValue) && toShowDenominazione(value)));
				// non faccio l'altro controllo perchè qui il soggetto è sempre interno				
				if (isChangedShowDenominazione) {
					Record lRecord = mDynamicForm.getValuesAsRecord();
					lRecord.setAttribute("codRapidoMittente", "");
					lRecord.setAttribute("denominazioneMittente", "");
					lRecord.setAttribute("cognomeMittente", "");
					lRecord.setAttribute("nomeMittente", "");
					lRecord.setAttribute("codfiscaleMittente", "");
//					lRecord.setAttribute("flgAssegnaAlMittente", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
					 													 // il valore di default, quindi utilizzo il clearValue()					
					lRecord.setAttribute("idMittente", "");		
					lRecord.setAttribute("idSoggetto", "");
					lRecord.setAttribute("idUoSoggetto", "");
					lRecord.setAttribute("idUserSoggetto", "");
					lRecord.setAttribute("idScrivaniaSoggetto", "");					
					lRecord.setAttribute("flgSelXAssegnazione", "");										
					mDynamicForm.setValues(lRecord.toMap());
					mDynamicForm.clearValue("flgAssegnaAlMittente");
					((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
					showHideFlgAssegnaAlMittente();
					((MittenteProtUscitaItem)getItem()).manageOnChanged();
				}
			}
		});
		tipoMittenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return true;
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
				if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
					if(isMittenteValorizzato() && !isPresenteInOrganigramma()) {
						return false;
					}
				}
				return true;
			}
		};
		lSoggPresenteInOrganigrammaValidator.setErrorMessage("Soggetto non presente in organigramma");

		// cod.rapido
		codRapidoMittenteItem = new ExtendedTextItem("codRapidoMittente", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoMittenteItem.setWidth(120);
		codRapidoMittenteItem.setColSpan(1);
		codRapidoMittenteItem.setValidators(lSoggPresenteInOrganigrammaValidator);		
		codRapidoMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(final ChangedEvent event) {
				mDynamicForm.setValue("idMittente", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");		
				mDynamicForm.clearValue("flgAssegnaAlMittente");
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
				if(showSelectOrganigramma()) {
					mDynamicForm.setValue("codRapidoChanged", true);
					mDynamicForm.setValue("organigrammaMittente", (String) null);
					mDynamicForm.setValue("tipoMittente", (String) null);						
					mDynamicForm.setValue("denominazioneMittente", (String) null);
					mDynamicForm.clearErrors(true);
					if (event.getValue() != null && !"".equals(event.getValue())) {
						organigrammaMittenteItem.fetchData(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								boolean trovato = false;
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codice = data.get(i).getAttribute("codice");
										String flgSelXFinalita = data.get(i).getAttribute("flgSelXFinalita");
										if (event.getValue().equals(codice) && (flgSelXFinalita == null || "1".equals(flgSelXFinalita))) {
											mDynamicForm.setValue("organigrammaMittente", data.get(i).getAttribute("id"));
											mDynamicForm.setValue("idMittente", data.get(i).getAttribute("idUo"));
											mDynamicForm.setValue("idSoggetto", data.get(i).getAttribute("idRubrica"));
											String typeNodo = data.get(i).getAttribute("typeNodo");
											if(typeNodo != null) {
												if(typeNodo.equals("UO")) {
													mDynamicForm.setValue("idUoSoggetto", data.get(i).getAttribute("idUo"));
													mDynamicForm.setValue("tipoMittente", "UOI");
														
												} else if(typeNodo.equals("SV")) {
													mDynamicForm.setValue("idScrivaniaSoggetto", data.get(i).getAttribute("idUo"));
													mDynamicForm.setValue("tipoMittente", "UP");													
												}
											}											
											mDynamicForm.setValue("flgSelXAssegnazione", data.get(i).getAttribute("flgSelXAssegnazione"));
											mDynamicForm.setValue("denominazioneMittente", data.get(i).getAttribute("descrizioneOrig"));
											mDynamicForm.setValue("cognomeMittente", data.get(i).getAttribute("cognome"));
											mDynamicForm.setValue("nomeMittente", data.get(i).getAttribute("nome"));
											mDynamicForm.setValue("codfiscaleMittente", data.get(i).getAttribute("codFiscale"));											
											mDynamicForm.clearErrors(true);
											trovato = true;
											((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
											showHideFlgAssegnaAlMittente();
											((MittenteProtUscitaItem) getItem()).manageOnChanged();
											break;
										}
									}
								}
								if (!trovato) {
									codRapidoMittenteItem.validate();
									codRapidoMittenteItem.blurItem();
								}
							}
						});
					} else {
						organigrammaMittenteItem.fetchData();
					}
				} else if (event.getValue() != null && !"".equals(event.getValue())) {
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoSoggetto", event.getValue());
					if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
						lRecord.setAttribute("flgInOrganigramma", "UO;UT");
						if (tipoMittenteItem.getValue() != null) {
							if ("UOI".equals(tipoMittenteItem.getValue())) {
								lRecord.setAttribute("flgInOrganigramma", "UO");
							} else if ("UP".equals(tipoMittenteItem.getValue())) {
								lRecord.setAttribute("flgInOrganigramma", "UT");
							}
						}
					}
					cercaSoggetto(lRecord, new CercaSoggettoServiceCallback() {

						@Override
						public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
							// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/
							// uscita / interna>
							if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
								mDynamicForm.setFieldErrors("codRapidoMittente", "Soggetto non presente in organigramma");
							} else {
								mDynamicForm.setFieldErrors("codRapidoMittente", I18NUtil.getMessages()
										.protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("mittente", "in uscita"));
							}
						}
					});					
				}
			}
		});
		
		SelectGWTRestDataSource organigrammaMittenteDS = new SelectGWTRestDataSource("LoadComboOrganigrammaDataSource", "id", FieldType.TEXT,
				new String[] { "descrizione" }, true);
		organigrammaMittenteDS.addParam("tipoAssegnatari", getTipoAssegnatari());
		organigrammaMittenteDS.addParam("finalita", FINALITA_SELECT_ORGANIGRAMMA);		
		organigrammaMittenteDS.addParam("idUd", ((MittenteProtItem) getItem()).getIdUdProtocollo());
		
		organigrammaMittenteItem = new FilteredSelectItemWithDisplay("organigrammaMittente", organigrammaMittenteDS) {

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
//				mDynamicForm.setValue("organigrammaMittente", record.getAttributeAsString("id"));
				mDynamicForm.setValue("codRapidoMittente", record.getAttributeAsString("codice"));
				mDynamicForm.setValue("idMittente", record.getAttributeAsString("idUo"));
				mDynamicForm.setValue("idSoggetto", record.getAttribute("idRubrica"));				
				String typeNodo = record.getAttributeAsString("typeNodo");
				if(typeNodo != null) {
					if(typeNodo.equals("UO")) {
						mDynamicForm.setValue("tipoMittente", "UOI");
						mDynamicForm.setValue("idUoSoggetto", record.getAttributeAsString("idUo"));
					} else if(typeNodo.equals("SV")) {
						mDynamicForm.setValue("tipoMittente", "UP");
						mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttributeAsString("idUo"));
					}					
				}				
				mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
				mDynamicForm.setValue("denominazioneMittente", record.getAttributeAsString("descrizioneOrig"));
				mDynamicForm.setValue("cognomeMittente", record.getAttribute("cognome"));
				mDynamicForm.setValue("nomeMittente", record.getAttribute("nome"));
				mDynamicForm.setValue("codfiscaleMittente", record.getAttribute("codFiscale"));
				mDynamicForm.clearErrors(true);
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaMittenteItem.fetchData();
					}
				});
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("organigrammaMittente", "");
				if(((MittenteProtUscitaItem)getItem()).isDimOrganigrammaNonStd()) {
					mDynamicForm.setValue("codRapidoMittente", AurigaLayout.getCodRapidoOrganigramma());
				} else {
					mDynamicForm.setValue("codRapidoMittente", "");
				}
				mDynamicForm.setValue("idMittente", "");
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("tipoMittente", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");
				mDynamicForm.setValue("denominazioneMittente", "");
				mDynamicForm.setValue("cognomeMittente", "");
				mDynamicForm.setValue("nomeMittente", "");
				mDynamicForm.setValue("codfiscaleMittente", "");
				mDynamicForm.clearErrors(true);				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaMittenteItem.fetchData();
					}
				});
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("organigrammaMittente", "");
					if(((MittenteProtUscitaItem)getItem()).isDimOrganigrammaNonStd()) {
						mDynamicForm.setValue("codRapidoMittente", AurigaLayout.getCodRapidoOrganigramma());
					} else {
						mDynamicForm.setValue("codRapidoMittente", "");
					}
					mDynamicForm.setValue("idMittente", "");
					mDynamicForm.setValue("idSoggetto", "");
					mDynamicForm.setValue("idUoSoggetto", "");
					mDynamicForm.setValue("idUserSoggetto", "");
					mDynamicForm.setValue("idScrivaniaSoggetto", "");
					mDynamicForm.setValue("tipoMittente", "");
					mDynamicForm.setValue("flgSelXAssegnazione", "");
					mDynamicForm.setValue("denominazioneMittente", "");
					mDynamicForm.setValue("cognomeMittente", "");
					mDynamicForm.setValue("nomeMittente", "");
					mDynamicForm.setValue("codfiscaleMittente", "");
					mDynamicForm.clearErrors(true);				
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							organigrammaMittenteItem.fetchData();
						}
					});
				}
			}
		};
		List<ListGridField> organigrammaMittentePickListFields = new ArrayList<ListGridField>();
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
			organigrammaMittentePickListFields.add(typeNodoField);
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
		if (((MittenteProtUscitaItem)getItem()).isDimOrganigrammaNonStd()) {
			codiceField.setCanFilter(false);
			// TextItem codiceFilterEditorType = new TextItem();
			// codiceFilterEditorType.setCanEdit(false);
			// codiceField.setFilterEditorType(codiceFilterEditorType);
		}
		organigrammaMittentePickListFields.add(codiceField);
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		organigrammaMittentePickListFields.add(descrizioneField);
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
			organigrammaMittentePickListFields.add(flgPuntoProtocolloField);
		}
		organigrammaMittenteItem.setPickListFields(organigrammaMittentePickListFields.toArray(new ListGridField[organigrammaMittentePickListFields.size()]));
		if (((MittenteProtUscitaItem)getItem()).isDimOrganigrammaNonStd()) {
			organigrammaMittenteItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimOrganigrammaNonStdMessage());
		} else {
			organigrammaMittenteItem.setFilterLocally(true);
		}
		
		organigrammaMittenteItem.setAutoFetchData(false);
		organigrammaMittenteItem.setFetchMissingValues(true);
		organigrammaMittenteItem.setAlwaysFetchMissingValues(true);
		organigrammaMittenteItem.setCachePickListResults(false);		
		organigrammaMittenteItem.setValueField("id");
		organigrammaMittenteItem.setOptionDataSource(organigrammaMittenteDS);
		organigrammaMittenteItem.setShowTitle(false);
		organigrammaMittenteItem.setWidth(450);
		organigrammaMittenteItem.setClearable(true);
		organigrammaMittenteItem.setShowIcons(true);
		organigrammaMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
				showHideFlgAssegnaAlMittente();
				((MittenteProtUscitaItem) getItem()).manageOnChanged();
			}
		});
		organigrammaMittenteItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		organigrammaMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showSelectOrganigramma();
			}
		});
		organigrammaMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

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
		ListGrid organigrammaMittentePickListProperties = organigrammaMittenteItem.getPickListProperties();
		organigrammaMittentePickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {
				String codRapidoMittente = mDynamicForm.getValueAsString("codRapidoMittente");
//				if (isDimOrgranigrammaNonStd() && (codRapidoMittente == null || "".equals(codRapidoMittente))) {
//					if(codRapidoMittenteItem.getCanEdit() != null && codRapidoMittenteItem.getCanEdit()) {
//						mDynamicForm.setFieldErrors("codRapidoMittente", "Filtro obbligatorio per popolare la lista di scelta");
//					}
//				}				
				GWTRestDataSource organigrammaMittenteDS = (GWTRestDataSource) organigrammaMittenteItem.getOptionDataSource();
				// organigrammaMittenteDS.addParam("ciToAdd", mDynamicForm.getValueAsString("organigrammaMittente"));
				organigrammaMittenteDS.addParam("codice", codRapidoMittente);
				organigrammaMittenteDS.addParam("finalita", FINALITA_SELECT_ORGANIGRAMMA);				
				organigrammaMittenteDS.addParam("idUd", ((MittenteProtItem) getItem()).getIdUdProtocollo());
				organigrammaMittenteDS.addParam("tipoAssegnatari", getTipoAssegnatari());
				organigrammaMittenteDS.addParam("uoProtocollante", ((MittenteProtUscitaItem) getItem()).getUoProtocollante());
				organigrammaMittenteItem.setOptionDataSource(organigrammaMittenteDS);
				organigrammaMittenteItem.invalidateDisplayValueCache();
			}
		});
		organigrammaMittenteItem.setPickListProperties(organigrammaMittentePickListProperties);

		// denominazione
		denominazioneMittenteItem = new ExtendedTextItem("denominazioneMittente", I18NUtil.getMessages().protocollazione_detail_denominazioneItem_title());
		denominazioneMittenteItem.setWidth(250);
		denominazioneMittenteItem.setAttribute("obbligatorio", true);
		denominazioneMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return toShowDenominazione(tipoMittenteItem.getValueAsString());
			}
		});
		denominazioneMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showSelectOrganigramma()) return false;
				return toShowDenominazione(tipoMittenteItem.getValueAsString());
			}
		}));
		denominazioneMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtUscitaItem)getItem()).manageOnChanged();
				if(denominazioneMittenteItem.getValue() != null && !"".equals(denominazioneMittenteItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("denominazioneSoggetto", denominazioneMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		denominazioneMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cognome
		cognomeMittenteItem = new ExtendedTextItem("cognomeMittente", I18NUtil.getMessages().protocollazione_detail_cognomeItem_title());
		cognomeMittenteItem.setWidth(125);
		cognomeMittenteItem.setAttribute("obbligatorio", true);
		cognomeMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoMittenteItem.getValueAsString());
			}
		});
		cognomeMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoMittenteItem.getValueAsString());
			}
		}));
		cognomeMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtUscitaItem)getItem()).manageOnChanged();
				if(cognomeMittenteItem.getValue() != null && !"".equals(cognomeMittenteItem.getValue()) &&
				   nomeMittenteItem.getValue() != null && !"".equals(nomeMittenteItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		cognomeMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// nome
		nomeMittenteItem = new ExtendedTextItem("nomeMittente", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeMittenteItem.setWidth(125);
		nomeMittenteItem.setAttribute("obbligatorio", true);
		nomeMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoMittenteItem.getValueAsString());
			}
		});
		nomeMittenteItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				if(showSelectOrganigramma()) return false;
				return isPersonaFisica(tipoMittenteItem.getValueAsString());
			}
		}));
		nomeMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtUscitaItem)getItem()).manageOnChanged();
				if(cognomeMittenteItem.getValue() != null && !"".equals(cognomeMittenteItem.getValue()) &&
				   nomeMittenteItem.getValue() != null && !"".equals(nomeMittenteItem.getValue())) {					
					final Record lRecord = new Record();
					lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.getValue());
					lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		nomeMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});

		// cod.fiscale
		codfiscaleMittenteItem = new ExtendedTextItem("codfiscaleMittente", I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
		codfiscaleMittenteItem.setWidth(150);
		codfiscaleMittenteItem.setCharacterCasing(CharacterCasing.UPPER);
		codfiscaleMittenteItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null || "".equals(value) || AurigaLayout.getParametroDBAsBoolean("DISATTIVA_CTRL_CF_PIVA_EMDI")) {
					return true;
				}
				if (isPersonaFisica(tipoMittenteItem.getValueAsString())) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				} else {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);
				}
			}
		});		
		codfiscaleMittenteItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearIdSoggetto();
				((MittenteProtUscitaItem)getItem()).manageOnChanged();
				if(codfiscaleMittenteItem.getValue() != null && !"".equals(codfiscaleMittenteItem.getValue())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("codfiscaleSoggetto", codfiscaleMittenteItem.getValue());
					lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
					cercaInRubricaAfterChangedField(lRecord);
				}
			}
		});
		codfiscaleMittenteItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(true);
			}
		});
		codfiscaleMittenteItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				
				if(showSelectOrganigramma()) return false;
				String tipoMittente = tipoMittenteItem.getValueAsString();
				if (isPersonaFisica(tipoMittente)) {
					codfiscaleMittenteItem.setLength(16);
					codfiscaleMittenteItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscaleItem_title());
				} else {
					codfiscaleMittenteItem.setLength(28);
					codfiscaleMittenteItem.setTitle(I18NUtil.getMessages().protocollazione_detail_codFiscalePIVAItem_title());
				}
				return toShowDenominazione(tipoMittente) || isPersonaFisica(tipoMittente);
			}
		});

		// bottone seleziona da rubrica
		ImgButtonItem lookupRubricaButton = new ImgButtonItem("lookupRubricaButton", "lookup/rubrica.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupRubricaButton_prompt());
		lookupRubricaButton.setColSpan(1);
		lookupRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
					return false;
				}
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente == null || "".equals(tipoMittente) || isPersonaFisica(tipoMittente) || isPersonaGiuridica(tipoMittente);
			}
		});
		lookupRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);			
				MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(null, tipoMittenteItem.getValueAsString());
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
				if(showSelectOrganigramma()) return false;
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente == null || "".equals(tipoMittente) || "UOI".equals(tipoMittente) || "UP".equals(tipoMittente);
			}
		});
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);			
				MittenteLookupOrganigramma lookupOrganigrammaPopup = new MittenteLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});

		// bottone cerca da rubrica
		ImgButtonItem cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", I18NUtil.getMessages()
				.protocollazione_detail_cercaInRubricaButton_prompt());
		cercaInRubricaButton.setColSpan(1);
		cercaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
					return false;
				}
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente == null || "".equals(tipoMittente) || isPersonaFisica(tipoMittente)
						|| (isPersonaGiuridica(tipoMittente) && !"AOOI".equalsIgnoreCase(tipoMittente));
			}
		});
		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);			
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
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return tipoMittente == null || "".equals(tipoMittente) || "UOI".equals(tipoMittente) || "UP".equals(tipoMittente);
			}
		});
		cercaInOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);				
				cercaInOrganigramma();
			}
		});

		// bottone salva in rubrica
		salvaInRubricaButton = new ImgButtonItem("salvainrubricaMittente", "buttons/saveIn.png", I18NUtil.getMessages()
				.protocollazione_detail_salvaInRubricaButton_prompt());
		salvaInRubricaButton.setColSpan(1);
		salvaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showSelectOrganigramma()) return false;
				if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
					return false;
				}
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return Layout.isPrivilegioAttivo("GRS/S/UO;I")
						&& (tipoMittente == null || "".equals(tipoMittente) || ((isPersonaFisica(tipoMittente) || isPersonaGiuridica(tipoMittente))
								&& !"AOOI".equalsIgnoreCase(tipoMittente) && !"PA".equalsIgnoreCase(tipoMittente))) 
						&& !(idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue()));
			}
		});
		salvaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);			
				String tipo = tipoMittenteItem.getValueAsString();
				String codRapido = codRapidoMittenteItem.getValueAsString();
				String denominazione = denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValueAsString() : null;
				String cognome = cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValueAsString() : null;
				String nome = nomeMittenteItem.isVisible() ? nomeMittenteItem.getValueAsString() : null;
				String codiceFiscale = codfiscaleMittenteItem.isVisible() ? codfiscaleMittenteItem.getValueAsString() : null;
				Record recordMittente = new Record();
				recordMittente.setAttribute("tipo", tipo);
				recordMittente.setAttribute("codRapido", codRapido);
				recordMittente.setAttribute("denominazione", denominazione);
				recordMittente.setAttribute("cognome", cognome);
				recordMittente.setAttribute("nome", nome);
				recordMittente.setAttribute("codiceFiscale", codiceFiscale);
				Record recordIndirizzo = getFormValuesAsRecord();
				SalvaInRubricaPopup salvaInRubricaPopup = new SalvaInRubricaPopup((AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? "SEL_SOGG_INT"
						: "SEL_UOI"), recordMittente, recordIndirizzo) {

					@Override
					public void manageLookupBack(Record record) {
						setFormValuesFromRecordRubrica(record);
					}
				};
				salvaInRubricaPopup.show();
			}
		});

		//bottone visualizza nominativo in rubrica
		visualizzaInRubricaButton = new ImgButtonItem("visualizzaInRubricaMittente", "buttons/detail.png", I18NUtil.getMessages()
				.protocollazione_detail_visualizzaInRubricaButton_prompt());
		visualizzaInRubricaButton.setAlwaysEnabled(true);
		visualizzaInRubricaButton.setColSpan(1);
		visualizzaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoMittente = tipoMittenteItem.getValueAsString();
				return idSoggettoItem.getValue() != null && !"".equals(idSoggettoItem.getValue())
						&& (tipoMittente == null || "".equals(tipoMittente) || isPersonaFisica(tipoMittente) || isPersonaGiuridica(tipoMittente));
			}
		});

		visualizzaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {

				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);			
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
							String nomeDettaglio = !mDynamicForm.getValueAsString("denominazioneMittente").equals("") ? 
									mDynamicForm.getValueAsString("denominazioneMittente") : mDynamicForm.getValueAsString("cognomeMittente") + " " + mDynamicForm.getValueAsString("nomeMittente");
									Layout.addModalWindow("dettaglio_soggetto", "Dettaglio " + nomeDettaglio, "buttons/detail.png", detail);
						}
					}
				});
			}
		});
		
		// check assegna
		flgAssegnaAlMittenteItem = new CheckboxItem("flgAssegnaAlMittente", I18NUtil.getMessages().protocollazione_detail_flgAssegnaItem_title());
		flgAssegnaAlMittenteItem.setRequired(false);
		flgAssegnaAlMittenteItem.setColSpan(1);
		flgAssegnaAlMittenteItem.setWidth(70);
		flgAssegnaAlMittenteItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				((MittenteProtItem) getItem()).manageChangeFlgAssegnaAlMittente(event);				
			}
		});
		flgAssegnaAlMittenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
				mDynamicForm.redraw();
			}
		});
		flgAssegnaAlMittenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				item.setTitle(I18NUtil.getMessages().protocollazione_detail_flgAssegnaItem_title());
				item.setCanEdit(getEditing());
				//TODO invece di guardare idAssegnatario posso guardare tutti i record di listaAssegnazioniSalvate
				String idAssegnatario = mDynamicForm.getValueAsString("idAssegnatario") != null ? mDynamicForm.getValueAsString("idAssegnatario") : null;
				if(idAssegnatario != null && !"".equals(idAssegnatario)) {
					String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto") != null ? mDynamicForm.getValueAsString("idUoSoggetto") : "";
					String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto") != null ? mDynamicForm.getValueAsString("idScrivaniaSoggetto") : "";
					String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto") != null ? mDynamicForm.getValueAsString("idUserSoggetto") : "";
					if((idAssegnatario.startsWith("UO") && idUoSoggetto.equals(idAssegnatario.substring(2))) ||
					   (idAssegnatario.startsWith("SV") && idScrivaniaSoggetto.equals(idAssegnatario.substring(2))) ||
					   (idAssegnatario.startsWith("UT") && idUserSoggetto.equals(idAssegnatario.substring(2)))) {
						boolean flgAssegnaAlMittente = mDynamicForm.getValue("flgAssegnaAlMittente") != null ? (Boolean) mDynamicForm.getValue("flgAssegnaAlMittente") : false;
						if(!flgAssegnaAlMittente) {
							mDynamicForm.setValue("flgAssegnaAlMittente", true);
							((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
						}
						item.setTitle(I18NUtil.getMessages().protocollazione_detail_flgAssegnataItem_title());
						item.setCanEdit(false);
						return true;
					}
				}
				//TODO per decommentare questa parte devo fare in modo che flgSelXAssegnazione venga caricato nel dettaglio
//				boolean flgSelXAssegnazione = mDynamicForm.getValueAsString("flgSelXAssegnazione") != null && "1".equals(mDynamicForm.getValueAsString("flgSelXAssegnazione"));				
//				if(/*!fromLoadDett && */!flgSelXAssegnazione) {
//					mDynamicForm.setValue("flgAssegnaAlMittente", false);
//					((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
//					item.setCanEdit(false);
//				}
				return showFlgAssegnaAlMittente;
			}
		});

		opzioniInvioHiddenItem = new HiddenItem("opzioniInvio");
		
		// BOTTONE : opzioni invio sull'assegnazione
		ImgButtonItem opzioniInvioAssegnazioneButton = new ImgButtonItem("opzioniInvioAssegnazioneButton", "buttons/altriDati.png", I18NUtil.getMessages()
				.protocollazione_detail_opzioniInvioAssegnazioneButton_prompt());
//		opzioniInvioAssegnazioneButton.setAlwaysEnabled(true);
		opzioniInvioAssegnazioneButton.setColSpan(1);
		opzioniInvioAssegnazioneButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				((MittenteProtUscitaItem) getItem()).setCercaInRubricaAfterChanged(false);		
				String tipoMittente = new Record(mDynamicForm.getValues()).getAttribute("tipoMittente");
				boolean flgUo = tipoMittente != null && "UOI".equals(tipoMittente);
				Record recordOpzioniInvio = new Record(mDynamicForm.getValues()).getAttributeAsRecord("opzioniInvio"); 
				OpzioniInvioAssegnazionePopup opzioniInvioAssegnazionePopup = new OpzioniInvioAssegnazionePopup(flgUo, recordOpzioniInvio, getEditing()) {

					@Override
					public String getIdUd() {
						return ((MittenteProtItem) getItem()).getIdUdProtocollo();
					}
					
					@Override
					public String getFlgUdFolder() {
						return "U";
					}
					
					@Override
					public void onClickOkButton(Record record) {
						mDynamicForm.setValue("opzioniInvio", record);	
					}
				};
				opzioniInvioAssegnazionePopup.show();
			}
		});
		opzioniInvioAssegnazioneButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showFlgAssegnaAlMittente && flgAssegnaAlMittenteItem.getValueAsBoolean();
			}
		});

		mDynamicForm.setFields(
			idMittenteItem,
			idSoggettoItem,
			idUoSoggettoItem,
			idUserSoggettoItem,
			idScrivaniaSoggettoItem,
			idAssegnatarioItem,
			flgSelXAssegnazioneItem,
			flgAssegnaAlMittenteXNuovaProtComeCopiaItem,
			codRapidoChangedHiddenItem,
			tipoMittenteItem,
			iconaFlgInOrganigrammaItem,
			codRapidoMittenteItem,
			lookupRubricaButton,
			lookupOrganigrammaButton,
			organigrammaMittenteItem,
			denominazioneMittenteItem,
			cognomeMittenteItem,
			nomeMittenteItem,
			codfiscaleMittenteItem,
			cercaInRubricaButton,
			cercaInOrganigrammaButton,
			salvaInRubricaButton,
			visualizzaInRubricaButton,
			flgAssegnaAlMittenteItem,
			opzioniInvioHiddenItem,
			opzioniInvioAssegnazioneButton
		);

		mDynamicForm.setNumCols(22);
		mDynamicForm.setColWidths("50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50", "100", "50",
				"100", "50", "100");

	}
	
	public boolean isValorizzatoSoggPerAssegnazione() {
		String idUoSoggetto = mDynamicForm.getValueAsString("idUoSoggetto");
		if (idUoSoggetto == null)
			idUoSoggetto = "";
		// String idUserSoggetto = mDynamicForm.getValueAsString("idUserSoggetto");
		// if (idUserSoggetto == null)
		// idUserSoggetto = "";
		String idScrivaniaSoggetto = mDynamicForm.getValueAsString("idScrivaniaSoggetto");
		if (idScrivaniaSoggetto == null)
			idScrivaniaSoggetto = "";
		String supportoOriginale = ((MittenteProtItem) getItem()).getSupportoOriginaleProt();
		if(supportoOriginale != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "cartaceo".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "digitale".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "misto".equals(supportoOriginale))) {
				return !idUoSoggetto.equalsIgnoreCase("");
			}
		}
		return (!idUoSoggetto.equalsIgnoreCase("") /* || !idUserSoggetto.equalsIgnoreCase("") */|| !idScrivaniaSoggetto.equalsIgnoreCase(""));
	}

	public void showHideFlgAssegnaAlMittente() {
		// Visualizzo solo se uno dei 3 campi idUoSoggetto, idUserSoggetto, idScrivaniaSoggetto e' valorizzato
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if (((MittenteProtItem) getItem()).getShowFlgAssegnaAlMittente() && isValorizzatoSoggPerAssegnazione()) {
			if(!fromLoadDett && mDynamicForm.getValue("flgAssegnaAlMittente") == null) {
				if(mDynamicForm.getValue("flgAssegnaAlMittenteXNuovaProtComeCopia") != null) {
					boolean flgAssegnaAlMittenteXNuovaProtComeCopia = (Boolean) mDynamicForm.getValue("flgAssegnaAlMittenteXNuovaProtComeCopia");
					mDynamicForm.setValue("flgAssegnaAlMittente", flgAssegnaAlMittenteXNuovaProtComeCopia);
					((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());
				} else {
					boolean flgSelXAssegnazione = mDynamicForm.getValueAsString("flgSelXAssegnazione") != null && "1".equals(mDynamicForm.getValueAsString("flgSelXAssegnazione"));
					if(((MittenteProtItem) getItem()).isAttivoAssegnatarioUnicoProt() && ((MittenteProtItem) getItem()).getNroAssegnazioniProt() > 0) {							
						flgSelXAssegnazione = false;
					}
					if(((MittenteProtItem) getItem()).isProtInModalitaWizard()) {
						if(((MittenteProtItem) getItem()).isSupportoOriginaleProtValorizzato() && ((MittenteProtItem) getItem()).isAttivoAssegnatarioUnicoCartaceoProt() && ((MittenteProtItem) getItem()).getNroAssegnazioniProt() > 0) {							
							flgSelXAssegnazione = false;
						}
					}
					// se il soggetto è selezionabile per l'assegnazione allora setto il check al valore di default
					if(flgSelXAssegnazione) {
						mDynamicForm.setValue("flgAssegnaAlMittente", ((MittenteProtItem) getItem()).getFlgAssegnaAlMittenteDefault());
						((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
					} 
				}
			}
			showFlgAssegnaAlMittente = true;
		} else {
//			mDynamicForm.setValue("flgAssegnaAlMittente", false); // non posso settare il check a false altrimenti non viene caricato successivamente  
			  													  // il valore di default, quindi utilizzo il clearValue()
			mDynamicForm.clearValue("flgAssegnaAlMittente");
			((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
			showFlgAssegnaAlMittente = false;
		}
		mDynamicForm.markForRedraw();
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
	
	public class MittenteLookupRubrica extends LookupSoggettiPopup {

		public MittenteLookupRubrica(Record record, String tipoMittente) {
			super(record, tipoMittente, true);
		}
		
		@Override
		public String getFinalita() {
			if (getItem() instanceof MittenteProtUscitaItem) {
				return ((MittenteProtUscitaItem) getItem()).getFinalitaLookupRubrica();
			} else {
				return AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? "SEL_SOGG_INT" : "SEL_UOI";
			}			
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
			return AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? new String[] { "UOI", "UP" } : new String[] { "UOI" };
		}

	}

	public class MittenteLookupOrganigramma extends LookupOrganigrammaPopup {

		public MittenteLookupOrganigramma(Record record) {
			super(record, true, (AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? null : new Integer(0)));
		}
		
		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordOrganigramma(record);
		}

		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVMitt");
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
		
		@Override
		public String getFinalita() {		
			if (getItem() instanceof MittenteProtUscitaItem) {
				return ((MittenteProtUscitaItem) getItem()).getFinalitaLookupOrganigramma();
			} else {					
				if(((MittenteProtItem) getItem()).isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
					return ((MittenteProtItem) getItem()).isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO";
				} 	
				return "MITT_DEST";
			}
		}
		
		@Override
		public String getIdUd() {
			return ((MittenteProtItem) getItem()).getIdUdProtocollo();
		}

	}

	protected void clearFormSoggettoValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoMittente", "");
		lRecord.setAttribute("denominazioneMittente", "");
		lRecord.setAttribute("cognomeMittente", "");
		lRecord.setAttribute("nomeMittente", "");
		lRecord.setAttribute("codfiscaleMittente", "");
		mDynamicForm.setValues(lRecord.toMap());
		((MittenteProtUscitaItem)getItem()).manageOnChanged();
	}

	protected void clearIdSoggetto() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("codRapidoMittente", "");
		lRecord.setAttribute("idMittente", "");
		lRecord.setAttribute("idSoggetto", "");
		lRecord.setAttribute("idUoSoggetto", "");
		lRecord.setAttribute("idUserSoggetto", "");
		lRecord.setAttribute("idScrivaniaSoggetto", "");
		lRecord.setAttribute("flgSelXAssegnazione", "");		
		mDynamicForm.setValues(lRecord.toMap());
		mDynamicForm.clearValue("flgAssegnaAlMittente");
		((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
	}

	protected boolean toShowDenominazione(String tipoSoggetto) {
		return tipoSoggetto == null || "".equals(tipoSoggetto) || (isPersonaGiuridica(tipoSoggetto) && !"AOOI".equalsIgnoreCase(tipoSoggetto));
	}

	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		return ((MittenteProtItem) getItem()).isPersonaGiuridica(tipoSoggetto);
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		return ((MittenteProtItem) getItem()).isPersonaFisica(tipoSoggetto);
	}

	protected void cercaInRubrica() {
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.isVisible() ? nomeMittenteItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codfiscaleMittenteItem.isVisible() ? codfiscaleMittenteItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());		
		cercaInRubrica(lRecord, true);	
	}
	
	protected void cercaInRubricaAfterChangedField(final Record record) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CERCA_IN_RUBRICA")) {
			Timer t1 = new Timer() {
				public void run() {
					if(((MittenteProtUscitaItem) getItem()).isCercaInRubricaAfterChanged()) {
						if(((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
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
					MittenteLookupRubrica lookupRubricaPopup = new MittenteLookupRubrica(record, tipoMittenteItem.getValueAsString());
					lookupRubricaPopup.show();
				}
			}
		});	
	}
	
	protected void cercaInOrganigramma() {
		final Record lRecord = new Record();
		lRecord.setAttribute("denominazioneSoggetto", denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValue() : null);
		lRecord.setAttribute("cognomeSoggetto", cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValue() : null);
		lRecord.setAttribute("nomeSoggetto", nomeMittenteItem.isVisible() ? nomeMittenteItem.getValue() : null);
		lRecord.setAttribute("codfiscaleSoggetto", codfiscaleMittenteItem.isVisible() ? codfiscaleMittenteItem.getValue() : null);
		lRecord.setAttribute("codTipoSoggetto", tipoMittenteItem.getValue());
		cercaInOrganigramma(lRecord, true);
	}
	
	protected void cercaInOrganigramma(final Record record, final boolean showLookupWithNoResults) {
		record.setAttribute("flgInOrganigramma", "UO;UT");
		if (tipoMittenteItem.getValue() != null) {
			if ("UOI".equals(tipoMittenteItem.getValue())) {
				record.setAttribute("flgInOrganigramma", "UO");
			} else if ("UP".equals(tipoMittenteItem.getValue())) {
				record.setAttribute("flgInOrganigramma", "UT");
			}
		}
		cercaSoggetto(record, new CercaSoggettoServiceCallback() {
	
			@Override
			public void executeOnError(boolean trovatiSoggMultipliInRicerca) {
				if(showLookupWithNoResults || trovatiSoggMultipliInRicerca) {						
					MittenteLookupOrganigramma lookupOrganigrammaPopup = new MittenteLookupOrganigramma(record);
					lookupOrganigrammaPopup.show();
				} else if(!showLookupWithNoResults && ((MittenteProtItem) getItem()).getFlgSoloInOrganigramma()) {
					Layout.addMessage(new MessageBean("Soggetto non presente in organigramma", "", MessageType.ERROR));
					mDynamicForm.setFieldErrors("codRapidoMittente", "Soggetto non presente in organigramma");
				}
			}
		});
	}

	protected void cercaSoggetto(Record lRecord, ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindSoggettoDatasource");
		lGwtRestService.addParam("tipiAmmessi", AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? "UOI,UP" :  "UOI");
		if(((MittenteProtItem) getItem()).isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
			lGwtRestService.addParam("finalita", ((MittenteProtItem) getItem()).isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO");
		} else {
			lGwtRestService.addParam("finalita", "MITT_DEST");
		}
		lGwtRestService.addParam("idUd", ((MittenteProtItem) getItem()).getIdUdProtocollo());
		lGwtRestService.call(lRecord, callback);
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
	// return tipoSoggetto;
	// }

	public String calcolaTipoSoggetto(String tipo) {
		String tipoSoggetto = null;
		if ("UO;UOI".equals(tipo)) {
			tipoSoggetto = "UOI";
		} else if ("UP".equals(tipo)) {
			tipoSoggetto = "UP";
		}
		return tipoSoggetto;
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
		// String tipoMittente = calcolaTipoSoggetto(record.getAttribute("flgPersFisica"), codTipoSoggetto);
		String tipoMittente = calcolaTipoSoggetto(record.getAttribute("tipo"));
		if (tipoMittente != null) {
			mDynamicForm.setValue("tipoMittente", tipoMittente);
			mDynamicForm.setValue("codRapidoMittente", record.getAttribute("codiceRapido"));
			if (isPersonaGiuridica(tipoMittente)) {
				mDynamicForm.setValue("denominazioneMittente", record.getAttribute("denominazione"));
			} else if (isPersonaFisica(tipoMittente)) {
				mDynamicForm.setValue("cognomeMittente", record.getAttribute("cognome"));
				mDynamicForm.setValue("nomeMittente", record.getAttribute("nome"));
				mDynamicForm.setValue("codfiscaleMittente", record.getAttribute("codiceFiscale"));
			}
			mDynamicForm.setValue("idMittente", record.getAttribute("idSoggetto"));
			mDynamicForm.setValue("idSoggetto", record.getAttribute("idSoggetto"));
			mDynamicForm.setValue("idUoSoggetto", record.getAttribute("idUo"));
			mDynamicForm.setValue("idUserSoggetto", record.getAttribute("idUtente"));
			mDynamicForm.setValue("idScrivaniaSoggetto", record.getAttribute("idScrivania"));
			mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));
			mDynamicForm.clearValue("flgAssegnaAlMittente");
			((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
			setIndirizzoCompletoFromRecordRubrica(record);
			showHideFlgAssegnaAlMittente();
			((MittenteProtUscitaItem)getItem()).manageOnChanged();
		} else {
			// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/ uscita /
			// interna>
			mDynamicForm.setFieldErrors("codRapidoMittente",
					I18NUtil.getMessages().protocollazione_detail_codrapidosoggetto_esitoValidazione_KO("mittente", "in uscita"));
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
		mDynamicForm.setValue("idMittente", idUoSvUt);
		mDynamicForm.setValue("idSoggetto", record.getAttribute("idRubrica"));
		String tipo = record.getAttribute("tipo");
		if (tipo.startsWith("UO")) {
			mDynamicForm.setValue("tipoMittente", "UOI");
			mDynamicForm.setValue("codRapidoMittente", record.getAttribute("codRapidoUo"));
			mDynamicForm.setValue("denominazioneMittente", record.getAttribute("descrUoSvUt"));
			mDynamicForm.setValue("idUoSoggetto", idUoSvUt);
		} else {
			mDynamicForm.setValue("tipoMittente", "UP");
			if(record.getAttribute("codRapidoSvUt") != null && !"".equals(record.getAttribute("codRapidoSvUt"))) {
				mDynamicForm.setValue("codRapidoMittente", record.getAttribute("codRapidoSvUt"));
			} else {
				mDynamicForm.setValue("codRapidoMittente", record.getAttribute("codRapidoUo"));
			}
			String cognomeNome = record.getAttribute("descrUoSvUt");
			if (cognomeNome != null && !"".equals(cognomeNome)) {
				String[] tokens = new StringSplitterClient(cognomeNome, "|").getTokens();
				if (tokens.length == 2) {
					mDynamicForm.setValue("cognomeMittente", tokens[0].trim());
					mDynamicForm.setValue("nomeMittente", tokens[1].trim());
				}
			}
			mDynamicForm.setValue("codfiscaleMittente", record.getAttribute("codFiscale"));
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
			mDynamicForm.setValue("organigrammaMittente", flgUoSvUt + idUoSvUt);
			manageLoadSelectOrganigrammaInEditRecord(mDynamicForm.getValuesAsRecord());
		}
		mDynamicForm.setValue("flgSelXAssegnazione", record.getAttribute("flgSelXAssegnazione"));	
		mDynamicForm.clearValue("flgAssegnaAlMittente");
		((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
		showHideFlgAssegnaAlMittente();
		((MittenteProtUscitaItem)getItem()).manageOnChanged();
		if(showSelectOrganigramma()) {	
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
	
				@Override
				public void execute() {
					organigrammaMittenteItem.fetchData();
				}
			});
		}
		mDynamicForm.markForRedraw();
	}

	@Override
	public void editRecord(Record record) {
		showFlgAssegnaAlMittente = false;
		if(showSelectOrganigramma()) {			
			manageLoadSelectOrganigrammaInEditRecord(record);			
		} else {
			record.setAttribute("organigrammaMittente", ""); // per evitare che poi in salvataggio venga passato il valore caricato da dettaglio, nel caso in cui la select da organigramma non sia attiva 
		}
		super.editRecord(record);
		showHideFlgAssegnaAlMittente();
		if(isChangedRecord(record)) {			
			((MittenteProtUscitaItem)getItem()).manageOnChanged();
		}
	}
	
	public void manageLoadSelectOrganigrammaInEditRecord(Record record) {
		String tipoMittente = record.getAttribute("tipoMittente") != null ? record.getAttribute("tipoMittente") : "";
		if (record.getAttribute("organigrammaMittente") != null && !"".equals(record.getAttributeAsString("organigrammaMittente"))
				&& record.getAttribute("denominazioneMittente") != null && !"".equals(record.getAttributeAsString("denominazioneMittente"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			if ("UOI".equals(tipoMittente)) {
				valueMap.put(record.getAttribute("organigrammaMittente"), "<b>" + record.getAttribute("denominazioneMittente") + "</b>");
			} else {
				valueMap.put(record.getAttribute("organigrammaMittente"), record.getAttribute("denominazioneMittente"));
			}
			organigrammaMittenteItem.setValueMap(valueMap);
		}
		GWTRestDataSource organigrammaDS = (GWTRestDataSource) organigrammaMittenteItem.getOptionDataSource();
		if (record.getAttribute("organigrammaMittente") != null && !"".equals(record.getAttributeAsString("organigrammaMittente"))
				&& ("UOI".equals(tipoMittente) || "UP".equals(tipoMittente))) {
			organigrammaDS.addParam("idUoSv", record.getAttributeAsString("idMittente"));
			if(tipoMittente.equals("UOI")) {
				organigrammaDS.addParam("flgUoSv", "UO");					
			} else if(tipoMittente.equals("UP")) {
				organigrammaDS.addParam("flgUoSv", "SV");										
			}			
		} else {
			organigrammaDS.addParam("idUoSv", null);
			organigrammaDS.addParam("flgUoSv", null);
		}
		organigrammaMittenteItem.setOptionDataSource(organigrammaDS);
	}

	public abstract class CercaSoggettoServiceCallback extends ServiceCallback<Record> {

		public abstract void executeOnError(boolean trovatiSoggMultipliInRicerca);

		@Override
		public void execute(Record object) {
			mDynamicForm.clearErrors(true);
			// String tipoMittente = calcolaTipoSoggetto(object.getAttribute("tipoSoggetto"), object.getAttribute("codTipoSoggetto"));
			String tipoMittente = calcolaTipoSoggetto(object.getAttribute("tipologiaSoggetto"));
			if (tipoMittente != null) {
				mDynamicForm.setValue("tipoMittente", tipoMittente);
				// Pulisco i dati del soggetto
				mDynamicForm.setValue("idMittente", "");				
				mDynamicForm.setValue("idSoggetto", "");
				mDynamicForm.setValue("idUoSoggetto", "");
				mDynamicForm.setValue("idUserSoggetto", "");
				mDynamicForm.setValue("idScrivaniaSoggetto", "");
				mDynamicForm.setValue("flgSelXAssegnazione", "");	
				mDynamicForm.clearValue("flgAssegnaAlMittente");
				((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
				mDynamicForm.setValue("denominazioneMittente", "");
				mDynamicForm.setValue("cognomeMittente", "");
				mDynamicForm.setValue("nomeMittente", "");
				mDynamicForm.setValue("codfiscaleMittente", "");
				if (object.getAttribute("codRapidoSoggetto") != null && !object.getAttribute("codRapidoSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codRapidoMittente", object.getAttribute("codRapidoSoggetto"));
				}
				if (object.getAttribute("denominazioneSoggetto") != null && !object.getAttribute("denominazioneSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("denominazioneMittente", object.getAttribute("denominazioneSoggetto"));
				}
				if (object.getAttribute("cognomeSoggetto") != null && !object.getAttribute("cognomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("cognomeMittente", object.getAttribute("cognomeSoggetto"));
				}
				if (object.getAttribute("nomeSoggetto") != null && !object.getAttribute("nomeSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("nomeMittente", object.getAttribute("nomeSoggetto"));
				}
				if (object.getAttribute("codfiscaleSoggetto") != null && !object.getAttribute("codfiscaleSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("codfiscaleMittente", object.getAttribute("codfiscaleSoggetto"));
				}
				if (object.getAttribute("idSoggetto") != null && !object.getAttribute("idSoggetto").equalsIgnoreCase("")) {
					mDynamicForm.setValue("idMittente", object.getAttribute("idSoggetto"));
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
				showHideFlgAssegnaAlMittente();
				((MittenteProtUscitaItem)getItem()).manageOnChanged();
				mDynamicForm.markForRedraw();
			} else {
				// Errore : Soggetto non presente in rubrica o di tipo non ammesso per un <mittente/destinatario> di una registrazione <in entrata/ uscita /
				// interna>
				executeOnError(object.getAttribute("trovatiSoggMultipliInRicerca") != null && object.getAttributeAsBoolean("trovatiSoggMultipliInRicerca"));
			}
		}
	}

	public boolean isMittenteValorizzato() {
		String denominazione = denominazioneMittenteItem.isVisible() ? denominazioneMittenteItem.getValueAsString() : null;
		String cognome = cognomeMittenteItem.isVisible() ? cognomeMittenteItem.getValueAsString() : null;
		String nome = nomeMittenteItem.isVisible() ? nomeMittenteItem.getValueAsString() : null;
		return ((denominazione != null && !"".equals(denominazione)) || ((cognome != null && !"".equals(cognome)) && (nome != null && !"".equals(nome))));
	}

	public boolean isChangedAndValid() {
		return isChanged() && isMittenteValorizzato();
	}

	public boolean showItemsIndirizzo() {
		return ((MittenteProtUscitaItem) getItem()).getShowItemsIndirizzo();
	}
	
	public void resetDefaultValueFlgAssegnaAlMittente() {
		boolean fromLoadDett =  mDynamicForm.getValue("fromLoadDett") != null ? (Boolean) mDynamicForm.getValue("fromLoadDett") : false; 
		if(!fromLoadDett) {
			mDynamicForm.clearValue("flgAssegnaAlMittente");
			((MittenteProtItem) getItem()).manageChangedFlgAssegnaAlMittente(mDynamicForm.getValuesAsRecord());	
		}
	}

	@Override
	public void redraw() {
		super.redraw();
		showHideFlgAssegnaAlMittente();
	}
	
	public void manageOnChangedUoProtocollante() {
		if(showSelectOrganigramma()) {	
			if(organigrammaMittenteItem != null) {
				final String value = organigrammaMittenteItem.getValueAsString();
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						organigrammaMittenteItem.fetchData(new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								RecordList data = response.getDataAsRecordList();
								if(value != null && !"".equals(value)) {
									boolean trovato = false;
									if (data.getLength() > 0) {						
										for (int i = 0; i < data.getLength(); i++) {
											String key = data.get(i).getAttribute("id");
											if (value.equals(key)) {
												trovato = true;
												break;
											}
										}
									}
									if (!trovato) {
										mDynamicForm.setValue("organigrammaMittente", "");
										if(((MittenteProtUscitaItem)getItem()).isDimOrganigrammaNonStd()) {
											mDynamicForm.setValue("codRapidoMittente", AurigaLayout.getCodRapidoOrganigramma());
										} else {
											mDynamicForm.setValue("codRapidoMittente", "");
										}
										mDynamicForm.setValue("idMittente", "");
										mDynamicForm.setValue("idSoggetto", "");
										mDynamicForm.setValue("idUoSoggetto", "");
										mDynamicForm.setValue("idUserSoggetto", "");
										mDynamicForm.setValue("idScrivaniaSoggetto", "");
										mDynamicForm.setValue("tipoMittente", "");
										mDynamicForm.setValue("flgSelXAssegnazione", "");
										mDynamicForm.setValue("denominazioneMittente", "");
										mDynamicForm.setValue("cognomeMittente", "");
										mDynamicForm.setValue("nomeMittente", "");
										mDynamicForm.setValue("codfiscaleMittente", "");
										mDynamicForm.clearErrors(true);				
										organigrammaMittenteItem.fireEvent(new ChangedEvent(organigrammaMittenteItem.getJsObj()));
									}
								}
							}
						});
					}
				});		
			}
		}
	}
	
}
