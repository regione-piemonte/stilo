/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DropCompleteEvent;
import com.smartgwt.client.widgets.events.DropCompleteHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.delibere.DettaglioPropostaDeliberaWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public class ListaEmendamentiItem extends GridItem {
	
	private ListaEmendamentiPopup _window;
	private NuovaPropostaAtto2CompletaDetail _detail;
	
	protected Map<String, ListaEmendamentiItem> mappaListGridSubEmendamenti;
	protected Map<String, Canvas> mappaExpansionComponent;
	
	protected ToolStripButton multiselectButton;
	protected ToolStripSeparator toolStripSeparator;
	protected Label listaEmendamentiBloccoRiordinoAutLabel;
	
	protected boolean multiselect;
	
	protected ListGridField idUd;
	protected ListGridField idProcess;
	protected ListGridField idEmendamento;
	protected ListGridField nroEmendamento;
	protected ListGridField nroSubEmendamento;
	protected ListGridField tsCaricamento;
	protected ListGridField strutturaProponente;
	protected ListGridField cdcStrutturaProponente;
	protected ListGridField firmatari;
	protected ListGridField tsPerfezionamento;
	protected ListGridField tipoFileRiferimento;
	protected ListGridField nroAllegatoRiferimento;
	protected ListGridField nroPaginaRiferimento;
	protected ListGridField nroRigaRiferimento;
	protected ListGridField effettoEmendamento; // sostituisce (S), aggiunge (A) o elimina (E)
	protected ListGridField emendamentoIntegrale; // (valori 1/0) Flag
	protected ListGridField testoHtml; // CKEDITOR
	protected ListGridField uriFile;
	protected ListGridField nomeFile; 
	protected ListGridField firmato; // (valori 1/0) Flag
	protected ListGridField mimetype;
	protected ListGridField convertibilePdf; // (valori 1/0) Flag
	protected ListGridField pareriEspressi; // data e autore
	protected ListGridField filePareri;	// NomeFile1|*|URI1||FlgFirmato1|*|DataPerfezionamento1|*|NomeFile2|*|URI2|*|FlgFirmato2|*|DataPerfezionamento2....
	protected ListGridField organoCollegiale;
	protected ListGridField subEmendamenti;

	protected ControlListGridField previewButtonField;
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	
	protected EmendamentiWindow emendamentiWindow;
	protected TabSet _tabSet;
	private boolean _subList;
	protected boolean _listaEmendamentiBloccoRiordinoAut;

	public ListaEmendamentiItem(String name, ListaEmendamentiPopup window, TabSet tabSet, NuovaPropostaAtto2CompletaDetail detail, boolean subList, boolean multiselect, boolean listaEmendamentiBloccoRiordinoAut) {
		super(name, subList ? "lista_subemendamenti" : "lista_emendamenti");
		
		this.multiselect = multiselect;
		mappaListGridSubEmendamenti = new LinkedHashMap<String, ListaEmendamentiItem>();
		mappaExpansionComponent = new LinkedHashMap<String, Canvas>();
		
		_tabSet = tabSet;
		_window = window;
		_detail = detail;
		_subList = subList;
		_listaEmendamentiBloccoRiordinoAut = listaEmendamentiBloccoRiordinoAut;
		
		setHeight("100%");

		setGridPkField("idEmendamento");
		setShowPreference(true);
		setShowNewButton(false);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(false);
		setShowEditButtons(true);
		
		idUd = new ListGridField("idUd", "Id. UD");
		idUd.setHidden(true);
		idUd.setCanHide(false);
		idUd.setCanGroupBy(false);
		
		idProcess = new ListGridField("idProcess", "Id. processo");
		idProcess.setHidden(true);
		idProcess.setCanHide(false);
		idProcess.setCanGroupBy(false);		
		
		idEmendamento = new ListGridField("idEmendamento", !subList ? "Id." : "Id. sub");
		idEmendamento.setType(ListGridFieldType.INTEGER);
		idEmendamento.setCanSort(false);
		idEmendamento.setCanHide(false);
		idEmendamento.setCanGroupBy(false);

		nroEmendamento = new ListGridField("nroEmendamento", "N°");
		nroEmendamento.setType(ListGridFieldType.INTEGER);
		nroEmendamento.setCanSort(false);
		nroEmendamento.setHidden(isSubList());
		nroEmendamento.setCanHide(false);
		nroEmendamento.setCanGroupBy(false);

		nroSubEmendamento = new ListGridField("nroSubEmendamento", "N° sub");
		nroSubEmendamento.setType(ListGridFieldType.INTEGER);
		nroSubEmendamento.setCanSort(false);
		nroSubEmendamento.setHidden(!isSubList());
		nroSubEmendamento.setCanHide(false);
		nroSubEmendamento.setCanGroupBy(false);

		tsCaricamento = new ListGridField("tsCaricamento", "Data e ora di caricamento");
		tsCaricamento.setType(ListGridFieldType.DATE);
		tsCaricamento.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsCaricamento.setWrap(false);
		tsCaricamento.setCanSort(false);
		tsCaricamento.setCanGroupBy(false);

		strutturaProponente = new ListGridField("strutturaProponente", "UO proponente");
		strutturaProponente.setCanSort(false);
		strutturaProponente.setCanGroupBy(false);

		cdcStrutturaProponente = new ListGridField("cdcStrutturaProponente", "Cdc UO proponente");
		cdcStrutturaProponente.setCanSort(false);
		cdcStrutturaProponente.setCanGroupBy(false);

		firmatari = new ListGridField("firmatari", "Firmatari");
		firmatari.setCanSort(false);
		firmatari.setCanGroupBy(false);

		tsPerfezionamento = new ListGridField("tsPerfezionamento", "Data e ora di perfezionamento");
		tsPerfezionamento.setType(ListGridFieldType.DATE);
		tsPerfezionamento.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		tsPerfezionamento.setWrap(false);
		tsPerfezionamento.setCanSort(false);
		tsPerfezionamento.setCanGroupBy(false);

		tipoFileRiferimento = new ListGridField("tipoFileRiferimento", "Riferimento emendamento");
		tipoFileRiferimento.setCanSort(false);
		tipoFileRiferimento.setCanGroupBy(false);

		nroAllegatoRiferimento = new ListGridField("nroAllegatoRiferimento", "N° allegato");
		nroAllegatoRiferimento.setType(ListGridFieldType.INTEGER);		
		nroAllegatoRiferimento.setCanSort(false);
		nroAllegatoRiferimento.setCanGroupBy(false);

		nroPaginaRiferimento = new ListGridField("nroPaginaRiferimento", "N° pagina");
		nroPaginaRiferimento.setType(ListGridFieldType.INTEGER);		
		nroPaginaRiferimento.setCanSort(false);
		nroPaginaRiferimento.setCanGroupBy(false);

		nroRigaRiferimento = new ListGridField("nroRigaRiferimento", "N° riga");
		nroRigaRiferimento.setType(ListGridFieldType.INTEGER);		
		nroRigaRiferimento.setCanSort(false);
		nroRigaRiferimento.setCanGroupBy(false);

		effettoEmendamento = new ListGridField("effettoEmendamento", "Effetto");
		effettoEmendamento.setCanSort(false);
		effettoEmendamento.setCanGroupBy(false);

		emendamentoIntegrale = new ListGridField("emendamentoIntegrale", "Integrale");
		emendamentoIntegrale.setCanGroupBy(false);
		emendamentoIntegrale.setCanSort(false);
		emendamentoIntegrale.setType(ListGridFieldType.ICON);
		emendamentoIntegrale.setIconWidth(16);
		emendamentoIntegrale.setIconHeight(16);
		Map<String, String> emendamentoIntegraleIcons = new HashMap<String, String>();		
		emendamentoIntegraleIcons.put("false", "blank.png");
		emendamentoIntegraleIcons.put("true", "ok.png");
		emendamentoIntegrale.setValueIcons(emendamentoIntegraleIcons);
		emendamentoIntegrale.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("emendamentoIntegrale") != null && Boolean.valueOf(record.getAttribute("emendamentoIntegrale"))) {
					return "Emendamento integrale";
				} else {
					return "Emendamento non integrale";
				}
			}
		});

		uriFile = new ListGridField("uriFile", "Uri file");
		uriFile.setHidden(true);
		uriFile.setCanHide(false);
		uriFile.setCanGroupBy(false);

		nomeFile = new ListGridField("nomeFile", "Nome file");
		nomeFile.setHidden(true);
		nomeFile.setCanHide(false);
		nomeFile.setCanGroupBy(false);

		firmato = new ListGridField("firmato", "Firmato");
		firmato.setCanGroupBy(false);
		firmato.setCanSort(false);
		firmato.setType(ListGridFieldType.ICON);
		firmato.setIconWidth(16);
		firmato.setIconHeight(16);
		Map<String, String> firmatoIcons = new HashMap<String, String>();		
		firmatoIcons.put("false", "blank.png");
		firmatoIcons.put("true", "firma/firma.png");
		firmato.setValueIcons(firmatoIcons);
		firmato.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("firmato") != null && Boolean.valueOf(record.getAttribute("firmato"))) {
					return "Firmato";
				} else {
					return "Non firmato";
				}
			}
		});

		mimetype = new ListGridField("mimetype", "Mimetype"); 
		mimetype.setHidden(true);
		mimetype.setCanHide(false);
		mimetype.setCanGroupBy(false);

		convertibilePdf = new ListGridField("convertibilePdf", "Convertibile Pdf"); 
		convertibilePdf.setHidden(true);
		convertibilePdf.setCanHide(false);
		convertibilePdf.setCanGroupBy(false);

		pareriEspressi = new ListGridField("pareriEspressi", "Pareri espressi"); 
		pareriEspressi.setCanSort(false);
		pareriEspressi.setCanGroupBy(false);

		filePareri = new ListGridField("filePareri", "File Pareri");	
		filePareri.setHidden(true);
		filePareri.setCanHide(false);
		filePareri.setCanGroupBy(false);

		testoHtml = new ListGridField("testoHtml", "Testo");
		testoHtml.setHidden(true);
		testoHtml.setCanHide(false);
		testoHtml.setCanGroupBy(false);

		organoCollegiale = new ListGridField("organoCollegiale", "Organo collegiale");
		organoCollegiale.setHidden(true);
		organoCollegiale.setCanHide(false);
		organoCollegiale.setCanGroupBy(false);

		subEmendamenti = new ListGridField("subemendamenti", "Subemendamenti");
		subEmendamenti.setHidden(true);
		subEmendamenti.setCanHide(false);
		subEmendamenti.setCanGroupBy(false);

		if (!subList) {
			setGridFields(
					idUd,
					idProcess,
					idEmendamento,
					nroEmendamento,
					tsCaricamento,
					strutturaProponente,
					cdcStrutturaProponente,
					firmatari,
					tsPerfezionamento,
					tipoFileRiferimento,
					nroAllegatoRiferimento,
					nroPaginaRiferimento,
					nroRigaRiferimento,
					effettoEmendamento, 
					emendamentoIntegrale, 
					testoHtml, 
					uriFile,
					nomeFile, 
					firmato,
					mimetype,
					convertibilePdf, 
					pareriEspressi,
					filePareri,
					organoCollegiale,
					subEmendamenti
					);
		} else {
			setGridFields(
					idUd,
					idProcess,
					idEmendamento,
					nroSubEmendamento,
					tsCaricamento,
					strutturaProponente,
					cdcStrutturaProponente,
					firmatari,
					tsPerfezionamento,
					tipoFileRiferimento,
					nroAllegatoRiferimento,
					nroPaginaRiferimento,
					nroRigaRiferimento,
					effettoEmendamento, 
					emendamentoIntegrale, 
					testoHtml, 
					uriFile,
					nomeFile, 
					firmato,
					mimetype,
					convertibilePdf, 
					pareriEspressi,
					filePareri,
					organoCollegiale,
					subEmendamenti
					);
		}
	}
	
	private Boolean isSubList() {
		return _subList;
	}

	@Override
	protected Canvas createExpansionComponent(final ListGridRecord record) {
		String idUdSubEmendamento = record.getAttribute("idUd");
		// Verifico se avevo già creato l'ExpansionComponent
		if (mappaExpansionComponent.get(idUdSubEmendamento) != null) {
			return mappaExpansionComponent.get(idUdSubEmendamento);
		}
		final Record[] subEmendamentiList = record.getAttributeAsRecordArray("listaSubEmendamenti");
		final ListGrid parentGrid = this.grid;
		
		VLayout layout = new VLayout(5);    
		layout.setPadding(5); 

		DynamicForm listaSubEmendamentiForm = new DynamicForm();
		listaSubEmendamentiForm.setKeepInParentRect(true);
		listaSubEmendamentiForm.setWidth100();
		listaSubEmendamentiForm.setHeight100();
		listaSubEmendamentiForm.setNumCols(7);
		listaSubEmendamentiForm.setColWidths(10, 10, 10, 10, 10, "*", "*");
		listaSubEmendamentiForm.setCellPadding(2);
		listaSubEmendamentiForm.setWrapItemTitles(false);

		ListaEmendamentiItem subEmendamentiItem = new ListaEmendamentiItem("listaSubEmendamenti", _window, _tabSet, _detail, true, getMultiselect(), _listaEmendamentiBloccoRiordinoAut) {
			@Override
			public boolean showRefreshButton() {
				return false;
			}
			
			@Override
			protected void saveSubEmendamentiXOrd(ListGridRecord[] listGridRecords) {
				record.setAttribute("listaSubEmendamenti", listGridRecords);
			}
			
			@Override
			protected RecordList getParentList() {
				return new RecordList(parentGrid.getRecords());
			}
			
			@Override
			protected ListGrid getParentGrid() {
				return parentGrid;
			}
		}; 
		
		subEmendamentiItem.setAutoDestroy(false);
		subEmendamentiItem.setHeight(224);    
		subEmendamentiItem.setCellHeight(22);    
		subEmendamentiItem.setCanEdit(false);
		subEmendamentiItem.setShowPreference(true);

		listaSubEmendamentiForm.setFields(subEmendamentiItem);	
		layout.addMember(listaSubEmendamentiForm);                                   
		listaSubEmendamentiForm.setValue("listaSubEmendamenti", subEmendamentiList);
		
		// Salvo la ListGrid dei sotto emendamenti
		mappaListGridSubEmendamenti.put(idUdSubEmendamento, subEmendamentiItem);
		// Salvo l'ExpansionComponent dei sotto emendamenti
		mappaExpansionComponent.put(idUdSubEmendamento, layout);

		return layout;    

	}
	
	@Override
	public boolean canExpandGridRecord(ListGridRecord record, int rowNum, ListGrid listGrid) {
		return record.getAttributeAsRecordArray("listaSubEmendamenti") != null  && record.getAttributeAsRecordArray("listaSubEmendamenti").length > 0;
	}
	
	@Override
	public ListGrid buildGrid() {
		final ListGrid grid = super.buildGrid();
		grid.setCanExpandRecords(!isSubList());
		grid.setShowAllRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE);
		grid.setCanDragRecordsOut(false);
		grid.setCanAcceptDroppedRecords(false);
		if (isSubList() && getMultiselect()) {
			grid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			grid.setSelectionType(SelectionStyle.SIMPLE);
		}
		grid.setSelectOnExpandRecord(false);
		grid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (!getMultiselect() && event.getFieldNum() == 0){
					event.cancel();
				} else if (getMultiselect() && (event.getFieldNum() == 0 || event.getFieldNum() == 1)) {
					event.cancel();
				} else {
					RecordList listaEmendamenti = new RecordList(grid.getRecords());
					if (listaEmendamenti != null && listaEmendamenti.getLength() > 0) {
						if (emendamentiWindow == null) {
							emendamentiWindow = new EmendamentiWindow("Lista emendamenti", "emendamenti_window", _tabSet, _detail);
						}
						Record recordToPass = new Record();
						recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);
						recordToPass.setAttribute("parentListaEmendamenti", getParentList());
						recordToPass.setAttribute("posEmendamento", grid.getRecordIndex(event.getRecord()));
						emendamentiWindow.initContent(recordToPass);
						emendamentiWindow.show();
						_window.markForDestroy();
						
					} else {
						SC.say("Nessun emendamento trovato");
					}
				}
			}
		});
		
		grid.addDropCompleteHandler(new DropCompleteHandler() {
			
			@Override
			public void onDropComplete(DropCompleteEvent event) {
				refreshNroEmendamento();
				if (isSubList()) {
					saveSubEmendamentiXOrd(grid.getRecords());
				}
				// grid.sort("nroEmendamento");
			}
		});
		// Ordinamenti iniziali
		if (isSubList()) {
			grid.addSort(new SortSpecifier("nroSubEmendamento", SortDirection.ASCENDING));			
		} else {
			grid.addSort(new SortSpecifier("nroEmendamento", SortDirection.ASCENDING));			
		}
		
		return grid;		
	}

	protected RecordList getParentList() {
		return null;
	}

	protected ListGrid getParentGrid() {
		return null;
	}
	
	protected void saveSubEmendamentiXOrd(ListGridRecord[] listGridRecords) {
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {

	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields(null, fields);
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		if(previewButtonField == null) {
			previewButtonField = buildPreviewButtonField();					
		}
		if(detailButtonField == null) {
			detailButtonField = buildDetailButtonField();					
		}
		
		if(modifyButtonField == null) {
			modifyButtonField = buildModifyButtonField();					
		}

		buttonsList.add(previewButtonField);
		buttonsList.add(detailButtonField);
		buttonsList.add(modifyButtonField);

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
	
	protected void onClickDetailButton(ListGridRecord record) {
		String title = "Dettaglio proposta/argomento emendamento N° " + record.getAttributeAsString("nroEmendamento");		
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));		
		if(record.getAttributeAsString("idProcess") != null && !"".equals(record.getAttributeAsString("idProcess"))) {
			AurigaLayout.apriDettaglioPratica(record.getAttributeAsString("idProcess"), title, null);
		} else {
			new DettaglioRegProtAssociatoWindow(lRecord, title);		
		}
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {		
//				if(record.getAttributeAsString("idProcess") != null && !"".equals(record.getAttributeAsString("idProcess"))) {
				if (showModifyButtonField(record)) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
//				if(record.getAttributeAsString("idProcess") != null && !"".equals(record.getAttributeAsString("idProcess"))) {
				if (showModifyButtonField(record)) {
					return I18NUtil.getMessages().modifyButton_prompt();	
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
//				if(event.getRecord().getAttributeAsString("idProcess") != null && !"".equals(event.getRecord().getAttributeAsString("idProcess"))) {
				if (showModifyButtonField(event.getRecord())) {
					onClickModifyButton(event.getRecord());						
				}
			}
		});		
//		modifyButtonField.setShowIfCondition(new ListGridFieldIfFunction() {
//			
//			@Override
//			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
//				return canEditEmendamenti;
//			}
//		});
		return modifyButtonField;
	}

	protected void onClickModifyButton(ListGridRecord record) {
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", record.getAttributeAsString("idUd"));		
		if(record.getAttributeAsString("idProcess") != null && !"".equals(record.getAttributeAsString("idProcess"))) {
			lRecord.setAttribute("idProcess", record.getAttributeAsString("idProcess"));
			lRecord.setAttribute("activityName", "#POST_DISCUSSIONE_AULA_" +  record.getAttributeAsString("organoCollegiale"));
			lRecord.setAttribute("idDefProcFlow", record.getAttributeAsString("idTipoFlussoActiviti"));
			lRecord.setAttribute("idInstProcFlow", record.getAttributeAsString("idIstanzaFlussoActiviti"));
			new DettaglioPropostaDeliberaWindow(lRecord, "Modifica proposta/argomento emendamento N° " + record.getAttributeAsString("nroEmendamento"), true, false);
		}
	}

	protected ControlListGridField buildPreviewButtonField() {
		ControlListGridField previewButtonField = new ControlListGridField("previewButton");  
		previewButtonField.setAttribute("custom", true);	
		previewButtonField.setShowHover(true);		
		previewButtonField.setCanReorder(false);
		previewButtonField.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(record.getAttribute("uriFile") != null && !"".equals(record.getAttributeAsString("uriFile"))) {
					return buildImgButtonHtml("file/preview.png");
				}
				return null;
			}
		});
		previewButtonField.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttribute("uriFile") != null && !"".equals(record.getAttributeAsString("uriFile"))) {
					return I18NUtil.getMessages().protocollazione_detail_previewButton_prompt();				
				}
				return null;
			}
		});		
		previewButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final ListGridRecord listRecord = event.getRecord();
				if(listRecord.getAttribute("uriFile") != null && !"".equals(listRecord.getAttributeAsString("uriFile"))) {
					onPreviewButton(listRecord);						
				}
			}
		});
		
		return previewButtonField;
	}
	
	protected void onPreviewButton(ListGridRecord listRecord) {
		String uriFileEmendamento = listRecord.getAttribute("uriFile");
		if (uriFileEmendamento != null && !uriFileEmendamento.equals("")) {
			if(listRecord.getAttributeAsBoolean("convertibilePdf")) {
				clickPreviewFile(listRecord);					
			}
		}
	}

	// Preview del file
	public void clickPreviewFile(final ListGridRecord listRecord) {
		final String idUd = listRecord != null ? listRecord.getAttribute("idUd") : null;
//		String idDoc = listRecord.getAttribute("idDoc");
		addToRecent(idUd, null);
		final String display = listRecord.getAttribute("nomeFile");
		final String uri = listRecord.getAttribute("uriFile");
		Record infoRecord = new Record();
		infoRecord.setAttribute("tipoFirma", display != null && display.toLowerCase().endsWith(".p7m") ? "CAdES" : "");
		infoRecord.setAttribute("firmato", listRecord.getAttributeAsBoolean("firmato"));
		infoRecord.setAttribute("mimetype", listRecord.getAttribute("mimetype"));
//		infoRecord.setAttribute("impronta", listRecord.getAttributeAsString("impronta"));
		infoRecord.setAttribute("bytes", new Long(listRecord.getAttribute("bytes")));					
		infoRecord.setAttribute("convertibile", listRecord.getAttributeAsBoolean("convertibilePdf"));
		infoRecord.setAttribute("correctFileName", display);
		InfoFileRecord info = new InfoFileRecord(infoRecord);
		PreviewControl.switchPreview(uri, true, info, "FileToExtractBean", display, null, true, false);
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> editButtons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton refreshButton = new ToolStripButton();   
		refreshButton.setIcon("buttons/refreshList.png");   
		refreshButton.setIconSize(16);
		refreshButton.setPrompt(I18NUtil.getMessages().refreshListButton_prompt());
		refreshButton.addClickHandler(new ClickHandler() {	

			@Override
			public void onClick(ClickEvent event) {
				_detail.recuperaListaEmendamenti(new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						RecordList listaEmendamenti = object.getAttributeAsRecordList("listaEmendamenti");
						boolean listaEmendamentiBloccoRiordinoAut = object.getAttributeAsBoolean("listaEmendamentiBloccoRiordinoAut").booleanValue();
						_detail.listaEmendamentiItem.setValue(listaEmendamenti);
						_detail.listaEmendamentiBloccoRiordinoAutItem.setValue(listaEmendamentiBloccoRiordinoAut);
						Record recordToPass = new Record();
						recordToPass.setAttribute("listaEmendamenti", listaEmendamenti);
						_window.initContent(recordToPass);
					}
				});
			}   
		});
		
		multiselectButton = new ToolStripButton();   
		multiselectButton.setIcon("buttons/multiselect.png");
		multiselectButton.setIconSize(16);
		multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
		multiselectButton.addClickHandler(new ClickHandler() {	

			@Override
			public void onClick(ClickEvent event) {
				if (getMultiselect()) {
					setMultiselect(false);
				} else {
					setMultiselect(true);
				}
			}
		});
				
		if(showRefreshButton()) {
			editButtons.add(refreshButton);
		}
		
		if(showMultiselectButton()) {
			editButtons.add(multiselectButton);
		}

		return editButtons;
	}
	
	@Override
	public List<Canvas> buildCustomEditCanvas() {
		List<Canvas> editCanvas = new ArrayList<Canvas>();
		toolStripSeparator = new ToolStripSeparator();
		toolStripSeparator.setVisibility((!_subList && _listaEmendamentiBloccoRiordinoAut) ? Visibility.VISIBLE : Visibility.HIDDEN);
		editCanvas.add(toolStripSeparator);
		listaEmendamentiBloccoRiordinoAutLabel = new Label();
		listaEmendamentiBloccoRiordinoAutLabel.setIcon("pratiche/icone/bloccoRiordAutoOut.png");
		listaEmendamentiBloccoRiordinoAutLabel.setPrompt("Disattivo riordino automatico: impostato ordinamento manuale");
		listaEmendamentiBloccoRiordinoAutLabel.setVisibility((!_subList && _listaEmendamentiBloccoRiordinoAut) ? Visibility.VISIBLE : Visibility.HIDDEN);
		editCanvas.add(listaEmendamentiBloccoRiordinoAutLabel);
		return editCanvas;
		
	}
	
	public boolean showRefreshButton() {
		return !isSubList();
	}
	
	public boolean showMultiselectButton() {
		return !isSubList();
	}
	
	public Boolean getMultiselect() {
		return multiselect;
	}
	
	public void setMultiselect(Boolean multiselect) {
		this.multiselect = multiselect;
		if (multiselect) {
			grid.deselectAllRecords();
			grid.setShowSelectedStyle(false);
			multiselectButton.setIcon("buttons/multiselect_off.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOffButton_prompt());
			grid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
			grid.setSelectionType(SelectionStyle.SIMPLE);
		} else {
			grid.deselectAllRecords();
			grid.setShowSelectedStyle(true);
			multiselectButton.setIcon("buttons/multiselect.png");
			multiselectButton.setPrompt(I18NUtil.getMessages().multiselectOnButton_prompt());
			grid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);
			grid.setSelectionType(SelectionStyle.SINGLE);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				grid.markForRedraw();
			}
		});
		
		if (mappaListGridSubEmendamenti != null && mappaListGridSubEmendamenti.keySet() != null) {
			Iterator<String> iteratore = mappaListGridSubEmendamenti.keySet().iterator();
			while(iteratore.hasNext()) {
				String key = iteratore.next();
				ListaEmendamentiItem listGridSubEmendamento =  mappaListGridSubEmendamenti.get(key);
				if (listGridSubEmendamento != null) {
					listGridSubEmendamento.setMultiselect(multiselect);
				}
				
			}
		}
	}

	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

				}
			});
		}
	}
	
	public void refreshNroEmendamento() {
//		List<String> listIdRecordsSel = getIdSelectedRecords();
//		grid.deselectAllRecords();
		int n = 1;
		for (ListGridRecord record : grid.getRecords()) {
			if(isSubList()) {
				record.setAttribute("nroSubEmendamento", n);
			} else {
				record.setAttribute("nroEmendamento", n);
			}
			n++;
		}
		updateGridItemValue();		
//		reselectRecords(listIdRecordsSel);
	}

//	private List<String> getIdSelectedRecords() {
//		List<String> listIdRecordsSel = new ArrayList<String>();		
//		ListGridRecord[] recordsSel = grid.getSelectedRecords();
//		for (ListGridRecord listGridRecord : recordsSel) {
//			listIdRecordsSel.add(listGridRecord.getAttribute("idUd"));
//		}		
//		return listIdRecordsSel;
//	}
//
//	private void reselectRecords(List<String> listIdRecordsSel) {
//		for (String idRecordToSel : listIdRecordsSel) {
//			for (ListGridRecord listGridRecord : grid.getRecords()) {
//				if (listGridRecord.getAttribute("idUd").equals(idRecordToSel)) {
//					grid.selectRecord(listGridRecord);
//				}
//			}
//		}
//	}
	
	public void sort(String sortField) {
		if (grid != null) {
			grid.sort(sortField);
		}
	}
	
	public ListGrid getGrid(){
		return grid;
	}
	
	public List<ListGridRecord> getRecordSelezionati() {
		List<ListGridRecord> listaRecordSelezionati = new LinkedList<ListGridRecord>();
		ListGridRecord[] arrayRecordSelezionati = grid.getSelectedRecords();
		if (arrayRecordSelezionati != null && arrayRecordSelezionati.length > 0) {		
			Collections.addAll(listaRecordSelezionati, arrayRecordSelezionati);
		}
		
		Set<String> setKey = mappaListGridSubEmendamenti.keySet();
		if (setKey != null && !setKey.isEmpty()) {
			for (String key : setKey) {
				ListaEmendamentiItem subEmendamentoItem = mappaListGridSubEmendamenti.get(key);
				ListGrid gridSubEmendamenti = subEmendamentoItem.getGrid();
				ListGridRecord[] subEmendamentiSelezionati = gridSubEmendamenti.getSelectedRecords();
				for (ListGridRecord subEmendamentoSelezionato : subEmendamentiSelezionati) {
					listaRecordSelezionati.add(subEmendamentoSelezionato);
				}
				
			}
		}
		return listaRecordSelezionati;
	}
	
	public void clearMappe() {
		if (mappaListGridSubEmendamenti != null) {
			mappaListGridSubEmendamenti.clear();
		} else {
			mappaListGridSubEmendamenti = new LinkedHashMap<String, ListaEmendamentiItem>();
		}
		if (mappaExpansionComponent != null) {
			mappaExpansionComponent.clear();
		} else {
			mappaExpansionComponent = new LinkedHashMap<String, Canvas>();
		}
	}
	
	public boolean showModifyButtonField(ListGridRecord record) {
//		if (record.getAttributeAsString("idProcess") != null && !"".equals(record.getAttributeAsString("idProcess")) && record.getAttributeAsString("organoColleggiale") != null && !"".equals(record.getAttributeAsString("organoColleggiale"))) {
//			return true;
//		} else {
//			return false;
//		}
		return false;
	}
	
	public void setBloccoRiordinoAut(boolean listaEmendamentiBloccoRiordinoAut) {
		_listaEmendamentiBloccoRiordinoAut = listaEmendamentiBloccoRiordinoAut;
		if (toolStripSeparator != null) {
			toolStripSeparator.setVisibility((!_subList && _listaEmendamentiBloccoRiordinoAut) ? Visibility.VISIBLE : Visibility.HIDDEN);
		}
		if (listaEmendamentiBloccoRiordinoAutLabel != null) {
			listaEmendamentiBloccoRiordinoAutLabel.setVisibility((!_subList && _listaEmendamentiBloccoRiordinoAut) ? Visibility.VISIBLE : Visibility.HIDDEN);
		}
	}
	
}
	
