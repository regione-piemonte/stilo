/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
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

public abstract class ListaMovimentiGSAItem extends GridItem {
	
	protected ListGridField id;
	protected ListGridField codTipoMovimento;
	protected ListGridField desTipoMovimento;
	protected ListGridField flgEntrataUscita;
	protected ListGridField numeroCapitolo;
	protected ListGridField numeroArticolo;
	protected ListGridField idUoStrutturaCompetente;
	protected ListGridField codRapidoStrutturaCompetente;
	protected ListGridField desStrutturaCompetente;
	protected ListGridField numeroMovimento;
	protected ListGridField annoMovimento;
	protected ListGridField descrizioneMovimento;
	protected ListGridField numeroSub;
	protected ListGridField annoSub;
	protected ListGridField numeroModifica;	
	protected ListGridField annoModifica;	
	protected ListGridField dataInserimento;
	protected ListGridField dataRegistrazione;
	protected ListGridField importo;		
	protected ListGridField codiceCIG;
	protected ListGridField codiceCUP;
	protected ListGridField codiceSoggetto;	
	protected ListGridField datiGsa;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected ToolStrip totaliToolStrip;
	protected HLayout totaleEntrateLayout;
	protected Label totaleEntrateLabel;
	protected HLayout totaleUsciteLayout;
	protected Label totaleUsciteLabel;

	protected int count = 0;
	
	public ListaMovimentiGSAItem(String name) {
		
		super(name, "listaMovimentiGSA");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);
		
		codTipoMovimento = new ListGridField("codTipoMovimento");
		codTipoMovimento.setHidden(true);
		codTipoMovimento.setCanHide(false);
		
		desTipoMovimento = new ListGridField("desTipoMovimento", "Tipo movimento");
		
		flgEntrataUscita = new ListGridField("flgEntrataUscita", "Entrata/Uscita");
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_E_value());
		flgEntrataUscitaValueMap.put("U", I18NUtil.getMessages().nuovaPropostaAtto2_detail_listaDatiContabili_flgEntrataUscita_U_value());
		flgEntrataUscita.setValueMap(flgEntrataUscitaValueMap);
		
		numeroCapitolo = new ListGridField("numeroCapitolo", "Cap.");
		numeroCapitolo.setType(ListGridFieldType.INTEGER);
		
		numeroArticolo = new ListGridField("numeroArticolo", "Art.");
		numeroArticolo.setType(ListGridFieldType.INTEGER);
		
		idUoStrutturaCompetente = new ListGridField("idUoStrutturaCompetente");
		idUoStrutturaCompetente.setHidden(true);
		idUoStrutturaCompetente.setCanHide(false);
				
		codRapidoStrutturaCompetente = new ListGridField("codRapidoStrutturaCompetente");
		codRapidoStrutturaCompetente.setHidden(true);
		codRapidoStrutturaCompetente.setCanHide(false);
		
		desStrutturaCompetente = new ListGridField("desStrutturaCompetente", "Struttura competente");
		desStrutturaCompetente.setAttribute("custom", true);
		desStrutturaCompetente.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codRapidoStrutturaCompetente", "desStrutturaCompetente");
			}
		});
		if(!showStrutturaCompetente()) {
			desStrutturaCompetente.setHidden(true);
			desStrutturaCompetente.setCanHide(false);
		}
		
		numeroMovimento = new ListGridField("numeroImpAcc", "Imp./acc. N°");
		numeroMovimento.setType(ListGridFieldType.INTEGER);
		
		annoMovimento = new ListGridField("annoImpAcc", "Anno imp./acc.");
		annoMovimento.setType(ListGridFieldType.INTEGER);
	
		descrizioneMovimento = new ListGridField("descrizioneMovimento", "Descrizione imp./acc.");
		
		numeroSub = new ListGridField("numeroSub", "N° sub");
		numeroSub.setType(ListGridFieldType.INTEGER);
		
		annoSub = new ListGridField("annoSub", "Anno sub");
		annoSub.setType(ListGridFieldType.INTEGER);
		
		numeroModifica = new ListGridField("numeroModifica", "N° modifica");
		numeroModifica.setType(ListGridFieldType.INTEGER);
		
		annoModifica = new ListGridField("annoModifica", "Anno modifica");
		annoModifica.setType(ListGridFieldType.INTEGER);
		
		dataInserimento = new ListGridField("dataInserimento", "Data inserimento");
		dataInserimento.setType(ListGridFieldType.DATE);
		dataInserimento.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		dataRegistrazione = new ListGridField("dataRegistrazione", "Data registrazione");
		dataRegistrazione.setType(ListGridFieldType.DATE);
		dataRegistrazione.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
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
		
		codiceSoggetto = new ListGridField("codiceSoggetto", "Cod. soggetto");
		
		datiGsa = new ListGridField("datiGsa", "Presenti dati GSA");
		datiGsa.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		datiGsa.setType(ListGridFieldType.ICON);
		datiGsa.setWidth(30);
		datiGsa.setIconWidth(16);
		datiGsa.setIconHeight(16);
		datiGsa.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String datiGsa = record.getAttribute("datiGsa") != null ? record.getAttribute("datiGsa") : "";
				if (isDatiRilevantiGsa() && datiGsa != null && !"".equals(datiGsa)) {
					return buildIconHtml("attiInLavorazione/GSA.png");
				}
				return null;
			}
		});
		datiGsa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String datiGsa = record.getAttribute("datiGsa") != null ? record.getAttribute("datiGsa") : "";
				if (isDatiRilevantiGsa() && datiGsa != null && !"".equals(datiGsa)) {
					return "Presenti dati GSA";
				}
				return null;
			}
		});
		
		setGridFields(
			id,
			codTipoMovimento,
			desTipoMovimento,
			flgEntrataUscita,
			numeroCapitolo,
			numeroArticolo,
			idUoStrutturaCompetente,
			codRapidoStrutturaCompetente,
			desStrutturaCompetente,
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
			codiceSoggetto,	
			datiGsa
		);				
	}
	
	public String getDescrizioneWithCodice(Record record, String codiceFieldName, String descrizioneFieldName) {
		String codice = record.getAttribute(codiceFieldName);
		String descrizione = record.getAttribute(descrizioneFieldName);
		if(codice != null && !"".equals(codice) && descrizione != null && !"".equals(descrizione)) {
			return codice + " - " + descrizione;
		} else if(descrizione!= null && !"".equals(descrizione)) {
			return descrizione;													
		} else if(codice != null && !"".equals(codice)) {
			return codice;
		}	
		return null;
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
		grid.addSort(new SortSpecifier("flgEntrataUscita", SortDirection.DESCENDING));
		grid.addSort(new SortSpecifier("annoMovimento", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroMovimento", SortDirection.ASCENDING));	
		grid.addSort(new SortSpecifier("annoSub", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroSub", SortDirection.ASCENDING));	
		grid.addSort(new SortSpecifier("annoModifica", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("numeroModifica", SortDirection.ASCENDING));			
		grid.addSort(new SortSpecifier("descrizioneMovimento", SortDirection.ASCENDING));
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
				String tipoMovimento = record.getAttribute("tipoMovimento") != null ? record.getAttribute("tipoMovimento") : "";
				float importo = 0;
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					importo = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("importo"))).floatValue();			
				}
				//TODO correggere il calcolo dei totali considerando gli importi solo degli impegni/accertamenti padre, e gli importi dei sub e delle modifiche solo se presenti singolarmente (senza padre)
				if("E".equals(flgEntrataUscita) && "Accertamento".equalsIgnoreCase(tipoMovimento)) {
					totaleEntrate += importo;
				} else if("U".equals(flgEntrataUscita) && "Impegno".equalsIgnoreCase(tipoMovimento)) {
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
	
	public void onClickNewButton() {
		grid.deselectAllRecords();		
		final DettaglioMovimentiGSAWindow lDettaglioMovimentiGSAWindow = new DettaglioMovimentiGSAWindow(this, "dettaglioMovimentiGSAWindow", true, null) {
			
			@Override
			public void saveData(Record newRecord) {
				// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB						
				newRecord.setAttribute("id", "NEW_" + count++);
				addData(newRecord);	
			}		
		};
		lDettaglioMovimentiGSAWindow.show();
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {	
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {			
					if(isEditable() && isShowEditButtons() && isDatiRilevantiGsa()) {
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
				return buildImgButtonHtml("buttons/detail.png");
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return I18NUtil.getMessages().detailButton_prompt();				
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				onClickDetailButton(event.getRecord());				
			}
		});		
		return detailButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final DettaglioMovimentiGSAWindow lDettaglioMovimentiGSAWindow = new DettaglioMovimentiGSAWindow(this, "dettaglioMovimentiGSAWindow", false, record);
		lDettaglioMovimentiGSAWindow.show();
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
	
	public boolean isShowModifyButton(Record record) {
		return isDatiRilevantiGsa();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();		
		final DettaglioMovimentiGSAWindow lDettaglioMovimentiGSAWindow = new DettaglioMovimentiGSAWindow(this, "dettaglioMovimentiGSAWindow", true, record) {
			
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
		lDettaglioMovimentiGSAWindow.show();
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
	
	public boolean isShowDeleteButton(Record record) {
		return isDatiRilevantiGsa();
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	@Override
	protected void manageOnShowValue(RecordList data) {
		// ATTENZIONE:
		// ogni volta che setto un valore sul gridItem finisco qui dentro, sia facendo il setValue() sull'item sia settando il valore nel form o nel vm che lo contiene, quindi non solo quando carico i valori dalla loadDettaglio
		// anche quando fa lo show per la prima volta del gridItem (tipo selezionando il tab che lo contiene) entra qui dentro 
		if (data != null) {
			for (int i = 0; i < data.getLength(); i++) {
				Record lRecord = data.get(i);
				if(lRecord.getAttribute("id") == null) {
					lRecord.setAttribute("id", i);					
				}
				lRecord.setAttribute("valuesOrig", lRecord.toMap());
			}
		}
		setData(data);
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaMovimentiGSA", fields);
	}
	
	public boolean isShowDatiStoriciButton() {
		return false;
	}
	
	public void onClickDatiStoriciButton() {
		
	}
	
	public boolean isShowRefreshListButton() {
		return false;
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
		record.setAttribute("nomeEntita", "listaMovimentiGSA");
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
			rec.setAttribute("desStrutturaCompetente", getDescrizioneWithCodice(getGrid().getRecords()[i], "codRapidoStrutturaCompetente", "desStrutturaCompetente"));
			records[i] = rec;
		}
		return records;
	}
	
	public boolean showStrutturaCompetente() {
		return false;
	}
	
	protected boolean isDatiRilevantiGsa(){
		return false;
	}

	public abstract boolean isGrigliaEditabile();
	
}
