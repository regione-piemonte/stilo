/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public abstract class ListaInvioMovimentiContabiliSICRAItem extends GridItem {
	
	protected ListGridField id;
	protected ListGridField flgEntrataUscita;
	protected ListGridField flgAutoIncrementante;
	protected ListGridField flgCopertoDaFPV;
	protected ListGridField flgPrenotazione;
	protected ListGridField idImpAcc;
	protected ListGridField numeroImpAcc;
	protected ListGridField annoImpAcc;
	protected ListGridField codiceImpAcc;	
	protected ListGridField tipoDettaglio;
	protected ListGridField numeroDettaglio;
	protected ListGridField annoDettaglio;	
	protected ListGridField codCentroCosto;
	protected ListGridField oggetto;	
	protected ListGridField descrizioneEstesa;	
	protected ListGridField idCapitolo;
	protected ListGridField numeroCapitolo;
	protected ListGridField descrizioneCapitolo;
	protected ListGridField pianoDeiContiFinanz;
	protected ListGridField codiceCapitolo;
	protected ListGridField livelliPdC;
	protected ListGridField descrizionePianoDeiConti;
	protected ListGridField annoCompetenza;
	protected ListGridField importo;
	protected ListGridField codiceCIG;
	protected ListGridField codiceCUP;	
	protected ListGridField dataValuta;
	protected ListGridField codiceSoggetto;
	protected ListGridField tipoSoggetto;
	protected ListGridField flgSoggEstero;
	protected ListGridField denominazioneSogg;
	protected ListGridField cognomeSogg;
	protected ListGridField nomeSogg;
	protected ListGridField codFiscaleSogg;
	protected ListGridField codPIVASogg;
	protected ListGridField indirizzoSogg;
	protected ListGridField cap;
	protected ListGridField localita;
	protected ListGridField provincia;
	protected ListGridField note;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleEntrateLayout;
	protected Label totaleEntrateLabel;
	protected HLayout totaleUsciteLayout;
	protected Label totaleUsciteLabel;

	protected int count = 0;
	protected HashMap<String, Record> mappaMovimentiSicraToDelete = new HashMap<String, Record>();
	protected HashMap<String, Record> mappaMovimentiSicraToInsert = new HashMap<String, Record>();
	
	public ListaInvioMovimentiContabiliSICRAItem(String name) {
		
		super(name, "listaInvioMovimentiContabiliSICRA");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);
		  
		flgEntrataUscita = new ListGridField("flgEntrataUscita", "Entrata/Uscita");
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_E_value());
		flgEntrataUscitaValueMap.put("U", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_U_value());
		flgEntrataUscita.setValueMap(flgEntrataUscitaValueMap);
		
		flgAutoIncrementante = new ListGridField("flgAutoIncrementante", "Auto-incrementante");
		flgAutoIncrementante.setType(ListGridFieldType.ICON);
		flgAutoIncrementante.setWidth(30);
		flgAutoIncrementante.setIconWidth(16);
		flgAutoIncrementante.setIconHeight(16);
		Map<String, String> flgAutoIncrementanteValueIcons = new HashMap<String, String>();		
		flgAutoIncrementanteValueIcons.put("1", "ok.png");
		flgAutoIncrementanteValueIcons.put("0", "blank.png");
		flgAutoIncrementanteValueIcons.put("", "blank.png");
		flgAutoIncrementante.setValueIcons(flgAutoIncrementanteValueIcons);		
		flgAutoIncrementante.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgAutoIncrementante") != null && "1".equals(record.getAttribute("flgAutoIncrementante"))) {
					return "Auto-incrementante";
				}				
				return null;
			}
		});	
		
		flgCopertoDaFPV = new ListGridField("flgCopertoDaFPV", "Coperto da FPV");
		flgCopertoDaFPV.setType(ListGridFieldType.ICON);
		flgCopertoDaFPV.setWidth(30);
		flgCopertoDaFPV.setIconWidth(16);
		flgCopertoDaFPV.setIconHeight(16);
		Map<String, String> flgCopertoDaFPVValueIcons = new HashMap<String, String>();		
		flgCopertoDaFPVValueIcons.put("1", "ok.png");
		flgCopertoDaFPVValueIcons.put("0", "blank.png");
		flgCopertoDaFPVValueIcons.put("", "blank.png");
		flgCopertoDaFPV.setValueIcons(flgCopertoDaFPVValueIcons);		
		flgCopertoDaFPV.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgCopertoDaFPV") != null && "1".equals(record.getAttribute("flgCopertoDaFPV"))) {
					return "Coperto da FPV";
				}				
				return null;
			}
		});	
		
		flgPrenotazione = new ListGridField("flgPrenotazione", "Prenotazione");
		
		idImpAcc = new ListGridField("idImpAcc");
		idImpAcc.setHidden(true);
		idImpAcc.setCanHide(false);
		
		numeroImpAcc = new ListGridField("numeroImpAcc", "Imp./acc. N°");
		numeroImpAcc.setType(ListGridFieldType.INTEGER);
		
		annoImpAcc = new ListGridField("annoImpAcc", "Anno imp./acc.");
		annoImpAcc.setType(ListGridFieldType.INTEGER);
		
		codiceImpAcc = new ListGridField("codiceImpAcc", "Cod. imp./acc.");
		
		codCentroCosto = new ListGridField("codCentroCosto", "CdC");
//		codCentroCosto.setHidden(true);
//		codCentroCosto.setCanHide(false);
		
		oggetto = new ListGridField("oggetto", "Descrizione");
		
		descrizioneEstesa = new ListGridField("descrizioneEstesa", "Descrizione estesa");
		descrizioneEstesa.setHidden(true);
		descrizioneEstesa.setCanHide(false);
		
		idCapitolo = new ListGridField("idCapitolo");
		idCapitolo.setHidden(true);
		idCapitolo.setCanHide(false);
		
		numeroCapitolo = new ListGridField("numeroCapitolo");
		numeroCapitolo.setType(ListGridFieldType.INTEGER);
		numeroCapitolo.setHidden(true);
		numeroCapitolo.setCanHide(false);
				
		descrizioneCapitolo = new ListGridField("descrizioneCapitolo", "Capitolo");
		descrizioneCapitolo.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				String descrizioneCapitoloXOrd = record.getAttributeAsString("numeroCapitolo") + " - " + record.getAttributeAsString("descrizioneCapitolo");
				for(int i = 0; i < 10 - record.getAttributeAsString("numeroCapitolo").length(); i++) {
					descrizioneCapitoloXOrd = "0" + descrizioneCapitoloXOrd;
				}
				return descrizioneCapitoloXOrd;
			}
		});
//		descrizioneCapitolo.setAttribute("custom", true);
//		descrizioneCapitolo.setCellFormatter(new CellFormatter() {
//			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if(record.getAttribute("numeroCapitolo") != null && !"".equals(record.getAttribute("numeroCapitolo"))) {
//					String descrizioneCapitolo = record.getAttribute("numeroCapitolo") + "";
//					if(record.getAttribute("descrizioneCapitolo")!= null && !"".equals(record.getAttribute("descrizioneCapitolo"))) {
//						descrizioneCapitolo += " - " + record.getAttribute("descrizioneCapitolo");													
//					}
//					return descrizioneCapitolo;
//				}	
//				return null;
//			}
//		});
		
		pianoDeiContiFinanz = new ListGridField("pianoDeiContiFinanz", "Piano dei conti finanz.");
		
		codiceCapitolo = new ListGridField("codiceCapitolo", "Cod. capitolo");
		
		livelliPdC = new ListGridField("livelliPdC", "PdC");
		livelliPdC.setType(ListGridFieldType.INTEGER);
		
		descrizionePianoDeiConti = new ListGridField("descrizionePianoDeiConti", "Descrizione piano dei conti");
		
		annoCompetenza = new ListGridField("annoCompetenza", "Anno competenza");
		annoCompetenza.setType(ListGridFieldType.INTEGER);
		
		importo = new ListGridField("importo", "Importo (&euro;)");
		importo.setType(ListGridFieldType.FLOAT);	
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});
		  
		codiceCIG = new ListGridField("codiceCIG", "CIG");
		
		codiceCUP = new ListGridField("codiceCUP", "CUP");
		
		dataValuta = new ListGridField("dataValuta", "Data valuta");
//		dataValuta.setType(ListGridFieldType.DATE);
//		dataValuta.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataValuta.setHidden(true);
		dataValuta.setCanHide(false);
		
		codiceSoggetto = new ListGridField("codiceSoggetto", "Cod. soggetto");
		
		tipoSoggetto = new ListGridField("tipoSoggetto", "Persona fisica/giuridica");
		tipoSoggetto.setHidden(true);
		tipoSoggetto.setCanHide(false);
		
		flgSoggEstero = new ListGridField("flgSoggEstero", "Soggetto estero");
		flgSoggEstero.setHidden(true);
		flgSoggEstero.setCanHide(false);
		
		denominazioneSogg = new ListGridField("denominazioneSogg", "Denominazione sogg.");
		
		cognomeSogg = new ListGridField("cognomeSogg", "Cognome sogg.");
		cognomeSogg.setHidden(true);
		cognomeSogg.setCanHide(false);
		
		nomeSogg = new ListGridField("nomeSogg", "Nome sogg.");
		nomeSogg.setHidden(true);
		nomeSogg.setCanHide(false);
		
		codFiscaleSogg = new ListGridField("codFiscaleSogg", "C.F.");
		codFiscaleSogg.setHidden(true);
		codFiscaleSogg.setCanHide(false);
		
		codPIVASogg = new ListGridField("codPIVASogg", "P.IVA");
		codPIVASogg.setHidden(true);
		codPIVASogg.setCanHide(false);
		
		indirizzoSogg = new ListGridField("indirizzoSogg", "Indirizzo sogg.");
		indirizzoSogg.setHidden(true);
		indirizzoSogg.setCanHide(false);
		
		cap = new ListGridField("cap", "CAP");
		cap.setHidden(true);
		cap.setCanHide(false);
		
		localita = new ListGridField("localita", "Località");
		localita.setHidden(true);
		localita.setCanHide(false);
		
		provincia = new ListGridField("provincia", "Prov.");
		provincia.setHidden(true);
		provincia.setCanHide(false);
				
		note = new ListGridField("note", "Note");
		
		setGridFields(
			id,
			flgEntrataUscita,
			flgAutoIncrementante,
			flgCopertoDaFPV,
			flgPrenotazione,
			idImpAcc,
			numeroImpAcc,
			annoImpAcc,
			codiceImpAcc,
			codCentroCosto,
			oggetto,
			descrizioneEstesa,
			idCapitolo,
			numeroCapitolo,
			descrizioneCapitolo,
			pianoDeiContiFinanz,
			codiceCapitolo,
			livelliPdC,
			descrizionePianoDeiConti,
			annoCompetenza,
			importo,
			codiceCIG,
			codiceCUP,
			dataValuta,
			codiceSoggetto,
			tipoSoggetto,
			flgSoggEstero,
			denominazioneSogg,
			cognomeSogg,
			nomeSogg,
			codFiscaleSogg,
			codPIVASogg,
			indirizzoSogg,
			cap,
			localita,
			provincia,
			note
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
		grid.addSort(new SortSpecifier("descrizioneCapitolo", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("annoCompetenza", SortDirection.ASCENDING));
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
			totaleEntrateLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totaleEntrate) + " euro</b></span>");
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
		setGridFields("listaInvioMovimentiContabiliSICRA", fields);
	}
	
	public boolean isEscludiFiltroCdC() {
		return false;
	}
	
	public void getListaCdC(final ServiceCallback<HashMap<String, String>> callback) {
		if(isEscludiFiltroCdC()) {
			if(callback != null) {
				callback.execute(new LinkedHashMap<String, String>());
			}	
		} else {
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboCdCUoProponenteDataSource");
			lGwtRestDataSource.addParam("idUdProposta", getIdUdProposta());			
			lGwtRestDataSource.addParam("uoProponente", getUoProponenteCorrente());
			Layout.showWaitPopup("Caricamento dati in corso...");
			lGwtRestDataSource.fetchData(null, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {															
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						RecordList listaCdC = response.getDataAsRecordList();
						if(listaCdC.getLength() > 0) {
							HashMap<String, String> valueMap = new LinkedHashMap<String, String>();
							for(int i = 0; i < listaCdC.getLength(); i++) {
								valueMap.put(listaCdC.get(i).getAttribute("key"), listaCdC.get(i).getAttribute("value"));
							}
							if(callback != null) {
								callback.execute(valueMap);
							}
						} else {
							Layout.addMessage(new MessageBean("Nessun CdC associato alla U.O. proponente selezionata", "", MessageType.ERROR));						
						}					
					}
				}
			});
		}
	}

	
	@Override
	public void onClickNewButton() {
		grid.deselectAllRecords();		
		getListaCdC(new ServiceCallback<HashMap<String,String>>() {
			
			@Override
			public void execute(final HashMap<String, String> valueMapCdC) {
				InvioMovimentiContabiliSICRAWindow lInvioMovimentiContabiliSICRAWindow = new InvioMovimentiContabiliSICRAWindow(ListaInvioMovimentiContabiliSICRAItem.this, "invioMovimentiContabiliSICRAWindow", null, true) {
					
					@Override
					public HashMap<String, String> getCdCValueMap() {
						return valueMapCdC;
					}
					
					@Override
					public void saveData(Record newRecord) {
						// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB						
						newRecord.setAttribute("id", "NEW_" + count++);
						addData(newRecord);				
					}
				};
				lInvioMovimentiContabiliSICRAWindow.show();
			}
		});		
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final InvioMovimentiContabiliSICRAWindow lInvioMovimentiContabiliSICRAWindow = new InvioMovimentiContabiliSICRAWindow(this, "invioMovimentiContabiliSICRAWindow", record, false);
		lInvioMovimentiContabiliSICRAWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();		
		getListaCdC(new ServiceCallback<HashMap<String,String>>() {
			
			@Override
			public void execute(final HashMap<String, String> valueMapCdC) {
				final InvioMovimentiContabiliSICRAWindow lInvioMovimentiContabiliSICRAWindow = new InvioMovimentiContabiliSICRAWindow(ListaInvioMovimentiContabiliSICRAItem.this, "invioMovimentiContabiliSICRAWindow", record, true) {
					
					@Override
					public HashMap<String, String> getCdCValueMap() {
						return valueMapCdC;
					}
					
					@Override
					public void saveData(final Record updatedRecord) {
						if(record.getAttribute("idImpAcc") != null && !"".equals(record.getAttribute("idImpAcc"))) {
							mappaMovimentiSicraToDelete.put(record.getAttribute("idImpAcc"), record);
							mappaMovimentiSicraToInsert.put(updatedRecord.getAttribute("idImpAcc"), updatedRecord);							
						}
						updateData(updatedRecord, record);	
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				    		
							@Override
							public void execute() {
								grid.selectSingleRecord(updatedRecord);
							}
				    	});		
					}		
				};
				lInvioMovimentiContabiliSICRAWindow.settaDatiAnagraficiNonModificabili();
				lInvioMovimentiContabiliSICRAWindow.show();
			}
		});			
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		if(record.getAttribute("idImpAcc") != null && !"".equals(record.getAttribute("idImpAcc"))) {
			mappaMovimentiSicraToDelete.put(record.getAttribute("idImpAcc"), record);
			if(mappaMovimentiSicraToInsert.containsKey(record.getAttribute("idImpAcc"))) {
				mappaMovimentiSicraToInsert.remove(record.getAttribute("idImpAcc"));
			}
		}
		grid.deselectAllRecords();
		removeData(record);
	}
	
	@Override
	public void setData(RecordList data) {
		super.setData(data);
	}
	
	public RecordList getListaMovimentiToDelete() {
		RecordList listaMovimentiToDelete = new RecordList();
		// aggiungo i movimenti da cancellare da SICRA (compresi quelli modificati che andranno poi reinseriti)
		if(mappaMovimentiSicraToDelete != null && mappaMovimentiSicraToDelete.size() > 0) {
			Iterator<String> iterator = mappaMovimentiSicraToDelete.keySet().iterator();
			while(iterator.hasNext()) {
				listaMovimentiToDelete.add(mappaMovimentiSicraToDelete.get(iterator.next()));
			}
		}
		return listaMovimentiToDelete;
	}
	
	public RecordList getListaMovimentiToInsert() {
		RecordList listaMovimentiToInsert = new RecordList();
		// aggiungo i movimenti nuovi che andranno inseriti su SICRA
		if(getData() != null) {
			for(int i = 0; i < getData().getLength(); i++) {
				Record record = getData().get(i);
				if(record.getAttribute("idImpAcc") == null || "".equals(record.getAttribute("idImpAcc"))) {
					listaMovimentiToInsert.add(record);
				}
			}		
		}
		// aggiungo anche i movimenti modificati già presenti su Sicra (che dovranno essere reinseriti dopo la cancellazione)
		if(mappaMovimentiSicraToInsert != null) {
			if(mappaMovimentiSicraToInsert.size() > 0) {
				Iterator<String> iterator = mappaMovimentiSicraToInsert.keySet().iterator();
				while(iterator.hasNext()) {
					listaMovimentiToInsert.add(mappaMovimentiSicraToInsert.get(iterator.next()));
				}
			}
		}
		return listaMovimentiToInsert;
	}
	
	public void resetListaMovimentiToDeleteAndInsert() {
		 mappaMovimentiSicraToDelete = new HashMap<String, Record>();
		 mappaMovimentiSicraToInsert = new HashMap<String, Record>();
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
		record.setAttribute("nomeEntita", "listaInvioMovimentiContabiliSICRA");
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
	
	public HashSet<String> getVociPEGNoVerifDisp() {
		return new HashSet<String>();
	}
	
	public abstract boolean isGrigliaEditabile();
		
}
