/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.NoteMancanzaFilePopup;
import it.eng.auriga.ui.module.layout.shared.bean.CelleExcelBeneficiariEnum;
import it.eng.auriga.ui.module.layout.shared.bean.CelleExcelDatiContabiliADSPEnum;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

public abstract class ListaDatiContabiliADSPItem extends GridItem {
	
	protected ListGridField id;	
	protected ListGridField mostraErrori;
	protected ListGridField flgEntrataUscita;	
	protected ListGridField annoEsercizio;
	protected ListGridField capitolo;
	protected ListGridField conto;
	protected ListGridField codiceCIG;
	protected ListGridField codiceCUP;
	protected ListGridField importo;
	protected ListGridField imponibile;
	protected ListGridField note;
	protected ListGridField ultimoImportoAllineato;
	protected ListGridField opera;
	protected ListGridField desOpera;
	protected ListGridField statoSistemaContabile;
	protected ListGridField disponibilitaImporto;
	protected ListGridField operazioneSistemaContabile;
	protected ListGridField erroreSistemaContabile;
	protected ListGridField keyCapitolo;
	protected ListGridField ultimoKeyCapitoloAllineato;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	protected ControlListGridField ripristinoButtonField;
		
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleEntrateLayout;
	protected Label totaleEntrateLabel;
	protected HLayout totaleUsciteLayout;
	protected Label totaleUsciteLabel;

	protected HashMap<String, HashSet<String>> mappaErrori = new HashMap<String, HashSet<String>>();	
	protected HashMap<String, String> mappaKeyCapitoli = new HashMap<String, String>();
	
	private static final String STATO_SISTEMA_CONTABILE_ALLINEATO = "allineato";
	private static final String STATO_SISTEMA_CONTABILE_NON_ALLINEATO = "non_allineato";
	private static final String STATO_SISTEMA_CONTABILE_DA_ALLINEARE = "da_allineare";
	private static final String STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING = "da_allineare_warning";
	
	private static final String STATO_DISPONIBILITA_IMPORTO_DISPONIBILE = "disponibile";
	private static final String STATO_DISPONIBILITA_IMPORTO_NON_DISPONIBILE = "non_disponibile";
	
	private static final String OPERAZIONE_SISTEMA_CONTABILE_INSERT = "insert";
	private static final String OPERAZIONE_SISTEMA_CONTABILE_UPDATE = "update";
	private static final String OPERAZIONE_SISTEMA_CONTABILE_DELETE = "delete";
	
//	/**RICORDARSI DI TENERE GLI INDICI ALLINEATI ANCHE NELL'IMPORT (ContabilitaADSPDataSource)  **/
//	private static final int CELLA_MOVIMENTO_E_U = 0;
//	private static final int CELLA_ANNO_ESERCIZIO = 1;
//	private static final int CELLA_CIG = 2;
//	private static final int CELLA_CUP = 3;
//	private static final int CELLA_CAPITOLO = 4;
//	private static final int CELLA_CONTO = 5;
//	private static final int CELLA_OPERA = 6;
//	private static final int CELLA_IMPORTO = 7;
//	private static final int CELLA_NOTE = 8;
//	private static final int CELLA_IMPONIBILE = 9;
//	
//	/** AGGIORNARE NUMERO CAMPI TOTALI SE SI AGGIUNGE UNA COLONNA**/
//	private static final int NUMERO_CAMPI = 10;
	
	private String uriFileImportExcel;
	
	public ListaDatiContabiliADSPItem(String name) {
		
		super(name, "listaDatiContabiliADSP");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);	
		
		keyCapitolo = new ListGridField("keyCapitolo");
		keyCapitolo.setHidden(true);
		keyCapitolo.setCanHide(false);
		
		ultimoKeyCapitoloAllineato = new ListGridField("ultimoKeyCapitoloAllineato");
		ultimoKeyCapitoloAllineato.setHidden(true);
		ultimoKeyCapitoloAllineato.setCanHide(false);		
		  		
		mostraErrori = new ListGridField("mostraErrori");
		mostraErrori.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		mostraErrori.setCanHide(false);
		mostraErrori.setCanDragResize(false);				
		mostraErrori.setWidth(25);		
		mostraErrori.setAttribute("custom", true);	
		mostraErrori.setAlign(Alignment.CENTER);
		mostraErrori.setShowHover(true);		
		mostraErrori.setCanReorder(false);
		mostraErrori.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(record.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {
					return buildImgButtonHtml("exclamation.png");
				}
				return null;
			}
		});
		mostraErrori.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(record.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {
					return "Mostra errori";
				}
				return null;				
			}
		});		
		mostraErrori.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(listRecord.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {					
					StringBuffer buffer = new StringBuffer();
					buffer.append("<ul>");
					for(String errore : listaErrori) {
						buffer.append("<li>" + errore + "</li>");			
					}
					buffer.append("</ul>");			
					SC.warn(buffer.toString());
				}																							
			}
		});	
		
		flgEntrataUscita = new ListGridField("flgEntrataUscita", "Entrata/Uscita");
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", "Entrata");
		flgEntrataUscitaValueMap.put("U", "Uscita");
		flgEntrataUscita.setValueMap(flgEntrataUscitaValueMap);
		
		annoEsercizio = new ListGridField("annoEsercizio", getTitleEsercizioDatiContabiliADSP());
		annoEsercizio.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showEsercizioDatiContabiliADSP();
			}
		});		
				
		capitolo = new ListGridField("capitolo", getTitleCapitoloDatiContabiliADSP());
		capitolo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showCapitoloDatiContabiliADSP();
			}
		});	
		
		conto = new ListGridField("conto", getTitleContoDatiContabiliADSP());
		conto.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showContoDatiContabiliADSP();
			}
		});	
		
		codiceCIG = new ListGridField("codiceCIG", getTitleDecretoCIGDatiContabiliADSP());
		codiceCIG.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showDecretoCIGDatiContabiliADSP();
			}
		});	

		codiceCUP = new ListGridField("codiceCUP", getTitleDecretoCUPDatiContabiliADSP());
		codiceCUP.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showDecretoCUPDatiContabiliADSP();
			}
		});	

		importo = new ListGridField("importo", getTitleDecretoImportoDatiContabiliADSP());
		importo.setType(ListGridFieldType.FLOAT);	
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});	
		importo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showDecretoImportoDatiContabiliADSP();
			}
		});	
				
		imponibile = new ListGridField("imponibile", getTitleDecretoImponibileDatiContabiliADSP());
		imponibile.setType(ListGridFieldType.FLOAT);	
		imponibile.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("imponibile"));				
			}
		});	
		imponibile.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showDecretoImponibileDatiContabiliADSP();
			}
		});	
		
		note = new ListGridField("note", "Note");
		note.setType(ListGridFieldType.ICON);
		note.setWidth(30);
		note.setIconWidth(16);
		note.setIconHeight(16);
		note.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("note") != null && !"".equalsIgnoreCase(record.getAttribute("note"))) {
					return buildImgButtonHtml("file/icon_postit.png");
				}
				return null;
			}
		});
		
		note.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("note") != null) {
					return "Annotazioni apposte";
				}
				return null;
			}
		});
		
		note.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final Record record = event.getRecord();
				NoteMancanzaFilePopup popupNote = new NoteMancanzaFilePopup("Note", record.getAttribute("note"), false);
				popupNote.show();
			}
		});
		
		ultimoImportoAllineato = new ListGridField("ultimoImportoAllineato");
		ultimoImportoAllineato.setType(ListGridFieldType.FLOAT);	
		ultimoImportoAllineato.setHidden(true);
		ultimoImportoAllineato.setCanHide(false);

		opera  = new ListGridField("opera");
		opera.setHidden(true);
		opera.setCanHide(false);
		
		desOpera = new ListGridField("desOpera", getTitleDecretoOperaDatiContabiliADSP());
		desOpera.setAttribute("custom", true);
		desOpera.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("opera") != null && !"".equals(record.getAttribute("opera"))) {					
					if(record.getAttribute("desOpera")!= null && !"".equals(record.getAttribute("desOpera"))) {
						return record.getAttribute("desOpera");
					} 
					/*
					else {
						LinkedHashMap<String, String> lOpereADSPValueMap = getOpereADSPValueMap();
						if(lOpereADSPValueMap.containsKey(record.getAttribute("opera"))) {
							return lOpereADSPValueMap.get(record.getAttribute("opera"));
						}
					}
					*/					
					return record.getAttribute("opera");
				}	
				return null;
			}
		});
		desOpera.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showDecretoOperaDatiContabiliADSP();
			}
		});	
		
		statoSistemaContabile  = new ListGridField("statoSistemaContabile", "Stato");
		statoSistemaContabile.setType(ListGridFieldType.ICON);
		statoSistemaContabile.setWidth(40);
		statoSistemaContabile.setIconWidth(16);
		statoSistemaContabile.setIconHeight(16);
		Map<String, String> statoSistemaContabileValueIcons = new HashMap<String, String>();		
		statoSistemaContabileValueIcons.put(STATO_SISTEMA_CONTABILE_NON_ALLINEATO, "buttons/rosso.png");
		statoSistemaContabileValueIcons.put(STATO_SISTEMA_CONTABILE_DA_ALLINEARE, "buttons/giallo.png");
		statoSistemaContabileValueIcons.put(STATO_SISTEMA_CONTABILE_ALLINEATO, "buttons/verde.png");
		statoSistemaContabileValueIcons.put(STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING, "buttons/gialloWarning.png");
		statoSistemaContabileValueIcons.put("undefined", "blank.png");
		statoSistemaContabileValueIcons.put("", "blank.png");
		statoSistemaContabile.setValueIcons(statoSistemaContabileValueIcons);		
		statoSistemaContabile.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if (record.getAttribute("statoSistemaContabile") != null) {
					if (STATO_SISTEMA_CONTABILE_NON_ALLINEATO.equals(record.getAttribute("statoSistemaContabile"))) {
						return record.getAttribute("erroreSistemaContabile");
					} else if (STATO_SISTEMA_CONTABILE_DA_ALLINEARE.equals(record.getAttribute("statoSistemaContabile"))) {
						if (record.getAttribute("erroreSistemaContabile") != null && !"".equals(record.getAttribute("erroreSistemaContabile"))) {
							return record.getAttribute("erroreSistemaContabile");
						} else {
							return "Da allineare";
						}
					} else if (STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING.equals(record.getAttribute("statoSistemaContabile"))) {
						return record.getAttribute("erroreSistemaContabile");
					} else if (STATO_SISTEMA_CONTABILE_ALLINEATO.equals(record.getAttribute("statoSistemaContabile"))) {
						return "Allineato";
					} 
				}
								
				return null;
			}
		});
		
		disponibilitaImporto  = new ListGridField("disponibilitaImporto", "Disponibilità importo");
		disponibilitaImporto.setType(ListGridFieldType.ICON);
		disponibilitaImporto.setWidth(40);
		disponibilitaImporto.setIconWidth(16);
		disponibilitaImporto.setIconHeight(16);
		Map<String, String> statoDisponibilitaImportoValueIcons = new HashMap<String, String>();		
		statoDisponibilitaImportoValueIcons.put(STATO_DISPONIBILITA_IMPORTO_DISPONIBILE, "pratiche/task/icone/svolta_OK.png");
		statoDisponibilitaImportoValueIcons.put(STATO_DISPONIBILITA_IMPORTO_NON_DISPONIBILE, "pratiche/task/icone/svolta_KO.png");
		statoDisponibilitaImportoValueIcons.put("undefined", "pratiche/task/icone/svolta_W.png");
		statoDisponibilitaImportoValueIcons.put("", "pratiche/task/icone/svolta_W.png");
		disponibilitaImporto.setValueIcons(statoDisponibilitaImportoValueIcons);		
		disponibilitaImporto.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if (record.getAttribute("disponibilitaImporto") != null) {
					if (STATO_DISPONIBILITA_IMPORTO_DISPONIBILE.equals(record.getAttribute("disponibilitaImporto"))) {
						return "Importo disponibile";
					} else if (STATO_DISPONIBILITA_IMPORTO_NON_DISPONIBILE.equals(record.getAttribute("disponibilitaImporto"))) {
						return "Importo non disponibile";
					} else {
						return "Disponibilità importo da verificare";
					} 
				}
				
				return "Disponibilità importo da verificare";
			}
		});
		
		
		operazioneSistemaContabile  = new ListGridField("operazioneSistemaContabile", "Operazione");
		operazioneSistemaContabile.setHidden(true);
		operazioneSistemaContabile.setCanHide(false);
		
		erroreSistemaContabile  = new ListGridField("erroreSistemaContabile", "Errore");
		erroreSistemaContabile.setHidden(true);
		erroreSistemaContabile.setCanHide(false);
		
		setGridFields(
			id,
			mostraErrori,
			flgEntrataUscita,
			annoEsercizio,
			capitolo,
			conto,
			codiceCIG,
			codiceCUP,
			importo,
			imponibile,
			ultimoImportoAllineato,
			opera,
			desOpera,
			note,
			statoSistemaContabile,
			disponibilitaImporto,
			operazioneSistemaContabile,
			erroreSistemaContabile,
			keyCapitolo,
			ultimoKeyCapitoloAllineato
		);		
	}
	
	@Override
	public void init(FormItem item) {
		
		super.init(item);
		
		totaliToolStrip = new ToolStrip();
		totaliToolStrip.setBackgroundColor("transparent");
		totaliToolStrip.setBackgroundImage("blank.png");
		totaliToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		totaliToolStrip.setBorder("0px");
		totaliToolStrip.setWidth100();           
		totaliToolStrip.setHeight(30);
		
		totaleEntrateLayout = new HLayout();
		totaleEntrateLayout.setOverflow(Overflow.VISIBLE);
		totaleEntrateLayout.setWidth(5);
	
		totaleEntrateLabel = new Label();
		totaleEntrateLabel.setAlign(Alignment.CENTER);
		totaleEntrateLabel.setValign(VerticalAlignment.CENTER);
		totaleEntrateLabel.setWrap(false);
		
		totaleEntrateLayout.setMembers(totaleEntrateLabel);
		
		totaleUsciteLayout = new HLayout();
		totaleUsciteLayout.setOverflow(Overflow.VISIBLE);
		totaleUsciteLayout.setWidth(5);
	
		totaleUsciteLabel = new Label();
		totaleUsciteLabel.setAlign(Alignment.CENTER);
		totaleUsciteLabel.setValign(VerticalAlignment.CENTER);
		totaleUsciteLabel.setWrap(false);
		
		totaleUsciteLayout.setMembers(totaleUsciteLabel);
		
		totaliToolStrip.addMember(totaleEntrateLayout);
		totaliToolStrip.addFill();
		totaliToolStrip.addMember(totaleUsciteLayout);
		
		layout.addMember(totaliToolStrip);
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		grid.addSort(new SortSpecifier("annoEsercizio", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("capitolo", SortDirection.ASCENDING));
		return grid;		
	}
	
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
		aggiornaTotali();
	}
	
	public void aggiornaTotali() {
		if(grid.getRecords().length > 0) {			
			String pattern = "#,##0.00";
			float totaleEntrate = 0;
			float totaleUscite = 0;
			for(int i = 0; i < grid.getRecords().length; i++) {
				Record record = grid.getRecords()[i];
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrate += importo;
				} else if("U".equals(flgEntrataUscita)) {
					totaleUscite += importo;
				}
			}
			if(isDisattivaIntegrazioneSistemaContabile()) {
				totaleEntrateLabel.setContents("");
			}else {
				totaleEntrateLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totaleEntrate) + " euro</b></span>");
			}
			totaleUsciteLabel.setContents("<span style=\"color:#37505f\"><b>Totale uscite " + NumberFormat.getFormat(pattern).format(totaleUscite) + " euro</b></span>");
			totaliToolStrip.show();
		} else {
			totaleEntrateLabel.setContents("");
			totaleUsciteLabel.setContents("");
			totaliToolStrip.hide();
		}
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();		
		ToolStripButton newButton = new ToolStripButton();   
		newButton.setIcon("buttons/new.png");   
		newButton.setIconSize(16);
		newButton.setPrefix("newButton");
		newButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		newButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickNewButton();   	
			}   
		});  
		if (isShowNewButton()) {
			buttons.add(newButton);
		}		
		ToolStripButton refreshListButton = new ToolStripButton();   
		refreshListButton.setIcon("buttons/refreshList.png");   
		refreshListButton.setIconSize(16);
		refreshListButton.setPrefix("refreshListButton");
		refreshListButton.setPrompt("Allineamento col sistema contabile");
		refreshListButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickRefreshListButton();   	
			}   
		});  
		if (isShowRefreshListButton()) {
			buttons.add(refreshListButton);
		}	
		ToolStripButton deleteMovimentiListButton = new ToolStripButton();   
		deleteMovimentiListButton.setIcon("buttons/delete.png");   
		deleteMovimentiListButton.setIconSize(16);
		deleteMovimentiListButton.setPrefix("deleteMovimenti");
		deleteMovimentiListButton.setPrompt("Cancella tutti i movimenti contabili");
		deleteMovimentiListButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickDeleteMovimentiListButton();   	
			}   
		});  
		
		buttons.add(deleteMovimentiListButton);
		
		ToolStripButton downloadTemplateExcelButton = new ToolStripButton();   
		downloadTemplateExcelButton.setIcon("file/download_manager.png");   
		downloadTemplateExcelButton.setIconSize(16);
		downloadTemplateExcelButton.setPrefix("downloadTemplateExcel");
		downloadTemplateExcelButton.setPrompt("Download template excel movimenti contabili");
		downloadTemplateExcelButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickDownloadTemplateExcelButton();   	
			}   
		});  
		
		buttons.add(downloadTemplateExcelButton);
		
		ToolStripButton exportXlsButton = new ToolStripButton();   
		exportXlsButton.setIcon("export/xls.png"); 
		exportXlsButton.setIconSize(16);
		exportXlsButton.setPrefix("exportXlsButton");
		exportXlsButton.setPrompt("Esporta in formato xls");
		exportXlsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickExportDatiContabiliButton();   	
			}   
		});  
		
		buttons.add(exportXlsButton);
		
		ToolStripButton verificaDisponibilitaImportoButton = new ToolStripButton();   
		verificaDisponibilitaImportoButton.setIcon("euro.png");   
		verificaDisponibilitaImportoButton.setIconSize(16);
		verificaDisponibilitaImportoButton.setPrefix("verificaDisponibilitaImportoButton");
		verificaDisponibilitaImportoButton.setPrompt("Verifica disponibilita importo");
		verificaDisponibilitaImportoButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickVerificaDisponibilitaImportoButton();   	
			}   
		}); 
		
		buttons.add(verificaDisponibilitaImportoButton);
		

		return buttons;
	}
	
	@Override
	public List<Canvas> buildCustomEditCanvas() {
		List<Canvas> editCanvas = new ArrayList<Canvas>();
		FileUploadItemWithFirmaAndMimeType uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				grid.deselectAllRecords();
				uriFileImportExcel = uri;
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info == null || info.getMimetype() == null 	|| (!info.getMimetype().equals("application/excel") 
						&& !info.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
					GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
				} else {
					onClickImportXlsButton(uriFileImportExcel, info.getMimetype());
				}				
			}
		});
		
		uploadFileItem.setPickerIconSrc("setPickerIconSrc");
		uploadFileItem.setPrompt("Importa dati da Excel");
		
		if (isShowNewButton()) {

			DynamicForm otherNewButtonsForm = new DynamicForm();
			otherNewButtonsForm.setHeight(1);
			otherNewButtonsForm.setWidth(1);
			otherNewButtonsForm.setOverflow(Overflow.VISIBLE);
			otherNewButtonsForm.setPrefix("otherNewButtons");
			otherNewButtonsForm.setNumCols(20);
			
			List<FormItem> listaOtherNewButtonsFormFields = new ArrayList<FormItem>();
			listaOtherNewButtonsFormFields.add(uploadFileItem);
			
			otherNewButtonsForm.setFields(listaOtherNewButtonsFormFields.toArray(new FormItem[listaOtherNewButtonsFormFields.size()]));
			
			editCanvas.add(otherNewButtonsForm);
		}
		
		return editCanvas;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("otherNewButtons")) {
					if(isEditable() && isShowEditButtons() && isShowNewButton()) {
						member.show();
					} else {
						member.hide();
					}
				}
				if(member instanceof ToolStripButton) {
					if(isEditable() && isShowEditButtons()) {
						member.show();	
					} else {
						member.hide();						
					}
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("refreshListButton")){
						if (isShowRefreshListButton()  && !isDisattivaIntegrazioneSistemaContabile()) {								
								((ToolStripButton) member).show();
						}
						else {
							((ToolStripButton) member).hide();
						}
					}	
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("verificaDisponibilitaImportoButton")){
						if (isDisattivaIntegrazioneSistemaContabile()) {								
							((ToolStripButton) member).show();
						}
						else {
							((ToolStripButton) member).hide();
						}
					}	
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("exportXlsButton")){
						if (isShowExportXlsButton()) {								
								((ToolStripButton) member).show();
						}
						else {
							((ToolStripButton) member).hide();
						}
					}	
				}
			}		
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();
		}
	}	
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		if(isShowEditButtons()) {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
			if(isShowModifyButton()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
				
			if(ripristinoButtonField == null) {
				ripristinoButtonField = buildRipristinaButtonField();					
			}
			buttonsList.add(ripristinoButtonField);
						
		} else {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
		}
		return buttonsList;
	}

	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(!isShowEditButtons() || !isEditable()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable()) {
					event.cancel();
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		return detailButtonField;
	}
	
	public boolean isShowModifyButton(Record record) {
		return isShowDeleteButton(record);
	}	
		
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public boolean isShowDeleteButton(Record record) {
		
		if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE) && 
				(isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_NON_ALLINEATO) || isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE) 
						|| isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING))) {
			return false;
		}
		
		return true;
		
		// se ho stato da_allineare e operazione delete non mostro il bottone
//		if(isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE) && isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
//			return false;
//		}
//		if(isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_NON_ALLINEATO) && isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
//			return false;
//		}
//		return true;
	}
	
	public boolean isShowRipristinaButton(Record record) {		
		return !isShowDeleteButton(record);
	}	
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(event.getRecord())) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});	
		
		return deleteButtonField;
	}
	
	
	public ControlListGridField buildRipristinaButtonField() {
		ControlListGridField ripristinaButtonField = new ControlListGridField("ripristinaButton");  
		ripristinaButtonField.setAttribute("custom", true);	
		ripristinaButtonField.setShowHover(true);		
		ripristinaButtonField.setCanReorder(false);
		ripristinaButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowRipristinaButton(record)) {
					return buildImgButtonHtml("archivio/ripristina.png");
				}
				return null;
			}
		});
		ripristinaButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowRipristinaButton(record)) {
					return "Ripristina";	
				}
				return null;
			}
		});	
		
		ripristinaButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowRipristinaButton(event.getRecord())) {
				event.cancel();
				Record record = event.getRecord();
				
				if (isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE) || isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING)) {
					if (isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
						record.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
						record.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_UPDATE);
						updateGridItemValue();
					} 
				}else if (isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_NON_ALLINEATO)) {
						if (isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
							SC.say("I dati non sono sincronizzati con il sistema contabile, premere il tasto di allineamento prima di effettuare l'operazione");
						} 
					}
				}				
			}
		});			 
		return ripristinaButtonField;
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaDatiContabiliADSP", fields);
	}	
	
	@Override
	public void onClickNewButton() {
		grid.deselectAllRecords();	
		
		manageStatoAtto(new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				onClickNewButtonAfterManageStato();	
			}
		});
		
	}

	/**
	 * 
	 */
	public void onClickNewButtonAfterManageStato() {
		Integer statoAtto = recuperaStatoAtto();
		if(statoAtto!=null && (statoAtto==99 || statoAtto>0)) {
			return;
		}
		
		String cdrUOCompetente = recuperacdR();
		Boolean flgSenzaImpegniCont = recuperaflgSenzaImpegniCont();
		String idUd = recuperaidUd();
		DatiContabiliADSPWindow lDatiContabiliADSPWindow = new DatiContabiliADSPWindow(ListaDatiContabiliADSPItem.this, "datiContabiliADSPWindow", 
				null, true, cdrUOCompetente, flgSenzaImpegniCont, idUd) {
			
			@Override
			public void saveData(Record newRecord) {
				// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB	
				RecordList righeSalvate = getData();
				int ultimoIndice = righeSalvate!=null && righeSalvate.getLength()>0 ? righeSalvate.getLength() : 0;
				newRecord.setAttribute("id", "NEW_" + ultimoIndice);
				newRecord.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
				newRecord.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_INSERT);
				addData(newRecord);				
			}
		};
		lDatiContabiliADSPWindow.show();
	}

	protected abstract String recuperacdR();
	
	protected abstract Integer recuperaStatoAtto();
	
	protected abstract Boolean recuperaflgSenzaImpegniCont();
	
	protected abstract String recuperaidUd();
	
	protected abstract void manageStatoAtto(DSCallback callback);

	public void onClickDetailButton(final ListGridRecord record) {
		String cdrUOCompetente = recuperacdR();
		Boolean flgSenzaImpegniCont = recuperaflgSenzaImpegniCont();
		String idUd = recuperaidUd();
		final DatiContabiliADSPWindow lDatiContabiliADSPWindow = new DatiContabiliADSPWindow(this, "datiContabiliADSPWindow", record,
				false, cdrUOCompetente, flgSenzaImpegniCont, idUd);
		lDatiContabiliADSPWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();	
		
		manageStatoAtto(new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				onClickModifyButtonAfterManageStato(record);
			}
		});
		
	}

	/**
	 * @param record
	 */
	public void onClickModifyButtonAfterManageStato(final ListGridRecord record) {
		Integer statoAtto = recuperaStatoAtto();
		if(statoAtto!=null && (statoAtto==99 || statoAtto>0)) {
			return;
		}
		
		String cdrUOCompetente = recuperacdR();
		Boolean flgSenzaImpegniCont = recuperaflgSenzaImpegniCont();
		String idUd = recuperaidUd();
		final DatiContabiliADSPWindow lDatiContabiliADSPWindow = new DatiContabiliADSPWindow(ListaDatiContabiliADSPItem.this, 
				"datiContabiliADSPWindow", record, true, cdrUOCompetente, flgSenzaImpegniCont, idUd) {
			
			@Override
			public void saveData(final Record updatedRecord) {
				updateData(updatedRecord, record);	
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		    		
					@Override
					public void execute() {
						grid.selectSingleRecord(updatedRecord);
					}
		    	});		
			}		
		};
		lDatiContabiliADSPWindow.show();
	}
	
	public void onClickDeleteButton(final ListGridRecord record) {
		grid.deselectAllRecords();
		
		manageStatoAtto(new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				onClickDeleteButtonAfterManageStato(record);
			}
		});		
	}

	/**
	 * @param record
	 */
	public void onClickDeleteButtonAfterManageStato(ListGridRecord record) {
		Integer statoAtto = recuperaStatoAtto();
		if(statoAtto!=null && (statoAtto==99 || statoAtto>0)) {
			return;
		}
		
		if(isDisattivaIntegrazioneSistemaContabile()) {
			sottraiImportoDaMappaKeyCapitoli(record);
			removeData(record);
			return;
		}
		
		if(isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE) || isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_DA_ALLINEARE_WARNING)) {
			if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_INSERT)) {
				removeData(record);
			}else if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
				record.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
				record.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_DELETE);
				updateGridItemValue();
			}
		}else if (isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_ALLINEATO)) {
			if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_INSERT) || isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
				record.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
				record.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_DELETE);
				updateGridItemValue();
			}
		}else if (isStatoSistemaContabile(record, STATO_SISTEMA_CONTABILE_NON_ALLINEATO)) {
			if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_UPDATE)) {
				record.setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
				record.setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_DELETE);
				updateGridItemValue();
			} else if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_INSERT) || isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE)) {
				SC.say("I dati non sono sincronizzati con il sistema contabile, premere il tasto di allineamento prima di effettuare l'operazione");
				updateGridItemValue();
			}
		}
	}	
	
	@Override
	public void setData(RecordList data) {
		mappaErrori = new HashMap<String, HashSet<String>>();		
		mappaKeyCapitoli = new HashMap<String, String>();
		
		if(data != null) {
			for(int i = 0; i < data.getLength(); i++) {
				if(data.get(i).getAttribute("statoSistemaContabile") == null || "".equals(data.get(i).getAttribute("statoSistemaContabile")) ) {
					data.get(i).setAttribute("statoSistemaContabile", STATO_SISTEMA_CONTABILE_DA_ALLINEARE);
				}
				if(data.get(i).getAttribute("operazioneSistemaContabile") == null || "".equals(data.get(i).getAttribute("operazioneSistemaContabile")) ) {
					data.get(i).setAttribute("operazioneSistemaContabile", OPERAZIONE_SISTEMA_CONTABILE_INSERT);
				}
				
				String keyCapitolo = data.get(i).getAttribute("keyCapitolo");
				String importo = data.get(i).getAttribute("importo").replace(".", "").replace(",", ".");
				
				if(mappaKeyCapitoli.get(keyCapitolo)!=null && !"".equalsIgnoreCase(keyCapitolo)) {
					String importoPresente = mappaKeyCapitoli.get(keyCapitolo);
					
					String importoTotale = String.valueOf(Double.valueOf(importoPresente) + Double.valueOf(importo));
					
					mappaKeyCapitoli.put(keyCapitolo, importoTotale);
					
				}else {
					mappaKeyCapitoli.put(keyCapitolo, importo);
				}
				
			}		
		}
		super.setData(data);
	}
	
	@Override
	public void updateData(Record record, Record oldRecord) {
		String id = record.getAttribute("id");
		mappaErrori.put(id, new HashSet<String>());		
		super.updateData(record, oldRecord);
	}

	public boolean isShowRefreshListButton() {
		return true;
	}
	
	public void onClickRefreshListButton() {
		
	}
	
	public void onClickVerificaDisponibilitaImportoButton() {
		
	}
	
	public void onClickDeleteMovimentiListButton() {
		
	}
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	abstract public void onClickImportXlsButton(String uriFileImportExcel, String mimetype);
	
	abstract public void onClickDownloadTemplateExcelButton();
	
	
	public void onClickExportDatiContabiliButton() {		
		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
//		String[] fields = new String[mappa.keySet().size()];
//		String[] headers = new String[mappa.keySet().size()];
		String[] fields = new String[CelleExcelDatiContabiliADSPEnum.values().length];
		String[] headers = new String[CelleExcelDatiContabiliADSPEnum.values().length];
		
		/**
		 * 
		 * DEVO TENERE ALLINEATI L'ORDINE DELLE COLONNE SIA NELL EXPORT CHE NELL IMPORT, COSI NON HO PROBLEMI NELL IMPORT IN QUANTO 
		 * LA LETTURA DEI CAMPI LI E' STATICA E POSIZIONALE, QUINDI DEVO SAPERE ALLA COLONNA X ESATTAMENTE CHE TIPO DI DATO C'è
		 * 
		 * **/
		
		for (String key : mappa.keySet()) {			
			/*campi da escludere nell export*/
			if(!key.equalsIgnoreCase("mostraErrori") && !key.equalsIgnoreCase("statoSistemaContabile") && !key.equalsIgnoreCase("disponibilitaImporto")) {				
				String header = mappa.get(key);
				if(header.equalsIgnoreCase("Entrata/Uscita")) {
					header = "Movimento U/E";
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.MOVIMENTO_E_U)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.MOVIMENTO_E_U)] = header;
				}else if(header.equalsIgnoreCase("Esercizio")) {
					header = "Anno esercizio";
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.ANNO_ESERCIZIO)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.ANNO_ESERCIZIO)] = header;
				}else if(header.equalsIgnoreCase("CIG")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CIG)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CIG)] = header;
				}else if(header.equalsIgnoreCase("CUP")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CUP)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CUP)] = header;
				}else if(header.equalsIgnoreCase("Capitolo")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CAPITOLO)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CAPITOLO)] = header;
				}else if(header.equalsIgnoreCase("Conto")) {
					header = "Conto+CdR";
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CONTO)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.CONTO)] = header;
				}else if(header.equalsIgnoreCase("Opera")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.OPERA)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.OPERA)] = header;
				}else if(header.equalsIgnoreCase("Importo")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.IMPORTO)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.IMPORTO)] = header;
				}else if(header.equalsIgnoreCase("Note")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.NOTE)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.NOTE)] = header;
				}else if(header.equalsIgnoreCase("Imponibile")) {
					fields[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.IMPONIBILE)] = key;
					headers[CelleExcelDatiContabiliADSPEnum.getIndexFromCell(CelleExcelDatiContabiliADSPEnum.IMPONIBILE)] = header;
				}	
			}
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaDatiContabiliADSP");
		record.setAttribute("formatoExport", "xls");
		record.setAttribute("criteria", (String) null);
		record.setAttribute("fields", fields);
		record.setAttribute("headers", headers);
		record.setAttribute("records", extractRecords(fields));
		record.setAttribute("overflow", false);
		
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.performCustomOperation("export", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					String filename = "Results." + record.getAttribute("formatoExport");
					String url = response.getData()[0].getAttribute("tempFileOut");
					// se l'esportazione ha restituito un uri allora lancio il download del documento generato, altrimenti
					// vuol dire che è abilitato per questo datasource l'esportazione asincrona e quindi la generazione è stata schedulata
					if (url != null) {
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(url));
					}
				}
				/*
				 * else { Layout.addMessage(new MessageBean("Si è verificato un errore durante l'esportazione della lista", "", MessageType.ERROR)); }
				 */
			}
		}, new DSRequest());
	
	};
		
	protected LinkedHashMap<String, String> createFieldsMap(Boolean includeXord) {
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();

		for (int i = 0; i < getGrid().getFields().length; i++) {

			ListGridField field = getGrid().getFields()[i];
			String fieldName = field.getName();

			if (fieldName.endsWith("XOrd") && includeXord) {

				String fieldTitle = field.getTitle() + " (Ordinamento)";

				if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField)  && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
					mappa.put(fieldName, fieldTitle);
				}
			}

			if (fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();

			/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
			if (field.getDisplayField() != null)
				fieldName = field.getDisplayField();

			if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
				mappa.put(fieldName, fieldTitle);
			}
		}
		return mappa;
	}
	
	protected Record[] extractRecords(String[] fields) {
		Record[] records = new Record[getGrid().getRecords().length];
		for (int i = 0; i < getGrid().getRecords().length; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				rec.setAttribute(fieldName, getGrid().getRecords()[i].getAttribute(fieldName));
			}
			// Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
//			String numeroCapitolo = getGrid().getRecords()[i].getAttribute("numeroCapitolo");
//			String descrizioneCapitolo = getGrid().getRecords()[i].getAttribute("descrizioneCapitolo");
//			if(numeroCapitolo != null && !"".equals(numeroCapitolo)) {
//				if(descrizioneCapitolo != null && !"".equals(descrizioneCapitolo)) {
//					rec.setAttribute("descrizioneCapitolo", numeroCapitolo + " - " + descrizioneCapitolo);
//				} else {
//					rec.setAttribute("descrizioneCapitolo", numeroCapitolo);
//				}				
//			}	
			records[i] = rec;
		}
		return records;
	}
	
	@Override
	public void clearErrors() {
		super.clearErrors();
		mappaErrori = new HashMap<String, HashSet<String>>();	
		refreshRows();
	}
	
	public Map getMapErrors() {
		HashMap<String, String> errors = new HashMap<String, String>();
		if(mappaErrori != null) {
			for(String id: mappaErrori.keySet()) {
				if(mappaErrori.get(id) != null && mappaErrori.get(id).size() > 0) {
					errors.put(id, "Questa riga contiene errori");
				}
			}
		}
		return errors;
	}
	
	@Override
	public Boolean validate() {
		
		// le stesse logiche di validazione vanno replicate nel metodo valuesAreValid, ma senza popolare mappaErrori
		
		boolean valid = true;
		
		mappaErrori = new HashMap<String, HashSet<String>>();
		
		for (ListGridRecord record : grid.getRecords()) {
			
			String id = record.getAttribute("id");
			
			mappaErrori.put(id, new HashSet<String>());
			
//			if(!isStatoSistemaContabile(record, "allineato")) {
//				mappaErrori.get(id).add("Dati non allineati con il sistema contabile");
//				valid = false;
//			}
			
			if(record.getAttribute("flgEntrataUscita") == null || "".equals(record.getAttribute("flgEntrataUscita"))) {
				mappaErrori.get(id).add("Campo \"Entrata/Uscita\" obbligatorio");
				valid = false;				
			}
			
			if(isRequiredDecretoCIGDatiContabiliADSP()) {
				if(record.getAttribute("codiceCIG") == null || "".equals(record.getAttribute("codiceCIG"))) {
					mappaErrori.get(id).add("Campo \"CIG\" obbligatorio");
					valid = false;				
				}
			} 
			
			if(isRequiredDecretoCUPDatiContabiliADSP()) {
				if(record.getAttribute("codiceCUP") == null || "".equals(record.getAttribute("codiceCUP"))) {
					mappaErrori.get(id).add("Campo \"CUP\" obbligatorio");
					valid = false;				
				}
			} 
			
			if(isRequiredDecretoOperaDatiContabiliADSP()) {
				if(record.getAttribute("opera") == null || "".equals(record.getAttribute("opera"))) {
					mappaErrori.get(id).add("Campo \"Opera\" obbligatorio");
					valid = false;		
				}
			} 
			
			grid.refreshRow(grid.getRecordIndex(record));
		}
		
		return valid;
	}

	public boolean isEsclusoCIGProposta() {
		return false;
	}
	
	public String getIdUdProposta() {
		return null;
	}	
	
	public String getUoProponenteCorrente() {
		return null;
	}	
		
	public abstract boolean isGrigliaEditabile();
	
	public boolean showEsercizioDatiContabiliADSP() {
		return false;
	}
		
	public String getTitleEsercizioDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredEsercizioDatiContabiliADSP() {
		return false;
	}
	
	public boolean showCapitoloDatiContabiliADSP() {
		return false;
	}
		
	public String getTitleCapitoloDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredCapitoloDatiContabiliADSP() {
		return false;
	}
	
	public boolean showContoDatiContabiliADSP() {
		return false;
	}
		
	public String getTitleContoDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredContoDatiContabiliADSP() {
		return false;
	}
	
	public boolean showDecretoCIGDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoCIGDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoCIGDatiContabiliADSP() {
		return false;
	}
	
	public String[] getCIGValueMap() {
		return null;
	}
	
	public RecordList getCIGCUPRecordList() {
		return null;
	}	
	
	public boolean showDecretoCUPDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoCUPDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoCUPDatiContabiliADSP() {
		return false;
	}				
	
	public boolean showDecretoImportoDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoImportoDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoImportoDatiContabiliADSP() {
		return false;
	}				
	
	public boolean isSkipControlloImportoMaggioreDiZeroValidator() {
		return true;
	}
	
	public boolean showDecretoImponibileDatiContabiliADSP() {		
		return isDisattivaIntegrazioneSistemaContabile();
	}
	
	public String getTitleDecretoImponibileDatiContabiliADSP() {
		return "Imponibile";
	}
	
	public boolean isRequiredDecretoImponibileDatiContabiliADSP() {
		return showDecretoImponibileDatiContabiliADSP();
	}				
	
	public boolean isSkipControlloImponibileMaggioreDiZeroValidator() {
		return isSkipControlloImportoMaggioreDiZeroValidator();
	}
	
	public boolean showDecretoOperaDatiContabiliADSP() {
		return false;
	}
	
	public String getTitleDecretoOperaDatiContabiliADSP() {
		return null;
	}
	
	public boolean isRequiredDecretoOperaDatiContabiliADSP() {
		return false;
	}
	
	public LinkedHashMap<String, String> getOpereADSPValueMap() {
		return null;
	}
	
	/*
	public void reloadCIGCUPValueMap() {		
		String[] lCIGValueMap = getCIGValueMap();
		RecordList lCIGCUPRecordList = getCIGCUPRecordList();
		for (ListGridRecord record : grid.getRecords()) {
			if(lCIGValueMap != null && lCIGValueMap.length > 0) {
				if(record.getAttribute("codiceCIG") != null && !"".equals(record.getAttribute("codiceCIG"))) {
					boolean trovato = false;
					for(int i = 0; i < lCIGValueMap.length; i++) {
						if(lCIGValueMap[i] != null && lCIGValueMap[i].equals(record.getAttribute("codiceCIG"))) {
							trovato = true;
							break;
						}
					}
					if(!trovato) {
						record.setAttribute("codiceCIG", "");
					}
				}
				if(isRequiredDecretoCIGDatiContabiliADSP()) {
					if(lCIGValueMap.length == 1) {
						if(record.getAttribute("codiceCIG") == null || "".equals(record.getAttribute("codiceCIG"))) {
							record.setAttribute("codiceCIG", lCIGValueMap[0]);
						}
					}
				}
			}
			if(lCIGCUPRecordList != null && lCIGCUPRecordList.getLength() > 0) {
				List<String> listaCodCUP = new ArrayList<String>();
				if(record.getAttribute("codiceCIG") != null && !"".equals(record.getAttribute("codiceCIG"))) {
					for(int i=0; i < lCIGCUPRecordList.getLength(); i++) {
						if(lCIGCUPRecordList.get(i).getAttribute("codiceCIG") != null && record.getAttribute("codiceCIG").equals(lCIGCUPRecordList.get(i).getAttribute("codiceCIG"))) {
							listaCodCUP.add(lCIGCUPRecordList.get(i).getAttribute("codiceCUP"));
						}
					}
				}
				if(record.getAttribute("codiceCUP") != null && !"".equals(record.getAttribute("codiceCUP")) && listaCodCUP != null && !listaCodCUP.contains(record.getAttribute("codiceCUP"))) {
					record.setAttribute("codiceCUP", "");
				}
				if(isRequiredDecretoCUPDatiContabiliADSP()) {
					if(listaCodCUP != null && listaCodCUP.size() == 1) {						
						if(record.getAttribute("codiceCUP") == null || "".equals(record.getAttribute("codiceCUP"))) {
							record.setAttribute("codiceCUP", listaCodCUP.get(0));
						}
					}
				}	
			}
			grid.refreshRow(grid.getRecordIndex(record));
		}		
		updateGridItemValue();				
	}
	
	public void reloadOpereADSPValueMap() {
		LinkedHashMap<String, String> lOpereADSPValueMap = getOpereADSPValueMap();
		opera.setValueMap(lOpereADSPValueMap);		
		for (ListGridRecord record : grid.getRecords()) {
			if(record.getAttribute("opera") != null && !"".equals(record.getAttribute("opera")) && lOpereADSPValueMap != null && !lOpereADSPValueMap.containsKey(record.getAttribute("opera"))) {
				record.setAttribute("opera", "");
			}			
			if(isRequiredDecretoOperaDatiContabiliADSP()) {
				if(lOpereADSPValueMap != null && lOpereADSPValueMap.size() == 1) {
					if(record.getAttribute("opera") == null || "".equals(record.getAttribute("opera"))) {
						record.setAttribute("opera", lOpereADSPValueMap.keySet().iterator().next());
					}
				}
			}	
			grid.refreshRow(grid.getRecordIndex(record));
		}		
		updateGridItemValue();		
	}
	*/
	
	boolean isStatoSistemaContabile(Record record, String stato) {
		return stato != null && record.getAttribute("statoSistemaContabile") != null && stato.equals(record.getAttribute("statoSistemaContabile"));
	}
	
	boolean isOperazioneSistemaContabile(Record record, String operazione) {
		return operazione != null && record.getAttribute("operazioneSistemaContabile") != null && operazione.equals(record.getAttribute("operazioneSistemaContabile"));
	}
	
	@Override
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		if(isOperazioneSistemaContabile(record, OPERAZIONE_SISTEMA_CONTABILE_DELETE))  {
			return it.eng.utility.Styles.cellDeletePending;
		}
		return super.getGridBaseStyle(record, rowNum, colNum);
	}
	
	public boolean isDisattivaIntegrazioneSistemaContabile() {
		return false;
	}
	
	public HashMap<String, String> getMappaKeyCapitoli(){
		return mappaKeyCapitoli;
	}
	
	public void sottraiImportoDaMappaKeyCapitoli(Record record) throws NumberFormatException {
		if(mappaKeyCapitoli.get(record.getAttributeAsString("keyCapitolo"))!=null 
					&& !"".equalsIgnoreCase(mappaKeyCapitoli.get(record.getAttributeAsString("keyCapitolo")))) {
				String importoPresente = mappaKeyCapitoli.get(record.getAttributeAsString("keyCapitolo"));
				
				Double importoAggiornato = Double.valueOf(importoPresente) 
						- Double.valueOf(record.getAttributeAsString("importo").replace(".", "").replace(",", "."));
				
				if(importoAggiornato!=null && importoAggiornato>0) {
					mappaKeyCapitoli.put(record.getAttributeAsString("keyCapitolo"), String.valueOf(importoAggiornato));
				}else {
					mappaKeyCapitoli.remove(record.getAttributeAsString("keyCapitolo"));
				}
				
			}
		
	}
	
}
