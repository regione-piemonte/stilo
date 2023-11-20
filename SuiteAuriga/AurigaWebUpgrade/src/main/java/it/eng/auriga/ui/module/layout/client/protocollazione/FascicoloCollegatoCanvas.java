/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;

import it.eng.auriga.ui.module.layout.client.protocollazione.ClassificaFascicoloCollegatoValidator.Tipo;
import it.eng.auriga.ui.module.layout.client.titolario.LookupTitolarioPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgStaticItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class FascicoloCollegatoCanvas extends ReplicableCanvas{
	
	// FilteredSelectItemWithDisplay
	private FilteredSelectItemWithDisplay classificheItem;

	// TitleItem
	private TitleItem fascicolazioneTitleItem;
	private TitleItem classificazioneTitleItem;
	
	// ExtendedTextItem
	private ExtendedTextItem nomeFascicoloItem;
	private ExtendedTextItem nroFascicoloItem;
	private ExtendedTextItem nroSottofascicoloItem;
	private ExtendedTextItem nroInsertoItem;
	private ExtendedTextItem codiceItem;
	private ExtendedTextItem indiceItem;
	
	// TextAreaItem
	private TextAreaItem motiviItem;
	
	// ImgButtonItem
	private ImgButtonItem lookupArchivioButton;
	private ImgButtonItem lookupTitolarioButton;
	
	// AnnoItem
	private AnnoItem annoFascicoloItem;
	
	// HiddenItem
	private HiddenItem idFolderFascicoloItem;
	private HiddenItem idClassificaItem;
	private HiddenItem provCIClassifItem;
	private HiddenItem descrizioneClassificaItem;
	private HiddenItem livelloRiservatezzaItem;
	private HiddenItem nomeFascicoloPrecItem;
	private HiddenItem annoFascicoloPrecItem;
	private HiddenItem nroFascicoloPrecItem;
	private HiddenItem nroSottofascicoloPrecItem;
	private HiddenItem nroInsertoPrecItem;
	private HiddenItem flgClassificaAttivaItem;
	private HiddenItem setted;
	private HiddenItem correctValue;
	private HiddenItem idFolderCollegatoHiddenItem;
	private HiddenItem datiCollegamentoHiddenItem;
	private HiddenItem pathFolderHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;
	
	public FascicoloCollegatoCanvas(FascicoliCollegatiItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setValidateOnChange(false);
		mDynamicForm.setNumCols(20);
		mDynamicForm.setColWidths("50", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*");
		
		idFolderCollegatoHiddenItem= new HiddenItem("idFolderCollegato");
		pathFolderHiddenItem       = new HiddenItem("pathFolder");		
		datiCollegamentoHiddenItem = new HiddenItem("datiCollegamento");		
		idClassificaItem           = new HiddenItem("idClassifica");
		provCIClassifItem          = new HiddenItem("provCIClassif");
		descrizioneClassificaItem  = new HiddenItem("descrizioneClassifica");
		livelloRiservatezzaItem    = new HiddenItem("livelloRiservatezza");
		flgClassificaAttivaItem    = new HiddenItem("flgAttiva");
		idFolderFascicoloItem      = new HiddenItem("idFolderFascicolo");
		nomeFascicoloPrecItem      = new HiddenItem("nomeFascicoloPrec");
		annoFascicoloPrecItem      = new HiddenItem("annoFascicoloPrec");
		nroFascicoloPrecItem       = new HiddenItem("nroFascicoloPrec");
		nroSottofascicoloPrecItem  = new HiddenItem("nroSottofascicoloPrec");
		nroInsertoPrecItem         = new HiddenItem("nroInsertoPrec");
		correctValue               = new HiddenItem("correctValue"); correctValue.setValue(false);
		setted                     = new HiddenItem("setted"); setted.setDefaultValue(false);

		// ********************************************************************
		// CLASSIFICAZIONE
		// ********************************************************************
		classificazioneTitleItem = new TitleItem(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_title());
		classificazioneTitleItem.setName("classifica");
		classificazioneTitleItem.setValidators(new ClassificaRequiredValidator());

		// indice
		indiceItem = new ExtendedTextItem("indice", I18NUtil.getMessages().protocollazione_detail_indiceItem_title());
		indiceItem.setWidth(100);
		indiceItem.setColSpan(1);
		indiceItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				manageIndiceChange();
				isNotMoreValid();
			}
		});
		indiceItem.setValidators(new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (indiceItem.getValue() != null && !"".equals(indiceItem.getValueAsString().trim()) && classificheItem.getValue() == null) {
					return false;
				}
				return true;
			}
		});

		// BOTTONI : seleziona dal titolario
		lookupTitolarioButton = new ImgButtonItem("lookupTitolarioButton", "lookup/titolario.png",I18NUtil.getMessages().protocollazione_detail_lookupTitolarioButton_prompt());
		lookupTitolarioButton.setWidth(16);
		lookupTitolarioButton.setColSpan(1);
		lookupTitolarioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				ClassificaFascicoloLookupTitolario lookupTitolarioPopup = new ClassificaFascicoloLookupTitolario(mDynamicForm.getValuesAsRecord());
				lookupTitolarioPopup.show();
			}
		});
		
		// lista classifiche assegnabili
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboClassificaDataSource", "idClassifica", FieldType.TEXT, new String[] { "descrizione" }, true);
		classificheItem = new FilteredSelectItemWithDisplay("classifiche", lGwtRestDataSource) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
				classificheDS.addParam("descrizione", record.getAttributeAsString("descrizione"));
				classificheItem.setOptionDataSource(classificheDS);

				mDynamicForm.setValue("idClassifica", record.getAttributeAsString("idClassifica"));
				mDynamicForm.setValue("provCIClassif", record.getAttributeAsString("provCI"));
				mDynamicForm.setValue("descrizioneClassifica", record.getAttributeAsString("descrizione"));

				mDynamicForm.setValue("indice", record.getAttributeAsString("indice"));
				mDynamicForm.setValue("flgAttiva", record.getAttributeAsString("flgAttiva"));
				mDynamicForm.clearFieldErrors("indice", true);
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
				classificheDS.addParam("descrizione", null);
				classificheItem.setOptionDataSource(classificheDS);

				mDynamicForm.setValue("idClassifica", "");
				mDynamicForm.setValue("provCIClassif", "");
				mDynamicForm.setValue("descrizioneClassifica", "");

				mDynamicForm.setValue("classifiche", "");
				mDynamicForm.setValue("indice", "");
				mDynamicForm.setValue("flgAttiva", "");
				mDynamicForm.clearFieldErrors("indice", true);
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
					classificheDS.addParam("descrizione", null);
					classificheItem.setOptionDataSource(classificheDS);

					mDynamicForm.setValue("idClassifica", "");
					mDynamicForm.setValue("provCIClassif", "");
					mDynamicForm.setValue("descrizioneClassifica", "");

					mDynamicForm.setValue("indice", "");
					mDynamicForm.setValue("flgAttiva", "");
					mDynamicForm.clearFieldErrors("indice", true);

				}
			}
		};
		classificheItem.setAutoFetchData(false);
		classificheItem.setFetchMissingValues(true);
		
		ListGridField indiceField = new ListGridField("indice", "Indice"); indiceField.setWidth(100);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		ListGridField descrizioneEstesaField = new ListGridField("descrizioneEstesa", "Descr. estesa"); descrizioneEstesaField.setHidden(true);
		ListGridField flgAttivaField = new ListGridField("flgAttiva"); flgAttivaField.setHidden(true);
		ListGridField iconaFlgClassificaChiusaField = new ListGridField("iconaFlgClassificaChiusa");
		iconaFlgClassificaChiusaField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		iconaFlgClassificaChiusaField.setAlign(Alignment.CENTER);
		iconaFlgClassificaChiusaField.setWrap(false);
		iconaFlgClassificaChiusaField.setWidth(30);
		iconaFlgClassificaChiusaField.setAttribute("custom", true);
		iconaFlgClassificaChiusaField.setShowHover(true);
		iconaFlgClassificaChiusaField.setCanSort(false);
		iconaFlgClassificaChiusaField.setCanFilter(false);
		iconaFlgClassificaChiusaField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgAttiva = (String) record.getAttribute("flgAttiva");
				if (flgAttiva != null && "0".equals(flgAttiva)) {
					return "<div align=\"center\"><img src=\"images/titolario/tipo/classificaChiusa.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
				}
				return null;
			}
		});
		iconaFlgClassificaChiusaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgAttiva = (String) record.getAttribute("flgAttiva");
				if (flgAttiva != null && "0".equals(flgAttiva)) {		
						return "Classifica chiusa";	
				}
				return null;
			}
		});
		classificheItem.setPickListFields(flgAttivaField, iconaFlgClassificaChiusaField, indiceField, descrizioneField, descrizioneEstesaField);
		classificheItem.setEmptyPickListMessage(I18NUtil.getMessages().protocollazione_detail_classificazioneItem_noSearchOrEmptyMessage());
		classificheItem.setValueField("idClassifica");
		classificheItem.setOptionDataSource(lGwtRestDataSource);
		classificheItem.setShowTitle(false);
		classificheItem.setClearable(true);
		classificheItem.setShowIcons(true);
		classificheItem.setWidth(400);
		classificheItem.setColSpan(5);
		classificheItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		classificheItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				isNotMoreValid();
			}
		});
		
		ImgStaticItem iconaFlgClassificaChiusaItem = new ImgStaticItem("iconaFlgClassificaChiusa", "titolario/tipo/classificaChiusa.png", "Classifica chiusa");
		iconaFlgClassificaChiusaItem.setWidth(16);
		iconaFlgClassificaChiusaItem.setColSpan(1);
		iconaFlgClassificaChiusaItem.setCellStyle(it.eng.utility.Styles.formCell);
		iconaFlgClassificaChiusaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isClassificaChiusa();
			}
		});
		
		// ********************************************************************
		// FASCICOLO
		// ********************************************************************		
		fascicolazioneTitleItem = new TitleItem(I18NUtil.getMessages().protocollazione_detail_fascicolazioneItem_title());
		fascicolazioneTitleItem.setName("fascicolo");
		fascicolazioneTitleItem.setValidators(new FascicoloRequiredValidator(), new FascicoloValidator());
		
		// codice
		codiceItem = new ExtendedTextItem("codice", I18NUtil.getMessages().protocollazione_detail_codiceItem_title());
		codiceItem.setEndRow(false);
		codiceItem.setWidth(100);
		//codiceItem.setColSpan(2);
		codiceItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				isNotMoreValid();
				manageBlur();
			}
		});
		codiceItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI");
			}
		});

		// nome
		nomeFascicoloItem = new ExtendedTextItem("nomeFascicolo", I18NUtil.getMessages().protocollazione_detail_nomeFascicoloItem_title());
		nomeFascicoloItem.setEndRow(false);
		nomeFascicoloItem.setWidth(346);
		nomeFascicoloItem.setColSpan(4);
		nomeFascicoloItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				isNotMoreValid();
				manageBlur();
			}
		});

		// anno
		annoFascicoloItem = new AnnoItem("annoFascicolo", I18NUtil.getMessages().protocollazione_detail_annoFascicoloItem_title());
		annoFascicoloItem.setWidth(60);
		annoFascicoloItem.setEndRow(false);
		annoFascicoloItem.setColSpan(1);
		annoFascicoloItem.setValidators(new ClassificaFascicoloCollegatoValidator(Tipo.ANNO));
		annoFascicoloItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				annoFascicoloItem.validate();
				nroFascicoloItem.validate();
				isNotMoreValid();
				manageBlur();
			}
		});

		// numero fascicolo
		nroFascicoloItem = new ExtendedTextItem("nroFascicolo", I18NUtil.getMessages().protocollazione_detail_nroFascicoloItem_title());
		nroFascicoloItem.setWidth(60);
		nroFascicoloItem.setKeyPressFilter("[0-9.]");
		nroFascicoloItem.setTextAlign(Alignment.RIGHT);
		nroFascicoloItem.setLength(10);
		nroFascicoloItem.setEndRow(false);
		nroFascicoloItem.setColSpan(1);
		nroFascicoloItem.setValidators(new ClassificaFascicoloCollegatoValidator(Tipo.NUMERO));
		nroFascicoloItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				nroFascicoloItem.validate();
				annoFascicoloItem.validate();
				isNotMoreValid();
				manageBlur();
			}
		});

		// numero sotto-fascicolo
		nroSottofascicoloItem = new ExtendedTextItem("nroSottofascicolo", I18NUtil.getMessages().protocollazione_detail_nroSottoFascicoloItem_title());
		nroSottofascicoloItem.setWidth(60);
		nroSottofascicoloItem.setKeyPressFilter("[0-9.]");
		nroSottofascicoloItem.setTextAlign(Alignment.RIGHT);
		nroSottofascicoloItem.setLength(5);
		nroSottofascicoloItem.setEndRow(false);
		nroSottofascicoloItem.setColSpan(1);
		ClassificaFascicoloCollegatoValidator lClassificaFascicoloValidatorSotto = new ClassificaFascicoloCollegatoValidator(Tipo.FASCICOLO);
		nroSottofascicoloItem.setValidators(lClassificaFascicoloValidatorSotto);
		nroSottofascicoloItem.setValidateOnChange(true);
		nroSottofascicoloItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				isNotMoreValid();
				manageBlur();
			}
		});		
		nroSottofascicoloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("CREAZIONE_SOTTOFASC");
			}
		});
		
		// numero inserto
		nroInsertoItem = new ExtendedTextItem("nroInserto", I18NUtil.getMessages().protocollazione_detail_nroInsertoItem_title());
		nroInsertoItem.setWidth(60);
		nroInsertoItem.setKeyPressFilter("[0-9.]");
		nroInsertoItem.setTextAlign(Alignment.RIGHT);
		nroInsertoItem.setLength(5);
		nroInsertoItem.setEndRow(false);
		nroInsertoItem.setColSpan(1);
		ClassificaFascicoloCollegatoValidator lClassificaFascicoloValidatorInserto = new ClassificaFascicoloCollegatoValidator(Tipo.FASCICOLO);
		nroInsertoItem.setValidators(lClassificaFascicoloValidatorInserto);
		nroInsertoItem.setValidateOnChange(true);
		nroInsertoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				isNotMoreValid();
				manageBlur();
			}
		});
		nroInsertoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return AurigaLayout.getParametroDBAsBoolean("CREAZIONE_INSERTI");				
			}
		});
		
		// BOTTONI : seleziona dall'archivio, nuovo
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", I18NUtil.getMessages().protocollazione_detail_lookupArchivioButton_prompt());
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idClassifica = (String) classificheItem.getValue();
				if (idClassifica != null && !"".equals(idClassifica)) {
					Record record = new Record();
					record.setAttribute("idClassifica", idClassifica);
					new GWTRestDataSource("ArchivioTreeDatasource", true, "idNode", FieldType.TEXT).performCustomOperation("getIdNodoClassifica", record,
							new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										String idRootNode = record.getAttributeAsString("idNode");
										ClassificaFascicoloLookupArchivio lookupArchivioPopup = new ClassificaFascicoloLookupArchivio(mDynamicForm.getValuesAsRecord(), idRootNode);
										lookupArchivioPopup.show();
									}
								}
							}, new DSRequest());
				} else {
					ClassificaFascicoloLookupArchivio lookupArchivioPopup = new ClassificaFascicoloLookupArchivio(mDynamicForm.getValuesAsRecord());
					lookupArchivioPopup.show();
				}
			}
		});

		
		// Motivi
		motiviItem = new TextAreaItem("motivi" , "Motivi collegamento");
		motiviItem.setTitleColSpan(3);
		motiviItem.setHeight(40);
		motiviItem.setWidth(510);
		motiviItem.setColSpan(15);

		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(4);
		spacerItem.setWidth(150);
		
		SpacerItem littleSpacerItem = new SpacerItem();
		littleSpacerItem.setColSpan(1);
		littleSpacerItem.setWidth(20);

		SpacerItem littleSpacer2Item = new SpacerItem();
		littleSpacer2Item.setColSpan(1);
		littleSpacer2Item.setWidth("*");

		
			mDynamicForm.setFields(classificazioneTitleItem, indiceItem,  lookupTitolarioButton,  classificheItem,   iconaFlgClassificaChiusaItem, 
				               fascicolazioneTitleItem,  codiceItem,  littleSpacer2Item,       nomeFascicoloItem, littleSpacerItem, annoFascicoloItem, nroFascicoloItem, nroSottofascicoloItem, nroInsertoItem, lookupArchivioButton, motiviItem, 
				               
				               // hidden
				               correctValue,
				               setted,
				               flgClassificaAttivaItem, 
				               nroInsertoPrecItem,  
				               nroSottofascicoloPrecItem, 
				               nroFascicoloPrecItem, 
				               annoFascicoloPrecItem, 
				               nomeFascicoloPrecItem,
				               livelloRiservatezzaItem, 
				               descrizioneClassificaItem,
				               provCIClassifItem, 
				               idClassificaItem, 
				               idFolderFascicoloItem,
				               idFolderCollegatoHiddenItem,
				       		   datiCollegamentoHiddenItem,
				       		   pathFolderHiddenItem
								);

		addChild(mDynamicForm);
		
	}	
	
	public class FascicoloCollegatoLookupArchivio extends LookupArchivioPopup {

		public FascicoloCollegatoLookupArchivio(Record record) {
			super(record, true);			
		}

		public FascicoloCollegatoLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);			
		}

		@Override
		public String getWindowTitle() {
			return "Seleziona fascicolo da collegare";
		}
		
		@Override
		public String getFinalita() {			
			return "COLLEGA_FASC";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);						
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}	

		@Override
		public void manageMultiLookupUndo(Record record) {	

		}			
	}
	
	protected void manageBlur() {
		manageBlur(false);
	}

	protected void manageBlur(final boolean forceToSetValues) {
		// Verifico le condizioni possibili per far scattare la ricerca
		String nomeFascicolo = mDynamicForm.getValueAsString("nomeFascicolo");
		String codice = mDynamicForm.getValueAsString("codice");
		String capofila = mDynamicForm.getValueAsString("capofila");
		String indice = mDynamicForm.getValueAsString("indice");
		String classifiche = mDynamicForm.getValueAsString("classifiche");
		String nroFascicolo = mDynamicForm.getValueAsString("nroFascicolo");
		String annoFascicolo = mDynamicForm.getValueAsString("annoFascicolo");
		if (isSetted(nomeFascicolo) || isSetted(codice) || isSetted(capofila)
				|| (isSetted(annoFascicolo) && isSetted(nroFascicolo) && (isSetted(classifiche) || isSetted(indice)))) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindFascTitolarioDatasource");
			Record lRecord = new Record();
			lRecord.setAttribute("nomeFascicolo", nomeFascicoloItem.getValue());
			lRecord.setAttribute("annoFascicolo", annoFascicoloItem.getValue());
			lRecord.setAttribute("nroFascicolo", nroFascicoloItem.getValue());
			lRecord.setAttribute("nroSottofascicolo", nroSottofascicoloItem.getValue());
			lRecord.setAttribute("nroInserto", nroInsertoItem.getValue());
			lRecord.setAttribute("idClassifica", classificheItem.getValue());
			lRecord.setAttribute("indiceClassifica", indiceItem.getValue());
			lRecord.setAttribute("nroSecondario", codice);
			lRecord.setAttribute("estremiDocCapofila", capofila);
			lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					String idFolderFascicolo = object.getAttribute("idFolderFascicolo");
					if (idFolderFascicolo != null && !idFolderFascicolo.equalsIgnoreCase("")) {
						String idFolderFascicoloOld = mDynamicForm.getValueAsString("idFolderFascicolo");
						if (forceToSetValues || idFolderFascicoloOld == null || !idFolderFascicolo.equals(idFolderFascicoloOld)) {
							setFormValuesFromRecordArchivio(object);
						}
						setIfNotNull("livelloRiservatezza", object, "livelloRiservatezza");
						setIfNotNull("idFolderFascicolo", object, "idFolderFascicolo");
						mDynamicForm.setValue("setted", true);
						fascicolazioneTitleItem.validate();
					} else {
						mDynamicForm.setValue("idFolderFascicolo", (String) null);
						mDynamicForm.setValue("setted", false);
						fascicolazioneTitleItem.validate();
					}
				}
			});
		} else {
			mDynamicForm.setValue("idFolderFascicolo", (String) null);
			fascicolazioneTitleItem.validate();
		}
	}

	private boolean isSetted(String value) {
		if (value != null && value.trim().length() > 0 && !value.trim().equals("")) {
			return true;
		}
		return false;
	}

	public void setFormValuesFromRecordTitolario(Record record) {
		mDynamicForm.clearErrors(true);
		String idClassifica = record.getAttribute("idClassificazione");
		if (idClassifica == null || "".equals(idClassifica)) {
			idClassifica = record.getAttribute("idFolder");
		}
		mDynamicForm.setValue("classifiche", idClassifica);
		mDynamicForm.setValue("indice", record.getAttribute("indice"));
		mDynamicForm.setValue("idClassifica", idClassifica);
		mDynamicForm.setValue("flgAttiva", record.getAttribute("flgAttiva"));
		manageIndiceChange();
		mDynamicForm.markForRedraw();
	}

	public void setFormValuesFromRecordArchivio(Record record) {
		mDynamicForm.clearErrors(true);
		setIfNotNull("classifiche", record, "idClassifica");
		setIfNotNull("idClassifica", record, "idClassifica");
		setIfNotNull("indice", record, "indiceClassifica");
		String idFolderFascicolo = record.getAttribute("idUdFolder");
		if (idFolderFascicolo == null || "".equals(idFolderFascicolo)) {
			idFolderFascicolo = record.getAttribute("idFolder");
		}
		setIfNotNull("idFolderFascicolo", idFolderFascicolo);
		setIfNotNull("nomeFascicolo", record, "nomeFascicolo");
		setIfNotNull("annoFascicolo", record, "annoFascicolo");
		setIfNotNull("nroFascicolo", record, "nroFascicolo");
		setIfNotNull("nroSottofascicolo", record, "nroSottofascicolo");
		setIfNotNull("nroInserto", record, "nroInserto");
		setIfNotNull("livelloRiservatezza", record, "livelloRiservatezza");
		String codice = record.getAttribute("nroSecondario");
		if (codice == null || "".equals(codice)) {
			codice = record.getAttribute("codice");
		}
		setIfNotNull("codice", codice);
		String capofila = record.getAttribute("estremiDocCapofila");
		if (capofila == null || "".equals(capofila)) {
			capofila = record.getAttribute("capofila");
		}
		setIfNotNull("capofila", capofila);
		manageIndiceChange();
		mDynamicForm.setValue("setted", true);
		mDynamicForm.markForRedraw();
	}

	private void setIfNotNull(String property, Record record, String attribute) {
		if (mDynamicForm.getValue(property) == null) {
			mDynamicForm.setValue(property, record.getAttribute(attribute));
		} else {
			if (!mDynamicForm.getValue(property).equals(record.getAttribute(attribute))) {
				mDynamicForm.setValue(property, record.getAttribute(attribute));
			}
		}
	}

	private void setIfNotNull(String property, String value) {
		if (mDynamicForm.getValue(property) == null) {
			mDynamicForm.setValue(property, value);
		} else {
			if (!mDynamicForm.getValue(property).equals(value)) {
				mDynamicForm.setValue(property, value);
			}
		}
	}

	@Override
	public void editRecord(Record record) {
		if (record.getAttribute("classifiche") != null && !"".equals(record.getAttributeAsString("classifiche"))) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(record.getAttribute("classifiche"), record.getAttribute("descrizioneClassifica"));
			classificheItem.setValueMap(valueMap);
		}

		SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
		if (record.getAttribute("classifiche") != null && !"".equals(record.getAttributeAsString("classifiche"))) {
			classificheDS.addParam("idClassifica", record.getAttributeAsString("classifiche"));
			classificheDS.addParam("descrizione", record.getAttributeAsString("descrizioneClassifica"));
		} else {
			classificheDS.addParam("idClassifica", null);
			classificheDS.addParam("descrizione", null);
		}
		classificheItem.setOptionDataSource(classificheDS);
		super.editRecord(record);
		if (record.getAttribute("idFolderFascicolo") != null && !"".equals(record.getAttribute("idFolderFascicolo"))) {
			mDynamicForm.setValue("setted", true);
		}
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}
	
	public class FascicoloRequiredValidator extends CustomValidator {

		public FascicoloRequiredValidator() {
			setErrorMessage("Fascicolo obbligatorio");
		}

		@Override
		protected boolean condition(Object value) {
				if (getFormItem() != null && getFormItem().getForm() != null) {
					DynamicForm lForm = getFormItem().getForm();
					String nroFascicolo = lForm.getField("nroFascicolo") != null && lForm.getField("nroFascicolo").getValue() != null ? (String) lForm.getField("nroFascicolo").getValue() : null;
					String annoFascicolo = lForm.getField("annoFascicolo") != null && lForm.getField("annoFascicolo").getValue() != null ? (String) lForm.getField("annoFascicolo").getValue() : null;
					String nroSottofascicolo = lForm.getField("nroSottofascicolo") != null && lForm.getField("nroSottofascicolo").getValue() != null ? (String) lForm.getField("nroSottofascicolo").getValue() : null;
					String nroInserto = lForm.getField("nroInserto") != null && lForm.getField("nroInserto").getValue() != null ? (String) lForm.getField("nroInserto").getValue() : null;
					String nomeFascicolo = lForm.getField("nomeFascicolo") != null && lForm.getField("nomeFascicolo").getValue() != null ? (String) lForm.getField("nomeFascicolo").getValue() : null;
					if (!isSetted(nroFascicolo) && !isSetted(annoFascicolo) && !isSetted(nroSottofascicolo)  && !isSetted(nroInserto) && !isSetted(nomeFascicolo)) {						
						return false;					
					}
				}
				return true;
		}
	}
	
	public class FascicoloValidator extends CustomValidator {

		public FascicoloValidator() {
			setErrorMessage("Fascicolo inesistente");
		}

		@Override
		protected boolean condition(Object value) {
			if (getFormItem() != null && getFormItem().getForm() != null) {
				DynamicForm lForm = getFormItem().getForm();
				String nroFascicolo = lForm.getField("nroFascicolo") != null && lForm.getField("nroFascicolo").getValue() != null ? (String) lForm.getField("nroFascicolo").getValue() : null;
				String annoFascicolo = lForm.getField("annoFascicolo") != null && lForm.getField("annoFascicolo").getValue() != null ? (String) lForm.getField("annoFascicolo").getValue() : null;
				String nroSottofascicolo = lForm.getField("nroSottofascicolo") != null && lForm.getField("nroSottofascicolo").getValue() != null ? (String) lForm.getField("nroSottofascicolo").getValue() : null;
				String nroInserto = lForm.getField("nroInserto") != null && lForm.getField("nroInserto").getValue() != null ? (String) lForm.getField("nroInserto").getValue() : null;
				String nomeFascicolo = lForm.getField("nomeFascicolo") != null && lForm.getField("nomeFascicolo").getValue() != null ? (String) lForm.getField("nomeFascicolo").getValue() : null;
				if (isSetted(nroFascicolo) || isSetted(annoFascicolo) || isSetted(nroSottofascicolo) || isSetted(nroInserto) || isSetted(nomeFascicolo)) {
					boolean setted = lForm.getField("setted") != null && lForm.getField("setted").getValue() != null && (Boolean) lForm.getField("setted").getValue();
					return setted;
				}
			} 
			return true;
		}
	}

	public class ClassificaRequiredValidator extends CustomValidator {

		public ClassificaRequiredValidator() {
			setErrorMessage("Classificazione obbligatoria");
		}

		@Override
		protected boolean condition(Object value) {
			if (getFormItem() != null && getFormItem().getForm() != null) {
				DynamicForm lForm = getFormItem().getForm();
				String indice = lForm.getField("indice") != null && lForm.getField("indice").getValue() != null ? (String) lForm.getField("indice").getValue() : null;
				String classifiche = lForm.getField("classifiche") != null && lForm.getField("classifiche").getValue() != null ? (String) lForm.getField("classifiche").getValue() : null;
				if (isSetted(indice) && isSetted(classifiche)) {
					return true;
				}
			} 
			return false;
		}
	}

	public class ClassificaFascicoloLookupTitolario extends LookupTitolarioPopup {

		public ClassificaFascicoloLookupTitolario(Record record) {
			super(record, true);
		}
		
		public String getFinalita() {
			return "ASSEGNAZIONE";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordTitolario(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	public class ClassificaFascicoloLookupArchivio extends LookupArchivioPopup {

		public ClassificaFascicoloLookupArchivio(Record record) {
			super(record, true);
		}

		public ClassificaFascicoloLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}
		
		@Override
		public String getFinalita() {			
			return "COLLEGA_FOLDER";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	public void cercaFascOnBlur(String nomeItem) {
		String nomeFascicolo = mDynamicForm.getValueAsString("nomeFascicolo");
		String codice = mDynamicForm.getValueAsString("codice");
		String capofila = mDynamicForm.getValueAsString("capofila");
		String annoFascicolo = mDynamicForm.getValueAsString("annoFascicolo");
		String nroFascicolo = mDynamicForm.getValueAsString("nroFascicolo");
		String nroSottofascicolo = mDynamicForm.getValueAsString("nroSottofascicolo");
		String nroInserto = mDynamicForm.getValueAsString("nroInserto");
		String classifiche = mDynamicForm.getValueAsString("classifiche");
		String indice = mDynamicForm.getValueAsString("indice");
		if (isSetted(nomeFascicolo) || isSetted(codice) || isSetted(capofila)
				|| (isSetted(annoFascicolo) && isSetted(nroFascicolo) && (isSetted(classifiche) || isSetted(indice)))) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindFascTitolarioDatasource");
			Record lRecord = new Record();
			lRecord.setAttribute("nomeFascicolo", nomeFascicolo);
			lRecord.setAttribute("annoFascicolo", annoFascicolo);
			lRecord.setAttribute("nroFascicolo", nroFascicolo);
			lRecord.setAttribute("nroSottofascicolo", nroSottofascicolo);
			lRecord.setAttribute("nroInserto", nroInserto);
			lRecord.setAttribute("idClassifica", classifiche);
			lRecord.setAttribute("indiceClassifica", indice);
			lRecord.setAttribute("nroSecondario", codice);
			lRecord.setAttribute("estremiDocCapofila", capofila);
			lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					String idFolderFascicolo = object.getAttribute("idFolderFascicolo");
					if (idFolderFascicolo != null && !idFolderFascicolo.equalsIgnoreCase("")) {
						mDynamicForm.setValue("correctValue", true);
						fascicolazioneTitleItem.validate();
						mDynamicForm.clearErrors(true);
						setIfNotNull("classifiche", object, "idClassifica");
						setIfNotNull("idClassifica", object, "idClassifica");
						setIfNotNull("indice", object, "indiceClassifica");
						setIfNotNull("idFolderFascicolo", object, "idFolderFascicolo");
						setIfNotNull("nomeFascicolo", object, "nomeFascicolo");
						setIfNotNull("annoFascicolo", object, "annoFascicolo");
						setIfNotNull("nroFascicolo", object, "nroFascicolo");
						setIfNotNull("nroSottofascicolo", object, "nroSottofascicolo");
						setIfNotNull("nroInserto", object, "nroInserto");
						setIfNotNull("livelloRiservatezza", object, "livelloRiservatezza");
						setIfNotNull("codice", object, "nroSecondario");
						setIfNotNull("capofila", object, "estremiDocCapofila");
						mDynamicForm.setValue("setted", true);
						mDynamicForm.markForRedraw();
					} else {
						mDynamicForm.setValue("correctValue", false);
						fascicolazioneTitleItem.validate();

					}
				}
			});
		}
	}

	protected void isNotMoreValid() {
		Boolean isSetted = mDynamicForm.getValue("setted") != null && (Boolean) mDynamicForm.getValue("setted");
		// Se prima di questo change il valore era settato, invalido automaticamente il valore
		if (isSetted == null || isSetted) {
			mDynamicForm.setValue("setted", false);
		}
		// valido l'item
		fascicolazioneTitleItem.validate();
	}

	protected void manageIndiceChange() {
		mDynamicForm.setValue("classifiche", (String) null);
		mDynamicForm.setValue("idClassifica", (String) null);
		mDynamicForm.setValue("provCIClassif", (String) null);
		mDynamicForm.setValue("descrizioneClassifica", (String) null);
		mDynamicForm.setValue("flgAttiva", (String) null);		
		mDynamicForm.clearErrors(true);
		final String value = indiceItem.getValueAsString();
		SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
		classificheDS.addParam("indice", value);
		classificheDS.addParam("descrizione", null);
		classificheItem.setOptionDataSource(classificheDS);
		if (value != null && !"".equals(value)) {
			//TODO ho cambiato classificheDS.fetchData con classificheItem.fetchData altrimenti non ricaricava la combo quando si tornava dalla lookup
			//TODO controllare anche le altre lookup dove viene fatta le fetch sul datasource e non sull'item
			classificheItem.fetchData(new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					RecordList data = response.getDataAsRecordList();
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String indice = data.get(i).getAttribute("indice");
							if (value.equals(indice)) {
								SelectGWTRestDataSource classificheDS = (SelectGWTRestDataSource) classificheItem.getOptionDataSource();
								classificheDS.addParam("descrizione", data.get(i).getAttributeAsString("descrizione"));
								classificheItem.setOptionDataSource(classificheDS);
								mDynamicForm.setValue("classifiche", data.get(i).getAttribute("idClassifica"));
								mDynamicForm.setValue("idClassifica", data.get(i).getAttribute("idClassifica"));
								mDynamicForm.setValue("provCIClassif", data.get(i).getAttributeAsString("provCI"));
								mDynamicForm.setValue("descrizioneClassifica", data.get(i).getAttributeAsString("descrizione"));
								mDynamicForm.setValue("flgAttiva", data.get(i).getAttribute("flgAttiva"));
								mDynamicForm.markForRedraw();
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						indiceItem.validate();
						indiceItem.blurItem();
					}
					// Se la classifica e' stata trovata allora cerco il fascicolo
					else {
						cercaFascOnBlur("nomeFascicolo");
					}
				}
			});
		}
	}
	
	public boolean isClassificaChiusa() {
		String flgAttiva = mDynamicForm.getValueAsString("flgAttiva");
		return flgAttiva != null && "0".equals(flgAttiva);
	}
}