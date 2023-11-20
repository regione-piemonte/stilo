/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

public abstract class ListaInvioDatiSpesaItem extends GridItem {
	
	protected ListGridField id;
	protected ListGridField flgEntrataUscita;
	protected ListGridField annoEsercizio;
	protected ListGridField flgCorrelata;
	protected ListGridField oggetto;
	protected ListGridField capitolo;
	protected ListGridField articolo;
	protected ListGridField numero;
	protected ListGridField codUnitaOrgCdR;
	protected ListGridField desUnitaOrgCdR;
	protected ListGridField descrizioneCapitolo;
	protected ListGridField numeroCrono;
	protected ListGridField titolo;
	protected ListGridField liv5PdC;
	protected ListGridField annoCompetenza;
	protected ListGridField importo;
	protected ListGridField codiceCIG;
	protected ListGridField codiceCUP;
	protected ListGridField codiceGAMIPBM;
	protected ListGridField annoEsigibilitaDebito;
	protected ListGridField dataEsigibilitaDa;
	protected ListGridField dataEsigibilitaA;
	protected ListGridField dataScadenzaEntrata;
	protected ListGridField dichiarazioneDL78;
	protected ListGridField tipoFinanziamento;
	protected ListGridField denominazioneSogg;
	protected ListGridField codFiscaleSogg;
	protected ListGridField codPIVASogg;
	protected ListGridField indirizzoSogg;
	protected ListGridField cap;
	protected ListGridField localita;
	protected ListGridField provincia;
	protected ListGridField specifiche;
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

	public ListaInvioDatiSpesaItem(String name) {
		
		super(name, "listaInvioDatiSpesa");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);
		  
		flgEntrataUscita = new ListGridField("flgEntrataUscita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_title());
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_E_value());
		flgEntrataUscitaValueMap.put("U", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_U_value());
		flgEntrataUscita.setValueMap(flgEntrataUscitaValueMap);
		
		annoEsercizio = new ListGridField("annoEsercizio", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_annoEsercizio_title());
		annoEsercizio.setType(ListGridFieldType.INTEGER);
		
		flgCorrelata = new ListGridField("flgCorrelata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgCorrelata_title());
		flgCorrelata.setType(ListGridFieldType.ICON);
		flgCorrelata.setWidth(30);
		flgCorrelata.setIconWidth(16);
		flgCorrelata.setIconHeight(16);
		Map<String, String> flgCorrelataValueIcons = new HashMap<String, String>();		
		flgCorrelataValueIcons.put("1", "ok.png");
		flgCorrelataValueIcons.put("0", "blank.png");
		flgCorrelataValueIcons.put("", "blank.png");
		flgCorrelata.setValueIcons(flgCorrelataValueIcons);		
		flgCorrelata.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if(record.getAttribute("flgCorrelata") != null && "1".equals(record.getAttribute("flgCorrelata"))) {
					return I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgCorrelata_title();
				}				
				return null;
			}
		});		
		
		oggetto = new ListGridField("oggetto", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_oggetto_title());
		
		capitolo = new ListGridField("capitolo");
		capitolo.setType(ListGridFieldType.INTEGER);
		capitolo.setHidden(true);
		capitolo.setCanHide(false);
		
		articolo = new ListGridField("articolo");
		articolo.setType(ListGridFieldType.INTEGER);
		articolo.setHidden(true);
		articolo.setCanHide(false);
		
		numero = new ListGridField("numero");
		numero.setType(ListGridFieldType.INTEGER);
		numero.setHidden(true);
		numero.setCanHide(false);
		
		codUnitaOrgCdR = new ListGridField("codUnitaOrgCdR");
		codUnitaOrgCdR.setType(ListGridFieldType.INTEGER);
		codUnitaOrgCdR.setHidden(true);
		codUnitaOrgCdR.setCanHide(false);
		
		desUnitaOrgCdR = new ListGridField("desUnitaOrgCdR", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_unitaOrgCdR_title());
		desUnitaOrgCdR.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return record.getAttributeAsInt("codUnitaOrgCdR");
			}
		});
		desUnitaOrgCdR.setAttribute("custom", true);
		desUnitaOrgCdR.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("codUnitaOrgCdR") != null && !"".equals(record.getAttribute("codUnitaOrgCdR"))) {
					String desUnitaOrgCdR = record.getAttribute("codUnitaOrgCdR") + "";
					if(record.getAttribute("desUnitaOrgCdR")!= null && !"".equals(record.getAttribute("desUnitaOrgCdR"))) {
						desUnitaOrgCdR += " - " + record.getAttribute("desUnitaOrgCdR");													
					}
					return desUnitaOrgCdR;
				}	
				return null;
			}
		});
		
		descrizioneCapitolo = new ListGridField("descrizioneCapitolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_descrizioneCapitolo_title());
		descrizioneCapitolo.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				return (record.getAttributeAsInt("capitolo") * 1000000) +
					   (record.getAttributeAsInt("articolo") * 1000) +
					   (record.getAttributeAsInt("numero"));
			}
		});
		
		numeroCrono = new ListGridField("numeroCrono", "Crono");
		numeroCrono.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showNumeroCrono();
			}
		});	
		
		titolo = new ListGridField("titolo");
		titolo.setType(ListGridFieldType.INTEGER);
		titolo.setHidden(true);
		titolo.setCanHide(false);
		
		liv5PdC = new ListGridField("liv5PdC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_liv5PdC_title());
		liv5PdC.setType(ListGridFieldType.INTEGER);
		
		annoCompetenza = new ListGridField("annoCompetenza", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_annoCompetenza_title());
		annoCompetenza.setType(ListGridFieldType.INTEGER);
		
		importo = new ListGridField("importo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_importo_title());
		importo.setType(ListGridFieldType.FLOAT);	
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});
		  
		codiceCIG = new ListGridField("codiceCIG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codiceCIG_title());
		
		codiceCUP = new ListGridField("codiceCUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codiceCUP_title());
		
		codiceGAMIPBM = new ListGridField("codiceGAMIPBM", getTitleCodiceGAMIPBM());
		
		annoEsigibilitaDebito = new ListGridField("annoEsigibilitaDebito", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_annoEsigibilitaDebito_title());
		
		dataEsigibilitaDa = new ListGridField("dataEsigibilitaDa", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaDa_title());
		dataEsigibilitaDa.setType(ListGridFieldType.DATE);
		dataEsigibilitaDa.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		if(isListaInvioDatiSpesaContoCapitale()) {
			dataEsigibilitaDa.setHidden(true);
			dataEsigibilitaDa.setCanHide(false);
		}
		
		dataEsigibilitaA = new ListGridField("dataEsigibilitaA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dataEsigibilitaA_title());
		dataEsigibilitaA.setType(ListGridFieldType.DATE);
		dataEsigibilitaA.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		if(isListaInvioDatiSpesaContoCapitale()) {
			dataEsigibilitaA.setHidden(true);
			dataEsigibilitaA.setCanHide(false);
		}
		
		dataScadenzaEntrata = new ListGridField("dataScadenzaEntrata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dataScadenzaEntrata_title());
		dataScadenzaEntrata.setType(ListGridFieldType.DATE);
		dataScadenzaEntrata.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		dichiarazioneDL78 = new ListGridField("dichiarazioneDL78", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_dichiarazioneDL78_title());
		dichiarazioneDL78.setValueMap("SI", "NO");
		
		tipoFinanziamento = new ListGridField("tipoFinanziamento", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_tipoFinanziamento_title());
		
		denominazioneSogg = new ListGridField("denominazioneSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_denominazioneSogg_title());
		
		codFiscaleSogg = new ListGridField("codFiscaleSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codFiscaleSogg_title());
		
		codPIVASogg = new ListGridField("codPIVASogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codPIVASogg_title());
		
		indirizzoSogg = new ListGridField("indirizzoSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_indirizzoSogg_title());
		
		cap = new ListGridField("cap", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_cap_title());
		
		localita = new ListGridField("localita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_localita_title());
		
		provincia = new ListGridField("provincia", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_provincia_title());
		
		specifiche = new ListGridField("specifiche", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_specifiche_title());
//		specifiche.setCellFormatter(new CellFormatter() {
//			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				String specifiche = null;
//				if(record.getAttribute("specifiche") != null && !"".equals(record.getAttribute("specifiche"))) {
//					StringSplitterClient lStringSplitterClient = new StringSplitterClient(record.getAttribute("specifiche"), "|*|");
//					String[] listaSpecifiche = lStringSplitterClient.getTokens();
//					for(int i = 0; i < listaSpecifiche.length; i++) {
//						if(specifiche == null) {
//							specifiche = listaSpecifiche[i];
//						} else {
//							specifiche += "\n" + listaSpecifiche[i];
//						}
//					}
//				}	
//				return specifiche;
//			}
//		});
		
		note = new ListGridField("note", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_note_title());
		
		setGridFields(
			id,
			flgEntrataUscita,
			annoEsercizio,
			flgCorrelata,
			oggetto,
			capitolo,
			articolo,
			numero,
			codUnitaOrgCdR,
			desUnitaOrgCdR,
			descrizioneCapitolo,
			numeroCrono,
			titolo,
			liv5PdC,
			annoCompetenza,
			importo,
			codiceCIG,
			codiceCUP,
			codiceGAMIPBM,
			annoEsigibilitaDebito,
			dataEsigibilitaDa,
			dataEsigibilitaA,
			dataScadenzaEntrata,
			dichiarazioneDL78,
			tipoFinanziamento,
			denominazioneSogg,
			codFiscaleSogg,
			codPIVASogg,
			indirizzoSogg,
			cap,
			localita,
			provincia,
			specifiche,
			note
		);		
	}
	
	public boolean showNumeroCrono() {
		return false;
	}
	
	public String getTitleCodiceGAMIPBM() {
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_codiceGAMIPBM_title();
	}
	
//	public RecordList buildSpecificheRecordListFromString(String specifiche) {
//		RecordList listaSpecifiche = new RecordList();
//		if(specifiche != null && !"".equals(specifiche)) {
//			StringSplitterClient lStringSplitterClient = new StringSplitterClient(specifiche, "|*|");
//			for(int i = 0; i < lStringSplitterClient.getTokens().length; i++) {
//				Record record = new Record();
//				record.setAttribute("value", lStringSplitterClient.getTokens()[i]);
//				listaSpecifiche.add(record);
//			}
//		}	
//		return listaSpecifiche;
//	}
	
//	public String buildSpecificheStringFromRecordList(RecordList listaSpecifiche) {
//		String specifiche = null;
//		if(listaSpecifiche != null) {
//			for(int i = 0; i < listaSpecifiche.getLength(); i++) {
//				if(specifiche == null) {
//					specifiche = listaSpecifiche.get(i).getAttribute("value");
//				} else {
//					specifiche += "|*|" + listaSpecifiche.get(i).getAttribute("value");
//				}
//			}
//		}	
//		return specifiche;
//	}
	
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
			float totaleEntrateCorrelate = 0;
			float totaleUscite = 0;
			float totaleUsciteCorrelate = 0;
			for(int i = 0; i < grid.getRecords().length; i++) {
				Record record = grid.getRecords()[i];
				String flgEntrataUscita = record.getAttribute("flgEntrataUscita") != null ? record.getAttribute("flgEntrataUscita") : "";
				boolean flgCorrelata = record.getAttribute("flgCorrelata") != null && "1".equals(record.getAttribute("flgCorrelata"));
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				if("E".equals(flgEntrataUscita)) {
					totaleEntrate += importo;
					if(flgCorrelata) {
						totaleEntrateCorrelate += importo;
					}
				} else if("U".equals(flgEntrataUscita)) {
					totaleUscite += importo;
					if(flgCorrelata) {
						totaleUsciteCorrelate += importo;
					}
				}
			}
			if(totaleEntrateCorrelate > 0) {
				totaleEntrateLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totaleEntrate) + " euro di cui correlate " + NumberFormat.getFormat(pattern).format(totaleEntrateCorrelate) + " euro</b></span>");
			} else {
				totaleEntrateLabel.setContents("<span style=\"color:green\"><b>Totale entrate " + NumberFormat.getFormat(pattern).format(totaleEntrate) + " euro</b></span>");
			}
			if(totaleUsciteCorrelate > 0) {
				totaleUsciteLabel.setContents("<span style=\"color:#37505f\"><b>Totale uscite " + NumberFormat.getFormat(pattern).format(totaleUscite) + " euro di cui correlate " + NumberFormat.getFormat(pattern).format(totaleUsciteCorrelate) + " euro</b></span>");
			} else {
				totaleUsciteLabel.setContents("<span style=\"color:#37505f\"><b>Totale uscite " + NumberFormat.getFormat(pattern).format(totaleUscite) + " euro</b></span>");
			}
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
		setGridFields("listaInvioDatiSpesa", fields);
	}
	
	@Override
	public void onClickNewButton() {
		grid.deselectAllRecords();
		InvioDatiSpesaWindow lInvioDatiSpesaWindow = new InvioDatiSpesaWindow(this, "invioDatiSpesaWindow", null, true) {
			
			@Override
			public void saveData(Record newRecord) {
				// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB
				newRecord.setAttribute("id", "NEW_" + count++);
//				newRecord.setAttribute("specifiche", buildSpecificheStringFromRecordList(newRecord.getAttributeAsRecordList("listaSpecifiche")));
				addData(newRecord);				
			}
		};
		lInvioDatiSpesaWindow.show();
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
//		record.setAttribute("listaSpecifiche", buildSpecificheRecordListFromString(record.getAttribute("specifiche")));
		final InvioDatiSpesaWindow lInvioDatiSpesaWindow = new InvioDatiSpesaWindow(this, "invioDatiSpesaWindow", record, false);
		lInvioDatiSpesaWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();		
//		record.setAttribute("listaSpecifiche", buildSpecificheRecordListFromString(record.getAttribute("specifiche")));
		final InvioDatiSpesaWindow lInvioDatiSpesaWindow = new InvioDatiSpesaWindow(this, "invioDatiSpesaWindow", record, true) {
			
			@Override
			public void saveData(final Record updatedRecord) {
//				newRecord.setAttribute("specifiche", buildSpecificheStringFromRecordList(updatedRecord.getAttributeAsRecordList("listaSpecifiche")));
				updateData(updatedRecord, record);	
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		    		
					@Override
					public void execute() {
						grid.selectSingleRecord(updatedRecord);
					}
		    	});		
			}		
		};
		lInvioDatiSpesaWindow.show();
	}

	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
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
		record.setAttribute("nomeEntita", "listaInvioDatiSpesa");
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
			//TODO Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
			String codUnitaOrgCdR = getGrid().getRecords()[i].getAttribute("codUnitaOrgCdR");
			String desUnitaOrgCdR = getGrid().getRecords()[i].getAttribute("desUnitaOrgCdR");
			if(codUnitaOrgCdR != null && !"".equals(codUnitaOrgCdR)) {
				if(desUnitaOrgCdR != null && !"".equals(desUnitaOrgCdR)) {
					rec.setAttribute("desUnitaOrgCdR", codUnitaOrgCdR + " - " + desUnitaOrgCdR);
				} else {
					rec.setAttribute("desUnitaOrgCdR", codUnitaOrgCdR);
				}				
			}	
			records[i] = rec;
		}
		return records;
	}
	
	public HashSet<String> getVociPEGNoVerifDisp() {
		return new HashSet<String>();
	}
	
	public abstract String getSIBDataSourceName();
	
	public abstract boolean isGrigliaEditabile();
	
	public boolean isListaInvioDatiSpesaCorrente() {
		return getName() != null && "listaInvioDatiSpesaCorrente".equals(getName());
	}
	
	public boolean isListaInvioDatiSpesaContoCapitale() {
		return getName() != null && "listaInvioDatiSpesaContoCapitale".equals(getName());
	}
	
	public boolean isEsclusoCIGProposta() {
		return false;
	}
	
	public String[] getCIGValueMap() {
		return null;
	}
	
}
