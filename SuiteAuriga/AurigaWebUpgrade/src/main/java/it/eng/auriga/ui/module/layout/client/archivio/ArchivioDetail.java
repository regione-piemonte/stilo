/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.HtmlFlowWindow;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupTopograficoPopup;
import it.eng.auriga.ui.module.layout.client.anagrafiche.SalvaTopograficoPopup;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.gestioneProcedimenti.avvioProcedimento.ProcessTreeWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.CondivisioneItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.FascicoliCollegatiPopup;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PermessiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaPopup;
import it.eng.auriga.ui.module.layout.client.scrivania.ScrivaniaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.HeaderDetailSection;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ArchivioDetail extends CustomDetail {

	private final int TITTLE_ALIGN_WIDTH = 0;
	
	protected TabSet tabSet;
	protected Tab tabDatiFolder;

	protected VLayout layoutTabFolder;

	// DynamicForm
	protected DynamicForm form;
	protected DynamicForm estremiForm;
	protected DynamicForm folderTypeForm;
	protected DynamicForm datiIdentificativiForm;
	// protected DynamicForm responsabileForm;
	protected DynamicForm datiPrincipaliForm;
	protected DynamicForm taskForm;
	protected DynamicForm procFlowForm;
	protected DynamicForm assegnazioneForm;
	protected DynamicForm condivisioneForm;
	protected DynamicForm permessiForm;
	protected DynamicForm collocazioneFisicaForm;
	protected DynamicForm altriDatiForm;

	// DetailSection
	protected HeaderDetailSection estremiSection;
	protected HeaderDetailSection foldertypeSection;
	protected HeaderDetailSection datiidentificativiSection;
	// protected DetailSection responsabileSection;
	protected DetailSection datiprincipaliSection;
	protected DetailSection taskSection;
	protected DetailSection assegnazioneSection;
	protected DetailSection condivisioneSection;
	protected DetailSection permessiSection;
	protected DetailSection collocazionefisicaSection;
	protected DetailSection altridatiSection;

	// HiddenItem
	protected HiddenItem idUdFolderItem;
	protected HiddenItem flgUdFolderItem;
	protected HiddenItem idClassificaItem;
	protected HiddenItem idFolderAppItem;
	protected HiddenItem templateNomeFolderItem;	
	protected HiddenItem idTopograficoItem;
	protected HiddenItem idTopograficoOutItem;
	protected HiddenItem descrizioneTopograficoItem;
	protected HiddenItem noteCollocazioneFisicaItem;
	protected HiddenItem flgSottoFascInsertoItem;
	protected HiddenItem nomeItem;
	protected HiddenItem flgSelXFinalitaItem;
	protected HiddenItem flgFascTitolarioItem;
	// protected HiddenItem typeNodoItem;
	// protected HiddenItem idUOScrivResponsabileItem;
	protected HiddenItem faseCorrenteProcItem;
	protected HiddenItem idProcessItem;
	protected HiddenItem estremiProcessItem;
	protected HiddenItem idDefProcFlowItem;
	protected HiddenItem idInstProcFlowItem;
	protected HiddenItem idUdCapofilaItem;
	protected HiddenItem flgArchivioHiddenItem;
	protected HiddenItem timestampGetDataItem;
	
	protected HiddenItem presenzaFascCollegatiItem;
	protected HiddenItem fascicoliDaCollegareItem;
	
	// TextItem
	protected TextItem tipo;
	protected TextItem desUserAperturaFascicoloItem;
	protected TextItem desUserChiusuraFascicoloItem;
	protected TextItem altroIdentificativo;
	protected TextItem nomeFascicoloItem;
	protected TextItem indiceClassificaItem;
	protected TextItem descClassificaItem;
	protected ExtendedTextItem nroFascicoloItem;
	protected TextItem nroSottofascicoloItem;
	protected TextItem nroInsertoItem;
	protected TextItem codiceItem;
	protected TextItem capofilaItem;
	protected TextItem statoFascItem;
	protected TextItem nomeTopograficoItem;
	protected ExtendedTextItem codRapidoTopograficoItem;
	protected TaskItem taskItem;
	protected AssegnazioneItem assegnazioneItem;
	protected CondivisioneItem condivisioneItem;
	protected PermessiItem permessiItem;

	// FilteredSelectItem
	// protected FilteredSelectItemWithDisplay responsabileFascicoloItem;

	// SelectItem
	protected SelectItem idFolderTypeItem;
	protected SelectItem prioritaItem;
	protected SelectItem livelloRiservatezzaItem;

	// TextAreaItem
	protected TextAreaItem descContenutiFascicoloItem;
	protected TextAreaItem noteFascicoloItem;

	// DateItem
	protected DateItem dataAperturaFascicoloItem;
	protected DateItem dataChiusuraFascicoloItem;
	protected DateItem dtTermineRiservatezzaItem;

	protected CheckboxItem propagaRiservatezzaContenutiItem;

	protected AnnoItem annoFascicoloItem;

	// ImgButtonItem
	protected ImgButtonItem iterProcessoCollegatoButton;
	protected ImgButtonItem operazioniEffettuateButton;
	protected ImgButtonItem lookupTopograficoCollocazioneFisicaButton;
	protected ImgButtonItem salvaInTopograficoCollocazioneFisicaButton;
	protected ImgButtonItem visualizzaProcFlowButton;
	protected ImgButtonItem visualizzaAlberoIterButton;
	protected ImgButtonItem apriDettaglioCapofilaButton;
	protected ImgButtonItem flgArchivioValueCImgButtonItem;
	protected ImgButtonItem flgArchivioValueDImgButtonItem;
	protected ImgButtonItem flgArchivioValueSImgButtonItem;
	
	protected ImgButtonItem fascicoliCollegatiButton;
	protected ImgButtonItem collegaFascicoliButton;
	

	protected String folderType;
	protected String rowidFolder;
	protected LinkedHashMap<String, String> attributiAddFolderTabs;
	protected HashMap<String, VLayout> attributiAddFolderLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddFolderDetails;

	public ArchivioDetail(String nomeEntita) {

		super(nomeEntita);
		
		init();
		
	}
	
	protected void init() {
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);

		createTabSet();

		setMembers(tabSet);
	}
	
	protected void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			tabSet.setCanFocus(true);		
		} else {
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		}
		tabSet.setPaneMargin(0);

		tabDatiFolder = new Tab("<b>" + getTitleTabDatiFascicolo() + "</b>");
		tabDatiFolder.setAttribute("tabID", "HEADER");
		tabDatiFolder.setPrompt(getTitleTabDatiFascicolo());

		VLayout spacerFolder = new VLayout();
		spacerFolder.setHeight100();
		spacerFolder.setWidth100();

		layoutTabFolder = createLayoutTab(getLayoutTabDatiFolder(), spacerFolder);

		// Aggiungo i layout ai tab
		tabDatiFolder.setPane(layoutTabFolder);

		tabSet.addTab(tabDatiFolder);
	}
	
	public void setTitleTabDatiFascicolo(Record record) {
		tabDatiFolder.setTitle("Dati principali");
	}
	
	public String getTitleTabDatiFascicolo() {
		return "Dati principali";
	}
	
	public VLayout getLayoutTabDatiFolder() {
		
		idFolderAppItem = new HiddenItem("idFolderApp");
		idUdFolderItem = new HiddenItem("idUdFolder");
		idClassificaItem = new HiddenItem("idClassifica");
		flgSottoFascInsertoItem = new HiddenItem("flgSottoFascInserto");
		nomeItem = new HiddenItem("nome");
		flgUdFolderItem = new HiddenItem("flgUdFolder");
		flgSelXFinalitaItem = new HiddenItem("flgSelXFinalita");
		idProcessItem = new HiddenItem("idProcess");
		estremiProcessItem = new HiddenItem("estremiProcess");
		flgFascTitolarioItem = new HiddenItem("flgFascTitolario");
		flgFascTitolarioItem.setValue(true);

		// idUOScrivResponsabileItem = new HiddenItem("idUOScrivResponsabile");

		// sezione ESTREMI
		estremiForm = new DynamicForm() {

			@Override
			public void setFields(FormItem... fields) {
				super.setFields(fields);
				for (FormItem item : fields) {
					item.setTitleStyle(it.eng.utility.Styles.formTitleBold);
				}
			}
		};
		
		estremiForm.setValuesManager(vm);
		estremiForm.setWidth("100%");
		estremiForm.setHeight("5");
		estremiForm.setPadding(5);
		estremiForm.setWrapItemTitles(false);
		estremiForm.setNumCols(20);
		estremiForm.setColWidths(1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		estremiForm.setTabSet(tabSet);
		estremiForm.setTabID("HEADER");
		
		indiceClassificaItem = new TextItem("indiceClassifica", I18NUtil.getMessages().protocollazione_detail_classificazioneItem_title());
		indiceClassificaItem.setWidth(100);
		indiceClassificaItem.setColSpan(2);

		descClassificaItem = new TextItem("descClassifica");
		descClassificaItem.setShowTitle(false);
		descClassificaItem.setEndRow(true);
		descClassificaItem.setWidth(736);
		descClassificaItem.setColSpan(11);

		annoFascicoloItem = new AnnoItem("annoFascicolo", I18NUtil.getMessages().protocollazione_detail_annoFascicoloItem_title());
		annoFascicoloItem.setStartRow(true);
		annoFascicoloItem.setEndRow(false);
		annoFascicoloItem.setWidth(60);

		nroFascicoloItem = new ExtendedTextItem("nroFascicolo", I18NUtil.getMessages().protocollazione_detail_nroFascicoloItem_title());
		nroFascicoloItem.setEndRow(false);
		nroFascicoloItem.setTextAlign(Alignment.RIGHT);
		nroFascicoloItem.setLength(10);
		nroFascicoloItem.setWidth(100);

		nroSottofascicoloItem = new TextItem("nroSottofascicolo", I18NUtil.getMessages().protocollazione_detail_nroSottoFascicoloItem_title());
		nroSottofascicoloItem.setEndRow(false);
		nroSottofascicoloItem.setTextAlign(Alignment.RIGHT);
		nroSottofascicoloItem.setLength(5);
		nroSottofascicoloItem.setWidth(60);
		nroSottofascicoloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("CREAZIONE_SOTTOFASC");
			}
		});
				
		// nro inserto
		nroInsertoItem = new TextItem("nroInserto", I18NUtil.getMessages().protocollazione_detail_nroInsertoItem_title());
		nroInsertoItem.setEndRow(false);
		nroInsertoItem.setTextAlign(Alignment.RIGHT);
		nroInsertoItem.setLength(5);
		nroInsertoItem.setWidth(60);
		nroInsertoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("CREAZIONE_INSERTI");
			}
		});
		
		flgArchivioHiddenItem = new HiddenItem("flgArchivio");

		timestampGetDataItem = new HiddenItem("timestampGetData");

		// Icona #FlgArchivio = C
		flgArchivioValueCImgButtonItem = new ImgButtonItem("flgArchivioValueCImgButton", "lettere/lettera_C_verde.png", I18NUtil.getMessages()
				.archivio_detail_flgArchivioValueCAlt_value());
		flgArchivioValueCImgButtonItem.setAlwaysEnabled(true);
		flgArchivioValueCImgButtonItem.setColSpan(1);
		flgArchivioValueCImgButtonItem.setIconWidth(24);
		flgArchivioValueCImgButtonItem.setIconHeight(24);
		flgArchivioValueCImgButtonItem.setIconVAlign(VerticalAlignment.CENTER);
		flgArchivioValueCImgButtonItem.setAlign(Alignment.CENTER);
		flgArchivioValueCImgButtonItem.setWidth(16);
		flgArchivioValueCImgButtonItem.setRedrawOnChange(true);
		flgArchivioValueCImgButtonItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (flgArchivioHiddenItem.getValue() != null && !flgArchivioHiddenItem.getValue().equals("") && flgArchivioHiddenItem.getValue()
						.equals("C"));
			}
		});

		// Icona #FlgArchivio = D
		flgArchivioValueDImgButtonItem = new ImgButtonItem("flgArchivioValueDImgButton", "lettere/lettera_D_viola.png", I18NUtil.getMessages()
				.archivio_detail_flgArchivioValueDAlt_value());
		flgArchivioValueDImgButtonItem.setAlwaysEnabled(true);
		flgArchivioValueDImgButtonItem.setColSpan(1);
		flgArchivioValueDImgButtonItem.setIconWidth(24);
		flgArchivioValueDImgButtonItem.setIconHeight(24);
		flgArchivioValueDImgButtonItem.setIconVAlign(VerticalAlignment.CENTER);
		flgArchivioValueDImgButtonItem.setAlign(Alignment.CENTER);
		flgArchivioValueDImgButtonItem.setWidth(16);
		flgArchivioValueDImgButtonItem.setRedrawOnChange(true);
		flgArchivioValueDImgButtonItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (flgArchivioHiddenItem.getValue() != null && !flgArchivioHiddenItem.getValue().equals("") && flgArchivioHiddenItem.getValue()
						.equals("D"));
			}
		});

		// Icona #FlgArchivio = S
		flgArchivioValueSImgButtonItem = new ImgButtonItem("flgArchivioValueSImgButton", "lettere/lettera_S_blu.png", I18NUtil.getMessages()
				.archivio_detail_flgArchivioValueSAlt_value());
		flgArchivioValueSImgButtonItem.setAlwaysEnabled(true);
		flgArchivioValueSImgButtonItem.setColSpan(1);
		flgArchivioValueSImgButtonItem.setIconWidth(24);
		flgArchivioValueSImgButtonItem.setIconHeight(24);
		flgArchivioValueSImgButtonItem.setIconVAlign(VerticalAlignment.CENTER);
		flgArchivioValueSImgButtonItem.setAlign(Alignment.CENTER);
		flgArchivioValueSImgButtonItem.setWidth(16);
		flgArchivioValueSImgButtonItem.setRedrawOnChange(true);
		flgArchivioValueSImgButtonItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (flgArchivioHiddenItem.getValue() != null && !flgArchivioHiddenItem.getValue().equals("") && flgArchivioHiddenItem.getValue()
						.equals("S"));
			}
		});

		dataAperturaFascicoloItem = new DateItem("tsApertura", I18NUtil.getMessages().protocollazione_detail_dataAperturaFascicoloItem_title());
		dataAperturaFascicoloItem.setEndRow(false);
		
		desUserAperturaFascicoloItem = new TextItem("desUserApertura", I18NUtil.getMessages().protocollazione_detail_desUserAperturaFascicoloItem_title());
		desUserAperturaFascicoloItem.setEndRow(false);
		desUserAperturaFascicoloItem.setWidth(200);

		HiddenItem abilAssegnazioneSmistamentoItem = new HiddenItem("abilAssegnazioneSmistamento");
		abilAssegnazioneSmistamentoItem.setDefaultValue(true);

		iterProcessoCollegatoButton = new ImgButtonItem("iterProcessoCollegato", "buttons/gear.png", "Vai all’iter/processo collegato");
		iterProcessoCollegatoButton.setAlwaysEnabled(true);
		iterProcessoCollegatoButton.setColSpan(1);
		iterProcessoCollegatoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return idProcessItem.getValue() != null && !"".equals(idProcessItem.getValue());
			}
		});
		iterProcessoCollegatoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				AurigaLayout.apriDettaglioPratica((String) idProcessItem.getValue(), (String) estremiProcessItem.getValue());
			}
		});

		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate", "protocollazione/operazioniEffettuate.png", I18NUtil.getMessages()
				.protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idFolder = String.valueOf(idUdFolderItem.getValue());
				String estremi = "";
				if (annoFascicoloItem.getValue() != null && !"".equals(annoFascicoloItem.getValue())) {
					estremi += annoFascicoloItem.getValue() + " ";
				}
				if (indiceClassificaItem.getValue() != null && !"".equals(indiceClassificaItem.getValue())) {
					estremi += indiceClassificaItem.getValue() + " ";
				}
				boolean isSottofasc = false;
				if (nroFascicoloItem.getValue() != null && !"".equals(nroFascicoloItem.getValue())) {
					estremi += "N° " + nroFascicoloItem.getValue();
					if (nroSottofascicoloItem.getValue() != null && !"".equals(nroSottofascicoloItem.getValue())) {
						estremi += "/" + nroSottofascicoloItem.getValue();
						isSottofasc = true;
					}
					estremi += " ";
				}
				if (nomeItem.getValue() != null && !"".equals(nomeItem.getValue())) {
					estremi += nomeItem.getValue();
				}
				new OperazioniEffettuateWindow(idFolder, "F", isSottofasc ? I18NUtil.getMessages()
						.operazionieffettuateSottofasc_window_title(estremi) : I18NUtil.getMessages().operazionieffettuateFasc_window_title(estremi));
			}
		});

		
		// Fascicoli collegati		
		presenzaFascCollegatiItem = new HiddenItem("presenzaFascCollegati");
		fascicoliDaCollegareItem  = new HiddenItem("listaFascicoliDaCollegare");
			
		fascicoliCollegatiButton = new ImgButtonItem("fascicoliCollegatiButton", "buttons/link.png", "Fascicoli collegati");
		fascicoliCollegatiButton.setAlwaysEnabled(true);
		fascicoliCollegatiButton.setColSpan(1);
		fascicoliCollegatiButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record(vm.getValues());					
				FascicoliCollegatiPopup fascicoliCollegatiPopup = new FascicoliCollegatiPopup(record) {	
					@Override
					public void onSaveButtonClick(RecordList listaFascicoliCollegati) {
						if (listaFascicoliCollegati != null && listaFascicoliCollegati.getLength() > 0) {
							estremiForm.setValue("presenzaFascCollegati", "1");
						} else {
							estremiForm.setValue("presenzaFascCollegati", "0");
						}
						estremiForm.markForRedraw();
					}
				};
			}
		});
		fascicoliCollegatiButton.setShowIfCondition(new FormItemIfFunction() {	
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {					
				boolean show = (estremiForm.getValueAsString("presenzaFascCollegati") != null && "1".equals(estremiForm.getValueAsString("presenzaFascCollegati"))); 
				return show;
			}
		});
			
		collegaFascicoliButton = new ImgButtonItem("collegaFascicoliButton", "buttons/addlink.png", "Collega fascicoli");
		collegaFascicoliButton.setAlwaysEnabled(true);
		collegaFascicoliButton.setColSpan(1);
		collegaFascicoliButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record(vm.getValues());
				boolean abilModificaDati = true;
				record.setAttribute("abilModificaDati", abilModificaDati);
				FascicoliCollegatiPopup fascicoliCollegatiPopup = new FascicoliCollegatiPopup(record) {
					@Override
					public void onSaveButtonClick(RecordList listaFascicoliCollegati) {
						if (listaFascicoliCollegati != null && listaFascicoliCollegati.getLength() > 0) {
							estremiForm.setValue("presenzaFascCollegati", "1");
						} else {
							estremiForm.setValue("presenzaFascCollegati", "0");
						}
						estremiForm.markForRedraw();
					}
				};
			}
		});
		collegaFascicoliButton.setShowIfCondition(new FormItemIfFunction() {
	
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				
				// Il bottone si abilita se posso modificare i dati oppure se sono abilitato a collegare i fascicoli
				boolean show = showFascicoliCollegatiButton(record) && (estremiForm.getValueAsString("presenzaFascCollegati") == null || "".equals(estremiForm.getValueAsString("presenzaFascCollegati")) || "0".equals(estremiForm.getValueAsString("presenzaFascCollegati"))); 
				return show;
			}
		});

		codiceItem = new TextItem("codice", I18NUtil.getMessages().protocollazione_detail_codiceItem_title());
		codiceItem.setWidth(100);
		codiceItem.setStartRow(true);
		codiceItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return true; // lo faccio vedere sempre
			}
		});


		idUdCapofilaItem = new HiddenItem("idUdCapofila");

		capofilaItem = new TextItem("capofila", I18NUtil.getMessages().protocollazione_detail_capofilaItem_title());
		capofilaItem.setWidth(160);
		capofilaItem.setColSpan(2);
		capofilaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return (codiceItem.getValue() != null && !"".equals(codiceItem.getValue()))
//						|| (capofilaItem.getValue() != null && !"".equals(capofilaItem.getValue()));
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAPOFILA"); // lo faccio vedere solo quando il parametro è attivo
			}
		});

		apriDettaglioCapofilaButton = new ImgButtonItem("apriDettaglioCapofila", "buttons/detail.png", "Dettaglio doc. capofila");
		apriDettaglioCapofilaButton.setAlwaysEnabled(true);
		apriDettaglioCapofilaButton.setColSpan(2);
		apriDettaglioCapofilaButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return idUdCapofilaItem.getValue() != null && !"".equals(idUdCapofilaItem.getValue());
			}
		});

		apriDettaglioCapofilaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				record.setAttribute("idUd", idUdCapofilaItem.getValue());
				new DettaglioRegProtAssociatoWindow(record, "Dettaglio doc. capofila");
			}
		});
				
		dataChiusuraFascicoloItem = new DateItem("tsChiusura", I18NUtil.getMessages().protocollazione_detail_dataChiusuraFascicoloItem_title());
		dataChiusuraFascicoloItem.setEndRow(false);
		dataChiusuraFascicoloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (dataChiusuraFascicoloItem.getValue() != null && !dataChiusuraFascicoloItem.getValue().equals(""));
			}
		});

		desUserChiusuraFascicoloItem = new TextItem("desUserChiusura", I18NUtil.getMessages().protocollazione_detail_desUserChiusuraFascicoloItem_title());
		desUserChiusuraFascicoloItem.setEndRow(false);
		desUserChiusuraFascicoloItem.setWidth(200);
		desUserChiusuraFascicoloItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (desUserChiusuraFascicoloItem.getValue() != null && !desUserChiusuraFascicoloItem.getValue().equals(""));
			}
		});
		
		statoFascItem = new TextItem("statoFasc", I18NUtil.getMessages().protocollazione_detail_stato_detail());
		statoFascItem.setColSpan(8);
		statoFascItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return value != null && !"".equals(value);
			}
		});

		estremiForm.setItems(idFolderAppItem, idUdFolderItem, flgSottoFascInsertoItem, flgUdFolderItem, idClassificaItem, idProcessItem, estremiProcessItem,
				indiceClassificaItem, descClassificaItem, annoFascicoloItem, nroFascicoloItem, nroSottofascicoloItem, nroInsertoItem, flgArchivioValueCImgButtonItem,
				flgArchivioValueDImgButtonItem, flgArchivioValueSImgButtonItem, dataAperturaFascicoloItem, desUserAperturaFascicoloItem, flgSelXFinalitaItem,
				abilAssegnazioneSmistamentoItem, iterProcessoCollegatoButton, operazioniEffettuateButton,
				timestampGetDataItem,
				
				presenzaFascCollegatiItem,
				fascicoliDaCollegareItem,
				fascicoliCollegatiButton,
				collegaFascicoliButton,
			
				codiceItem, capofilaItem, apriDettaglioCapofilaButton,
				dataChiusuraFascicoloItem, desUserChiusuraFascicoloItem, statoFascItem, flgArchivioHiddenItem, idUdCapofilaItem);

		// sezione TIPO
		folderTypeForm = new DynamicForm();
		folderTypeForm.setValuesManager(vm);
		folderTypeForm.setWidth("*");
		folderTypeForm.setHeight("5");
		folderTypeForm.setPadding(5);
		folderTypeForm.setWrapItemTitles(false);
		folderTypeForm.setNumCols(8);
		folderTypeForm.setTabSet(tabSet);
		folderTypeForm.setTabID("HEADER");

		final GWTRestDataSource idFolderTypeDS = new GWTRestDataSource("LoadComboTipoFolderDataSource", "idFolderType", FieldType.TEXT);

		// lista tipi fascicoli
		idFolderTypeItem = new SelectItem("idFolderType", I18NUtil.getMessages().archivio_list_tipoField_title()) {

			@Override
			public void onOptionClick(Record record) {
				templateNomeFolderItem.setValue(record.getAttributeAsString("templateNomeFolder"));
				datiIdentificativiForm.markForRedraw();
				showHideSections();
			}
			
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					templateNomeFolderItem.setValue("");
				}
				datiIdentificativiForm.markForRedraw();
				showHideSections();
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				templateNomeFolderItem.setValue("");
				datiIdentificativiForm.markForRedraw();
				showHideSections();
			};
		};
		
		SpacerItem spacerFoderType = new SpacerItem();
		spacerFoderType.setWidth(TITTLE_ALIGN_WIDTH + 5);

		idFolderTypeItem.setShowTitle(false);
		idFolderTypeItem.setWidth(883);
		idFolderTypeItem.setColSpan(4);
		idFolderTypeItem.setAlign(Alignment.CENTER);
		idFolderTypeItem.setValueField("idFolderType");
		idFolderTypeItem.setDisplayField("descFolderType");
		idFolderTypeItem.setOptionDataSource(idFolderTypeDS);
		idFolderTypeItem.setAutoFetchData(false);
		idFolderTypeItem.setAlwaysFetchMissingValues(true);
		idFolderTypeItem.setFetchMissingValues(true);
		idFolderTypeItem.setAllowEmptyValue(true);
		idFolderTypeItem.setEndRow(false);
		
		templateNomeFolderItem = new HiddenItem("templateNomeFolder");

		folderTypeForm.setItems(spacerFoderType, idFolderTypeItem, templateNomeFolderItem);

		// sezione DATI IDENTIFICATIVI
		datiIdentificativiForm = new DynamicForm();
		datiIdentificativiForm.setValuesManager(vm);
		datiIdentificativiForm.setWidth("*");
		datiIdentificativiForm.setHeight("5");
		datiIdentificativiForm.setPadding(5);
		datiIdentificativiForm.setWrapItemTitles(false);
		datiIdentificativiForm.setNumCols(8);
		datiIdentificativiForm.setTabSet(tabSet);
		datiIdentificativiForm.setTabID("HEADER");

		nomeFascicoloItem = new TextItem("nomeFascicolo", I18NUtil.getMessages().protocollazione_detail_nomeFascicoloItem_title());
		nomeFascicoloItem.setStartRow(true);
		nomeFascicoloItem.setEndRow(false);
		nomeFascicoloItem.setWidth(838);
		nomeFascicoloItem.setColSpan(8);
//		nomeFascicoloItem.setRequired(true);
		nomeFascicoloItem.setAttribute("obbligatorio", true);		
		nomeFascicoloItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {
			
			@Override
			public boolean execute(FormItem formItem, Object value) {
				boolean hasTemplateNomeFolder = templateNomeFolderItem.getValue() != null && !"".equals(templateNomeFolderItem.getValue());
				return (mode != null && mode.equals("view")) || !hasTemplateNomeFolder;
			}
		}));
		nomeFascicoloItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean hasTemplateNomeFolder = templateNomeFolderItem.getValue() != null && !"".equals(templateNomeFolderItem.getValue());
				return (mode != null && mode.equals("view")) || !hasTemplateNomeFolder;
			}
		});

		datiIdentificativiForm.setItems(nomeFascicoloItem);

		// sezione DATI PRINCIPALI
		datiPrincipaliForm = new DynamicForm();
		datiPrincipaliForm.setValuesManager(vm);
		datiPrincipaliForm.setWidth("*");
		datiPrincipaliForm.setHeight("5");
		datiPrincipaliForm.setPadding(5);
		datiPrincipaliForm.setWrapItemTitles(false);
		datiPrincipaliForm.setNumCols(10);
		datiPrincipaliForm.setColWidths(1,1,1,1,1,1,1,1,"*");
		datiPrincipaliForm.setTabSet(tabSet);
		datiPrincipaliForm.setTabID("HEADER");

		descContenutiFascicoloItem = new TextAreaItem("descContenutiFascicolo", I18NUtil.getMessages().archivio_detail_descContenutiFascicoloItem_title());
		descContenutiFascicoloItem.setStartRow(true);
		descContenutiFascicoloItem.setEndRow(false);
		descContenutiFascicoloItem.setLength(600);
		descContenutiFascicoloItem.setHeight(40);
		descContenutiFascicoloItem.setWidth(778);
		descContenutiFascicoloItem.setColSpan(8);

		// Livello riservatezza
		GWTRestDataSource livelloRiservatezzaDS = new GWTRestDataSource("LoadComboLivelloRiservatezzaDataSource", "key", FieldType.TEXT);
		livelloRiservatezzaItem = new SelectItem("livelloRiservatezza", I18NUtil.getMessages().protocollazione_detail_livelloRiservatezzaItem_title());
		livelloRiservatezzaItem.setOptionDataSource(livelloRiservatezzaDS);
		livelloRiservatezzaItem.setAutoFetchData(false);
		livelloRiservatezzaItem.setAlwaysFetchMissingValues(true);
		livelloRiservatezzaItem.setFetchMissingValues(true);
		livelloRiservatezzaItem.setDisplayField("value");
		livelloRiservatezzaItem.setValueField("key");
		livelloRiservatezzaItem.setWidth(100);
		livelloRiservatezzaItem.setWrapTitle(false);
		livelloRiservatezzaItem.setAllowEmptyValue(true);
		livelloRiservatezzaItem.setStartRow(true);
		livelloRiservatezzaItem.setEndRow(false);
		livelloRiservatezzaItem.setColSpan(1);
		livelloRiservatezzaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (livelloRiservatezzaItem.getValue() == null || livelloRiservatezzaItem.getValue().equals("")) {
					datiPrincipaliForm.setValue("dtTermineRiservatezza", "");
					propagaRiservatezzaContenutiItem.setValue(false);
					propagaRiservatezzaContenutiItem.hide();
				} else {
					if (editing) {
						propagaRiservatezzaContenutiItem.setValue(true);
						propagaRiservatezzaContenutiItem.show();
					} else {
						propagaRiservatezzaContenutiItem.setValue(false);
						propagaRiservatezzaContenutiItem.hide();
					}
				}
				datiPrincipaliForm.redraw();
			}
		});

		propagaRiservatezzaContenutiItem = new CheckboxItem("propagaRiservatezzaContenuti", "propaga riservatezza ai contenuti");
		propagaRiservatezzaContenutiItem.setVisible(false);
		propagaRiservatezzaContenutiItem.setValue(false);
		propagaRiservatezzaContenutiItem.setColSpan(1);
		propagaRiservatezzaContenutiItem.setWidth("*");

		// Data termine riservatezza
		dtTermineRiservatezzaItem = new DateItem("dtTermineRiservatezza", I18NUtil.getMessages().protocollazione_detail_dataRiservatezzaItem_title());
		dtTermineRiservatezzaItem.setWrapTitle(false);
		dtTermineRiservatezzaItem.setEndRow(false);
		dtTermineRiservatezzaItem.setColSpan(1);
		dtTermineRiservatezzaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (livelloRiservatezzaItem.getValue() != null && !livelloRiservatezzaItem.getValue().equals(""));
			}
		});

		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(100);
		spacer.setColSpan(1);

		// Priorita riservatezza
		GWTRestDataSource prioritaRiservatezzaDS = new GWTRestDataSource("LoadComboPrioritaRiservatezzaDataSource", "key", FieldType.TEXT);
		prioritaItem = new SelectItem("priorita", I18NUtil.getMessages().protocollazione_detail_prioritaRiservatezzaItem_title());
		prioritaItem.setOptionDataSource(prioritaRiservatezzaDS);
		prioritaItem.setAutoFetchData(false);
		prioritaItem.setAlwaysFetchMissingValues(true);
		prioritaItem.setFetchMissingValues(true);
		prioritaItem.setAllowEmptyValue(true);
		prioritaItem.setWidth(150);
		prioritaItem.setDisplayField("value");
		prioritaItem.setValueField("key");
		prioritaItem.setColSpan(1);

		datiPrincipaliForm.setItems(descContenutiFascicoloItem, livelloRiservatezzaItem, dtTermineRiservatezzaItem, propagaRiservatezzaContenutiItem, spacer,
				prioritaItem);

		// sezione TASK
		taskForm = new DynamicForm();
		taskForm.setValuesManager(vm);
		taskForm.setWidth("100%");
		taskForm.setHeight("5");
		taskForm.setPadding(5);
		taskForm.setTabSet(tabSet);
		taskForm.setTabID("HEADER");

		faseCorrenteProcItem = new HiddenItem("faseCorrenteProc");

		taskItem = new TaskItem() {

			@Override
			public void reloadTask() {
				manageReloadTask();
			}
		};
		taskItem.setName("listaTask");
		taskItem.setShowTitle(false);

		taskForm.setFields(faseCorrenteProcItem, taskItem);

		procFlowForm = new DynamicForm();
		procFlowForm.setValuesManager(vm);
		procFlowForm.setWidth("100%");
		procFlowForm.setHeight("5");
		procFlowForm.setPadding(0);
		procFlowForm.setMargin(0);
		procFlowForm.setNumCols(5);
		procFlowForm.setColWidths(20, 20, 20, 20, "*");
		procFlowForm.setTabSet(tabSet);
		procFlowForm.setTabID("HEADER");

		idDefProcFlowItem = new HiddenItem("idDefProcFlow");
		idInstProcFlowItem = new HiddenItem("idInstProcFlow");

		visualizzaProcFlowButton = new ImgButtonItem("visualizzaProcFlowButton", "menu/modellatoreProcessi.png", "Visualizza grafico procedimento");
		visualizzaProcFlowButton.setAlwaysEnabled(true);
		visualizzaProcFlowButton.setEndRow(false);
		visualizzaProcFlowButton.setColSpan(1);
		visualizzaProcFlowButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String url = AurigaLayout.getParametroDB("URL_MODELLATORE_PROCESSI");
				String username = AurigaLayout.getParametroDB("USERID_ACTIVITI_FLOW") != null ? AurigaLayout.getParametroDB("USERID_ACTIVITI_FLOW") : "mattia";
				String password = AurigaLayout.getParametroDB("PWD_ACTIVITI_FLOW") != null ? AurigaLayout.getParametroDB("PWD_ACTIVITI_FLOW") : "zanin";
				if (url != null && !"".equals(url) && username != null && !"".equals(username) && password != null && !"".equals(password)) {
					if (!url.endsWith("/"))
						url += "/";
					url += "diagram-viewer/showDiagram.html?usernameExt=" + username + "&passwordExt=" + password + "&processDefinitionId="
							+ idDefProcFlowItem.getValue() + "&processInstanceId=" + idInstProcFlowItem.getValue();
					try {
						HtmlFlowWindow visualizzaProcFlowWindow = new HtmlFlowWindow("grafico_procedimento", "Grafico del procedimento", url);
						visualizzaProcFlowWindow.show();
					} catch (Exception e) {
						Window.open(url, "_newtab", "");
					}
				} else {
					Layout.addMessage(new MessageBean("Parametro in DB non configurato", "", MessageType.ERROR));
				}
			}
		});

		visualizzaAlberoIterButton = new ImgButtonItem("visualizzaAlberoIterButton", "archivio/visualizzaIter.png", "Iter svolto dal procedimento");
		visualizzaAlberoIterButton.setAlwaysEnabled(true);
		visualizzaAlberoIterButton.setEndRow(false);
		visualizzaAlberoIterButton.setColSpan(1);
		visualizzaAlberoIterButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				ProcessTreeWindow lProcessTreeWindow = new ProcessTreeWindow("ProcessTreeWindow");
				lProcessTreeWindow.loadRecord((String) idProcessItem.getValue(), nomeFascicoloItem.getValueAsString());
			}
		});

		procFlowForm.setFields(idProcessItem, idDefProcFlowItem, idInstProcFlowItem, visualizzaProcFlowButton, visualizzaAlberoIterButton);

		// sezione ASSEGNAZIONE
		assegnazioneForm = new DynamicForm();
		assegnazioneForm.setNumCols(6);
		assegnazioneForm.setValuesManager(vm);
		assegnazioneForm.setWidth("100%");
		assegnazioneForm.setHeight("5");
		assegnazioneForm.setPadding(5);
		assegnazioneForm.setColWidths(1, 1, 1, 1, "*", "*");
		assegnazioneForm.setTabSet(tabSet);
		assegnazioneForm.setTabID("HEADER");

		assegnazioneItem = new AssegnazioneItem(){
			@Override
			public String getCodRapidoTitle() {
				return super.getCodRapidoTitle();
			}
			
			@Override
			public Boolean getShowRemoveButton() {
				return false;
			}
			
			@Override
			public int getFilteredSelectItemWidth() {
				return 722;
			}
		};
		assegnazioneItem.setName("listaAssegnazioni");
		assegnazioneItem.setShowTitle(false);
		assegnazioneItem.setFlgUdFolder("F");
		assegnazioneItem.setNotReplicable(true);
		assegnazioneItem.setFlgSenzaLD(true);
		assegnazioneItem.setColSpan(2);
		assegnazioneForm.setFields(assegnazioneItem);

		// sezione INVIO PER CONOSCENZA / CONDIVISIONE
		condivisioneForm = new DynamicForm();
		condivisioneForm.setValuesManager(vm);
		condivisioneForm.setWidth("100%");
		condivisioneForm.setHeight("5");
		condivisioneForm.setPadding(5);
		condivisioneForm.setTabSet(tabSet);
		condivisioneForm.setTabID("HEADER");

		condivisioneItem = new CondivisioneItem(){
			@Override
			public int getFilteredSelectItemWidth() {
				return 572;
			}
		};
		condivisioneItem.setName("listaDestInvioCC");
		condivisioneItem.setShowTitle(false);
		condivisioneItem.setFlgUdFolder("F");
		
		condivisioneForm.setFields(condivisioneItem);

		// sezione PERMESSI
		permessiForm = new DynamicForm();
		permessiForm.setValuesManager(vm);
		permessiForm.setWidth("*");
		permessiForm.setHeight("5");
		permessiForm.setPadding(5);
		permessiForm.setWrapItemTitles(false);
		permessiForm.setNumCols(8);
		permessiForm.setTabSet(tabSet);
		permessiForm.setTabID("HEADER");

		permessiItem = new PermessiItem(true){
			@Override
			public int getFilteredSelectItemWidth() {
				return 446;
			}
		};
		permessiItem.setName("listaACL");
		permessiItem.setShowTitle(false);
		permessiItem.setCanEdit(true);

		permessiForm.setItems(permessiItem);

		// sezione COLLOCAZIONE FISICA
		collocazioneFisicaForm = new DynamicForm();
		collocazioneFisicaForm.setValuesManager(vm);
		collocazioneFisicaForm.setWidth("100%");
		collocazioneFisicaForm.setHeight("5");
		collocazioneFisicaForm.setPadding(5);
		collocazioneFisicaForm.setWrapItemTitles(false);
		collocazioneFisicaForm.setNumCols(7);
		collocazioneFisicaForm.setColWidths(1, 1, 1, 1, 1, 1, "*");
		collocazioneFisicaForm.setTabSet(tabSet);
		collocazioneFisicaForm.setTabID("HEADER");

		idTopograficoItem = new HiddenItem("idTopografico");
		idTopograficoOutItem = new HiddenItem("idTopograficoOut");
		descrizioneTopograficoItem = new HiddenItem("descrizioneTopografico");
		noteCollocazioneFisicaItem = new HiddenItem("noteCollocazioneFisica");

		// cod.rapido
		codRapidoTopograficoItem = new ExtendedTextItem("codRapidoTopografico", I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title());
		codRapidoTopograficoItem.setEndRow(false);
		codRapidoTopograficoItem.setWidth(120);

		codRapidoTopograficoItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String value = codRapidoTopograficoItem.getValueAsString();
				collocazioneFisicaForm.clearErrors(true);
				collocazioneFisicaForm.setValue("idTopografico", "");
				collocazioneFisicaForm.setValue("idTopograficoOut", "");
				collocazioneFisicaForm.setValue("nomeTopografico", "");
				collocazioneFisicaForm.setValue("descrizioneTopografico", "");
				collocazioneFisicaForm.setValue("noteCollocazioneFisica", "");
				if (value != null && !"".equals(value)) {
					GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("FindTopograficoDatasource");
					Record lRecord = new Record();
					lRecord.setAttribute("codRapidoTopografico", codRapidoTopograficoItem.getValue());
					lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

						@Override
						public void execute(Record object) {
							// Se non ha trovato niente
							if (object.getAttributeAsBoolean("vuoto")) {
								collocazioneFisicaForm.setFieldErrors("codRapidoTopografico", I18NUtil.getMessages()
										.protocollazione_detail_esitoValidazione_KO_value());
							} else {
								collocazioneFisicaForm.clearFieldErrors("codRapidoTopografico", true);
								if (object.getAttribute("idTopograficoOut") != null && !object.getAttribute("idTopograficoOut").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("idTopograficoOut", object.getAttribute("idTopograficoOut"));
								}
								if (object.getAttribute("codRapidoTopografico") != null && !object.getAttribute("codRapidoTopografico").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("codRapidoTopografico", object.getAttribute("codRapidoTopografico"));
								}
								if (object.getAttribute("nomeTopografico") != null && !object.getAttribute("nomeTopografico").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("nomeTopografico", object.getAttribute("nomeTopografico"));
								}

								if (object.getAttribute("descrTopografico") != null && !object.getAttribute("descrTopografico").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("descrizioneTopografico", object.getAttribute("descrTopografico"));
								}

								if (object.getAttribute("idTopografico") != null && !object.getAttribute("idTopografico").equalsIgnoreCase("")) {
									collocazioneFisicaForm.setValue("idTopografico", object.getAttribute("idTopografico"));
								}
							}
						}
					});
				}
			}
		});
		
		SpacerItem spacerTopografico = new SpacerItem();
		spacerTopografico.setWidth(20);

		lookupTopograficoCollocazioneFisicaButton = new ImgButtonItem("lookupTopograficoCollocazioneFisicaButton", "lookup/topografico.png", I18NUtil
				.getMessages().protocollazione_detail_lookupTopograficoButton_prompt());
		lookupTopograficoCollocazioneFisicaButton.setEndRow(false);
		lookupTopograficoCollocazioneFisicaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				LookupTopograficoPopup lookupTopograficoPopup = new LookupTopograficoPopup() {

					@Override
					public void manageLookupBack(Record record) {
						collocazioneFisicaForm.setValue("idTopografico", record.getAttribute("idTopografico"));
						collocazioneFisicaForm.setValue("codRapidoTopografico", record.getAttribute("codiceRapido"));
						collocazioneFisicaForm.setValue("nomeTopografico", record.getAttribute("nome"));
						collocazioneFisicaForm.setValue("descrizioneTopografico", record.getAttribute("descrizione"));
						collocazioneFisicaForm.setValue("noteCollocazioneFisica", record.getAttribute("note"));
					}

					@Override
					public void manageMultiLookupUndo(Record record) {
					}

					@Override
					public void manageMultiLookupBack(Record record) {
					}
				};
				lookupTopograficoPopup.show();
			}
		});

		nomeTopograficoItem = new TextItem("nomeTopografico", I18NUtil.getMessages().protocollazione_detail_nomeItem_title());
		nomeTopograficoItem.setShowTitle(false);
		nomeTopograficoItem.setEndRow(false);
		nomeTopograficoItem.setWidth(724);
		nomeTopograficoItem.addChangedHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				collocazioneFisicaForm.clearErrors(true);
				collocazioneFisicaForm.setValue("idTopografico", "");
				collocazioneFisicaForm.setValue("idTopograficoOut", "");
			}
		});

		salvaInTopograficoCollocazioneFisicaButton = new ImgButtonItem("salvaInTopograficoCollocazioneFisicaButton", "buttons/saveIn.png", I18NUtil
				.getMessages().protocollazione_detail_salvaInTopograficoButton_prompt());
		salvaInTopograficoCollocazioneFisicaButton.setColSpan(1);
		salvaInTopograficoCollocazioneFisicaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record();
				lRecord.setAttribute("codiceRapido", codRapidoTopograficoItem.getValue());
				lRecord.setAttribute("nome", nomeTopograficoItem.getValue());
				lRecord.setAttribute("descrizione", descrizioneTopograficoItem.getValue());
				lRecord.setAttribute("note", noteCollocazioneFisicaItem.getValue());
				SalvaTopograficoPopup salvaTopograficoPopup = new SalvaTopograficoPopup(lRecord) {

					@Override
					public void manageLookupBack(Record record) {
						codRapidoTopograficoItem.setValue(record.getAttribute("codiceRapido"));
						nomeTopograficoItem.setValue(record.getAttribute("nome"));
						descrizioneTopograficoItem.setValue(record.getAttribute("descrizione"));
						noteCollocazioneFisicaItem.setValue(record.getAttribute("note"));
						idTopograficoItem.setValue(record.getAttribute("idTopografico"));
						collocazioneFisicaForm.clearErrors(true);
					}
				};
				salvaTopograficoPopup.show();
			}
		});

		collocazioneFisicaForm.setFields(codRapidoTopograficoItem, spacerTopografico, lookupTopograficoCollocazioneFisicaButton, nomeTopograficoItem,
				salvaInTopograficoCollocazioneFisicaButton, idTopograficoItem,
				// idTopograficoOutItem,
				descrizioneTopograficoItem, noteCollocazioneFisicaItem);

		// sezione ALTRI DATI
		altriDatiForm = new DynamicForm();
		altriDatiForm.setValuesManager(vm);
		altriDatiForm.setWidth("100%");
		altriDatiForm.setHeight("5");
		altriDatiForm.setPadding(5);
		altriDatiForm.setWrapItemTitles(false);
		altriDatiForm.setNumCols(8);
		altriDatiForm.setColWidths(1,1,1,1,1,1,"*","*");
		altriDatiForm.setTabSet(tabSet);
		altriDatiForm.setTabID("HEADER");

		noteFascicoloItem = new TextAreaItem("noteFascicolo", I18NUtil.getMessages().archivio_detail_noteFascicoloItem_title());
		noteFascicoloItem = new TextAreaItem("noteFascicolo", I18NUtil.getMessages().archivio_detail_noteFascicoloItem_title());
		noteFascicoloItem.setLength(800);
		noteFascicoloItem.setHeight(40);
		noteFascicoloItem.setWidth(861);
		noteFascicoloItem.setEndRow(false);

		altriDatiForm.setItems(noteFascicoloItem);

		estremiSection = new HeaderDetailSection(I18NUtil.getMessages().archivio_detail_estremiSection_title(), true, true, false, estremiForm);
		foldertypeSection = new HeaderDetailSection(I18NUtil.getMessages().archivio_list_tipoField_title(), true, true, false, folderTypeForm);
		datiidentificativiSection = new HeaderDetailSection("Dati identificativi", true, true, false, datiIdentificativiForm);
		// responsabileSection = new DetailSection(I18NUtil.getMessages().archivio_detail_responsabileSection_title(), true, true, false, responsabileForm);
		datiprincipaliSection = new DetailSection(I18NUtil.getMessages().archivio_detail_datiprincipaliSection_title(), true, true, false, datiPrincipaliForm);
		taskSection = new DetailSection("Lista task", true, true, false, taskForm, procFlowForm) {

			@Override
			public void manageOnChangeCanEdit(FormItem item) {
				// questo metodo non funziona se ho più di un form nella sezione quindi intanto lo disabilito (poi sarà da sistemare)
			}
		};
		assegnazioneSection = new DetailSection(I18NUtil.getMessages().archivio_detail_assegnazioneSection_title(), true, true, false, assegnazioneForm);
		condivisioneSection = new DetailSection(I18NUtil.getMessages().archivio_detail_condivisioneSection_title(), true, true, false, condivisioneForm){
			@Override
			public boolean showFirstCanvasWhenEmptyAfterOpen() {
				return true;
			}
			
		};
		permessiSection = new DetailSection(I18NUtil.getMessages().archivio_detail_permessiSection_title(), true, true, false, permessiForm);
		collocazionefisicaSection = new DetailSection(I18NUtil.getMessages().archivio_detail_collocazionefisicaSection_title(), true, true, false,
				collocazioneFisicaForm);
		altridatiSection = new DetailSection(I18NUtil.getMessages().archivio_detail_altridatiSection_title(), true, true, false, altriDatiForm);

		estremiSection.show();

		VLayout lVLayout = new VLayout();	
		lVLayout.setWidth100();
		lVLayout.setHeight(50);

		lVLayout.addMember(estremiSection);
		lVLayout.addMember(foldertypeSection);
		lVLayout.addMember(datiidentificativiSection);
		// lVLayout.addMember(responsabileSection);
		lVLayout.addMember(datiprincipaliSection);
		lVLayout.addMember(taskSection);
		lVLayout.addMember(assegnazioneSection);
		lVLayout.addMember(condivisioneSection);
		lVLayout.addMember(permessiSection);
		lVLayout.addMember(collocazionefisicaSection);
		lVLayout.addMember(altridatiSection);
		
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

	protected void manageReloadTask() {
		layout.reload(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(taskItem != null) {
					taskItem.setCanEdit(false);
				}
			}
		});
	}
	
	public List<DynamicForm> getAllDetailForms() {
		List<DynamicForm> allDetailForms = super.getAllDetailForms();
		if (attributiAddFolderDetails != null) {
			for (String key : attributiAddFolderDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddFolderDetails.get(key);
				for (DynamicForm form : detail.getForms()) {
					allDetailForms.add(form);
				}
			}
		}
		return allDetailForms;
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
	public void showTabErrors(TabSet tabSet) {
		super.showTabErrors(tabSet);
		if (attributiAddFolderTabs != null) {
			for (String key : attributiAddFolderTabs.keySet()) {
				if (attributiAddFolderDetails != null && attributiAddFolderDetails.get(key) != null) {
					attributiAddFolderDetails.get(key).showTabErrors(tabSet);
				}
			}
		}
	}	

	public boolean customValidate() {
		boolean valid = super.customValidate();		
		if (attributiAddFolderDetails != null) {
			for (String key : attributiAddFolderDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddFolderDetails.get(key);
				if(!detail.customValidate()) {
					valid = false;
				}
				for (DynamicForm form : detail.getForms()) {
					form.clearErrors(true);
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							boolean itemValid = lReplicableItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
									lReplicableItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							boolean itemValid = lIDocumentItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lIDocumentItem != null && lIDocumentItem.getForm() != null && lIDocumentItem.getForm().getDetailSection() != null) {
									lIDocumentItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof CKEditorItem) {
							CKEditorItem lCKEditorItem = (CKEditorItem) item;
							boolean itemValid = lCKEditorItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lCKEditorItem != null && lCKEditorItem.getForm() != null && lCKEditorItem.getForm().getDetailSection() != null) {
									lCKEditorItem.getForm().getDetailSection().open();
								}
							}
						} else {
							boolean itemValid = item.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
									item.getForm().getDetailSection().open();
								}
							}
						}
					}
				}
			}
		}		
		return valid;
	}

	public Record getRecordToSave() {
		final Record lRecordToSave = new Record(vm.getValues());
		if(nomeFascicoloItem != null) {			
			lRecordToSave.setAttribute("nomeFascicolo", nomeFascicoloItem.getValueAsString());
		}
		if (attributiAddFolderDetails != null) {
			lRecordToSave.setAttribute("rowidFolder", rowidFolder);
			lRecordToSave.setAttribute("valori", getAttributiDinamiciFolder());
			lRecordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciFolder());
		}
		return lRecordToSave;
	}

	public void newMode() {

		super.newMode();

		if(taskSection != null) {
			taskSection.hide();
		}
		if(assegnazioneSection != null) {
			Record lRecordAssegnazione = new Record();
			RecordList lRecordList = new RecordList();
			if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				Record lRecord = new Record();	
				lRecord.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				lRecordList.add(lRecord);	
			} else {
				lRecordList.add(new Record());
			}
			lRecordAssegnazione.setAttribute("listaAssegnazioni", lRecordList);
			assegnazioneForm.setValues(lRecordAssegnazione.toMap());
			assegnazioneItem.resetCanvasChanged();
			assegnazioneSection.show();
			assegnazioneSection.setTitle(I18NUtil.getMessages().archivio_detail_assegnazioneSection_title());			
		}
		if(condivisioneSection != null) {
			condivisioneSection.setTitle(I18NUtil.getMessages().archivio_detail_condivisioneSection_title());
			condivisioneSection.showFirstCanvas();
		}
		if(operazioniEffettuateButton != null) {
			operazioniEffettuateButton.hide();
		}
	}

	public void viewMode() {
		
		super.viewMode();
		
		Record lDetailRecord = new Record(vm.getValues());
		
		if(taskSection != null) {
			taskSection.hide();
		}
		if(assegnazioneSection != null) {
			RecordList listaAssegnazioni = lDetailRecord.getAttributeAsRecordList("listaAssegnazioni");
			boolean isSettato = false;
			if (listaAssegnazioni != null && listaAssegnazioni.getLength() == 1) {
				Record lRecord = listaAssegnazioni.get(0);
				if (lRecord.getAttribute("idUo") != null && !"".equals(lRecord.getAttribute("idUo"))) {
					isSettato = true;
				}
			}
			if(!isSettato) {
				Record lRecordAssegnazione = new Record();
				RecordList lRecordList = new RecordList();
				lRecordList.add(new Record());
				lRecordAssegnazione.setAttribute("listaAssegnazioni", lRecordList);
				assegnazioneForm.setValues(lRecordAssegnazione.toMap());
				assegnazioneItem.resetCanvasChanged();
			}
			assegnazioneSection.show();
			assegnazioneSection.setTitle(I18NUtil.getMessages().archivio_detail_assegnazioneSection_readonly_title());
		}
		if(condivisioneSection != null) {
			condivisioneSection.setTitle(I18NUtil.getMessages().archivio_detail_condivisioneSection_readonly_title());
		}
		if(operazioniEffettuateButton != null) {
			operazioniEffettuateButton.show();
		}
		
		setTitleTabDatiFascicolo(lDetailRecord);		
	}

	public void editMode() {
		
		super.editMode();
		
		Record lDetailRecord = new Record(vm.getValues());
		
		if(taskSection != null) {
			taskSection.hide();
		}
		if(assegnazioneSection != null) {			
			RecordList listaAssegnazioni = lDetailRecord.getAttributeAsRecordList("listaAssegnazioni");
			Record lRecordAssegnazione = new Record();
			RecordList lRecordList = new RecordList();
			if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
				Record lRecord = new Record();	
				lRecord.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
				lRecordList.add(lRecord);	
			} else {
				lRecordList.add(new Record());
			}
			lRecordAssegnazione.setAttribute("listaAssegnazioni", lRecordList);
			assegnazioneForm.setValues(lRecordAssegnazione.toMap());
			assegnazioneItem.resetCanvasChanged();
			boolean isSettato = false;
			if (listaAssegnazioni != null && listaAssegnazioni.getLength() == 1) {
				Record lRecord = listaAssegnazioni.get(0);
				if (lRecord.getAttribute("idUo") != null && !"".equals(lRecord.getAttribute("idUo"))) {
					isSettato = true;
				}
			}
			if (isSettato && !lDetailRecord.getAttributeAsBoolean("abilAssegnazioneSmistamento")) {
				assegnazioneSection.hide();
			}
			assegnazioneSection.setTitle(I18NUtil.getMessages().archivio_detail_assegnazioneSection_title());
		}
		if(condivisioneSection != null) {			
			Record lRecordCondivisione = new Record();
			lRecordCondivisione.setAttribute("listaDestInvioCC", new RecordList());
			condivisioneForm.setValues(lRecordCondivisione.toMap());
			condivisioneItem.resetCanvasChanged();
			condivisioneSection.setTitle(I18NUtil.getMessages().archivio_detail_condivisioneSection_title());
			condivisioneSection.showFirstCanvas();
		}
		if(operazioniEffettuateButton != null) {			
			operazioniEffettuateButton.show();
		}
		
		setTitleTabDatiFascicolo(lDetailRecord);	
	}

	@Override
	public void editNewRecord() {
		vm.clearErrors(true);
		clearTabErrors(tabSet);

		super.editNewRecord();

		String idFolderApp = ((CustomAdvancedTreeLayout) getLayout()).getNavigator().getCurrentNode().getIdFolder();
		idFolderAppItem.setValue(idFolderApp);
		flgUdFolderItem.setValue("F");
		flgFascTitolarioItem.setValue(true);
		loadCombo();

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			caricaAttributiDinamiciFolder(folderType, null);
		}
		
		showHideSections();
	}

	@Override
	public void editNewRecord(Map initialValues) {
		
		// Setto il nome del tipo folder nella select
		if(idFolderTypeItem != null) {
			if (initialValues != null && initialValues.get("idFolderType") != null && !"".equalsIgnoreCase((String) initialValues.get("idFolderType")) && 
				initialValues.get("descFolderType") != null && !"".equalsIgnoreCase((String) initialValues.get("descFolderType"))){
				LinkedHashMap<String, String> folderValueMap = new LinkedHashMap<String, String>();
				folderValueMap.put((String)initialValues.get("idFolderType"), (String)initialValues.get("descFolderType"));
				idFolderTypeItem.setValueMap(folderValueMap);
			}
		}
		
		// Setto il livello di riservatezza
		if(livelloRiservatezzaItem != null) {
			if (initialValues != null && initialValues.get("livelloRiservatezza") != null && !"".equalsIgnoreCase((String) initialValues.get("livelloRiservatezza")) && 
				initialValues.get("desLivelloRiservatezza") != null && !"".equalsIgnoreCase((String) initialValues.get("desLivelloRiservatezza"))){
				LinkedHashMap<String, String> riservatezzaValueMap = new LinkedHashMap<String, String>();
				riservatezzaValueMap.put((String)initialValues.get("livelloRiservatezza"), (String)initialValues.get("desLivelloRiservatezza"));
				livelloRiservatezzaItem.setValueMap(riservatezzaValueMap);
			}
		}
		
		// Setto il livello di priorità
		if(prioritaItem != null) {
			if (initialValues != null && initialValues.get("priorita") != null && !"".equalsIgnoreCase((String) initialValues.get("priorita")) && 
				initialValues.get("desPriorita") != null && !"".equalsIgnoreCase((String) initialValues.get("desPriorita"))){
				LinkedHashMap<String, String> prioritaValueMap = new LinkedHashMap<String, String>();
				prioritaValueMap.put((String)initialValues.get("priorita"), "<div><img src=\"images/protocollazione/riservatezza/" + (String)initialValues.get("priorita") + ".png\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + (String)initialValues.get("desPriorita") + "</div>");
				prioritaItem.setValueMap(prioritaValueMap);
			}
		}

		vm.clearErrors(true);
		clearTabErrors(tabSet);

		super.editNewRecord(initialValues);

		flgUdFolderItem.setValue("F");
		flgFascTitolarioItem.setValue(true);
		loadCombo();

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			this.folderType = new Record(initialValues).getAttribute("idFolderType");
			caricaAttributiDinamiciFolder(folderType, null);
		}
		
		showHideSections();
	}

	@Override
	public void editRecord(Record record) {
		
		// Setto il nome del tipo folder nella select
		if(idFolderTypeItem != null) {
			if (record.getAttribute("idFolderType") != null && !"".equalsIgnoreCase(record.getAttribute("idFolderType")) && 
				record.getAttribute("descFolderType") != null && !"".equalsIgnoreCase(record.getAttribute("descFolderType"))){
				LinkedHashMap<String, String> folderValueMap = new LinkedHashMap<String, String>();
				folderValueMap.put(record.getAttribute("idFolderType"), record.getAttribute("descFolderType"));
				idFolderTypeItem.setValueMap(folderValueMap);
			}
		}
			    
		// Setto il livello di riservatezza
		if(livelloRiservatezzaItem != null) {
			if (record.getAttribute("livelloRiservatezza") != null && !"".equalsIgnoreCase(record.getAttribute("livelloRiservatezza")) && 
				record.getAttribute("desLivelloRiservatezza") != null && !"".equalsIgnoreCase(record.getAttribute("desLivelloRiservatezza"))){
				LinkedHashMap<String, String> riservatezzaValueMap = new LinkedHashMap<String, String>();
				riservatezzaValueMap.put(record.getAttribute("livelloRiservatezza"), record.getAttribute("desLivelloRiservatezza"));
				livelloRiservatezzaItem.setValueMap(riservatezzaValueMap);
			}
		}
		
		// Setto il livello di priorità
		if(prioritaItem != null) {
			if (record.getAttribute("priorita") != null && !"".equalsIgnoreCase(record.getAttribute("priorita")) && 
				record.getAttribute("desPriorita") != null && !"".equalsIgnoreCase(record.getAttribute("desPriorita"))){
				LinkedHashMap<String, String> prioritaValueMap = new LinkedHashMap<String, String>();
				prioritaValueMap.put(record.getAttribute("priorita"), "<div><img src=\"images/protocollazione/riservatezza/" + record.getAttribute("priorita") + ".png\" width=\"14\" height=\"14\" style=\"vertical-align:middle;\" /> " + record.getAttribute("desPriorita") + "</div>");
				prioritaItem.setValueMap(prioritaValueMap);
			}
		}
				    
		vm.clearErrors(true);
		clearTabErrors(tabSet);

		super.editRecord(record);

		flgUdFolderItem.setValue("F");
		flgFascTitolarioItem.setValue(true);
		loadCombo();

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			this.folderType = record.getAttribute("idFolderType");
			this.rowidFolder = record.getAttribute("rowidFolder");
			caricaAttributiDinamiciFolder(folderType, rowidFolder);
		}
		
		showHideSections();
	}
	
	public void showHideSections() {
		boolean hasTemplateNomeFolder = templateNomeFolderItem.getValue() != null && !"".equals(templateNomeFolderItem.getValue());
		if ((mode != null && mode.equals("view")) || !hasTemplateNomeFolder) {
			datiidentificativiSection.show();
		} else {		
			datiidentificativiSection.hide();
		}
	}

	public void loadCombo() {
		
		Record record = new Record(getValuesManager().getValues());
		
		if(idFolderTypeItem != null) {			
			GWTRestDataSource idFolderTypeDS = (GWTRestDataSource) idFolderTypeItem.getOptionDataSource();
			idFolderTypeDS.addParam("idClassifica", record.getAttribute("idClassifica"));
			idFolderTypeDS.addParam("idFolderApp", record.getAttribute("idFolderApp"));
			idFolderTypeDS.addParam("idFolderType", record.getAttribute("idFolderType"));
			idFolderTypeItem.setOptionDataSource(idFolderTypeDS);
			// idFolderTypeItem.fetchData();
		}
	}
	
	public DynamicForm getForm() {
		return form;
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);

		// i campi della sezione SEGNATURA devono essere sempre read-only
		if(estremiForm != null) {
			setCanEdit(false, estremiForm);			
			if(indiceClassificaItem != null) {
				indiceClassificaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(descClassificaItem != null) {
				descClassificaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(annoFascicoloItem != null) {
				annoFascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(nroFascicoloItem != null) {
				nroFascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(nroSottofascicoloItem != null) {
				nroSottofascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(nroInsertoItem != null) {
				nroInsertoItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(desUserAperturaFascicoloItem != null) {
				desUserAperturaFascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(desUserChiusuraFascicoloItem != null) {
				desUserChiusuraFascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(dataAperturaFascicoloItem != null) {
				dataAperturaFascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(dataChiusuraFascicoloItem != null) {
				dataChiusuraFascicoloItem.setCanEdit(false);
				dataChiusuraFascicoloItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(codiceItem != null) {
				if(canEdit) {
					codiceItem.setCanEdit(true);
				} else {
					codiceItem.setCanEdit(false);
					codiceItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
				}
			}
			if(capofilaItem != null) {
				capofilaItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
			if(statoFascItem != null) {
				statoFascItem.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
			}
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI")) {
			if(idFolderTypeItem != null) {
				idFolderTypeItem.setCanEdit(false);
			}
		}

		if (attributiAddFolderDetails != null) {
			for (String key : attributiAddFolderDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddFolderDetails.get(key);
				detail.setCanEdit(canEdit);
			}
		}
	}

	public void caricaAttributiDinamiciFolder(final String idFolderType, final String rowidFolder) {
		if (idFolderType != null && !"".equals(idFolderType)) {
			Record lRecordLoad = new Record();
			lRecordLoad.setAttribute("idFolderType", idFolderType);
			new GWTRestService<Record, Record>("LoadComboGruppiAttrCustomTipoFolderDataSource").call(lRecordLoad, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					final boolean isReload = (attributiAddFolderTabs != null && attributiAddFolderTabs.size() > 0);
					attributiAddFolderTabs = (LinkedHashMap<String, String>) object.getAttributeAsMap("gruppiAttributiCustomTipoFolder");
					attributiAddFolderLayouts = new HashMap<String, VLayout>();
					attributiAddFolderDetails = new HashMap<String, AttributiDinamiciDetail>();
					if (attributiAddFolderTabs != null && attributiAddFolderTabs.size() > 0) {
						GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
						lGwtRestService.addParam("flgNomeAttrConSuff", "true");
						Record lAttributiDinamiciRecord = new Record();
						lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_FOLDER");
						lAttributiDinamiciRecord.setAttribute("rowId", rowidFolder);
						lAttributiDinamiciRecord.setAttribute("tipoEntita", idFolderType);
						lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

							@Override
							public void execute(Record object) {
								RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
								if (attributiAdd != null && !attributiAdd.isEmpty()) {
									for (final String key : attributiAddFolderTabs.keySet()) {
										RecordList attributiAddCategoria = new RecordList();
										for (int i = 0; i < attributiAdd.getLength(); i++) {
											Record attr = attributiAdd.get(i);
											if (attr.getAttribute("categoria") != null
													&& (attr.getAttribute("categoria").equalsIgnoreCase(key) || ("HEADER_" + attr.getAttribute("categoria"))
															.equalsIgnoreCase(key))) {
												attributiAddCategoria.add(attr);
											}
										}
										if (!attributiAddCategoria.isEmpty()) {
											if(key.equals("#HIDDEN")) {
												// Gli attributi che fanno parte di questo gruppo non li considero
											} else if (key.startsWith("HEADER_")) {
												AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
														.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
														.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
														tabSet, "HEADER");
												detail.setCanEdit(new Boolean(editing));
												attributiAddFolderDetails.put(key, detail);
												VLayout layout = (VLayout) layoutTabFolder.getMembers()[0];
												attributiAddFolderLayouts.put(key, layout);
												int pos = 0;
												for (Canvas member : layout.getMembers()) {
													if (member instanceof HeaderDetailSection) {
														pos++;
													} else {
														break;
													}
												}
												if (key.equals("HEADER_NOME")) {
													datiidentificativiSection.setVisible(false);
												}
												for (DetailSection detailSection : attributiAddFolderDetails.get(key).getDetailSections()) {
													if (isReload) {
														((DetailSection) layout.getMember(pos++)).setForms(detailSection.getForms());
													} else {
														layout.addMember(detailSection, pos++);
													}
												}
											} else {
												AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
														.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
														.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null,
														tabSet, key);
												detail.setCanEdit(new Boolean(editing));
												attributiAddFolderDetails.put(key, detail);
												VLayout layout = new VLayout();
												layout.setHeight100();
												layout.setWidth100();
												layout.setMembers(detail);
												attributiAddFolderLayouts.put(key, layout);
												VLayout layoutTab = new VLayout();
												layoutTab.addMember(layout);
												if (tabSet.getTabWithID(key) != null) {
													tabSet.getTabWithID(key).setPane(layoutTab);
												} else {
													Tab tab = new Tab("<b>" + attributiAddFolderTabs.get(key) + "</b>");
													tab.setAttribute("tabID", key);
													tab.setPrompt(attributiAddFolderTabs.get(key));
													tab.setPane(layoutTab);
													tabSet.addTab(tab);
												}
											}
										}
									}
								}
							}
						});
					}
				}
			});
		}
	}

	public Map<String, Object> getAttributiDinamiciFolder() {
		Map<String, Object> attributiDinamiciFolder = null;
		if (attributiAddFolderTabs != null) {
			for (String key : attributiAddFolderTabs.keySet()) {
				if (attributiAddFolderDetails != null && attributiAddFolderDetails.get(key) != null) {
					if (attributiDinamiciFolder == null) {
						attributiDinamiciFolder = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddFolderDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddFolderDetails.get(key).getRecordToSave();
					attributiDinamiciFolder.putAll(attributiAddFolderDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciFolder;
	}

	public Map<String, String> getTipiAttributiDinamiciFolder() {
		Map<String, String> tipiAttributiDinamiciFolder = null;
		if (attributiAddFolderTabs != null) {
			for (String key : attributiAddFolderTabs.keySet()) {
				if (attributiAddFolderDetails != null && attributiAddFolderDetails.get(key) != null) {
					if (tipiAttributiDinamiciFolder == null) {
						tipiAttributiDinamiciFolder = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddFolderDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddFolderDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciFolder.putAll(attributiAddFolderDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciFolder;
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Stampa"
	 * 
	 */
	public void clickStampa(String nomeModello) {
		Record detailRecord = new Record(vm.getValues());
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");	
		lGwtRestDataSource.addParam("nomeModello", nomeModello);
		lGwtRestDataSource.addParam("nomeFileStampa", getNomeFileStampaFromModello(nomeModello));
		lGwtRestDataSource.executecustom("creaStampa", detailRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					String nomeFilePdf = result.getAttribute("nomeFile");
					String uriFilePdf = result.getAttribute("uri");
					InfoFileRecord infoFilePdf = InfoFileRecord.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFile").getJsObj()));
					PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
				}
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Restituisci"
	 */
	public void clickRestituisci() {
		final Record detailRecord = new Record(vm.getValues());
		RestituzionePopup restituzionePopup = new RestituzionePopup() {

			@Override
			public void onClickOkButton(final DSCallback callback) {
				
				final RecordList listaUdFolder = new RecordList();
				listaUdFolder.add(detailRecord);
				final Record record = new Record();
				record.setAttribute("listaRecord", listaUdFolder);
				record.setAttribute("flgIgnoreWarning", _form.getValue("flgIgnoreWarning"));
				record.setAttribute("messaggio", _form.getValue("messaggio"));
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("RestituzioneDataSource");
				lGwtRestDataSource.addData(record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(callback != null){
							callback.execute(response, rawData, request);
						}
						operationCallbackRestituisci(response, detailRecord, _form, new DSCallback() {
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {														
								
								if (detailRecord.getAttribute("flgUdFolder") != null &&
										"U".equalsIgnoreCase(detailRecord.getAttribute("flgUdFolder"))) {
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUd", detailRecord.getAttribute("idUd"));
									GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource", "idUd", FieldType.TEXT);
									if(layout != null && layout instanceof ScrivaniaLayout) {
										lGwtRestDataSourceProtocollo.addParam("idNode", ((ScrivaniaLayout)layout).getIdNode());
									}
									lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Record record = response.getData()[0];
												editRecord(record);
												setSaved(true);
												if(layout != null) {
													layout.viewMode();
												} else {
													viewMode();
												}
												manageStampaEtichettaPostAssegnazione(record);
											}
										}
									});
									
//									reload(new DSCallback() {
//										@Override
//										public void execute(DSResponse response, Object rawData, DSRequest request) {
//											setSaved(true);
//											if (layout != null) {
//												layout.viewMode();
//											} else {
//												viewMode();
//											}
//											manageStampaEtichettaPostAssegnazione(detailRecord);
//										}
//									});
								} else if (detailRecord.getAttribute("flgUdFolder") != null &&
										"F".equalsIgnoreCase(detailRecord.getAttribute("flgUdFolder"))) {
									Record lRecordToLoad = new Record();
									lRecordToLoad.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
									GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
									if(layout != null && layout instanceof ScrivaniaLayout) {
										lGwtRestDataSource.addParam("idNode", ((ScrivaniaLayout)layout).getIdNode());
									}
									lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Record record = response.getData()[0];
												editRecord(record);
												setSaved(true);
												if(layout != null) {
													layout.viewMode();
												} else {
													viewMode();
												}
											}
										}
									});
								}
							}
						});
					}
				});
			}
		};
		restituzionePopup.show();
	}
	
	/**
	 * Metodo per la stampa delle etichette in fase di post-assegnazione UD
	 */
	private void manageStampaEtichettaPostAssegnazione(Record detailRecord) {
		
		String codSupportoOrig = detailRecord != null && detailRecord.getAttributeAsString("codSupportoOrig") != null ? 
				detailRecord.getAttributeAsString("codSupportoOrig") : null;
		String segnaturaXOrd = detailRecord != null && detailRecord.getAttributeAsString("segnaturaXOrd") != null ? 
				detailRecord.getAttributeAsString("segnaturaXOrd") : null;
		
		if( (codSupportoOrig != null && "C".equals(codSupportoOrig)) &&
			(segnaturaXOrd != null && (segnaturaXOrd.startsWith("1-") || segnaturaXOrd.startsWith("2-"))) &&
			AurigaLayout.getParametroDBAsBoolean("ATTIVA_STAMPA_AUTO_ETICH_POST_ASS") &&
			AurigaLayout.getImpostazioneStampaAsBoolean("stampaEtichettaAutoReg") ){
				
			final Record recordToPrint = new Record();
			recordToPrint.setAttribute("idUd", detailRecord.getAttribute("idUd"));
			recordToPrint.setAttribute("listaAllegati", detailRecord.getAttributeAsRecordList("listaAllegati"));
			recordToPrint.setAttribute("idDoc", detailRecord.getAttribute("idDocPrimario"));
			if(AurigaLayout.getImpostazioneStampaAsBoolean("skipSceltaOpzStampa")){
								
				/**
				 * Viene verificato che sia stata selezionata una stampante in precedenza
				 */
				if(AurigaLayout.getImpostazioneStampa("stampanteEtichette") != null && 
						!"".equals(AurigaLayout.getImpostazioneStampa("stampanteEtichette"))){
					buildStampaEtichettaAutoPostAss(recordToPrint, null);
				} else {
					PrinterScannerUtility.printerScanner("", new PrinterScannerCallback() {

						@Override
						public void execute(String nomeStampante) {
							recordToPrint.setAttribute("nomeStampante", nomeStampante);
							buildStampaEtichettaAutoPostAss(recordToPrint,nomeStampante);
						}
					}, new PrinterScannerCallback() {
						
						@Override
						public void execute(String nomeStampante) {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().protocollazione_detail_stampaEtichettaPostRegSenzaOpzStampa_errorMessage(),
									"", MessageType.ERROR));
						}
					});
				}	
			} else {
				recordToPrint.setAttribute("flgHideBarcode", true);
				StampaEtichettaPopup stampaEtichettaPopup = new StampaEtichettaPopup(recordToPrint);
				stampaEtichettaPopup.show();
			}
		}
	}
	
	/**
	 * Stampa dell'etichetta post-assegnazione
	 */
	private void buildStampaEtichettaAutoPostAss(Record record, String nomeStampante) {
		
		Layout.showWaitPopup("Stampa etichetta in corso...");
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttribute("idUd"));
		lRecord.setAttribute("listaAllegati", record.getAttributeAsRecordList("listaAllegati"));
		if(nomeStampante == null || "".equals(nomeStampante)) {
			nomeStampante = AurigaLayout.getImpostazioneStampa("stampanteEtichette");
		}
		lRecord.setAttribute("nomeStampante", nomeStampante);
		lRecord.setAttribute("nroEtichette",  "1");		
		lRecord.setAttribute("flgPrimario", AurigaLayout.getImpostazioneStampaAsBoolean("flgPrimario"));
		lRecord.setAttribute("flgAllegati", AurigaLayout.getImpostazioneStampaAsBoolean("flgAllegati"));
		lRecord.setAttribute("flgRicevutaXMittente", AurigaLayout.getImpostazioneStampaAsBoolean("flgRicevutaXMittente"));
		lRecord.setAttribute("flgHideBarcode", true);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_ETICHETTA_ORIG_COPIA")){			
			lRecord.setAttribute("notazioneCopiaOriginale",  AurigaLayout.getImpostazioneStampa("notazioneCopiaOriginale"));
		}
		
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("PreparaValoriEtichettaDatasource");
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				final String nomeStampante = object.getAttribute("nomeStampante");
				final Record[] etichette = object.getAttributeAsRecordArray("etichette");
				final String numCopie = object.getAttribute("nroEtichette");
				StampaEtichettaUtility.stampaEtichetta("", "", nomeStampante, "", etichette, numCopie, new StampaEtichettaCallback() {

					@Override
					public void execute() {
					
					}
				});
			}

			@Override
			public void manageError() {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Impossibile stampare l'etichetta", "", MessageType.ERROR));
			}
		});
	}
	
	private void operationCallbackRestituisci(DSResponse response, final Record record, DynamicForm _form, final DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null) {
				if (errorMessages.get(record.getAttribute("idUd")) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute("idUd"));
				} else {
					errorMsg = "Si è verificato un errore durante la restituzione!";
				}
			}
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				if (data.getAttributeAsInt("flgIgnoreWarning") != 1) {
					Layout.addMessage(new MessageBean("Restituzione effettuata con successo", "", MessageType.INFO));
					layout.reloadListAndSetCurrentRecord(record);
					if (callback != null) {
						callback.execute(new DSResponse(), null, new DSRequest());
					}
				} else {
					_form.setValue("flgIgnoreWarning", "1");
				}
			}
		}
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Segna come visionato"
	 */
	public void clickSegnaComeVisionato() {
		final Record detailRecord = new Record(vm.getValues());
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);		
		lGwtRestDataSource.addParam("flgUdFolder", detailRecord.getAttribute("flgUdFolder"));
		lGwtRestDataSource.addParam("idUdFolder", detailRecord.getAttribute("idUdFolder"));
		lGwtRestDataSource.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					RecordList listaUoDestNotifiche = response.getDataAsRecordList();
					
					String title = null;
					
					if(detailRecord.getAttribute("flgUdFolder").equals("U")){
						//Documento
						title = I18NUtil.getMessages().segnaComeVisionato_Popup_Ud_title(getEstremiUdFromList(detailRecord));
					}else if(detailRecord.getAttribute("flgUdFolder").equals("F")){
						//Fascicolo
						title = I18NUtil.getMessages().segnaComeVisionato_Popup_Folder_title(getEstremiFolderFromList(detailRecord));
					}
					SegnaComeVisionatoPopup segnaComeVisionatoPopup = new SegnaComeVisionatoPopup(title, null, listaUoDestNotifiche) {

						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							RecordList listaUdFolderNoteAggiornate = new RecordList();
							String noteInserita = object.getAttribute("note"); 
							Record udFolderNoteAggiornate = new Record();
							udFolderNoteAggiornate.setAttribute("flgUdFolder", detailRecord.getAttribute("flgUdFolder"));
							udFolderNoteAggiornate.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
							udFolderNoteAggiornate.setAttribute("note", noteInserita);
							listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
							Record lRecordSelezionatiNoteAggiornate = new Record();
							lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
							lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));
							lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));
							GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
							lGwtRestDataSourceArchivio.executecustom("segnaComeVisionato", lRecordSelezionatiNoteAggiornate, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (callback != null) {
										callback.execute(response, rawData, request);
									}
									operationCallback(response, detailRecord, "idUdFolder", "Operazione effettuata con successo",
											"Si è verificato un errore durante l'aggiornamento !", new DSCallback() {

										@Override
										public void execute(DSResponse response, Object rawData,
												DSRequest request) {
											reload(new DSCallback() {
												@Override
												public void execute(DSResponse response, Object rawData,
														DSRequest request) {
													setSaved(true);
													if(layout != null) {
														layout.viewMode();
													} else {
														viewMode();
													}
												}
											});
										}
									});
								}
							});
						}
					};
					segnaComeVisionatoPopup.show();
				}
			}
		});
	}
	
	/**
	 * Metodo che implementa l'azione del bottone "Segna come archiviato"
	 */
	public void clickSegnaComeArchiviato() {
		final Record detailRecord = new Record(vm.getValues());
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboUoDestNotificheDataSource", "key", FieldType.TEXT);		
		lGwtRestDataSource.addParam("flgUdFolder", detailRecord.getAttribute("flgUdFolder"));
		lGwtRestDataSource.addParam("idUdFolder", detailRecord.getAttribute("idUdFolder"));
		final String idNodo = ((ScrivaniaLayout) layout).getIdNode();
		lGwtRestDataSource.addParam("altriParametriIn", "TIPO_AZIONE|*|A|*|CI_NODO_SCRIVANIA|*|" + idNodo);
		lGwtRestDataSource.fetchData(null, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					
					RecordList listaUoDestNotifiche = response.getDataAsRecordList();
					
					String title = null;
					
					if(detailRecord.getAttribute("flgUdFolder").equals("U")){
						//Documento
						title = I18NUtil.getMessages().segnaComeArchiviato_Popup_Ud_title(getEstremiUdFromList(detailRecord));
					}else if(detailRecord.getAttribute("flgUdFolder").equals("F")){
						//Fascicolo
						title = I18NUtil.getMessages().segnaComeArchiviato_Popup_Folder_title(getEstremiFolderFromList(detailRecord));
					}
					SegnaComeArchiviatoPopup segnaComeArchiviatoPopup = new SegnaComeArchiviatoPopup(title, null, listaUoDestNotifiche) {
						
						@Override
						public void onClickOkButton(Record object, final DSCallback callback) {
							RecordList listaUdFolderNoteAggiornate = new RecordList();
							String noteInserita = object.getAttribute("note"); 
							Record udFolderNoteAggiornate = new Record();
							udFolderNoteAggiornate.setAttribute("flgUdFolder", detailRecord.getAttribute("flgUdFolder"));
							udFolderNoteAggiornate.setAttribute("idUdFolder", detailRecord.getAttribute("idUdFolder"));
							udFolderNoteAggiornate.setAttribute("note", noteInserita);
							listaUdFolderNoteAggiornate.add(udFolderNoteAggiornate);
							Record lRecordSelezionatiNoteAggiornate = new Record();
							lRecordSelezionatiNoteAggiornate.setAttribute("listaRecord", listaUdFolderNoteAggiornate);
							lRecordSelezionatiNoteAggiornate.setAttribute("listaUoSelezionate", object.getAttributeAsRecordList("listaUoSelezionate"));
							lRecordSelezionatiNoteAggiornate.setAttribute("flgAnchePerUtente", object.getAttributeAsRecordList("flgAnchePerUtente"));
							lRecordSelezionatiNoteAggiornate.setAttribute("idNodo", idNodo);
							GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
							lGwtRestDataSourceArchivio.executecustom("segnaComeArchiviato", lRecordSelezionatiNoteAggiornate, new DSCallback() {
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (callback != null) {
										callback.execute(response, rawData, request);
									}
									operationCallback(response, detailRecord, "idUdFolder", "Operazione effettuata con successo",
											"Si è verificato un errore durante l'aggiornamento !", new DSCallback() {
										
										@Override
										public void execute(DSResponse response, Object rawData,
												DSRequest request) {
											reload(new DSCallback() {
												@Override
												public void execute(DSResponse response, Object rawData,
														DSRequest request) {
													setSaved(true);
													if(layout != null) {
														layout.viewMode();
													} else {
														viewMode();
													}
												}
											});
										}
									});
								}
							});
						}
					};
					segnaComeArchiviatoPopup.show();
				}
			}
		});
	}
	
	/**
	 * Metodo che ricarica i dati di dettaglio (se si è in inserimento
	 *  i campi vengono ripuliti e riportati alla situazione
	 * iniziale)
	 * 
	 */
	public void reload(final DSCallback callback) {

		if (mode != null && mode.equals("new")) {
			vm.clearErrors(true);
			editNewRecord();
		} else {
			Record lRecordToLoad = new Record(vm.getValues());
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
			if(layout != null && layout instanceof ScrivaniaLayout) {
				lGwtRestDataSource.addParam("idNode", ((ScrivaniaLayout)layout).getIdNode());
			}
			lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						editRecord(record);
						if(callback != null){
							callback.execute(response, null, new DSRequest());
						}
						
					} else {
						Layout.addMessage(
								new MessageBean("Si è verificato un errore durante il caricamento del dettaglio", "",
										MessageType.ERROR));
					}
				}
			});
		}
	}
	
	private String getEstremiFolderFromList(Record record){
		String estremi = "";
		if(record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome"))){
			estremi = record.getAttribute("percorsoNome");
		}else{
			if (record.getAttributeAsString("annoFascicolo") != null && !"".equals(record.getAttributeAsString("annoFascicolo"))) {
				estremi += record.getAttributeAsString("annoFascicolo") + " ";
			}
			if (record.getAttributeAsString("indiceClassifica") != null && !"".equals(record.getAttributeAsString("indiceClassifica"))) {
				estremi += record.getAttributeAsString("indiceClassifica") + " ";
			}
			if (record.getAttributeAsString("nroFascicolo") != null && !"".equals(record.getAttributeAsString("nroFascicolo"))) {
				estremi += "N° " + record.getAttributeAsString("nroFascicolo");
				if (record.getAttributeAsString("nroSottofascicolo") != null && !"".equals(record.getAttributeAsString("nroSottofascicolo"))) {
					estremi += "/" + record.getAttributeAsString("nroSottofascicolo");
				}
				estremi += " ";
			}
			if (record.getAttributeAsString("nome") != null && !"".equals(record.getAttributeAsString("nome"))) {
				estremi += record.getAttributeAsString("nome");
			}
		}
		return estremi;
	}
	
	private String getEstremiUdFromList(Record record){
		return record.getAttribute("segnatura");
	}
	
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback) {
		operationCallback(response, record, pkField, successMessage, errorMessage, callback, null);
	}
	
	public void operationCallback(DSResponse response, Record record, String pkField, String successMessage, String errorMessage, DSCallback callback, DSCallback errorCallback) {
		
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			if (errorMessages != null && !errorMessages.isEmpty()) {
				if (errorMessages.get(record.getAttribute(pkField)) != null) {
					errorMsg = (String) errorMessages.get(record.getAttribute(pkField));
				} else {
					errorMsg = errorMessage != null ? errorMessage : "Si è verificato un errore durante l'operazione!";
				}
			}
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
				if (errorCallback != null) {
					errorCallback.execute(new DSResponse(), null, new DSRequest());
				}
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}
	
	public String getNomeFileStampaFromModello(String nomeModello) {
		if(nomeModello != null) {
			if(nomeModello.equals("COPERTINA_FASC")) {
				return "Copertina.pdf";
			} else if(nomeModello.equals("SEGNATURA_FASC")) {
				return "Segnatura.pdf";
			} else if(nomeModello.equals("LISTA_CONTENUTI_FASC")) {
				return "Lista contenuti.pdf";
			}				
		}
		return null;
	}

	/**
	 * Metodo che indica se mostrare o meno i bottoni relativi ai fascicoli collegati
	 * 
	 */
	public boolean showFascicoliCollegatiButton() {
		return true;
	}
	
	public boolean showFascicoliCollegatiButton(Record record) {
		return (record != null && (record.getAttributeAsBoolean("abilModificaDati") || record.getAttributeAsBoolean("abilGestioneCollegamentiFolder")));
	}	
}
