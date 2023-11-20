/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.toponomastica.LookupCiviciPopup;
import it.eng.auriga.ui.module.layout.client.toponomastica.LookupViarioPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ComboBoxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public abstract class IndirizzoCanvas extends ReplicableCanvas {

	public static final String _COD_ISTAT_ITALIA = "200";
	public static final String _NOME_STATO_ITALIA = "ITALIA";
	private static final int COMPONENT_WIDTH = 380;

	// public static final String _COD_ISTAT_MILANO = "015146";
	// public static final String _NOME_COMUNE_MILANO = "MILANO";

	protected HiddenItem nomeStatoItem;
	protected FilteredSelectItem statoItem;
	protected CheckboxItem flgFuoriComuneItem;
	protected HiddenItem codToponimoItem;
	protected HiddenItem civicoPresenteInViaItem;
	protected SelectItem tipoToponimoItem;
	protected ExtendedTextItem toponimoItem;
	protected FormItem indirizzoItem;
	protected ExtendedTextItem civicoItem;
	protected ExtendedTextItem appendiciItem;
	protected HiddenItem nomeComuneItem;
	protected FilteredSelectItem comuneItem;
	protected ExtendedTextItem cittaItem;
	protected TextItem provinciaItem;
	protected SelectItem frazioneItem;
	protected ComboBoxItem capItem;
	protected TextItem zonaItem; // restringere
	protected TextItem complementoIndirizzoItem;

	protected VLayout lVLayoutCanvas;
	protected ReplicableCanvasForm mDynamicForm;
	protected ReplicableCanvasForm mIndirizzoDynamicForm1;
	protected ReplicableCanvasForm mIndirizzoDynamicForm2;

	protected DetailSection detailSectionIndirizzo;

	public IndirizzoCanvas(ReplicableItem item) {
		super(item);
	}

	public IndirizzoCanvas(ReplicableItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {
		
		buildMainForm();
		
		if (showItemsIndirizzo()) {
			buildIndirizzoForm();
			lVLayoutCanvas = new VLayout();
			lVLayoutCanvas.setHeight(5);
			lVLayoutCanvas.setOverflow(Overflow.VISIBLE);
			if (showItemsIndirizzoWithBorder()) {
				// VLayout lVLayoutIndirizzo = new VLayout();
				// lVLayoutIndirizzo.setPadding(11);
				// lVLayoutIndirizzo.setMargin(4);
				// lVLayoutIndirizzo.setIsGroup(true);
				// lVLayoutIndirizzo.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">Indirizzo</span>");
				// lVLayoutIndirizzo.setOverflow(Overflow.VISIBLE);
				// lVLayoutIndirizzo.setHeight(5);
				// lVLayoutIndirizzo.setMembers(mIndirizzoDynamicForm1, mIndirizzoDynamicForm2);
				// lVLayoutCanvas.setMembers(mDynamicForm, lVLayoutIndirizzo);
				detailSectionIndirizzo = new DetailSection("Indirizzo", true, showOpenIndirizzo(), false, mIndirizzoDynamicForm1, mIndirizzoDynamicForm2) {

					@Override
					public void open() {
						super.open();
						lVLayoutCanvas.setMembers(lVLayoutCanvas.getMembers());
						detailSectionIndirizzo.setOverflow(Overflow.VISIBLE);
						detailSectionIndirizzo.setHeight100();
						lVLayoutCanvas.markForRedraw();
					}
					
					@Override
					public void open(ServiceCallback<String> errorCallback) {
						super.open(errorCallback);
						lVLayoutCanvas.setMembers(lVLayoutCanvas.getMembers());
						detailSectionIndirizzo.setOverflow(Overflow.VISIBLE);
						detailSectionIndirizzo.setHeight100();
						lVLayoutCanvas.markForRedraw();
					}

					@Override
					public void close() {
						super.close();
						lVLayoutCanvas.setMembers(lVLayoutCanvas.getMembers());
						detailSectionIndirizzo.setOverflow(Overflow.HIDDEN);
						detailSectionIndirizzo.setHeight(20);
						lVLayoutCanvas.markForRedraw();
					}
					
					@Override
					public boolean showTitleInSection() {
						return false;
					}
					
				};
				lVLayoutCanvas.setMembers(mDynamicForm, detailSectionIndirizzo);
			} else {
				lVLayoutCanvas.setMembers(mDynamicForm, mIndirizzoDynamicForm1, mIndirizzoDynamicForm2);
			}
			addChild(lVLayoutCanvas);
		} else {
			addChild(mDynamicForm);
		}
	}
	
	public void setVisibleIndirizzo(boolean visible) {
		setVisibleIndirizzo(visible, true);
	}

	public void setVisibleIndirizzo(boolean visible, boolean clearValues) {		
		if(detailSectionIndirizzo != null){
			detailSectionIndirizzo.hide();
		}					
		if(mIndirizzoDynamicForm1 != null){
			mIndirizzoDynamicForm1.hide();
		}
		if(mIndirizzoDynamicForm2 != null){
			mIndirizzoDynamicForm2.hide();
		}
		if (showItemsIndirizzo()) {
			if (visible) {
				mIndirizzoDynamicForm1.show();
				mIndirizzoDynamicForm2.show();
				getValuesManager().addMember(mIndirizzoDynamicForm1);
				getValuesManager().addMember(mIndirizzoDynamicForm2);
				if (showItemsIndirizzoWithBorder() && detailSectionIndirizzo != null) {
					detailSectionIndirizzo.show();									
					lVLayoutCanvas.setMembers(mDynamicForm, detailSectionIndirizzo);
				} else {
					lVLayoutCanvas.setMembers(mDynamicForm, mIndirizzoDynamicForm1, mIndirizzoDynamicForm2);
				}
			} else {
				if(clearValues) {
					editRecordIndirizzo(new Record());
				}
				getValuesManager().removeMember(mIndirizzoDynamicForm1);
				getValuesManager().removeMember(mIndirizzoDynamicForm2);
				lVLayoutCanvas.setMembers(mDynamicForm);
			}
			lVLayoutCanvas.redraw();
		}
	}

	@Override
	public void redraw() {
		
		super.redraw();	
		
		if(detailSectionIndirizzo != null){
			detailSectionIndirizzo.close();
			if (showItemsIndirizzo() && showItemsIndirizzoWithBorder()) {
				if (showOpenIndirizzo()) {
					detailSectionIndirizzo.open();
				}
			}
		}
	}

	public boolean showOpenIndirizzo() {
		return false;
	}

	@Override
	public ReplicableCanvasForm[] getForm() {		
		List<ReplicableCanvasForm> listaForm = new ArrayList<ReplicableCanvasForm>();
		listaForm.add(mDynamicForm);
		if(mIndirizzoDynamicForm1 != null) {
			listaForm.add(mIndirizzoDynamicForm1);
		}
		if(mIndirizzoDynamicForm2 != null) {
			listaForm.add(mIndirizzoDynamicForm2);
		}
		return listaForm.toArray(new ReplicableCanvasForm[listaForm.size()]);		
	}

	protected void buildIndirizzoForm() {

		final GWTRestDataSource statoDS = new GWTRestDataSource("StatoDataSource", "codIstatStato", FieldType.TEXT, true);
		final GWTRestDataSource tipoToponimoDS = new GWTRestDataSource("TipoToponimoDataSource", "key", FieldType.TEXT, true);
		if (isIndirizzoObbligatorio()) {
			tipoToponimoDS.addParam("obbligatorio", "true");
		}
		final GWTRestDataSource comuniDS = new GWTRestDataSource("ComuneDataSource", "codIstatComune", FieldType.TEXT, true);
		comuniDS.addParam("flgSoloVld", "1");
		final GWTRestDataSource frazioniDS = new GWTRestDataSource("FrazioneDataSource", "frazione", FieldType.TEXT);
		final GWTRestDataSource capDS = new GWTRestDataSource("CapDataSource", "cap", FieldType.TEXT);

		nomeStatoItem = new HiddenItem("nomeStato");
		nomeStatoItem.setDefaultValue(_NOME_STATO_ITALIA);

		statoItem = new FilteredSelectItem("stato", I18NUtil.getMessages().soggetti_detail_indirizzi_statoItem_title()) {
			
			@Override
			public void onOptionClick(Record record) {	
				
				super.onOptionClick(record);
				
				mIndirizzoDynamicForm1.setValue("nomeStato", record.getAttribute("nomeStato"));
			}

			@Override
			protected void clearSelect() {
				
				super.clearSelect();
				
				mIndirizzoDynamicForm1.setValue("nomeStato", "");
				mIndirizzoDynamicForm1.setValue("comune", "");
				mIndirizzoDynamicForm1.setValue("provincia", "");
				mIndirizzoDynamicForm1.setValue("citta", "");
				mIndirizzoDynamicForm1.redraw();
			};
			
			@Override
			public void setValue(String value) {
				
				super.setValue(value);
				
				if (value == null || "".equals(value)) {
					mIndirizzoDynamicForm1.setValue("nomeStato", "");
					mIndirizzoDynamicForm1.setValue("comune", "");
					mIndirizzoDynamicForm1.setValue("provincia", "");
					mIndirizzoDynamicForm1.setValue("citta", "");
					mIndirizzoDynamicForm1.redraw();
				}
            }
		};
		ListGridField codIstatStatoField = new ListGridField("codIstatStato", "Cod. Istat");
		codIstatStatoField.setHidden(true);
		ListGridField nomeStatoField = new ListGridField("nomeStato", "Stato");
		statoItem.setPickListFields(codIstatStatoField, nomeStatoField);
		statoItem.setValueField("codIstatStato");
		statoItem.setDisplayField("nomeStato");
		statoItem.setOptionDataSource(statoDS);
		statoItem.setWidth(150);
		statoItem.setAutoFetchData(false);
		statoItem.setAlwaysFetchMissingValues(true);
		statoItem.setFetchMissingValues(true);
		statoItem.setDefaultValue(_COD_ISTAT_ITALIA);
		LinkedHashMap<String, String> statoValueMap = new LinkedHashMap<String, String>();
		statoValueMap.put(_COD_ISTAT_ITALIA, _NOME_STATO_ITALIA);
		statoItem.setValueMap(statoValueMap);				
		statoItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
		statoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return getShowStato() && isIndirizzoObbligatorio();
			}
		}));		
		statoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return getShowStato();
			}
		});
		statoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				mIndirizzoDynamicForm1.redraw();
				mIndirizzoDynamicForm2.redraw();
				Record record = getFormValuesAsRecord();
				GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
				if (record.getAttribute("stato") != null && !"".equals(record.getAttributeAsString("stato"))) {
					statoDS.addParam("codIstatStato", record.getAttributeAsString("stato"));
				} else {
					statoDS.addParam("codIstatStato", null);
				}
				statoItem.setOptionDataSource(statoDS);
				manageOnChangedIndirizzo();
			}
		});

		flgFuoriComuneItem = new CheckboxItem("flgFuoriComune", "fuori Comune");
		flgFuoriComuneItem.setColSpan(1);
		flgFuoriComuneItem.setWidth(50);
		flgFuoriComuneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				mIndirizzoDynamicForm1.setValue("codToponimo", "");
				mIndirizzoDynamicForm1.setValue("tipoToponimo", "");
				mIndirizzoDynamicForm1.setValue("indirizzo", "");
				mIndirizzoDynamicForm1.setValue("civico", "");
				mIndirizzoDynamicForm1.setValue("appendici", "");
				mIndirizzoDynamicForm1.setValue("nomeComune", "");
				mIndirizzoDynamicForm1.setValue("comune", "");
				mIndirizzoDynamicForm1.setValue("citta", "");
				mIndirizzoDynamicForm1.setValue("provincia", "");
				mIndirizzoDynamicForm2.setValue("frazione", "");
				mIndirizzoDynamicForm2.setValue("cap", "");
				mIndirizzoDynamicForm2.setValue("zona", "");
				mIndirizzoDynamicForm2.setValue("complementoIndirizzo", "");
				frazioneItem.fetchData();
				capItem.fetchData();
				if (event.getValue() != null && (Boolean)event.getValue()) {
					tipoToponimoItem.setValue("VIA");
				}
				mIndirizzoDynamicForm1.redraw();
				mIndirizzoDynamicForm2.redraw();
				manageOnChangedIndirizzo();
			}
		});
		flgFuoriComuneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(!isStatoItalia()) {
					flgFuoriComuneItem.setValue(true);
				} else if(!showFlgFuoriComune()) {
					flgFuoriComuneItem.setValue(getFlgFuoriComune());
				}
				return showFlgFuoriComune() && isStatoItalia();
			}
		});

		ImgButtonItem lookupIndirizzoButton = new ImgButtonItem("lookupIndirizzoButton", "lookup/indirizzo.png", "Seleziona indirizzo dal viario");
		lookupIndirizzoButton.setColSpan(1);
		lookupIndirizzoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && isInComune() && !isClientToponomasticaAttivo();
			}
		});
		lookupIndirizzoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				LookupViario lookupViario = new LookupViario(null);
				lookupViario.show();
			}
		});

		codToponimoItem = new HiddenItem("codToponimo");
		civicoPresenteInViaItem = new HiddenItem("civicoPresenteInVia");

		tipoToponimoItem = new FilteredSelectItem("tipoToponimo");
		tipoToponimoItem.setValueField("key");
		tipoToponimoItem.setDisplayField("displayValue");
		ListGridField tipoToponimoField = new ListGridField("value", "Tipo");
		tipoToponimoItem.setPickListFields(tipoToponimoField);
		tipoToponimoItem.setShowTitle(false);
		tipoToponimoItem.setWidth(120);
		tipoToponimoItem.setAllowEmptyValue(false);
		tipoToponimoItem.setOptionDataSource(tipoToponimoDS);
		tipoToponimoItem.setAutoFetchData(false);
		tipoToponimoItem.setAlwaysFetchMissingValues(true);
		tipoToponimoItem.setFetchMissingValues(true);
		tipoToponimoItem.setDefaultValue("VIA");
		LinkedHashMap<String, String> tipoToponimoValueMap = new LinkedHashMap<String, String>();
		tipoToponimoValueMap.put("VIA", "VIA");
		tipoToponimoItem.setValueMap(tipoToponimoValueMap);
		tipoToponimoItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
		tipoToponimoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return (isStatoItalia() && !isInComune()) && isIndirizzoObbligatorio();
			}
		}));
		tipoToponimoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && !isInComune();
			}
		});		
		tipoToponimoItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				manageOnChangedIndirizzo();
			}
		});
		
		toponimoItem = new ExtendedTextItem("toponimo");
		toponimoItem.setShowTitle(false);
		toponimoItem.setWidth(200);
		toponimoItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
		toponimoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return (isStatoItalia() && !isInComune()) && isIndirizzoObbligatorio();
			}
		}));
		toponimoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && !isInComune();
			}
		});
		toponimoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageOnChangedIndirizzo();
			}
		});
		
		if (!isClientToponomasticaAttivo()) {
			CustomValidator indirizzoInComuneValidator = new CustomValidator() {
				
				@Override
				protected boolean condition(Object value) {				
					final Record lRecordIndirizzo = getFormValuesAsRecord();
					if (isStatoItalia() && isInComune() && isNotBlank(lRecordIndirizzo.getAttribute("indirizzo"))) {
						return isNotBlank(lRecordIndirizzo.getAttribute("codToponimo"));
					} 
					return true;
				}
			};
			indirizzoInComuneValidator.setErrorMessage("Indirizzo non valido");
			
			indirizzoItem = new ExtendedTextItem("indirizzo", I18NUtil.getMessages().soggetti_detail_indirizzi_indirizzoItem_title());
			indirizzoItem.setWidth(250);
			indirizzoItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
			indirizzoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
	
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return (!isStatoItalia() || isInComune()) && isIndirizzoObbligatorio();
				}
			}), indirizzoInComuneValidator);	
			indirizzoItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					// if(isStatoItalia() && isInComune()) {
					// indirizzoItem.setCanEdit(false);
					// } else {
					// indirizzoItem.setCanEdit(getEditing());
					// }
					return !isStatoItalia() || isInComune();
				}
			});
			indirizzoItem.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					mIndirizzoDynamicForm1.setValue("codToponimo", "");
				}
			}); 
			
			if (indirizzoItem instanceof ExtendedTextItem) {
				((ExtendedTextItem) indirizzoItem).addChangedBlurHandler(new ChangedHandler() {
		
					@Override
					public void onChanged(ChangedEvent event) {
		
						mIndirizzoDynamicForm1.markForRedraw();
						mIndirizzoDynamicForm2.markForRedraw();
						controllaDatiIndirizzo(false);
						manageOnChangedIndirizzo();
					}
				});
			}
		} else {
			
			GWTRestDataSource aurigaAutoCompletamentoIndirizziDS = new GWTRestDataSource("AurigaAutoCompletamentoIndirizziDatasource");
			
			indirizzoItem = new ComboBoxItem("indirizzo",  I18NUtil.getMessages().soggetti_detail_indirizzi_indirizzoItem_title());
			indirizzoItem.setValueField("nomeVia");
			indirizzoItem.setDisplayField("nomeVia");
			indirizzoItem.setShowPickerIcon(false);
			indirizzoItem.setWidth(COMPONENT_WIDTH);
			indirizzoItem.setTextBoxStyle(it.eng.utility.Styles.textItem);
			indirizzoItem.setCanEdit(true);
			indirizzoItem.setColSpan(8);
			indirizzoItem.setEndRow(false);
			indirizzoItem.setStartRow(false);			
			((ComboBoxItem) indirizzoItem).setAutoFetchData(false);
			((ComboBoxItem) indirizzoItem).setAlwaysFetchMissingValues(true);
			((ComboBoxItem) indirizzoItem).setAddUnknownValues(true);
			((ComboBoxItem) indirizzoItem).setOptionDataSource(aurigaAutoCompletamentoIndirizziDS);
			((ComboBoxItem) indirizzoItem).setFetchDelay(500);
			((ComboBoxItem) indirizzoItem).setValidateOnChange(false);
			indirizzoItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {

				@Override
				public String getHoverHTML(FormItem item, DynamicForm form) {
					return StringUtil.asHTML((String) indirizzoItem.getValue());
				}
			});
			indirizzoItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
			((ComboBoxItem) indirizzoItem).setValidators(new CustomValidator() {

				@Override
				protected boolean condition(Object value) {
					String codToponimo = mIndirizzoDynamicForm1.getValueAsString("codToponimo");
					return !isIndirizzoObbligatorio() || !isInComune() || (codToponimo != null && !"".equalsIgnoreCase(codToponimo));
				}
			});
			indirizzoItem.setShowIfCondition(new FormItemIfFunction() {
				
				@Override
				public boolean execute(FormItem item, Object value, DynamicForm form) {
					return !isStatoItalia() || isInComune();
				}
			});
//			indirizzoItem.addChangedHandler(new ChangedHandler() {
//				
//				@Override
//				public void onChanged(ChangedEvent event) {
//					if(indirizzoItem.getSelectedRecord() != null && indirizzoItem.getSelectedRecord().getAttributeAsString("nomeVia") != null
//							&& indirizzoItem.getSelectedRecord().getAttributeAsString("codiceStrada") != null) {
//						mIndirizzoDynamicForm1.setValue("codToponimo", indirizzoItem.getSelectedRecord().getAttributeAsString("codiceStrada"));
//					} 
//				}
//			});
			indirizzoItem.addChangeHandler(new ChangeHandler() {
				
				@Override
				public void onChange(ChangeEvent event) {
					mIndirizzoDynamicForm1.setValue("codToponimo", "");
				}
			});
			indirizzoItem.addBlurHandler(new BlurHandler() {
				
				@Override
				public void onBlur(BlurEvent event) {
					
					indirizzoItem.validate();
				}
			});			
			
			ListGridField indirizzoPrefNameField = new ListGridField("nomeContatto");
			indirizzoPrefNameField.setWidth("100%");
			indirizzoPrefNameField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

					if (record != null) {
						return record.getAttributeAsString("nomeVia");
					}
					return null;
				}
			});
			((ComboBoxItem) indirizzoItem).setPickListFields(indirizzoPrefNameField);
			
			ListGrid indirizzoPickListProperties = new ListGrid();
			indirizzoPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
			indirizzoPickListProperties.setShowHeader(false);
			indirizzoPickListProperties.addCellClickHandler(new CellClickHandler() {

				@Override
				public void onCellClick(CellClickEvent event) {
					event.cancel();
					selezionaIndirizzo(event.getRecord());
				}
			});
			
			indirizzoPickListProperties.addFetchDataHandler(new FetchDataHandler() {

				@Override
				public void onFilterData(FetchDataEvent event) {
							
					String nome = indirizzoItem != null && indirizzoItem.getValue() != null ? (String) indirizzoItem.getValue() : null;
					GWTRestDataSource lComboBoxIndirizziDS = (GWTRestDataSource) indirizzoItem.getOptionDataSource();
					lComboBoxIndirizziDS.addParam("indirizzo", nome);
					((ComboBoxItem) indirizzoItem).setOptionDataSource(lComboBoxIndirizziDS);
					((ComboBoxItem) indirizzoItem).invalidateDisplayValueCache();
				}
			});
			((ComboBoxItem) indirizzoItem).setPickListProperties(indirizzoPickListProperties);
		}
		


		// ImgButtonItem clearIndirizzoButton = new ImgButtonItem("clearIndirizzoButton", "buttons/clear.png", "Rimuovi indirizzo");
		// clearIndirizzoButton.setColSpan(1);
		// clearIndirizzoButton.setShowIfCondition(new FormItemIfFunction() {
		// @Override
		// public boolean execute(FormItem item, Object value, DynamicForm form) {
		// return isStatoItalia() && isInComune();
		// }
		// });
		// clearIndirizzoButton.addIconClickHandler(new IconClickHandler() {
		// @Override
		// public void onIconClick(IconClickEvent event) {
		// clearIndirizzo();
		// }
		// });

		ImgButtonItem cercaIndirizzoButton = new ImgButtonItem("cercaIndirizzoButton", "lookup/indirizzosearch.png", "Cerca indirizzo nel viario");
		cercaIndirizzoButton.setColSpan(1);
		cercaIndirizzoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && isInComune() && !isClientToponomasticaAttivo();
			}
		});
		cercaIndirizzoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				controllaDatiIndirizzo(true);
			}
		});

		SpacerItem civicoSpacerItem = new SpacerItem();
		civicoSpacerItem.setWidth(10);
		civicoSpacerItem.setColSpan(1);

		ImgButtonItem lookupCivicoButton = new ImgButtonItem("lookupCivicoButton", "lookup/civico.png", "Seleziona civico");
		lookupCivicoButton.setColSpan(1);
		lookupCivicoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && isInComune() && isCodToponimoValorizzato() && !isClientToponomasticaAttivo();
			}
		});
		lookupCivicoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = getFormValuesAsRecord();
				LookupCivici lookupCivici = new LookupCivici(record.getAttribute("codToponimo"));
				lookupCivici.show();
			}
		});

		civicoItem = new ExtendedTextItem("civico", I18NUtil.getMessages().soggetti_detail_indirizzi_civicoItem_title());
		
		if (isClientToponomasticaAttivo()) {
			civicoItem.setWidth(68);
		} else { 
			civicoItem.setWidth(50);
		}
		civicoItem.setLength(5);
		civicoItem.setHint("&nbsp;/");
		civicoItem.setHintStyle(it.eng.utility.Styles.formTitle);
		if (isClientToponomasticaAttivo()) {
//			final boolean civicoObbligatorioInClientToponomastica = getValoreSempliceVariabileSezioneCacheAsBoolean(getParametriClientToponomastica(), "civicoObbligatorio");
//			if (civicoObbligatorioInClientToponomastica) {
//				civicoItem.setAttribute("obbligatorio", true);
//			} else {
//				civicoItem.setAttribute("obbligatorio", false);
//			}
			civicoItem.setAttribute("obbligatorio", isCivicoObbligatorio());
			
			civicoItem.setValidators(new CustomValidator() {
				
				@Override
				protected boolean condition(Object value) {
					String numeroCivico = mIndirizzoDynamicForm1.getValueAsString("civico") != null ? mIndirizzoDynamicForm1.getValueAsString("civico") : "";
					String letteraCivico = mIndirizzoDynamicForm1.getValueAsString("appendici") != null ? mIndirizzoDynamicForm1.getValueAsString("appendici") : "";
					if (!isCivicoObbligatorio() && "".equalsIgnoreCase(numeroCivico) && "".equalsIgnoreCase(letteraCivico)){
						// Se numero e lettera del civico sono vuoti non faccio la verifica
						return true;
					} else {
						String civicoPresenteInVia = mIndirizzoDynamicForm1.getValueAsString("civicoPresenteInVia");
						return  Boolean.parseBoolean(civicoPresenteInVia);
					}
				}
			});
		} else if (isCivicoObbligatorio()) {
			civicoItem.setAttribute("obbligatorio", true);
			civicoItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

				@Override
				public boolean execute(FormItem formItem, Object value) {
					return !isStatoItalia() || isInComune();
				}
			}));
		}
		civicoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (isClientToponomasticaAttivo()) {
					civicoPresenteInViaItem.setValue("");
					controllaIndirizzoSuClientToponomastica();
				} else {
					controllaDatiIndirizzo(false);
				}
				manageOnChangedIndirizzo();
			}
		});
		
		appendiciItem = new ExtendedTextItem("appendici");
		appendiciItem.setWidth(50);
		appendiciItem.setTitleOrientation(TitleOrientation.RIGHT);
		appendiciItem.setShowTitle(false);
		appendiciItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (isClientToponomasticaAttivo()) {
					civicoPresenteInViaItem.setValue("");
					controllaIndirizzoSuClientToponomastica();
				} else {
					controllaDatiIndirizzo(false);
				}
			}
		});

		nomeComuneItem = new HiddenItem("nomeComune");

		comuneItem = new FilteredSelectItem("comune", I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_title()) {

			@Override
			public void onOptionClick(Record record) {
				
				super.onOptionClick(record);
				
				mIndirizzoDynamicForm1.setValue("nomeComune", record.getAttribute("nomeComune"));

				GWTRestDataSource comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				comuneDS.addParam("nomeComune", record.getAttribute("nomeComune"));
				comuneItem.setOptionDataSource(comuneDS);

				mIndirizzoDynamicForm1.setValue("provincia", record.getAttribute("targaProvincia"));
				Criterion[] criterias = new Criterion[1];
				String value = record.getAttribute(comuneItem.getValueField());
				if (value != null && !"".equals(value)) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, value);
				}
				frazioneItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						Map frazioneValueMap = response.getDataAsRecordList().getValueMap(frazioneItem.getValueField(), frazioneItem.getDisplayField());
						if (frazioneItem.getValue() != null && !"".equals(frazioneItem.getValueAsString())
								&& !frazioneValueMap.containsKey(frazioneItem.getValueAsString())) {
							mIndirizzoDynamicForm2.setValue("frazione", "");
						}
						Criterion[] criterias = new Criterion[1];
						if (frazioneItem.getValue() != null && !"".equals(frazioneItem.getValueAsString())) {
							criterias[0] = new Criterion("frazione", OperatorId.IEQUALS, frazioneItem.getValueAsString());
						} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
							criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comuneItem.getValueAsString());
						}
						capItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								
								Map capValueMap = response.getDataAsRecordList().getValueMap(capItem.getValueField(), capItem.getDisplayField());
								if (capItem.getValue() != null && !"".equals(capItem.getValueAsString())
										&& !capValueMap.containsKey(capItem.getValueAsString())) {
									mIndirizzoDynamicForm2.setValue("cap", "");
								}
								if (capValueMap.keySet().size() == 1) {
									mIndirizzoDynamicForm2.setValue("cap", String.valueOf(capValueMap.keySet().iterator().next()));
								}
							}
						});
					}
				});
			}

			@Override
			protected void clearSelect() {
				
				super.clearSelect();
				
				mIndirizzoDynamicForm1.setValue("nomeComune", "");
				GWTRestDataSource comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				comuneDS.addParam("nomeComune", null);
				comuneItem.setOptionDataSource(comuneDS);
				mIndirizzoDynamicForm1.setValue("provincia", "");
			};

			@Override
			public void setValue(String value) {	
				
				super.setValue(value);			
				
				if (value == null || "".equals(value)) {
					mIndirizzoDynamicForm1.setValue("nomeComune", "");
					GWTRestDataSource comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
					comuneDS.addParam("nomeComune", null);
					comuneItem.setOptionDataSource(comuneDS);
					mIndirizzoDynamicForm1.setValue("provincia", "");
				}
			}
		};
		ListGridField codIstatComuneField = new ListGridField("codIstatComune", "Cod. Istat");
		codIstatComuneField.setHidden(true);
		ListGridField nomeComuneField = new ListGridField("nomeComune", "Comune");
		ListGridField targaProvinciaField = new ListGridField("targaProvincia", "Prov.");
		targaProvinciaField.setWidth(50);
		comuneItem.setPickListFields(codIstatComuneField, nomeComuneField, targaProvinciaField);
		comuneItem.setEmptyPickListMessage(I18NUtil.getMessages().soggetti_detail_indirizzi_comuneItem_noSearchOrEmptyMessage());
		comuneItem.setValueField("codIstatComune");
		comuneItem.setDisplayField("nomeComune");
		comuneItem.setOptionDataSource(comuniDS);
		comuneItem.setWidth(220);
		comuneItem.setAutoFetchData(false);		
		comuneItem.setAlwaysFetchMissingValues(true);
		comuneItem.setFetchMissingValues(true);
		comuneItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
		comuneItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return (isStatoItalia() && !isInComune()) && isIndirizzoObbligatorio();
			}
		}));
		comuneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && !isInComune();
			}
		});
		comuneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				Record record = getFormValuesAsRecord();
				GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				if (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune"))) {
					comuniDS.addParam("codIstatComune", record.getAttributeAsString("comune"));
					comuniDS.addParam("nomeComune", record.getAttributeAsString("nomeComune"));
				} else {
					comuniDS.addParam("codIstatComune", null);
					comuniDS.addParam("nomeComune", null);
				}
				comuneItem.setOptionDataSource(comuniDS);
				manageOnChangedIndirizzo();
			}
		});

		cittaItem = new ExtendedTextItem("citta", I18NUtil.getMessages().soggetti_detail_indirizzi_cittaItem_title());
		cittaItem.setWidth(220);
		cittaItem.setAttribute("obbligatorio", isIndirizzoObbligatorio());
		cittaItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return !isStatoItalia() && isIndirizzoObbligatorio();
			}
		}));
		cittaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !isStatoItalia();
			}
		});
		cittaItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageOnChangedIndirizzo();
			}
		});

		provinciaItem = new TextItem("provincia", I18NUtil.getMessages().soggetti_detail_indirizzi_provinciaItem_title());
		provinciaItem.setWidth(50);
		provinciaItem.setCanEdit(false);
		provinciaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia() && !isInComune();
			}
		});

		frazioneItem = new SelectItem("frazione", I18NUtil.getMessages().soggetti_detail_indirizzi_frazioneItem_title()) {

			@Override
			public void onOptionClick(Record record) {
				
				AdvancedCriteria criteria = null;
				String value = record.getAttribute(frazioneItem.getValueField());
				if (value != null && !"".equals(value)) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("frazione", OperatorId.IEQUALS, value);
					criteria = new AdvancedCriteria(OperatorId.AND, criterias);
				} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
					Criterion[] criterias = new Criterion[1];
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comuneItem.getValueAsString());
					criteria = new AdvancedCriteria(OperatorId.AND, criterias);
				}
				capItem.getOptionDataSource().fetchData(criteria, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						Map capValueMap = response.getDataAsRecordList().getValueMap(capItem.getValueField(), capItem.getDisplayField());
						if (capItem.getValue() != null && !"".equals(capItem.getValueAsString()) && !capValueMap.containsKey(capItem.getValueAsString())) {
							mIndirizzoDynamicForm2.setValue("cap", "");
						}
						if (capValueMap.keySet().size() == 1) {
							mIndirizzoDynamicForm2.setValue("cap", String.valueOf(capValueMap.keySet().iterator().next()));
						}
					}
				});
			}
		};
		frazioneItem.setValueField("frazione");
		frazioneItem.setDisplayField("frazione");
		frazioneItem.setOptionDataSource(frazioniDS);
		frazioneItem.setWidth(150);
		frazioneItem.setAllowEmptyValue(true);
		frazioneItem.setAutoFetchData(false);	
		frazioneItem.setAlwaysFetchMissingValues(true);
		frazioneItem.setFetchMissingValues(true);	
		frazioneItem.setCachePickListResults(false);
		frazioneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				
				Criterion[] criterias = new Criterion[1];
				String comune = null;
				if (isStatoItalia() && isInComune()) {
					comune = getCodIstatComuneRif();
				} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
					comune = comuneItem.getValueAsString();
				}
				if (comune != null) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comune);
				} else {
					criterias[0] = new Criterion("frazione", OperatorId.IS_NULL);
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		frazioneItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia();
			}
		});

		capItem = new ComboBoxItem("cap", I18NUtil.getMessages().soggetti_detail_indirizzi_capItem_title());
		ListGridField capField = new ListGridField("cap", "Cap");
		capItem.setPickListFields(capField);
		capItem.setValueField("cap");
		capItem.setDisplayField("cap");
		capItem.setOptionDataSource(capDS);
		capItem.setWidth(80);
		capItem.setAllowEmptyValue(true);
		capItem.setCachePickListResults(false);
		capItem.setAutoFetchData(false);
		capItem.setLength(5);
		capItem.setKeyPressFilter("[0-9]");
		capItem.setAlwaysFetchMissingValues(true);
		capItem.setFetchMissingValues(true);
		capItem.setAddUnknownValues(true);
		capItem.setCompleteOnTab(false);
		capItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {

			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				
				Criterion[] criterias = new Criterion[1];
				String frazione = null;
				if (frazioneItem.getValue() != null && !"".equals(frazioneItem.getValueAsString())) {
					frazione = frazioneItem.getValueAsString();
				}
				String comune = null;
				if (isStatoItalia() && isInComune()) {
					comune = getCodIstatComuneRif();
				} else if (comuneItem.getValue() != null && !"".equals(comuneItem.getValueAsString())) {
					comune = comuneItem.getValueAsString();
				}
				if (frazione != null) {
					criterias[0] = new Criterion("frazione", OperatorId.IEQUALS, frazione);
				} else if (comune != null) {
					criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, comune);
				} else {
					criterias[0] = new Criterion("cap", OperatorId.IS_NULL);
				}
				return new AdvancedCriteria(OperatorId.AND, criterias);
			}
		});
		capItem.setAttribute("obbligatorio", isIndirizzoObbligatorio() && !isForceCapNonObbligatorio());
		capItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isStatoItalia() && isIndirizzoObbligatorio() && !isForceCapNonObbligatorio();
			}
		}));
		capItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isStatoItalia();
			}
		});		
		capItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				manageOnChangedIndirizzo();
			}
		});

		zonaItem = new TextItem("zona", I18NUtil.getMessages().soggetti_detail_indirizzi_zonaItem_title());
		zonaItem.setWidth(80);

		complementoIndirizzoItem = new TextItem("complementoIndirizzo", I18NUtil.getMessages().soggetti_detail_indirizzi_complementoIndirizzoItem_title());
		complementoIndirizzoItem.setWidth(350);
		complementoIndirizzoItem.setWrapTitle(false);

		mIndirizzoDynamicForm1 = new ReplicableCanvasForm() {
			
			@Override
			public boolean hasValue(Record defaultRecord) {
				
				Map<String,Object> values = getValuesManager() != null ? getValuesManager().getValues() : getValues();

				if (values != null && values.size() > 0) {
					if (checkIndirizzoValorizzato(values)) {
						return true;
					}
				}
				return false;
			}
		};
		mIndirizzoDynamicForm1.setWrapItemTitles(false);
		mIndirizzoDynamicForm1.setNumCols(20);
		mIndirizzoDynamicForm1.setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");
		mIndirizzoDynamicForm1.setFields(
			nomeStatoItem, statoItem, flgFuoriComuneItem, lookupIndirizzoButton, codToponimoItem, tipoToponimoItem, toponimoItem,
			civicoPresenteInViaItem, indirizzoItem, cercaIndirizzoButton, civicoSpacerItem, lookupCivicoButton, civicoItem, appendiciItem, nomeComuneItem, comuneItem, cittaItem, 
			provinciaItem);

		mIndirizzoDynamicForm2 = new ReplicableCanvasForm() {
			
			@Override
			public boolean hasValue(Record defaultRecord) {
				
				Map<String,Object> values = getValuesManager() != null ? getValuesManager().getValues() : getValues();

				if (values != null && values.size() > 0) {
					if (checkIndirizzoValorizzato(values)) {
						return true;
					}
				}
				return false;
			}
		};
		mIndirizzoDynamicForm2.setWrapItemTitles(false);
		mIndirizzoDynamicForm2.setNumCols(20);
		mIndirizzoDynamicForm2.setColWidths(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, "*", "*");
		mIndirizzoDynamicForm2.setFields(frazioneItem, capItem, zonaItem, complementoIndirizzoItem);
	}

	protected boolean isStatoItalia() {
		String stato = ((statoItem.getValue() != null) ? (String) statoItem.getValue() : "");
		return "".equals(stato) || _COD_ISTAT_ITALIA.equals(stato) || _NOME_STATO_ITALIA.equals(stato);
	}

	// Controllo che la parte di indirizzo (composto da mIndirizzoDynamicForm1 e mIndirizzoDynamicForm2) sia valorizzato
	public Boolean checkIndirizzoValorizzato(Map<String, Object> lMap){

		if (isStatoItalia()) {
			if (isInComune(lMap.get("flgFuoriComune") != null && (Boolean)lMap.get("flgFuoriComune"))) {
				if((lMap.get("cap") != null && !"".equals(lMap.get("cap"))) || 
				   (lMap.get("indirizzo") != null && !"".equals(lMap.get("indirizzo"))) ) {
					return true;
				}
			} else {
				if((lMap.get("cap") != null && !"".equals(lMap.get("cap"))) || 
				   (lMap.get("toponimo") != null && !"".equals(lMap.get("toponimo"))) || 
				   (lMap.get("nomeComune") != null && !"".equals(lMap.get("nomeComune"))) ) {
					return true;
				}
			}
		}else {
			if ((lMap.get("citta") != null && !"".equals(lMap.get("citta"))) ||
				(lMap.get("indirizzo") != null && !"".equals(lMap.get("indirizzo"))) ) {
				return true;
			}
		}
		return false;
	}

	
	public boolean getFlgFuoriComune() {
		return true;
	}
	
	public boolean getShowStato() {
		if(isInComune()) {
			return false;
		}
		return true;
	}

	protected boolean isInComune() {
		return isInComune(flgFuoriComuneItem.getValueAsBoolean());		
	}
	
	protected boolean isInComune(Boolean flgFuoriComune) {
		return (showFlgFuoriComune() && (flgFuoriComune == null || !flgFuoriComune)) || (!showFlgFuoriComune() && !getFlgFuoriComune());
	}

	private boolean isCodToponimoValorizzato() {
		Record record = getFormValuesAsRecord();
		return record.getAttribute("codToponimo") != null && !"".equals(record.getAttribute("codToponimo"));
	}
	
	protected boolean isControllaIndirizzoAfterEditRecord() {
		return false;
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		
		if (showItemsIndirizzo()) {
			// if(isStatoItalia() && isInComune()) {
			// indirizzoItem.setCanEdit(false);
			// }
			provinciaItem.setCanEdit(false);
		}
	}
	
	@Override
	public void editRecord(Record record) {
		
		if (showItemsIndirizzo()) {
			GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
			if (record.getAttribute("stato") != null && !"".equals(record.getAttributeAsString("stato"))) {
				statoDS.addParam("codIstatStato", record.getAttributeAsString("stato"));
			} else {
				statoDS.addParam("codIstatStato", null);
			}
			statoItem.setOptionDataSource(statoDS);
			GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
			if (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune"))) {
				comuniDS.addParam("codIstatComune", record.getAttributeAsString("comune"));
				comuniDS.addParam("nomeComune", record.getAttributeAsString("nomeComune"));
			} else {
				comuniDS.addParam("codIstatComune", null);
				comuniDS.addParam("nomeComune", null);
			}
			comuneItem.setOptionDataSource(comuniDS);
			if ((record.getAttribute("provincia") == null || "".equals(record.getAttributeAsString("provincia")))
					&& (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune")))) {
				final String codIstatComune = record.getAttributeAsString("comune");
				Criterion[] criterias = new Criterion[1];
				criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, codIstatComune);
				comuneItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();
						if (data.getLength() > 0) {
							for (int i = 0; i < data.getLength(); i++) {
								if (data.get(i).getAttribute("codIstatComune").equals(codIstatComune)) {
									mIndirizzoDynamicForm1.setValue("provincia", data.get(i).getAttribute("targaProvincia"));
									break;
								}
							}
						}
					}
				});
			}
			if (isClientToponomasticaAttivo() && record.getAttribute("civico") != null && !"".equalsIgnoreCase(record.getAttribute("civico"))) {
				record.setAttribute("civicoPresenteInVia", true);
			}
			initSelectValueMaps(record);	
		}
		
		super.editRecord(record);
		
		if (showItemsIndirizzo()) {
			if (isControllaIndirizzoAfterEditRecord()) {
				controllaDatiIndirizzo(false);
			}
			manageOnChangedIndirizzo();
		}
	}
	
	
	public void initSelectValueMaps(Record record) {
				
		if (record.getAttribute("cap") != null && !"".equals(record.getAttributeAsString("cap"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("cap"), record.getAttribute("cap"));
			capItem.setValueMap(valueMap);
		}

		if (record.getAttribute("frazione") != null && !"".equals(record.getAttributeAsString("frazione"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("frazione"), record.getAttribute("frazione"));
			frazioneItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune")) && 
			record.getAttribute("nomeComune") != null && !"".equals(record.getAttributeAsString("nomeComune"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("comune"), record.getAttribute("nomeComune"));
			comuneItem.setValueMap(valueMap);
		}
		
		if (record.getAttribute("stato") != null && !"".equals(record.getAttributeAsString("stato")) &&
			record.getAttribute("nomeStato") != null && !"".equals(record.getAttributeAsString("nomeStato"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("stato"), record.getAttribute("nomeStato"));
			statoItem.setValueMap(valueMap);
		} else {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(_COD_ISTAT_ITALIA, _NOME_STATO_ITALIA);
			statoItem.setValueMap(valueMap);
		}

		if (record.getAttribute("tipoToponimo") != null && !"".equals(record.getAttributeAsString("tipoToponimo"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("tipoToponimo"), record.getAttribute("tipoToponimo"));
			tipoToponimoItem.setValueMap(valueMap);			
		} else{
		    LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put("VIA", "VIA");
			tipoToponimoItem.setValueMap(valueMap);
		}	
	}	
	
	public void editRecordIndirizzo(Record record) {
		if (showItemsIndirizzo()) {
			GWTRestDataSource statoDS = (GWTRestDataSource) statoItem.getOptionDataSource();
			if (record.getAttribute("stato") != null && !"".equals(record.getAttributeAsString("stato"))) {
				statoDS.addParam("codIstatStato", record.getAttributeAsString("stato"));
			} else {
				statoDS.addParam("codIstatStato", null);
			}
			statoItem.setOptionDataSource(statoDS);
			GWTRestDataSource comuniDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
			if (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune"))) {
				comuniDS.addParam("codIstatComune", record.getAttributeAsString("comune"));
				comuniDS.addParam("nomeComune", record.getAttributeAsString("nomeComune"));
			} else {
				comuniDS.addParam("codIstatComune", null);
				comuniDS.addParam("nomeComune", null);
			}
			comuneItem.setOptionDataSource(comuniDS);
			if ((record.getAttribute("provincia") == null || "".equals(record.getAttributeAsString("provincia")))
					&& (record.getAttribute("comune") != null && !"".equals(record.getAttributeAsString("comune")))) {
				final String codIstatComune = record.getAttributeAsString("comune");
				Criterion[] criterias = new Criterion[1];
				criterias[0] = new Criterion("codIstatComune", OperatorId.IEQUALS, codIstatComune);
				comuneItem.getOptionDataSource().fetchData(new AdvancedCriteria(OperatorId.AND, criterias), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();
						if (data.getLength() > 0) {
							for (int i = 0; i < data.getLength(); i++) {
								if (data.get(i).getAttribute("codIstatComune").equals(codIstatComune)) {
									mIndirizzoDynamicForm1.setValue("provincia", data.get(i).getAttribute("targaProvincia"));
									break;
								}
							}
						}
					}
				});
			}
			if (isClientToponomasticaAttivo() && record.getAttribute("civico") != null && !"".equalsIgnoreCase(record.getAttribute("civico"))) {
				record.setAttribute("civicoPresenteInVia", true);
			}
			initSelectValueMaps(record);	
			
			mIndirizzoDynamicForm1.setValues(record.toMap());
			mIndirizzoDynamicForm2.setValues(record.toMap());
			
			if (isControllaIndirizzoAfterEditRecord()) {
				controllaDatiIndirizzo(false);
			}
			manageOnChangedIndirizzo();
		}		
	}

//	public void clearIndirizzo() {
//		if(showItemsIndirizzo()) {
//			mIndirizzoDynamicForm1.setValue("codToponimo", "");
//			mIndirizzoDynamicForm1.setValue("indirizzo", "");
//			mIndirizzoDynamicForm1.redraw();
//			manageOnChangedIndirizzo();
//		}
//	}

	public boolean isBlank(String str) {
		return str == null || "".equals(str);
	}

	public boolean isNotBlank(String str) {
		return str != null && !"".equals(str);
	}

	protected void controllaDatiIndirizzo(final boolean isFromCercaIndirizzoButton) {
		// Verifico le condizioni possibili per far scattare la ricerca
		final Record lRecordIndirizzo = getFormValuesAsRecord();
		if (isStatoItalia() && isInComune()) {
			if (isNotBlank(lRecordIndirizzo.getAttribute("indirizzo"))) {
				GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("CtrlDatiIndirizzoDataSource");
				lGwtRestService.call(lRecordIndirizzo, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						
						Boolean esito = object.getAttributeAsBoolean("esito");
						if (esito != null && esito) {
							// Pulisco i dati del viario
							mIndirizzoDynamicForm1.clearErrors(true);
							mIndirizzoDynamicForm2.clearErrors(true);

							mIndirizzoDynamicForm1.setValue("codToponimo", object.getAttribute("codToponimo"));
							mIndirizzoDynamicForm1.setValue("indirizzo", object.getAttribute("indirizzo"));
							mIndirizzoDynamicForm2.setValue("zona", object.getAttribute("zona"));
							mIndirizzoDynamicForm2.setValue("cap", object.getAttribute("cap"));
							
							manageOnChangedIndirizzo();
						} else {
							// ogni volta che scatta la validazione se non trova un solo risultato apre la lookup degli indirizzi
							// if (isFromCercaIndirizzoButton) {
							cercaIndirizzo();
							// }
						}
					}
				});
			} else {
				mIndirizzoDynamicForm1.setValue("codToponimo", "");
				if (isFromCercaIndirizzoButton) {
					cercaIndirizzo();
				}
			}
		}
	}

	protected void cercaIndirizzo() {
		Record record = getFormValuesAsRecord();
		LookupViario lookupViario = new LookupViario(record.getAttributeAsString("indirizzo"));
		lookupViario.show();
	}

	public abstract void buildMainForm();

	/**
	 * 
	 * Lookup VIARIO - CIVICI
	 *
	 */

	public class LookupViario extends LookupViarioPopup {

		public LookupViario(String indirizzo) {
			super(indirizzo, null, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordViario(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	public void setFormValuesFromRecordViario(Record record) {
		SC.echo(record.getJsObj());

		// Pulisco i dati del viario
		mIndirizzoDynamicForm1.setValue("codToponimo", "");
		mIndirizzoDynamicForm1.setValue("indirizzo", "");
		mIndirizzoDynamicForm2.setValue("zona", "");
		mIndirizzoDynamicForm2.setValue("cap", "");

		mIndirizzoDynamicForm1.clearErrors(true);
		mIndirizzoDynamicForm2.clearErrors(true);

		String descrNomeToponimo = record.getAttribute("descrNomeToponimo");
		String codiceViarioToponimo = record.getAttribute("codiceViarioToponimo");
		String capVie = record.getAttribute("capVie");
		String zonaVie = record.getAttribute("zonaVie");

		if (codiceViarioToponimo != null && !"".equals(codiceViarioToponimo)) {
			mIndirizzoDynamicForm1.setValue("codToponimo", codiceViarioToponimo);
			mIndirizzoDynamicForm1.setValue("indirizzo", descrNomeToponimo);
			mIndirizzoDynamicForm2.setValue("zona", zonaVie);
			mIndirizzoDynamicForm2.setValue("cap", capVie);
		}
		
		manageOnChangedIndirizzo();
	}

	public class LookupCivici extends LookupCiviciPopup {

		public LookupCivici(String codToponimo) {
			super(codToponimo, null, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordCivici(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	public void setFormValuesFromRecordCivici(Record record) {
		SC.echo(record.getJsObj());

		// Pulisco i dati del viario
		mIndirizzoDynamicForm1.setValue("civico", "");
		mIndirizzoDynamicForm1.setValue("appendici", "");
		mIndirizzoDynamicForm2.setValue("zona", "");
		mIndirizzoDynamicForm2.setValue("cap", "");

		mIndirizzoDynamicForm1.clearErrors(true);
		mIndirizzoDynamicForm2.clearErrors(true);

		String nrCivico = record.getAttribute("nrCivico");
		String esponenteBarrato = record.getAttribute("esponenteBarrato");
		String cap = record.getAttribute("cap");
		String zona = record.getAttribute("zona");

		if (nrCivico != null && !"".equals(nrCivico)) {
			mIndirizzoDynamicForm1.setValue("civico", nrCivico);
			mIndirizzoDynamicForm1.setValue("appendici", esponenteBarrato);
			mIndirizzoDynamicForm2.setValue("zona", zona);
			mIndirizzoDynamicForm2.setValue("cap", cap);
		}
		
		manageOnChangedIndirizzo();
	}

	public boolean isIndirizzoObbligatorio() {
		return false;
	}
	
	public boolean isCivicoObbligatorio() {
		return false;
	}
	
	public boolean isForceCapNonObbligatorio() {
		return true;
	}

	public abstract boolean showItemsIndirizzo();

	public boolean showItemsIndirizzoWithBorder() {
		return true;
	}

	public boolean showFlgFuoriComune() {
		String codIstatComuneRif = getCodIstatComuneRif();
		return codIstatComuneRif != null && !"".equals(codIstatComuneRif);
	}

	public String getCodIstatComuneRif() {
		return AurigaLayout.getParametroDB("ISTAT_COMUNE_RIF");
	}

	public String getNomeComuneRif() {
		return AurigaLayout.getParametroDB("NOME_COMUNE_RIF");
	}

	protected void setIndirizzoCompletoFromRecordRubrica(Record lRecord) {
		if (showItemsIndirizzo()) {
			if(lRecord != null) {
				if (lRecord.getAttribute("indirizzoCompleto") != null && !"".equals(lRecord.getAttribute("indirizzoCompleto"))) {
					// se arrivo dalla lista
					StringSplitterClient st = new StringSplitterClient(lRecord.getAttribute("indirizzoCompleto"), "|*|");
					try {
						String indirizzo = st.getTokens()[0] != null ? st.getTokens()[0] : "";// 1) descrizione Via/toponimo
						String cap = st.getTokens()[2] != null ? st.getTokens()[2] : "";// 3) CAP
						String nomeComuneCitta = st.getTokens()[3] != null ? st.getTokens()[3] : "";// 4) Comune / Città
						String targaProvincia = st.getTokens()[4] != null ? st.getTokens()[4] : "";// 5) Targa provincia
						String codViaInToponomastica = st.getTokens()[6] != null ? st.getTokens()[6] : "";// 7) Cod. via se in toponomastica
						String tipoToponimo = st.getTokens()[7] != null ? st.getTokens()[7] : "";// 8) Tipo toponimo
						String codIstatComune = st.getTokens()[8] != null ? st.getTokens()[8] : "";// 9) Cod. ISTAT comune
						String codIstatStato = st.getTokens()[9] != null ? st.getTokens()[9] : "";// 10) Cod. ISTAT stato
						String nomeStato = st.getTokens()[10] != null ? st.getTokens()[10] : "";// 11) Nome stato
						String civico = st.getTokens()[11] != null ? st.getTokens()[11] : "";// 12) Civico prima parte
						String appendice = st.getTokens()[12] != null ? st.getTokens()[12] : "";// 13)Appendice del civico
						String zona = st.getTokens()[13] != null ? st.getTokens()[13] : "";// 14)Zona
						String localitaFrazione = st.getTokens()[14] != null ? st.getTokens()[14] : "";// 15)Località/frazione
						String altriDati = st.getTokens()[15] != null ? st.getTokens()[15] : "";// 16)Complemento/dati aggiuntivi indirizzo
						Record record = new Record();
						record.setAttribute("codToponimo", codViaInToponomastica);
						record.setAttribute("indirizzo", indirizzo);
						record.setAttribute("provincia", targaProvincia);
						record.setAttribute("tipoToponimo", tipoToponimo);
						record.setAttribute("comune", codIstatComune);
						record.setAttribute("citta", nomeComuneCitta);
						record.setAttribute("nomeComune", nomeComuneCitta);
						record.setAttribute("stato", codIstatStato);
						record.setAttribute("nomeStato", nomeStato);
						record.setAttribute("civico", civico);
						record.setAttribute("appendici", appendice);
						record.setAttribute("complementoIndirizzo", altriDati);
						record.setAttribute("frazione", localitaFrazione);
						record.setAttribute("cap", cap);
						record.setAttribute("zona", zona);
						if ((codViaInToponomastica != null && !"".equals(codViaInToponomastica)) || (indirizzo == null || "".equals(indirizzo))) {
							record.setAttribute("flgFuoriComune", false);
						} else {
							record.setAttribute("flgFuoriComune", true);
							record.setAttribute("toponimo", indirizzo);
						}
						editRecordIndirizzo(record);					
					} catch (Exception e) {
						
					}
				} else if(lRecord.getAttributeAsRecordList("listaIndirizzi") != null && lRecord.getAttributeAsRecordList("listaIndirizzi").getLength() > 0) {
					// se arrivo dal dettaglio					
					editRecordIndirizzo(lRecord.getAttributeAsRecordList("listaIndirizzi").get(0));
				} else {
					// soggetto senza indirizzo				
					editRecordIndirizzo(new Record());
				}
			}
		}
	}
	
	protected void setIndirizzoAfterFindSoggettoService(Record object) {
		if (showItemsIndirizzo()) {
			Record recordIndirizzo = new Record();					
			if ((object.getAttribute("codToponomastica") != null && !"".equals(object.getAttribute("codToponomastica")))
					|| (object.getAttribute("indirizzo") == null || "".equals(object.getAttribute("indirizzo")))) {
				recordIndirizzo.setAttribute("flgFuoriComune", false);
			} else {
				recordIndirizzo.setAttribute("flgFuoriComune", true);
				recordIndirizzo.setAttribute("toponimo", object.getAttribute("indirizzo"));
			}
			if (object.getAttribute("codToponomastica") != null && !object.getAttribute("codToponomastica").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("codToponimo", object.getAttribute("codToponomastica"));
			}
			if (object.getAttribute("indirizzo") != null && !object.getAttribute("indirizzo").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("indirizzo", object.getAttribute("indirizzo"));
			}
			if (object.getAttribute("civico") != null && !object.getAttribute("civico").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("civico", object.getAttribute("civico"));
			}
			if (object.getAttribute("localitaFrazione") != null && !object.getAttribute("localitaFrazione").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("frazione", object.getAttribute("localitaFrazione"));
			}
			if (object.getAttribute("cap") != null && !object.getAttribute("cap").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("cap", object.getAttribute("cap"));
			}
			if (object.getAttribute("comune") != null && !object.getAttribute("comune").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("comune", object.getAttribute("comune"));
			}
			if (object.getAttribute("nomeComune") != null && !object.getAttribute("nomeComune").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("nomeComune", object.getAttribute("nomeComune"));
			}
			if (object.getAttribute("codStato") != null && !object.getAttribute("codStato").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("stato", object.getAttribute("codStato"));
			}
			if (object.getAttribute("nomeStato") != null && !object.getAttribute("nomeStato").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("nomeStato", object.getAttribute("nomeStato"));
			}
			if (object.getAttribute("zona") != null && !object.getAttribute("zona").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("zona", object.getAttribute("zona"));
			}
			if (object.getAttribute("complementoIndirizzo") != null && !object.getAttribute("complementoIndirizzo").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("complementoIndirizzo", object.getAttribute("complementoIndirizzo"));
			}
			if (object.getAttribute("tipoToponimo") != null && !object.getAttribute("tipoToponimo").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("tipoToponimo", object.getAttribute("tipoToponimo"));
			}
			if (object.getAttribute("appendici") != null && !object.getAttribute("appendici").equalsIgnoreCase("")) {
				recordIndirizzo.setAttribute("appendici", object.getAttribute("appendici"));
			}
			editRecordIndirizzo(recordIndirizzo);
		}
	}
	
	public void manageOnChangedIndirizzo() {
		
	}
	
	public boolean isClientToponomasticaAttivo() {
		String toponomasticaClientConfig = AurigaLayout.getParametroDB("XML_CONF_WS_TOPONOMATICA");
		return toponomasticaClientConfig != null && !"".equalsIgnoreCase(toponomasticaClientConfig);
	}
	
	public String getParametriClientToponomastica() {
		return AurigaLayout.getParametroDB("XML_CONF_WS_TOPONOMATICA");
	}
	
	protected void selezionaIndirizzo(Record record) {
		indirizzoItem.setValue(record.getAttribute("nomeVia"));
		mIndirizzoDynamicForm1.setValue("codToponimo", record.getAttribute("codiceStrada"));
		mIndirizzoDynamicForm1.setValue("civicoPresenteInVia", false);
		mIndirizzoDynamicForm1.setValue("civico", "");
		mIndirizzoDynamicForm1.setValue("appendici", "");
		mIndirizzoDynamicForm1.markForRedraw();
	}
	
	protected void controllaIndirizzoSuClientToponomastica() {
		String codiceStrada = mIndirizzoDynamicForm1.getValueAsString("codToponimo");
		String numeroCivico = mIndirizzoDynamicForm1.getValueAsString("civico");
		String letteraCivico = mIndirizzoDynamicForm1.getValueAsString("appendici");
		String coloreCivico = "";
		boolean civicoObbligatorioInClientToponomastica = isCivicoObbligatorio();
		
		mIndirizzoDynamicForm1.clearFieldErrors("civico", false);
		
		if (codiceStrada != null && !"".equalsIgnoreCase(codiceStrada) && numeroCivico != null && !"".equalsIgnoreCase(numeroCivico)) {
			Record lRecord = new Record();
			lRecord.setAttribute("codiceStrada", codiceStrada);
			lRecord.setAttribute("numeroCivico", numeroCivico);
			lRecord.setAttribute("letteraCivico", letteraCivico);
			lRecord.setAttribute("coloreCivico", coloreCivico);
		
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaAutoCompletamentoIndirizziDatasource");
			lGwtRestDataSource.executecustom("verificaNumeroCivico", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						if (result.getAttributeAsBoolean("esitoVerifica")) {
							mIndirizzoDynamicForm1.setValue("civicoPresenteInVia", true);
						} else {
							mIndirizzoDynamicForm1.setValue("civicoPresenteInVia", false);
							mIndirizzoDynamicForm1.setFieldErrors("civico", "Valore non valido");
						}
					} else {
						mIndirizzoDynamicForm1.setValue("civicoPresenteInVia", false);
						mIndirizzoDynamicForm1.setFieldErrors("civico", "Valore non valido");
					}
				}
			});
		} else if (numeroCivico == null || "".equalsIgnoreCase(numeroCivico) && (letteraCivico == null || "".equalsIgnoreCase(letteraCivico)) && !civicoObbligatorioInClientToponomastica)  {
			mIndirizzoDynamicForm1.setValue("civicoPresenteInVia", true);
		} else {
			mIndirizzoDynamicForm1.setValue("civicoPresenteInVia", false);
			mIndirizzoDynamicForm1.setFieldErrors("civico", "Valore non valido");
		}
	}
	
	/**
	 * 
	 * Metodo che estrae dai parametri XML il valore del nodo ValoreSemplice legato al nodo con nome specificato da parametro in ingresso
	 * 
	 * Il file XML ha la seguente struttura
	 * 
	 * <?xml version="1.0" encoding="ISO-8859-1"?>
	 * 
	 * <SezioneCache>
	 * 
	 * <Variabile> ... </Variabile>
	 * 
	 * <Variabile>
	 * 
	 * <Nome>nomeVariabile</Nome>
	 * 
	 * <ValoreSemplice>stringa</ValoreSemplice>
	 * 
	 * </Variabile>
	 * 
	 * ...
	 * @param sezioneCache La sezione cache da cui estrarre il valore
	 * @param nomeVariabile Il nome del nodo da cui ricavarne il valore
	 * @return il valore della variabile
	 */
	protected static String getValoreSempliceVariabileSezioneCacheAsString(String sezioneCache, String nomeVariabile) {
		if (sezioneCache == null || "".equalsIgnoreCase(sezioneCache)) {
			return null;
		}
		// Faccio il parsing delle impostazioni
		Document messageDom = XMLParser.parse(sezioneCache);
		// Ottengo la lista dei nodi del documento XML
		NodeList listaNodiNome = messageDom.getElementsByTagName("Nome");
		String returnValue = "false";
		// Scorro la lista dei nodi, per cercare quello con il nome desiderato
		for (int i = 0; i < listaNodiNome.getLength(); i++) {
			Node nodo = listaNodiNome.item(i);
			if (nodo.getFirstChild().getNodeValue().equalsIgnoreCase(nomeVariabile)) {
				// Ho trovato il nodo, vado nel nodo padre per prendere il figlio ValoreSemplice
				Node nodoPadre = nodo.getParentNode();
				// Scorro tutti i figli del padre, per cercare il ValoreSemplice associato
				for (int j = 0; j < nodoPadre.getChildNodes().getLength(); j++) {
					String nodeValue = nodoPadre.getChildNodes().item(j).getNodeName();
					if (nodeValue.equalsIgnoreCase("ValoreSemplice")) {
						// Estraggo il valore del nodo ValoreSemplice
						returnValue = nodoPadre.getChildNodes().item(j).getFirstChild().getNodeValue();
					}
				}
			}
		}
		return returnValue;
	}
	
	/**
	 * 
	 * Metodo che estrae dai parametri XML il valore del nodo ValoreSemplice legato al nodo con nome specificato da parametro in ingresso
	 * 
	 * Il file XML ha la seguente struttura
	 * 
	 * <?xml version="1.0" encoding="ISO-8859-1"?>
	 * 
	 * <SezioneCache>
	 * 
	 * <Variabile> ... </Variabile>
	 * 
	 * <Variabile>
	 * 
	 * <Nome>nomeVariabile</Nome>
	 * 
	 * <ValoreSemplice>booleano</ValoreSemplice>
	 * 
	 * </Variabile>
	 * 
	 * ...
	 * @param sezioneCache La sezione cache da cui estrarre il valore
	 * @param nomeVariabile Il nome del nodo da cui ricavarne il valore
	 * @return il valore booleano della variabile
	 */
	public static boolean getValoreSempliceVariabileSezioneCacheAsBoolean(String sezioneCache, String nomeVariabile) {
		if (sezioneCache == null || "".equalsIgnoreCase(sezioneCache)) {
			return false;
		}
		return Boolean.valueOf(getValoreSempliceVariabileSezioneCacheAsString(sezioneCache, nomeVariabile));
	}

	protected static void setParametriRettangoloFirmaPades(Record recordDaPassare, String firmatario) {
		String parametriRettangoloFirmaJson = AurigaLayout.getParametroDB("POSITION_GRAPHIC_SIGNATURE_IN_PADES");
		if ((parametriRettangoloFirmaJson != null) && (!"".equalsIgnoreCase(parametriRettangoloFirmaJson.trim()))) {
			Record[] recordArray = Record.convertToRecordArray(JSON.decode(parametriRettangoloFirmaJson));
			if ((recordArray != null) && (recordArray.length > 0)) {
				Record parametriRettangoloFirmaPades = recordArray[0];
				parametriRettangoloFirmaPades.setAttribute("firmatario", firmatario);
				recordDaPassare.setAttribute("rettangoloFirmaPades", parametriRettangoloFirmaPades);
			}
		}
	}
	

}
