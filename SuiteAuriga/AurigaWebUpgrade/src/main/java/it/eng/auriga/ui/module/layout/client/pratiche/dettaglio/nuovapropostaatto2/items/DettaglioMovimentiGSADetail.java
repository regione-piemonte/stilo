/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.DateUtil;
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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.protocollazione.SelezionaUOItem;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class DettaglioMovimentiGSADetail extends CustomDetail {
	
	protected ListaMovimentiGSAItem gridItem;
	
	protected TabSet tabSet;
	
	protected Tab tabDatiPrincipali;
	protected Tab tabDatiGsa;
	
	protected DynamicForm mDynamicForm;
	
	protected FilteredSelectItemWithDisplay codTipoMovimento;
	protected HiddenItem desTipoMovimento;
	protected SelectItem flgEntrataUscita;
	protected ExtendedNumericItem numeroCapitolo;
	protected NumericItem numeroArticolo;
	protected SelezionaUOItem listaStrutturaCompetente;	
	protected NumericItem numeroMovimento;
	protected AnnoItem annoMovimento;
	protected TextAreaItem descrizioneMovimento;	
	protected NumericItem numeroSub;
	protected AnnoItem annoSub;
	protected NumericItem numeroModifica;	
	protected AnnoItem annoModifica;	
	protected DateItem dataInserimento;
	protected DateItem dataRegistrazione;
	protected ExtendedNumericItem importo;		
	protected TextItem codiceCIG;
	protected TextItem codiceCUP;
	protected ExtendedTextItem codiceSoggetto;
	
	protected DynamicForm mDynamicFormGsa;
	
	protected DatiGSAItem listaDatiGsaItem;
	
	public DettaglioMovimentiGSADetail(String nomeEntita, final ListaMovimentiGSAItem gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		mDynamicForm.setTabSet(tabSet);
		mDynamicForm.setTabID("HEADER");

		SelectGWTRestDataSource tipoMovimentoDS = new SelectGWTRestDataSource("LoadComboTipoMovimentoGSADataSource", "key", FieldType.TEXT, new String[] { "key", "value" }, true);
		
		codTipoMovimento = new FilteredSelectItemWithDisplay("codTipoMovimento", "Tipo movimento", tipoMovimentoDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid pickListProperties = super.builPickListProperties();	
				pickListProperties.setShowFilterEditor(false); 
				/*
				pickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource tipoMovimentoDS = (GWTRestDataSource) codTipoMovimento.getOptionDataSource();		
//						tipoMovimentoDS.addParam("", ...);
						codTipoMovimento.setOptionDataSource(tipoMovimentoDS);
						codTipoMovimento.invalidateDisplayValueCache();
					}
				});
				*/
				return pickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				desTipoMovimento.setValue(record.getAttribute("value"));
			}					
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				codTipoMovimento.setValue("");
				desTipoMovimento.setValue("");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					codTipoMovimento.setValue("");
					desTipoMovimento.setValue("");
				}
            }
		};   
		codTipoMovimento.setStartRow(true);
		codTipoMovimento.setWidth(630);
		codTipoMovimento.setColSpan(17);
		codTipoMovimento.setEmptyPickListMessage("Nessun record trovato o si è verificato un errore durante la chiamata ai servizi di AMCO");
		codTipoMovimento.setPickListWidth(630);
		codTipoMovimento.setValueField("key");
//		codTipoMovimento.setDisplayField("value");
		ListGridField nomeField = new ListGridField("key", "Nome");
		nomeField.setWidth(200);
		ListGridField descrizioneField = new ListGridField("value", "Descrizione");
		descrizioneField.setWidth("*");
		codTipoMovimento.setPickListFields(nomeField, descrizioneField);		
		codTipoMovimento.setAutoFetchData(false);
		codTipoMovimento.setFetchMissingValues(true);
		codTipoMovimento.setAlwaysFetchMissingValues(true);
		codTipoMovimento.setCachePickListResults(false);
		codTipoMovimento.setRequired(true);
		codTipoMovimento.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				reloadDatiGSA();
			}
		});
		
		desTipoMovimento = new HiddenItem("desTipoMovimento");
		
		flgEntrataUscita = new SelectItem("flgEntrataUscita", "Entrata/Uscita");
		flgEntrataUscita.setStartRow(true);
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", "Entrata");
		flgEntrataUscitaValueMap.put("U", "Uscita");		
		flgEntrataUscita.setValueMap(flgEntrataUscitaValueMap);
//		flgEntrataUscita.setDefaultValue("U");
//		flgEntrataUscita.setAllowEmptyValue(false);
		flgEntrataUscita.setAllowEmptyValue(true);
		flgEntrataUscita.setWidth(150);
		flgEntrataUscita.setColSpan(1);
		flgEntrataUscita.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(numeroCapitolo.getValueAsString() != null && !"".equals(numeroCapitolo.getValueAsString())) {
					flgEntrataUscita.setAttribute("obbligatorio", true);
					flgEntrataUscita.setTitle(FrontendUtil.getRequiredFormItemTitle("Entrata/Uscita"));
				} else {					
					flgEntrataUscita.setAttribute("obbligatorio", false);
					flgEntrataUscita.setTitle("Entrata/Uscita");					
				}				
				return true;
			}
		});
		flgEntrataUscita.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				return numeroCapitolo.getValueAsString() != null && !"".equals(numeroCapitolo.getValueAsString());
			}
		}));
		flgEntrataUscita.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
//				reloadDatiGSA();
				mDynamicForm.redraw();
			}
		});
		
		numeroCapitolo = new ExtendedNumericItem("numeroCapitolo", "Cap.", false);
		numeroCapitolo.setStartRow(true);
		numeroCapitolo.setColSpan(1);
		numeroCapitolo.setWidth(150);
		numeroCapitolo.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
//				reloadDatiGSA();
				mDynamicForm.redraw();
			}
		});
		
		numeroArticolo = new NumericItem("numeroArticolo", "Art.");	
		numeroArticolo.setColSpan(1);
		numeroArticolo.setWidth(150);	
		
		listaStrutturaCompetente = new SelezionaUOItem() {
			
			@Override
			public int getSelectItemOrganigrammaWidth() {
				return 650;
			}
			
			@Override
			public boolean skipValidation() {
				if(gridItem.showStrutturaCompetente()) {
					return super.skipValidation();
				}
				return true;
			}
			
//			@Override
//			protected VLayout creaVLayout() {
//				VLayout lVLayout = super.creaVLayout();
//				lVLayout.setWidth100();
//				lVLayout.setPadding(11);
//				lVLayout.setMargin(4);
//				lVLayout.setIsGroup(true);
//				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
//				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">Struttura competente</span>");				
//				return lVLayout;
//			}
			
//			@Override
//			public Boolean getShowRemoveButton() {
//				return true;
//			}
			
			@Override
			public void manageChangedUoSelezionata() {						
				
			}					
		};
		listaStrutturaCompetente.setName("listaStrutturaCompetente");
		listaStrutturaCompetente.setTitle("Struttura competente");		
//		listaStrutturaCompetente.setShowTitle(false);
		listaStrutturaCompetente.setColSpan(18);
		listaStrutturaCompetente.setStartRow(true);
		listaStrutturaCompetente.setNotReplicable(true);
		listaStrutturaCompetente.setAttribute("obbligatorio", true);
		listaStrutturaCompetente.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return gridItem.showStrutturaCompetente();
			}
		});
		
		numeroMovimento = new NumericItem("numeroMovimento", "N° imp./acc.", false);
		numeroMovimento.setStartRow(true);
		numeroMovimento.setWidth(150);
		numeroMovimento.setColSpan(1);
		
		annoMovimento = new AnnoItem("annoMovimento", "/ Anno");
		annoMovimento.setColSpan(1);
		
		descrizioneMovimento = new TextAreaItem("descrizioneMovimento", "Descrizione imp./acc.");
		descrizioneMovimento.setWidth(630);
		descrizioneMovimento.setHeight(40);
		descrizioneMovimento.setColSpan(17);
		descrizioneMovimento.setStartRow(true);

		numeroSub = new NumericItem("numeroSub", "N° sub", false);
		numeroSub.setStartRow(true);
		numeroSub.setWidth(150);
		numeroSub.setColSpan(1);
		
		annoSub = new AnnoItem("annoSub", "/ Anno");
		annoSub.setColSpan(1);
		
		numeroModifica = new NumericItem("numeroModifica", "N° modifica", false);
		numeroModifica.setStartRow(true);
		numeroModifica.setWidth(150);
		numeroModifica.setColSpan(1);
		
		annoModifica = new AnnoItem("annoModifica", "/ Anno");
		annoModifica.setColSpan(1);
		
		dataInserimento = new DateItem("dataInserimento", "Data inserimento");
		dataInserimento.setStartRow(true);
		dataInserimento.setWidth(150);
		dataInserimento.setColSpan(1);
		
		dataRegistrazione = new DateItem("dataRegistrazione", "Data registrazione");
		dataRegistrazione.setStartRow(true);
		dataRegistrazione.setWidth(150);
		dataRegistrazione.setColSpan(1);
		
		importo = new ExtendedNumericItem("importo", "Importo (&euro;)");
		importo.setKeyPressFilter("[0-9.,]");
		importo.setStartRow(true);
		importo.setWidth(150);
		importo.setColSpan(1);		
		importo.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importo.setValue(NumberFormatUtility.getFormattedValue(importo.getValueAsString()));
				return true;
			}
		});
		RegExpValidator importoPrecisionValidator = new RegExpValidator();
		importoPrecisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
		importoPrecisionValidator.setErrorMessage("Valore non valido o superato il limite di 2 cifre decimali");
		CustomValidator importoMaggioreOUgualeDiZeroValidator = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				// se l'importo è vuoto, ma non è obbligatorio, la validazione deve andare a buon fine
				if(value == null || "".equals(value)) {
					return true;
				}
				String pattern = "#,##0.00";
				double importo = 0;
				if(value != null && !"".equals(value)) {
					importo = new Double(NumberFormat.getFormat(pattern).parse((String) value)).doubleValue();			
				}
				return importo >= 0;
			}
		};
		importoMaggioreOUgualeDiZeroValidator.setErrorMessage("Valore non valido: l'importo deve essere maggiore o uguale di zero");
		importo.setValidators(importoPrecisionValidator, importoMaggioreOUgualeDiZeroValidator);
		importo.addChangedBlurHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {				
				importo.setValue(NumberFormatUtility.getFormattedValue((String) event.getValue()));
			}
		});
		
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
		codiceCIG.setWidth(150);
		codiceCIG.setColSpan(1);
		codiceCIG.setLength(10);
		codiceCIG.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCIG.setValidators(codiceCIGLengthValidator);			
	
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
		codiceCUP.setWidth(150);
		codiceCUP.setColSpan(1);
		codiceCUP.setLength(15);
		codiceCUP.setInputTransformer(new FormItemInputTransformer() {
			
			@Override
			public Object transformInput(DynamicForm form, FormItem item, Object value, Object oldValue) {
				return value != null ? ((String)value).trim().replaceAll(" ", "") : null;
			}
		});
		codiceCUP.setValidators(codiceCUPLengthValidator);
		
		codiceSoggetto = new ExtendedTextItem("codiceSoggetto", "Cod. soggetto");
		codiceSoggetto.setStartRow(true);
		codiceSoggetto.setWidth(150);
		codiceSoggetto.setColSpan(1);
		codiceSoggetto.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
//				reloadDatiGSA();
			}
		});
		
		mDynamicForm.setFields(
			codTipoMovimento,
			desTipoMovimento,
			flgEntrataUscita,
			numeroCapitolo,
			numeroArticolo,
			listaStrutturaCompetente,
			numeroMovimento,
			annoMovimento,
			descrizioneMovimento,	
			numeroSub,
			annoSub,
			numeroModifica,	
			annoModifica,	
			dataInserimento,
			dataRegistrazione,
			importo,		
			codiceCIG,
			codiceCUP,
			codiceSoggetto
		);
		
		mDynamicFormGsa = new DynamicForm();
		mDynamicFormGsa.setWidth100();
		mDynamicFormGsa.setNumCols(18);
		mDynamicFormGsa.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicFormGsa.setValuesManager(vm);
		mDynamicFormGsa.setWrapItemTitles(false);
//		mDynamicFormGsa.setCellBorder(1);
		mDynamicFormGsa.setTabSet(tabSet);
		mDynamicFormGsa.setTabID("DATI_GSA");

		listaDatiGsaItem = new DatiGSAItem() {
			
			@Override
			public String getImportoDefaultValue() {
				return (String) importo.getValue();
			}
			
			@Override
			public Record getRecordFiltriContoPrimaNota() {
				// devo passare solo il tipoMovimento quindi commento gli altri filtri
				// se rimetto i filtri devo decommentare anche la chiamata alla reloadDatiGSA() nell'evento changed del relativo item
				Record lRecord = new Record();
				lRecord.setAttribute("nomeTipoDoc", codTipoMovimento.getValueAsString());
//				lRecord.setAttribute("codiceBP", codiceSoggetto.getValueAsString());
//				lRecord.setAttribute("codiceCapitolo", numeroCapitolo.getValueAsString());
//				lRecord.setAttribute("entrataUscita", flgEntrataUscita.getValueAsString());
				return lRecord;
			}
			
			@Override
			public boolean isFromDettaglioMovimentoGSA() {
				return true;
			}
		};
		listaDatiGsaItem.setName("listaDatiGsa");
		listaDatiGsaItem.setShowTitle(false);
		listaDatiGsaItem.setColSpan(18);
		
		mDynamicFormGsa.setFields(listaDatiGsaItem);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		createTabSet();

		setMembers(tabSet);
	}
	
	public void reloadDatiGSA() {
		listaDatiGsaItem.reloadSelectContoPrimaNota();
	}
	
	protected void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		tabDatiPrincipali = new Tab("<b>" + getTitleTabDatiPrincipali() + "</b>");
		tabDatiPrincipali.setAttribute("tabID", "HEADER");
		tabDatiPrincipali.setPrompt(getTitleTabDatiPrincipali());
		tabDatiPrincipali.setPane(createLayoutTab(getLayoutTabDatiPrincipali(), lVLayoutSpacer));
		tabSet.addTab(tabDatiPrincipali);

		tabDatiGsa = new Tab("<b>" + getTitleTabDatiGsa() + "</b>");
		tabDatiGsa.setAttribute("tabID", "DATI_GSA");
		tabDatiGsa.setPrompt(getTitleTabDatiGsa());
		tabDatiGsa.setPane(createLayoutTab(getLayoutTabDatiGsa(), lVLayoutSpacer));
		tabSet.addTab(tabDatiGsa);
	}
	
	public String getTitleTabDatiPrincipali() {
		return "Dati principali";
	}
	
	public VLayout getLayoutTabDatiPrincipali() {
		
        VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
				
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		lVLayout.addMember(mDynamicForm);	
		
		return lVLayout;
	}
	
	protected boolean isDatiRilevantiGsa() {
		return gridItem != null ? gridItem.isDatiRilevantiGsa() : false;
	}
	
	protected boolean showTabDatiGsa() {
		return true;
	}
	
	public String getTitleTabDatiGsa() {
		return "Dati GSA";
	}
	
	public VLayout getLayoutTabDatiGsa() {
		
        VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
				
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);

		lVLayout.addMember(mDynamicFormGsa);
		
		return lVLayout;
	}
	
	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
	
	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
	@Override
	public void editNewRecord() {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		super.editNewRecord();
		showHideTabs();
	}
		
	@Override
	public void editRecord(Record record) {
		vm.clearErrors(true);
		clearTabErrors(tabSet);
		RecordList listaStrutturaCompetente = new RecordList();
		String idUoStrutturaCompetente = record.getAttribute("idUoStrutturaCompetente");
		if(idUoStrutturaCompetente != null && !"".equals(idUoStrutturaCompetente)) {
			String codRapidoStrutturaCompetente = record.getAttribute("codRapidoStrutturaCompetente");
			String desStrutturaCompetente = record.getAttribute("desStrutturaCompetente");
			Record lRecordStrutturaCompetente = new Record();
			lRecordStrutturaCompetente.setAttribute("idUo", idUoStrutturaCompetente);
			lRecordStrutturaCompetente.setAttribute("codRapido", codRapidoStrutturaCompetente);
			lRecordStrutturaCompetente.setAttribute("descrizione", desStrutturaCompetente);
			lRecordStrutturaCompetente.setAttribute("organigramma", "UO" + idUoStrutturaCompetente);
			listaStrutturaCompetente.add(lRecordStrutturaCompetente);			
		} else {
			listaStrutturaCompetente.add(new Record());
		}
		record.setAttribute("listaStrutturaCompetente", listaStrutturaCompetente);
		String datiGsa = record.getAttribute("datiGsa");
		if(datiGsa != null) {
			RecordList listaDatiGsa = new RecordList();
			StringSplitterClient str = new StringSplitterClient(datiGsa, "|**|");
			for(int r = 0 ; r < str.getTokens().length; r++) {
				String riga = str.getTokens()[r];
				if(riga != null && !"".equals(riga)) {
					StringSplitterClient stc = new StringSplitterClient(riga, "|*|");
					Record lRecordDatiGsa = new Record();
					lRecordDatiGsa.setAttribute("raggruppamento", stc.getTokens()[0]);
					lRecordDatiGsa.setAttribute("progressivo", stc.getTokens()[1]);
					lRecordDatiGsa.setAttribute("flgContoDebitoCredito", stc.getTokens()[2]);
					lRecordDatiGsa.setAttribute("codContoPrimaNota", stc.getTokens()[3]);
					lRecordDatiGsa.setAttribute("desContoPrimaNota", stc.getTokens()[4]);
					lRecordDatiGsa.setAttribute("importo", stc.getTokens()[5]);
					lRecordDatiGsa.setAttribute("flgDareAvere", stc.getTokens()[6]);
					lRecordDatiGsa.setAttribute("dataCompetenzaDa", stc.getTokens()[7] != null ? DateUtil.parseInput(stc.getTokens()[7]) : null);
					lRecordDatiGsa.setAttribute("dataCompetenzaA", stc.getTokens()[8] != null ? DateUtil.parseInput(stc.getTokens()[8]) : null);
					listaDatiGsa.add(lRecordDatiGsa);
				}
			}
			record.setAttribute("listaDatiGsa", listaDatiGsa);
		}
		super.editRecord(record);
		if(codTipoMovimento != null) {
			if (record.getAttribute("codTipoMovimento") != null && !"".equals(record.getAttributeAsString("codTipoMovimento")) &&
				record.getAttribute("desTipoMovimento") != null && !"".equals(record.getAttributeAsString("desTipoMovimento"))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(record.getAttribute("codTipoMovimento"), record.getAttribute("codTipoMovimento") + " - " + record.getAttribute("desTipoMovimento"));
				codTipoMovimento.setValueMap(valueMap);
			}
		}
		showHideTabs();
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		
		// Devo settare le date nel formato DD/MM/YYYY
		lRecordToSave.setAttribute("dataInserimento", dataInserimento.getValueAsDate() != null ? DateUtil.format(dataInserimento.getValueAsDate()) : null);
		lRecordToSave.setAttribute("dataRegistrazione", dataRegistrazione.getValueAsDate() != null ? DateUtil.format(dataRegistrazione.getValueAsDate()) : null);		
		
		RecordList listaStrutturaCompetente = lRecordToSave.getAttributeAsRecordList("listaStrutturaCompetente");
		if(listaStrutturaCompetente != null && listaStrutturaCompetente.getLength() > 0) {
			lRecordToSave.setAttribute("idUoStrutturaCompetente", listaStrutturaCompetente.get(0).getAttribute("idUo"));
			lRecordToSave.setAttribute("codRapidoStrutturaCompetente", listaStrutturaCompetente.get(0).getAttribute("codRapido"));
			lRecordToSave.setAttribute("desStrutturaCompetente", listaStrutturaCompetente.get(0).getAttribute("descrizione"));
		}		
		
		RecordList listaDatiGsa = lRecordToSave.getAttributeAsRecordList("listaDatiGsa");
		if(listaDatiGsa != null) {
			String datiGsa = "";
			for(int i = 0; i < listaDatiGsa.getLength(); i++) {
				Record lRecordDatiGsa = listaDatiGsa.get(i);
				datiGsa += lRecordDatiGsa.getAttribute("raggruppamento") != null ? lRecordDatiGsa.getAttribute("raggruppamento") : "";
				datiGsa += "|*|" + (lRecordDatiGsa.getAttribute("progressivo") != null ? lRecordDatiGsa.getAttribute("progressivo") : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttribute("flgContoDebitoCredito") != null ? lRecordDatiGsa.getAttribute("flgContoDebitoCredito") : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttribute("codContoPrimaNota") != null ? lRecordDatiGsa.getAttribute("codContoPrimaNota") : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttribute("desContoPrimaNota") != null ? lRecordDatiGsa.getAttribute("desContoPrimaNota") : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttribute("importo") != null ? lRecordDatiGsa.getAttribute("importo") : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttribute("flgDareAvere") != null ? lRecordDatiGsa.getAttribute("flgDareAvere") : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttributeAsDate("dataCompetenzaDa") != null ? DateUtil.format(lRecordDatiGsa.getAttributeAsDate("dataCompetenzaDa")) : "");
				datiGsa += "|*|" + (lRecordDatiGsa.getAttributeAsDate("dataCompetenzaA") != null ? DateUtil.format(lRecordDatiGsa.getAttributeAsDate("dataCompetenzaA")) : "");
				datiGsa += "|**|";
			}
			lRecordToSave.setAttribute("datiGsa", datiGsa);		
		}
		
		return lRecordToSave;
	}
	
	public void showHideTabs() {
		if(tabSet != null && tabDatiGsa != null) {
			if(!showTabDatiGsa()) {
				tabSet.removeTab(tabDatiGsa);
			}
		}		
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
	}
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate();
		if(showTabDatiGsa()) {
			if(listaDatiGsaItem != null) {
				if(!listaDatiGsaItem.validateImportiDareAvereGruppi()) {
					valid = false;
				}
			}
		}
		return valid;
	}
	
}