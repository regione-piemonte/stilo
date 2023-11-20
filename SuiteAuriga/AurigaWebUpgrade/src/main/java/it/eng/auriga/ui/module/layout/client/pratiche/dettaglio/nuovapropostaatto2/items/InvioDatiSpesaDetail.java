/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemInputTransformer;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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
import it.eng.auriga.ui.module.layout.client.common.items.SelectItemValoriDizionario;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class InvioDatiSpesaDetail extends CustomDetail {
	
	protected ListaInvioDatiSpesaItem gridItem;
	
	protected DynamicForm mDynamicForm;
	
	protected SelectItem flgEntrataUscita;
	protected SelectItem annoEsercizio;
	protected CheckboxItem isCorrelata;
	protected TextAreaItem oggetto;
	protected FilteredSelectItem capitolo;
	protected SelectItem articolo;
	protected SelectItem numero;
	protected TextItem descrizioneCapitolo;
	protected NumericItem numeroCrono;
	protected HiddenItem titolo;
	protected HiddenItem liv1234PdC;
	protected HiddenItem codUnitaOrgCdRCap;
	protected HiddenItem desUnitaOrgCdRCap;
	protected HiddenItem codUnitaOrgCdRArt;
	protected HiddenItem desUnitaOrgCdRArt;
	protected HiddenItem codUnitaOrgCdRNum;
	protected HiddenItem desUnitaOrgCdRNum;
	protected ExtendedNumericItem codUnitaOrgCdR;
	protected TextItem desUnitaOrgCdR;	
	protected SelectItem liv5PdC;
	protected SelectItem annoCompetenza;
	protected NumericItem importoDisponibile;
	protected ExtendedNumericItem importo;	
	protected SelectItem codiceCIG;
	protected TextItem codiceCUP;
	protected TextItem codiceGAMIPBM;
	protected AnnoItem annoEsigibilitaDebito;
	protected DateItem dataEsigibilitaDa;
	protected DateItem dataEsigibilitaA;
	protected DateItem dataScadenzaEntrata;
	protected SelectItem dichiarazioneDL78;
	protected TextItem tipoFinanziamento;
	protected TextItem denominazioneSogg;
	protected TextItem codFiscaleSogg;
	protected TextItem codPIVASogg;
	protected TextItem indirizzoSogg;
	protected TextItem cap;
	protected TextItem localita;
	protected TextItem provincia;
	protected SelectItemValoriDizionario listaSpecifiche;
	protected TextAreaItem note;
	
	public InvioDatiSpesaDetail(String nomeEntita, final ListaInvioDatiSpesaItem gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		flgEntrataUscita = new SelectItem("flgEntrataUscita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_title());
		flgEntrataUscita.setStartRow(true);
		flgEntrataUscita.setValueMap(buildFlgEntrataUscitaValueMap());
		flgEntrataUscita.setDefaultValue("U");
		flgEntrataUscita.setAllowEmptyValue(false);
		flgEntrataUscita.setWidth(150);
		flgEntrataUscita.setColSpan(1);
		flgEntrataUscita.setRequired(true);
		flgEntrataUscita.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				reloadSelectItem(capitolo, false);
				mDynamicForm.redraw();
			}
		});
		
		annoEsercizio = new SelectItem("annoEsercizio", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsercizio_title());
		annoEsercizio.setWidth(150);
		annoEsercizio.setColSpan(1);
		annoEsercizio.setDefaultValue(DateTimeFormat.getFormat("yyyy").format(new Date()));
		annoEsercizio.setValueMap(buildAnnoEsercizioValueMap());
		annoEsercizio.setRequired(true);
		annoEsercizio.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				reloadSelectItem(capitolo, false);
			}
		});
		
		isCorrelata = new CheckboxItem("isCorrelata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgCorrelata_title());
		isCorrelata.setDefaultValue(false);
		isCorrelata.setColSpan(1);
		isCorrelata.setWidth("*");
		
		oggetto = new TextAreaItem("oggetto", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_oggetto_title());
		oggetto.setWidth(630);
		oggetto.setHeight(40);
		oggetto.setColSpan(14);
		oggetto.setStartRow(true);
		oggetto.setRequired(true);
		
		final ExtendedNumericItem codUnitaOrgCdRFilterXCap = new ExtendedNumericItem("codUnitaOrgCdRFilterXCap", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdRFilterXCap_title(), false);
		codUnitaOrgCdRFilterXCap.setWidth(150);
		codUnitaOrgCdRFilterXCap.setColSpan(1);
		codUnitaOrgCdRFilterXCap.setStartRow(true);
		codUnitaOrgCdRFilterXCap.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				reloadSelectItem(capitolo, true);			
			}
		});
		codUnitaOrgCdRFilterXCap.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing;
			}
		});
		
		final ExtendedTextItem desUnitaOrgCdRFilterXCap = new ExtendedTextItem("desUnitaOrgCdRFilterXCap");
		desUnitaOrgCdRFilterXCap.setShowTitle(false);
		desUnitaOrgCdRFilterXCap.setWidth(480);
		desUnitaOrgCdRFilterXCap.setColSpan(13);
		desUnitaOrgCdRFilterXCap.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				reloadSelectItem(capitolo, true);			
			}
		});
		desUnitaOrgCdRFilterXCap.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return editing;
			}
		});
		
		GWTRestDataSource lSuggerimentiCapitoloDataSource = new GWTRestDataSource(gridItem.getSIBDataSourceName(), "capitolo", FieldType.TEXT, true);
		lSuggerimentiCapitoloDataSource.addParam("fieldNameCombo", "capitolo");
				
		capitolo = new FilteredSelectItem("capitolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_capitolo_title()) {	
		
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
						GWTRestDataSource lSuggerimentiCapitoloDataSource = (GWTRestDataSource) capitolo.getOptionDataSource();
						lSuggerimentiCapitoloDataSource.addParam("annoEsercizio", annoEsercizio.getValueAsString());
						lSuggerimentiCapitoloDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());							
						lSuggerimentiCapitoloDataSource.addParam("codUnitaOrgCdR", codUnitaOrgCdRFilterXCap.getValueAsString());
						lSuggerimentiCapitoloDataSource.addParam("desUnitaOrgCdR", desUnitaOrgCdRFilterXCap.getValueAsString());	
						capitolo.setOptionDataSource(lSuggerimentiCapitoloDataSource);
						capitolo.invalidateDisplayValueCache();
					}
				});
				return pickListProperties;
			}
		};
		capitolo.setColSpan(1);
		capitolo.setWidth(150);
		capitolo.setPickListWidth(700);
		capitolo.setStartRow(true);
		capitolo.setRequired(true);
		capitolo.setAutoFetchData(false);
		capitolo.setAlwaysFetchMissingValues(true);
		capitolo.setFetchDelay(500);
		capitolo.setOptionDataSource(lSuggerimentiCapitoloDataSource);
		capitolo.setValueField("capitolo");
		capitolo.setDisplayField("capitolo");
		ListGridField descrizioneCapitoloField = new ListGridField("descrizioneCapitolo");		
		capitolo.setPickListFields(descrizioneCapitoloField);
		capitolo.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(capitolo.getSelectedRecord() != null) {
					mDynamicForm.setValue("codUnitaOrgCdRCap", capitolo.getSelectedRecord().getAttribute("codUnitaOrgCdR"));
					mDynamicForm.setValue("desUnitaOrgCdRCap", capitolo.getSelectedRecord().getAttribute("desUnitaOrgCdR"));							
				} else {
					mDynamicForm.setValue("codUnitaOrgCdRCap", "");		
					mDynamicForm.setValue("desUnitaOrgCdRCap", "");							
				}
				reloadSelectItem(articolo, false);
			}
		});
		
		GWTRestDataSource lSuggerimentiArticoloDataSource = new GWTRestDataSource(gridItem.getSIBDataSourceName(), "articolo", FieldType.TEXT);
		lSuggerimentiArticoloDataSource.addParam("fieldNameCombo", "articolo");
		
		articolo = new SelectItem("articolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_articolo_title());	
		articolo.setColSpan(1);
		articolo.setWidth(150);
		articolo.setRequired(true);
		articolo.setAutoFetchData(false);
		articolo.setAlwaysFetchMissingValues(true);
		articolo.setFetchDelay(500);
		articolo.setOptionDataSource(lSuggerimentiArticoloDataSource);
		articolo.setValueField("articolo");
		articolo.setDisplayField("articolo");		
		ListGrid articoloPickListProperties = new ListGrid();
		articoloPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		articoloPickListProperties.setShowHeader(false);
		articoloPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {					
				GWTRestDataSource lSuggerimentiArticoloDataSource = (GWTRestDataSource) articolo.getOptionDataSource();
				lSuggerimentiArticoloDataSource.addParam("annoEsercizio", annoEsercizio.getValueAsString());
				lSuggerimentiArticoloDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());	
				lSuggerimentiArticoloDataSource.addParam("capitolo", capitolo.getValueAsString());
				articolo.setOptionDataSource(lSuggerimentiArticoloDataSource);
				articolo.invalidateDisplayValueCache();
			}
		});
		articolo.setPickListProperties(articoloPickListProperties);
		articolo.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				if(articolo.getSelectedRecord() != null) {
					mDynamicForm.setValue("codUnitaOrgCdRArt", articolo.getSelectedRecord().getAttribute("codUnitaOrgCdR"));
					mDynamicForm.setValue("desUnitaOrgCdRArt", articolo.getSelectedRecord().getAttribute("desUnitaOrgCdR"));							
				} else {
					mDynamicForm.setValue("codUnitaOrgCdRArt", "");		
					mDynamicForm.setValue("desUnitaOrgCdRArt", "");							
				}			
				reloadSelectItem(numero, false);
			}
		});

		GWTRestDataSource lSuggerimentiNumeroDataSource = new GWTRestDataSource(gridItem.getSIBDataSourceName(), "numero", FieldType.TEXT);
		lSuggerimentiNumeroDataSource.addParam("fieldNameCombo", "numero");
		
		numero = new SelectItem("numero", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numero_title());
		numero.setColSpan(1);
		numero.setWidth(150);
		numero.setRequired(true);
		numero.setAutoFetchData(false);
		numero.setAlwaysFetchMissingValues(true);
		numero.setFetchDelay(500);
		numero.setOptionDataSource(lSuggerimentiNumeroDataSource);
		numero.setValueField("numero");
		numero.setDisplayField("numero");		
		ListGrid numeroPickListProperties = new ListGrid();
		numeroPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		numeroPickListProperties.setShowHeader(false);
		numeroPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {					
				GWTRestDataSource lSuggerimentiNumeroDataSource = (GWTRestDataSource) numero.getOptionDataSource();
				lSuggerimentiNumeroDataSource.addParam("annoEsercizio", annoEsercizio.getValueAsString());
				lSuggerimentiNumeroDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());
				lSuggerimentiNumeroDataSource.addParam("capitolo", capitolo.getValueAsString());
				lSuggerimentiNumeroDataSource.addParam("articolo", articolo.getValueAsString());
				numero.setOptionDataSource(lSuggerimentiNumeroDataSource);
				numero.invalidateDisplayValueCache();
			}
		});
		numero.setPickListProperties(numeroPickListProperties);
		numero.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.setValue("descrizioneCapitolo", numero.getSelectedRecord() != null ? numero.getSelectedRecord().getAttribute("descrizioneCapitolo") : "");
				mDynamicForm.setValue("titolo", numero.getSelectedRecord() != null ? numero.getSelectedRecord().getAttribute("titolo") : "");
				mDynamicForm.setValue("liv1234PdC", numero.getSelectedRecord() != null ? numero.getSelectedRecord().getAttribute("liv1234PdC") : "");
				if(numero.getSelectedRecord() != null) {
					mDynamicForm.setValue("codUnitaOrgCdRNum", numero.getSelectedRecord().getAttribute("codUnitaOrgCdR"));
					mDynamicForm.setValue("desUnitaOrgCdRNum", numero.getSelectedRecord().getAttribute("desUnitaOrgCdR"));
				} else {
					mDynamicForm.setValue("codUnitaOrgCdRNum", "");
					mDynamicForm.setValue("desUnitaOrgCdRNum", "");
				}					
				String liv5PdCValue = numero.getSelectedRecord() != null ? numero.getSelectedRecord().getAttribute("liv5PdC") : "";
				if(!"".equals(liv5PdCValue) && !"0".equals(liv5PdCValue)) {
					mDynamicForm.setValue("liv5PdC", liv5PdCValue);	
				} else {
					reloadSelectItem(liv5PdC, false);
				}
				reloadSelectItem(annoCompetenza, false);
			}
		});
			
		descrizioneCapitolo = new TextItem("descrizioneCapitolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_descrizioneCapitolo_title());
		descrizioneCapitolo.setWidth(630);
		descrizioneCapitolo.setColSpan(14);
		descrizioneCapitolo.setStartRow(true);
		descrizioneCapitolo.setRequired(true);
		
		numeroCrono = new NumericItem("numeroCrono", "Crono", false);
		numeroCrono.setStartRow(true);
		numeroCrono.setColSpan(1);
		numeroCrono.setWidth(150);
		numeroCrono.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showNumeroCrono();
			}
		});
		
		titolo = new HiddenItem("titolo");
		
		liv1234PdC = new HiddenItem("liv1234PdC");
				
		codUnitaOrgCdR = new ExtendedNumericItem("codUnitaOrgCdR", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdR_title(), false);
		codUnitaOrgCdR.setWidth(150);
		codUnitaOrgCdR.setColSpan(1);
		codUnitaOrgCdR.setStartRow(true);
		codUnitaOrgCdR.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {		
				mDynamicForm.setValue("desUnitaOrgCdR", "");
			}
		});
		
		desUnitaOrgCdR = new TextItem("desUnitaOrgCdR");
		desUnitaOrgCdR.setShowTitle(false);
		desUnitaOrgCdR.setWidth(480);
		desUnitaOrgCdR.setColSpan(13);
		
		codUnitaOrgCdRCap = new HiddenItem("codUnitaOrgCdRCap");
		desUnitaOrgCdRCap = new HiddenItem("desUnitaOrgCdRCap");		
		
		codUnitaOrgCdRArt = new HiddenItem("codUnitaOrgCdRArt");
		desUnitaOrgCdRArt = new HiddenItem("desUnitaOrgCdRArt");		
		
		codUnitaOrgCdRNum = new HiddenItem("codUnitaOrgCdRNum");
		desUnitaOrgCdRNum = new HiddenItem("desUnitaOrgCdRNum");			
		
		/*
		final StaticTextItem liv1234PdCLabel = new StaticTextItem("liv1234PdCLabel", "PdC");
		liv1234PdCLabel.setStartRow(true);
		liv1234PdCLabel.setAttribute("obbligatorio", true);
		liv1234PdCLabel.setAlign(Alignment.RIGHT);
		liv1234PdCLabel.setWidth(1);
		liv1234PdCLabel.setWrap(false);		
		liv1234PdCLabel.setValueFormatter(new FormItemValueFormatter() {
			@Override
			public String formatValue(Object value, Record record, DynamicForm form,
					FormItem item) {
				String label = null;
				String liv1234PdC = vm.getValueAsString("liv1234PdC");
				if(liv1234PdC != null && !"".equals(liv1234PdC)) {
					label = liv1234PdC.replace(".", "&nbsp;.&nbsp;") + " .";													
				} else {
					label = "";					
				}				
				return label;
			}
		});		
		liv1234PdCLabel.setShowIfCondition(new FormItemIfFunction() {			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				String liv1234PdC = vm.getValueAsString("liv1234PdC");
				if (liv1234PdC != null && !"".equals(liv1234PdC)) {
					liv5PdC.setShowTitle(false);
					liv5PdC.setStartRow(false);
					liv5PdC.setColSpan(1);	
					return true;
				} else {
					liv5PdC.setShowTitle(true);
					liv5PdC.setStartRow(true);
					liv5PdC.setColSpan(2);	
					return false;
				}
			}
		});
		*/
		
		GWTRestDataSource lLiv5PdCDataSource = new GWTRestDataSource(gridItem.getSIBDataSourceName(), "liv5PdC", FieldType.TEXT);
		lLiv5PdCDataSource.addParam("fieldNameCombo", "liv5PdC");
		
		liv5PdC = new SelectItem("liv5PdC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_liv5PdC_title());
		liv5PdC.setStartRow(true);
		liv5PdC.setWidth(150);
		liv5PdC.setPickListWidth(700);
		liv5PdC.setColSpan(1);
		liv5PdC.setRequired(true);
		liv5PdC.setAutoFetchData(false);
		liv5PdC.setAlwaysFetchMissingValues(true);
		liv5PdC.setFetchDelay(500);
		liv5PdC.setOptionDataSource(lLiv5PdCDataSource);
		liv5PdC.setValueField("liv5PdC");
		liv5PdC.setDisplayField("liv5PdC");
		ListGridField descrizionePdCField = new ListGridField("descrizionePdC");		
		liv5PdC.setPickListFields(descrizionePdCField);		
		ListGrid liv5PdCPickListProperties = new ListGrid();
		liv5PdCPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		liv5PdCPickListProperties.setShowHeader(false);
		liv5PdCPickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {					
				GWTRestDataSource lSuggerimentiLiv5PdCDataSource = (GWTRestDataSource) liv5PdC.getOptionDataSource();
				lSuggerimentiLiv5PdCDataSource.addParam("annoEsercizio", annoEsercizio.getValueAsString());
				lSuggerimentiLiv5PdCDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());
				lSuggerimentiLiv5PdCDataSource.addParam("capitolo", capitolo.getValueAsString());
				lSuggerimentiLiv5PdCDataSource.addParam("articolo", articolo.getValueAsString());
				lSuggerimentiLiv5PdCDataSource.addParam("numero", numero.getValueAsString());	
				lSuggerimentiLiv5PdCDataSource.addParam("liv1234PdC", (String) liv1234PdC.getValue());					
				liv5PdC.setOptionDataSource(lSuggerimentiLiv5PdCDataSource);
				liv5PdC.invalidateDisplayValueCache();
			}
		});
		liv5PdC.setPickListProperties(liv5PdCPickListProperties);
		
		GWTRestDataSource lCompetenzaPluriennaleDataSource = new GWTRestDataSource(gridItem.getSIBDataSourceName(), "annoCompetenza", FieldType.TEXT);
		lCompetenzaPluriennaleDataSource.addParam("fieldNameCombo", "annoCompetenza");
		
		annoCompetenza = new SelectItem("annoCompetenza", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCompetenza_title());
		annoCompetenza.setStartRow(true);
		annoCompetenza.setWidth(150);
		annoCompetenza.setPickListWidth(275);		
		annoCompetenza.setColSpan(1);
		annoCompetenza.setRequired(true);
		annoCompetenza.setAutoFetchData(false);
		annoCompetenza.setAlwaysFetchMissingValues(true);
		annoCompetenza.setFetchDelay(500);
		annoCompetenza.setOptionDataSource(lCompetenzaPluriennaleDataSource);
		annoCompetenza.setValueField("annoCompetenza");
		annoCompetenza.setDisplayField("annoCompetenza");
		ListGridField annoCompetenzaField = new ListGridField("annoCompetenza", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCompetenza_title());
		annoCompetenzaField.setType(ListGridFieldType.INTEGER);
		annoCompetenzaField.setWidth(125);
		ListGridField importoDisponibileField = new ListGridField("importoDisponibile", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importoDisponibile_title());
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
				lCompetenzaPluriennaleDataSource.addParam("annoEsercizio", annoEsercizio.getValueAsString());
				lCompetenzaPluriennaleDataSource.addParam("flgEntrataUscita", flgEntrataUscita.getValueAsString());
				lCompetenzaPluriennaleDataSource.addParam("capitolo", capitolo.getValueAsString());
				lCompetenzaPluriennaleDataSource.addParam("articolo", articolo.getValueAsString());
				lCompetenzaPluriennaleDataSource.addParam("numero", numero.getValueAsString());
				annoCompetenza.setOptionDataSource(lCompetenzaPluriennaleDataSource);
				annoCompetenza.invalidateDisplayValueCache();
			}
		});
		annoCompetenzaPickListProperties.addCellClickHandler(new CellClickHandler() {			
			@Override
			public void onCellClick(CellClickEvent event) {
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
		
		importoDisponibile = new ExtendedNumericItem("importoDisponibile", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importoDisponibile_title());
		importoDisponibile.setKeyPressFilter("[0-9.,]");
		importoDisponibile.setWidth(150);
		importoDisponibile.setColSpan(1);
		importoDisponibile.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importoDisponibile.setValue(NumberFormatUtility.getFormattedValue(importoDisponibile.getValueAsString()));				
				return true;
			}
		});

		importo = new ExtendedNumericItem("importo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importo_title());
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
		CustomValidator importoDisponibilitaValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				String annoCorrente = DateTimeFormat.getFormat("yyyy").format(new Date());			
				String annoInizioPeriodoBilancio = AurigaLayout.getParametroDB("ANNO_INIZIO_PERIODO_BILANCIO");
				HashSet<Integer> triennoDiBilancio = new HashSet<Integer>();
				if(annoInizioPeriodoBilancio != null && !"".equals(annoInizioPeriodoBilancio)) {
					if(Integer.parseInt(annoInizioPeriodoBilancio) >= Integer.parseInt(annoCorrente)) {
						triennoDiBilancio.add(Integer.parseInt(annoInizioPeriodoBilancio));
					}
					triennoDiBilancio.add(Integer.parseInt(annoInizioPeriodoBilancio) + 1);
					triennoDiBilancio.add(Integer.parseInt(annoInizioPeriodoBilancio) + 2);
				} else {
					triennoDiBilancio.add(Integer.parseInt(annoCorrente));
				}
				boolean skipControlloDisponibilita = true;
				if(flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString()) && 
				   annoCompetenza.getValueAsString() != null && !"".equals(annoCompetenza.getValueAsString())) {
					if(capitolo.getValueAsString() != null && !"".equals(capitolo.getValueAsString()) && 
					   articolo.getValueAsString() != null && !"".equals(articolo.getValueAsString()) && 
					   numero.getValueAsString() != null && !"".equals(numero.getValueAsString())) {
						String titoloVocePEGSelezionata = titolo.getValue() != null ? (String) titolo.getValue() : "";
						if(!getVociPEGNoVerifDisp().contains(titoloVocePEGSelezionata) && triennoDiBilancio.contains(Integer.parseInt(annoCompetenza.getValueAsString()))) {
							skipControlloDisponibilita = false;
						}
					}
				}
				if(!skipControlloDisponibilita) {
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
				return true;	
			}
		};
		importoDisponibilitaValidator.setErrorMessage("Valore non valido: il valore impegnato non deve superare la disponibilità");
		CustomValidator importoMaggioreDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				String pattern = "#,##0.00";
				double importo = 0;
				if(value != null && !"".equals(value)) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				return importo > 0;
			}
		};
		importoMaggioreDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore di zero");
		importo.setValidators(importoPrecisionValidator, importoMaggioreDiZeroValidator, importoDisponibilitaValidator);
		
		importo.setWidth(150);
		importo.setColSpan(1);
		importo.setAttribute("obbligatorio", true);
//		importo.setRequired(true);
		
		if(!gridItem.isEsclusoCIGProposta()) {						
//			CustomValidator codiceCIGLengthValidator = new CustomValidator() {
//	
//				@Override
//				protected boolean condition(Object value) {
//					if (value != null && !"".equals((String) value)) {
//						String valore = (String) value;
//						return valore.length() == 10;
//					}
//					return true;
//				}
//			};
//			codiceCIGLengthValidator.setErrorMessage("Il codice CIG, se indicato, deve essere di 10 caratteri");
			
			codiceCIG = new SelectItem("codiceCIG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCIG_title());
			codiceCIG.setWidth(150);
			codiceCIG.setColSpan(1);
			codiceCIG.setStartRow(true);
//			codiceCIG.setLength(10);
			String[] lCIGValueMap = gridItem.getCIGValueMap();
			if(lCIGValueMap != null && lCIGValueMap.length > 0) {
				if(lCIGValueMap.length == 1) {
					codiceCIG.setDefaultToFirstOption(true);
				}
				codiceCIG.setValueMap(lCIGValueMap);
				codiceCIG.setRequired(true);
			}
//			codiceCIG.setValidators(codiceCIGLengthValidator);
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
		
		codiceCUP = new TextItem("codiceCUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCUP_title());
		codiceCUP.setWidth(150);
		codiceCUP.setColSpan(1);
		codiceCUP.setStartRow(true);
		codiceCUP.setLength(15);
		codiceCUP.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCUP.setValidators(codiceCUPLengthValidator);
		
		codiceGAMIPBM = new TextItem("codiceGAMIPBM", getTitleCodiceGAMIPBM());
		codiceGAMIPBM.setWidth(150);
		codiceGAMIPBM.setColSpan(1);
		codiceGAMIPBM.setStartRow(true);
		
		annoEsigibilitaDebito = new AnnoItem("annoEsigibilitaDebito", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsigibilitaDebito_title());
		annoEsigibilitaDebito.setWidth(150);
		annoEsigibilitaDebito.setColSpan(1);
		annoEsigibilitaDebito.setStartRow(true);
		annoEsigibilitaDebito.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		});
		if(isListaInvioDatiSpesaCorrente() || isListaInvioDatiSpesaContoCapitale()) {
			annoEsigibilitaDebito.setAttribute("obbligatorio", true);
			annoEsigibilitaDebito.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
				}
			}));
		}
		
		dataEsigibilitaDa = new DateItem("dataEsigibilitaDa", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaDa_title());
		dataEsigibilitaDa.setWidth(150);
		dataEsigibilitaDa.setColSpan(1);
		dataEsigibilitaDa.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isListaInvioDatiSpesaContoCapitale()) {
					return false;
				}
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		});
		if(isListaInvioDatiSpesaCorrente()) {
			dataEsigibilitaDa.setAttribute("obbligatorio", true);
			dataEsigibilitaDa.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
				}
			}));
		}
		
		dataEsigibilitaA = new DateItem("dataEsigibilitaA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaA_title());
		dataEsigibilitaA.setWidth(150);
		dataEsigibilitaA.setColSpan(1);
		dataEsigibilitaA.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isListaInvioDatiSpesaContoCapitale()) {
					return false;
				}
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		});
		if(isListaInvioDatiSpesaCorrente()) {
			dataEsigibilitaA.setAttribute("obbligatorio", true);
			dataEsigibilitaA.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
				
				@Override
				public boolean execute(FormItem formItem, Object value) {
					return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
				}
			}));
		}
		
		dataScadenzaEntrata = new DateItem("dataScadenzaEntrata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataScadenzaEntrata_title());
		dataScadenzaEntrata.setStartRow(true);
		dataScadenzaEntrata.setWidth(150);
		dataScadenzaEntrata.setColSpan(1);
		dataScadenzaEntrata.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString());
			}
		});
		dataScadenzaEntrata.setAttribute("obbligatorio", true);
		dataScadenzaEntrata.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString());
			}
		}));
				
		dichiarazioneDL78 = new SelectItem("dichiarazioneDL78", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dichiarazioneDL78_title());
		dichiarazioneDL78.setValueMap("SI", "NO");
		dichiarazioneDL78.setAllowEmptyValue(false);
		dichiarazioneDL78.setWidth(150);
		dichiarazioneDL78.setColSpan(1);
		dichiarazioneDL78.setStartRow(true);
		dichiarazioneDL78.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		});
		dichiarazioneDL78.setAttribute("obbligatorio", true);
		dichiarazioneDL78.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return flgEntrataUscita.getValueAsString() != null && "U".equals(flgEntrataUscita.getValueAsString());
			}
		}));
		
		tipoFinanziamento = new TextItem("tipoFinanziamento", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoFinanziamento_title());
		tipoFinanziamento.setWidth(295);
		tipoFinanziamento.setColSpan(4);
		tipoFinanziamento.setStartRow(true);
		
		denominazioneSogg = new TextItem("denominazioneSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_denominazioneSogg_title());
		denominazioneSogg.setWidth(295);
		denominazioneSogg.setColSpan(3);
		denominazioneSogg.setStartRow(true);
		denominazioneSogg.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString())) {
					denominazioneSogg.setAttribute("obbligatorio", true);
					denominazioneSogg.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_denominazioneSogg_title()));
				} else {
					denominazioneSogg.setAttribute("obbligatorio", false);
					denominazioneSogg.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_denominazioneSogg_title());
				}
				return true;
			}
		});
		denominazioneSogg.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString());
			}
		}));
		
		CustomValidator codFiscalePIVAValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString())) {
					boolean isCodFiscaleValorizzato = codFiscaleSogg.getValueAsString() != null && !"".equals(codFiscaleSogg.getValueAsString().trim());
					boolean isCodPIVAValorizzato = codPIVASogg.getValueAsString() != null && !"".equals(codPIVASogg.getValueAsString().trim());
					if(!isCodFiscaleValorizzato && !isCodPIVAValorizzato) {
						return false;
					}
				}
				return true;
			}
		};
		codFiscalePIVAValidator.setErrorMessage("Almeno uno tra C.F. e P.IVA deve essere valorizzato");
		
		codFiscaleSogg = new TextItem("codFiscaleSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codFiscaleSogg_title());
		codFiscaleSogg.setWidth(150);
		codFiscaleSogg.setColSpan(1);
		codFiscaleSogg.setStartRow(true);
		codFiscaleSogg.setLength(16);
		codFiscaleSogg.setCharacterCasing(CharacterCasing.UPPER);
		codFiscaleSogg.setValidators(codFiscalePIVAValidator, new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals(value)) {
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);					
				}
				return true;
			}
		});		
		codFiscaleSogg.setShowIfCondition(new FormItemIfFunction() {
					
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString())) {
					codFiscaleSogg.setAttribute("obbligatorio", true);
					codFiscaleSogg.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codFiscaleSogg_title()));
				} else {
					codFiscaleSogg.setAttribute("obbligatorio", false);
					codFiscaleSogg.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codFiscaleSogg_title());
				}
				return true;
			}
		});
		
		codPIVASogg = new TextItem("codPIVASogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codPIVASogg_title());
		codPIVASogg.setWidth(150);
		codPIVASogg.setColSpan(1);
		codPIVASogg.setStartRow(true);
		codPIVASogg.setLength(11);
		codPIVASogg.setCharacterCasing(CharacterCasing.UPPER);
		codPIVASogg.setValidators(codFiscalePIVAValidator, new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value != null && !"".equals(value)) {
					RegExp regExp = RegExp.compile(RegExpUtility.partitaIvaRegExp());
					return regExp.test((String) value);					
				}
				return true;
			}
		});		
		codPIVASogg.setShowIfCondition(new FormItemIfFunction() {
					
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(flgEntrataUscita.getValueAsString() != null && "E".equals(flgEntrataUscita.getValueAsString())) {
					codPIVASogg.setAttribute("obbligatorio", true);
					codPIVASogg.setTitle(FrontendUtil.getRequiredFormItemTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codPIVASogg_title()));
				} else {
					codPIVASogg.setAttribute("obbligatorio", false);
					codPIVASogg.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codPIVASogg_title());
				}
				return true;
			}
		});
		
		indirizzoSogg = new TextItem("indirizzoSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_indirizzoSogg_title());
		indirizzoSogg.setWidth(630);
		indirizzoSogg.setColSpan(14);
		indirizzoSogg.setStartRow(true);
		
		cap = new TextItem("cap", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_cap_title());
		cap.setLength(5);
		cap.setWidth(150);
		cap.setColSpan(1);
		
		localita = new TextItem("localita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_localita_title());
		localita.setWidth(630);
		localita.setColSpan(14);
		localita.setStartRow(true);
		
		provincia = new TextItem("provincia", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_provincia_title());
		provincia.setLength(2);
		provincia.setWidth(50);
		provincia.setColSpan(1);			
		
		String dictionaryEntrySpecifiche = null;
		if(isListaInvioDatiSpesaCorrente()) {
			dictionaryEntrySpecifiche = "DATI_CONT_CORR_AUR_CARATTERISTICHE";
		} else if(isListaInvioDatiSpesaContoCapitale()) {
			dictionaryEntrySpecifiche = "DATI_CONT_CCAP_AUR_CARATTERISTICHE";
		}
		
		listaSpecifiche = new SelectItemValoriDizionario("listaSpecifiche", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_listaSpecifiche_title(), dictionaryEntrySpecifiche, true);
		listaSpecifiche.setWidth(630);
		listaSpecifiche.setColSpan(14);
		listaSpecifiche.setStartRow(true);
		listaSpecifiche.setMultiple(true);
		
		note = new TextAreaItem("note", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_note_title());
		note.setWidth(630);
		note.setHeight(40);
		note.setColSpan(14);
		note.setStartRow(true);
		
		List<FormItem> items = new ArrayList<FormItem>();		
		items.add(flgEntrataUscita); items.add(annoEsercizio); items.add(isCorrelata);
		items.add(oggetto);
		items.add(codUnitaOrgCdRFilterXCap); items.add(desUnitaOrgCdRFilterXCap);
		items.add(capitolo); items.add(articolo); items.add(numero);
		items.add(descrizioneCapitolo);
		items.add(numeroCrono);
		items.add(titolo); items.add(liv1234PdC);
		items.add(codUnitaOrgCdR); items.add(desUnitaOrgCdR);
		items.add(codUnitaOrgCdRCap); items.add(desUnitaOrgCdRCap);
		items.add(codUnitaOrgCdRArt); items.add(desUnitaOrgCdRArt);
		items.add(codUnitaOrgCdRNum); items.add(desUnitaOrgCdRNum);
		/*items.add(liv1234PdCLabel);*/ items.add(liv5PdC);
		items.add(annoCompetenza); items.add(importoDisponibile); items.add(importo);	
		if(!gridItem.isEsclusoCIGProposta()) {	
			items.add(codiceCIG);
		}
		items.add(codiceCUP);
		items.add(codiceGAMIPBM);	
		items.add(annoEsigibilitaDebito); items.add(dataEsigibilitaDa); items.add(dataEsigibilitaA);			  			
		items.add(dataScadenzaEntrata);
		items.add(dichiarazioneDL78);
		items.add(tipoFinanziamento);
		items.add(denominazioneSogg);
		items.add(codFiscaleSogg);
		items.add(codPIVASogg);
		items.add(indirizzoSogg); items.add(cap);
		items.add(localita); items.add(provincia);
		items.add(listaSpecifiche);
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
	
	public static LinkedHashMap<String, String> buildTipoDettaglioValueMap() {
		LinkedHashMap<String, String> tipoDettaglioValueMap = new LinkedHashMap<String, String>();
		tipoDettaglioValueMap.put("IPG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_IPG_value()); //impegno
		tipoDettaglioValueMap.put("ACC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_ACC_value()); //accertamento
		tipoDettaglioValueMap.put("VIP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VIP_value()); //variazione di impegno
		tipoDettaglioValueMap.put("VAC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VAC_value()); //variazione di accertamento
		tipoDettaglioValueMap.put("SIP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SIP_value()); //subimpegno
		tipoDettaglioValueMap.put("SAC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SAC_value()); //subaccertamento
		tipoDettaglioValueMap.put("VSI", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSI_value()); //variazione di subimpegno
		tipoDettaglioValueMap.put("VSA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSA_value()); //variazione di subaccertamento
		tipoDettaglioValueMap.put("COP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_COP_value()); //cronoprogramma
		tipoDettaglioValueMap.put("SCP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SCP_value()); //subcronoprogramma
		return tipoDettaglioValueMap;
	}
	
	public static LinkedHashMap<String, String> buildFlgEntrataUscitaValueMap() {
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_E_value());
		flgEntrataUscitaValueMap.put("U", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_U_value());		
		return flgEntrataUscitaValueMap;
	}
	
	public static LinkedHashMap<String, String> buildAnnoEsercizioValueMap() {
		String annoCorrente = DateTimeFormat.getFormat("yyyy").format(new Date());
//		String annoPrec = String.valueOf(Integer.parseInt(annoCorrente) - 1);
//		String annoSucc = String.valueOf(Integer.parseInt(annoCorrente) + 1);
		LinkedHashMap<String, String> annoEsercizioValueMap = new LinkedHashMap<String, String>();
//		annoEsercizioValueMap.put(annoPrec, annoPrec);
		annoEsercizioValueMap.put(annoCorrente, annoCorrente);		
//		annoEsercizioValueMap.put(annoSucc, annoSucc);		
		return annoEsercizioValueMap;
	}
	
	public HashSet<String> getVociPEGNoVerifDisp() {
		return gridItem != null && gridItem.getVociPEGNoVerifDisp() != null ? gridItem.getVociPEGNoVerifDisp() : new HashSet<String>();
	}
	
	public boolean isListaInvioDatiSpesaCorrente() {
		return gridItem != null && gridItem.isListaInvioDatiSpesaCorrente();
	}
	
	public boolean isListaInvioDatiSpesaContoCapitale() {
		return gridItem != null && gridItem.isListaInvioDatiSpesaContoCapitale();
	}
	
	public boolean showNumeroCrono() {
		return gridItem != null ? gridItem.showNumeroCrono() : false;
	}
	
	public String getTitleCodiceGAMIPBM() {
		String label = gridItem != null ? gridItem.getTitleCodiceGAMIPBM() : null;
		return label != null && !"".equals(label) ? label : I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceGAMIPBM_title();
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		descrizioneCapitolo.setCanEdit(false);
		importoDisponibile.setCanEdit(false);
	}
	
	public void reloadSelectItem(final SelectItem selectItem, final boolean cascadeOnlyOnChanged) {
		final String fieldName = selectItem.getName();
		final String value = selectItem.getValueAsString() != null ? selectItem.getValueAsString() : "";
		if(fieldName.equals("annoCompetenza")) {
			mDynamicForm.setValue("annoCompetenza", "");		
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
							if(fieldName.equals("capitolo")) {
								mDynamicForm.setValue("codUnitaOrgCdRCap", data.get(0).getAttribute("codUnitaOrgCdR"));
								mDynamicForm.setValue("desUnitaOrgCdRCap", data.get(0).getAttribute("desUnitaOrgCdR"));									
							} else if(fieldName.equals("articolo")) {
								mDynamicForm.setValue("codUnitaOrgCdRArt", data.get(0).getAttribute("codUnitaOrgCdR"));
								mDynamicForm.setValue("desUnitaOrgCdRArt", data.get(0).getAttribute("desUnitaOrgCdR"));									
							} else if(fieldName.equals("numero")) {
								mDynamicForm.setValue("codUnitaOrgCdRNum", data.get(0).getAttribute("codUnitaOrgCdR"));
								mDynamicForm.setValue("desUnitaOrgCdRNum", data.get(0).getAttribute("desUnitaOrgCdR"));	
								mDynamicForm.setValue("descrizioneCapitolo", data.get(0).getAttribute("descrizioneCapitolo"));		
								mDynamicForm.setValue("titolo", data.get(0).getAttribute("titolo"));
								mDynamicForm.setValue("liv1234PdC", data.get(0).getAttribute("liv1234PdC"));	
								String liv5PdCValue = data.get(0).getAttribute("liv5PdC") != null ? data.get(0).getAttribute("liv5PdC") : "";
								if(!"".equals(liv5PdCValue) && !"0".equals(liv5PdCValue)) {
									mDynamicForm.setValue("liv5PdC", liv5PdCValue);	
								}
							}
						} else if (!"".equals(value)) {
							boolean trovato = false;
							for (int i = 0; i < data.getLength(); i++) {
								if (value.equals(data.get(i).getAttribute(selectItem.getValueFieldName()))) {				
									if(fieldName.equals("capitolo")) {
										mDynamicForm.setValue("codUnitaOrgCdRCap", data.get(i).getAttribute("codUnitaOrgCdR"));
										mDynamicForm.setValue("desUnitaOrgCdRCap", data.get(i).getAttribute("desUnitaOrgCdR"));									
									} else if(fieldName.equals("articolo")) {
										mDynamicForm.setValue("codUnitaOrgCdRArt", data.get(i).getAttribute("codUnitaOrgCdR"));
										mDynamicForm.setValue("desUnitaOrgCdRArt", data.get(i).getAttribute("desUnitaOrgCdR"));									
									} else if(fieldName.equals("numero")) {
										mDynamicForm.setValue("codUnitaOrgCdRNum", data.get(i).getAttribute("codUnitaOrgCdR"));
										mDynamicForm.setValue("desUnitaOrgCdRNum", data.get(i).getAttribute("desUnitaOrgCdR"));	
										mDynamicForm.setValue("descrizioneCapitolo", data.get(i).getAttribute("descrizioneCapitolo"));
										mDynamicForm.setValue("titolo", data.get(i).getAttribute("titolo"));
										mDynamicForm.setValue("liv1234PdC", data.get(i).getAttribute("liv1234PdC"));
										String liv5PdCValue = data.get(i).getAttribute("liv5PdC") != null ? data.get(i).getAttribute("liv5PdC") : "";
										if(!"".equals(liv5PdCValue) && !"0".equals(liv5PdCValue)) {
											mDynamicForm.setValue("liv5PdC", liv5PdCValue);	
										}
									}
									trovato = true;
									break;
								} 
							}
							if (!trovato) {
								mDynamicForm.setValue(fieldName, "");
								if(cascadeOnlyOnChanged) {
									selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
								}
								if(fieldName.equals("capitolo")) {
									mDynamicForm.setValue("codUnitaOrgCdRCap", "");
									mDynamicForm.setValue("desUnitaOrgCdRCap", "");									
								} else if(fieldName.equals("articolo")) {
									mDynamicForm.setValue("codUnitaOrgCdRArt", "");
									mDynamicForm.setValue("desUnitaOrgCdRArt", "");									
								} else if(fieldName.equals("numero")) {
									mDynamicForm.setValue("codUnitaOrgCdRNum", "");
									mDynamicForm.setValue("desUnitaOrgCdRNum", "");	
									mDynamicForm.setValue("descrizioneCapitolo", "");
									mDynamicForm.setValue("titolo", "");
									mDynamicForm.setValue("liv1234PdC", "");	
									mDynamicForm.setValue("liv5PdC", "");	
								}	
							}
						}
					} else {
						mDynamicForm.setValue(fieldName, ""); 
						if(cascadeOnlyOnChanged) {
							selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
						}
						if(fieldName.equals("capitolo")) {
							mDynamicForm.setValue("codUnitaOrgCdRCap", "");
							mDynamicForm.setValue("desUnitaOrgCdRCap", "");									
						} else if(fieldName.equals("articolo")) {
							mDynamicForm.setValue("codUnitaOrgCdRArt", "");
							mDynamicForm.setValue("desUnitaOrgCdRArt", "");									
						} else if(fieldName.equals("numero")) {
							mDynamicForm.setValue("codUnitaOrgCdRNum", "");
							mDynamicForm.setValue("desUnitaOrgCdRNum", "");	
							mDynamicForm.setValue("descrizioneCapitolo", "");
							mDynamicForm.setValue("titolo", "");
							mDynamicForm.setValue("liv1234PdC", "");	
							mDynamicForm.setValue("liv5PdC", "");
						}
					}					
					if(!cascadeOnlyOnChanged) {
						selectItem.fireEvent(new ChangedEvent(selectItem.getJsObj()));
					}
					aggiornaUnitaOrgCdR();					
				}
			}
		});
	}
	
	public void aggiornaUnitaOrgCdR() {
		String cap = capitolo.getValueAsString() != null ? capitolo.getValueAsString() : "";
		String art = articolo.getValueAsString() != null ? articolo.getValueAsString() : "";
		String num = numero.getValueAsString() != null ? numero.getValueAsString() : "";
//		String codUOCdR = codUnitaOrgCdR.getValueAsString() != null ? codUnitaOrgCdR.getValueAsString() : "";
//		String desUOCdR = desUnitaOrgCdR.getValueAsString() != null ? desUnitaOrgCdR.getValueAsString() : "";
		if(!"".equals(cap) && !"".equals(art) && !"".equals(num)) {
			if(!"0".equals(num)) {
				mDynamicForm.setValue("codUnitaOrgCdR", mDynamicForm.getValueAsString("codUnitaOrgCdRNum"));
				mDynamicForm.setValue("desUnitaOrgCdR", mDynamicForm.getValueAsString("desUnitaOrgCdRNum"));	
			} else if(!"0".equals(art)) {
				mDynamicForm.setValue("codUnitaOrgCdR", mDynamicForm.getValueAsString("codUnitaOrgCdRArt"));
				mDynamicForm.setValue("desUnitaOrgCdR", mDynamicForm.getValueAsString("desUnitaOrgCdRArt"));	
			} else if(!"0".equals(cap)) {
				mDynamicForm.setValue("codUnitaOrgCdR", mDynamicForm.getValueAsString("codUnitaOrgCdRCap"));
				mDynamicForm.setValue("desUnitaOrgCdR", mDynamicForm.getValueAsString("desUnitaOrgCdRCap"));	
			} 
			// se non sono entrambi valorizzati sono nel caso in cui li sto utilizzando come filtri quindi non li sbianco
//			else if(!"".equals(codUOCdR) && !"".equals(desUOCdR)) {
//				mDynamicForm.setValue("codUnitaOrgCdR", "");
//				mDynamicForm.setValue("desUnitaOrgCdR", "");			
//			}
		} 		
		// se non sono entrambi valorizzati sono nel caso in cui li sto utilizzando come filtri quindi non li sbianco
//		else if(!"".equals(codUOCdR) && !"".equals(desUOCdR)) {
//			mDynamicForm.setValue("codUnitaOrgCdR", "");
//			mDynamicForm.setValue("desUnitaOrgCdR", "");
//		}		
	}
	
	@Override
	public void editNewRecord() {
		super.editNewRecord();
		reloadSelectItem(capitolo, false);
	}
	
	@Override
	public void editRecord(Record record) {
		// flgCorrelata nel dettaglio lo chiamo in un altro modo  perchè qui è un boolean mentre in lista una stringa con valori 1/0
		record.setAttribute("isCorrelata", record.getAttribute("flgCorrelata") != null && "1".equals(record.getAttribute("flgCorrelata")));
		// specifiche nel dettaglio lo chiamo in un altro modo (listaSpecifiche) perchè qui è un array mentre in lista una stringa
		if(record.getAttribute("specifiche") != null && !"".equals(record.getAttribute("specifiche"))) {
			record.setAttribute("listaSpecifiche", ((String) record.getAttribute("specifiche")).split(","));			
		} else {
			record.setAttribute("listaSpecifiche", new String[0]);
		}
		super.editRecord(record);
	}
	
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("flgCorrelata", isCorrelata.getValueAsBoolean() != null && isCorrelata.getValueAsBoolean() ? "1" : "0");
		// Devo settare le date nel formato DD/MM/YYYY
		lRecordToSave.setAttribute("dataEsigibilitaDa", dataEsigibilitaDa.getValueAsDate() != null ? DateUtil.format(dataEsigibilitaDa.getValueAsDate()) : null);
		lRecordToSave.setAttribute("dataEsigibilitaA", dataEsigibilitaA.getValueAsDate() != null ? DateUtil.format(dataEsigibilitaA.getValueAsDate()) : null);
		lRecordToSave.setAttribute("dataScadenzaEntrata", dataScadenzaEntrata.getValueAsDate() != null ? DateUtil.format(dataScadenzaEntrata.getValueAsDate()) : null);
		lRecordToSave.setAttribute("specifiche", listaSpecifiche.getValueAsString() != null ? listaSpecifiche.getValueAsString() : null);
		return lRecordToSave;
	}
	
}