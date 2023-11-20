/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
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
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class InvioMovimentiContabiliSICRADetail extends CustomDetail {
	
	protected ListaInvioMovimentiContabiliSICRAItem gridItem;
	protected DynamicForm mDynamicForm;
	
	protected SelectItem flgEntrataUscita;
	protected CheckboxItem isAutoIncrementante;
	protected CheckboxItem isCopertoDaFPV;
	protected RadioGroupItem flgPrenotazione;
	protected HiddenItem idImpAcc;
	protected TextItem numeroImpAcc;
	protected TextItem annoImpAcc;
	protected TextItem codiceImpAcc;
	protected SelectItem codCentroCostoFilterXCap;
	protected ExtendedTextItem codiceCapitoloFilterXCap;
	protected TextItem oggetto;
	protected TextAreaItem descrizioneEstesa;
	protected FilteredSelectItem idCapitolo;
	protected HiddenItem numeroCapitolo;
	protected HiddenItem descrizioneCapitolo;
	protected HiddenItem codCentroCostoCapitolo;
	protected TextItem pianoDeiContiFinanz;
//	protected TextItem codiceCapitolo;
	protected TextItem livelliPdC;
	protected TextItem descrizionePianoDeiConti;
	protected SelectItem annoCompetenza;
	protected HiddenItem skipCtrlDisp;
	protected HiddenItem flgCodiceSoggNonTrovato;
	protected NumericItem importoDisponibile;
	protected ExtendedNumericItem importo;	
	protected FormItem codiceCIG;
	protected TextItem codiceCUP;
	protected DateItem dataValuta;
	protected ExtendedNumericItem codiceSoggetto;
	protected RadioGroupItem tipoSoggetto;
	protected CheckboxItem isSoggEstero;
	protected TextItem denominazioneSogg;
	protected TextItem cognomeSogg;
	protected TextItem nomeSogg;
	protected TextItem codFiscaleSoggPF;
	protected TextItem codFiscaleSoggPG;
	protected TextItem codPIVASogg;
	protected TextItem indirizzoSogg;
	protected ImgButtonItem cercaInRubricaButton;
	protected ImgButtonItem sbiancaDatiAnagraficiButton;
	protected TextItem cap;
	protected TextItem localita;
	protected TextItem provincia;
	protected TextAreaItem note;
	
	private boolean flgCampiSettatiDaSicra = false;
	
	protected HashMap<String, String> codCentroCostoValueMap;
	
	public InvioMovimentiContabiliSICRADetail(String nomeEntita, final ListaInvioMovimentiContabiliSICRAItem gridItem, final HashMap<String, String> codCentroCostoValueMap) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		this.codCentroCostoValueMap = codCentroCostoValueMap;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		flgEntrataUscita = new SelectItem("flgEntrataUscita", "Entrata/Uscita");
		flgEntrataUscita.setStartRow(true);
		flgEntrataUscita.setValueMap(buildFlgEntrataUscitaValueMap());
		flgEntrataUscita.setDefaultValue("U");
		flgEntrataUscita.setAllowEmptyValue(false);
		flgEntrataUscita.setWidth(160);
		flgEntrataUscita.setColSpan(1);
		flgEntrataUscita.setRequired(true);
		flgEntrataUscita.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				reloadSelectItem(idCapitolo, false);
				mDynamicForm.redraw();
			}
		});
		
		isAutoIncrementante = new CheckboxItem("isAutoIncrementante", "auto-incrementante");
		isAutoIncrementante.setDefaultValue(false);
		isAutoIncrementante.setColSpan(1);
		isAutoIncrementante.setWidth("*");
		isAutoIncrementante.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString());
			}
		});
		isAutoIncrementante.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});
		
		isCopertoDaFPV = new CheckboxItem("isCopertoDaFPV", "coperto da FPV");
		isCopertoDaFPV.setDefaultValue(false);
		isCopertoDaFPV.setColSpan(1);
		isCopertoDaFPV.setWidth("*");
		isCopertoDaFPV.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(idCapitolo.getValueAsString() != null && !"".equals(idCapitolo.getValueAsString())) {
					reloadSelectItem(annoCompetenza, false);
				}
			}
		});
		isCopertoDaFPV.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		});
		
		flgPrenotazione = new RadioGroupItem("flgPrenotazione", "Prenotazione");
		flgPrenotazione.setStartRow(true);
		flgPrenotazione.setValueMap("SI", "NO");
		flgPrenotazione.setDefaultValue("NO");
		flgPrenotazione.setVertical(false);
		flgPrenotazione.setWrap(false);
		flgPrenotazione.setShowDisabled(false);
		flgPrenotazione.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		});
		flgPrenotazione.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});
		
		idImpAcc = new HiddenItem("idImpAcc");
		
		numeroImpAcc = new TextItem("numeroImpAcc", "Imp./acc. N°");
		numeroImpAcc.setStartRow(true);
		numeroImpAcc.setWidth(160);
		numeroImpAcc.setColSpan(1);
		numeroImpAcc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		annoImpAcc = new TextItem("annoImpAcc", "Anno imp./acc.");
		annoImpAcc.setWidth(160);
		annoImpAcc.setColSpan(1);
		annoImpAcc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
		
		codiceImpAcc = new TextItem("codiceImpAcc", "Cod. imp./acc.");
		codiceImpAcc.setWidth(160);
		codiceImpAcc.setColSpan(1);
		codiceImpAcc.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});
			
		oggetto = new TextItem("oggetto", "Descrizione");
		oggetto.setStartRow(true);
		oggetto.setWidth(630);
		oggetto.setColSpan(14);
		oggetto.setRequired(true);
		oggetto.setLength(100);
		
		descrizioneEstesa = new TextAreaItem("descrizioneEstesa", "Dettaglio descr.");
		descrizioneEstesa.setStartRow(true);
		descrizioneEstesa.setWidth(630);
		descrizioneEstesa.setHeight(40);
		descrizioneEstesa.setColSpan(14);
		descrizioneEstesa.setLength(2000);
		
		codCentroCostoFilterXCap = new SelectItem("codCentroCosto", "Filtra per CdC");
		codCentroCostoFilterXCap.setStartRow(true);
		codCentroCostoFilterXCap.setWidth(160);
		codCentroCostoFilterXCap.setColSpan(1);
		if(codCentroCostoValueMap != null && codCentroCostoValueMap.size() > 0) {
			codCentroCostoFilterXCap.setValueMap(codCentroCostoValueMap);
			if(codCentroCostoValueMap.size() == 1) {
				codCentroCostoFilterXCap.setValue(codCentroCostoValueMap.keySet().toArray(new String[1])[0]);
			}
		}
		codCentroCostoFilterXCap.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				reloadSelectItem(idCapitolo, true);			
			}
		});
		codCentroCostoFilterXCap.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing && (codCentroCostoValueMap != null && codCentroCostoValueMap.size() > 1);
			}
		});			
		
		codiceCapitoloFilterXCap = new ExtendedTextItem("codiceCapitolo", "Cod. capitolo");
		codiceCapitoloFilterXCap.setStartRow(true);
		codiceCapitoloFilterXCap.setWidth(160);
		codiceCapitoloFilterXCap.setColSpan(1);
		codiceCapitoloFilterXCap.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				reloadSelectItem(idCapitolo, true);			
			}
		});		
		
		GWTRestDataSource lSuggerimentiCapitoloDataSource = new GWTRestDataSource("SICRADataSource", "idCapitolo", FieldType.TEXT, true);
		lSuggerimentiCapitoloDataSource.addParam("fieldNameCombo", "idCapitolo");
				
		idCapitolo = new FilteredSelectItem("idCapitolo", "Capitolo") {	
		
			@Override
			public boolean getShowPickListHeader() {
				return false;
			}
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid pickListProperties = super.builPickListProperties();
				pickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {					
						GWTRestDataSource lSuggerimentiCapitoloDataSource = (GWTRestDataSource) idCapitolo.getOptionDataSource();
						lSuggerimentiCapitoloDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());	
						if(codCentroCostoValueMap == null || codCentroCostoValueMap.size() == 0) {
							lSuggerimentiCapitoloDataSource.addParam("codCentroCosto", null);
							lSuggerimentiCapitoloDataSource.addParam("escludiFiltroCdC", "true");
						} else if(codCentroCostoValueMap.size() == 1) {
							lSuggerimentiCapitoloDataSource.addParam("codCentroCosto", codCentroCostoValueMap.keySet().toArray(new String[1])[0]);
							lSuggerimentiCapitoloDataSource.addParam("escludiFiltroCdC", null);
						} else {				
							lSuggerimentiCapitoloDataSource.addParam("codCentroCosto", codCentroCostoFilterXCap.getValueAsString());
							lSuggerimentiCapitoloDataSource.addParam("escludiFiltroCdC", null);
						}
						if(codiceCapitoloFilterXCap.getValueAsString() != null && !"".equals(codiceCapitoloFilterXCap.getValueAsString())) {
							lSuggerimentiCapitoloDataSource.addParam("codiceCapitolo", codiceCapitoloFilterXCap.getValueAsString());
						} else {
							lSuggerimentiCapitoloDataSource.addParam("codiceCapitolo", "");
						}
						idCapitolo.setOptionDataSource(lSuggerimentiCapitoloDataSource);
						idCapitolo.invalidateDisplayValueCache();
					}
				});
				return pickListProperties;
			}
		};
		idCapitolo.setStartRow(true);
		idCapitolo.setWidth(630);
		idCapitolo.setColSpan(14);
		idCapitolo.setPickListWidth(700);
		idCapitolo.setRequired(true);
		idCapitolo.setAutoFetchData(false);
		idCapitolo.setAlwaysFetchMissingValues(true);
		idCapitolo.setFetchDelay(500);
		idCapitolo.setOptionDataSource(lSuggerimentiCapitoloDataSource);
		idCapitolo.setValueField("idCapitolo");
		idCapitolo.setDisplayField("descrizioneCapitolo");
		ListGridField descrizioneCapitoloField = new ListGridField("descrizioneCapitolo");		
		idCapitolo.setPickListFields(descrizioneCapitoloField);
		if(codCentroCostoValueMap == null || codCentroCostoValueMap.size() == 0) {
			idCapitolo.setEmptyPickListMessage("Nessun record trovato o filtri incompleti o poco restrittivi: filtrare per cod. capitolo (almeno 3 caratteri)");
		} else if(codCentroCostoValueMap != null && codCentroCostoValueMap.size() > 1) {
			idCapitolo.setEmptyPickListMessage("Nessun record trovato o filtri incompleti: selezionare un CdC");
		} else {
			idCapitolo.setEmptyPickListMessage("Nessun record trovato");
		}
		idCapitolo.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				mDynamicForm.setValue("numeroCapitolo", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("numeroCapitolo") : "");
				mDynamicForm.setValue("descrizioneCapitolo", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("descrizioneCapitolo") : "");				
				mDynamicForm.setValue("codCentroCostoCapitolo", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("codCentroCosto") : "");				
				mDynamicForm.setValue("pianoDeiContiFinanz", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("pianoDeiContiFinanz") : "");
				mDynamicForm.setValue("codiceCapitolo", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("codiceCapitolo") : "");
				mDynamicForm.setValue("livelliPdC", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("livelliPdC") : "");				
				mDynamicForm.setValue("descrizionePianoDeiConti", idCapitolo.getSelectedRecord() != null ? idCapitolo.getSelectedRecord().getAttribute("descrizionePianoDeiConti") : "");
				reloadSelectItem(annoCompetenza, false);
			}
		});
			
		numeroCapitolo = new HiddenItem("numeroCapitolo");
		
		descrizioneCapitolo = new HiddenItem("descrizioneCapitolo");
		
		codCentroCostoCapitolo = new HiddenItem("codCentroCostoCapitolo");
		
		pianoDeiContiFinanz = new TextItem("pianoDeiContiFinanz", "Piano dei conti finanz.");
		pianoDeiContiFinanz.setStartRow(true);
		pianoDeiContiFinanz.setWidth(160);
		pianoDeiContiFinanz.setColSpan(1);
				
//		codiceCapitolo = new TextItem("codiceCapitolo", "Cod. capitolo");
//		codiceCapitolo.setWidth(160);
//		codiceCapitolo.setColSpan(1);
		
		livelliPdC = new TextItem("livelliPdC", "PdC");
		livelliPdC.setWidth(160);
		livelliPdC.setColSpan(1);
		
		descrizionePianoDeiConti = new TextItem("descrizionePianoDeiConti", "Descrizione piano dei conti");
		descrizionePianoDeiConti.setStartRow(true);
		descrizionePianoDeiConti.setWidth(630);
		descrizionePianoDeiConti.setColSpan(14);
		
		GWTRestDataSource lCompetenzaPluriennaleDataSource = new GWTRestDataSource("SICRADataSource", "annoCompetenza", FieldType.TEXT);
		lCompetenzaPluriennaleDataSource.addParam("fieldNameCombo", "annoCompetenza");
		
		annoCompetenza = new SelectItem("annoCompetenza", "Anno competenza");
		annoCompetenza.setStartRow(true);
		annoCompetenza.setWidth(160);
		annoCompetenza.setPickListWidth(275);		
		annoCompetenza.setColSpan(1);
		annoCompetenza.setRequired(true);
		annoCompetenza.setAutoFetchData(false);
		annoCompetenza.setAlwaysFetchMissingValues(true);
		annoCompetenza.setFetchDelay(500);
		annoCompetenza.setOptionDataSource(lCompetenzaPluriennaleDataSource);
		annoCompetenza.setValueField("annoCompetenza");
		annoCompetenza.setDisplayField("annoCompetenza");
		ListGridField annoCompetenzaField = new ListGridField("annoCompetenza", "Anno competenza");
		annoCompetenzaField.setType(ListGridFieldType.INTEGER);
		annoCompetenzaField.setWidth(125);
		ListGridField importoDisponibileField = new ListGridField("importoDisponibile", "Disponibilità (&euro;)");
		importoDisponibileField.setType(ListGridFieldType.FLOAT);
		importoDisponibileField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importoDisponibile"));				
			}
		});
		annoCompetenza.setPickListFields(annoCompetenzaField, importoDisponibileField);
		ListGrid annoCompetenzaPickListProperties = new ListGrid();
		annoCompetenzaPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
//		annoCompetenzaPickListProperties.setShowHeader(false);
		annoCompetenzaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {					
				GWTRestDataSource lCompetenzaPluriennaleDataSource = (GWTRestDataSource) annoCompetenza.getOptionDataSource();
				lCompetenzaPluriennaleDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());
				if(flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString()) && isCopertoDaFPV.getValueAsBoolean() != null && isCopertoDaFPV.getValueAsBoolean()) {
					lCompetenzaPluriennaleDataSource.addParam("isCopertoDaFPV", "true");
				} else {
					lCompetenzaPluriennaleDataSource.addParam("isCopertoDaFPV", "");
				}
				lCompetenzaPluriennaleDataSource.addParam("idCapitolo", idCapitolo.getValueAsString());
				lCompetenzaPluriennaleDataSource.addParam("numeroCapitolo", (String) numeroCapitolo.getValue());
				if(codCentroCostoValueMap == null || codCentroCostoValueMap.size() == 0) {
					lCompetenzaPluriennaleDataSource.addParam("codCentroCosto", null);
					lCompetenzaPluriennaleDataSource.addParam("escludiFiltroCdC", "true");
				} else if(codCentroCostoValueMap.size() == 1) {
					lCompetenzaPluriennaleDataSource.addParam("codCentroCosto", codCentroCostoValueMap.keySet().toArray(new String[1])[0]);
					lCompetenzaPluriennaleDataSource.addParam("escludiFiltroCdC", null);
				} else {				
					lCompetenzaPluriennaleDataSource.addParam("codCentroCosto", codCentroCostoFilterXCap.getValueAsString());
					lCompetenzaPluriennaleDataSource.addParam("escludiFiltroCdC", null);
				}
				if(codiceCapitoloFilterXCap.getValueAsString() != null && !"".equals(codiceCapitoloFilterXCap.getValueAsString())) {
					lCompetenzaPluriennaleDataSource.addParam("codiceCapitolo", codiceCapitoloFilterXCap.getValueAsString());
				} else {
					lCompetenzaPluriennaleDataSource.addParam("codiceCapitolo", "");
				}
				annoCompetenza.setOptionDataSource(lCompetenzaPluriennaleDataSource);
				annoCompetenza.invalidateDisplayValueCache();
			}
		});
		annoCompetenzaPickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
				mDynamicForm.setValue("skipCtrlDisp", event.getRecord().getAttributeAsBoolean("skipCtrlDisp"));
				mDynamicForm.setValue("importoDisponibile", event.getRecord().getAttribute("importoDisponibile"));
			}
		});
		annoCompetenza.setPickListProperties(annoCompetenzaPickListProperties);
		annoCompetenza.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		skipCtrlDisp = new HiddenItem("skipCtrlDisp");
		skipCtrlDisp.setDefaultValue(false);
		
		flgCodiceSoggNonTrovato = new HiddenItem("flgCodiceSoggNonTrovato");
		flgCodiceSoggNonTrovato.setDefaultValue(false);
		
		importoDisponibile = new ExtendedNumericItem("importoDisponibile", "Disponibilità (&euro;)");
		importoDisponibile.setKeyPressFilter("[0-9.,]");
		importoDisponibile.setWidth(160);
		importoDisponibile.setColSpan(1);
		importoDisponibile.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importoDisponibile.setValue(NumberFormatUtility.getFormattedValue(importoDisponibile.getValueAsString()));				
				return true;
			}
		});

		importo = new ExtendedNumericItem("importo", "Importo (&euro;)");
		importo.setKeyPressFilter("[0-9.,]");
		importo.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importo.setValue(NumberFormatUtility.getFormattedValue(importo.getValueAsString()));
				if(annoCompetenza.getValueAsString() == null || "".equals(annoCompetenza.getValueAsString())) {
					importo.setCanEdit(false);
				} else {
					importo.setCanEdit(editing);
				}
				return true;
			}
		});
		importo.addChangedBlurHandler(new ChangedHandler() {
				
			@Override
			public void onChanged(ChangedEvent event) {				
				importo.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});		
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		CustomValidator importoSuperioreDisponibilitaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {					
				if(flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString())) {
					boolean isAnnoWithSkipCtrlDisp = skipCtrlDisp.getValue() != null && (Boolean) skipCtrlDisp.getValue();
					if(!isAnnoWithSkipCtrlDisp && !isSkipControlloImportoSuperioreDisponibilita()) {
						// il controllo va' fatto solo per l'anno corrente e se sono in USCITA
						String pattern = "#,##0.00";
						double importo = 0;
						if(value != null && !"".equals(value)) {
							importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
						}
						double disponibilita = 0;
						if(importoDisponibile.getValueAsString() != null && !"".equals(importoDisponibile.getValueAsString())) {
							disponibilita = new Double(NumberFormat.getFormat(pattern).parse(importoDisponibile.getValueAsString())).doubleValue();
						}
						return importo <= disponibilita;
					}
				}
				return true;
			}
		};
		importoSuperioreDisponibilitaValidator.setErrorMessage("Valore non valido: il valore impegnato non deve superare la disponibilità");
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(!isSkipControlloImportoMaggioreDiZeroValidator()) {
					String pattern = "#,##0.00";
					double importo = 0;
					if(value != null && !"".equals(value)) {
						importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
					}
					return importo > 0;
				}
				return true;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		importo.setValidators(importoPrecisionValidator, importoMaggioreDiZeroValidator, importoSuperioreDisponibilitaValidator);
		
		importo.setWidth(160);
		importo.setColSpan(1);
		importo.setAttribute("obbligatorio", true);
//		importo.setRequired(true);
		
		if(!gridItem.isEsclusoCIGProposta()) {
			String[] lCIGValueMap = gridItem.getCIGValueMap();
			if(lCIGValueMap != null && lCIGValueMap.length > 0) {	
				
				codiceCIG = new SelectItem("codiceCIG", "CIG");
				codiceCIG.setStartRow(true);
				codiceCIG.setWidth(160);
				codiceCIG.setColSpan(1);
				if(lCIGValueMap.length == 1) {
					((SelectItem) codiceCIG).setDefaultValue(lCIGValueMap[0]);
				}
				codiceCIG.setValueMap(lCIGValueMap);
				((SelectItem)codiceCIG).setAllowEmptyValue(true);
	//			codiceCIG.setRequired(true);			
			} else {
				
				CustomValidator codiceCIGLengthValidator = new CustomValidator() {
	
					@Override
					protected boolean condition(Object value) {
						if (value != null && !"".equals((String) value)) {
							String valore = (String) value;
							return valore.length() == 10;
						}
						return true;
					}
				};
				codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");
				
				codiceCIG = new TextItem("codiceCIG", "CIG");
				codiceCIG.setStartRow(true);
				codiceCIG.setWidth(160);
				codiceCIG.setColSpan(1);
				((TextItem) codiceCIG).setLength(10);
				codiceCIG.setInputTransformer(new FormItemInputTransformer() {
					
					@Override
					public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
						return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
					}
				});
				codiceCIG.setValidators(codiceCIGLengthValidator);			
			}
		}
		
		CustomValidator codiceCUPLengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals((String) value)) {
					String valore = (String) value;
					return valore.length() == 15;
				}
				return true;
			}
		};
		codiceCUPLengthValidator.setErrorMessage("Il codice CUP, se indicato, deve essere di 15 caratteri");
		
		codiceCUP = new TextItem("codiceCUP", "CUP");
		codiceCUP.setStartRow(true);
		codiceCUP.setWidth(160);
		codiceCUP.setColSpan(1);
		codiceCUP.setLength(15);
		codiceCUP.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCUP.setValidators(codiceCUPLengthValidator);
		
		dataValuta = new DateItem("dataValuta", "Data valuta");
		dataValuta.setStartRow(true);
		dataValuta.setWidth(160);
		dataValuta.setColSpan(1);
		dataValuta.setRequired(true);
		dataValuta.setDefaultValue(new Date());
		dataValuta.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		});
		
		codiceSoggetto = new ExtendedNumericItem("codiceSoggetto", "Cod. soggetto", false);
		codiceSoggetto.setStartRow(true);
		codiceSoggetto.setWidth(160);
		codiceSoggetto.setColSpan(1);
		codiceSoggetto.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				if(codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString())) {
					final Record lRecord = new Record();
					lRecord.setAttribute("codiceSoggetto", codiceSoggetto.getValueAsString());
					if(!AurigaLayout.getParametroDBAsBoolean("DISABILITA_RICERCA_ANAGRAFICA_SICRA")) {
						cercaInRubricaAnagraficaSicra(lRecord, "ricercaAutomatica");
						mDynamicForm.markForRedraw();
					}					
				}
			}
		});		
		
		sbiancaDatiAnagraficiButton = new ImgButtonItem("sbiancaDatiAnagraficiButton", "buttons/clear.png", "Sbianca dati anagrafici");
		sbiancaDatiAnagraficiButton.setColSpan(1);
		sbiancaDatiAnagraficiButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgCampiSettatiDaSicra;
			}
		});
		sbiancaDatiAnagraficiButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				sbiancaCampiAnagrafici(false);
				flgCampiSettatiDaSicra = false;
			}
		});
		
		tipoSoggetto = new RadioGroupItem("tipoSoggetto", "Persona");
		tipoSoggetto.setStartRow(true);
		tipoSoggetto.setValueMap("fisica", "giuridica");
		tipoSoggetto.setVertical(false);
		tipoSoggetto.setWrap(false);
		tipoSoggetto.setShowDisabled(false);
//		tipoSoggetto.setRequired(true);		
		tipoSoggetto.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isSoggettoObbligatorio()) {
					tipoSoggetto.setAttribute("obbligatorio", true);
					tipoSoggetto.setTitle(FrontendUtil.getRequiredFormItemTitle("Persona"));
				} else {
					tipoSoggetto.setAttribute("obbligatorio", false);
					tipoSoggetto.setTitle("Persona");
				}
				return true;
			}
		});
		tipoSoggetto.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isSoggettoObbligatorio();
			}
		}));		
		tipoSoggetto.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				sbiancaCampiAnagrafici(true);
				mDynamicForm.markForRedraw();
			}
		});
		
		isSoggEstero = new CheckboxItem("isSoggEstero", "estero");
		isSoggEstero.setDefaultValue(false);
		isSoggEstero.setColSpan(1);
		isSoggEstero.setWidth("*");
		isSoggEstero.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		denominazioneSogg = new TextItem("denominazioneSogg", "Denominazione");
		denominazioneSogg.setStartRow(true);
		denominazioneSogg.setWidth(315);
		denominazioneSogg.setColSpan(3);
		denominazioneSogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isCodiceSoggettoValorizzato = codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString().trim());
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				if(isSoggettoObbligatorio() && !isCodiceSoggettoValorizzato) {
					denominazioneSogg.setAttribute("obbligatorio", true);
					denominazioneSogg.setTitle(FrontendUtil.getRequiredFormItemTitle("Denominazione"));
				} else {
					denominazioneSogg.setAttribute("obbligatorio", false);
					denominazioneSogg.setTitle("Denominazione");
				}
				return isPersonaGiuridica;
			}
		});
		denominazioneSogg.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isCodiceSoggettoValorizzato = codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString().trim());
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				return isSoggettoObbligatorio() && !isCodiceSoggettoValorizzato && isPersonaGiuridica;
			}
		}));
		
		cognomeSogg = new TextItem("cognomeSogg", "Cognome");
		cognomeSogg.setStartRow(true);
		cognomeSogg.setWidth(160);
		cognomeSogg.setColSpan(1);
		cognomeSogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isCodiceSoggettoValorizzato = codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString().trim());
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				if(isSoggettoObbligatorio() && !isCodiceSoggettoValorizzato) {
					cognomeSogg.setAttribute("obbligatorio", true);
					cognomeSogg.setTitle(FrontendUtil.getRequiredFormItemTitle("Cognome"));
				} else {
					cognomeSogg.setAttribute("obbligatorio", false);
					cognomeSogg.setTitle("Cognome");
				}
				
				return isPersonaFisica;
			}
		});
		cognomeSogg.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isCodiceSoggettoValorizzato = codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString().trim());
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				return isSoggettoObbligatorio() && !isCodiceSoggettoValorizzato && isPersonaFisica;
			}
		}));
		
		nomeSogg = new TextItem("nomeSogg", "Nome");
		nomeSogg.setWidth(160);
		nomeSogg.setColSpan(1);
		nomeSogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isCodiceSoggettoValorizzato = codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString().trim());
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				if(isSoggettoObbligatorio() && !isCodiceSoggettoValorizzato) {
					nomeSogg.setAttribute("obbligatorio", true);
					nomeSogg.setTitle(FrontendUtil.getRequiredFormItemTitle("Nome"));
				} else {
					nomeSogg.setAttribute("obbligatorio", false);
					nomeSogg.setTitle("Nome");
				}
				return isPersonaFisica;
			}
		});
		nomeSogg.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isCodiceSoggettoValorizzato = codiceSoggetto.getValueAsString() != null && !"".equals(codiceSoggetto.getValueAsString().trim());
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				return isSoggettoObbligatorio() && !isCodiceSoggettoValorizzato && isPersonaFisica;
			}
		}));
		
		codFiscaleSoggPF = new TextItem("codFiscaleSoggPF", "C.F.");
		codFiscaleSoggPF.setStartRow(true);
		codFiscaleSoggPF.setWidth(160);
		codFiscaleSoggPF.setColSpan(1);
		codFiscaleSoggPF.setLength(16);
		codFiscaleSoggPF.setCharacterCasing(CharacterCasing.UPPER);
		codFiscaleSoggPF.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean isSoggettoEstero = isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean();
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				return isSoggettoObbligatorio() && !isSoggettoEstero && isPersonaFisica;
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());					
				if (value != null && !"".equals(value) && isPersonaFisica) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);					
				}
				return true;
			}
		});		
		codFiscaleSoggPF.setShowIfCondition(new FormItemIfFunction() {
					
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isSoggettoEstero = isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean();
				boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());					
				if(isSoggettoObbligatorio() && !isSoggettoEstero) {
					codFiscaleSoggPF.setAttribute("obbligatorio", true);
					codFiscaleSoggPF.setTitle(FrontendUtil.getRequiredFormItemTitle("C.F."));
				} else {
					codFiscaleSoggPF.setAttribute("obbligatorio", false);
					codFiscaleSoggPF.setTitle("C.F.");
				}
				return isPersonaFisica;
			}
		});
		
		CustomValidator codFiscalePIVAObbligatoriValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				boolean isSoggettoEstero = isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean();
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				if(isSoggettoObbligatorio() && !isSoggettoEstero && isPersonaGiuridica) {
					boolean isCodFiscaleValorizzato = codFiscaleSoggPG.getValueAsString() != null && !"".equals(codFiscaleSoggPG.getValueAsString().trim());
					boolean isCodPIVAValorizzato = codPIVASogg.getValueAsString() != null && !"".equals(codPIVASogg.getValueAsString().trim());
					if(!isCodFiscaleValorizzato && !isCodPIVAValorizzato) {
						return false;
					}
				}
				return true;
			}
		};
		codFiscalePIVAObbligatoriValidator.setErrorMessage("Almeno uno tra C.F. e P.IVA deve essere valorizzato");
		
		codFiscaleSoggPG = new TextItem("codFiscaleSoggPG", "C.F.");
		codFiscaleSoggPG.setStartRow(true);
		codFiscaleSoggPG.setWidth(160);
		codFiscaleSoggPG.setColSpan(1);
		codFiscaleSoggPG.setCharacterCasing(CharacterCasing.UPPER);
		codFiscaleSoggPG.setValidators(codFiscalePIVAObbligatoriValidator, new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());					
				if (value != null && !"".equals(value) && isPersonaGiuridica) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					return regExp.test((String) value);					
				}
				return true;
			}
		});		
		codFiscaleSoggPG.setShowIfCondition(new FormItemIfFunction() {
					
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isSoggettoEstero = isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean();
				if (isSoggettoEstero) {
					codFiscaleSoggPG.setLength(28);
				} else {
					codFiscaleSoggPG.setLength(16);
				}
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());					
				if(isSoggettoObbligatorio() && !isSoggettoEstero) {
					codFiscaleSoggPG.setAttribute("obbligatorio", true);
					codFiscaleSoggPG.setTitle(FrontendUtil.getRequiredFormItemTitle("C.F."));
				} else {
					codFiscaleSoggPG.setAttribute("obbligatorio", false);
					codFiscaleSoggPG.setTitle("C.F.");
				}
				return isPersonaGiuridica;
			}
		});
		
		codPIVASogg = new TextItem("codPIVASogg", "P.IVA");
		codPIVASogg.setWidth(160);
		codPIVASogg.setColSpan(1);
		codPIVASogg.setLength(11);
		codPIVASogg.setCharacterCasing(CharacterCasing.UPPER);
		codPIVASogg.setValidators(codFiscalePIVAObbligatoriValidator, new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				boolean isSoggettoEstero = isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean();
				if (value != null && !"".equals(value) && !isSoggettoEstero) {
					RegExp regExp = RegExp.compile(RegExpUtility.partitaIvaRegExp());
					return regExp.test((String) value);					
				}
				return true;
			}
		});		
		codPIVASogg.setShowIfCondition(new FormItemIfFunction() {
					
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isSoggettoEstero = isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean();
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				if(isSoggettoObbligatorio() && !isSoggettoEstero && isPersonaGiuridica) {
					codPIVASogg.setAttribute("obbligatorio", true);
					codPIVASogg.setTitle(FrontendUtil.getRequiredFormItemTitle("P.IVA"));
				} else {
					codPIVASogg.setAttribute("obbligatorio", false);
					codPIVASogg.setTitle("P.IVA");
				}
				return isPersonaGiuridica;
			}
		});
		
		cercaInRubricaButton = new ImgButtonItem("cercaInRubricaButton", "lookup/rubricasearch.png", "Cerca soggetto in anagrafe fornitori");
		cercaInRubricaButton.setColSpan(1);
		cercaInRubricaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String tipoSoggettoValue = tipoSoggetto.getValueAsString();
				return (tipoSoggettoValue!= null && !"".equals(tipoSoggettoValue) && codiceSoggetto.getCanEdit()) && !AurigaLayout.getParametroDBAsBoolean("DISABILITA_RICERCA_ANAGRAFICA_SICRA");
				}
		});
		cercaInRubricaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {			
				final Record lRecord = new Record();
				boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
				if(isPersonaGiuridica) {
					if(!(denominazioneSogg.getValue()!= null && !"".equalsIgnoreCase(denominazioneSogg.getValueAsString())) &&
					   !(codFiscaleSoggPG.getValue()!= null && !"".equalsIgnoreCase(codFiscaleSoggPG.getValueAsString())) &&
					   !(codPIVASogg.getValue()!= null && !"".equalsIgnoreCase(codPIVASogg.getValueAsString()))) {
						
						SC.say("Bisogna valorizzare almeno un campo tra: Denominazione, C.F., P.IVA");
						
						return;
					}
					lRecord.setAttribute("denominazione", denominazioneSogg.getValueAsString()!=null ? denominazioneSogg.getValueAsString() : null);
					lRecord.setAttribute("partitaIva", codPIVASogg.getValueAsString()!=null ? codPIVASogg.getValueAsString() : null);		
					lRecord.setAttribute("codiceFiscale", codFiscaleSoggPG.getValueAsString()!=null ? codFiscaleSoggPG.getValueAsString() : null);
				}else {
					if(!(cognomeSogg.getValue()!= null && !"".equalsIgnoreCase(cognomeSogg.getValueAsString())) &&
					   !(codFiscaleSoggPF.getValue()!= null && !"".equalsIgnoreCase(codFiscaleSoggPF.getValueAsString()))){
							
						SC.say("Bisogna valorizzare almeno un campo tra: Cognome, C.F.");
								
						return;
					}					
					
					lRecord.setAttribute("codiceFiscale", codFiscaleSoggPF.getValueAsString()!=null ? codFiscaleSoggPF.getValueAsString() : null);
					lRecord.setAttribute("nome", nomeSogg.getValueAsString()!=null ? nomeSogg.getValueAsString() : null);
					lRecord.setAttribute("cognome", cognomeSogg.getValueAsString()!=null ? cognomeSogg.getValueAsString() : null);
				}		
								
				cercaInRubricaAnagraficaSicra(lRecord, "ricercaOnDemand");
				mDynamicForm.markForRedraw();
			}
		});
		
		indirizzoSogg = new TextItem("indirizzoSogg", "Indirizzo");
		indirizzoSogg.setStartRow(true);
		indirizzoSogg.setWidth(630);
		indirizzoSogg.setColSpan(14);
		
		cap = new TextItem("cap", "CAP");
		cap.setLength(5);
		cap.setWidth(80);
		cap.setColSpan(1);
		
		localita = new TextItem("localita", "Località");
		localita.setStartRow(true);
		localita.setWidth(630);
		localita.setColSpan(14);
		
		provincia = new TextItem("provincia", "Prov.");
		provincia.setLength(2);
		provincia.setWidth(80);
		provincia.setColSpan(1);	
				
		note = new TextAreaItem("note", "Note");
		note.setStartRow(true);
		note.setWidth(630);
		note.setHeight(40);
		note.setColSpan(14);
		
		List<FormItem> items = new ArrayList<FormItem>();		
		items.add(flgEntrataUscita); items.add(isAutoIncrementante); items.add(isCopertoDaFPV); items.add(flgPrenotazione);		
		items.add(idImpAcc); items.add(numeroImpAcc); items.add(annoImpAcc); items.add(codiceImpAcc);
		items.add(oggetto);
		items.add(descrizioneEstesa);		
		items.add(codCentroCostoFilterXCap);		
		items.add(codiceCapitoloFilterXCap);		
		items.add(idCapitolo); items.add(numeroCapitolo); items.add(descrizioneCapitolo); items.add(codCentroCostoCapitolo);		
		items.add(pianoDeiContiFinanz); /*items.add(codiceCapitolo);*/ items.add(livelliPdC); items.add(descrizionePianoDeiConti);		
		items.add(annoCompetenza); items.add(skipCtrlDisp); items.add(importoDisponibile); items.add(importo);			
		if(!gridItem.isEsclusoCIGProposta()) {
			items.add(codiceCIG);
		}		
		items.add(codiceCUP);		
		items.add(dataValuta);		
		items.add(codiceSoggetto); items.add(flgCodiceSoggNonTrovato);
		items.add(sbiancaDatiAnagraficiButton);		
		items.add(tipoSoggetto); items.add(isSoggEstero);		
		items.add(denominazioneSogg); items.add(cognomeSogg); items.add(nomeSogg);		
		items.add(codFiscaleSoggPF); items.add(codFiscaleSoggPG); items.add(codPIVASogg);		
		items.add(cercaInRubricaButton);		
		items.add(indirizzoSogg);		
		items.add(cap);		
		items.add(localita);		
		items.add(provincia);		
		items.add(note);		
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));			
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
	
	protected void sbiancaCampiAnagrafici(boolean changeTipoSoggetto) {
		denominazioneSogg.setCanEdit(true);
		denominazioneSogg.setValue("");
		
		codFiscaleSoggPG.setCanEdit(true);
		codFiscaleSoggPG.setValue("");
		
		nomeSogg.setCanEdit(true);
		nomeSogg.setValue("");
		
		cognomeSogg.setCanEdit(true);
		cognomeSogg.setValue("");
		
		codFiscaleSoggPF.setCanEdit(true);
		codFiscaleSoggPF.setValue("");
		
		codPIVASogg.setCanEdit(true);
		codPIVASogg.setValue("");
		
		indirizzoSogg.setCanEdit(true);
		indirizzoSogg.setValue("");
		
		cap.setCanEdit(true);
		cap.setValue("");
		
		provincia.setCanEdit(true);
		provincia.setValue("");
		
		localita.setCanEdit(true);
		localita.setValue("");
		
		isSoggEstero.setCanEdit(true);
		isSoggEstero.setValue("");
		
		codiceSoggetto.setCanEdit(true);
		codiceSoggetto.setValue("");
		
		if(!changeTipoSoggetto) {
			tipoSoggetto.setCanEdit(true);
			tipoSoggetto.setValue("");
		}

	}

	protected void cercaInRubricaAnagraficaSicra(final Record lRecord, final String tipoRicerca) {
//		Timer t1 = new Timer() {
//			public void run() {
				final GWTRestDataSource lSICRADataSource = new GWTRestDataSource("SICRADataSource");
				lSICRADataSource.extraparam.put("tipoRicerca", tipoRicerca);
				lSICRADataSource.executecustom("cercaInRubricaAnagrafica", lRecord, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record lRecord = response.getData()[0];
							if(lRecord!=null) {
								RecordList listaAnagrafica = lRecord.getAttributeAsRecordList("listaAnagraficaSicra");
								
								/*Ricerca scatenata dal cambio focus sul campo codiceSoggetto*/
								if("ricercaAutomatica".equalsIgnoreCase(tipoRicerca)){
									if(listaAnagrafica!=null && listaAnagrafica.getLength()==1) {
										Record anagraficaTrovataSicraBean = listaAnagrafica.get(0);
										settaCampiConDatiSicra(anagraficaTrovataSicraBean);
									}else {
										/*Setto questo flag hiddenItem che verrà valutato alla pressione del tasto ok e sbiancherà il campo codiceSoggetto*/
										mDynamicForm.setValue("flgCodiceSoggNonTrovato", true);
									}
								/*Ricerca scatenata ala pressione del tasto di ricerca in rubrica*/
								}else if("ricercaOnDemand".equalsIgnoreCase(tipoRicerca)){
									/*Se trova un solo risultato lo setto nei campi*/
									if(listaAnagrafica!=null && listaAnagrafica.getLength() == 1) {
										Record anagraficaTrovataSicraBean = listaAnagrafica.get(0);
										settaCampiConDatiSicra(anagraficaTrovataSicraBean);
										
									/*Se trova piu risultati apro popup di selezione*/
									}else if(listaAnagrafica!=null && listaAnagrafica.getLength() > 1) {	
											// per ogni voce dell'anagrafica compongo la descrizione da visualizzare
											for(int i = 0; i < listaAnagrafica.getLength(); i++) {
												Record anagraficaTrovataSicraBean = listaAnagrafica.get(i);
												String descrizione = "";
												if (anagraficaTrovataSicraBean.getAttributeAsString("denominazione") != null && !"".equalsIgnoreCase(anagraficaTrovataSicraBean.getAttributeAsString("denominazione"))) {
													descrizione = anagraficaTrovataSicraBean.getAttributeAsString("denominazione");
												} else if ((anagraficaTrovataSicraBean.getAttributeAsString("nome") != null && !"".equalsIgnoreCase(anagraficaTrovataSicraBean.getAttributeAsString("nome"))) ||
														(anagraficaTrovataSicraBean.getAttributeAsString("cognome") != null && !"".equalsIgnoreCase(anagraficaTrovataSicraBean.getAttributeAsString("cognome")))) {
													String cognome = anagraficaTrovataSicraBean.getAttributeAsString("cognome") != null ? anagraficaTrovataSicraBean.getAttributeAsString("cognome") : "";
													String nome = anagraficaTrovataSicraBean.getAttributeAsString("nome") != null ? anagraficaTrovataSicraBean.getAttributeAsString("nome") : "";
													descrizione = cognome + (cognome.length() >= 1 ? " " : "") + nome;
												}
												anagraficaTrovataSicraBean.setAttribute("ragSocialeCognomeNome", descrizione);	
											}
											
											ListaAnagraficaSicraPopup listaAnagraficaSicraPopup = new ListaAnagraficaSicraPopup() {


												@Override
												public void manageRecordSelection(Record record) {
													settaCampiConDatiSicra(record);
												}
											};  
											Record recordToPass = new Record();
											recordToPass.setAttribute("listaAnagraficaSicra", listaAnagrafica);
											listaAnagraficaSicraPopup.initContent(recordToPass);
											listaAnagraficaSicraPopup.show();									
									}
									
									/*Non trova nessun risultato*/
									else {
										SC.say("Soggetto non presente o non individuabile nell'anagrafe fornitori del sistema contabile");
										return;
									}
								}							
							}
						} 												
					}
				});					
//			}
//		};
//		String delay = AurigaLayout.getParametroDB("CERCA_IN_RUBRICA_DELAY");
//		t1.schedule((delay != null && !"".equals(delay)) ? Integer.parseInt(delay) : 1000);		
	}

		/**
		 * @param anagraficaTrovataSicraBean
		 */
		public void settaCampiConDatiSicra(Record anagraficaTrovataSicraBean) {
			if(/*(tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString()))
					|| */ (anagraficaTrovataSicraBean.getAttribute("codPIVASogg")!=null && !"".equalsIgnoreCase(anagraficaTrovataSicraBean.getAttribute("codPIVASogg").trim()))
					|| (anagraficaTrovataSicraBean.getAttribute("denominazione")!=null && !"".equalsIgnoreCase(anagraficaTrovataSicraBean.getAttribute("denominazione").trim()) 
					&& (anagraficaTrovataSicraBean.getAttribute("cognome")==null || "".equalsIgnoreCase(anagraficaTrovataSicraBean.getAttribute("cognome"))))) {
				denominazioneSogg.setValue(anagraficaTrovataSicraBean.getAttribute("denominazione")!=null ? anagraficaTrovataSicraBean.getAttribute("denominazione"): "");
				denominazioneSogg.setCanEdit(false);
				denominazioneSogg.setVisible(true);
				codFiscaleSoggPG.setValue(anagraficaTrovataSicraBean.getAttribute("codiceFiscale")!=null ? anagraficaTrovataSicraBean.getAttribute("codiceFiscale"): "");
				codFiscaleSoggPG.setCanEdit(false);
				codFiscaleSoggPG.setVisible(true);
				tipoSoggetto.setValue("giuridica");
				
				codPIVASogg.setValue(anagraficaTrovataSicraBean.getAttribute("partitaIva")!=null ? anagraficaTrovataSicraBean.getAttribute("partitaIva"): "");
				codPIVASogg.setCanEdit(false);
			}else{
				nomeSogg.setValue(anagraficaTrovataSicraBean.getAttribute("nome")!=null ? anagraficaTrovataSicraBean.getAttribute("nome"): "");
				nomeSogg.setCanEdit(false);
				nomeSogg.setVisible(true);
				cognomeSogg.setValue(anagraficaTrovataSicraBean.getAttribute("cognome")!=null ? anagraficaTrovataSicraBean.getAttribute("cognome"): "");
				cognomeSogg.setCanEdit(false);
				cognomeSogg.setVisible(true);
				codFiscaleSoggPF.setValue(anagraficaTrovataSicraBean.getAttribute("codiceFiscale")!=null ? anagraficaTrovataSicraBean.getAttribute("codiceFiscale"): "");
				codFiscaleSoggPF.setCanEdit(false);
				codFiscaleSoggPF.setVisible(true);
				tipoSoggetto.setValue("fisica");
			}
			
			codiceSoggetto.setValue(anagraficaTrovataSicraBean.getAttribute("codiceSoggetto")!=null ? anagraficaTrovataSicraBean.getAttribute("codiceSoggetto"): "");
			codiceSoggetto.setCanEdit(false);
			
			indirizzoSogg.setValue(anagraficaTrovataSicraBean.getAttribute("indirizzo")!=null ? anagraficaTrovataSicraBean.getAttribute("indirizzo"): "");
			indirizzoSogg.setCanEdit(false);
			
			cap.setValue(anagraficaTrovataSicraBean.getAttribute("cap")!=null ? anagraficaTrovataSicraBean.getAttribute("cap"): "");
			cap.setCanEdit(false);
			
			provincia.setValue(anagraficaTrovataSicraBean.getAttribute("provincia")!=null ? anagraficaTrovataSicraBean.getAttribute("provincia"): "");
			provincia.setCanEdit(false);
			
			localita.setValue(anagraficaTrovataSicraBean.getAttribute("localita")!=null ? anagraficaTrovataSicraBean.getAttribute("localita"): "");
			localita.setCanEdit(false);
			
			tipoSoggetto.setCanEdit(false);
//			isSoggEstero.setCanEdit(false);
			
			flgCampiSettatiDaSicra = true;
			
		}
		
		
	public boolean isImpegnoPrenotazione() {
		boolean isImpegnoPrenotazione = false;
		if(flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString())) {
			isImpegnoPrenotazione = flgPrenotazione.getValueAsString() != null && "SI".equals(flgPrenotazione.getValueAsString());
		}
		return isImpegnoPrenotazione;
	}
	
	public boolean isAccertamentoAutoIncrementante() {
		boolean isAccertamentoAutoIncrementante = false;
		if(flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString())) {
			isAccertamentoAutoIncrementante = isAutoIncrementante.getValueAsBoolean() != null && isAutoIncrementante.getValueAsBoolean();
		}
		return isAccertamentoAutoIncrementante;
	}
	
	public boolean isSoggettoObbligatorio() {		
		return !isImpegnoPrenotazione() && !isAccertamentoAutoIncrementante();
	}
	
	public HashSet<String> getVociPEGNoVerifDisp() {
		return gridItem != null && gridItem.getVociPEGNoVerifDisp() != null ? gridItem.getVociPEGNoVerifDisp() : new HashSet<String>();
	}
	
	public boolean isSkipControlloImportoMaggioreDiZeroValidator() {
		return isAccertamentoAutoIncrementante();
	}
	
	public boolean isSkipControlloImportoSuperioreDisponibilita() {
		boolean skipControlloImportoSuperioreDisponibilita = false;
		String liv1PdC = livelliPdC.getValueAsString() != null ? livelliPdC.getValueAsString().substring(0, livelliPdC.getValueAsString().indexOf(".")) : null;
		if(liv1PdC != null && !"".equals(liv1PdC) && getVociPEGNoVerifDisp().contains(liv1PdC)) {
			skipControlloImportoSuperioreDisponibilita = true;
		}
		return skipControlloImportoSuperioreDisponibilita;
	}
	
	public void controlloWarningImportoSuperioreDisponibilita(final BooleanCallback callback) {	
		boolean isAnnoWithSkipCtrlDisp = skipCtrlDisp.getValue() != null && (Boolean) skipCtrlDisp.getValue();		
		if(!isAnnoWithSkipCtrlDisp && !isSkipControlloImportoSuperioreDisponibilita()) {
			String value = importo.getValueAsString();
			String pattern = "#,##0.00";
			double importo = 0;
			if(value != null && !"".equals(value)) {
				importo = new Double(NumberFormat.getFormat(pattern).parse(value)).doubleValue();			
			}
			double disponibilita = 0;
			if(importoDisponibile.getValueAsString() != null && !"".equals(importoDisponibile.getValueAsString())) {
				disponibilita = new Double(NumberFormat.getFormat(pattern).parse(importoDisponibile.getValueAsString())).doubleValue();
			}
			if(importo > disponibilita) {
				AurigaLayout.showConfirmDialogWithWarning("Attenzione!", "L'importo inserito supera la disponibilità. Procedere comunque?", "Si", "No", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (callback != null) {
							callback.execute(value);
						}
					}
				});
			} else {
				if (callback != null) {
					callback.execute(true);
				}
			}
		} else {
			if (callback != null) {
				callback.execute(true);
			}
		}
	}
	
	public static LinkedHashMap<String, String> buildFlgEntrataUscitaValueMap() {
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", "Entrata");
		flgEntrataUscitaValueMap.put("U", "Uscita");		
		return flgEntrataUscitaValueMap;
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		numeroImpAcc.setCanEdit(false);
		annoImpAcc.setCanEdit(false);
		codiceImpAcc.setCanEdit(false);
		descrizioneCapitolo.setCanEdit(false);
		pianoDeiContiFinanz.setCanEdit(false);
//		codiceCapitolo.setCanEdit(false);
		livelliPdC.setCanEdit(false);
		descrizionePianoDeiConti.setCanEdit(false);
		importoDisponibile.setCanEdit(false);
	}
	
	public void reloadSelectItem(final SelectItem selectItem, final boolean cascadeOnlyOnChanged) {
		final String fieldName = selectItem.getName();
		final String value = selectItem.getValueAsString() != null ? selectItem.getValueAsString() : "";
		if(fieldName.equals("annoCompetenza")) {
			mDynamicForm.setValue("annoCompetenza", "");			
			mDynamicForm.setValue("skipCtrlDisp", false);			
			mDynamicForm.setValue("importoDisponibile", "");			
			mDynamicForm.setValue("importo", "");	
			mDynamicForm.markForRedraw();
		}
		selectItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					RecordList data = response.getDataAsRecordList();
					if (data.getLength() > 0) {
						if (data.getLength() == 1) {
							String newValue = data.get(0).getAttribute(selectItem.getValueFieldName());
							mDynamicForm.setValue(fieldName, newValue);
							if (!value.equals(newValue)) {
								if(cascadeOnlyOnChanged) {
									selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
								}
							} 
							if(fieldName.equals("idCapitolo")) {
								mDynamicForm.setValue("numeroCapitolo", data.get(0).getAttribute("numeroCapitolo"));
								mDynamicForm.setValue("descrizioneCapitolo", data.get(0).getAttribute("descrizioneCapitolo"));		
								mDynamicForm.setValue("codCentroCostoCapitolo", data.get(0).getAttribute("codCentroCosto"));
								mDynamicForm.setValue("pianoDeiContiFinanz", data.get(0).getAttribute("pianoDeiContiFinanz"));
								mDynamicForm.setValue("codiceCapitolo", data.get(0).getAttribute("codiceCapitolo"));
								mDynamicForm.setValue("livelliPdC", data.get(0).getAttribute("livelliPdC"));	
								mDynamicForm.setValue("descrizionePianoDeiConti", data.get(0).getAttribute("descrizionePianoDeiConti"));											
							} else if(fieldName.equals("annoCompetenza")) {										
								mDynamicForm.setValue("skipCtrlDisp", data.get(0).getAttributeAsBoolean("skipCtrlDisp"));
								mDynamicForm.setValue("importoDisponibile", data.get(0).getAttribute("importoDisponibile"));
							}
						} else if (!"".equals(value)) {
							boolean trovato = false;
							for (int i = 0; i < data.getLength(); i++) {
								if (value.equals(data.get(i).getAttribute(selectItem.getValueFieldName()))) {				
									if(fieldName.equals("idCapitolo")) {
										mDynamicForm.setValue("numeroCapitolo", data.get(i).getAttribute("numeroCapitolo"));
										mDynamicForm.setValue("descrizioneCapitolo", data.get(i).getAttribute("descrizioneCapitolo"));
										mDynamicForm.setValue("codCentroCostoCapitolo", data.get(i).getAttribute("codCentroCosto"));
										mDynamicForm.setValue("pianoDeiContiFinanz", data.get(i).getAttribute("pianoDeiContiFinanz"));
										mDynamicForm.setValue("codiceCapitolo", data.get(i).getAttribute("codiceCapitolo"));
										mDynamicForm.setValue("livelliPdC", data.get(i).getAttribute("livelliPdC"));
										mDynamicForm.setValue("descrizionePianoDeiConti", data.get(i).getAttribute("descrizionePianoDeiConti"));										
									}/* else if(fieldName.equals("annoCompetenza")) {						
										mDynamicForm.setValue("skipCtrlDisp", data.get(i).getAttributeAsBoolean("skipCtrlDisp"));
										mDynamicForm.setValue("importoDisponibile", data.get(i).getAttribute("importoDisponibile"));
									}*/
									trovato = true;
									break;
								} 
							}
							if (!trovato) {
								mDynamicForm.setValue(fieldName, "");
								if(cascadeOnlyOnChanged) {
									selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
								}
								if(fieldName.equals("idCapitolo")) {
									mDynamicForm.setValue("numeroCapitolo", "");
									mDynamicForm.setValue("descrizioneCapitolo", "");									
									mDynamicForm.setValue("codCentroCostoCapitolo", "");
									mDynamicForm.setValue("pianoDeiContiFinanz", "");
									mDynamicForm.setValue("codiceCapitolo", "");
									mDynamicForm.setValue("livelliPdC", "");
									mDynamicForm.setValue("descrizionePianoDeiConti", "");									
								}/* else if(fieldName.equals("annoCompetenza")) {				
									mDynamicForm.setValue("skipCtrlDisp", false);
									mDynamicForm.setValue("importoDisponibile", "");
								}*/
							}
						}
					} else {
						mDynamicForm.setValue(fieldName, ""); 
						if(cascadeOnlyOnChanged) {
							selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
						}
						if(fieldName.equals("idCapitolo")) {
							mDynamicForm.setValue("numeroCapitolo", "");
							mDynamicForm.setValue("descrizioneCapitolo", "");
							mDynamicForm.setValue("codCentroCostoCapitolo", "");
							mDynamicForm.setValue("pianoDeiContiFinanz", "");
							mDynamicForm.setValue("codiceCapitolo", "");
							mDynamicForm.setValue("livelliPdC", "");
							mDynamicForm.setValue("descrizionePianoDeiConti", "");							
						}
					}					
					if(!cascadeOnlyOnChanged) {
						selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
					}				
				}
			}
		});
	}
	
	@Override
	public void editNewRecord() {
		super.editNewRecord();
		sbiancaCampiAnagrafici(false);
		reloadSelectItem(idCapitolo, false);
	}
	
	@Override
	public void editRecord(Record record) {
		manageLoadSelectInEditRecord(record, idCapitolo, "idCapitolo", new String[] {"descrizioneCapitolo"}, "idCapitolo");
		record.setAttribute("codCentroCosto", record.getAttribute("codCentroCosto"));
		record.setAttribute("codCentroCostoCapitolo", record.getAttribute("codCentroCosto"));		
		// flgAutoIncrementante nel dettaglio lo chiamo in un altro modo  perchè qui è un boolean mentre in lista una stringa con valori 1/0		
		record.setAttribute("isAutoIncrementante", record.getAttribute("flgAutoIncrementante") != null && "1".equals(record.getAttribute("flgAutoIncrementante")));
		// flgCopertoDaFPV nel dettaglio lo chiamo in un altro modo  perchè qui è un boolean mentre in lista una stringa con valori 1/0		
		record.setAttribute("isCopertoDaFPV", record.getAttribute("flgCopertoDaFPV") != null && "1".equals(record.getAttribute("flgCopertoDaFPV")));
		record.setAttribute("isSoggEstero", record.getAttribute("flgSoggEstero") != null && "1".equals(record.getAttribute("flgSoggEstero")));
		boolean isPersonaFisica = record.getAttribute("tipoSoggetto") != null && "fisica".equalsIgnoreCase(record.getAttribute("tipoSoggetto"));
		boolean isPersonaGiuridica = record.getAttribute("tipoSoggetto") != null && "giuridica".equalsIgnoreCase(record.getAttribute("tipoSoggetto"));
		if(isPersonaFisica) {
			record.setAttribute("denominazioneSogg", (String) null);
			record.setAttribute("codFiscaleSoggPF", record.getAttribute("codFiscaleSogg"));
		} else if(isPersonaGiuridica) {
			record.setAttribute("codFiscaleSoggPG", record.getAttribute("codFiscaleSogg"));
		}
		super.editRecord(record);
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();		
		if(codCentroCostoValueMap == null || codCentroCostoValueMap.size() == 0) {
			lRecordToSave.setAttribute("codCentroCosto", codCentroCostoCapitolo.getValue());
		} else if(codCentroCostoValueMap.size() == 1) {
			lRecordToSave.setAttribute("codCentroCosto", codCentroCostoValueMap.keySet().toArray(new String[1])[0]);
		}	
		if(flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString())) {
			lRecordToSave.setAttribute("flgAutoIncrementante", isAutoIncrementante.getValueAsBoolean() != null && isAutoIncrementante.getValueAsBoolean() ? "1" : "0");
		} else {
			lRecordToSave.setAttribute("flgAutoIncrementante", "");
		}		
		if(flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString())) {
			lRecordToSave.setAttribute("flgCopertoDaFPV", isCopertoDaFPV.getValueAsBoolean() != null && isCopertoDaFPV.getValueAsBoolean() ? "1" : "0");
		} else {
			lRecordToSave.setAttribute("flgCopertoDaFPV", "");
		}
		lRecordToSave.setAttribute("flgSoggEstero", isSoggEstero.getValueAsBoolean() != null && isSoggEstero.getValueAsBoolean() ? "1" : "0");
		// Devo settare le date nel formato DD/MM/YYYY
		lRecordToSave.setAttribute("dataValuta", dataValuta.getValueAsDate() != null ? DateUtil.format(dataValuta.getValueAsDate()) : null);
		boolean isPersonaFisica = tipoSoggetto.getValueAsString() != null && "fisica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
		boolean isPersonaGiuridica = tipoSoggetto.getValueAsString() != null && "giuridica".equalsIgnoreCase(tipoSoggetto.getValueAsString());
		if(isPersonaFisica) {
			String cognomeSogg = lRecordToSave.getAttribute("cognomeSogg") != null ? lRecordToSave.getAttribute("cognomeSogg") : "";
			String nomeSogg = lRecordToSave.getAttribute("nomeSogg") != null ? lRecordToSave.getAttribute("nomeSogg") : "";
			if(!"".equals(cognomeSogg) && !"".equals(nomeSogg)) {
				lRecordToSave.setAttribute("denominazioneSogg", cognomeSogg + " " + nomeSogg);				
			} else if(!"".equals(cognomeSogg)) {
				lRecordToSave.setAttribute("denominazioneSogg", cognomeSogg);				
			} else if(!"".equals(nomeSogg)) {
				lRecordToSave.setAttribute("denominazioneSogg", nomeSogg);				
			} else {
				lRecordToSave.setAttribute("denominazioneSogg", (String) null);			
			}
			lRecordToSave.setAttribute("codFiscaleSogg", lRecordToSave.getAttribute("codFiscaleSoggPF"));
			lRecordToSave.setAttribute("codPIVASogg", (String) null);
		} else if(isPersonaGiuridica) {
			lRecordToSave.setAttribute("cognomeSogg", (String) null);
			lRecordToSave.setAttribute("nomeSogg", (String) null);			
			lRecordToSave.setAttribute("codFiscaleSogg", lRecordToSave.getAttribute("codFiscaleSoggPG"));			
		}
		
		if(lRecordToSave.getAttribute("flgCodiceSoggNonTrovato")!=null && "true".equalsIgnoreCase(lRecordToSave.getAttribute("flgCodiceSoggNonTrovato"))) {
			lRecordToSave.setAttribute("codiceSoggetto", (String) null); 
		}
		
		return lRecordToSave;
	}

	public void settaDatiAnagraficiNonModificabili() {
		denominazioneSogg.setCanEdit(false);
		codFiscaleSoggPG.setCanEdit(false);
		codPIVASogg.setCanEdit(false);
		nomeSogg.setCanEdit(false);
		cognomeSogg.setCanEdit(false);
		codFiscaleSoggPF.setCanEdit(false);
		codiceSoggetto.setCanEdit(false);
		indirizzoSogg.setCanEdit(false);
		cap.setCanEdit(false);
		provincia.setCanEdit(false);
		localita.setCanEdit(false);
		tipoSoggetto.setCanEdit(false);
//		isSoggEstero.setCanEdit(false);
		flgCampiSettatiDaSicra = true;
	}
	
}