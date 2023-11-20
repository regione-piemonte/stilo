/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public abstract class ListaDatiContabiliAVBItem extends GridItem {
	
	protected ListGridField id;	
	protected ListGridField missione;
	protected ListGridField desMissione;
	protected ListGridField programma;		
	protected ListGridField desProgramma;
	protected ListGridField livelliPdC;
	protected ListGridField desLivelliPdC;
	protected ListGridField annoBilancio;
	protected ListGridField capitolo;
	protected ListGridField centroDiCosto;	
	protected ListGridField nroImpAcc;
	protected ListGridField subImpAcc;			
	protected ListGridField annoGestResidui;
	protected ListGridField nroLiquidazione;
	protected ListGridField annoLiquidazione;		
	protected ListGridField importo;			
	protected ListGridField flgPrenotazione;
	protected ListGridField flgSoggettiVari;
	protected ListGridField nominativoSogg;
	protected ListGridField codFisPIVASogg; 
	protected ListGridField sedeSogg;			
	protected ListGridField codiceCIG;
	protected ListGridField codiceCUP;
	protected ListGridField iban;
	protected ListGridField nroMandato;
	protected ListGridField dataMandato;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleLayout;
	protected Label totaleLabel;
	
	protected int count = 0;
	
	public ListaDatiContabiliAVBItem(String name) {
		
		super(name, name); // passo come nomeLista il parametro name in input perchè voglio differenziare le preference del layout lista per impegni, accertamenti e liquidazioni
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);
		
		missione = new ListGridField("missione", "Missione");
		missione.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB();
			}
		});		
		
		desMissione = new ListGridField("desMissione");
		desMissione.setHidden(true);
		desMissione.setCanHide(false);								

		programma = new ListGridField("programma", "Programma");	
		programma.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB();
			}
		});
				
		desProgramma = new ListGridField("desProgramma");
		desProgramma.setHidden(true);
		desProgramma.setCanHide(false);								

		livelliPdC = new ListGridField("livelliPdC", "Piano dei Conti");	
		livelliPdC.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});

		desLivelliPdC = new ListGridField("desLivelliPdC");
		desLivelliPdC.setHidden(true);
		desLivelliPdC.setCanHide(false);						
		
		annoBilancio = new ListGridField("annoBilancio", "Anno bilancio");
		annoBilancio.setType(ListGridFieldType.INTEGER);
		annoBilancio.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});
		
		capitolo = new ListGridField("capitolo", "Capitolo");	
		capitolo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});

		centroDiCosto = new ListGridField("centroDiCosto", "CdC");		
		centroDiCosto.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});

		nroImpAcc = new ListGridField("nroImpAcc", "N°"); 
		nroImpAcc.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				if(isListaImpegniAVB()) {
					nroImpAcc.setTitle("N° impegno");
				} else if(isListaAccertamentiAVB()) {		
					nroImpAcc.setTitle("N° accertamento");
				}
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});
		
		subImpAcc = new ListGridField("subImpAcc", "Sub");
		subImpAcc.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});
		
		annoGestResidui = new ListGridField("annoGestResidui", "Anno gestione residui");
		annoGestResidui.setType(ListGridFieldType.INTEGER);
		annoGestResidui.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});

		nroLiquidazione = new ListGridField("nroLiquidazione", "N° liquidazione");
		nroLiquidazione.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaLiquidazioniAVB();
			}
		});

		annoLiquidazione = new ListGridField("annoLiquidazione", "Anno liquidazione");
		annoLiquidazione.setType(ListGridFieldType.INTEGER);
		annoLiquidazione.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaLiquidazioniAVB();
			}
		});
					
		importo = new ListGridField("importo", "Importo (&euro;)");
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
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});
		
		flgPrenotazione = new ListGridField("flgPrenotazione", "Prenotazione");
		flgPrenotazione.setType(ListGridFieldType.ICON);
		flgPrenotazione.setWidth(30);
		flgPrenotazione.setIconWidth(16);
		flgPrenotazione.setIconHeight(16);
		Map<String, String> flgPrenotazioneValueIcons = new HashMap<String, String>();		
		flgPrenotazioneValueIcons.put("1", "ok.png");
		flgPrenotazioneValueIcons.put("0", "blank.png");
		flgPrenotazioneValueIcons.put("", "blank.png");
		flgPrenotazione.setValueIcons(flgPrenotazioneValueIcons);		
		flgPrenotazione.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgPrenotazione") != null && "1".equals(record.getAttribute("flgPrenotazione"))) {
					return "Prenotazione";
				}				
				return null;
			}
		});	
		flgPrenotazione.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB();
			}
		});
		
		flgSoggettiVari = new ListGridField("flgSoggettiVari", "Soggetti vari");
		flgSoggettiVari.setType(ListGridFieldType.ICON);
		flgSoggettiVari.setWidth(30);
		flgSoggettiVari.setIconWidth(16);
		flgSoggettiVari.setIconHeight(16);
		Map<String, String> flgSoggettiVariValueIcons = new HashMap<String, String>();		
		flgSoggettiVariValueIcons.put("1", "ok.png");
		flgSoggettiVariValueIcons.put("0", "blank.png");
		flgSoggettiVariValueIcons.put("", "blank.png");
		flgSoggettiVari.setValueIcons(flgSoggettiVariValueIcons);		
		flgSoggettiVari.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgSoggettiVari") != null && "1".equals(record.getAttribute("flgSoggettiVari"))) {
					return "Soggetti vari";
				}				
				return null;
			}
		});	
		flgSoggettiVari.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB();
			}
		});
		
		nominativoSogg = new ListGridField("nominativoSogg", "Nominativo soggetto");	
		nominativoSogg.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB() || isListaLiquidazioniAVB();
			}
		});

		codFisPIVASogg = new ListGridField("codFisPIVASogg", "CF/P.IVA soggetto");	
		codFisPIVASogg.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB() || isListaLiquidazioniAVB();
			}
		});
		
		sedeSogg = new ListGridField("sedeSogg", "Sede soggetto");			
		sedeSogg.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB() || isListaLiquidazioniAVB(); 
			}
		});

		codiceCIG = new ListGridField("codiceCIG", "CIG");
		codiceCIG.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});

		codiceCUP = new ListGridField("codiceCUP", "CUP");
		codiceCUP.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaImpegniAVB() || isListaAccertamentiAVB();
			}
		});

		iban = new ListGridField("iban", "IBAN");
		iban.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaLiquidazioniAVB();
			}
		});

		nroMandato = new ListGridField("nroMandato", "N° mandato");	
		nroMandato.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaLiquidazioniAVB();
			}
		});

		dataMandato = new ListGridField("dataMandato", "Data mandato");
		dataMandato.setType(ListGridFieldType.DATE);
		dataMandato.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataMandato.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return isListaLiquidazioniAVB();
			}
		});

		setGridFields(
			id,
			missione,
			desMissione,
			programma,	
			desProgramma,
			livelliPdC,
			desLivelliPdC,
			annoBilancio,
			capitolo,
			centroDiCosto,	
			nroImpAcc,
			subImpAcc,			
			annoGestResidui,
			nroLiquidazione,
			annoLiquidazione,		
			importo,			
			flgPrenotazione,
			flgSoggettiVari,
			nominativoSogg,
			codFisPIVASogg, 
			sedeSogg,			
			codiceCIG,
			codiceCUP,
			iban,
			nroMandato,
			dataMandato
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
		
		totaleLayout = new HLayout();
		totaleLayout.setOverflow(Overflow.VISIBLE);
		totaleLayout.setWidth(5);
	
		totaleLabel = new Label();
		totaleLabel.setAlign(Alignment.CENTER);
		totaleLabel.setValign(VerticalAlignment.CENTER);
		totaleLabel.setWrap(false);
		
		totaleLayout.setMembers(totaleLabel);
		
		totaliToolStrip.addFill();
		totaliToolStrip.addMember(totaleLayout);
		
		layout.addMember(totaliToolStrip);	
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		grid.addSort(new SortSpecifier("descrizioneCapitolo", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("annoCompetenza", SortDirection.ASCENDING));
		return grid;		
	}
	
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
		aggiornaTotale();
	}
	
	public void aggiornaTotale() {
		if(grid.getRecords().length > 0 && (isListaImpegniAVB() || isListaAccertamentiAVB())) {			
			String pattern = "#,##0.00";
			float totale = 0;
			for(int i = 0; i < grid.getRecords().length; i++) {
				Record record = grid.getRecords()[i];
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				totale += importo;									
			}
			if(isListaImpegniAVB()) {
				totaleLabel.setContents("<span style=\"color:#37505f\"><b>Totale uscite " + NumberFormat.getFormat(pattern).format(totale) + " euro</b></span>");			
			} else if(isListaAccertamentiAVB()) {
				totaleLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totale) + " euro</b></span>");
			}
			totaliToolStrip.show();
		} else {
			totaleLabel.setContents("");
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
		refreshListButton.setPrompt(I18NUtil.getMessages().refreshListButton_prompt());
		refreshListButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickRefreshListButton();   	
			}   
		});  
		if (isShowRefreshListButton()) {
			buttons.add(refreshListButton);
		}	
		ToolStripButton exportXlsButton = new ToolStripButton();   
		exportXlsButton.setIcon("export/xls.png"); 
		exportXlsButton.setIconSize(16);
		exportXlsButton.setPrefix("exportXlsButton");
		exportXlsButton.setPrompt("Esporta in formato xls");
		exportXlsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickExportXlsButton();   	
			}   
		});  
		if (isShowExportXlsButton()) {
			buttons.add(exportXlsButton);
		}
		return buttons;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if(isEditable() && isShowEditButtons()) {
						member.show();	
					} else {
						member.hide();						
					}
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("refreshListButton"))
					{
						if (isShowRefreshListButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}	
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("exportXlsButton"))
					{
						if (isShowExportXlsButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
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
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});			 
		return deleteButtonField;
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaDatiContabiliAVB", fields);
	}
	
//	public boolean isEscludiFiltroCdC() {
//		return false;
//	}
//	
//	public void getListaCdC(final ServiceCallback<HashMap<String, String>> callback) {
//		if(isEscludiFiltroCdC()) {
//			if(callback != null) {
//				callback.execute(new LinkedHashMap<String, String>());
//			}	
//		} else {
//			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboCdCUoProponenteDataSource");
//			lGwtRestDataSource.addParam("idUdProposta", getIdUdProposta());			
//			lGwtRestDataSource.addParam("uoProponente", getUoProponenteCorrente());
//			Layout.showWaitPopup("Caricamento dati in corso...");
//			lGwtRestDataSource.fetchData(null, new DSCallback() {
//				@Override
//				public void execute(DSResponse response, Object rawData, DSRequest request) {															
//					Layout.hideWaitPopup();
//					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
//						RecordList listaCdC = response.getDataAsRecordList();
//						if(listaCdC.getLength() > 0) {
//							HashMap<String, String> valueMap = new LinkedHashMap<String, String>();
//							for(int i = 0; i < listaCdC.getLength(); i++) {
//								valueMap.put(listaCdC.get(i).getAttribute("key"), listaCdC.get(i).getAttribute("value"));
//							}
//							if(callback != null) {
//								callback.execute(valueMap);
//							}
//						} else {
//							Layout.addMessage(new MessageBean("Nessun CdC associato alla U.O. proponente selezionata", "", MessageType.ERROR));						
//						}					
//					}
//				}
//			});
//		}
//	}

	
	@Override
	public void onClickNewButton() {
		grid.deselectAllRecords();		
//		getListaCdC(new ServiceCallback<HashMap<String,String>>() {
//			
//			@Override
//			public void execute(final HashMap<String, String> valueMapCdC) {
				DatiContabiliAVBWindow lDatiContabiliAVBWindow = new DatiContabiliAVBWindow(ListaDatiContabiliAVBItem.this, "datiContabiliAVBWindow", null, true) {
					
//					@Override
//					public HashMap<String, String> getCdCValueMap() {
//						return valueMapCdC;
//					}
					
					@Override
					public void saveData(Record newRecord) {
						// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB						
						newRecord.setAttribute("id", "NEW_" + count++);
						addData(newRecord);				
					}
				};
				lDatiContabiliAVBWindow.show();
//			}
//		});		
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final DatiContabiliAVBWindow lDatiContabiliAVBWindow = new DatiContabiliAVBWindow(this, "datiContabiliAVBWindow", record, false);
		lDatiContabiliAVBWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();		
//		getListaCdC(new ServiceCallback<HashMap<String,String>>() {
//			
//			@Override
//			public void execute(final HashMap<String, String> valueMapCdC) {
				final DatiContabiliAVBWindow lDatiContabiliAVBWindow = new DatiContabiliAVBWindow(ListaDatiContabiliAVBItem.this, "datiContabiliAVBWindow", record, true) {
					
//					@Override
//					public HashMap<String, String> getCdCValueMap() {
//						return valueMapCdC;
//					}
					
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
				lDatiContabiliAVBWindow.show();
//			}
//		});			
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	@Override
	public void setData(RecordList data) {
		super.setData(data);
	}

	public boolean isShowRefreshListButton() {
		return true;
	}
	
	public void onClickRefreshListButton() {
		
	}
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	public void onClickExportXlsButton() {		

		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for (String key : mappa.keySet()) {
			fields[n] = key;
			headers[n] = mappa.get(key);
			n++;
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaDatiContabiliAVB");
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
	}	
	
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
	
	public boolean isEsclusoCIGProposta() {
		return false;
	}
	
	public String[] getCIGValueMap() {
		return null;
	}
	
	public String getIdUdProposta() {
		return null;
	}	
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
//	public HashSet<String> getVociPEGNoVerifDisp() {
//		return new HashSet<String>();
//	}
	
	public abstract boolean isGrigliaEditabile();

	public abstract TipoDatiContabiliAVBEnum getTipoDatiContabiliAVB();

	public boolean isListaImpegniAVB() {
		return getTipoDatiContabiliAVB() != null && getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.IMPEGNI);			
	}

	public boolean isListaAccertamentiAVB() {
		return getTipoDatiContabiliAVB() != null && getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.ACCERTAMENTI);
	}
	
	public boolean isListaLiquidazioniAVB() {
		return getTipoDatiContabiliAVB() != null && getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.LIQUIDAZIONI);
	}
	
	public enum TipoDatiContabiliAVBEnum {
		
		IMPEGNI("I"),
		ACCERTAMENTI("A"),
		LIQUIDAZIONI("L");
		
	    private String tipoDatiContabiliAVB;

	    TipoDatiContabiliAVBEnum(String tipoDatiContabiliAVB) {
	        this.tipoDatiContabiliAVB = tipoDatiContabiliAVB;
	    }

	    public String getValue() {
	        return tipoDatiContabiliAVB;
	    }
	    
	}
		
}
